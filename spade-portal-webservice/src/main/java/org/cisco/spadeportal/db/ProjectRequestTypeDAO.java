package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.ProjectRequestTypeEntity;

/**
 * @author sarbr
 *
 */
public class ProjectRequestTypeDAO {

	private static final Logger LOGGER = Logger.getLogger(ProjectRequestTypeDAO.class.getName());
	private static final String PROJECT_REQUEST_NAME = "select ProjectRequestTypeName FROM projectrequesttype where ProjectRequestTypeId = ? ";

	/**
	 * @return list
	 */
	public List<ProjectRequestTypeEntity> getProjectRequestTypesFromDB() {
		List<ProjectRequestTypeEntity> projectRequestTypeList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT ProjectRequestTypeName FROM projectrequesttype ";
			statement = connection.prepareStatement(selectSQL);
			rs = statement.executeQuery();
			projectRequestTypeList = new ArrayList<ProjectRequestTypeEntity>();
			while (rs.next()) {
				ProjectRequestTypeEntity projectRequestType = new ProjectRequestTypeEntity();
				projectRequestType.setName(rs.getString("ProjectRequestTypeName"));
				projectRequestTypeList.add(projectRequestType);
			}
			LOGGER.log(Level.INFO, " Queried the table to ProjectRequestType from projectrequesttype table--> "
					+ projectRequestTypeList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectRequestTypeList;
	}

	/**
	 * @param projectRequestTypeName
	 * @return String
	 */
	public ProjectRequestTypeEntity getProjectRequestTypeId(String projectRequestTypeName, Connection existingConnection) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}

		ProjectRequestTypeEntity projectRequestTypeEntity = new ProjectRequestTypeEntity();
		try {

			String selectSQL = "SELECT ProjectRequestTypeId FROM projectrequesttype where ProjectRequestTypeName = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, projectRequestTypeName);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectRequestTypeEntity.setProjectRequestTypeId(rs.getString("ProjectRequestTypeId"));
			}
			LOGGER.log(Level.INFO, " Queried the table to get projectRequestId from projectequesttype table--> "
					+ projectRequestTypeEntity.getProjectRequestTypeId());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		
		}

		return projectRequestTypeEntity;
	}

	/**
	 * @param projectRequestTypeId
	 * @return
	 */
	public ProjectRequestTypeEntity getProjectRequestTypeName(String projectRequestTypeId, Connection existingConnection) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		ProjectRequestTypeEntity entity = new ProjectRequestTypeEntity();

		try {
			statement = connection.prepareStatement(PROJECT_REQUEST_NAME);
			statement.setString(1, projectRequestTypeId);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setName(rs.getString("ProjectRequestTypeName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}
		}

		return entity;
	}
}
