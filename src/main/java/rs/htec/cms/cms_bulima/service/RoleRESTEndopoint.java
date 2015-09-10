/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsRole;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("/role")
public class RoleRESTEndopoint {

    RestHelperClass helper;

    public RoleRESTEndopoint() {
        helper = new RestHelperClass();
    }

    /**
     * API for method: /role<br/>
     * This method gets authorization token from HTTP header return roles in
     * JSON format. Example for JSON:<br/>
     * [<br/>
     * {<br/>
     * "name": "admin",<br/> "id": 1 <br/>},<br/> {<br/> "name": "custom1",<br/> "id": 2 <br/>}<br/> ]<br/>
     *
     * @param token
     * @return {@link Response} Response {@link Status#OK} 200 OK with JSON body
     * @throws NotAuthorizedException
     * @throws DataNotFoundException
     *
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRole(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.CMS_ROLE, MethodConstants.SEARCH, token);
            List<CmsRole> roles = em.createNamedQuery("CmsRole.findAll").getResultList();
            if (roles.isEmpty()) {
                throw new DataNotFoundException("Requested page does not exist..");
            }
            return Response.ok().entity(roles).build();
        } catch (IllegalArgumentException e) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, e);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

}
