/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
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
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.domain.Formation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.LineUpDifference;
import rs.htec.cms.cms_bulima.helper.LineUpWrapper;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/lineup")
public class FantasyClubLineUpRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public FantasyClubLineUpRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    @GET
    @Path("/{idFantasyClub}/{idMatchday}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLineUp(@HeaderParam("authorization") String token, @PathParam("idFantasyClub") long idFantasyClub,
            @PathParam("idMatchday") long idMatchday) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        LineUpWrapper lineUpWrapper = new LineUpWrapper();

        List<FantasyClubLineUpPlayer> lineUpPlayer = lineUpWrapper.getLineUpList(idFantasyClub, idMatchday);
        return Response.ok().entity(helper.getJson(lineUpPlayer)).build();
    }

    @GET
    @Path("/difference")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDifference(@HeaderParam("authorization") String token, @QueryParam("idFantasyClub1") long idFantasyClub1,
            @QueryParam("idFantasyClub2") long idFantasyClub2, @QueryParam("idMatchday1") long idMatchday1, @QueryParam("idMatchday2") long idMatchday2) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        LineUpWrapper lineUpWrapper = new LineUpWrapper();
        LineUpDifference difference = new LineUpDifference();
        Formation f1 = lineUpWrapper.getFormation(idFantasyClub1, idMatchday1);
        Formation f2 = lineUpWrapper.getFormation(idFantasyClub2, idMatchday2);
        Formation fDiff = lineUpWrapper.returnDifferenceForFormation(f1, f2);
        difference.setFormationDifference(fDiff);
        List<FantasyClubLineUpPlayer> lineUpPlayer1 = lineUpWrapper.getLineUpList(idFantasyClub1, idMatchday1);
        List<FantasyClubLineUpPlayer> lineUpPlayer2 = lineUpWrapper.getLineUpList(idFantasyClub2, idMatchday2);

        List<FantasyClubLineUpPlayer> lineUpDifference = lineUpWrapper.returnDifference(lineUpPlayer1, lineUpPlayer2);
        difference.setLineUpDifference(helper.getJsonArray(lineUpDifference));
        return Response.ok().entity(difference).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewsById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        FantasyClubLineUp fantasyClubLineUp = null;
        try {
            fantasyClubLineUp = (FantasyClubLineUp) em.createNamedQuery("FantasyClubLineUp.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("Fantasy Club Line Up at index " + id + " does not exist..");
        }

        return Response.ok().entity(fantasyClubLineUp).build();
    }
}
