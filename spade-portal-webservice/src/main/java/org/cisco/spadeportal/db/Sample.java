/**
 * 
 */
package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.cisco.spadeportal.bean.entity.TechnologyEntity;

/**
 * @author sarbr
 *
 */
public class Sample {
	public static void main(String[] args) {
		Sample technologyDAO = new Sample();
		technologyDAO.getTechnologiesFromDB("9d83523d-fcc4-11e6-95b3-507b9dc361f2");
	}

	public List<TechnologyEntity> getTechnologiesFromDB(String customerId) {
		List<TechnologyEntity> technologyList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {
			System.out.println("Invoking query");
			String selectSQL = "SELECT TechnologyName FROM technology where CustomerId = ? ";
			System.out.println(selectSQL);
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			rs = statement.executeQuery();
			technologyList = new ArrayList<TechnologyEntity>();
			while (rs.next()) {
				TechnologyEntity technology = new TechnologyEntity();
				technology.setTechnologyName(rs.getString("TechnologyName"));
				System.out.println(" Queried the table Technology to get technology list--> " + technology);
				technologyList.add(technology);
			}
		} catch (SQLException e) {
			System.out.println("Exception occur"+e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return technologyList;
	}
}
