package org.fisantex.test.employeeapp.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

 
import org.fisantex.test.employeeapp.config.Employee;
import org.fisantex.test.employeeapp.model.model.bean.EmployeeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/employee")
public class EmployeeResource {
	
	@Autowired
	private Employee employeeApp;
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTest() {		
		return employeeApp.getName();
		 
	}
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EmployeeBean getEmployee(@PathParam("id") Long id) {
		return EmployeeBean.builder().addresse("erere").name("adfdfd").lastName("iiii").id(Long.valueOf(1)).build();
	}
	 
}
