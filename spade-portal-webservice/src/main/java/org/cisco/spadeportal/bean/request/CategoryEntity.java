/**
 * 
 */
package org.cisco.spadeportal.bean.request;

/**
 * @author sarbr
 *
 */
public class CategoryEntity {

	private String categoryName;
	private String technologyName;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getTechnologyName() {
		return technologyName;
	}
	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}
}
