package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cisco.spadeportal.bean.entity.ArtifactoryEntity;

/**
 * @author sarbr
 *
 */
public class ArtifactoryDAO {
	private static final Logger LOGGER = Logger.getLogger(ArtifactoryDAO.class.getName());

	/**
	 * @param orgId
	 * @return ArtifactoryEntity
	 */
	public ArtifactoryEntity getArtifactoryId(String customerId,Connection existingConnection) {
		ArtifactoryEntity artifactoryEntity = new ArtifactoryEntity();
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
			String selectSQL = "SELECT arti.ArtifactoryId FROM artifactory arti, customerinfo cu WHERE cu.CustomerId = ? AND arti.OrgId = cu.OrgId";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			rs = statement.executeQuery();
			while (rs.next()) {
				artifactoryEntity.setArtifactoryId(rs.getString("ArtifactoryId"));
			}
			LOGGER.log(Level.INFO, " Queried the table artifactory to get artifactoryId");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}
		}
		return artifactoryEntity;
	}
}
