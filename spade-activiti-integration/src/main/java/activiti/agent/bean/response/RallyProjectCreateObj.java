package activiti.agent.bean.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "_rallyAPIMajor", "_rallyAPIMinor", "_ref", "_refObjectUUID", "_objectVersion", "_refObjectName",
		"CreationDate", "_CreatedAt", "ObjectID", "ObjectUUID", "VersionId", "Description", "Name", "Notes", "owner",
		"SchemaVersion", "State", "workspace", "_type" })
public class RallyProjectCreateObj {

	@JsonProperty("_rallyAPIMajor")
	private String rallyAPIMajor;
	@JsonProperty("_rallyAPIMinor")
	private String rallyAPIMinor;
	@JsonProperty("_ref")
	private String ref;
	@JsonProperty("_refObjectUUID")
	private String refObjectUUID;
	@JsonProperty("_objectVersion")
	private String objectVersion;
	@JsonProperty("_refObjectName")
	private String refObjectName;
	@JsonProperty("CreationDate")
	private String creationDate;
	@JsonProperty("_CreatedAt")
	private String createdAt;
	@JsonProperty("ObjectID")
	private Integer objectID;
	@JsonProperty("ObjectUUID")
	private String objectUUID;
	@JsonProperty("VersionId")
	private String versionId;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Notes")
	private String notes;
	@JsonProperty("Owner")
	private RallyProjectOwner owner;
	@JsonProperty("SchemaVersion")
	private String schemaVersion;
	@JsonProperty("State")
	private String state;
	@JsonProperty("Workspace")
	private RallyWorkspace workspace;
	@JsonProperty("_type")
	private String type;
	@JsonIgnore
	private Map<String, java.lang.Object> additionalProperties = new HashMap<String, java.lang.Object>();

	@JsonProperty("_rallyAPIMajor")
	public String getRallyAPIMajor() {
		return rallyAPIMajor;
	}

	@JsonProperty("_rallyAPIMajor")
	public void setRallyAPIMajor(String rallyAPIMajor) {
		this.rallyAPIMajor = rallyAPIMajor;
	}

	@JsonProperty("_rallyAPIMinor")
	public String getRallyAPIMinor() {
		return rallyAPIMinor;
	}

	@JsonProperty("_rallyAPIMinor")
	public void setRallyAPIMinor(String rallyAPIMinor) {
		this.rallyAPIMinor = rallyAPIMinor;
	}

	@JsonProperty("_ref")
	public String getRef() {
		return ref;
	}

	@JsonProperty("_ref")
	public void setRef(String ref) {
		this.ref = ref;
	}

	@JsonProperty("_refObjectUUID")
	public String getRefObjectUUID() {
		return refObjectUUID;
	}

	@JsonProperty("_refObjectUUID")
	public void setRefObjectUUID(String refObjectUUID) {
		this.refObjectUUID = refObjectUUID;
	}

	@JsonProperty("_objectVersion")
	public String getObjectVersion() {
		return objectVersion;
	}

	@JsonProperty("_objectVersion")
	public void setObjectVersion(String objectVersion) {
		this.objectVersion = objectVersion;
	}

	@JsonProperty("_refObjectName")
	public String getRefObjectName() {
		return refObjectName;
	}

	@JsonProperty("_refObjectName")
	public void setRefObjectName(String refObjectName) {
		this.refObjectName = refObjectName;
	}

	@JsonProperty("CreationDate")
	public String getCreationDate() {
		return creationDate;
	}

	@JsonProperty("CreationDate")
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	@JsonProperty("_CreatedAt")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("_CreatedAt")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("ObjectID")
	public Integer getObjectID() {
		return objectID;
	}

	@JsonProperty("ObjectID")
	public void setObjectID(Integer objectID) {
		this.objectID = objectID;
	}

	@JsonProperty("ObjectUUID")
	public String getObjectUUID() {
		return objectUUID;
	}

	@JsonProperty("ObjectUUID")
	public void setObjectUUID(String objectUUID) {
		this.objectUUID = objectUUID;
	}

	@JsonProperty("VersionId")
	public String getVersionId() {
		return versionId;
	}

	@JsonProperty("VersionId")
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	@JsonProperty("Description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("Description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("Name")
	public String getName() {
		return name;
	}

	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("Notes")
	public String getNotes() {
		return notes;
	}

	@JsonProperty("Notes")
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@JsonProperty("Owner")
	public RallyProjectOwner getOwner() {
		return owner;
	}

	@JsonProperty("Owner")
	public void setOwner(RallyProjectOwner owner) {
		this.owner = owner;
	}

	@JsonProperty("SchemaVersion")
	public String getSchemaVersion() {
		return schemaVersion;
	}

	@JsonProperty("SchemaVersion")
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	@JsonProperty("State")
	public String getState() {
		return state;
	}

	@JsonProperty("State")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("Workspace")
	public RallyWorkspace getWorkspace() {
		return workspace;
	}

	@JsonProperty("Workspace")
	public void setWorkspace(RallyWorkspace workspace) {
		this.workspace = workspace;
	}

	@JsonProperty("_type")
	public String getType() {
		return type;
	}

	@JsonProperty("_type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonAnyGetter
	public Map<String, java.lang.Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, java.lang.Object value) {
		this.additionalProperties.put(name, value);
	}

}