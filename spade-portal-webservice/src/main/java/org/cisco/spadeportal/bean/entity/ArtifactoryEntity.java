package org.cisco.spadeportal.bean.entity;

/**
 * @author sarbr
 *
 */
public class ArtifactoryEntity {
	private String userName;
	private String password;
	private String url;
	private String artifactoryId;
	public String getArtifactoryId() {
		return artifactoryId;
	}
	public void setArtifactoryId(String artifactoryId) {
		this.artifactoryId = artifactoryId;
	}
	private String name;
	private String orgId;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
