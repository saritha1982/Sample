package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author anunag
 *
 */
public class PortalConfigDAO {
	private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

	/**
	 * @param name
	 * @return String
	 */
	public String getConfig(String name) {
		String value = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT ConfigValue FROM portalconfig where ConfigKey = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, name);
			rs = statement.executeQuery();
			while (rs.next()) {
				value = rs.getString("ConfigValue");
				LOGGER.log(Level.INFO, " Queried the table protalconfig to get value --> " + value);
				break;
			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return value;
	}
}
