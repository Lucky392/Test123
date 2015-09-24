/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.PremiumItem;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/items")
public class PremiumItemRESTEndpoint {
    
    RestHelperClass helper;

    public PremiumItemRESTEndpoint() {
        helper = new RestHelperClass();
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumItems(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<PremiumItem> items;
        StringBuilder query = new StringBuilder("SELECT p FROM PremiumItem p ");
        if (search != null) {
            search = "%" + search + "%";
            query.append(" p.name LIKE '")
                    .append(search)
                    .append("' OR p.shopName LIKE '")
                    .append(search).append("'");
        }
        items = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (items == null || items.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        return Response.ok().entity(helper.getJson(items)).build();
    }
}
