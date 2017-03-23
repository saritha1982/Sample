package activiti.agent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import activiti.agent.bean.RallyInfo;
import activiti.agent.bean.UserInfo;

public class RallyAgentDAO {
	private static final String SELECT_RALLY_WORKSPACE_OBJ_ID = "SELECT ou.RallyWorkspaceObjId FROM organisationunit as ou, customerinfo as cu WHERE cu.CustomerId = ? AND ou.OrgId = cu.OrgId ";
	private static final String SELECT_RALLY_PROJECT_OBJ_ID = " SELECT RallyProjectObjId FROM rallyproject WHERE Name = ? AND RallyWorkspaceObjId = ? ";
	private static final String CREATE_RALLY_PROJECT = "INSERT INTO rallyproject VALUES ( ? , ?, ?, ? )";
	private static final String SELECT_RALLY_INSTANCE = "SELECT UserName, Password, URL FROM rally ra, customerinfo cu WHERE cu.CustomerId = ? AND ra.OrgId = cu.OrgId";
	private static final String SELECT_USER_OBJ_ID = "SELECT RallyUserObjId FROM useronboard WHERE CustomerId = ? AND EmailAddress = ? ";
	private static final String SELECT_USERS_FOR_PROJECT = "SELECT * FROM useronboard WHERE CustomerId = ? AND OnBoardId  = ? AND RallyUserObjId is NULL";
	private static final String UPDATE_PROJECT_OBJ_ID = "UPDATE projectonboard SET RallyProjectObjId = ? WHERE CustomerId = ? AND OnBoardId  = ? ";
	private static final String SELECT_USER_ONBOARD_OBJ_ID = "SELECT RallyUserObjId FROM useronboard WHERE CustomerId = ? AND EmailAddress = ? AND OnBoardId = ? ";
	private static final String SELECT_RALLY_USER_OBJ_ID = "SELECT UserObjectId FROM rallyuserlist WHERE UserName = ? ";
	private static final String SELECT_PROJECTNAME = "SELECT ProjectName FROM projectonboard WHERE OnBoardId = ? ";
	private static final String SELECT_USERS_FOR_GITHUB = "SELECT * FROM useronboard WHERE CustomerId = ? AND OnBoardId  = ? ";
	private static final String SELECT_USER_FOR_PROJECT = "SELECT * FROM useronboard WHERE CustomerId = ? AND OnBoardId  = ? AND EmailAddress = ? ";
	private static final String CREATE_RALLY_USER_LIST = "INSERT INTO rallyuserlist VALUES ( ? , ? )";
	private static final String SELECT_CATEGORY_OBJ_ID = "SELECT RallyCategoryObjId FROM category WHERE CategoryName = ? ";

	
	public RallyInfo getRallyDetails(String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		RallyInfo rally = new RallyInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_RALLY_INSTANCE;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rally.setUserName(rs.getString("UserName"));
				rally.setApiKey(rs.getString("Password"));
				rally.setUrl(rs.getString("URL"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		if (rally != null) {
			System.out.println("UserName in Rally table" + rally.getUserName());
		}
		return rally;
	}

	public List<UserInfo> getUsersForProject(String custId, String projectOnboardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		List<UserInfo> userList = new ArrayList<UserInfo>();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_USERS_FOR_PROJECT;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.setString(2, projectOnboardId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				UserInfo user = new UserInfo();

				user.setUserName(rs.getString("UserName"));
				user.setEmail(rs.getString("EmailAddress"));
				user.setCustId(rs.getString("CustomerId"));
				user.setProjectOnBoardId(rs.getString("OnBoardId"));
				user.setDisabled(rs.getBoolean("Disabled"));
				user.setProjectRoleId(rs.getString("ProjectRoleId"));
				user.setAcessLevel(rs.getString("AccessLevel"));
				user.setRallyObjId(rs.getLong("RallyUserObjId"));
				// add to list

				userList.add(user);
			}
			System.out.println("User List " + userList);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return userList;
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

	public String updateProjectObjId(String custId, String projectObjId, String onboardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyProjectObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = UPDATE_PROJECT_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setLong(1, Long.parseLong(projectObjId));
			statement.setString(2, custId);
			statement.setString(3, onboardId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyProjectObjId;
	}

	public void updateRallyUserObjId(String rallyUserObjId, String emailAddress) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "UPDATE useronboard SET RallyUserObjId = ? where EmailAddress = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, rallyUserObjId);
			statement.setString(2, emailAddress);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
	}

	public String getRallyUserOnBoardObjId(String cutId, String emailId, String onboardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyUserObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_USER_ONBOARD_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, cutId);
			statement.setString(2, emailId);
			statement.setString(3, onboardId);

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

	public List<String> getRallyUserObjId(String userName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyUserObjId = "";
		Connection connection = null;
		List<String> objectIdList = new ArrayList<String>();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_RALLY_USER_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, userName);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyUserObjId = rs.getString("UserObjectId");
				objectIdList.add(rallyUserObjId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return objectIdList;
	}

	public String getProjectName(String onBoardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String projectName = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_PROJECTNAME;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, onBoardId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				projectName = rs.getString("ProjectName");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectName;
	}

	public List<UserInfo> getUsersForGitHubProject(String custId, String projectOnboardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		List<UserInfo> userList = new ArrayList<UserInfo>();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_USERS_FOR_GITHUB;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.setString(2, projectOnboardId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				UserInfo user = new UserInfo();

				user.setUserName(rs.getString("UserName"));
				user.setEmail(rs.getString("EmailAddress"));
				user.setCustId(rs.getString("CustomerId"));
				user.setProjectOnBoardId(rs.getString("OnBoardId"));
				user.setDisabled(rs.getBoolean("Disabled"));
				user.setProjectRoleId(rs.getString("ProjectRoleId"));
				user.setAcessLevel(rs.getString("AccessLevel"));
				user.setRallyObjId(rs.getLong("RallyUserObjId"));
				// add to list

				userList.add(user);
			}
			System.out.println("User List " + userList);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return userList;
	}

	public UserInfo getUserForProject(String custId, String projectOnboardId, String emailAddress) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		UserInfo user = new UserInfo();

		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_USER_FOR_PROJECT;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.setString(2, projectOnboardId);
			statement.setString(3, emailAddress);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {

				user.setUserName(rs.getString("UserName"));
				user.setEmail(rs.getString("EmailAddress"));
				user.setCustId(rs.getString("CustomerId"));
				user.setProjectOnBoardId(rs.getString("OnBoardId"));
				user.setDisabled(rs.getBoolean("Disabled"));
				user.setProjectRoleId(rs.getString("ProjectRoleId"));
				user.setAcessLevel(rs.getString("AccessLevel"));
				user.setRallyObjId(rs.getLong("RallyUserObjId"));
				// add to list

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return user;
	}

	public void insertIntoRallyUserList(String userName, String userObjectId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(CREATE_RALLY_USER_LIST);
			statement.setString(1, userName);
			statement.setString(2, userObjectId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
	}
	
	public String getCategoryRallyObjId(String categoryName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String rallyUserObjId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_CATEGORY_OBJ_ID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, categoryName);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rallyUserObjId = rs.getString("RallyCategoryObjId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rallyUserObjId;
	}

}
