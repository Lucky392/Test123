/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 * 
 * Provides methods for edit, view, search and filter.
 * 
 */
public class BugReportRESTEndpoint {
    
    RestHelperClass helper;
    Validator validator;

    public BugReportRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }
    
    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getBugReport(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page) {
        
        return null;
    }
    
    
}
