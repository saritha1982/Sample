package org.cisco.spadeportal.resources;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cisco.spadeportal.bean.entity.CustomerEntity;
import org.cisco.spadeportal.bean.entity.WorkTypeEntity;
import org.cisco.spadeportal.db.CustomerDAO;
import org.cisco.spadeportal.db.WorkTypeDAO;

/**
 * @author sarbr
 *
 */
@Path("/customers/workTypes")
public class WorkTypeResource {

	private static final Logger LOGGER = Logger.getLogger(WorkTypeResource.class.getName());

	WorkTypeDAO workTypeDAO=new WorkTypeDAO();
	CustomerDAO customerDAO = new CustomerDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<WorkTypeEntity> getWorkTypes(){
		LOGGER.log(Level.INFO,"Getting workTypes from DB");
		return workTypeDAO.getWorkTypesFromDB();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{workTypeId}")
	public WorkTypeEntity getWorkTypeDetail(@PathParam("workTypeId") String workTypeId){
		LOGGER.log(Level.INFO,"Getting workType detail from DB");
		return workTypeDAO.getWorkTypeDetailFromDB(workTypeId,null);
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{workTypeName}/customerNames")
	public List<CustomerEntity> getCustomers(@PathParam("workTypeName") String workTypeName) {
		LOGGER.log(Level.INFO, "Getting customer Names from DB");
		return customerDAO.getCustomerNamesFromDB(workTypeName);

	}
	
	
}
