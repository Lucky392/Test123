/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import rs.htec.cms.cms_bulima.domain.FantasyClub;
import rs.htec.cms.cms_bulima.domain.News;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("favorite_club")
public class FavoriteClubRESTEndpoint {
    
    @InjectParam
    RestHelperClass helper;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFavoriteClub(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyClub> fantasyClubs;
        
        fantasyClubs = em.createNamedQuery("FantasyClub.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (fantasyClubs == null || fantasyClubs.isEmpty()) {
            throw new DataNotFoundException("There is no news for this search!");
        }
        long count = (long) em.createQuery("SELECT count(fc) FROM FantasyClub fc").getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(getMap(fantasyClubs));
        return Response.ok().entity(go).build();
    }
    
    private Map getMap(List<FantasyClub> fantasyClubs){
        Map m = new HashMap();
        for (FantasyClub fc : fantasyClubs) {
            m.put(fc.getName(), fc.getId());
        }
        return m;
    }
    
}
