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
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Auction;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
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
     * API for this method: .../rest/auctions/{id} This method returns
     * JSON list of Auction for one Fantasy Club. It produces APPLICATION_JSON
     * media type. Example for JSON list: <br/>[ {
     * <br/>"startPrice": "100000",<br/> "idFantasyClubSeller": "27434",<br/>
     * "auctionEndTimestamp": "2015-07-20 15:32:35.0",<br/>
     * "fantasyClubCreditHistoryList": "[]",<br/>
     * "auctionStartTimestamp": "2015-07-20 15:32:35.0",<br/>
     * "idFantasyLeaguePlayer": "1589762",<br/> "id": "51",<br/> "isFair":
     * "1",<br/>
     * "auctionFinishedTimestamp": "2015-07-20 15:32:35.0",<br/> "createDate":
     * "2015-07-20 15:32:35.0",<br/> "idFantasyLeague": "7175",<br/> "bidList":
     * "[]"<br/> },<br/> {<br/>
     * "startPrice": "3675000",<br/> "idFantasyClubSeller": "27434",<br/>
     * "auctionEndTimestamp": "2015-07-21 15:39:28.0",<br/>
     * "fantasyClubCreditHistoryList": "[1877]",<br/> "auctionStartTimestamp":
     * "2015-07-20 15:39:28.0",<br/> "idFantasyLeaguePlayer": "1589269",<br/>
     * "id": "102",<br/>
     * "isFair": "1",<br/> "auctionFinishedTimestamp": "2015-07-21
     * 15:45:00.0",<br/>
     * "createDate": "2015-07-20 15:39:28.0",<br/> "idFantasyLeague":
     * "7175",<br/>
     * "bidList": "[]" <br/>} ]
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Fantasy Club
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception: <br/>{<br/>
     * "errorMessage": "There is no Auction for this Club!",<br/> "errorCode":
     * 404<br/> }
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuctionsForClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<Auction> auctions;
        StringBuilder query = new StringBuilder("SELECT a FROM Auction a WHERE a.idFantasyClubSeller.id = ");
        query.append(id);
        auctions = em.createQuery(query.toString()).getResultList();
        if (auctions.isEmpty()) {
            throw new DataNotFoundException("There is no Auction for this Club!");
        } else {
            List<AuctionPOJO> pojos = new ArrayList<>();
            for (Auction a : auctions) {
                pojos.add(new AuctionPOJO(a));
            }
            return Response.ok().entity(pojos).build();
        }
    }

}
