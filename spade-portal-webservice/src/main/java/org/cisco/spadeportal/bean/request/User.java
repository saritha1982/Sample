package org.cisco.spadeportal.bean.request;

import java.util.List;

public class User {
	private String userName;
	private String emailAddress;
	private boolean disabled;
	private String workspace;
	private String accessLevel;
	private String projectRole;
	private String customerId;
	private String onBoardId;
	private String userObjectId;
	public String getUserObjectId() {
		return userObjectId;
	}
	public void setUserObjectId(String userObjectId) {
		this.userObjectId = userObjectId;
	}
	private List<Project> projects;


	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public String getOnBoardId() {
		return onBoardId;
	}
	public void setOnBoardId(String onBoardId) {
		this.onBoardId = onBoardId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public String getWorkspace() {
		return workspace;
	}
	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	public String getProjectRole() {
		return projectRole;
	}
	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
