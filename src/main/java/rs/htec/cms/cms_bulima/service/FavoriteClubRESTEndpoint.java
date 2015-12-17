/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import rs.htec.cms.cms_bulima.domain.FavoriteClub;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("favorite_clubs")
public class FavoriteClubRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns all favorite clubs. 
     *
     *
     * @param token header parameter for checking permission
     * @param page number of page for searched results
     * @param limit number of matchPlayerStats that are returned in body
     * @param orderingColumn
     * @return Favorite Clubs status 200 OK
     * @throws DataNotFoundException
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFavoriteClub(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FavoriteClub> favoriteClubs;

        favoriteClubs = em.createNamedQuery("FavoriteClub.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (favoriteClubs == null || favoriteClubs.isEmpty()) {
            throw new DataNotFoundException("There is no FavoriteClub for this search!");
        }
        long count = (long) em.createQuery("SELECT count(fc) FROM FavoriteClub fc").getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(getMap(favoriteClubs));
        return Response.ok().entity(go).build();
    }

    private List getMap(List<FavoriteClub> fantasyClubs) {
        List<Map> list = new ArrayList<>();
        for (FavoriteClub fc : fantasyClubs) {
            Map m = new HashMap();
            m.put("fantasyClubName", fc.getMediumName());
            m.put("fantasyClubId", fc.getId());
            list.add(m);
        }
        return list;
    }

}
