package org.fisantex.test.employeeapp.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fisantex.test.employeeapp.model.bean.OperationResult;

 

public class ElaborateResponseException extends WebApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElaborateResponseException(OperationResult message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(message).type(MediaType.APPLICATION_JSON).build());
    }
}
