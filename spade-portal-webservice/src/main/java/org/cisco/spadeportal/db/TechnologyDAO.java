package org.cisco.spadeportal.db;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.bean.activiti.RallyInfo;
import org.cisco.spadeportal.bean.entity.CustomerEntity;
import org.cisco.spadeportal.bean.entity.TechnologyEntity;
import org.cisco.spadeportal.bean.request.CategoryEntity;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.response.CreateResponse;

/**
 * @author sarbr
 *
 */
public class TechnologyDAO {

	private static final Logger LOGGER = Logger.getLogger(TechnologyDAO.class.getName());
	private static final String SELECT_RALLY_WORKSPACE_OBJ_ID = "SELECT ou.RallyWorkspaceObjId FROM organisationunit as ou, customerinfo as cu WHERE cu.CustomerId = ? AND ou.OrgId = cu.OrgId ";
	private static final String SELECT_USER_OBJ_ID = "SELECT RallyUserObjId FROM useronboard WHERE CustomerId = ? AND EmailAddress = ? ";
	private static final String CREATE_RALLY_PROJECT = "INSERT INTO rallyproject VALUES ( ? , ?, ?, ? )";
	private static final String SELECT_RALLY_TECH_PROJECT_OBJ_ID = " SELECT RallyTechObjId FROM technology WHERE TechnologyId = ? AND RallyWorkspaceObjId = ? ";
	private static final String ADD_CATEGORY = "insert into category VALUES (uuid() ,? ,? ,? ,?,?)";
	private static final String GET_CUSTOMER = "select Internal from customerinfo where CustomerId = ?";
	private static final String SELECT_RALLY_PROJECT_OBJ_ID = " SELECT RallyProjectObjId FROM rally WHERE Name = ? AND RallyWorkspaceObjId = ? ";
	private static final String SELECT_RALLY_CUSTOMER_PROJECT_OBJ_ID = " SELECT RallyCustObjId FROM customerinfo WHERE CustomerId = ? ";






	//	CategoryDAO categoryDAO = new CategoryDAO();

	// CategoryDAO categoryDAO = new CategoryDAO();


	/**
	 * @param customerId
	 * @return list
	 */
	public List<TechnologyEntity> getTechnologiesFromDB(String customerId) {
		List<TechnologyEntity> technologyList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT TechnologyName FROM technology where CustomerId = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			rs = statement.executeQuery();
			technologyList = new ArrayList<TechnologyEntity>();
			while (rs.next()) {
				TechnologyEntity technology = new TechnologyEntity();
				technology.setTechnologyName(rs.getString("TechnologyName"));
				LOGGER.log(Level.INFO, " Queried the table Technology to get technology list--> " + technology);
				technologyList.add(technology);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return technologyList;
	}

	/**
	 * @param customerId
	 * @return TechnologyEntity
	 */
	public TechnologyEntity getTechnologyFromDB(String customerId, String technologyId, Connection existingConnection) {
		TechnologyEntity technology = new TechnologyEntity();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		if (existingConnection != null) {
			connection = existingConnection;
		} else {
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		try {
			//String selectSQL = "SELECT TechnologyName FROM technology where CustomerId = ? and TechnologyId = ? ";
			String selectSQL = "SELECT TechnologyName FROM technology where TechnologyId = ? ";

			statement = connection.prepareStatement(selectSQL);
			/*statement.setString(1, customerId);
			statement.setString(2, technologyId);*/
			statement.setString(1, technologyId);
			rs = statement.executeQuery();

			while (rs.next()) {
				technology.setTechnologyName(rs.getString("TechnologyName"));
			}
			// LOGGER.log(Level.INFO, " Queried the table to get Technology
			// Detail--> " + technology);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			if (existingConnection == null) {
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}
		}
		return technology;
	}

	/**
	 * @param customerId
	 * @param technologyName
	 * @return String
	 */
	public TechnologyEntity getTechnologyId(String customerId, String technologyName, Connection existingConnection) {
		TechnologyEntity technologyEntity = new TechnologyEntity();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		if (existingConnection != null) {
			connection = existingConnection;
		} else {
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		try {
			String selectSQL = "SELECT TechnologyId FROM technology where CustomerId = ? and TechnologyName = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			statement.setString(2, technologyName);
			rs = statement.executeQuery();
			while (rs.next()) {
				technologyEntity.setTechnologyId(rs.getString("TechnologyId"));
			}
			LOGGER.log(Level.INFO, " Queried the table to get Technology Id--> " + technologyEntity.getTechnologyId());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if (existingConnection == null) {
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}
		}
		return technologyEntity;
	}

	/*
	 * public int addTechnology(String customerId, TechnologyEntity entity) {
	 * 
	 * Connection connection = null; PreparedStatement statement = null;
	 * ResultSet rs = null; int rows = 0; String query =
	 * "INSERT into technology SET TechnologyId = uuid(), TechnologyName = ?, RallyProjectObjId = ? , CustomerId = ? "
	 * ; try { connection = DBConnectionFactory.getDatabaseConnection();
	 * statement = connection.prepareStatement(query); statement.setString(1,
	 * entity.getTechnologyName()); statement.setLong(2, 77849357652l);
	 * statement.setString(3, customerId);
	 * 
	 * rows = statement.executeUpdate();
	 * 
	 * } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Exception Occurred",
	 * e); } finally { DBConnectionFactory.releaseConnection(statement, rs,
	 * connection); }
	 * 
	 * return rows; }
	 */

	public int createGitRepo(String orgName, String repo) {
		orgName = orgName.trim();
		orgName = orgName.replaceAll(" ", "-");
		repo = repo.trim();
		Response response = null;
		// String uri = "https://github3.cisco.com/api/v3/orgs/" + orgName +
		// "/repos";
		String body = "{\"name\":\"" + repo + "\"}";
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget target = client.target("https://github3.cisco.com/api/v3/orgs");
		WebTarget repoTarget = target.path(orgName + "/repos");
		// String uri =
		// "https://github3.cisco.com/api/v3/orgs/CICDPortalTesting/repos";
		// WebTarget repoTarget = client.target(uri);
		System.out.println(repoTarget.getUri());
		Invocation.Builder invocationBuilder = repoTarget.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header("authorization", "Basic YXMtY2ktdXNlci5nZW46IUNJQ0RwYXNz");
		//invocationBuilder.header("authorization", "Basic eXVnc2luZ2g6T012aXNobnVqaWppci85");

		response = invocationBuilder.post(Entity.entity(body, MediaType.APPLICATION_JSON));
		// response = invocationBuilder.get();

		System.out.println(response.readEntity(String.class));

		try {
			System.out.println(response.getStatus());
			if (response.getStatus() != 201 && response.getStatus() != 200) {
				throw new RuntimeException("failed : http Code :" + response.getStatus());
			}
		} catch (Exception e) {

			LOGGER.log(Level.SEVERE, "Exception occured", e);
		}

		return response.getStatus();

	}
	
	public String getGithubRepoName(String technologyName){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String query = "select * from technology t,githubrepo g WHERE t.TechnologyName = ? and t.GitHubRepoId=g.GitHubRepoId ";
		String name = null;
		
		try{
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, technologyName);
			
			rs = statement.executeQuery();
			while(rs.next()){
				name = rs.getString("GitHubRepoName");
			}
			
		}catch(SQLException e){
			LOGGER.log(Level.SEVERE, "Exception occurred", e);
		}finally{
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		
		
		return name;
		
	}

	public int createGithubCategory(String customerId, CategoryEntity category) {
		CustomerDAO dao = new CustomerDAO();
		
		String orgName = dao.getOrgName(customerId);
		String name = getGithubRepoName(category.getTechnologyName());
		
		// String category = null;
		
		
		return (createCategoryFile(orgName, name, category.getCategoryName()));
	}

	public int createCategoryFile(String orgName, String tech, String pro) {
		
		orgName = orgName.trim();
		tech = tech.trim();
		pro = pro.trim();
		orgName = orgName.replaceAll(" ", "-");
		tech = tech.replaceAll(" ", "-");
		pro = pro.replaceAll(" ", "-");
		Response response = null;

		System.out.println("Inside create category (repo) function");
		String uri = "";

		if (pro.equals("")) { // when creating master branch...
			uri = "https://github3.cisco.com/api/v3/repos/" + orgName + "/" + tech + "/contents/readme.txt";

		} else { // when creating category inside repositories..
			pro = pro.trim();
			pro = pro.replaceAll(" ", "-");

			uri = "https://github3.cisco.com/api/v3/repos/" + orgName + "/" + tech + "/contents/" + pro + "/readme.txt";
		}

		String body = "{\"message\":\"my commit message\",\"content\" : \"aGkgdGhpcyBpcyBtZS4uLg==\"}";
		System.out.println(uri);

		// Client client = Client.create();
		// WebResource webresource = client.resource(new URI(uri));
		// ClientResponse response =
		// webresource.accept(CommonConstants.CONTENT_TYPE)
		// .header("Authorization",
		// CommonConstants.AUTHORIZATION_VALUE).put(ClientResponse.class, body);
		// if (response.getStatus() != 201) {
		// throw new RuntimeException("Failed : Http Code :" +
		// response.getStatus());
		// }

		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget target = client.target(uri);
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header("authorization", "Basic YXMtY2ktdXNlci5nZW46IUNJQ0RwYXNz");
		response = invocationBuilder.put(Entity.json(body));

		try {
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : Http Code :" + response.getStatus());
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occurred while creating the master branch", e);
		}

		// System.out.println("success");
		return response.getStatus();

	}

	public String createBranchAPI(String orgName, String repo) {
		TechnologyDAO technologyDAO = new TechnologyDAO();
		orgName = orgName.trim();
		repo = repo.trim();
		orgName = orgName.replaceAll(" ", "-");
		repo = repo.replaceAll(" ", "-");
		String output = "";
		Response response = null;
		System.out.println("inside createBranchAPI");
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget target = client.target("https://github3.cisco.com/api/v3/repos");

		WebTarget org = target.path(orgName);
		WebTarget repos = org.path(repo);
		WebTarget branch = repos.path("git/refs/heads");

		System.out.println(branch.getUri());

		Invocation.Builder invocationBuilder = branch.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header("authorization", "Basic YXMtY2ktdXNlci5nZW46IUNJQ0RwYXNz");

		response = invocationBuilder.get();
		output = response.readEntity(String.class);

		try {
			if (response.getStatus() != 200 && response.getStatus() != 409) {
				throw new RuntimeException("Failed : Http Code :" + response.getStatus());
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occured during creating the branch", e);
		}

		if (response.getStatus() == 409) {
			// 409 means there is no branch there in repo. SO first create a
			// master branch.
			System.out.println("inside 409 status code");

			String pro = "";
			technologyDAO.createCategoryFile(orgName, repo, pro);
			// gitAgent.createBranchAPI(user, tech);
			// output = response.getEntity(String.class);
			// output = (String) response.getEntity();
			// output = response.readEntity(String.class);
			return output;

		}
		// System.out.println(response.getStatus());

		// System.out.println(output);
		JSONArray jsonArray = new JSONArray(output);
		JSONObject jsonObject = new JSONObject();
		jsonObject = (JSONObject) jsonArray.get(0);

		jsonObject = (JSONObject) jsonObject.get("object");

		String sha = (String) jsonObject.get("sha");
		System.out.println(sha);
		String body = "{\"ref\" : \"refs/heads/feature_ssc\" , \"sha\" : \"" + sha + "\"}";
		String uri = "https://github3.cisco.com/api/v3/repos/" + orgName + "/" + repo + "/git/refs";

		target = client.target(uri);
		invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header("authorization", "Basic YXMtY2ktdXNlci5nZW46IUNJQ0RwYXNz");

		response = invocationBuilder.post(Entity.json(body));

		try {
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : Http Code :" + response.getStatus());
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occurred while creating the brnach", e);
		}
		System.out.println(response.getStatus());
		return response.readEntity(String.class);
	}

	public String insertIntoGroup(String tech, String cutomerId) {
		tech = tech + "-Managers";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		// String query = "INSERT into technology SET TechnologyId = uuid(),
		// TechnologyName = ?, RallyProjectObjId = ? , CustomerId = ? ";
		String query = "INSERT into groups SET GroupId = uuid(), GroupName = ?, Description = ? , CustomerId = ? ";
		// String query = "INSERT INTO group SET GroupId = uuid(), GroupName =
		// ?, Description = ?, CustomerId = ?";
		System.out.println(query);
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(query);

			statement.setString(1, tech);
			statement.setString(2, "");
			statement.setString(3, cutomerId);

			System.out.println(statement);
			// rows = statement.executeUpdate();
			statement.executeUpdate();
			LOGGER.log(Level.INFO, "Inserted into group table.");

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occurred", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return tech;
	}

	public String getGroupId(String name, String customerId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String id = null;
		String query = "SELECT GroupId FROM groups WHERE GroupName = ? and CustomerId = ? ";
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, customerId);
			rs = statement.executeQuery();

			while (rs.next()) {
				id = rs.getString("GroupId");
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception thrown", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return id;
	}

	public int updatetechnology(String groupId, String repoId, String techName, String customerId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int rows = 0;
		String query = "UPDATE technology SET GroupId = ?, GitHubRepoId = ? WHERE TechnologyName = ? and CustomerId = ?";
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, null);
			statement.setString(2, repoId);
			statement.setString(3, techName);
			statement.setString(4, customerId);
			
			
			rows = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception thrown", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return rows;
	}

	public String getWorkSpaceObjId(String cutId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyWorkspaceObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_RALLY_WORKSPACE_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, cutId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyWorkspaceObjId = rs.getString("RallyWorkspaceObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyWorkspaceObjId;
	}

	public String getRallyUserObjId(String cutId, String emailId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyUserObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_USER_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, cutId);
			statement.setString(2, emailId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyUserObjId = rs.getString("RallyUserObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyUserObjId;
	}

	public void createRallyProject(String rallyProjectObjId, String name, String owner, String rallyWorkspaceObjId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(CREATE_RALLY_PROJECT);
			statement.setLong(1, Long.parseLong(rallyProjectObjId));
			statement.setString(2, name);
			statement.setString(3, owner);
			statement.setString(4, rallyWorkspaceObjId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
	}



	public void createTechnologyRallyProject(String custId, String rallyProjectName)
			throws URISyntaxException, IOException {
		GitHubDAO gitHubDAO = new GitHubDAO();
		CustomerDAO customerDAO = new CustomerDAO();
		ProjectDAO dao = new ProjectDAO();
		RallyInfo rally = dao.getRallyDetails(custId);
		// Create and configure a new instance of RallyRestApi
		RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
		restApi.setApplicationName("TestApp");
		try {
			// get rally workspace id for this customer
			String rallyWrksp = getWorkSpaceObjId(custId);

			// Create a Project
			JsonObject newProject = new JsonObject();
			newProject.addProperty("Name", rallyProjectName);
			newProject.addProperty("Description", "");
			newProject.addProperty("State", "Open");
			System.out.println("Workspace "+rallyWrksp);
			newProject.addProperty("Workspace", "/workspace/" + rallyWrksp);
			newProject.addProperty("Parent","/project/" + getCustomerProjectObjId(custId));
			System.out.println("creating project under "+newProject);
			CreateRequest createRequest = new CreateRequest("Project", newProject);
			CreateResponse createResponse = restApi.create(createRequest);
			JsonObject respObj = createResponse.getObject();
			System.out.println("Response object " + respObj);
			System.out.println(
					String.format("Created Rally Project with technology name %s", respObj.get("_ref").getAsString()));
			// here update the response to DB
			System.out.println("Rally Project ObjectID" + respObj.get("ObjectID").getAsString());
			insertIntoTechnologyTable(custId, respObj.get("ObjectID").getAsString(), rallyProjectName,rallyWrksp);
			gitHubDAO.updateGithubOrg(customerDAO.getCustomerName(custId, null).getName(),null);
			String githubOrgId = gitHubDAO.getGitOrgId(customerDAO.getCustomerName(custId, null).getName(), null);
			customerDAO.updateCustomerInfo(custId, githubOrgId, null);
			
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			restApi.close();
		}

	}

	public CustomerEntity getCustomerType(String customerId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		CustomerEntity entity = new CustomerEntity();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(GET_CUSTOMER);
			statement.setString(1, customerId);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setInternal(rs.getBoolean("Internal"));
				// customerId = rs.getString("CustomerId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return entity;
	}
	public String insertIntoTechnologyTable(String custId, String projectObjId, String technologyName,String rallyWorkspaceObjId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyProjectObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "INSERT INTO technology VALUES ( uuid() ,?, ?, ? ,? ,? ,?)";

			System.out.println("Technology Name " + technologyName);
			
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, technologyName);
			statement.setString(2, null);
			statement.setLong(3, Long.parseLong(projectObjId));
			statement.setString(4, custId);
			statement.setString(5, null);
			statement.setString(6, rallyWorkspaceObjId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyProjectObjId;
	}

	public String getProjectObjId(String workspaceId, String projectName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyProjectObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_RALLY_PROJECT_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, projectName);
			statement.setString(2, workspaceId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyProjectObjId = rs.getString("RallyProjectObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyProjectObjId;
	}

	/**
	 * @param custId
	 * @param categoryEntity
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void createCategoryRallyProject(String custId, CategoryEntity categoryEntity, String technologyName)
			throws URISyntaxException, IOException {
		categoryEntity.setTechnologyName(technologyName);
		ProjectDAO dao = new ProjectDAO();
		RallyInfo rally = dao.getRallyDetails(custId);
		// Create and configure a new instance of RallyRestApi
		RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
		restApi.setApplicationName("TestApp");
		try {
			// get rally workspace id for this customer
			String rallyWrksp = getWorkSpaceObjId(custId);

			// Create a Project
			JsonObject newProject = new JsonObject();
			newProject.addProperty("Name", categoryEntity.getCategoryName());
			newProject.addProperty("Description", "");
			newProject.addProperty("State", "Open");
			newProject.addProperty("Workspace", "/workspace/" + rallyWrksp);
			if (categoryEntity.getTechnologyName() != null && categoryEntity.getTechnologyName().trim().length() > 0) {
				newProject.addProperty("Parent",
						"/project/" + getTechnologyProjectObjId(rallyWrksp, categoryEntity.getTechnologyName(),custId));
				System.out.println("Technology Name "+categoryEntity.getTechnologyName());
			}
			System.out.println("Project"+newProject);
			CreateRequest createRequest = new CreateRequest("Project", newProject);
			CreateResponse createResponse = restApi.create(createRequest);
			JsonObject respObj = createResponse.getObject();
			System.out.println("Response object " + respObj);
			createCategory(custId, categoryEntity, respObj.get("ObjectID").getAsString(),rallyWrksp);
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			restApi.close();
		}

	}

	/**
	 * @param customerId
	 * @param technologyName
	 * @param technologyObjectId
	 */

	public void createCategory(String customerId,CategoryEntity entity,String categoryObjId,String rallyWrksp){

		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(ADD_CATEGORY);
			statement.setString(1, entity.getCategoryName());
			statement.setString(2,
					getTechnologyId(customerId, entity.getTechnologyName(), connection).getTechnologyId());
			statement.setLong(3, Long.parseLong(categoryObjId));
			statement.setString(4, "");
			statement.setLong(5, Long.parseLong(rallyWrksp));
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
	}

	/**
	 * @param customerId
	 * @param technologyName
	 * @return String
	 */
	public TechnologyEntity getCategoryId(String customerId, String categoryName, Connection existingConnection) {
		TechnologyEntity technologyEntity = new TechnologyEntity();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		if (existingConnection != null) {
			connection = existingConnection;
		} else {
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		try {
			String selectSQL = "SELECT CategoryId FROM category where CustomerId = ? and CategoryName = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			statement.setString(2, categoryName);
			rs = statement.executeQuery();
			while (rs.next()) {
				technologyEntity.setTechnologyId(rs.getString("CategoryId"));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if (existingConnection == null) {
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}
		}
		return technologyEntity;
	}


	public String getTechnologyProjectObjId(String workspaceId, String technologyName,String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyProjectObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_RALLY_TECH_PROJECT_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, getTechnologyId(custId, technologyName, connection).getTechnologyId());
			statement.setString(2, workspaceId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyProjectObjId = rs.getString("RallyTechObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyProjectObjId;
	}

	/**
	 * @param projectName
	 * @return
	 */
	public String getCustomerProjectObjId(String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyProjectObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_RALLY_CUSTOMER_PROJECT_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyProjectObjId = rs.getString("RallyCustObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyProjectObjId;
	}

	
	public String getWorkSpaceObjId() {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyWorkspaceObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT RallyWorkspaceObjId FROM organisationunit";
			statement = connection.prepareStatement(selectSQL);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyWorkspaceObjId = rs.getString("RallyWorkspaceObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyWorkspaceObjId;
	}
}
