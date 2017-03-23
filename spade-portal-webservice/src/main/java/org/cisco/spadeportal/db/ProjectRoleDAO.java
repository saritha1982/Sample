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

import org.cisco.spadeportal.bean.entity.ProjectRoleEntity;


/**
 * @author yugsingh
 *
 */
public class ProjectRoleDAO {
	private static final String GET_PROJECT_ROLES ="select * from projectrole"; 
	private static final String GET_PROJECT_ROLE_ID ="select ProjectRoleId from projectrole where ProjectRole = ? "; 

	
	public List<ProjectRoleEntity> getProjectRolesFromDB(){
		ArrayList<ProjectRoleEntity> projectRolesList = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		conn = DBConnectionFactory.getDatabaseConnection();
		try {
			projectRolesList = new ArrayList<ProjectRoleEntity>();
			statement = conn.prepareStatement(GET_PROJECT_ROLES);
			rs = statement.executeQuery();
			while(rs.next()){
				ProjectRoleEntity roleEntity = new ProjectRoleEntity();
				roleEntity.setRoleName(rs.getString("ProjectRole"));
				projectRolesList.add(roleEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnectionFactory.releaseConnection(statement, rs, conn);
		}
		return projectRolesList;
	}
	
	public String getProjectRoleId(String projectRoleName) {
		String id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(GET_PROJECT_ROLE_ID);
			statement.setString(1, projectRoleName);
			statement.execute();

			rs = statement.getResultSet();
			while (rs.next())
				id = rs.getString("ProjectRoleId");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return id;

	}
	
	/*public static void main(String[] args) {
		ProjectRoleDAO d = new ProjectRoleDAO();
		System.out.println(d.getProjectRolesFromDB());
	}*/
}
