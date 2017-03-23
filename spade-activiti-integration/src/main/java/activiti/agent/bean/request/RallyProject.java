package activiti.agent.bean.request;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Name", "Workspace", "State", "Parent" })
public class RallyProject {

	@JsonProperty("Name")
	private String name;
	@JsonProperty("Workspace")
	private String workspace;
	@JsonProperty("State")
	private String state;
	@JsonProperty("Parent")
	private String parent;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("Name")
	public String getName() {
		return name;
	}

	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("Workspace")
	public String getWorkspace() {
		return workspace;
	}

	@JsonProperty("Workspace")
	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	@JsonProperty("State")
	public String getState() {
		return state;
	}

	@JsonProperty("State")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("Parent")
	public String getParent() {
		return parent;
	}

	@JsonProperty("Parent")
	public void setParent(String parent) {
		this.parent = parent;
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
