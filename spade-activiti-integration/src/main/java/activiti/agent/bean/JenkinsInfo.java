package activiti.agent.bean;

public class JenkinsInfo {
	private String userName;
	private String url;
	private String password;
	private String jenkinsId;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
