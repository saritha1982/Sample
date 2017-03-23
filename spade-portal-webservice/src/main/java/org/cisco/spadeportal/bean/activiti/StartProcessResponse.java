package org.cisco.spadeportal.bean.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "url", "businessKey", "suspended", "ended", "processDefinitionId", "processDefinitionUrl",
		"activityId", "variables", "tenantId", "completed" })
public class StartProcessResponse {

	@JsonProperty("id")
	private String id;
	@JsonProperty("url")
	private String url;
	@JsonProperty("businessKey")
	private String businessKey;
	@JsonProperty("suspended")
	private Boolean suspended;
	@JsonProperty("ended")
	private Boolean ended;
	@JsonProperty("processDefinitionId")
	private String processDefinitionId;
	@JsonProperty("processDefinitionUrl")
	private String processDefinitionUrl;
	@JsonProperty("activityId")
	private String activityId;
	@JsonProperty("variables")
	private List<Object> variables = null;
	@JsonProperty("tenantId")
	private String tenantId;
	@JsonProperty("completed")
	private Boolean completed;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("businessKey")
	public String getBusinessKey() {
		return businessKey;
	}

	@JsonProperty("businessKey")
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@JsonProperty("suspended")
	public Boolean getSuspended() {
		return suspended;
	}

	@JsonProperty("suspended")
	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	@JsonProperty("ended")
	public Boolean getEnded() {
		return ended;
	}

	@JsonProperty("ended")
	public void setEnded(Boolean ended) {
		this.ended = ended;
	}

	@JsonProperty("processDefinitionId")
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	@JsonProperty("processDefinitionId")
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@JsonProperty("processDefinitionUrl")
	public String getProcessDefinitionUrl() {
		return processDefinitionUrl;
	}

	@JsonProperty("processDefinitionUrl")
	public void setProcessDefinitionUrl(String processDefinitionUrl) {
		this.processDefinitionUrl = processDefinitionUrl;
	}

	@JsonProperty("activityId")
	public String getActivityId() {
		return activityId;
	}

	@JsonProperty("activityId")
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@JsonProperty("variables")
	public List<Object> getVariables() {
		return variables;
	}

	@JsonProperty("variables")
	public void setVariables(List<Object> variables) {
		this.variables = variables;
	}

	@JsonProperty("tenantId")
	public String getTenantId() {
		return tenantId;
	}

	@JsonProperty("tenantId")
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@JsonProperty("completed")
	public Boolean getCompleted() {
		return completed;
	}

	@JsonProperty("completed")
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
