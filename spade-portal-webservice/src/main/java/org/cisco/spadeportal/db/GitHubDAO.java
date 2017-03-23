package org.cisco.spadeportal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.entity.GitHubEntity;

public class GitHubDAO {

	private static final Logger LOGGER = Logger.getLogger(GitHubDAO.class.getName());

	public GitHubEntity getGitHubId(String custId,Connection existingConnection) {
		GitHubEntity githubEntity = new GitHubEntity();
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

			String selectSQL = "SELECT git.GitHubId FROM github git, customerinfo cu WHERE cu.CustomerId = ? AND git.OrgId = cu.OrgId";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			rs = statement.executeQuery();
			while (rs.next()) {
				githubEntity.setGithubId(rs.getString("GitHubId"));
			}
			LOGGER.log(Level.INFO, " Queried the table github to get githubId" + githubEntity.getGithubId());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			if(existingConnection==null){
				DBConnectionFactory.releaseConnection(statement, rs, connection);
			}		
		}
		return githubEntity;
	}

	public String getGitRepoId(String repo) {
		repo=repo.trim();
		repo=repo.replaceAll(" ", "-");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String query = "select GitHubRepoId FROM githubrepo WHERE GitHubRepoName = ? ";
		String repoId = null;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, repo);
			rs = statement.executeQuery();

			while (rs.next()) {
				repoId = rs.getString("GitHubRepoId");
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occurred", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return repoId;
	}

	public String updateGithubRepo(String orgName, String techName) {
		orgName = orgName.trim();
		techName = techName.trim();
		
		orgName = orgName.replaceAll(" ", "-");
		techName = techName.replaceAll(" ", "-");
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		int insertedline = 0;

		String url = "https://github3.cisco.com/" + orgName + "/" + techName + ".git";
		System.out.println(url);

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement("insert into githubrepo VALUES ( uuid() , ?, ?)");
			statement.setString(1, techName);
			statement.setString(2, url);
			insertedline = statement.executeUpdate();
			System.out.println(insertedline);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return url;
	}
	
	public void updateGithubOrg(String orgName, Connection existingConnection){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String query = "insert into githuborg VALUES(uuid(), ?)";
		int inserted = 0;
		
		if(existingConnection!= null){
			connection = existingConnection;
		}else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		try{
			statement = connection.prepareStatement(query);
			statement.setString(1, orgName);
			inserted = statement.executeUpdate();
			System.out.println(inserted);
		}catch(SQLException e){
			LOGGER.log(Level.INFO, "Exception occurred", e);
		}finally{
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
	}
	
	public String getGitOrgId(String name, Connection existingConnection){
		CustomerDAO customerDAO = new CustomerDAO();
		Connection connection = null;
		PreparedStatement statement = null;
		//String customerName = customerDAO.getOrgName(custId);
		ResultSet rs = null;
		String query = "select * from githuborg WHERE GitHubOrgName = ?";
		String orgId = null;
		if(existingConnection!= null){
			connection = existingConnection;
		}else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		try{
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			rs = statement.executeQuery();
			while(rs.next()){
				orgId = rs.getString("GitHubOrgId");
			}
		}catch(SQLException e){
			LOGGER.log(Level.INFO, "Exception occurred", e);
		}finally{
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return orgId;
	}

}
