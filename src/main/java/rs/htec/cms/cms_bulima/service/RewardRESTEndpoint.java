/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Reward;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.RewardPOJO;

/**
 *
 * @author stefan
 */
@Path("/reward")
public class RewardRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns Reward for defined id.
     * Example for response:
     * { <br/>
     * "createDate": 1424078743000,<br/>
     * "imageUrl": null,<br/>
     * "amountPremiumCurrency": 0,<br/>
     * "idPremiumItem": 4,<br/>
     * "updateAt": null,<br/>
     * "amountPremiumItem": 1,<br/>
     * "amountIngameCurrency": 0,<br/>
     * "chance": 25,<br/>
     * "description": null,<br/>
     * "name": "1x Scout",<br/>
     * "id": 3<br/>
     * }<br/>
     *
     *
     * @param token - header parameter for checking permission
     * @param id - id of Reward that should be returned
     * @return Reward in JSON for defined id
     * @throws DataNotFoundException if there is no Reward with id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRewardById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        RewardPOJO pojo;
        try {
            Reward reward = (Reward) em.createNamedQuery("Reward.findById").setParameter("id", id).getSingleResult();
            pojo = new RewardPOJO(reward);
        } catch (NoResultException e) {
            throw new DataNotFoundException("Reward at index " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
    }

}
