package org.cisco.spadeportal.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.db.CustomerDAO;

@Path("/editProject")
public class EditResource {

	private static final Logger LOGGER = Logger.getLogger(EditResource.class.getName());

	CustomerDAO customerDAO = new CustomerDAO();

	@Path("/{onBoardId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerId(@PathParam("onBoardId") String onBoardId, @HeaderParam("demo") String demo) {
		LOGGER.log(Level.INFO, "Inside getCustomerId function");
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			return Response.ok().entity(customerDAO.getCustomerId(onBoardId)).status(200).build();
		} else {
			return Response.ok().entity("f9946b4f-e87b-11e6-883c-d8cb8a429f06").status(200).build();
		}
	}

}
