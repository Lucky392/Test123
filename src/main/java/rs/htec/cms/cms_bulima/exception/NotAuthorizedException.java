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
 * @author stefan
 */
public class NotAuthorizedException extends WebApplicationException{
 
    public NotAuthorizedException (String message){
        super(Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorMessage(message, 401)).type(MediaType.APPLICATION_JSON).build());
    }
}
