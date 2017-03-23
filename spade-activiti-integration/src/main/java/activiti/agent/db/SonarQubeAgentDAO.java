/**
 * 
 */
package activiti.agent.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import activiti.agent.bean.JenkinsInfo;

/**
 * @author sarbr
 *
 */
public class SonarQubeAgentDAO {

	private static final String SELECT_SONARQUBE = "SELECT SonarId,UserName, Password, URL FROM sonarqube so, customerinfo cu WHERE cu.CustomerId = ? AND so.OrgId = cu.OrgId";

	
	public JenkinsInfo getSonarQubeDetails(String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		JenkinsInfo jenkinsInfo = new JenkinsInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_SONARQUBE;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				jenkinsInfo.setJenkinsId(rs.getString("SonarId"));
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
}
