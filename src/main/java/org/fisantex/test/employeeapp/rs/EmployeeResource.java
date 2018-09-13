package org.fisantex.test.employeeapp.rs;

 
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
 
import org.fisantex.test.employeeapp.controller.EmployeeController;
import org.fisantex.test.employeeapp.model.bean.EmployeeBean;
import org.fisantex.test.employeeapp.model.bean.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/employee")
public class EmployeeResource {
	
	@Autowired
	private EmployeeController employeeController;
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTest() {		
		return employeeController.getName();				 
	}
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EmployeeBean getEmployee(@PathParam("id") Long id) {
		return employeeController.getEmployee(id);
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmployeeBean> getEmployeeList(@QueryParam("sort") String sort,@QueryParam("filter") String filters) {
		return employeeController.getEmployeeList(sort,filters);
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
	public OperationResult addEmployee(EmployeeBean bean) {		
		return employeeController.addEmployee(bean);
	}
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
	public OperationResult updateEmployee(EmployeeBean bean) {		
		return employeeController.updateEmployee(bean);
	}
	 
	@DELETE
	@Path("/id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
	public OperationResult removeEmployee(@PathParam("id") Long id) {				
		return employeeController.removeEmployee(id);
	}
}
