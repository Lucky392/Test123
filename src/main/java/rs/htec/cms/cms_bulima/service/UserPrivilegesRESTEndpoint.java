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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsRole;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("/privileges")
public class UserPrivilegesRESTEndpoint {

    RestHelperClass helper;

    public UserPrivilegesRESTEndpoint() {
        helper = new RestHelperClass();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPrivileges(@HeaderParam("authorization") String token, List<CmsUserPrivileges> userPrivileges) {
        try {
            EntityManager em = helper.getEntityManager();
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.ADD, token);
            if (userPrivileges.size() > 0 && userPrivileges.get(0).getCmsRole() != null) {
                helper.persistObject(em, userPrivileges.get(0).getCmsRole());
            } else {
                //napravi novi izuzetak i baci ga!!!!
            }
            CmsRole role = (CmsRole) em.createNamedQuery("CmsRole.findByName").setParameter("name", userPrivileges.get(0).getCmsRole().getName()).getSingleResult();
            long roleID = role.getId();
            for (CmsUserPrivileges cup : userPrivileges) {
                cup.getCmsUserPrivilegesPK().setRoleId(roleID);
                cup.setCmsRole(null);

                helper.persistObject(em, cup);
            }
            
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }
    
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPrivileges2(@HeaderParam("authorization") String token, List<CmsUserPrivileges> userPrivileges) {
        try {
            EntityManager em = helper.getEntityManager();
            helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.ADD, token);
            
            helper.persistObject(em, userPrivileges.get(0).getCmsRole());
            
            CmsRole role = (CmsRole) em.createNamedQuery("CmsRole.findByName").setParameter("name", userPrivileges.get(0).getCmsRole().getName()).getSingleResult();
            for (CmsUserPrivileges cup : userPrivileges) {
                cup.getCmsUserPrivilegesPK().setRoleId(role.getId());
                helper.persistObject(em, cup);
            }
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivileges() {
        EntityManager em = helper.getEntityManager();
        return Response.ok().entity(em.createNamedQuery("CmsUserPrivileges.findAll").getResultList()).build();
    }

}
