package org.cisco.spadeportal.bean.entity;

/**
 * @author sarbr
 *
 */
public class JenkinsEntity {
	private String userName;
	private String password;
	private String url;
	private int instanceName;
	private String jenkinsId;
	private String orgId;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getJenkinsId() {
		return jenkinsId;
	}
	public void setJenkinsId(String jenkinsId) {
		this.jenkinsId = jenkinsId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(int instanceName) {
		this.instanceName = instanceName;
	}
	
}
