/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
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
import rs.htec.cms.cms_bulima.domain.Bid;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.pojo.BidPOJO;

/**
 *
 * @author marko
 */
@Path("/bids")
public class BidRESTEndPoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns single bid for defined id. <br/>Example for response: {<br/>
     * "createDate": 1437409470000,<br/>
     * "biddingPrice": 5100500,<br/>
     * "biddingAtTimestamp": 1437409471000,<br/>
     * "returnMoneyTimestamp": 1437410951000,<br/>
     * "bidManager": 0,<br/>
     * "bidManagerPrice": 0,<br/>
     * "idAuction": 125,<br/>
     * "idFantasyClubBidder": 51441,<br/>
     * "fantasyClubBidderName": "F95 Düsseldorf 1895",<br/>
     * "id": 34<br/>
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
    public Response getBidsById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        BidPOJO pojo = null;
        try {
            Bid bid = (Bid) em.createNamedQuery("Bid.findById").setParameter("id", id).getSingleResult();
            pojo = new BidPOJO(bid);
        } catch (Exception e) {
            throw new DataNotFoundException("Bid at index " + id + " does not exist..");
        }

        return Response.ok().entity(pojo).build();
    }

//    /**
//     * API for this method: .../rest/bids/{id} This method returns
//     * JSON list of Bids for one Fantasy Club. It produces APPLICATION_JSON
//     * media type. Example for JSON list: <br/>[ {<br/>
//     * "bidManagerPrice": "0",<br/> "idAuction": "1203343",<br/> "bidManager":
//     * "0",<br/>
//     * "idFantasyClubBidder": "100",<br/> "id": "187030",<br/> "biddingPrice":
//     * "7831000",<br/>
//     * "returnMoneyTimestamp": "null",<br/> "biddingAtTimestamp": "2015-08-01
//     * 09:46:40.0",<br/> "createDate": "2015-08-01 09:46:39.0"<br/> },<br/>
//     * {<br/>
//     * "bidManagerPrice": "0",<br/> "idAuction": "2137720",<br/> "bidManager":
//     * "0",<br/>
//     * "idFantasyClubBidder": "100",<br/> "id": "257568",<br/> "biddingPrice":
//     * "12500000",<br/>
//     * "returnMoneyTimestamp": "null",<br/> "biddingAtTimestamp": "2015-08-05
//     * 19:07:47.0",<br/> "createDate": "2015-08-05 19:07:46.0"<br/> } ]
//     *
//     * @param token is a header parameter for checking permission
//     * @param id is id of Fantasy Club
//     * @return Response 200 OK with JSON body
//     * @throws DataNotFoundException Example for this exception: <br/>{<br/>
//     * "errorMessage": "There is no Bids for this Club!",<br/> "errorCode":
//     * 404<br/> }
//     */
//    @GET
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getBidsForClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
//        List<Bid> bids;
//        StringBuilder query = new StringBuilder("SELECT b FROM Bid b WHERE b.idFantasyClubBidder.id = ");
//        query.append(id);
//        bids = em.createQuery(query.toString()).getResultList();
//        if (bids.isEmpty()) {
//            throw new DataNotFoundException("There is no Bids for this Club!");
//        } else {
//            return Response.ok().entity(bids).build();
//        }
//    }
    /**
     * API for this method: .../rest/bids?idAuction=VALUE&idFantasyClubBidder=VALUE This method returns JSON list of Bids
     * for one Auction. It produces APPLICATION_JSON media type. Example for
     * JSON list: <br/>[ {<br/>
     * "bidManagerPrice": "0",<br/> "idAuction": "2137720",<br/> "bidManager":
     * "0",<br/>
     * "idFantasyClubBidder": "100",<br/> "id": "257568",<br/> "biddingPrice":
     * "12500000",<br/>
     * "returnMoneyTimestamp": "null",<br/> "biddingAtTimestamp": "2015-08-05
     * 19:07:47.0",<br/> "createDate": "2015-08-05 19:07:46.0"<br/> } ]
     *
     * @param token header parameter for checking permission
     * @param page number of page at which we search for News
     * @param limit number of Bids method returns
     * @param idAuction id of auction
     * @param idFantasyClubBidder id for fantasy club bidder
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no Bids for this Auction!",<br/> "errorCode":
     * 404<br/> }
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBids(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("idAuction") String idAuction,
            @QueryParam("idFantasyClubBidder") String idFantasyClubBidder) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<Bid> bids;
        StringBuilder query = new StringBuilder("SELECT b FROM Bid b ");
        String operator = "WHERE";
        if (idAuction != null) {
            query.append(operator).append(" b.idAuction.id = ").append(idAuction);
            operator = " AND";
        }
        if (idFantasyClubBidder != null) {
            query.append(operator).append(" b.idFantasyClubBidder.id = ").append(idFantasyClubBidder);
        }
        bids = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (bids.isEmpty()) {
            throw new DataNotFoundException("There is no Bids for this search!");
        } else {
            List<BidPOJO> pojos = BidPOJO.toBidPOJOList(bids);
            String countQuery = query.toString().replaceFirst("b", "count(b)");
            long count = (long) em.createQuery(countQuery).getSingleResult();
            GetObject go = new GetObject();
            go.setCount(count);
            go.setData(pojos);
            return Response.ok().entity(go).build();
        }
    }

}
