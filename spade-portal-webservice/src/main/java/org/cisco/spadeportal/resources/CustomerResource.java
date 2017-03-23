package org.cisco.spadeportal.resources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cisco.spadeportal.bean.entity.CustomerEntity;
import org.cisco.spadeportal.db.CustomerDAO;
import org.cisco.spadeportal.db.TechnologyDAO;

/**
 * @author sarbr
 *
 */
@Path("/customers")
public class CustomerResource {

	private static final Logger LOGGER = Logger.getLogger(CustomerResource.class.getName());

	CustomerDAO customerDAO = new CustomerDAO();
	TechnologyDAO technologyDAO=new TechnologyDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerEntity> getCustomers(@PathParam("workTypeName") String workTypeName) {
		LOGGER.log(Level.INFO, "Getting customer Names from DB");
		return customerDAO.getCustomerNamesFromDB(workTypeName);

	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{customerName}/customerId")
	public Response CustomerId(@PathParam("customerName") String customerName, @HeaderParam("demo") String demo){
		if(demo==null || demo.equalsIgnoreCase("demo")){
			return Response.ok().entity(customerDAO.customerId(customerName)).status(200).build();
			
		}
		else{
			return Response.ok().entity("{\"error message\" : \"Exception occured\"}").status(500).build();
		}
		
		
	}

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

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response createCustomer(CustomerEntity customer) throws URISyntaxException, IOException {
		System.out.println(customer.getInternal());
		int inserted = customerDAO.createCustomerRallyProject(customer);
		
		if (inserted > 0 ) {
			return Response.ok().status(201).entity("Customer Created").build();
		}
		return Response.status(500).entity("Customer Not Created").build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/edit")
	public Response updateCustomer(CustomerEntity customer) {
		int rows = customerDAO.updateCustomerInfo(customer.getCustomerId(), customer);
		if (rows > 0 ) {
			return Response.ok().status(201).entity("Customer Updated").build();
		}
		return Response.status(500).entity("Customer Not Updated").build();

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCustomer() {
		return Response.status(404).build();

	}

}
