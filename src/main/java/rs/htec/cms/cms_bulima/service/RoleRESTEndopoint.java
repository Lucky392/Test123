/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.domain.CmsRole;

/**
 *
 * @author lazar
 */
@Path("/role")
public class RoleRESTEndopoint {
    
    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRole(@HeaderParam("authorization") String token, CmsRole role){
        return null;
    }
    
}
