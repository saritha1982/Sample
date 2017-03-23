package activiti.agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;
import com.rallydev.rest.util.Ref;

import activiti.agent.bean.RallyInfo;
import activiti.agent.bean.UserInfo;
import activiti.agent.db.RallyAgentDAO;

public class RallyActivitiAgent implements JavaDelegate {

	@Override
	public void execute(DelegateExecution delexe) throws Exception {
		System.out.println("called in service... Executing " + this.getClass().getName() + " Agent...");

		// create rally project
		RallyActivitiAgent instance = new RallyActivitiAgent();
		System.out.println(delexe.getVariables());
		// check if this process is for project onboarding or user onboarding
		// and call appropriate processing
		System.out.println(delexe.getProcessDefinitionId());
		if (delexe.getProcessDefinitionId().startsWith("UserOnboardingProcess")) {
			instance.onboardUsers(delexe.getVariable("custid", String.class),
					delexe.getVariable("projectOnboardId", String.class), delexe);
		} else if (delexe.getProcessDefinitionId().startsWith("ProjectOnboardingProcess")) {
			instance.createProject(delexe, delexe.getVariable("parent", String.class),
					delexe.getVariable("note", String.class), delexe.getVariable("name", String.class),
					delexe.getVariable("owner", String.class), delexe.getVariable("custid", String.class));
		}
	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * RallyActivitiAgent test = new RallyActivitiAgent(); //
	 * test.createProject(null, "AS Software Development", "AS-CICD", //
	 * "Test Project for checking child project creation", //
	 * "TestProj-Child-AS-CICD", "anunag"); UserInfo userTest=new UserInfo();
	 * userTest.setUserName("rgollapu");
	 * userTest.setEmail("rgollapu@cisco.com"); userTest.setAcessLevel("User");
	 * test.createRallyUser("76af68f8-e3ff-11e6-9ef9-005056bf51bd",
	 * "4thProject04", userTest); RallyAgentDAO dao = new RallyAgentDAO();
	 * 
	 * String rallyObjId=dao.getRallyUserOnBoardObjId(
	 * "76af68f8-e3ff-11e6-9ef9-005056bf51bd", userTest.getEmail(),
	 * "4thProject04"); System.out.println("rallyObjId"+rallyObjId); // provide
	 * appropriate permission to user to corresponding // project
	 * test.provideUsersAccess("76af68f8-e3ff-11e6-9ef9-005056bf51bd",
	 * "4thProject04", userTest, rallyObjId); }
	 */

	/**
	 * @param delexe
	 * @param parent
	 * @param dsc
	 * @param name
	 * @param owner
	 * @param custId
	 * @throws Exception
	 */
	private void createProject(DelegateExecution delexe, String parent, String dsc, String name, String owner,
			String custId) throws Exception {

		// get rally info and use for API invocation
		RallyAgentDAO dao = new RallyAgentDAO();
		RallyInfo rally = dao.getRallyDetails(custId);
		// Create and configure a new instance of RallyRestApi
		RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
		restApi.setApplicationName("TestApp");
		
		try {
			// get rally workspace id for this customer
			String rallyWrksp = dao.getWorkSpaceObjId(custId);
			String categoryObjId="";
			String categoryName="";
			// Create a Project
			JsonObject newProject = new JsonObject();
			newProject.addProperty("Name", name);
			newProject.addProperty("Description", "");
			newProject.addProperty("State", "Open");
			if (owner != null && owner.length() > 0) {
				String userObjId = dao.getRallyUserObjId(custId, owner);
				newProject.addProperty("Owner",
						"/owner/" + userObjId == null || userObjId.trim().length() < 1 ? "62511710928" : userObjId);
			}
			newProject.addProperty("Workspace", "/workspace/" + rallyWrksp);
			if (parent != null && parent.trim().length() > 0 && !parent.equalsIgnoreCase("--Select-a-Parent-Project--")) {
				newProject.addProperty("Parent", "/project/" + dao.getProjectObjId(rallyWrksp, parent));
				System.out.println("Created projected under parent by name "+parent);
			}
			else{
				categoryName=delexe.getVariable("categoryName", String.class);
				categoryObjId=dao.getCategoryRallyObjId(categoryName);
				newProject.addProperty("Parent", "/project/" +categoryObjId);
				System.out.println("Json Object for category  "+newProject);
				System.out.println("Created projected under category by name "+categoryName);
			}
			CreateRequest createRequest = new CreateRequest("Project", newProject);
			CreateResponse createResponse = restApi.create(createRequest);
			JsonObject respObj = createResponse.getObject();
			System.out.println("Response object " + respObj);
			System.out.println(String.format("Created %s", respObj.get("_ref").getAsString()));
			// here update the response to DB
			dao.createRallyProject(respObj.get("ObjectID").getAsString(), respObj.get("Name").getAsString(), "",
					rallyWrksp);
			System.out.println("Rally Project ObjectID" + respObj.get("ObjectID").getAsString());
			dao.updateProjectObjId(custId, respObj.get("ObjectID").getAsString(),
					delexe.getVariable("projectOnboardId", String.class));

			// update the process with url
			if (delexe != null) {
				delexe.setVariable("RallyBaseURL", respObj.get("_ref").toString());
			}
			System.out.println(
					"After setting RallyBaseURL in the workflow " + delexe.getVariable("RallyBaseURL", String.class));

		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			restApi.close();
		}

	}

	private void onboardUsers(String custId, String projectObjId, DelegateExecution delexe) throws Exception {
		try {

			RallyAgentDAO dao = new RallyAgentDAO();
			if (delexe.getVariable("addOnUser", String.class).equalsIgnoreCase("false")) {
				System.out.println("Adding user in rally while onbaording project");
				List<UserInfo> usersList = dao.getUsersForProject(custId, projectObjId);
				for (UserInfo user : usersList) {
					// check if user exists, if not create
					createRallyUser(custId, projectObjId, user);
					String rallyObjId = dao.getRallyUserOnBoardObjId(custId, user.getEmail(), projectObjId);
					System.out.println("rallyObjId" + rallyObjId);

					// provide appropriate permission to user to corresponding
					// project
					provideUsersAccess(custId, projectObjId, user, rallyObjId);
				}
			} else {
				System.out.println("Adding user in rally");
				String emailAddress = delexe.getVariable("emailAddress", String.class);
				UserInfo user = dao.getUserForProject(custId, projectObjId, emailAddress);
				createRallyUser(custId, projectObjId, user);
				String rallyObjId = dao.getRallyUserOnBoardObjId(custId, user.getEmail(), projectObjId);
				System.out.println("rallyObjId" + rallyObjId);

				// provide appropriate permission to user to corresponding
				// project
				provideUsersAccess(custId, projectObjId, user, rallyObjId);

			}

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	private String getRole(String access) {
		String role = "";
		if ("User".equalsIgnoreCase(access)) {
			role = "Editor";
		}

		if ("Admin".equalsIgnoreCase(access)) {
			role = "Project Admin";
		}
		if ("No Access".equalsIgnoreCase(access)) {
			role = "Viewer";
		}
		if ("Viewer".equalsIgnoreCase(access)) {
			role = "Viewer";
		}
		return role;
	}

	private void provideUsersAccess(String custId, String projectObjId, UserInfo user, String userObjId)
			throws Exception {
		RallyRestApi restApi = null;
		try {

			RallyAgentDAO dao = new RallyAgentDAO();
			// Create and configure a new instance of RallyRestApi
			RallyInfo rally = dao.getRallyDetails(custId);

			restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
			restApi.setApplicationName("TestApp");

			// Create a Project permission
			JsonObject newProject = new JsonObject();
			System.out.println("User Object ID  " + userObjId.trim());
			newProject.addProperty("User", "user/" + userObjId.trim());
			newProject.addProperty("Role", getRole(user.getAcessLevel()));
			String rallyWrksp = dao.getWorkSpaceObjId(custId);
			System.out.println(" rallyWrksp  " + rallyWrksp);
			newProject.addProperty("Workspace", "/workspace/" + rallyWrksp);
			System.out.println(
					"Query to get ProjectObjId" + dao.getProjectObjId(rallyWrksp, dao.getProjectName(projectObjId)));
			// add project id
			newProject.addProperty("Project",
					"/project/" + dao.getProjectObjId(rallyWrksp, dao.getProjectName(projectObjId)));
			System.out.println("Provide Project Access for user json " + newProject);
			CreateRequest createRequest = new CreateRequest("ProjectPermission", newProject);
			System.out.println("createRequest URL " + createRequest.toUrl());
			CreateResponse createResponse = restApi.create(createRequest);
			JsonObject respObj = createResponse.getObject();
			if (createResponse.wasSuccessful()) {
				System.out.println(String.format("Created %s", respObj.get("_ref").getAsString()));
			} else {
				System.out.println("Permission didn't apply because of the following error ");
				String[] errorList;
				errorList = createResponse.getErrors();
				for (int i = 0; i < errorList.length; i++) {
					System.out.println("Error list  " + errorList[i]);
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			restApi.close();
		}
	}

	private void createRallyUser(String custId, String projectObjId, UserInfo user) throws Exception {
		RallyRestApi restApi = null;
		try {

			RallyAgentDAO dao = new RallyAgentDAO();
			// Create and configure a new instance of RallyRestApi
			RallyInfo rally = dao.getRallyDetails(custId);
			restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
			restApi.setApplicationName("TestApp");

			// Create a Project
			JsonObject newProject = new JsonObject();
			newProject.addProperty("UserName", user.getUserName() + "@cisco.com");
			newProject.addProperty("EmailAddress", user.getEmail());
			newProject.addProperty("DisplayName", user.getUserName());
			CreateRequest createRequest = new CreateRequest("User", newProject);
			CreateResponse createResponse = restApi.create(createRequest);
			if (createResponse.wasSuccessful()) {
				System.out.println(
						String.format("Created User %s", createResponse.getObject().get("_ref").getAsString()));
				String ref = Ref.getRelativeRef(createResponse.getObject().get("_ref").getAsString());
				System.out.println(String.format("\nReading User %s...", ref));
				GetRequest getRequest = new GetRequest(ref);
				GetResponse getResponse = restApi.get(getRequest);
				JsonObject obj = getResponse.getObject();
				System.out.println(String.format("Read User. Name = %s, State = %s", obj.get("UserName").getAsString(),
						obj.get("EmailAddress").getAsString()) + "Object Id is" + obj.get("ObjectID").getAsString());
				dao.updateRallyUserObjId(obj.get("ObjectID").getAsString(), projectObjId);
				System.out.println("User Object ID " + obj.get("ObjectID").getAsString());
				dao.updateRallyUserObjId(obj.get("ObjectID").getAsString(), projectObjId);

			} else {
				System.out.println("User Already Exists in Rally so quering the rallyUserlist table" + user.getEmail());
				String rallyUserObjId = "";
				if (!(user.getEmail().equalsIgnoreCase("") || user.getEmail().length() == 0)) {
					QueryRequest request = new QueryRequest("Users");
					request.setFetch(new Fetch(new String[] { "UserName" }));
					// request.setLimit(65870);
					request.setQueryFilter(((new QueryFilter("UserName", "=", user.getEmail()))));
					QueryResponse response = restApi.query(request);
					if (response.wasSuccessful()) {
						for (int i = 0; i < response.getTotalResultCount(); i++) {
							rallyUserObjId = StringUtils.substringBefore(
									StringUtils.substringAfterLast(
											response.getResults().get(i).getAsJsonObject().get("_ref").toString(), "/"),
									"\"");
							System.out.println("rallyUserObjId found for user using Rally API" + user.getEmail()
									+ " ====" + rallyUserObjId);

						}
					}

					// rallyUserObjId=dao.getRallyUserObjId(user.getEmail()).get(0);
					if (!(rallyUserObjId.equalsIgnoreCase("") || rallyUserObjId.length() == 0)) {
						System.out
								.println("rallyUserObjId found for user " + user.getEmail() + " ====" + rallyUserObjId);
						dao.updateRallyUserObjId(rallyUserObjId, user.getEmail());
					} else {
						System.out.println(
								"Invoke the below method to get the new rally user's list otherwise dont invoke");
						// getRallyUsers(restApi);
					}

				}

			}

		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			restApi.close();
		}
	}

	/**
	 * @param restApi
	 * @throws IOException
	 * 
	 */
	private void getRallyUsers(RallyRestApi restApi) throws IOException {
		try {
			QueryRequest request = new QueryRequest("Users");
			restApi.setApplicationName("TestApp");
			request.setFetch(new Fetch(new String[] { "UserName" }));
			QueryResponse response = restApi.query(request);
			System.out.println("TotalResultCount: " + response.getTotalResultCount());
			if (response.wasSuccessful()) {
				File myFile = new File("MyTestFile.txt");
				// check if file exist, otherwise create the file before writing
				if (!myFile.exists()) {
					myFile.createNewFile();
				}
				BufferedWriter bufferedWriter = null;

				Writer writer = new FileWriter(myFile);
				bufferedWriter = new BufferedWriter(writer);

				for (int i = 0; i < response.getTotalResultCount(); i++) {
					bufferedWriter.write(response.getResults().get(i).getAsJsonObject().get("UserName") + "="
							+ response.getResults().get(i).getAsJsonObject().get("_ref") + "\n");
				}
				bufferedWriter.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (restApi != null) {
				restApi.close();
			}

		}
	}

}
