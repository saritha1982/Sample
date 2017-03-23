package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.OrganisationEntity;

/**
 * @author sarbr
 *
 */
public class OrganisationDAO {
	private static final Logger LOGGER = Logger.getLogger(OrganisationDAO.class.getName());

	/**
	 * @param customerId
	 * @return OrganisationEntity
	 */
	public OrganisationEntity getOrgId(String orgId){
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		OrganisationEntity organisationEntity=new OrganisationEntity();
		try {

			String selectSQL = "SELECT * FROM organisationunit where OrgId = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, orgId);
			rs = statement.executeQuery();
			while (rs.next()) {
				organisationEntity.setOrgId(rs.getString("OrgId"));
				LOGGER.log(Level.INFO,
						" Queried the table to get row from organisationunit--> " + organisationEntity.getOrgId());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return organisationEntity;
	}

}
