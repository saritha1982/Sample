package org.cisco.spadeportal.resources;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.bean.entity.ProjectRequestTypeEntity;
import org.cisco.spadeportal.db.ProjectRequestTypeDAO;

/**
 * @author sarbr
 *
 */
@Path("/projectRequestTypes")
public class ProjectRequestTypeResource {

	private static final Logger LOGGER = Logger.getLogger(ProjectRequestTypeResource.class.getName());
	ProjectRequestTypeDAO projectRequestTypeDAO=new ProjectRequestTypeDAO();


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectRequestTypeEntity> getProjectRequestType(@PathParam("customerId") String customerId){
		LOGGER.log(Level.INFO,"Getting ProjectRequestTypes from DB");
		return projectRequestTypeDAO.getProjectRequestTypesFromDB();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProjectRequestType(@PathParam("customerId") String customerId){
		return Response.status(404).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProjectRequestType(@PathParam("customerId") String customerId){
		return Response.status(404).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{requestTypeId}")
	public Response getProjectRequestType(@PathParam("technologyId") String technologyId,@PathParam("customerId") String customerId){
		return Response.status(404).build();
	}

}
