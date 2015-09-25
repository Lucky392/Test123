/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
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
import rs.htec.cms.cms_bulima.domain.Bid;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
@Path("/bid")
public class BidRESTEndPoint {

    RestHelperClass helper;

    public BidRESTEndPoint() {
        helper = new RestHelperClass();
    }

    /**
     * API for this method: .../rest/bid/{id} This method returns
     * JSON list of Bids for one Fantasy Club. It produces APPLICATION_JSON
     * media type. Example for JSON list: <br/>[ {<br/>
     * "bidManagerPrice": "0",<br/> "idAuction": "1203343",<br/> "bidManager":
     * "0",<br/>
     * "idFantasyClubBidder": "100",<br/> "id": "187030",<br/> "biddingPrice":
     * "7831000",<br/>
     * "returnMoneyTimestamp": "null",<br/> "biddingAtTimestamp": "2015-08-01
     * 09:46:40.0",<br/> "createDate": "2015-08-01 09:46:39.0"<br/> },<br/>
     * {<br/>
     * "bidManagerPrice": "0",<br/> "idAuction": "2137720",<br/> "bidManager":
     * "0",<br/>
     * "idFantasyClubBidder": "100",<br/> "id": "257568",<br/> "biddingPrice":
     * "12500000",<br/>
     * "returnMoneyTimestamp": "null",<br/> "biddingAtTimestamp": "2015-08-05
     * 19:07:47.0",<br/> "createDate": "2015-08-05 19:07:46.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Fantasy Club
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception: <br/>{<br/>
     * "errorMessage": "There is no Bids for this Club!",<br/> "errorCode":
     * 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBidsForClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<Bid> bids;
        StringBuilder query = new StringBuilder("SELECT b FROM Bid b WHERE b.idFantasyClubBidder.id = ");
        query.append(id);
        bids = em.createQuery(query.toString()).getResultList();
        if (bids.isEmpty()) {
            throw new DataNotFoundException("There is no Bids for this Club!");
        } else {
            return Response.ok().entity(bids).build();
        }
    }

    /**
     * API for this method: .../rest/bid/auction/{id} This method
     * returns JSON list of Bids for one Auction. It produces APPLICATION_JSON
     * media type. Example for JSON list: <br/>[ {<br/>
     * "bidManagerPrice": "0",<br/> "idAuction": "2137720",<br/> "bidManager":
     * "0",<br/>
     * "idFantasyClubBidder": "100",<br/> "id": "257568",<br/> "biddingPrice":
     * "12500000",<br/>
     * "returnMoneyTimestamp": "null",<br/> "biddingAtTimestamp": "2015-08-05
     * 19:07:47.0",<br/> "createDate": "2015-08-05 19:07:46.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Auction
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no Bids for this Auction!",<br/> "errorCode":
     * 404<br/> }
     */
    @GET
    @Path("/auction/{auctionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBidsForAuction(@HeaderParam("authorization") String token, @PathParam("auctionId") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<Bid> bids;
        StringBuilder query = new StringBuilder("SELECT b FROM Bid b WHERE b.idAuction.id = ");
        query.append(id);
        bids = em.createQuery(query.toString()).getResultList();
        if (bids.isEmpty()) {
            throw new DataNotFoundException("There is no Bids for this Auction!");
        } else {
            return Response.ok().entity(bids).build();
        }
    }
}