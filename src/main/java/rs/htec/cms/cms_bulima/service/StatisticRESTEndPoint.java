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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.FantasyManager;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/statistics")
public class StatisticRESTEndPoint {

    RestHelperClass helper;

    public StatisticRESTEndPoint() {
        helper = new RestHelperClass();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyManager(@HeaderParam("authorization") String token, @PathParam("email") String email) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyManager> fm;
        String query = "SELECT f FROM FantasyManager f JOIN f.idUser u WHERE u.email = '" + email + "'";
        fm = em.createQuery(query).getResultList();
        if (fm.isEmpty()) {
            throw new DataNotFoundException("There is no Fantasy Managers for this user!");
        } else {
            return Response.ok().entity(helper.getJson(fm)).build();
        }
    }
}
