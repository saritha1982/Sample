/**
 * 
 */
package activiti.agent.bean;

/**
 * @author sarbr
 *
 */
public class CustomerInfo {
	private String customerName;
	private boolean internal;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public boolean isInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
	}

}
