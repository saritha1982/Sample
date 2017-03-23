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
public class ArtifactoryAgentDAO {
private static final String SELECT_ARTIFACTORY = "SELECT ArtifactoryId,UserName, Password, URL FROM artifactory ai, customerinfo cu WHERE cu.CustomerId = ? AND ai.OrgId = cu.OrgId";

	
	public JenkinsInfo getArtifactoryDetails(String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		JenkinsInfo jenkinsInfo = new JenkinsInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_ARTIFACTORY;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				jenkinsInfo.setJenkinsId(rs.getString("ArtifactoryId"));
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
