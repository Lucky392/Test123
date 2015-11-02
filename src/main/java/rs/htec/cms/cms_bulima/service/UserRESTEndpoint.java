/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.User;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
@Path("/user")
public class UserRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns User for specified id.
     * 
     * @param token - header parameter for checking permission
     * @param id - of User that should be returned
     * @return User
     * @throws DataNotFoundException if User for defined id does not exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        User user = null;
        try {
            user = (User) em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("User with id " + id + " does not exist..");
        }
        return Response.ok().entity(user).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@HeaderParam("authorization") String token, User user){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        User oldUser = em.find(User.class, user.getId());
        if (oldUser == null){
            throw new DataNotFoundException("User at index " + user.getId() + " doesn't exist..");
        }
        user.setCreateDate(oldUser.getCreateDate());
        if (user.getEmail() == null){
            user.setEmail(oldUser.getEmail());
        }
        if (user.getUserhash() == null){
            user.setUserhash(oldUser.getUserhash());
        }
        if (user.getNewEmail() == null){
            user.setNewEmail(oldUser.getNewEmail());
        }
        if (user.getNewEmailActiveTimestamp() == null){
            user.setNewEmailActiveTimestamp(oldUser.getNewEmailActiveTimestamp());
        }
        if (user.getLandingPage() == null){
            user.setLandingPage(oldUser.getLandingPage());
        }
        if (user.getSocialNetwork() == null){
            user.setSocialNetwork(oldUser.getSocialNetwork());
        }
        if (user.getPremiumStatusActiveTimestamp() == null){
            user.setPremiumStatusActiveTimestamp(oldUser.getPremiumStatusActiveTimestamp());
        }
        if (user.getPassword() == null){
            user.setPassword(oldUser.getPassword());
        }
        helper.mergeObject(em, user);
        return Response.ok().build();
    }

}
