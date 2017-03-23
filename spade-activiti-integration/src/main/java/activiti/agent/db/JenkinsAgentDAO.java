package activiti.agent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import activiti.agent.bean.CustomerInfo;
import activiti.agent.bean.GitHubInfo;
import activiti.agent.bean.JenkinsInfo;

public class JenkinsAgentDAO {

	private static final String CREATE_JENKINS_INSTANCE = "INSERT INTO jenkinsjob VALUES ( ? , ?, ?, ? )";
	private static final String SELECT_JENKINS = "SELECT JenkinsId,UserName, Password, URL FROM jenkins je, customerinfo cu WHERE cu.CustomerId = ? AND je.OrgId = cu.OrgId";
	private static final String SELECT_GITHUB = "SELECT URL FROM githubrepo where GitHubRepoName = ? ";
	private static final String SELECT_CUSTOMER_INFO = "SELECT Internal,CustomerName FROM customerinfo where CustomerId = ? ";


	public JenkinsInfo getJenkinsDetails(String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		JenkinsInfo jenkinsInfo = new JenkinsInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_JENKINS;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				jenkinsInfo.setJenkinsId(rs.getString("JenkinsId"));
				jenkinsInfo.setUserName(rs.getString("UserName"));
				jenkinsInfo.setPassword(rs.getString("Password"));
				jenkinsInfo.setUrl(rs.getString("URL"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return jenkinsInfo;
	}

	public void createJenkinsInstance(String jenkinsId, String url, String jobName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(CREATE_JENKINS_INSTANCE);
			statement.setString(1, "uuid()");
			statement.setString(2, jenkinsId);
			statement.setString(3, url);
			statement.setString(4, jobName);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
	}

	public GitHubInfo getGithubDetails(String techName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		GitHubInfo gitHubInfo = new GitHubInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			String selectSQL = SELECT_GITHUB;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, techName);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				gitHubInfo.setUrl(rs.getString("URL"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return gitHubInfo;
	}
	
	public CustomerInfo getCustomerDetails(String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		CustomerInfo customerInfo = new CustomerInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_CUSTOMER_INFO;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				customerInfo.setInternal(rs.getBoolean("Internal"));
				customerInfo.setCustomerName(rs.getString("CustomerName"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return customerInfo;
	}

}
