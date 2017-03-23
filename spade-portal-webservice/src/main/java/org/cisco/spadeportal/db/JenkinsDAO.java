package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.JenkinsEntity;

/**
 * @author sarbr
 *
 */
public class JenkinsDAO {

	private static final Logger LOGGER = Logger.getLogger(JenkinsDAO.class.getName());

	/**
	 * @param orgId
	 * @return JenkinsEntity
	 */
	public JenkinsEntity getJenkinsId(String custId,Connection existingConnection) {
		JenkinsEntity jenkinsEntity = new JenkinsEntity();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		try {
			String selectSQL = "SELECT je.JenkinsId FROM jenkins je, customerinfo cu WHERE cu.CustomerId = ? AND je.OrgId = cu.OrgId";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1,custId);
			rs = statement.executeQuery();
			while (rs.next()) {
				jenkinsEntity.setJenkinsId(rs.getString("JenkinsId"));
			}
			LOGGER.log(Level.INFO, " Queried the table jenkins to get jenkinsId");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		}
		return jenkinsEntity;
	}
}
