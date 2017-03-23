package org.cisco.spadeportal.bean.entity;

/**
 * @author sarbr
 *
 */
public class TechnologyEntity {
	private String technologyName;
	private String technologyId;
	private String githubRepoName;

	public String getTechnologyId() {
		return technologyId;
	}

	public void setTechnologyId(String technologyId) {
		this.technologyId = technologyId;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	public String getGithubRepoName() {
		return githubRepoName;
	}

	public void setGithubRepoName(String githubRepoName) {
		this.githubRepoName = githubRepoName;
	}

}
