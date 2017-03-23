package activiti.agent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import activiti.agent.GithubActivitiAgent;
import activiti.agent.bean.GitHubInfo;

public class GitAgentDAO {
	private static final Logger LOGGER = Logger.getLogger(GithubActivitiAgent.class.getName());

	private static final String CUSTOMER_INFO = "select Internal from customerinfo WHERE CustomerId = ?";
	private static final String SELECT_GIT_HUB_ORG_NAME = "select GitHubOrgName from githuborg gi,customerinfo cu WHERE cu.CustomerId = ? AND gi.GitHubOrgId = cu.GitHubOrgId";
	private static final String SELECT_GIT_HUB_ORG_ID = "select GitHubOrgId from customerinfo WHERE CustomerId = ?";

	private static final String SELECT_GIT_HUB_REPO_ID = "select GitHubRepoId from technology WHERE CustomerId= ? AND TechnologyName = ?";
	private static final String SELECT_GIT_HUB_REPO_NAME = "select GitHubRepoName,URL from githubrepo WHERE GitHubRepoId = ?";

	private static final String GIT_REPO_URL = "insert into githubrepo VALUES ( uuid() , ?)";
	private static final String PROJECT_ONBOARD = "select TechnologyId, RallyProjectObjId from projectonboard WHERE OnBoardId = ?";
	private static final String UPDATE_CATEGORY = "insert into category VALUES (? ,?, ?)";
	private static final String SELECT_TECHNOLOGYID = "SELECT TechnologyId FROM projectonboard WHERE OnBoardId = ? ";
	private static final String SELECT_GITHUBREPOID = "SELECT GitHubRepoId FROM technology WHERE TechnologyId = ? ";
	private static final String SELECT_GITHUBREPOURL = "SELECT URL FROM githubrepo WHERE GitHubRepoId = ? ";
	private static final String CUSTOMER_NAME = "select CustomerName FROM customerinfo where CustomerId = ?";

	
	
	
	
	public String getCustomerName(String customerId,Connection existingConnection) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		String name = null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		
		try {
			statement = connection.prepareStatement(CUSTOMER_NAME);
			statement.setString(1, customerId);
			rs = statement.executeQuery();
			
			while (rs.next()) {
				name = rs.getString("CustomerName");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		
		}
		return name;
	}
	
	
	public Boolean internalCustomer(String customerId) {
		Boolean internalCustomer = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			// connection =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/spadedev","root","cisco123");
			statement = connection.prepareStatement(CUSTOMER_INFO);
			statement.setString(1, customerId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next())
				internalCustomer = rs.getBoolean("Internal");
			LOGGER.log(Level.INFO, "Queried the database table to know whether the customer is internal or not");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return internalCustomer;

	}

	public String getOrgId(String customerId) {
		String id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(SELECT_GIT_HUB_ORG_ID);
			statement.setString(1, customerId);
			statement.execute();

			rs = statement.getResultSet();
			while (rs.next())
				id = rs.getString("GitHubOrgId");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return id;

	}

	public String getGitHubOrgName(String customerId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String orgName = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(SELECT_GIT_HUB_ORG_NAME);
			statement.setString(1, customerId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next())
				orgName = rs.getString("GitHubOrgName");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return orgName;

	}

	public String getRepoId(String customerId, String techName) {
		String id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(SELECT_GIT_HUB_REPO_ID);
			statement.setString(1, customerId);
			statement.setString(2, techName);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next())
				id = rs.getString("GitHubRepoId");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return id;

	}

	public GitHubInfo getGitHubRepoName(String customerId, String techName) {

		techName = techName.trim();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		GitHubInfo githubInfo = new GitHubInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(SELECT_GIT_HUB_REPO_NAME);
			statement.setString(1, getRepoId(customerId, techName));
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				githubInfo.setRepoName(rs.getString("GitHubRepoName"));
				githubInfo.setUrl(rs.getString("URL"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return githubInfo;
	}

	public String updateGithubRepo(String orgName, String techName) {
		orgName = orgName.trim();
		techName = techName.trim();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		int insertedline = 0;

		String url = "https//github3.cisco.com/" + orgName + "/" + techName + ".git";
		System.out.println(url);

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(GIT_REPO_URL);
			statement.setString(1, url);
			insertedline = statement.executeUpdate();
			System.out.println(insertedline);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return url;
	}

	public int updateCategory(String projectOnboardId, String gitFolder) {
		gitFolder = gitFolder.trim();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		int insertedLine = 0;
		String techid = null;
		String rallyProjectId = null;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			statement = connection.prepareStatement(PROJECT_ONBOARD);

			statement.setString(1, projectOnboardId);
			statement.execute();

			rs = statement.getResultSet();
			while (rs.next()) {
				techid = rs.getString("TechnologyId");
				rallyProjectId = rs.getString("RallyProjectObjId");

			}

			System.out.println(techid);
			System.out.println(rallyProjectId);

			statement.close();

			statement = connection.prepareStatement(UPDATE_CATEGORY);

			statement.setString(1, techid);
			statement.setString(2, rallyProjectId);
			statement.setString(3, gitFolder);

			insertedLine = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return insertedLine;
	}

	public String getTechnologyId(String onBoardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String technologyId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_TECHNOLOGYID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, onBoardId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				technologyId = rs.getString("TechnologyId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return technologyId;
	}

	public String getGitHubRepoId(String onBoardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String gitHubRepoId = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_GITHUBREPOID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, getTechnologyId(onBoardId));
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				gitHubRepoId = rs.getString("GitHubRepoId");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return gitHubRepoId;
	}

	public GitHubInfo getGitHubRepoUrl(String onBoardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		GitHubInfo gitHubInfo = new GitHubInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_GIT_HUB_REPO_NAME;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, getGitHubRepoId(onBoardId));
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				gitHubInfo.setUrl(rs.getString("URL"));
				gitHubInfo.setRepoName(rs.getString("GitHubRepoName"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return gitHubInfo;
	}

}