package org.cisco.spadeportal.db;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.activiti.RallyInfo;
import org.cisco.spadeportal.bean.entity.CustomerEntity;
import org.cisco.spadeportal.bean.entity.OrganisationEntity;
import org.cisco.spadeportal.bean.entity.ProjectEntity;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.response.CreateResponse;

/**
 * @author sarbr
 *
 */
public class CustomerDAO {
	private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());
	private static final String CUSTOMER_NAME = "select CustomerName FROM customerinfo where CustomerId = ?";
	private static final String GET_CUSTOMER_ID = "select CustomerId from projectonboard where OnBoardId = ?";
	private static final String SELECT_CUSTOMER_ID = "select CustomerId from customerinfo where CustomerName = ?";


	TechnologyDAO technologyDAO=new TechnologyDAO();
	WorkTypeDAO workTypeDAO=new WorkTypeDAO();
	GitHubDAO gitHubDAO = new GitHubDAO();

	private static final String CUSTOMER_ID = "select CustomerId from  customerinfo where CustomerName = ?";
	
	
	
	
	public void updateCustomerInfo(String custId, String githubOrgId, Connection existingConnection){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int updated = 0;
		String query = "Update customerinfo SET GitHubOrgId = ? WHERE CustomerId = ? ";
		if(existingConnection!= null){
			connection = existingConnection;
		}else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		
		try{
			statement = connection.prepareStatement(query);
			statement.setString(1, githubOrgId);
			statement.setString(2, custId);
			updated = statement.executeUpdate();
			System.out.println(updated);
			
		}catch(SQLException e){
			LOGGER.log(Level.INFO, "Exception occurred", e);
			
		}finally{
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		
		
	}
	public CustomerEntity customerId(String customerName){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		CustomerEntity customerEntity = new CustomerEntity();
		try{
			
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(CUSTOMER_ID);
			statement.setString(1, customerName);
			rs = statement.executeQuery();
			
			while(rs.next()){
				customerEntity.setCustomerId(rs.getString("CustomerId"));
			}
			
		}catch(SQLException e){
			LOGGER.log(Level.SEVERE, "Exception thrown", e);
		}finally{
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		
		
		return customerEntity;
	}


	/**
	 * @param customerId
	 * @return list
	 */
	public List<CustomerEntity> getCustomerNamesFromDB(String workTypeName) {
		List<CustomerEntity> customerList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String selectSQL = null;

		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {
			if(!(workTypeName == null)){
				selectSQL = "SELECT * FROM customerinfo where WorkTypeId = ? ";
				statement = connection.prepareStatement(selectSQL);
				statement.setString(1, workTypeDAO.getWorkTypeId(workTypeName, connection));
			}else{
				selectSQL = "SELECT * FROM customerinfo ";
				statement = connection.prepareStatement(selectSQL);
			}
			
			
			
			
			rs = statement.executeQuery();
			customerList = new ArrayList<CustomerEntity>();
			while (rs.next()) {
				CustomerEntity customer = new CustomerEntity();
				customer.setName(rs.getString("CustomerName"));
				customer.setContact(rs.getString("Contact"));
				customer.setCustomerType(rs.getString("CustomerType"));
				customer.setEmailAddress(rs.getString("EmailAddress"));
				customer.setInternal(rs.getBoolean("Internal"));
				customer.setRegion(rs.getString("Region"));

				LOGGER.log(Level.INFO, " Queried the table to get CustomerName--> " + customer.getName());
				customerList.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return customerList;
	}

	public CustomerEntity getCustomerName(String customerId,Connection existingConnection) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection=null;
		if(existingConnection!=null){
			connection=existingConnection;
		}
		else{
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		CustomerEntity entity = new CustomerEntity();
		try {
			statement = connection.prepareStatement(CUSTOMER_NAME);
			statement.setString(1, customerId);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setName(rs.getString("CustomerName"));
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

	public CustomerEntity getCustomerId(String onBoardId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		CustomerEntity entity = new CustomerEntity();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(GET_CUSTOMER_ID);
			statement.setString(1, onBoardId);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setCustomerId(rs.getString("CustomerId"));
				// customerId = rs.getString("CustomerId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return entity;
	}

	public ProjectEntity getOnboardId(String projectName) {
		ProjectEntity entity = new ProjectEntity();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "select OnBoardId FROM projectonboard WHERE ProjectName = ?";
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, projectName);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setOnboardId(rs.getString("OnBoardId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return entity;

	}
	
	
	public int updateCustomerInfo(String custId, CustomerEntity customerEntity)
		 {
			PreparedStatement statement = null;
			ResultSet rs = null;
			int returnRow = 0;
			Connection connection = DBConnectionFactory.getDatabaseConnection();
			try {

			String selectSQL = "UPDATE customerinfo SET EmailAddress = ? , Region = ? , Contact = ? where CustomerId = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1,customerEntity.getEmailAddress());
			statement.setString(2,customerEntity.getRegion());
			statement.setString(3,customerEntity.getContact());
			statement.setString(4, customerEntity.getCustomerId());
			returnRow = statement.executeUpdate();

			} catch (SQLException e) {
			e.printStackTrace();
			} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
			}

			return returnRow;
			}

	public String getOrgName(String customerId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		String query = "SELECT * from customerinfo WHERE CustomerId = ? ";
		OrganisationEntity entity = new OrganisationEntity();
		String orgName = null;

		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, customerId);
			rs = statement.executeQuery();

			while (rs.next()) {
				entity.setOrgId(rs.getString("GitHubOrgId"));
				LOGGER.log(Level.INFO,
						"Queried the customerinfo table to get organization id  -->" + entity.getOrgId());

			}
			statement.close();
			rs.close();
			query = "SELECT * FROM githuborg WHERE GitHubOrgId = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, entity.getOrgId());

			rs = statement.executeQuery();
			while (rs.next()) {
				orgName = rs.getString("GitHubOrgName");
			}
			rs.close();

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occurred", e);
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return orgName;
	}
	public static void main(String[] args) throws URISyntaxException, IOException {
		CustomerDAO customerDAO=new CustomerDAO();
		CustomerEntity customer=new CustomerEntity();
		customer.setName("AS Community Test");
		customerDAO.createCustomerRallyProject(customer);
	}
	public int createCustomerRallyProject(CustomerEntity customer)
			throws URISyntaxException, IOException {
		int rows=0;
		ProjectDAO dao = new ProjectDAO();
		RallyInfo rally = dao.getRallyDetails();
		// Create and configure a new instance of RallyRestApi
		RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
		restApi.setApplicationName("TestApp");

		try {
			// get rally workspace id for this customer
			String rallyWrksp = technologyDAO.getWorkSpaceObjId();

			// Create a Project
			JsonObject newProject = new JsonObject();
			newProject.addProperty("Name", customer.getName());
			newProject.addProperty("Description", "");
			newProject.addProperty("State", "Open");
			newProject.addProperty("Workspace", "/workspace/" + rallyWrksp);
			CreateRequest createRequest = new CreateRequest("Project", newProject);
			CreateResponse createResponse = restApi.create(createRequest);
			JsonObject respObj = createResponse.getObject();
			System.out.println("Response object " + respObj);
			System.out.println(String.format("Created Rally Project with customer name %s", respObj.get("_ref").getAsString()));
			// here update the response to DB
			System.out.println("Rally Project ObjectID" + respObj.get("ObjectID").getAsString());
			rows= createCustomerWithRallyObj(customer,  respObj.get("ObjectID").getAsString());
			gitHubDAO.updateGithubOrg(customer.getName(),null);
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			restApi.close();
		}
		return rows;

	}
	
	public CustomerEntity getCustomerIdFromCust(String customerName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		CustomerEntity entity = new CustomerEntity();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();
			statement = connection.prepareStatement(SELECT_CUSTOMER_ID);
			statement.setString(1, customerName);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setCustomerId(rs.getString("CustomerId"));
				// customerId = rs.getString("CustomerId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return entity;
	}
	/**
	 * @param customer
	 * @return int
	 */
	public int createCustomerWithRallyObj(CustomerEntity customer,String projectObjId) {
		int returnRow = 0;
		Statement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {
			String insertQuery = " insert into customerinfo (CustomerId,CustomerName, CustomerType, Region, EmailAddress,Contact,"
					+ "GitHubOrgId,OrgId,Internal,RallyCustObjId,WorkTypeId)"
					+ " values (uuid(),?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			PreparedStatement preparedStmt = connection.prepareStatement(insertQuery);
			preparedStmt.setString(1, customer.getName());
			preparedStmt.setString(2, null);
			preparedStmt.setString(3, customer.getRegion());
			preparedStmt.setString(4, customer.getEmailAddress());
			preparedStmt.setString(5, customer.getContact());
			preparedStmt.setString(6, null);
			preparedStmt.setString(7, getOrganisationId("Advanced Services", connection));
			preparedStmt.setBoolean(8, false); 
			preparedStmt.setLong(9, Long.parseLong(projectObjId));
			preparedStmt.setString(10, workTypeDAO.getWorkTypeId("Customer", connection));
			LOGGER.log(Level.INFO, "Table data got Inserted in customer table");
			returnRow = preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return returnRow;
	}


	

	public String getOrganisationId(String name,Connection existingConnection) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		if(existingConnection==null){
			connection = DBConnectionFactory.getDatabaseConnection();
		}
		else{
			connection=existingConnection;
		}
		String query = "SELECT * from organisationunit WHERE OrgUnitName = ? ";
		OrganisationEntity entity = new OrganisationEntity();
		String orgName = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			rs = statement.executeQuery();
			while (rs.next()) {
				entity.setOrgId(rs.getString("OrgId"));
			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occurred", e);
		} finally {
			if(existingConnection==null)
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		
		orgName = entity.getOrgId();

		return orgName;
	}
}
