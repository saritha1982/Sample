package org.cisco.spadeportal.bean.entity;

/**
 * @author sarbr
 *
 */
public class GitHubEntity {
	private String userName;
	private String password;
	private String url;
	private String githubId;
	private String orgId;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getGithubId() {
		return githubId;
	}
	public void setGithubId(String githubId) {
		this.githubId = githubId;
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
	 
}
