package org.cisco.spadeportal.bean.entity;

import java.util.List;

/**
 * @author sarbr
 *
 */
public class UserEntity {

	private String userName;
	private String emailAddress;
	private boolean disabled;
	private String workspace;
	private String accessLevel;
	private String projectRole;
	private String customerId;
	private String contact;
	private String userId;
	private String onBoardId;
	private List<ProjectEntity> projectList;
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public List<ProjectEntity> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<ProjectEntity> projectList) {
		this.projectList = projectList;
	}
	public String getOnBoardId() {
		return onBoardId;
	}
	public void setOnBoardId(String onBoardId) {
		this.onBoardId = onBoardId;
	}
	
}
