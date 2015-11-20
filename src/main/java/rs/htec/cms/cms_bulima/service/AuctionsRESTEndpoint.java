/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Auction;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.AuctionPOJO;

/**
 *
 * @author stefan
 */
@Path("/auctions")
public class AuctionsRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns single auction for defined id.
     * <br/>Example for response: {<br/>
     * {<br/>
     * "createDate": 1437398996000,<br/>
     * "fantasyLeague": "Offizielle Liga 1",<br/>
     * "idFantasyLeaguePlayer": 1589275,<br/>
     * "startPrice": 3000000,<br/>
     * "isFair": 0,<br/>
     * "auctionStartTimestamp": 1437398996000,<br/>
     * "auctionEndTimestamp": 1437557396000,<br/>
     * "auctionFinishedTimestamp": 1437557700000,<br/>
     * "idFantasyLeague": 7175,<br/>
     * "idFantasyClubSeller": 0,<br/>
     * "fantasyClubSeller": null,<br/>
     * "fantasyLeaguePlayer": "Sebastian Boenisch",<br/>
     * "id": 4<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param id of bid that should be returned
     * @return Bid in JSON format
     * @throws DataNotFoundException if there is not Bid for defined id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuctionById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        AuctionPOJO pojo = null;
        try {
            Auction auction = (Auction) em.createNamedQuery("Auction.findById").setParameter("id", id).getSingleResult();
            pojo = new AuctionPOJO(auction);
        } catch (Exception e) {
            throw new DataNotFoundException("Auction at index " + id + " does not exist..");
        }

        return Response.ok().entity(pojo).build();
    }

    /**
     * API for this method:
     * .../rest/auctions?idFantasyLeague=Value&idFantasyClubSeller=Value&idFantasyLeaguePlayer=Value
     * This method returns JSON list of Auction for one Fantasy Club. It
     * produces APPLICATION_JSON media type. Example for JSON list: {<br/>
     * "count": 12,<br/>
     * "data": [<br/>
     * {<br/>
     * "createDate": 1437399155000,<br/>
     * "fantasyLeague": "Offizielle Liga 1",<br/>
     * "idFantasyLeaguePlayer": 1589762,<br/>
     * "startPrice": 100000,<br/>
     * "isFair": 1,<br/>
     * "auctionStartTimestamp": 1437399155000,<br/>
     * "auctionEndTimestamp": 1437399155000,<br/>
     * "auctionFinishedTimestamp": 1437399155000,<br/>
     * "idFantasyLeague": 7175,<br/>
     * "idFantasyClubSeller": 27434,<br/>
     * "fantasyClubSeller": "Los Chipirones",<br/>
     * "fantasyLeaguePlayer": "Thilo Kehrer",<br/>
     * "id": 51<br/>
     * },<br/>
     * {<br/>
     * "createDate": 1437399568000,<br/>
     * "fantasyLeague": "Offizielle Liga 1",<br/>
     * "idFantasyLeaguePlayer": 1589269,<br/>
     * "startPrice": 3675000,<br/>
     * "isFair": 1,<br/>
     * "auctionStartTimestamp": 1437399568000,<br/>
     * "auctionEndTimestamp": 1437485968000,<br/>
     * "auctionFinishedTimestamp": 1437486300000,<br/>
     * "idFantasyLeague": 7175,<br/>
     * "idFantasyClubSeller": 27434,<br/>
     * "fantasyClubSeller": "Los Chipirones",<br/>
     * "fantasyLeaguePlayer": "Sven Schipplock",<br/>
     * "id": 102<br/>
     * }<br/>
     * ]<br/>
     * }<br/>
     *
     * @param token header parameter for checking permission
     * @param page number of page at which we search for News
     * @param limit number of Bids method returns
     * @param idFantasyLeague id for FantasyLeague
     * @param idFantasyClubSeller id for FantasyClubSeller
     * @param idFantasyLeaguePlayer id for FantasyLeaguePlayer
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception: <br/>{<br/>
     * "errorMessage": "There is no Auction for this Club!",<br/> "errorCode":
     * 404<br/> }
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuctions(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("idFantasyLeague") String idFantasyLeague,
            @QueryParam("idFantasyClubSeller") String idFantasyClubSeller, @QueryParam("idFantasyLeaguePlayer") String idFantasyLeaguePlayer) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);

        List<Auction> auctions;
        StringBuilder query = new StringBuilder("SELECT a FROM Auction a ");
        String operator = "WHERE";
        if (idFantasyLeague != null) {
            query.append(operator).append(" a.idFantasyLeague.id = ").append(idFantasyLeague);
            operator = " AND";
        }
        if (idFantasyClubSeller != null) {
            query.append(operator).append(" a.idFantasyClubSeller.id = ").append(idFantasyClubSeller);
            operator = " AND";
        }
        if (idFantasyLeaguePlayer != null) {
            query.append(operator).append(" a.idFantasyLeaguePlayer.id = ").append(idFantasyLeaguePlayer);
        }
        auctions = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (auctions.isEmpty()) {
            throw new DataNotFoundException("There is no Auction for this search!");
        } else {
            List<AuctionPOJO> pojos = AuctionPOJO.toAuctionPOJOList(auctions);
            String countQuery = query.toString().replaceFirst("a", "count(a)");
            long count = (long) em.createQuery(countQuery).getSingleResult();
            GetObject go = new GetObject();
            go.setCount(count);
            go.setData(pojos);
            return Response.ok().entity(go).build();
        }
    }

}
