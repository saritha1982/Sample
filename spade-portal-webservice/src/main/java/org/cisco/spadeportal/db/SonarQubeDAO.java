package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cisco.spadeportal.bean.entity.SonarQubeEntity;

/**
 * @author sarbr
 *
 */
public class SonarQubeDAO {

	private static final Logger LOGGER = Logger.getLogger(SonarQubeDAO.class.getName());

	/**
	 * @param custId
	 * @return SonarQubeEntity
	 */
	public SonarQubeEntity getSonarQubeId(String custId,Connection existingConnection) {
		SonarQubeEntity sonarQubeEntity = new SonarQubeEntity();
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

			String selectSQL = "SELECT sonar.SonarId FROM sonarqube sonar, customerinfo cu WHERE cu.CustomerId = ? AND sonar.OrgId = cu.OrgId";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			rs = statement.executeQuery();
			while (rs.next()) {
				sonarQubeEntity.setSonarId(rs.getString("SonarId"));
			}
			LOGGER.log(Level.INFO, " Queried the table sonarqube to get SonarId"+sonarQubeEntity.getSonarId());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		}
		return sonarQubeEntity;
	}
}
