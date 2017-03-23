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
import java.util.logging.Logger;
import org.cisco.spadeportal.bean.request.CategoryEntity;


/**
 * @author sarbr
 *
 */
public class CategoryDAO {
	private static final Logger LOGGER = Logger.getLogger(CategoryDAO.class.getName());
	TechnologyDAO technologyDAO=new TechnologyDAO();
	
	

	/**
	 * @param customerId
	 * @return list
	 */
	public List<CategoryEntity> getCategoriesFromDB(String customerId,String technologyName) {
		List<CategoryEntity> categoryList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT CategoryName FROM category where TechnologyId = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, technologyDAO.getTechnologyId(customerId, technologyName, connection).getTechnologyId());
			rs = statement.executeQuery();
			categoryList = new ArrayList<CategoryEntity>();
			while (rs.next()) {
				CategoryEntity technology = new CategoryEntity();
				technology.setCategoryName(rs.getString("CategoryName"));
				categoryList.add(technology);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return categoryList;
	}

}
