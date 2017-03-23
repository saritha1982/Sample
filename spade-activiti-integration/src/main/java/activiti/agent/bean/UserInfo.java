package activiti.agent.bean;

public class UserInfo {

	private String userName;
	private String email;
	private String projectOnBoardId;
	private String acessLevel;
	private boolean disabled;
	private long rallyObjId;
	private String custId;
	private String projectRoleId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProjectOnBoardId() {
		return projectOnBoardId;
	}

	public void setProjectOnBoardId(String projectOnBoardId) {
		this.projectOnBoardId = projectOnBoardId;
	}

	public String getAcessLevel() {
		return acessLevel;
	}

	public void setAcessLevel(String acessLevel) {
		this.acessLevel = acessLevel;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public long getRallyObjId() {
		return rallyObjId;
	}

	public void setRallyObjId(long rallyObjId) {
		this.rallyObjId = rallyObjId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getProjectRoleId() {
		return projectRoleId;
	}

	public void setProjectRoleId(String projectRoleId) {
		this.projectRoleId = projectRoleId;
	}

}
