/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.Player;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.PlayerPOJO;

/**
 *
 * @author stefan
 */
@Path("/player")
public class PlayerRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    /**
     * Returns Player for defined id.
     * <br/>
     * Example for response:<br/>
     * {<br/>
     * "urlToPlayerPosition":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/playerPosition/1",<br/>
     * "urlToBlockStartMatchday": null,<br/>
     * "urlToClub":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/club/56",<br/>
     * "urlToNation":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/nation/1",<br/>
     * "urlToSeasonCurrent":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/season/3",v
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
     * @param id for Player
     * @return Player
     * @throws DataNotFoundException if Player for id does not exist
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        PlayerPOJO pojo;
        try {
            Player player = (Player) em.createNamedQuery("Player.findById").setParameter("id", id).getSingleResult();
            pojo = new PlayerPOJO(player);
        } catch (NoResultException e) {
            throw new DataNotFoundException("Player at index " + id + " does not exist..");
        }
        return Response.ok().entity(pojo).build();
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
     * @param player - Player in JSON
     * @return - 200 OK if Player is updated
     * @throws InputValidationException if Player properties are not valid
     * @throws DataNotFoundException if Player with id defined in JSON body
     * doesn't exist
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlayer(@HeaderParam("authorization") String token, Player player) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.EDIT, token);
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
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Player at index " + player.getId() + " does not exits");
        }
        return Response.ok().build();
    }
}
