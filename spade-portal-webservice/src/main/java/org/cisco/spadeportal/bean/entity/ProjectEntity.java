package org.cisco.spadeportal.bean.entity;

import java.sql.Date;

/**
 * @author sarbr
 *
 */
public class ProjectEntity {

	private String clarityId;
	private String contractId;
	private String projectId;
	private String technologyName;
	private String  worktype;
	private String customerName;
	//private Date projectStartDate;
	private String projectEndDate;
	//private Date projectEndDate;
	private String projectStartDate;
	private String projectName;
	private String projectRequestType;
	private String earlierProjectReference;
	private String projectManager;
	private String projectType;
	private String parentProject;
	private String projectState;
	private String notes;
	private String rallyProjectObjId;
	private String businessUnit;



	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getRallyProjectObjId() {
		return rallyProjectObjId;
	}
	public void setRallyProjectObjId(String rallyProjectObjId) {
		this.rallyProjectObjId = rallyProjectObjId;
	}
	private String onboardId;

	public String getOnboardId() {
		return onboardId;
	}
	public void setOnboardId(String onboardId) {
		this.onboardId = onboardId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	public String getClarityId() {
		return clarityId;
	}
	public void setClarityId(String clarityId) {
		this.clarityId = clarityId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getTechnologyName() {
		return technologyName;
	}
	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
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

	public String getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public String getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

}
