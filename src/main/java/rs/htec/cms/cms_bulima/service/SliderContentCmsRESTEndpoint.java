/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.SliderContent;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.exception.NotAuthorizedException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/slider")
public class SliderContentCmsRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public SliderContentCmsRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method: /news/{page}/{limit} This method returns JSON list of
     * slider content at defined page with defined limit. It produces
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2
     * limit:<br/> [ {<br/> "contentUrl":
     * "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_dailymessage.jpg",<br/>
     * "redirectUrl": "page=home",<br/> "showForMsec": "5000",<br/>
     * "idCompetition": "null",<br/> "positionInSlider": "1",<br/>
     * "stopShowingAt": "2015-07-22 13:22:48.0",<br/> "updateAt": "2015-02-17
     * 15:27:38.0",<br/> "id": "1",<br/> "text":
     * "{@code <div style=\"position: absolute; font-size: 16px; padding: 10px; line-height: 18px; top: 160px;\"><div style=\"font-family: TitilliumWeb-Bold; font-size: 18px; margin-bottom: 8px;\">}Saisonpause{@code </div>}Wir
     * starten schon bald mit der neuen Saison
     * 2015/2016!{@code </a></div>}",<br/>
     * "startShowingAt": "2015-05-03 18:10:00.0",<br/> "createDate": "2015-02-17
     * 15:59:00.0"<br/> },<br/> {<br/> "contentUrl":
     * "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_patch.jpg",<br/>
     * "redirectUrl": "page=home",<br/> "showForMsec": "5000",<br/>
     * "idCompetition": "null",<br/> "positionInSlider": "1",<br/>
     * "stopShowingAt": "2015-05-03 18:10:00.0",<br/> "updateAt": "2015-02-17
     * 15:27:38.0",<br/> "id": "2",<br/> "text":
     * "{@code <div style=\"position: absolute; font-size: 16px; padding: 10px; line-height: 18px; top: 140px;\"><div style=\"font-family: TitilliumWeb-Bold; font-size: 18px; margin-bottom: 8px;\">}Patch
     * 1.2 ist online:{@code </div>}Wie geht es nächste Saison mit eurer Liga
     * weiter? {@code <br>} Erfahrt mehr dazu auf unserer
     * {@code<a style=\"color:white;font-size:16px\" target=\"_blank\" href=\"https://www.facebook.com/SPORT1BundesligaManager\">}Facebook
     * Seite{@code </a>!</div>}",<br/> "startShowingAt": "2015-03-17
     * 17:27:00.0",<br/>
     * "createDate": "2015-02-17 15:59:00.0"<br/> } ]
     *
     * @param token
     * @param page number of page at which we search for sliders
     * @param limit number of sliders this method returns
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException
     * @throws NotAuthorizedException
     */
    @GET
    @Path("/{page}/{limit}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSlider(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.SEARCH, token);
        List<SliderContent> slider = em.createNamedQuery("SliderContent.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        return Response.ok().entity(helper.getJson(slider)).build();
    }

    /**
     * API for this method is /rest/slider This method recieves JSON object, and
     * put it in the base. Example for JSON:<br/> {<br/> "contentUrl":
     * "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_jerseys_v04.jpg",<br/>
     * "redirectUrl": "page=shop;sub=nspyre",<br/> "showForMsec": "5000",<br/>
     * "positionInSlider": "4",<br/> "stopShowingAt": "2015-05-28
     * 12:05:00.0",<br/>
     * "updateAt": "2015-03-18 16:13:41.0",<br/> "text": "",<br/>
     * "startShowingAt": "2015-03-18 16:30:00.0"<br/> }
     *
     * @param token
     * @param slider
     * @return Response with status CREATED (201)
     * @throws InputValidationException
     * @throws NotAuthorizedException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertSlider(@HeaderParam("authorization") String token, SliderContent slider) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.ADD, token);
        if (validator.checkLenght(slider.getContentUrl(), 255, true) && validator.checkLenght(slider.getRedirectUrl(), 255, true)
                && validator.checkLenght(slider.getText(), 1023, true)) {
            slider.setCreateDate(new Date());
            helper.persistObject(em, slider);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for method: /slider/{id} This method find news with defined id. Id is
     * retrieved from URL. If slider content with that index does not exist
     * method throws exception. Otherwise method remove that slider content.
     *
     * @param token
     * @param id of News that should be deleted.
     * @return Response 200 OK
     * @throws NotAuthorizedException
     */
    @DELETE
    @Path("/{id}")
    public Response deleteSlider(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.DELETE, token);
        SliderContent slider = em.find(SliderContent.class, id);
        helper.removeObject(em, slider, id);
        return Response.ok().build();
    }

    /**
     * API for this method is /rest/slider This method recieves JSON object, and
     * update database. Example for JSON: <br/>{ "contentUrl":
     * "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_jerseys_v04.jpg",<br/>
     * "redirectUrl": "page=shop;sub=nspyre",<br/> "showForMsec": "5000",<br/>
     * "positionInSlider": "4",<br/> "stopShowingAt": "2015-05-28
     * 12:05:00.0",<br/>
     * "updateAt": "2015-03-18 16:13:41.0",<br/> "text": "",<br/>
     * "startShowingAt": "2015-03-18 16:30:00.0"<br/> }
     *
     * @param token
     * @param slider
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException
     * @throws DataNotFoundException
     * @throws NotAuthorizedException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSlider(@HeaderParam("authorization") String token, SliderContent slider) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.EDIT, token);
        SliderContent oldSlider = em.find(SliderContent.class, slider.getId());
        if (oldSlider != null) {
            if (validator.checkLenght(slider.getContentUrl(), 255, true) && validator.checkLenght(slider.getRedirectUrl(), 255, true)
                    && validator.checkLenght(slider.getText(), 1023, true)) {
                slider.setCreateDate(new Date());
                helper.mergeObject(em, slider);
                return Response.ok("Successfully updated!").build();
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Slider at index" + slider.getId() + " does not exits");
        }
    }
}
