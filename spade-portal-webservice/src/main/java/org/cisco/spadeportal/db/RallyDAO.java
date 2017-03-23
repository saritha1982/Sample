package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.RallyEntity;

/**
 * @author sarbr
 *
 */
public class RallyDAO {

	private static final Logger LOGGER = Logger.getLogger(RallyDAO.class.getName());

	/**
	 * @param customerId
	 * @return RallyEntity
	 */
	public RallyEntity getRallyId(String customerId,Connection existingConnection) {
		RallyEntity rallyEntity = new RallyEntity();
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

			String selectSQL = "SELECT ra.RallyId FROM rally ra, customerinfo cu WHERE cu.CustomerId = ? AND ra.OrgId = cu.OrgId";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			rs = statement.executeQuery();
			while (rs.next()) {
				rallyEntity.setRallyId(rs.getString("RallyId"));
			}
			LOGGER.log(Level.INFO, " Queried the table rally to get rallyId");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		}
		return rallyEntity;
	}
}
