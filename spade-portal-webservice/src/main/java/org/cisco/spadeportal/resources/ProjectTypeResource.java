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

import org.cisco.spadeportal.bean.entity.ProjectTypeEntity;
import org.cisco.spadeportal.db.ProjectTypeDAO;

/**
 * @author sarbr
 *
 */
@Path("/projectTypes")
public class ProjectTypeResource {

	private static final Logger LOGGER = Logger.getLogger(ProjectResource.class.getName());

	ProjectTypeDAO projectTypeDAO = new ProjectTypeDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectTypeEntity> getProjectTypes(@PathParam("customerId") String customerId) {
		LOGGER.log(Level.INFO, "Getting project types from DB");
		return projectTypeDAO.getProjectTypesFromDB();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProjectTypes(@PathParam("customerId") String customerId) {
		return Response.status(404).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProjectTypes(@PathParam("customerId") String customerId) {
		return Response.status(404).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{projectType}")
	public Response getProjectType(@PathParam("technologyId") String technologyId,
			@PathParam("customerId") String customerId) {
		return Response.status(404).build();
	}
}
