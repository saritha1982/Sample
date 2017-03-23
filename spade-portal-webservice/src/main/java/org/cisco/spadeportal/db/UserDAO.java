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
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.bean.activiti.RallyInfo;
import org.cisco.spadeportal.bean.entity.ProjectEntity;
import org.cisco.spadeportal.bean.entity.ProjectRoleEntity;
import org.cisco.spadeportal.bean.entity.UserEntity;
import org.cisco.spadeportal.bean.request.Project;
import org.cisco.spadeportal.bean.request.User;
import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.UpdateResponse;

/**
 * @author sarbr
 *
 */
public class UserDAO {

	private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
	private static final Object LOCK = new Object();
	ProjectRoleDAO projectRoleDAO = new ProjectRoleDAO();
	ProjectDAO projectDAO = new ProjectDAO();

	/**
	 * @param customerId
	 * @return list
	 */
	public List<UserEntity> getUserListFromDB(String customerId) {
		List<UserEntity> userList = null;
		List<ProjectEntity> projectList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {
			//String selectSQL = "SELECT distinct UserName,AccessLevel,Disabled FROM useronboard where CustomerId = ? ";
			//String selectSQL = "SELECT distinct UserName,AccessLevel,Disabled FROM useronboard ";
			String selectSQL = "SELECT distinct UserName,AccessLevel FROM useronboard ";
			statement = connection.prepareStatement(selectSQL);
			//statement.setString(1, customerId);
			rs = statement.executeQuery();
			userList = new ArrayList<UserEntity>();
			while (rs.next()) {
				UserEntity user = new UserEntity();
				user.setUserName(rs.getString("UserName"));
				user.setAccessLevel(rs.getString("AccessLevel"));
				//user.setDisabled(rs.getBoolean("Disabled"));
				LOGGER.log(Level.INFO, " Queried the table User to get userName list--> " + user.getUserName());
				userList.add(user);
			}

			for (UserEntity user : userList) {
				//selectSQL = "SELECT OnBoardId,EmailAddress from useronboard where UserName = ? and AccessLevel = ? and Disabled = ?";
				selectSQL = "SELECT OnBoardId,EmailAddress from useronboard where UserName = ? and AccessLevel = ? ";
				statement = connection.prepareStatement(selectSQL);
				statement.setString(1, user.getUserName());
				statement.setString(2, user.getAccessLevel());
				//statement.setBoolean(3, user.isDisabled());
				rs = statement.executeQuery();
				projectList = new ArrayList<ProjectEntity>();
				while (rs.next()) {
					user.setEmailAddress(rs.getString("EmailAddress"));
					String onBoardId = rs.getString("OnBoardId");
					selectSQL = "select ProjectName from projectonboard where OnBoardId = ?";
					statement = connection.prepareStatement(selectSQL);
					statement.setString(1, onBoardId);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						ProjectEntity entity = new ProjectEntity();
						entity.setProjectName(resultSet.getString("ProjectName"));
						projectList.add(entity);
					}
				}
				user.setProjectList(projectList);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return userList;
	}

	public void onboardUser(List<User> users, String onboardId, String customerId) {
		for (User u : users) {
			onboardUser(u, onboardId, customerId);
		}
	}

	public int onboardUser(User user, String onboardId, String customerId) {
		int returnRow = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {
			String insertQuery = " insert into useronboard (UserName , EmailAddress ,Workspace , OnBoardId , Disabled "
					+ ",AccessLevel ,RallyUserObjId, CustomerId ,ProjectRoleId )"
					+ " values (?, ?, ?, ? , ?, ? ,? ,? ,? )";
			preparedStmt = connection.prepareStatement(insertQuery);
			preparedStmt.setString(1, user.getUserName());
			preparedStmt.setString(2, user.getEmailAddress());
			preparedStmt.setString(3, user.getWorkspace());
			preparedStmt.setString(4, onboardId);
			preparedStmt.setBoolean(5, user.isDisabled());
			preparedStmt.setString(6, user.getAccessLevel());
			preparedStmt.setString(7, null);
			preparedStmt.setString(8, customerId);
			preparedStmt.setString(9, projectRoleDAO.getProjectRoleId(user.getProjectRole()));
			returnRow = preparedStmt.executeUpdate();
			LOGGER.log(Level.INFO, "Table data got inserted into userboard table");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(preparedStmt, rs, connection);
		}
		return returnRow;
	}

	public int addUser(User user, Project project, String customerId) {
		int returnRow = 0;
		PreparedStatement preparedStmt = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {
			String insertQuery = " insert into useronboard (UserName , EmailAddress ,Workspace , OnBoardId , Disabled "
					+ ",AccessLevel ,RallyUserObjId, CustomerId ,ProjectRoleId )"
					+ " values (?, ?, ?, ? , ?, ? ,? ,? ,? )";
			preparedStmt = connection.prepareStatement(insertQuery);
			preparedStmt.setString(1, user.getUserName());
			preparedStmt.setString(2, user.getEmailAddress());
			preparedStmt.setString(3, user.getWorkspace());
			preparedStmt.setString(4, project.getOnBoardId());
			// preparedStmt.setString(4, user.getOnBoardId());
			preparedStmt.setBoolean(5, false);
			preparedStmt.setString(6, user.getAccessLevel());
			preparedStmt.setString(7, null);
			preparedStmt.setString(8, customerId);
			preparedStmt.setString(9, projectRoleDAO.getProjectRoleId(project.getProjectRole()));
			returnRow = preparedStmt.executeUpdate();
			LOGGER.log(Level.INFO, "Table data got inserted into userboard table");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(preparedStmt, rs, connection);
		}
		return returnRow;
	}

	/**
	 * @param userId
	 * @return UserEntity
	 */
	public UserEntity getCustomerId(String userId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		UserEntity user = new UserEntity();
		try {

			String selectSQL = "SELECT CustomerId FROM user where UserId = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, userId);
			rs = statement.executeQuery();
			while (rs.next()) {
				user.setCustomerId(rs.getString("CustomerId"));
				LOGGER.log(Level.INFO, " Queried the table User to get CustomerId--> " + user.getCustomerId());
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return user;
	}

	/**
	 * @param roleName
	 * @return ProjectRoleEntity
	 */
	public String getProjectRoleId(String roleName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		ProjectRoleEntity projectRole = new ProjectRoleEntity();
		try {

			String selectSQL = "SELECT ProjectRoleId FROM projectrole where ProjectRoleType = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, roleName);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectRole.setRoleId(rs.getString("ProjectRoleId"));
				LOGGER.log(Level.INFO, " Queried the table User to get ProjectRoleId--> " + projectRole.getRoleId());
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectRole.getRoleId();
	}

	

	/**
	 * @param custId
	 * @param onboardId
	 * @param userUpdateRequest
	 */
	public void editUserProjectRole(String custId, String onboardId, User userUpdateRequest) {
		System.out.println("role in dao " + userUpdateRequest.getAccessLevel());
		System.out.println("mail in dao ui " + userUpdateRequest.getEmailAddress());
		RallyInfo rally = projectDAO.getRallyDetails(custId);
		ProjectEntity projectEntity = null;
		User user = null;
		try {
			RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
				projectEntity = projectDAO.getProjectDetailFromDB(custId, onboardId);
				restApi.setApplicationName("TestApp");
				JsonObject newProject = new JsonObject();
				user = getUserDetailFromDB(custId, onboardId, userUpdateRequest);
				newProject.addProperty("User", "user/" + user.getUserObjectId());
				System.out.println("User object Id " + user.getUserObjectId());
				newProject.addProperty("Role", getRole(userUpdateRequest.getAccessLevel()));
				System.out.println("Role " + userUpdateRequest.getAccessLevel());
				newProject.addProperty("Disabled", false);
				newProject.addProperty("Workspace", "/workspace/" + getWorkSpaceObjId(custId));
				newProject.addProperty("Project", "/project/" + projectEntity.getRallyProjectObjId());
				CreateRequest updateRequest = new CreateRequest("ProjectPermission", newProject);
				CreateResponse updateResponse = restApi.create(updateRequest);
				if (updateResponse.wasSuccessful()) {
					System.out.println("Response object after updating user project role");

				} else {
					String[] errorList;
					errorList = updateResponse.getErrors();
					for (int i = 0; i < errorList.length; i++) {
						System.out.println("Error list  " + errorList[i]);
					}
				}
				// Release all resources
				restApi.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			role = "No Access";
			
		}
		if ("Viewer".equalsIgnoreCase(access)) {
			role = "Viewer";
		}
		return role;
	}

	/**
	 * @param customerId
	 * @param onboardId
	 * @return
	 */
	public User getUserDetailFromDB(String customerId, String onboardId, User userUpdateRequest) {
		User userOnBoardData = new User();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT UserName,EmailAddress,Workspace,Disabled,AccessLevel,ProjectRoleId,RallyUserObjId FROM useronboard where CustomerId = ? and OnBoardId = ? and  EmailAddress = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			statement.setString(2, onboardId);
			statement.setString(3, userUpdateRequest.getEmailAddress());
			rs = statement.executeQuery();
			while (rs.next()) {
				userOnBoardData.setUserName(rs.getString("UserName"));
				userOnBoardData.setEmailAddress(rs.getString("EmailAddress"));
				userOnBoardData.setWorkspace(rs.getString("Workspace"));
				userOnBoardData.setDisabled(rs.getBoolean("Disabled"));
				userOnBoardData.setDisabled(rs.getBoolean("AccessLevel"));
				userOnBoardData.setProjectRole(rs.getString("ProjectRoleId"));
				userOnBoardData.setUserObjectId(rs.getString("RallyUserObjId"));
				LOGGER.log(Level.INFO, " Queried the table projectonBoard to get projectDetail");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return userOnBoardData;
	}

	public int editUsers(User user, String onboardId, String userName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int rows = 0;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			String query = "UPDATE useronboard SET AccessLevel = ? ,Disabled = ? WHERE OnBoardId = ? and UserName = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, user.getAccessLevel());
			statement.setBoolean(2, user.isDisabled());
			statement.setString(3, onboardId);
			statement.setString(4, user.getUserName());

			rows = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rows;
	}

	/**
	 * @param roleName
	 * @return ProjectRoleEntity
	 */
	public String getProjectRoleName(String roleId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		ProjectRoleEntity projectRole = new ProjectRoleEntity();
		try {

			String selectSQL = "SELECT ProjectRole FROM projectrole where ProjectRoleId = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, roleId);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectRole.setRoleName(rs.getString("ProjectRole"));
				LOGGER.log(Level.INFO,
						" Queried the table User to get ProjectRoleName--> " + projectRole.getRoleName());
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectRole.getRoleId();
	}

	/**
	 * @param cutId
	 * @return
	 */
	public String getWorkSpaceObjId(String cutId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyWorkspaceObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT ou.RallyWorkspaceObjId FROM organisationunit as ou, customerinfo as cu WHERE cu.CustomerId = ? AND ou.OrgId = cu.OrgId ";
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

	/**
	 * @param custId
	 * @param onboardId
	 * @param userUpdateRequest
	 */
	public int disableUserinDB(User user) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int rows = 0;

		String query = "UPDATE useronboard SET Disabled = ? WHERE UserName = ? ";

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(query);
			statement.setBoolean(1, true);
			statement.setString(2, user.getUserName());
			rows = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return rows;
	}

	
	
	//public boolean disableGithubUser(String custId, String onBoardId, User userUpdateRequest){
	public boolean disableGithubUser(List<Project> projectList, User userUpdateRequest){
		ProjectEntity entity = new ProjectEntity();
		Response response = null;
		CustomerDAO customerDAO = new CustomerDAO();
		ProjectDAO projectDAO = new ProjectDAO();
		TechnologyDAO technologyDAO = new TechnologyDAO();
		String onBoardId = null;
		String custId = null;
		for(Project project : projectList){
			//onBoardId = project.getOnBoardId();
			onBoardId = customerDAO.getOnboardId(project.getProjectName()).getOnboardId();
			custId = customerDAO.getCustomerId(onBoardId).getCustomerId();
			entity = projectDAO.getProjectDetailFromDB(custId, onBoardId);
			String orgName = entity.getCustomerName();
			orgName= orgName.trim();
			orgName = orgName.replaceAll(" ", "-");
			String techName = entity.getTechnologyName();
			String gitRepoName = technologyDAO.getGithubRepoName(techName);
	
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			
			
			WebTarget target = client.target("https://github3.cisco.com/api/v3/repos/"+orgName+"/"+gitRepoName+"/collaborators/"+userUpdateRequest.getUserName());
			System.out.println(target.getUri());
			
			Invocation.Builder inBuilder = target.request(MediaType.APPLICATION_JSON).header("authorization", "Basic YXMtY2ktdXNlci5nZW46IUNJQ0RwYXNz");
			response = inBuilder.get();
			System.out.println(response.getStatus());
			
			if(response.getStatus() == 204){
	
				target = client.target("https://github3.cisco.com/api/v3/repos/"+orgName+"/"+gitRepoName+"/collaborators/"+userUpdateRequest.getUserName());
				System.out.println(target.getUri());
		
				inBuilder = target.request(MediaType.APPLICATION_JSON).header("authorization", "Basic YXMtY2ktdXNlci5nZW46IUNJQ0RwYXNz");
				response = inBuilder.delete();
				System.out.println(response.getStatus());
			}
			else{
				System.out.println("User has already been removed as collaborator");
			}
		}
		
		return true;

		}
	
		/*public static void main(String[] args) {
			UserDAO userDAO = new UserDAO();
			List<UserEntity> list = new ArrayList<UserEntity>();
			UserEntity u1= new UserEntity();
			u1.setOnBoardId("54545646");
			list.add(u1);
			UserEntity u2 = new UserEntity();
			u2.setOnBoardId("45456");
			list.add(u2);
			
			User userUpdateRequest = new User();
			userUpdateRequest.setUserName("yugsingh");
			userDAO.disableGithubUser(list, userUpdateRequest);
		}*/
	
	public boolean disableRallyUser(User userUpdateRequest,String projectObjectId) {
		boolean disableUser=false;
		RallyInfo rally = projectDAO.getRallyInfo();
		User user = null;
		try {
			RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
				restApi.setApplicationName("TestApp");
				JsonObject newProject = new JsonObject();
				user = getUserObjId(userUpdateRequest);
				newProject.addProperty("User", "user/" + user.getUserObjectId());
				System.out.println("User object Id " + user.getUserObjectId());
				newProject.addProperty("Role", getRole("Viewer"));
				newProject.addProperty("Disabled", false);
				newProject.addProperty("Workspace", "/workspace/" + getWorkSpaceId(projectObjectId));
				newProject.addProperty("Project", "/project/" + projectObjectId);
				CreateRequest updateRequest = new CreateRequest("ProjectPermission", newProject);
				CreateResponse updateResponse = restApi.create(updateRequest);
				if (updateResponse.wasSuccessful()) {
					System.out.println("Response object after updating user project role");
					disableUser=true;
				} else {
					String[] errorList;
					errorList = updateResponse.getErrors();
					for (int i = 0; i < errorList.length; i++) {
						System.out.println("Error list  " + errorList[i]);
					}
				}
				// Release all resources
				restApi.close();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return disableUser;


	}

	/**
	 * @param projectObjectId
	 * @return
	 */
	private String getWorkSpaceId(String projectObjectId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyWorkspaceObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT RallyWorkspaceObjId from rallyproject where RallyProjectObjId =? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, projectObjectId);
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
	/**
	 * @param projectObjectId
	 * @return
	 */
	public String getProjectObjId(String projectName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyProjectId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT RallyProjectObjId from rallyproject where Name =? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, projectName);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyProjectId = rs.getString("RallyProjectObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyProjectId;
	}

	/**
	 * @param userId
	 * @return UserEntity
	 */
	public UserEntity getCustomerIdForUser(String onBoardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		UserEntity user = new UserEntity();
		try {

			String selectSQL = "SELECT CustomerId FROM useronboard where OnBoardId = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, onBoardId);
			rs = statement.executeQuery();
			while (rs.next()) {
				user.setCustomerId(rs.getString("CustomerId"));
				LOGGER.log(Level.INFO, " Queried the table UserOnboard to get CustomerId--> " + user.getCustomerId());
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return user;
	}

	/**
	 * @param userId
	 * @return UserEntity
	 */
	public List<UserEntity> getOnboardId(String emailAddress) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		List<UserEntity> user = new ArrayList<UserEntity>();
		try {

			String selectSQL = "SELECT OnBoardId FROM useronboard where EmailAddress = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, emailAddress);
			rs = statement.executeQuery();
			while (rs.next()) {
				UserEntity userEntity=new UserEntity();
				userEntity.setOnBoardId(rs.getString("OnBoardId"));
				user.add(userEntity);	
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return user;
	}
	
	public User getUserObjId(User userUpdateRequest) {
		User userOnBoardData = new User();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT UserName,EmailAddress,Workspace,Disabled,AccessLevel,ProjectRoleId,RallyUserObjId FROM useronboard where EmailAddress = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, userUpdateRequest.getEmailAddress());
			rs = statement.executeQuery();
			while (rs.next()) {
				userOnBoardData.setUserName(rs.getString("UserName"));
				userOnBoardData.setEmailAddress(rs.getString("EmailAddress"));
				userOnBoardData.setWorkspace(rs.getString("Workspace"));
				userOnBoardData.setDisabled(rs.getBoolean("Disabled"));
				userOnBoardData.setDisabled(rs.getBoolean("AccessLevel"));
				userOnBoardData.setProjectRole(rs.getString("ProjectRoleId"));
				userOnBoardData.setUserObjectId(rs.getString("RallyUserObjId"));
				LOGGER.log(Level.INFO, " Queried the table projectonBoard to get projectDetail");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return userOnBoardData;
	}
	
	public int updateUserInfo(User user, String onboardId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int rows = 0;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			String query = "UPDATE useronboard SET AccessLevel = ? WHERE OnBoardId = ? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, "Viewer");
			statement.setString(2, onboardId);
			rows = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rows;
	}
	
	public String getOnboardIdFromProject(String projectName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String onBoardId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT OnBoardId from projectonboard where ProjectName =? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, projectName);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				onBoardId = rs.getString("OnBoardId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return onBoardId;
	}
}
