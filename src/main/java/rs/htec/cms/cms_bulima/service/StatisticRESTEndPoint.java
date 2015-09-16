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
import rs.htec.cms.cms_bulima.domain.Auction;
import rs.htec.cms.cms_bulima.domain.Bid;
import rs.htec.cms.cms_bulima.domain.FantasyClub;
import rs.htec.cms.cms_bulima.domain.FantasyClubCreditHistory;
import rs.htec.cms.cms_bulima.domain.FantasyManager;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
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

    /**
     * This method returns JSON list of Fantasy Managers for one user. It
     * produces APPLICATION_JSON media type. Example for JSON list: <br/>[
     * {<br/>
     * "idUser": "1",<br/> "firstname": "Wilhelm",<br/> "fantasyClubList":
     * "[1]",<br/>
     * "idFavClub": "null",<br/> "id": "1",<br/> "secondLeague": "0",<br/>
     * "updateTimestamp": "2014-08-01 15:52:31.0",<br/> "username":
     * "WilhelmTest",<br/> "lastname": "Penske",<br/>
     * "createDate": "2014-08-01 15:52:31.0",<br/> "profilePhotoUrl":
     * "null"<br/> },<br/> {<br/>
     * "idUser": "1",<br/> "firstname": "attila ",<br/> "fantasyClubList":
     * "[46146]",<br/>
     * "idFavClub": "null",<br/> "id": "46134",<br/> "secondLeague": "0",<br/>
     * "updateTimestamp": "2015-08-12 11:34:41.0",<br/> "username":
     * "falsbuak",<br/>
     * "lastname": "nemeth",<br/> "createDate": "2015-04-30 19:39:20.0",<br/>
     * "profilePhotoUrl": "null"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param email is email for what user you want Fantasy Managers
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no Fantasy Managers for this user!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("manager/{email}")
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

    /**
     * This method returns JSON list of Fantasy Clubs for one Fantasy Manager.
     * It produces APPLICATION_JSON media type. Example for JSON list: <br/>[
     * {<br/>
     * "lastLogin": "2014-08-04 14:36:54.0",<br/> "totalBLMPoints": "0",<br/>
     * "auctionList": "[]",<br/> "changeLineUpTimestamp": "null",<br/>
     * "activity": "removed",<br/> "firstLogin": "2014-08-04 14:36:54.0",<br/>
     * "isLeagueFounder": "0",<br/>
     * "lastLoginWith": "null",<br/> "isLineUpChangedByCoTrainer": "0",<br/>
     * "sportDirectorActiveTimestamp": "null",<br/> "bidList": "[]",<br/>
     * "idFantasyClubLogo": "293",<br/> "lastQuestionAnswered": "null",<br/>
     * "id": "55",<br/>
     * "credit": "40000000",<br/> "amountSubstituteBench": "0",<br/>
     * "idActiveFormation": "1",<br/> "createDate": "2014-08-04
     * 14:36:54.0",<br/> "idEmblem": "303",<br/>
     * "fantasyPlayerList": "[]",<br/> "idCaptain": "null",<br/>
     * "fantasyClubCreditHistoryList": "[]",<br/> "idFantasyManager": "56",<br/>
     * "updateTimestamp": "2015-08-01 02:30:34.0",<br/> "questionRightAnswered":
     * "0",<br/>
     * "newsList": "[]",<br/> "amountCoTrainer": "0",<br/> "name": "ASV Inter
     * Lupfer 1920",<br/> "amountSubstituteBenchSlots": "0",<br/>
     * "idFantasyLeague": "null"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Fantasy Manager
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception: <br/>{<br/>
     * "errorMessage": "There is no Fantasy Club for this Manager!",<br/>
     * "errorCode": 404 <br/>}
     */
    @GET
    @Path("club/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFantasyClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyClub> fc;
        String query = "SELECT f FROM FantasyClub f JOIN f.idFantasyManager u WHERE u.id = " + id;
        fc = em.createQuery(query).getResultList();
        if (fc.isEmpty()) {
            throw new DataNotFoundException("There is no Fantasy Club for this Manager!");
        } else {
            return Response.ok().entity(helper.getJson(fc)).build();
        }
    }

    /**
     * This method returns JSON list of Auction for one Fantasy Club. It
     * produces APPLICATION_JSON media type. Example for JSON list: <br/>[ {
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
    @Path("auction/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuctionsForClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<Auction> auctions;
        String query = "SELECT a FROM Auction a WHERE a.idFantasyClubSeller.id = " + id;
        auctions = em.createQuery(query).getResultList();
        if (auctions.isEmpty()) {
            throw new DataNotFoundException("There is no Auction for this Club!");
        } else {
            return Response.ok().entity(helper.getJson(auctions)).build();
        }
    }

    /**
     * This method returns JSON list of Bids for one Fantasy Club. It produces
     * APPLICATION_JSON media type. Example for JSON list: <br/>[ {<br/>
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
    @Path("bid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBidsForClub(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<Bid> bids;
        String query = "SELECT b FROM Bid b WHERE b.idFantasyClubBidder.id = " + id;
        bids = em.createQuery(query).getResultList();
        if (bids.isEmpty()) {
            throw new DataNotFoundException("There is no Bids for this Club!");
        } else {
            return Response.ok().entity(helper.getJson(bids)).build();
        }
    }

    /**
     * This method returns JSON list of Bids for one Auction. It produces
     * APPLICATION_JSON media type. Example for JSON list: <br/>[ {<br/>
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
    @Path("auction/bid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBidsForAuction(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<Bid> bids;
        String query = "SELECT b FROM Bid b WHERE b.idAuction.id = " + id;
        bids = em.createQuery(query).getResultList();
        if (bids.isEmpty()) {
            throw new DataNotFoundException("There is no Bids for this Auction!");
        } else {
            return Response.ok().entity(helper.getJson(bids)).build();
        }
    }

    /**
     * This method returns JSON list of Credit History for one Fantasy Club. It
     * produces APPLICATION_JSON media type. Example for JSON list: <br/> [ {
     * <br/> "updatedCredit": "16502803",<br/> "idFantasyClub": "100",<br/>
     * "idAuction": "null",<br/> "action": "QUESTION_OF_THE_DAY",<br/> "id":
     * "380601",<br/> "credit": "10000",<br/> "createDate": "2015-07-29
     * 10:17:30.0"<br/> },<br/> { <br/> "updatedCredit": "18460833",<br/>
     * "idFantasyClub": "100",<br/> "idAuction": "1171683",<br/> "action":
     * "BATCH_AUCTION_SELLING_TO_SPORT1",<br/> "id": "430034",<br/> "credit":
     * "1958030",<br/>
     * "createDate": "2015-07-30 10:25:08.0"<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param id is id of Fantasy Club
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for this exception:<br/> {<br/>
     * "errorMessage": "There is no credit history for this club!",<br/> "errorCode":
     * 404 <br/>}
     */
    @GET
    @Path("creditHistory/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreditHistory(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        List<FantasyClubCreditHistory> creditHistory;
        String query = "SELECT f FROM FantasyClubCreditHistory f WHERE f.idFantasyClub.id = " + id;
        creditHistory = em.createQuery(query).getResultList();
        if (creditHistory.isEmpty()) {
            throw new DataNotFoundException("There is no credit history for this club!");
        } else {
            return Response.ok().entity(helper.getJson(creditHistory)).build();
        }
    }
}
