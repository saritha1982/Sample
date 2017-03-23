package org.cisco.spadeportal.resources;

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

import org.cisco.spadeportal.bean.entity.UserEntity;
import org.cisco.spadeportal.bean.request.Project;
import org.cisco.spadeportal.bean.request.User;
import org.cisco.spadeportal.db.CustomerDAO;
import org.cisco.spadeportal.db.UserDAO;
import org.cisco.spadeportal.exceptions.SystemException;
import org.cisco.spadeportal.helpers.activiti.ActivitiHelper;

/**
 * @author sarbr
 *
 */
@Path("/customers/{customerId}/user")
public class UserResource {

	private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());
	UserDAO userDAO = new UserDAO();
	CustomerDAO customerDAO = new CustomerDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@PathParam("customerId") String customerId, @HeaderParam("demo") String demo) {
		LOGGER.log(Level.INFO, "Getting users from DB");
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			return Response.ok("Get Users").entity(userDAO.getUserListFromDB(customerId)).build();
		}
		List<UserEntity> res = new ArrayList<UserEntity>();
		UserEntity pe = new UserEntity();
		pe.setUserName("Anuj");
		pe.setProjectRole("admin");
		;
		pe.setAccessLevel("edit");
		UserEntity pe1 = new UserEntity();
		pe1.setUserName("Saritha");
		pe1.setProjectRole("developer");
		;
		pe1.setAccessLevel("view");
		res.add(pe);
		res.add(pe1);
		return Response.ok("Get Users").entity(res).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(@PathParam("customerId") String customerId, User user, @HeaderParam("demo") String demo)
			throws SystemException {
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			if (user.getProjects().size() != 0) {
				for (Project project : user.getProjects()) {
					customerId = customerDAO.getCustomerId(user.getProjects().get(0).getOnBoardId()).getCustomerId();
					userDAO.addUser(user, project, customerId);
					String onboardId = project.getOnBoardId();
					ActivitiHelper.getInstance().startAddUserProcess(customerId, user, onboardId);
				}
			}
		}
		return Response.ok().build();

	}



	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public Response editUser(@PathParam("customerId") String customerId, User
			userUpdateRequest,@HeaderParam("demo") String demo, @PathParam("onboardId") String
			onboardId) throws SystemException { 
		if (demo == null || !demo.equalsIgnoreCase("demo")) { 
			customerId=customerDAO.getCustomerId(onboardId).getCustomerId();
			userDAO.editUserProjectRole(customerId,	onboardId, userUpdateRequest); 
		} return Response.ok().build();

	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userId}")
	public Response getUser(@PathParam("userId") String userId) {
		return Response.status(404).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/disable")
	public Response deleteUser(@PathParam("customerId") String customerId, User userUpdateRequest,
			@HeaderParam("demo") String demo){
		
			LOGGER.log(Level.INFO, "Inside disable user function");
			String onboardId = null;
			if (demo == null || !demo.equalsIgnoreCase("demo")) {
				List<Project> projects=userUpdateRequest.getProjects();
				boolean isRallyUser=false;
				for(int i=0;i<projects.size();i++){
					String projectObjectId=userDAO.getProjectObjId(projects.get(i).getProjectName().trim());
					System.out.println("Project Name "+projects.get(i).getProjectName()+"Project Id"+projectObjectId);
					isRallyUser=userDAO.disableRallyUser(userUpdateRequest, projectObjectId);
					onboardId=userDAO.getOnboardIdFromProject(projects.get(i).getProjectName());
					userDAO.updateUserInfo(userUpdateRequest, onboardId);

				}
				boolean isGitHubUserDisabled=userDAO.disableGithubUser(projects, userUpdateRequest);
				if(isGitHubUserDisabled&&isRallyUser){
					System.out.println("User is disabled in Rally and Github");

				}else{
					System.out.println("User was not disabled in Rally and Github");

				}
			}

		
		return Response.ok().status(200).build();
	}
}
