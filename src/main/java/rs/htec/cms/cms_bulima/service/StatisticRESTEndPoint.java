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
            throw new DataNotFoundException("There is no Auctions for this Club!");
        } else {
            return Response.ok().entity(helper.getJson(auctions)).build();
        }
    }
 
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
