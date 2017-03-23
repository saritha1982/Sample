package org.cisco.spadeportal.bean.entity;

/**
 * @author sarbr
 *
 */
public class RallyEntity {
	private String userName;
	private String password;
	private String url;
	private int version;
	private String rallyId;
	private String orgId;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getRallyId() {
		return rallyId;
	}
	public void setRallyId(String rallyId) {
		this.rallyId = rallyId;
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
