package org.cisco.spadeportal.bean.entity;

/**
 * @author sarbr
 *
 */
public class SonarQubeEntity {
	private String userName;
	private String password;
	private String url;
	private String sonarId;
	public String getSonarId() {
		return sonarId;
	}
	public void setSonarId(String sonarId) {
		this.sonarId = sonarId;
	}
	private String name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String orgId;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
