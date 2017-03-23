/**
 * 
 */
package org.cisco.spadeportal.resources;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cisco.spadeportal.bean.entity.ProjectRoleEntity;
import org.cisco.spadeportal.db.ProjectRoleDAO;


/**
 * @author yugsingh
 *
 */

@Path("/projectRoles")
public class ProjectRoleResource {
	private static final Logger LOGGER = Logger.getLogger(Logger.class.getName());
	ProjectRoleDAO projectRoleDAO = new ProjectRoleDAO();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProjectRoleEntity> getProjectRoles(){
		LOGGER.log(Level.INFO, "Getting Project Roles from DB");
		return projectRoleDAO.getProjectRolesFromDB();
	}
	
	
}
