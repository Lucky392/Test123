/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsActionHistory;
import rs.htec.cms.cms_bulima.domain.Player;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.PlayerFilters;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.PlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/players")
public class PlayerRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * API for this call is : /rest/players. JSON that you need to send:
     * <br/>{<br/>
     * "search" : "Tom",<br/> "clubName" : "Vfl",<br/> "playerPosition" :
     * "Midfield",<br/>
     * "nation" : "Deutschland",<br/> "isCaptain": 0,<br/> "hasYellow": 0,<br/>
     * "hasRed": 0,<br/>
     * "isHurt" : 0,<br/> "minSizeCm" : 179,<br/> "maxSizeCm" : 181,<br/>
     * "minWeightKg" : 69,<br/>
     * "maxWeightKg" : 71,<br/> "maxAge" : 16,<br/> "minAge" : 15,<br/>
     * "maxMatchesTopLeage" : 4,<br/> "minMatchesTopLeage" : 0,<br/>
     * "maxMarketValue" : 6,<br/>
     * "minMarketValue" : 0 <br/>}
     *
     * JSON object that you receive: <br/>{ <br/>"count": 1,<br/> "data": [<br/>
     * {<br/>
     * "urlToPlayerPosition":
     * "http://bulima-api-staging.htec.co.rs/CMS_Bulima-1.0/rest/playerPositions/3",<br/>
     * "urlToNation":
     * "http://bulima-api-staging.htec.co.rs/CMS_Bulima-1.0/rest/nations/1",<br/>
     * "urlToSeasonCurrent":
     * "http://bulima-api-staging.htec.co.rs/CMS_Bulima-1.0/rest/seasons/4",<br/>
     * "urlToBlockStartMatchday": null,<br/> "blockStartMatchday": null,<br/>
     * "urlToClub":
     * "http://bulima-api-staging.htec.co.rs/CMS_Bulima-1.0/rest/clubs/69",<br/>
     * "seasonCurrent": "2015/2016",<br/> "club": "VfL Bochum",<br/> "nation":
     * "Deutschland",<br/> "idSport1Player": "361679",<br/> "shirtNumber":
     * "31",<br/>
     * "firstName": "Tom",<br/> "photoUrl2":
     * "http://assets.bundesligamanager.htec.co.rs/images/bulima-player-card/photos/playerpic_Tom_Baack.png",<br/>
     * "lastName": "Baack",<br/> "blockType": null,<br/> "trikotName": "Tom
     * Baack",<br/>
     * "fullname": "Tom Baack",<br/> "photoUrl": null,<br/> "createDate":
     * 1437385002000,<br/>
     * "playerPosition": "Midfield",<br/> "marketValue": 0,<br/> "isCaptain":
     * 0,<br/>
     * "updateAt": 1439784076000,<br/> "totalBLMPoints": 0,<br/> "sizeCm":
     * 180,<br/>
     * "weightKg": 70,<br/> "dateOfBirth": 921279600000,<br/> "dateJoinedTeam":
     * 1435701600000,<br/> "matchesTopLeage": 0,<br/> "scoreCountTopLeague":
     * 0,<br/>
     * "gradeAutoSeason": 0,<br/> "gradeAutoSeasonLeagueAvg": 0,<br/> "isHurt":
     * 0,<br/>
     * "hasYellowCard": 0,<br/> "hasYellowRedCard": 0,<br/> "hasRedCard":
     * 0,<br/>
     * "blockMatchdayAmount": 0,<br/> "idPlayerPosition": 3,<br/> "idNation":
     * 1,<br/>
     * "idSeasonCurrent": 4,<br/> "idBlockStartMatchday": null,<br/> "idClub":
     * 69,<br/>
     * "marketValueUpdateAt": 1439546092000,<br/> "blmPointsDiff": 0,<br/> "id":
     * 5736<br/> }<br/> ]<br/> }
     *
     * @param token
     * @param request
     * @param page
     * @param limit
     * @param filters
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPlayers(@HeaderParam("authorization") String token,@Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, PlayerFilters filters) {
        EntityManager em = EMF.createEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), filters);
        StringBuilder query = new StringBuilder("SELECT p FROM Player p ");
        String operator = "WHERE";
        if (filters.getSearch() != null) {
            query.append(operator)
                    .append(" (p.firstName LIKE '%")
                    .append(filters.getSearch())
                    .append("%' OR p.lastName LIKE '%")
                    .append(filters.getSearch())
                    .append("%'")
                    .append(" OR p.fullname LIKE '%")
                    .append(filters.getSearch())
                    .append("%')");
            operator = "AND";
        }
        if (filters.getClubName() != null) {
            query.append(operator)
                    .append(" p.idClub.mediumName LIKE '%")
                    .append(filters.getClubName())
                    .append("%'");
            operator = "AND";
        }
        if (filters.getPlayerPosition() != null) {
            query.append(operator)
                    .append(" p.idPlayerPosition.name LIKE '%")
                    .append(filters.getPlayerPosition())
                    .append("%'");
            operator = "AND";
        }
        if (filters.getNation() != null) {
            query.append(operator)
                    .append(" p.idNation.name LIKE '%")
                    .append(filters.getNation())
                    .append("%'");
            operator = "AND";
        }
        if (filters.getIsCaptain() != null) {
            query.append(operator)
                    .append(" p.isCaptain = ").append(filters.getIsCaptain() ? 1 : 0);
            operator = "AND";
        }
        if (filters.getHasYellow() != null) {
            query.append(operator)
                    .append(" p.hasYellowCard = ").append(filters.getHasYellow() ? 1 : 0);
            operator = "AND";
        }
        if (filters.getHasRed() != null) {
            query.append(operator)
                    .append(" p.hasRedCard = ").append(filters.getHasRed() ? 1 : 0);
            operator = "AND";
        }
        if (filters.getIsHurt() != null) {
            query.append(operator)
                    .append(" p.isHurt = ").append(filters.getIsHurt() ? 1 : 0);
            operator = "AND";
        }
        if (filters.getMinSizeCm() != null) {
            query.append(operator)
                    .append(" p.sizeCm >= ")
                    .append(filters.getMinSizeCm());
            operator = "AND";
        }
        if (filters.getMaxSizeCm() != null) {
            query.append(operator)
                    .append(" p.sizeCm <= ")
                    .append(filters.getMaxSizeCm());
            operator = "AND";
        }
        if (filters.getMinWeightKg() != null) {
            query.append(operator)
                    .append(" p.weightKg >= ")
                    .append(filters.getMinWeightKg());
            operator = "AND";
        }
        if (filters.getMaxWeightKg() != null) {
            query.append(operator)
                    .append(" p.weightKg <= ")
                    .append(filters.getMaxWeightKg());
            operator = "AND";
        }
        if (filters.getMinAge() != null) {
            query.append(operator)
                    .append(" TIMESTAMPDIFF(year,p.dateOfBirth,CURDATE()) >= ")
                    .append(filters.getMinAge());
            operator = "AND";
        }
        if (filters.getMaxAge() != null) {
            query.append(operator)
                    .append(" TIMESTAMPDIFF(year,p.dateOfBirth,CURDATE()) <= ")
                    .append(filters.getMaxAge());
            operator = "AND";
        }
        if (filters.getMaxMarketValue() != null) {
            query.append(operator)
                    .append(" p.marketValue <= ")
                    .append(filters.getMaxMarketValue());
            operator = "AND";
        }
        if (filters.getMinMarketValue() != null) {
            query.append(operator)
                    .append(" p.marketValue >= ")
                    .append(filters.getMinMarketValue());
            operator = "AND";
        }
        if (filters.getMaxMatchesTopLeage() != null) {
            query.append(operator)
                    .append(" p.matchesTopLeage <=")
                    .append(filters.getMaxMatchesTopLeage());
            operator = "AND";
        }
        if (filters.getMinMatchesTopLeage() != null) {
            query.append(operator)
                    .append(" p.matchesTopLeage >=")
                    .append(filters.getMinMatchesTopLeage());
        }
        List<Player> players = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (players == null || players.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no Players for this search!"), em);
            throw new DataNotFoundException("There is no Players for this search!");
        }
        String countQuery = query.toString().replaceFirst("p", "count(p)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(PlayerPOJO.toPlayerPOJOList(players));
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * Returns Player for defined id.
     * <br/>
     * Example for response:<br/>
     * {<br/>
     * "urlToPlayerPosition":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerPositions/1",<br/>
     * "urlToBlockStartMatchday": null,<br/>
     * "urlToClub":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/clubs/56",<br/>
     * "urlToNation":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/nations/1",<br/>
     * "urlToSeasonCurrent":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/seasons/3",v
     * "createDate": 1407424285000,<br/>
     * "fullname": "Timo Horn",<br/>
     * "updateAt": 1439784030000,<br/>
     * "marketValue": 5781000,<br/>
     * "isCaptain": 0,<br/>
     * "totalBLMPoints": 4,<br/>
     * "marketValueUpdateAt": 1439771430000,<br/>
     * "blmPointsDiff": 4,<br/>
     * "idSport1Player": "223764",<br/>
     * "firstName": "Timo",<br/>
     * "lastName": "Horn",<br/>
     * "trikotName": "Timo Horn",<br/>
     * "sizeCm": 191,<br/>
     * "weightKg": 76,<br/>
     * "dateOfBirth": 737157600000,<br/>
     * "photoUrl": null,<br/>
     * "dateJoinedTeam": 1341093600000,<br/>
     * "shirtNumber": "1",<br/>
     * "matchesTopLeage": 0,<br/>
     * "scoreCountTopLeague": 0,<br/>
     * "gradeAutoSeason": 0,<br/>
     * "gradeAutoSeasonLeagueAvg": 0,<br/>
     * "isHurt": 0,<br/>
     * "hasYellowCard": 0,<br/>
     * "hasYellowRedCard": 0,<br/>
     * "hasRedCard": 0,<br/>
     * "photoUrl2":
     * "http://assets.bundesligamanager.htec.co.rs/images/bulima-player-card/photos<br/>/playerpic_Timo_Horn.png",
     * "blockType": null,<br/>
     * "blockMatchdayAmount": 0,<br/>
     * "idPlayerPosition": 1,<br/>
     * "idNation": 1,<br/>
     * "idSeasonCurrent": 3,<br/>
     * "idBlockStartMatchday": null,<br/>
     * "idClub": 56,<br/>
     * "id": 3673<br/>
     * }<br/>
     *
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param id for Player
     * @return Player
     * @throws DataNotFoundException if Player for id does not exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        PlayerPOJO pojo;
        try {
            Player player = (Player) em.createNamedQuery("Player.findById").setParameter("id", id).getSingleResult();
            pojo = new PlayerPOJO(player);
        } catch (NoResultException e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Player at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Player at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(pojo).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * Updates Player in DB based on JSON object in body. <br/>
     * Example for json body:<br/>
     * {<br/>
     * "fullname": "Daniel Halfar",<br/>
     * "marketValue": 0,<br/>
     * "isCaptain": 0,<br/>
     * "totalBLMPoints": 5,<br/>
     * "marketValueUpdateAt": 1437438630000,<br/>
     * "blmPointsDiff": 1,<br/>
     * "idSport1Player": "1874",<br/>
     * "firstName": "Daniel",<br/>
     * "lastName": "Halfar",<br/>
     * "trikotName": "Daniel Halfar",<br/>
     * "sizeCm": 174,<br/>
     * "weightKg": 65,<br/>
     * "dateOfBirth": 568508400000,<br/>
     * "photoUrl": null,<br/>
     * "dateJoinedTeam": 1435701600000,<br/>
     * "shirtNumber": "28",<br/>
     * "matchesTopLeage": 0,<br/>
     * "scoreCountTopLeague": 0,<br/>
     * "gradeAutoSeason": 0,<br/>
     * "gradeAutoSeasonLeagueAvg": 0,<br/>
     * "isHurt": 0,<br/>
     * "hasYellowCard": 0,<br/>
     * "hasYellowRedCard": 0,<br/>
     * "hasRedCard": 0,<br/>
     * "photoUrl2":
     * "http://assets.bundesligamanager.htec.co.rs/images/bulima-player-card/photos/playerpic_Daniel_Halfar.png",<br/>
     * "blockType": null,<br/>
     * "blockMatchdayAmount": 0,<br/>
     * "idPlayerPosition": 3,<br/>
     * "idNation": 1,<br/>
     * "idSeasonCurrent": 4,<br/>
     * "idBlockStartMatchday": null,<br/>
     * "idClub": 3,<br/>
     * "id": 3690<br/>
     * }<br/>
     *
     * @param token - header parameter for checking permission
     * @param request
     * @param player - Player in JSON
     * @return - 200 OK if Player is updated
     * @throws InputValidationException if Player properties are not valid
     * @throws DataNotFoundException if Player with id defined in JSON body
     * doesn't exist
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlayer(@HeaderParam("authorization") String token, @Context HttpServletRequest request, Player player) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), player);
        Player foundedPlayer = em.find(Player.class, player.getId());
        if (foundedPlayer != null) {
            Validator validator = new Validator();
            if (validator.checkLenght(player.getIdSport1Player(), 255, true) && validator.checkLenght(player.getShirtNumber(), 255, true)
                    && validator.checkLenght(player.getFirstName(), 255, true) && validator.checkLenght(player.getPhotoUrl2(), 255, true)
                    && validator.checkLenght(player.getLastName(), 255, true) && validator.checkLenght(player.getBlockType(), 255, true)
                    && validator.checkLenght(player.getTrikotName(), 255, true) && validator.checkLenght(player.getFullname(), 255, true)
                    && validator.checkLenght(player.getPhotoUrl(), 255, true)) {
                player.setUpdateAt(new Date());
                player.setCreateDate(foundedPlayer.getCreateDate());
                helper.mergeObject(em, player);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Player at index " + player.getId() + " does not exits"), em);
            throw new DataNotFoundException("Player at index " + player.getId() + " does not exits");
        }
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
