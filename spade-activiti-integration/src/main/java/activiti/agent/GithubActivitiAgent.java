package activiti.agent;

import java.net.URI;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import activiti.agent.bean.GitHubInfo;
import activiti.agent.bean.UserInfo;
import activiti.agent.constants.CommonConstants;
import activiti.agent.db.GitAgentDAO;
import activiti.agent.db.RallyAgentDAO;


public class GithubActivitiAgent implements JavaDelegate {

	@Override
	public void execute(DelegateExecution delexe) throws Exception {
		System.out.println("called in service... Executing " + this.getClass().getName() + " Agent...");

		GithubActivitiAgent instance = new GithubActivitiAgent();
		if (delexe.getProcessDefinitionId().startsWith("UserOnboardingProcess")) {
			instance.onboardUsers(delexe.getVariable("custid", String.class),
					delexe.getVariable("projectOnboardId", String.class), delexe);

		} else if (delexe.getProcessDefinitionId().startsWith("ProjectOnboardingProcess")) {
			instance.createProject(delexe, delexe.getVariable("custid", String.class),
					delexe.getVariable("tech", String.class), delexe.getVariable("categoryName", String.class),
					delexe.getVariable("parent", String.class), delexe.getVariable("name", String.class),
					delexe.getVariable("projectOnboardId", String.class));
		}

	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * GithubActivitiAgent test = new GithubActivitiAgent(); //
	 * test.createProject(null, "e9dac821-001b-11e7-aae1-d8cb8a429f06", "ACI",
	 * "CICDTest", "CICD-Test2", "NSO-DEMO-Test-12", null);
	 * 
	 * // test.createCategoryFile("pro1", "tech1", "AS Community"); // //
	 * test.onboardUsers("24334e12-d66b-11e6-95ed-507b9dc361f2", "NSO", //
	 * "NSO-DEMO-Validation-12", null);
	 * 
	 * }
	 */

	private void onboardUsers(String custId, String projectObjId, DelegateExecution delexe) throws Exception {
		String orgName = null;
		GitHubInfo gitHubInfo = null;
		GitAgentDAO gitDao = new GitAgentDAO();
		RallyAgentDAO dao = new RallyAgentDAO();

		try {
			orgName = gitDao.getGitHubOrgName(custId);
			gitHubInfo = gitDao.getGitHubRepoUrl(projectObjId);

			if (delexe.getVariable("addOnUser", String.class).equalsIgnoreCase("false")) {
				List<UserInfo> usersList = dao.getUsersForGitHubProject(custId, projectObjId);
				System.out.println("Org Name " + orgName);
				for (UserInfo user : usersList) {
					addCollaborator(orgName, gitHubInfo.getRepoName(), user.getUserName());
				}
			} else {
				System.out.println("OrgName" + orgName + "Repo name " + gitHubInfo.getRepoName() + " userName "
						+ delexe.getVariable("userName", String.class));
				addCollaborator(orgName, gitHubInfo.getRepoName(), delexe.getVariable("userName", String.class));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Boolean exist(String response, String name) {
		System.out.println("Inside exists");
		Boolean flag = false;
		name = name.replaceAll(" ", "-");

		JSONArray jsonarray = new JSONArray(response);

		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = new JSONObject();
			jsonobject = (JSONObject) jsonarray.get(i);

			if (jsonobject.get("login").equals(name)) {
				flag = true;

				break;
			}

		}

		return flag;

	}

	public Boolean categoryExist(String response, String project) {
		System.out.println(response);
		project = project.trim();
		Boolean flag = false;
		project = project.replaceAll(" ", "-");
		JSONArray jsonArray = new JSONArray(response);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject = (JSONObject) jsonArray.get(i);
			if (jsonObject.get("name").equals(project)) {
				flag = true;
				break;
			}
		}

		return flag;
	}

	public String getOrgAPI() throws Exception {
		String uri = CommonConstants.GITHUB_USER_ORG_URI;
		Client client = Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse response = webresource.accept(CommonConstants.CONTENT_TYPE)
				.header("Authorization", CommonConstants.AUTHORIZATION_VALUE).get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : Http Code : " + response.getStatus());
		}
		return response.getEntity(String.class);

	}

	public String getRepoAPI(String orgName) throws Exception {
		System.out.println(orgName);
		orgName = orgName.replace(" ", "-");
		orgName = orgName.trim();
		System.out.println("Inside getRepoAPI");
		String uri = CommonConstants.GITHUB_ORG_URI + orgName + "/repos";
		Client client = Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse response = webresource.accept(CommonConstants.CONTENT_TYPE)
				.header("Authorization", CommonConstants.AUTHORIZATION_VALUE).get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("failed : http Code : " + response.getStatus());
		}
		return response.getEntity(String.class);
	}

	public Boolean existRepo(String response, String name) {
		
		name = name.trim();
		System.out.println("name of the repo is" + name);
		name = name.replaceAll(" ", "-");
		Boolean flag = false;
		JSONArray jsonarray = new JSONArray(response);
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = new JSONObject();
			jsonobject = (JSONObject) jsonarray.get(i);
			System.out.println(jsonobject.get("name"));
			if (jsonobject.get("name").equals(name)) {
				flag = true;
				break;
			}

		}
		return flag;

	}

	public void createRepoAPI(String id, String repo) throws Exception {
		id = id.trim();
		repo = repo.trim();

		// id refers to organization under which repository will be created
		String uri = CommonConstants.GITHUB_ORG_URI + id + "/repos";
		String body = "{\"name\":\"" + repo + "\"}";
		Client client = Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse response = webresource.accept(CommonConstants.CONTENT_TYPE)
				.header("authorization", CommonConstants.AUTHORIZATION_VALUE).post(ClientResponse.class, body);
		if (response.getStatus() != 201) {
			throw new RuntimeException("failed : http Code :" + response.getStatus());
		}

	}

	public String createCategoryFile(String orgName, String tech, String category, String pro) throws Exception {
		orgName = orgName.trim();
		tech = tech.trim();
		orgName = orgName.replaceAll(" ", "-");
		tech = tech.replaceAll(" ", "-");
		category = category.replaceAll(" ", "-");
		

		System.out.println("Inside create category (repo) function");
		String uri = "";

		if (pro.equals("")) { // when creating master branch...
			uri = CommonConstants.GITHUB_REPO_URI + orgName + "/" + tech + "/contents/readme.txt";

		} else { // when creating category inside repositories..
			pro = pro.trim();
			pro = pro.replaceAll(" ", "-");

			uri = CommonConstants.GITHUB_REPO_URI + orgName + "/" + tech + "/contents/" + category + "/" + pro
					+ "/readme.txt";
		}

		String body = "{\"message\":\"my commit message\",\"content\" : \"aGkgdGhpcyBpcyBtZS4uLg==\"}";
		System.out.println(uri);

		Client client = Client.create();
		WebResource webresource = client.resource(new URI(uri));
		ClientResponse response = webresource.accept(CommonConstants.CONTENT_TYPE)
				.header("Authorization", CommonConstants.AUTHORIZATION_VALUE).put(ClientResponse.class, body);
		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : Http Code :" + response.getStatus());
		}

		System.out.println("success");
		return response.getEntity(String.class);
	}

	public String createBranchAPI(String id, String tech) throws Exception {
		id = id.trim();
		tech = tech.trim();
		String output = "";
		GithubActivitiAgent gitAgent = new GithubActivitiAgent();
		System.out.println("inside createBranchAPI");
		System.out.println("Id " + id + "technology  " + tech);
		String uri = CommonConstants.GITHUB_REPO_URI + id + "/" + tech + "/git/refs/heads";
		System.out.println("URI in create branch " + uri);
		Client client = Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse response = webresource.accept(CommonConstants.CONTENT_TYPE)
				.header("Authorization", CommonConstants.AUTHORIZATION_VALUE).get(ClientResponse.class);

		output = response.getEntity(String.class);
		if (response.getStatus() != 200 && response.getStatus() != 409) {
			throw new RuntimeException("Failed : Http Code :" + response.getStatus());
		}

		if (response.getStatus() == 409) {
			// 409 means there is no branch there in repo. SO first create a
			// master branch.
			System.out.println("inside 409 status code");

			String pro = "";
			gitAgent.createCategoryFile(id, tech, null, pro);
			// gitAgent.createBranchAPI(user, tech);

			return output;

		}

		// post request for creating the branch for the specific repository

		// pay attention to this......

		JSONArray jsonArray = new JSONArray(output);
		JSONObject jsonObject = new JSONObject();
		jsonObject = (JSONObject) jsonArray.get(0);

		jsonObject = (JSONObject) jsonObject.get("object");

		String sha = (String) jsonObject.get("sha");
		System.out.println(sha);
		String body = "{\"ref\" : \"refs/heads/feature_ssc\" , \"sha\" : \"" + sha + "\"}";
		uri = CommonConstants.GITHUB_REPO_URI + id + "/" + tech + "/git/refs";
		webresource = client.resource(uri);
		response = webresource.accept(CommonConstants.CONTENT_TYPE)
				.header("authorization", CommonConstants.AUTHORIZATION_VALUE).post(ClientResponse.class, body);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : Http Code :" + response.getStatus());
		}
		return response.getEntity(String.class);

	}

	public String getCategory(String orgName, String repo, String file, String category) throws Exception {
		orgName = orgName.replaceAll(" ", "-");
		repo = repo.replaceAll(" ", "-");
		file = file.replaceAll(" ", "-");
		category = category.replaceAll(" ", "-");
		orgName = orgName.trim();
		repo = repo.trim();

		String uri = "";

		if (file.equals("")) {
			uri = CommonConstants.GITHUB_REPO_URI + orgName + "/" + repo + "/contents/" + category;
		}

		else {
			file = file.trim();
			file = file.replaceAll(" ", "-");
			uri = CommonConstants.GITHUB_REPO_URI + orgName + "/" + repo + "/contents/" + category + "/" + file;

		}

		System.out.println(uri);
		Client client = Client.create();
		WebResource webResource = client.resource(uri);
		ClientResponse response = webResource.accept(CommonConstants.CONTENT_TYPE)
				.header("authorization", CommonConstants.AUTHORIZATION_VALUE).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed Http Code : " + response.getStatus());

		}

		return response.getEntity(String.class);
	}

	public void createSubCategory(String orgName, String tech, String category, String parentProject, String project) {
		System.out.println("Inside create sub category function ");

		orgName = orgName.trim();
		tech = tech.trim();
		parentProject = parentProject.trim();
		project = project.trim();
		
		orgName = orgName.replaceAll(" ", "-");
		tech = tech.replaceAll(" ", "-");
		category = category.replaceAll(" ", "-");

		parentProject = parentProject.replaceAll(" ", "-");
		project = project.replaceAll(" ", "-");
		String uri = CommonConstants.GITHUB_REPO_URI + orgName + "/" + tech + "/contents/" + category + "/"
				+ parentProject + "/" + project + "/readme.txt";
		String body = "{\"message\" : \"my commit message\" , \"content\" : \"aGkgdGhpcyBpcyBtZS4uLg==\"}";

		Client client = Client.create();
		WebResource webResource = client.resource(uri);

		ClientResponse response = webResource.accept(CommonConstants.CONTENT_TYPE)
				.header("Authorization", CommonConstants.AUTHORIZATION_VALUE).put(ClientResponse.class, body);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : Http Code" + response.getStatus());
		}

		String output = response.getEntity(String.class);
		System.out.println(output);
		System.out.println("success");

	}

	public void addCollaborator(String orgName, String repoName, String userName) {
		orgName = orgName.replaceAll(" ", "-");
		repoName = repoName.replaceAll(" ", "-");
		
		String uri = CommonConstants.GITHUB_REPO_URI + orgName + "/" + repoName + "/collaborators/" + userName;
		System.out.println("Adding collaborator for this URI " + uri);
		Client client = Client.create();
		WebResource webResource = client.resource(uri);

		ClientResponse response = webResource.accept(CommonConstants.CONTENT_TYPE)
				.header("Authorization", CommonConstants.AUTHORIZATION_VALUE).put(ClientResponse.class);

		if (response.getStatus() != 204) {
			throw new RuntimeException("Failed : Http Code" + response.getStatus());
		}

	}

	public void createProject(DelegateExecution delexe, String custId, String tech, String category,
			String parentProject, String project, String projectOnboardId) {
		GitAgentDAO gitAgentDAO = new GitAgentDAO();
		String gitHubUrl = "";
		GitHubInfo gitHubInfo = null;
		GitAgentDAO dao = new GitAgentDAO();
		String output = null;
		String orgName = "";
		Boolean flag = false;
		Boolean internal = false;
		String techName = "";
		// after db
		String orgNameFromDb = null;
		String customerName = gitAgentDAO.getCustomerName(custId, null);

		try {
			GithubActivitiAgent gitAgent = new GithubActivitiAgent();
			// Queried the database to know whether internal or external
			// customer
			internal = dao.internalCustomer(custId);
			if (!internal) {
				orgNameFromDb = dao.getGitHubOrgName(custId);
				output = gitAgent.getOrgAPI();
				flag = gitAgent.exist(output, orgNameFromDb);
				System.out.println("flag is " + flag);

				if (flag == false) {

					// org doesn't exist. So same as customer name.

					// Then set the orgaName = userName (name of the customer)
					System.out.println("The oragnization doesn't exist");

					// throw exception as organization is to be created manually
					// in github3.

					throw new Exception("Organization doesn't exist. Please cretae one");
					// orgName = custId;
				} else {
					// setting the orgName as it already exists. No need to
					// create it.

					orgName = orgNameFromDb;
					System.out.println("Organization already exists");

				}

			} else {
				
				if(customerName.equalsIgnoreCase("AS Community Test")){
					orgName = "AS-Community-Test";
				}
				else if(customerName.equalsIgnoreCase("AS Internal Test")){
					orgName = "AS-Internal-Test";
				}else if(customerName.equalsIgnoreCase("AS Community")){
					orgName = "AS-Community";
				}else if(customerName.equalsIgnoreCase("AS Internal")){
					orgName = "AS-Internal";
				}

				// create As-Community only once; for Internal users.

				//orgName = "As-Community";
				//orgName = "CICDPortalTesting";
			}

			// Checking whether the technology exists or not...
			output = gitAgent.getRepoAPI(orgName);
			

			gitHubInfo = dao.getGitHubRepoName(custId, tech);

			flag = gitAgent.existRepo(output, gitHubInfo.getRepoName());
			System.out.println(flag);
			if (flag == false) {
				// here i am cretaing the new repo in github for a new
				// technology
				// gitAgent.createRepoAPI(custId, tech);
				gitAgent.createRepoAPI(orgName, gitHubInfo.getRepoName());
				techName = gitHubInfo.getRepoName();

				// create a new branch for the repo cretaed.
				// remeber on the master branch there must be some file.
				// otherwise it won't execute

				String branchOutput = gitAgent.createBranchAPI(orgName, techName);

				String y = branchOutput.substring(30, 35);
				System.out.println(y);
				if (y.equals("empty")) {
					gitAgent.createBranchAPI(orgName, techName);
				}

				// not required to update because databse will already has
				// reponame. But gihub3 won't and will insert using api.

				gitHubUrl = dao.updateGithubRepo(orgName, techName);
				if (gitHubUrl != null) {
					delexe.setVariable("GitHubUrl", gitHubUrl);

				}
				System.out.println(
						"After setting GitHubUrl in the workflow" + delexe.getVariable("GitHubUrl", String.class));

			} else {

				// technology(repo) already exists under organization in github
				techName = gitHubInfo.getRepoName();
				// String catrgory = gitAgent.getCategory(userName, tech);
				if (gitHubInfo.getUrl() != null) {
					delexe.setVariable("GitHubUrl", gitHubInfo.getUrl());

				}
				System.out.println(
						"After setting GitHubUrl in the workflow " + delexe.getVariable("GitHubUrl", String.class));
				System.out.println("Technology already exists");

			}

			// getting categories inside the repo... then create the new branch
			// and then parent project or category.
			String file = "";
			// String category = gitAgent.getCategory(custId, tech, file);

			String allCategories = gitAgent.getCategory(orgName, techName, file, category);

			// System.out.println("category is\n" + category);

			// Category

			// -----------------------------
			if (!(parentProject.isEmpty() || parentProject.equals("") || parentProject.equalsIgnoreCase("--Select-a-Parent-Project--") ) ) {
				Boolean exflag = gitAgent.categoryExist(allCategories, parentProject);

				if (exflag == false) {

					allCategories = gitAgent.createCategoryFile(orgName, techName, category, parentProject);

					System.out.println("last category \n" + category);

					// Updated the Category table
					// dao.updateCategory(projectOnboardId, parentProject);

				} else {
					System.out.println("The parent project already exists");
				}

				// After this adding at home. Please check it....

				// doubt whethet we need to check project inside parent project
				// exists or not..
				if (!(project.isEmpty() || project.equals(""))) {

					file = parentProject;
					allCategories = gitAgent.getCategory(orgName, techName, file, category);

					System.out.println(category);

					// adding at home
					exflag = gitAgent.categoryExist(allCategories, project);

					if (exflag == false) {
						gitAgent.createSubCategory(orgName, techName, category, parentProject, project);
					} else {
						System.out.println("The project already exists");
					}
				}
			} else if (parentProject.equals("") && !project.isEmpty()) {
				Boolean exflag = gitAgent.categoryExist(allCategories, project);
				if (exflag == false) {
					allCategories = gitAgent.createCategoryFile(orgName, techName, category, project);
					System.out.println("last category \n" + category);
					// dao.updateCategory(projectOnboardId, project);
				} else {
					System.out.println("The Parent project already exists");
				}
			} else {
				System.out.println("There is no parent project or project");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
