package org.cisco.spadeportal.db;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cisco.spadeportal.bean.activiti.RallyInfo;
import org.cisco.spadeportal.bean.entity.CustomerEntity;
import org.cisco.spadeportal.bean.entity.ProjectEntity;
import org.cisco.spadeportal.bean.entity.UserEntity;
import org.cisco.spadeportal.bean.request.ProjectCreateRequest;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.UpdateResponse;

/**
 * @author sarbr
 *
 */
public class ProjectDAO {
	private static final Object LOCK = new Object();
	private static final Logger LOGGER = Logger.getLogger(ProjectDAO.class.getName());
	CustomerDAO customerDAO = new CustomerDAO();
	TechnologyDAO technologyDAO = new TechnologyDAO();
	WorkTypeDAO workTypeDAO = new WorkTypeDAO();
	ProjectRequestTypeDAO projectRequestTypeDAO = new ProjectRequestTypeDAO();
	ProjectTypeDAO projectTypeDAO = new ProjectTypeDAO();
	ProjectStateDAO projectStateDAO = new ProjectStateDAO();
	RallyDAO rallyDAO = new RallyDAO();
	JenkinsDAO jenkinsDAO = new JenkinsDAO();
	SonarQubeDAO sonarQubeDAO = new SonarQubeDAO();
	GitHubDAO gitHubDAO = new GitHubDAO();
	OrganisationDAO organisationDAO = new OrganisationDAO();
	ArtifactoryDAO artifactoryDAO = new ArtifactoryDAO();
	private static final String SELECT_CUSTOMERID = "SELECT CustomerId FROM projectonboard WHERE OnBoardId = ? ";

	/**
	 * @param customerId,technologyId
	 * @return list
	 */
	public List<ProjectEntity> getEarlierProjectReferencesFromDB(String customerId, String technologyName,
			String projects) {
		System.out.println("inside getProjects" + projects);
		List<ProjectEntity> projectNameList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String selectSQL = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			if (!projects.equals("parentProjects")) {
				selectSQL = "SELECT ProjectName FROM projectonboard where CustomerId = ? and TechnologyId = ? ";
			} else {

				// Returns earlier Project Reference List
				selectSQL = "SELECT ProjectName FROM projectonboard where CustomerId = ? and TechnologyId = ? and (ParentProject ='' OR ParentProject IS NULL)";

			}
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			statement.setString(2,
					technologyDAO.getTechnologyId(customerId, technologyName, connection).getTechnologyId());

			rs = statement.executeQuery();
			projectNameList = new ArrayList<ProjectEntity>();
			while (rs.next()) {
				ProjectEntity projectOnBoard = new ProjectEntity();
				projectOnBoard.setEarlierProjectReference(rs.getString("ProjectName"));
				projectNameList.add(projectOnBoard);
			}
			LOGGER.log(Level.INFO,
					" Queried the table projectonBoard to get earlier project references from projectonboard table"
							+ projectNameList.size());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectNameList;
	}

	/**
	 * @param customerId,technologyId
	 * @return list
	 */
	public List<UserEntity> getManagersFromDB(String customerId, String technologyName) {
		List<UserEntity> managerNameList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {
			String selectSQL = "SELECT Name,EmailAddress,Contact FROM user where UserId IN (select UserId from groupmember where GroupId IN (select GroupId from technology where CustomerId = ? and technologyId = ?))";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, customerId);
			statement.setString(2,
					technologyDAO.getTechnologyId(customerId, technologyName, connection).getTechnologyId());
			rs = statement.executeQuery();
			managerNameList = new ArrayList<UserEntity>();
			while (rs.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setUserName(rs.getString("Name"));
				userEntity.setEmailAddress(rs.getString("EmailAddress"));
				userEntity.setContact(rs.getString("Contact"));
				managerNameList.add(userEntity);
			}
			LOGGER.log(Level.INFO,
					" Queried the table to get managerName from user table--> " + managerNameList.size());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return managerNameList;
	}

	/**
	 * @param projects
	 * @param customerId,technologyId
	 * @return list
	 */
	public List<ProjectEntity> getParentProjectsFromDB(String customerId, String technologyName, String projects) {

		return getEarlierProjectReferencesFromDB(customerId, technologyName, projects);
	}


	/**
	 * @param projectOnBoardData
	 * @return row affected
	 */

	public int onboardProject(ProjectCreateRequest projectOnBoardData, String customerId) {

		int returnRow = 0;
		ResultSet rs = null;
		PreparedStatement preparedStmt = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		java.sql.Date sqlstartDate = null;
		java.sql.Date sqlenddate=  null;
		
		try {
			if(projectOnBoardData.getProjectStartDate()!=null && projectOnBoardData.getProjectStartDate().length() > 0)
			sqlstartDate = convertStringToSqlDate(projectOnBoardData.getProjectStartDate());
			if(projectOnBoardData.getProjectEndDate()!=null && projectOnBoardData.getProjectEndDate().length() > 0)
			sqlenddate = convertStringToSqlDate(projectOnBoardData.getProjectEndDate());
			String insertQuery = " insert into projectonboard (ProjectId, TechnologyId , WorkTypeId ,"
					+ " CustomerId , ProjectStartDate , ProjectEndDate ,"
					+ " ProjectName,ProjectRequestTypeId,EarlierProjectReference,"
					+ "ProjectManager,ParentProject,ProjectTypeId," + "ClarityId,ContractId,RallyId,"
					+ "JenkinsId,GitHubId,ArtifactoryId," + "SonarId,ProjectStateId,Notes,OnBoardId,BusinessUnit)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,?)";
			preparedStmt = connection.prepareStatement(insertQuery);
			preparedStmt.setString(1, projectOnBoardData.getProjectId());
			preparedStmt.setString(2, technologyDAO
					.getTechnologyId(customerId, projectOnBoardData.getTechnologyName(), connection).getTechnologyId());
			preparedStmt.setString(3,
					workTypeDAO.getWorkTypeId(projectOnBoardData.getWorkType(), connection));
			preparedStmt.setString(4, customerId);
			preparedStmt.setDate(5, sqlstartDate);
			preparedStmt.setDate(6, sqlenddate);
			preparedStmt.setString(7, projectOnBoardData.getProjectName());
			preparedStmt.setString(8,
					projectRequestTypeDAO
							.getProjectRequestTypeId(projectOnBoardData.getProjectRequestType(), connection)
							.getProjectRequestTypeId());
			preparedStmt.setString(9, projectOnBoardData.getEarlierProjectReference());
			preparedStmt.setString(10, projectOnBoardData.getProjectManager());
			preparedStmt.setString(11, projectOnBoardData.getParentProject());

			preparedStmt.setString(12, projectTypeDAO
					.getProjectTypeId(projectOnBoardData.getProjectType(), connection).getProjectTypeId());
			preparedStmt.setString(13, projectOnBoardData.getClarityId());
			preparedStmt.setString(14, projectOnBoardData.getContractId());
			preparedStmt.setString(15, rallyDAO.getRallyId(customerId, connection).getRallyId());
			preparedStmt.setString(16, jenkinsDAO.getJenkinsId(customerId, connection).getJenkinsId());
			preparedStmt.setString(17, gitHubDAO.getGitHubId(customerId, connection).getGithubId());
			preparedStmt.setString(18, artifactoryDAO.getArtifactoryId(customerId, connection).getArtifactoryId());
			preparedStmt.setString(19, sonarQubeDAO.getSonarQubeId(customerId, connection).getSonarId());
			preparedStmt.setString(20,
					projectStateDAO.getProjectStateId(customerId, "Defined", connection).getProjectStateId());
			preparedStmt.setString(21, projectOnBoardData.getNotes());
			preparedStmt.setString(22, projectOnBoardData.getOnBoardId());
			preparedStmt.setString(23, projectOnBoardData.getBusinessUnit());
			returnRow = preparedStmt.executeUpdate();
			LOGGER.log(Level.INFO, "Table data got Inserted into project onboard table");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(preparedStmt, rs, connection);
		}
		return returnRow;
	}

	private java.sql.Date convertStringToSqlDate(String projectdate) throws ParseException {
		// String startDate = projectOnBoardData.getProjectStartDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = simpleDateFormat.parse(projectdate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	/**
	 * @param customerId,technologyId
	 * @return list
	 * @throws InterruptedException
	 */
	public List<ProjectEntity> getProjectListFromDB(String customerId) throws InterruptedException {
		List<ProjectEntity> projectList = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		CustomerEntity customerEntity = null;
		try {
//			customerEntity = customerDAO.getCustomerName(customerId, connection);
			//String selectSQL = "SELECT TechnologyId,OnBoardId,ProjectName,ProjectManager,ProjectTypeId FROM projectonboard where CustomerId = ? ";
			String selectSQL = "SELECT CustomerId,TechnologyId,OnBoardId,ProjectName,ProjectManager,ProjectTypeId FROM projectonboard ";
			statement = connection.prepareStatement(selectSQL);
			//statement.setString(1, customerId);
			rs = statement.executeQuery();
			projectList = new ArrayList<ProjectEntity>();
			while (rs.next()) {
				ProjectEntity projectOnBoard = new ProjectEntity();
				projectOnBoard.setTechnologyName(technologyDAO
						.getTechnologyFromDB(customerId, rs.getString("TechnologyId"), connection).getTechnologyName());
				projectOnBoard.setOnboardId(rs.getString("OnBoardId"));
				projectOnBoard.setProjectName(rs.getString("ProjectName"));
				projectOnBoard.setProjectManager(rs.getString("ProjectManager"));
				projectOnBoard.setProjectType(projectTypeDAO
						.getProjectTypeName(rs.getString("ProjectTypeId"), connection).getName());
				projectOnBoard.setCustomerName(customerDAO.getCustomerName(rs.getString("CustomerId"), connection).getName());
				projectList.add(projectOnBoard);
			}
			LOGGER.log(Level.INFO, " Queried the table projectonBoard to get projectList");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return projectList;
	}
	
	/**
	 * @param customerId,technologyId
	 * @return list
	 */
	public ProjectEntity getProjectDetailFromDB(String customerId, String onboardId) {
		ProjectEntity projectOnBoard = new ProjectEntity();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "SELECT OnBoardId,ProjectId,TechnologyId,WorkTypeId,CustomerId,ProjectStartDate,ProjectEndDate,ProjectName,ProjectRequestTypeId,"
					+ "EarlierProjectReference,ProjectManager,ParentProject,ProjectTypeId,ClarityId,ContractId,RallyProjectObjId,BusinessUnit,Notes FROM projectonboard where OnBoardId = ? ";
			statement = connection.prepareStatement(selectSQL);
//			statement.setString(1, customerId);
			statement.setString(1, onboardId);
			rs = statement.executeQuery();
			while (rs.next()) {

				projectOnBoard.setOnboardId(rs.getString("OnBoardId"));
				projectOnBoard.setProjectId(rs.getString("ProjectId"));
				projectOnBoard.setTechnologyName(technologyDAO
						.getTechnologyFromDB(customerId, rs.getString("TechnologyId"), connection).getTechnologyName());
				projectOnBoard.setWorktype(workTypeDAO
						.getWorkTypeDetailFromDB(rs.getString("WorkTypeId"), connection).getWorkTypeName());
				projectOnBoard.setCustomerName(customerDAO.getCustomerName(customerId, connection).getName());
				projectOnBoard.setProjectStartDate(rs.getString("ProjectStartDate"));
				projectOnBoard.setProjectEndDate(rs.getString("ProjectEndDate"));
				projectOnBoard.setProjectName(rs.getString("ProjectName"));
				projectOnBoard.setProjectRequestType(projectRequestTypeDAO
						.getProjectRequestTypeName(rs.getString("ProjectRequestTypeId"), connection)
						.getName());
				projectOnBoard.setEarlierProjectReference(rs.getString("EarlierProjectReference"));
				projectOnBoard.setProjectManager(rs.getString("ProjectManager"));
				projectOnBoard.setParentProject(rs.getString("ParentProject"));
				projectOnBoard.setProjectType(projectTypeDAO
						.getProjectTypeName(rs.getString("ProjectTypeId"), connection).getName());
				projectOnBoard.setClarityId(rs.getString("ClarityId"));
				projectOnBoard.setContractId(rs.getString("ContractId"));
				projectOnBoard.setRallyProjectObjId((rs.getString("RallyProjectObjId")));
				projectOnBoard.setBusinessUnit(rs.getString("BusinessUnit"));
				projectOnBoard.setNotes(rs.getString("Notes"));
			}
			LOGGER.log(Level.INFO, " Queried the table projectonBoard to get projectDetail");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectOnBoard;
	}

	/**
	 * @param activitProcessInstanceId
	 * @param onboardId
	 */
	public void updateActivitiProcessInstanceId(String activitProcessInstanceId, String onboardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		try {

			String selectSQL = "UPDATE projectonboard SET ActivitiPIID = ? where OnBoardId = ?";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, activitProcessInstanceId);
			statement.setString(2, onboardId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
	}

	public static void main(String[] args) throws ParseException, InterruptedException {
		// String startDate = "2017-01-10";
		ProjectDAO pdao = new ProjectDAO();

		// pdao.getProjectDetailFromDB("f9946b4f-e87b-11e6-883c-d8cb8a429f06",
		// "Project1").getProjectType());
		// pdao.getEarlierProjectReferencesFromDB("9d83523d-fcc4-11e6-95b3-507b9dc361f2",
		// "ACI", ""));

		// System.out.println(pdao.checkProjectIdExists("NSO-DEMO-local-10"));
		/*
		 * ProjectCreateRequest pc = new ProjectCreateRequest();
		 * pc.setOnBoardId("March-Jenkins2-Test1");
		 * pc.setProjectName("March-Jenkins2-Test1");
		 * pc.setProjectRequestType("New Request");
		 * pc.setProjectType("AS Fixed"); pc.setTechnologyName("NSO");
		 * pc.setWorkType("Internal Initiative");
		 * pc.setProjectManager("as-ci-user.gen@cisco.com");
		 * pc.setCustomerName("AS Community");
		 * pc.setProjectStartDate("2017-03-16");
		 * pc.setProjectEndDate("2018-03-16"); pdao.onboardProject(pc,
		 * "9d83523d-fcc4-11e6-95b3-507b9dc361f2");
		 */
		//dao.getProjectListFromDB("9d83523d-fcc4-11e6-95b3-507b9dc361f2");
		ProjectCreateRequest pc=new ProjectCreateRequest();
		pc.setWorkType("Internal");
		pc.setProjectRequestType("Change Request");
		pc.setProjectType("AS Transactional");
		pc.setNotes("Edited twice");
		pc.setBusinessUnit("Edited");
		pc.setProjectName("1515Project");
		System.out.println(pdao.updateProjectOnboardTable("693b60ea-07b4-11e7-95b3-507b9dc361f2", pc, "1515"));

	}
	/**
	 * @param custId
	 * @param projectOnBoardEditData
	 * @return
	 * @throws ParseException
	 */
	public int updateProjectOnboardTable(String custId, ProjectCreateRequest projectOnBoardEditData, String onboardId)
			throws ParseException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		int returnRow = 0;
		Date startDate=null;
		Date endDate=null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		if(projectOnBoardEditData.getProjectStartDate()!=null){
			startDate=convertStringToSqlDate(projectOnBoardEditData.getProjectStartDate());
		}
		if(projectOnBoardEditData.getProjectEndDate()!=null){
			endDate=convertStringToSqlDate(projectOnBoardEditData.getProjectEndDate());
		}
		
		try {

				String selectSQL = "UPDATE projectonboard SET WorkTypeId = ? , ProjectStartDate = ? , ProjectEndDate = ? ,"
						+ "ProjectRequestTypeId = ? , EarlierProjectReference = ? , "
						+ "ProjectManager = ? , ProjectTypeId = ? , ClarityId = ? , ContractId = ? , BusinessUnit = ? ,Notes = ? where ProjectName = ?";
				statement = connection.prepareStatement(selectSQL);
				statement.setString(1, workTypeDAO.getWorkTypeId(projectOnBoardEditData.getWorkType(),connection));
				statement.setDate(2, startDate);
				statement.setDate(3, endDate);
				statement.setString(4,
						projectRequestTypeDAO
								.getProjectRequestTypeId(projectOnBoardEditData.getProjectRequestType(), connection)
								.getProjectRequestTypeId());
				statement.setString(5, projectOnBoardEditData.getEarlierProjectReference());
				statement.setString(6, projectOnBoardEditData.getProjectManager());
				statement.setString(7, projectTypeDAO.getProjectTypeId(projectOnBoardEditData.getProjectType(),connection)
						.getProjectTypeId());
				statement.setString(8, projectOnBoardEditData.getClarityId());
				statement.setString(9, projectOnBoardEditData.getContractId());
				statement.setString(10, projectOnBoardEditData.getBusinessUnit());
				statement.setString(11, projectOnBoardEditData.getNotes());
				statement.setString(12, projectOnBoardEditData.getProjectName());
				returnRow = statement.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}

		return returnRow;
	}

	/**
	 * @param onboardId
	 * @return boolean
	 */
	public boolean checkProjectIdExists(String onboardId) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection connection = DBConnectionFactory.getDatabaseConnection();
		int count = 0;
		try {
			String selectSQL = "SELECT count(*) FROM projectonboard where OnBoardId = ?";
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, onboardId);
			rs = preparedStatement.executeQuery();
			if (rs.next())
				count = rs.getInt(1);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(preparedStatement, rs, connection);
		}
		return count > 0;
	}

	/**
	 * @param onBoardId
	 * @return
	 */
	public String getCustomerId(String onBoardId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		String projectName = "";
		Connection connection = null;
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = SELECT_CUSTOMERID;
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, onBoardId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				projectName = rs.getString("CustomerId");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return projectName;
	}

	/**
	 * @param custId
	 * @return
	 */
	public RallyInfo getRallyDetails(String custId) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		RallyInfo rally = new RallyInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT UserName, Password, URL FROM rally ra, customerinfo cu WHERE cu.CustomerId = ? AND ra.OrgId = cu.OrgId";
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, custId);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rally.setUserName(rs.getString("UserName"));
				rally.setApiKey(rs.getString("Password"));
				rally.setUrl(rs.getString("URL"));

			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		if (rally != null) {
			System.out.println("UserName in Rally table" + rally.getUserName());
		}
		return rally;
	}

	/**
	 * @param custId
	 * @param onboardId
	 */
	public void closeRallyProject(String custId, String onboardId) {
		RallyInfo rally = getRallyDetails(custId);
		ProjectEntity projectEntity = null;
		try {
			RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
			restApi.setApplicationName("TestApp");
			JsonObject newProject = new JsonObject();
			newProject.addProperty("State", "Closed");
			projectEntity = getProjectDetailFromDB(custId, onboardId);
			UpdateRequest updateRequest = new UpdateRequest("/project/" + projectEntity.getRallyProjectObjId(),
					newProject);
			UpdateResponse updateResponse = restApi.update(updateRequest);
			if (updateResponse.wasSuccessful()) {
				System.out.println("Rally Project Updated successfully ");

			} else {
				String[] errorList;
				errorList = updateResponse.getErrors();
				for (int i = 0; i < errorList.length; i++) {
					System.out.println("Error list  " + errorList[i]);
				}
			}
			restApi.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @param custId
	 * @return
	 */
	public RallyInfo getRallyDetails() {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		RallyInfo rally = new RallyInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT UserName, Password, URL FROM rally ";
			statement = connection.prepareStatement(selectSQL);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rally.setUserName(rs.getString("UserName"));
				rally.setApiKey(rs.getString("Password"));
				rally.setUrl(rs.getString("URL"));

			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		if (rally != null) {
			System.out.println("UserName in Rally table" + rally.getUserName());
		}
		return rally;
	}

	public RallyInfo getRallyInfo() {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		RallyInfo rally = new RallyInfo();
		try {
			connection = DBConnectionFactory.getDatabaseConnection();

			String selectSQL = "SELECT UserName, Password, URL FROM rally";
			statement = connection.prepareStatement(selectSQL);
			statement.execute();
			rs = statement.getResultSet();
			while (rs.next()) {
				rally.setUserName(rs.getString("UserName"));
				rally.setApiKey(rs.getString("Password"));
				rally.setUrl(rs.getString("URL"));

			}

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Exception occured", e);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.releaseConnection(statement, rs, connection);
		}
		return rally;
	}

}
