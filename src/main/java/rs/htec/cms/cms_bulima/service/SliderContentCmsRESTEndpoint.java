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

    @GET
    @Path("/{page}/{limit}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSlider(@HeaderParam("authorization") String token, @PathParam("page") int page, @PathParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        try {
            helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.SEARCH, token);
            List<SliderContent> slider = em.createNamedQuery("SliderContent.findAll").setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
            return Response.ok().entity(helper.getJson(slider)).build();
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    /**
     * API for this method is /rest/slider
     *This method recieves JSON object, and put it in the base. Example for JSON:
     *      {
                "contentUrl": "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_jerseys_v04.jpg",
                "redirectUrl": "page=shop;sub=nspyre",
                "showForMsec": "5000",
                "positionInSlider": "4",
                "stopShowingAt": "2015-05-28 12:05:00.0",
                "updateAt": "2015-03-18 16:13:41.0",
                "text": "",
                "startShowingAt": "2015-03-18 16:30:00.0"
            }
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
        try {
            helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.ADD, token);
            if (validator.checkLenght(slider.getContentUrl(), 255, true) && validator.checkLenght(slider.getRedirectUrl(), 255, true)
                    && validator.checkLenght(slider.getText(), 1023, true)) {
                slider.setCreateDate(new Date());
                helper.persistObject(em, slider);
                return Response.status(Response.Status.CREATED).build();
            } else {
                throw new InputValidationException("Validation failed");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSlider(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();

        try {
            helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.DELETE, token);
            SliderContent slider = em.find(SliderContent.class, id);
            helper.removeObject(em, slider, id);
            return Response.ok().build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }

    /**
     * API for this method is /rest/slider
     *This method recieves JSON object, and update database. Example for JSON:
     *      {
                "contentUrl": "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_jerseys_v04.jpg",
                "redirectUrl": "page=shop;sub=nspyre",
                "showForMsec": "5000",
                "positionInSlider": "4",
                "stopShowingAt": "2015-05-28 12:05:00.0",
                "updateAt": "2015-03-18 16:13:41.0",
                "text": "",
                "startShowingAt": "2015-03-18 16:30:00.0"
            }
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
        try {
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
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NewsCmsRESTEndpoint.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("You are not logged in!");
        }
    }
}
