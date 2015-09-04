/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lazar
 */
public class BasicAuthenticationException extends WebApplicationException {
    
    public BasicAuthenticationException (String message){
        super(Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorMessage(message, 406)).type(MediaType.APPLICATION_JSON).build());
    }
    
}
