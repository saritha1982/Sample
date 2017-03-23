/**
 * 
 */
package org.cisco.spadeportal.bean.request;

/**
 * @author sarbr
 *
 */
public class Project {
	private String projectName;
	private String onBoardId;
	private String projectRole;

	public String getOnBoardId() {
		return onBoardId;
	}

	public void setOnBoardId(String onBoardId) {
		this.onBoardId = onBoardId;
	}

	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
