package org.cisco.spadeportal.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.bean.entity.ProjectEntity;
import org.cisco.spadeportal.bean.request.User;
import org.cisco.spadeportal.db.CustomerDAO;
import org.cisco.spadeportal.db.UserDAO;

@Path("/user")
public class CustomerIdResource {
	CustomerDAO dao = new CustomerDAO();
	UserDAO userDAO = new UserDAO();

	@Path("/{projectName}/onBoardId")
	@GET
	public Response getOnboardId(@PathParam("projectName") String projectName, @HeaderParam("demo") String demo) {
		ProjectEntity entity = new ProjectEntity();
		if (demo == null || !demo.equalsIgnoreCase("demo")) {
			entity = dao.getOnboardId(projectName);

			return Response.ok().entity(entity).status(200).build();
		} else {
			return Response.status(404).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{userName}/project/{onBoardId}")
	public Response editMangaeUser(User user, @PathParam("userName") String userName, 
			@PathParam("onBoardId") String onBoardId, @HeaderParam("demo") String demo){
		System.out.println("Inside user edit method");
		String customerId= null;
		
		System.out.println("role from ui "+user.getAccessLevel());
		System.out.println("mail from ui " +user.getEmailAddress());
		

		int rows = 0;

		if(demo == null || !demo.equalsIgnoreCase("demo")){
			System.out.println("Updating useronboard table");
			rows = userDAO.editUsers(user, onBoardId, userName);
			customerId = userDAO.getCustomerIdForUser(onBoardId).getCustomerId();
		}

		if(rows!=0){
			System.out.println("Useronboard table is updated and now trying for Rally");
			userDAO.editUserProjectRole(customerId, onBoardId, user);
			/*if(user.isDisabled()){
				System.out.println("Now trying to disable user");
				userDAO.disableRallyUser(customerId, onBoardId, user);
			}*/
			return Response.ok().status(201).entity("User Edited").build();
		}
		else{
			return Response.ok().entity("incorrect body").status(404).build();
		}



	}








}
