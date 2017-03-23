package org.cisco.spadeportal.resources;

import java.io.IOException;
import java.net.URISyntaxException;
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

import org.cisco.spadeportal.bean.entity.TechnologyEntity;
import org.cisco.spadeportal.db.CustomerDAO;
import org.cisco.spadeportal.db.GitHubDAO;
import org.cisco.spadeportal.db.TechnologyDAO;

/**
 * @author sarbr
 *
 */
@Path("/customers/{customerId}/technologies")
public class TechnologyResource {

	private static final Logger LOGGER = Logger.getLogger(TechnologyResource.class.getName());
	TechnologyDAO technologyDAO = new TechnologyDAO();
	CustomerDAO CustomerDAO = new CustomerDAO();
	GitHubDAO gitDAO = new GitHubDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TechnologyEntity> getTechnologies(@PathParam("customerId") String customerId) {
		LOGGER.log(Level.INFO, "Getting technologies    from DB");
		return technologyDAO.getTechnologiesFromDB(customerId);
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTechnologies(@PathParam("customerId") String customerId, TechnologyEntity entity,
			@HeaderParam("demo") String demo) throws URISyntaxException, IOException {
		System.out.println(customerId);
		int status = 0;
		String response = null;
		String orgName = null;
		String repoId = null;
		String groupId = null;
		String groupName = null;
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
				technologyDAO.createTechnologyRallyProject(customerId, entity.getTechnologyName());
				orgName = CustomerDAO.getOrgName(customerId);
				status = technologyDAO.createGitRepo(orgName, entity.getGithubRepoName());
				if (status == 201) {
					response = technologyDAO.createBranchAPI(orgName, entity.getGithubRepoName());
					String check = response.substring(30, 35);
					System.out.println(check);
					if (check.equals("empty")) {
						technologyDAO.createBranchAPI(orgName, entity.getGithubRepoName());
						gitDAO.updateGithubRepo(orgName, entity.getGithubRepoName());
						//groupName = technologyDAO.insertIntoGroup(entity.getTechnologyName(), customerId);
						repoId = gitDAO.getGitRepoId(entity.getGithubRepoName());
						//groupId = technologyDAO.getGroupId(groupName, customerId);
						technologyDAO.updatetechnology(groupId, repoId, entity.getTechnologyName(), customerId);
						return Response.ok().entity("{\"message\":\"Technology Added\"}").status(201).build();
					}

				

			}
			

		}

		return Response.status(404).entity("something went wrong").build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTechnologies(@PathParam("customerId") String customerId) {
		return Response.status(404).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{technologyId}")
	public TechnologyEntity getTechnology(@PathParam("technologyId") String technologyId,
			@PathParam("customerId") String customerId) {
		LOGGER.log(Level.INFO, "Getting technology detail from DB");
		return technologyDAO.getTechnologyFromDB(customerId, technologyId,null);
	}

	// public

}
