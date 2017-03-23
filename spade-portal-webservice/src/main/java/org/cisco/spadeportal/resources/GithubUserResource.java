package org.cisco.spadeportal.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/{userName}")
public class GithubUserResource {
	private static final Logger LOGGER = Logger.getLogger(GithubUserResource.class.getName());
	
	
	@GET
	public Boolean userExists(@PathParam("userName") String userName){
		LOGGER.log(Level.INFO , "inside userExists function");
		
		String uri = "https://github3.cisco.com/api/v3/users/" + userName;
		Client client = Client.create();
		WebResource resource = client.resource(uri);
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
									.header("authorization", "Basic YXMtY2ktdXNlci5nZW46IUNJQ0RwYXNz").get(ClientResponse.class);
		
		if(response.getStatus() != 200){
			return false;
		}
		return true;
	}
	
}
