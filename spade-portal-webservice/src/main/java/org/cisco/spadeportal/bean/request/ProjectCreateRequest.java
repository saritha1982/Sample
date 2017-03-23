package org.cisco.spadeportal.bean.request;

import java.sql.Date;
import java.util.List;

public class ProjectCreateRequest {
	private String projectId;
	private String contractId;
	private String clarityId;
	private String technologyName;
	private String workType;
	private String customerName;
	//private Date projectEndDate;
	private String projectEndDate;
	//private Date projectStartDate;
	private String projectStartDate;
	private List<User> users;
	private String projectName;
	private String projectRequestType;
	private String earlierProjectReference;

	private String projectManager;
	private String projectType;
	private String parentProject;
	private String notes;
	private String onBoardId;
	private String categoryName;
	private String businessUnit;
	


	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOnBoardId() {
		return onBoardId;
	}

	public void setOnBoardId(String onBoardId) {
		this.onBoardId = onBoardId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	/*public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}*/

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	
	

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getClarityId() {
		return clarityId;
	}

	public void setClarityId(String clarityId) {
		this.clarityId = clarityId;
	}


	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectRequestType() {
		return projectRequestType;
	}

	public void setProjectRequestType(String projectRequestType) {
		this.projectRequestType = projectRequestType;
	}

	public String getEarlierProjectReference() {
		return earlierProjectReference;
	}

	public void setEarlierProjectReference(String earlierProjectReference) {
		this.earlierProjectReference = earlierProjectReference;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getParentProject() {
		return parentProject;
	}

	public void setParentProject(String parentProject) {
		this.parentProject = parentProject;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
