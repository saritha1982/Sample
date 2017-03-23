package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.ProjectStateEntity;

/**
 * @author sarbr
 *
 */
public class ProjectStateDAO {
	private static final Logger LOGGER = Logger.getLogger(ProjectStateDAO.class.getName());

	/**
	 * @param customerId
	 * @param projectStateName
	 * @return projectStateId
	 */
	public ProjectStateEntity getProjectStateId(String customerId, String projectStateName,Connection existingConnection) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		ProjectStateEntity projectStateEntity = new ProjectStateEntity();
		try {

			String selectSQL = "SELECT ps.ProjectStateId FROM projectstate ps, customerinfo cu where cu.OrgId = ps.OrgId AND cu.CustomerId= ? AND ProjectStateName = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			statement.setString(2, projectStateName);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectStateEntity.setProjectStateId(rs.getString("ProjectStateId"));
				
			}
			LOGGER.log(Level.INFO, " Queried the table to get projectStateId from projectstate--> "
					+ projectStateEntity.getProjectStateId());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}
		}
		return projectStateEntity;
	}
}
