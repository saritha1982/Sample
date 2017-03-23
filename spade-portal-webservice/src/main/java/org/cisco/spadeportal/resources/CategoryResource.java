/**
 * 
 */
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.bean.request.CategoryEntity;
import org.cisco.spadeportal.db.CategoryDAO;
import org.cisco.spadeportal.db.TechnologyDAO;

/**
 * @author sarbr
 *
 */
@Path("/customers/{customerId}/{technologyName}/categories")
public class CategoryResource {

	CategoryDAO categoryDAO = new CategoryDAO();
	TechnologyDAO technologyDAO = new TechnologyDAO();
	private static final Logger LOGGER = Logger.getLogger(CategoryResource.class.getName());

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCategory(@PathParam("customerId") String customerId,@PathParam("technologyName") String technologyName , CategoryEntity entity,
			@HeaderParam("demo") String demo) throws URISyntaxException, IOException {
		technologyDAO.createCategoryRallyProject(customerId, entity, technologyName);
		technologyDAO.createGithubCategory(customerId, entity);
		return Response.ok().entity("{\"message\":\"Category Added\"}").status(200).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CategoryEntity> getCategories(@PathParam("customerId") String customerId,
			@PathParam("technologyName") String technologyName) {
		LOGGER.log(Level.INFO, "Getting Categories  from DB");
		return categoryDAO.getCategoriesFromDB(customerId, technologyName);
	}

}
