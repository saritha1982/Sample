package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.ProjectTypeEntity;

/**
 * @author sarbr
 *
 */
public class ProjectTypeDAO {
	private static final Logger LOGGER = Logger.getLogger(ProjectTypeDAO.class.getName());
	private static final String PROJECT_TYPE_NAME = "select ProjectTypeName FROM projecttype where ProjectTypeId = ? ";

	/**
	 * @param customerId
	 * @return list
	 */
	public List<ProjectTypeEntity> getProjectTypesFromDB() {
		List<ProjectTypeEntity> projectTypeList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		connection = DBConnectionFactory.getDatabaseConnection();

		try {

			String selectSQL = "SELECT ProjectTypeName FROM projecttype ";
			statement = connection.prepareStatement(selectSQL);
			rs = statement.executeQuery();
			projectTypeList = new ArrayList<ProjectTypeEntity>();
			while (rs.next()) {
				ProjectTypeEntity projectType = new ProjectTypeEntity();
				projectType.setName(rs.getString("ProjectTypeName"));
				LOGGER.log(Level.INFO,
						" Queried the table to get ProjectType list from projecttype--> " + projectType.getName());
				projectTypeList.add(projectType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectTypeList;
	}

	/**
	 * @param customerId
	 * @param projectTypeName
	 * @return ProjectTypeEntity
	 */
	public ProjectTypeEntity getProjectTypeId(String projectTypeName,Connection existingConnection) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		ProjectTypeEntity projectTypeEntity = new ProjectTypeEntity();
		try {

			String selectSQL = "SELECT ProjectTypeId FROM projecttype where ProjectTypeName = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, projectTypeName);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectTypeEntity.setProjectTypeId(rs.getString("ProjectTypeId"));
			}
			LOGGER.log(Level.INFO, " Queried the table to get projectTypeId from projecttype--> "
					+ projectTypeEntity.getProjectTypeId());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		}
		return projectTypeEntity;
	}

	public ProjectTypeEntity getProjectTypeName(String projectTypeId, Connection existingConnection) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		ProjectTypeEntity entity = new ProjectTypeEntity();
		try {
			statement = connection.prepareStatement(PROJECT_TYPE_NAME);
			statement.setString(1, projectTypeId);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setName(rs.getString("ProjectTypeName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		}

		return entity;

	}
}
