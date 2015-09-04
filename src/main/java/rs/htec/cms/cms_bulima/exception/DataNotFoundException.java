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

public class DataNotFoundException extends WebApplicationException {
    
    public DataNotFoundException (String message){
        super(Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(message, 404)).type(MediaType.APPLICATION_JSON).build());
    }
}
