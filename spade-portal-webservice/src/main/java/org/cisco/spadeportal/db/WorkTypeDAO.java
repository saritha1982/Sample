package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.WorkTypeEntity;

/**
 * @author sarbr
 *
 */
public class WorkTypeDAO {
	private static final Logger LOGGER = Logger.getLogger(WorkTypeDAO.class.getName());

	/**
	 * @param customerId
	 * @return list
	 */
	public List<WorkTypeEntity> getWorkTypesFromDB() {
		List<WorkTypeEntity> workTypeList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT WorkTypeName FROM worktype";
			statement = connection.prepareStatement(selectSQL);
			rs = statement.executeQuery();
			workTypeList = new ArrayList<WorkTypeEntity>();
			while (rs.next()) {
				WorkTypeEntity workType = new WorkTypeEntity();
				workType.setWorkTypeName(rs.getString("WorkTypeName"));
				LOGGER.log(Level.INFO,
						" Queried the table WorkType to get worktype list--> " + workType.getWorkTypeName());
				workTypeList.add(workType);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return workTypeList;
	}

	/**
	 * @param customerId
	 * @return list
	 */
	public WorkTypeEntity getWorkTypeDetailFromDB(String workTypeId,Connection existingConnection) {
		WorkTypeEntity workType = new WorkTypeEntity();
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

			String selectSQL = "SELECT WorkTypeName FROM worktype  where WorkTypeId = ? ";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, workTypeId);
			rs = statement.executeQuery();
			while (rs.next()) {
				workType.setWorkTypeName(rs.getString("WorkTypeName"));
				
			}
			LOGGER.log(Level.INFO,
					" Queried the table WorkType to get worktype detail" + workType.getWorkTypeName());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occur", e);
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		}
		return workType;
	}

	
	
	/**
	 * @param customerId
	 * @param workTypeName
	 * @return String
	 */
	public String getWorkTypeId(String workTypeName,Connection existingConnection){
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}

		String workTypeId="";
		try {

			String selectSQL = "SELECT WorkTypeId FROM worktype  where  WorkTypeName = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, workTypeName);
			rs = statement.executeQuery();
			while (rs.next()) {
				workTypeId=rs.getString("WorkTypeId");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}
		}


		return workTypeId;
	}
}
