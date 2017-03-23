package org.cisco.spadeportal.resources;

import java.net.URI

;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.bean.entity.ProjectEntity;
import org.cisco.spadeportal.bean.entity.UserEntity;
import org.cisco.spadeportal.bean.request.ProjectCreateRequest;
import org.cisco.spadeportal.db.CustomerDAO;
import org.cisco.spadeportal.db.ProjectDAO;
import org.cisco.spadeportal.db.UserDAO;
import org.cisco.spadeportal.exceptions.BaseAPIException;
import org.cisco.spadeportal.helpers.activiti.ActivitiHelper;

/**
 * 
 * @author sarbr
 *
 */

@Path("/customers/{customerId}/projects")
public class ProjectResource {
	private static final Logger LOGGER = Logger.getLogger(ProjectResource.class.getName());

	ProjectDAO projectOnBoardDAO = new ProjectDAO();
	UserDAO userOnBoardDAO = new UserDAO();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response onBoardProject(ProjectCreateRequest projectOnBoardData, @PathParam("customerId") String customerId,
			@HeaderParam("demo") String demo) throws Exception {
		// Checking Project ID exists
		if (projectOnBoardDAO.checkProjectIdExists(projectOnBoardData.getOnBoardId())) {
			LOGGER.log(Level.INFO, "OnBoard Id Exists please give something else" + projectOnBoardData.getOnBoardId());

			throw new BaseAPIException("Onboard ID Exists");
		} else {
			if (demo == null || !demo.equalsIgnoreCase("demo")) {
				LOGGER.log(Level.INFO, "onboardId from ui" + projectOnBoardData.getOnBoardId());
				projectOnBoardDAO.onboardProject(projectOnBoardData, customerId);
				LOGGER.log(Level.INFO, "Users list" + projectOnBoardData.getUsers());
				ActivitiHelper.getInstance().startProjectOnBoardProcess(projectOnBoardData, customerId);
				if (projectOnBoardData.getUsers() != null) {
					userOnBoardDAO.onboardUser(projectOnBoardData.getUsers(), projectOnBoardData.getOnBoardId(),
							customerId);
					ActivitiHelper.getInstance().startUserOnBoardProcess(customerId, projectOnBoardData.getOnBoardId());
				}

			} else {
				URI uri = new URI("/customers/" + customerId + "/projects" + projectOnBoardData.getOnBoardId());
				return Response.created(uri).build();
			}
		}
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectList(@PathParam("customerId") String customerId, @HeaderParam("demo") String demo) throws InterruptedException {
		LOGGER.log(Level.INFO, "Getting project list from DB");

		List<ProjectEntity> list = new ArrayList<ProjectEntity>();

		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			list = projectOnBoardDAO.getProjectListFromDB(customerId);
			return Response.ok().entity(list).build();
		} else {
			List<ProjectEntity> res = new ArrayList<ProjectEntity>();
			ProjectEntity pe = new ProjectEntity();
			pe.setProjectId("NSO-DEMO-Validation-1");
			pe.setParentProject("NSO");
			pe.setProjectName("NSO-DEMO-Validation-1");
			ProjectEntity pe1 = new ProjectEntity();
			pe1.setProjectId("NSO-DEMO-Validation-2");
			pe1.setParentProject("NSO");
			pe1.setProjectName("NSO-DEMO-Validation-2");
			res.add(pe);
			res.add(pe1);
			return Response.ok().entity(res).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{onboardId}")
	public Response getProjectDetail(@PathParam("customerId") String customerId,
			@PathParam("onboardId") String onboardId, @HeaderParam("demo") String demo) {
		LOGGER.log(Level.INFO, "Getting project detail from DB");
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			System.out.println("Inside getProjectDetail");
			return Response.ok().entity(projectOnBoardDAO.getProjectDetailFromDB(customerId, onboardId)).build();

		} else {
			ProjectEntity pe = new ProjectEntity();
			pe.setProjectId("NSO-DEMO-Validation-1");
			pe.setParentProject("NSO");
			pe.setProjectName("NSO-DEMO-Validation-1");
			return Response.ok(pe).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{technologyName}/managers")
	public Response getProjectManagers(@PathParam("customerId") String customerId,
			@PathParam("technologyName") String technologyName) {

		List<UserEntity> list = projectOnBoardDAO.getManagersFromDB(customerId, technologyName);

		return Response.ok(list).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{technologyName}/{projects}")
	public Response getParentProjects(@PathParam("customerId") String customerId,
			@PathParam("technologyName") String technologyName, @PathParam("projects") String projects) {
		List<ProjectEntity> list = projectOnBoardDAO.getParentProjectsFromDB(customerId, technologyName, projects);

		return Response.ok(list).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{onboardId}")
	public Response editProject(ProjectCreateRequest projectOnBoardData, @PathParam("customerId") String customerId,
			@PathParam("onboardId") String onboardId, @HeaderParam("demo") String demo) throws ParseException {
		LOGGER.log(Level.INFO, "Editing Project ");
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			return Response.ok()
					.entity(projectOnBoardDAO.updateProjectOnboardTable(customerId, projectOnBoardData, onboardId))
					.build();

		} else {
			ProjectEntity pe = new ProjectEntity();
			pe.setProjectId("NSO-DEMO-Validation-1");
			pe.setParentProject("NSO");
			pe.setProjectName("NSO-DEMO-Validation-1");
			return Response.ok(pe).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{onboardId}")
	public Response deleteProject(@PathParam("customerId") String customerId, @PathParam("onboardId") String onboardId,
			@HeaderParam("demo") String demo) throws ParseException {
		LOGGER.log(Level.INFO, "Editing Project ");
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			String customerIdd=projectOnBoardDAO.getCustomerId(onboardId);
			projectOnBoardDAO.closeRallyProject(customerIdd, onboardId);
			return Response.ok().build();

		} else {
			ProjectEntity pe = new ProjectEntity();
			pe.setProjectId("NSO-DEMO-Validation-1");
			pe.setParentProject("NSO");
			pe.setProjectName("NSO-DEMO-Validation-1");
			return Response.ok(pe).build();
		}
	}

}
