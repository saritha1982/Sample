package activiti.agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.DeleteRequest;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.DeleteResponse;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.response.UpdateResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.Ref;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import activiti.agent.bean.RallyInfo;
import activiti.agent.db.RallyAgentDAO;

public class SampleRally {

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws URISyntaxException, IOException {

		//		getUsers();
		//		getRallyUserObjectId();
		//		getAllUsers();
//		getProperty();
		removeProject();
	}

	/**
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private static void getUsers() throws URISyntaxException, IOException {
		// Create and configure a new instance of RallyRestApi
		String myRallyURL = "https://rally1.rallydev.com";

		//		String wsapiVersion = "1.43";
		// RallyRestApi restApi = new RallyRestApi(new URI(myRallyURL),
		// myRallyUser, myRallyPassword);
		RallyRestApi restApi = new RallyRestApi(new URI(myRallyURL), "_5SjK7v6WT72boVr2CmUZhhc6jgTt0JZlDUzreNgf5Y");
		// restApi.setWsapiVersion(wsapiVersion);
		System.out.println(restApi.getWsapiVersion());

		try {

			System.out.println("Creating User...");
			JsonObject newUser = new JsonObject();
			newUser.addProperty("UserName", "smalaviy@cisco.com");
			newUser.addProperty("EmailAddress", "smalaviy@cisco.com");

			CreateRequest createRequest = new CreateRequest("User", newUser);
			CreateResponse createResponse = restApi.create(createRequest);

			if (createResponse.wasSuccessful()) {
				System.out.println(
						String.format("Created User %s", createResponse.getObject().get("_ref").getAsString()));

				String ref = Ref.getRelativeRef(createResponse.getObject().get("_ref").getAsString());
				System.out.println(String.format("\nReading User %s...", ref));
				GetRequest getRequest = new GetRequest(ref);
				GetResponse getResponse = restApi.get(getRequest);
				JsonObject obj = getResponse.getObject();
				System.out.println(String.format("Read User. Name = %s, State = %s", obj.get("UserName").getAsString(),
						obj.get("EmailAddress").getAsString()));

			} else {

				GetRequest getRequest = new GetRequest("Users");
				Fetch fetch=getRequest.getFetch();
				System.out.println("fetch "+fetch.size());

				getAllUsers();

				// Read Subscription
				/*QueryRequest subscriptionRequest = new QueryRequest("Users");
				subscriptionRequest.setFetch(new Fetch("Name", "EmailAddress"));
				QueryResponse subscriptionQueryResponse = restApi.query(subscriptionRequest);
				JsonArray subscriptionQueryResults = subscriptionQueryResponse.getResults();
				System.out.println("Size"+subscriptionQueryResults.size());
				for (int i =0;i<subscriptionQueryResults.size();i++){
					JsonElement subscriptionQueryElement = subscriptionQueryResults.get(i);
					JsonObject subscriptionQueryObject = subscriptionQueryElement.getAsJsonObject();
					System.out.println(subscriptionQueryObject.get("EmailAddress").getAsString());
				}*/
				//				String subID = subscriptionQueryObject.get("Name").toString();

				//			System.out.println("Read Subscription: " + subID);
				//			JsonArray myWorkspaces = subscriptionQueryObject.get("Projects").getAsJsonArray();

			}

		} finally {
			// Release all resources
			restApi.close();
		}
	}

	/**
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * 
	 */
	public static void getAllUsers() throws IOException, URISyntaxException {
		String host = "https://rally1.rallydev.com";
		String apiKey = "_5SjK7v6WT72boVr2CmUZhhc6jgTt0JZlDUzreNgf5Y";
		String applicationName = "get all users";

		RallyRestApi restApi = null;
		try {
			restApi = new RallyRestApi(new URI(host),apiKey);
			QueryRequest request = new QueryRequest("Users");
			restApi.setApplicationName("TestApp");  
			request.setFetch(new Fetch(new String[] {"UserName"}));
			request.setLimit(65870);
			//request.setQueryFilter(((new QueryFilter("CreationDate", "<", "2014-01-01").and(new QueryFilter("LastLoginDate","=",null))).or(new QueryFilter("LastLoginDate", "<", "2014-11-01"))).and(new QueryFilter("Disabled","=","false")));

			QueryResponse response = restApi.query(request);
			System.out.println("TotalResultCount: " + response.getTotalResultCount());
			if(response.wasSuccessful()){
				int totalCount=response.getTotalResultCount()/response.getPageSize();
				File myFile = new File("C:\\Users\\sarbr\\activiti_workspace\\spade workspace\\spade-activiti-integration\\MyTestFile.txt");
				// check if file exist, otherwise create the file before writing
				if (!myFile.exists()) {
					myFile.createNewFile();
				}
				BufferedWriter bufferedWriter = null;

				Writer writer = new FileWriter(myFile);
				bufferedWriter = new BufferedWriter(writer);
				for (int i=0; i<response.getTotalResultCount();i++){
					//					System.out.println("Start "+response.getStart());

					//					JsonObject userJsonObject = response.getResults().get(i).getAsJsonObject();
					//					System.out.println("Name: " + userJsonObject.get("UserName"));
					//					for(int j=0;j<response.getPageSize();j++){

					//					System.out.println("response "+response.getResults().get(i).getAsJsonObject().get("UserName")+"  count is "+response.getResults().get(i).getAsJsonObject().get("_ref")+"\n");
					bufferedWriter.write(response.getResults().get(i).getAsJsonObject().get("UserName")+"="+response.getResults().get(i).getAsJsonObject().get("_ref")+"\n");


					//					}
				}

			}


		} finally {
			if (restApi != null) {
				restApi.close();
			}

		}
	}

	public  static void getProperty() throws IOException
	{
		Properties prop = new Properties();
		InputStream input = new FileInputStream("C:\\Users\\sarbr\\activiti_workspace\\spade workspace\\spade-activiti-integration\\MyTestFile.properties");
		prop.load(input);
		System.out.println(StringUtils.substringAfterLast(prop.getProperty("sarbr@cisco.com"), "/"));

	}
	public static void getRallyUserObjectId() {
		String uri = "https://rally1.rallydev.com/#/61680796949ud/users";
		Client client = null;
		WebResource resource = null;
		ClientResponse response = null;
		client = Client.create();
		try {
			resource = client.resource(new URI(uri));
			response = resource.accept("application/json")
					.header("Authorization", "Basic YXMtY2ktdXNlci5nZW5AY2lzY28uY29tOiFEZXZvcHNhcw==")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed Http Code : " + response.getStatus());

			}
			String output = response.getEntity(String.class);
			System.out.println(output);
			/*JSONObject jsonObject = new JSONObject(output);
			JSONObject obj = new JSONObject();
			obj = (JSONObject) jsonObject.get("User");
			Long x = (Long) obj.get("ObjectID");
			String id = x.toString();
			System.out.println("Object ID" + id);*/
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

	public static void removeProject() throws URISyntaxException, IOException{
		RallyAgentDAO dao = new RallyAgentDAO();
		String custId="24334e12-d66b-11e6-95ed-507b9dc361f2";
		RallyInfo rally = dao.getRallyDetails(custId);
		RallyRestApi restApi = new RallyRestApi(new URI(rally.getUrl()), rally.getApiKey());
		restApi.setApplicationName("TestApp");
		JsonObject newProject = new JsonObject();
		newProject.addProperty("Disabled", "false");
//		String rallyWrksp = dao.getWorkSpaceObjId(custId);
		UpdateRequest updateRequest = new UpdateRequest("/user/60620825107", newProject);
		UpdateResponse updateResponse=restApi.update(updateRequest);
		if(updateResponse.wasSuccessful()){
			System.out.println("Response object "+updateResponse.toString());

		}
		else{
			String[] errorList;
			errorList = updateResponse.getErrors();
			for (int i = 0; i < errorList.length; i++) {
				System.out.println("Error list  "+errorList[i]);
			}
		}
			// Release all resources
			restApi.close();
	}
}