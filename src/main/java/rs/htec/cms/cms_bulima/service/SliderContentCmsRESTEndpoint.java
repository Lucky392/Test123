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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import rs.htec.cms.cms_bulima.domain.SliderContent;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/sliders")
public class SliderContentCmsRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * API for method: .../rest/sliders?page=VALUE&limit=VALUE&order= This
     * method returns JSON list of slider content at defined page with defined
     * limit. Default value for page is 1, and for limit is 10. It produces
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
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for sliders
     * @param limit number of sliders this method returns
     * @param order orders by defined column in ascending order (if with - then
     * descending)
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no sliders!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSlider(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("order") String order) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        StringBuilder query = new StringBuilder("SELECT s FROM SliderContent s");
        if (order != null) {
            if (order.startsWith("-")) {
                order = order.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(order);
        }
        List<SliderContent> slider = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (slider.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("There is no sliders!"), em);
            throw new DataNotFoundException("There is no sliders!");
        }
        String countQuery = "Select COUNT(ip) From SliderContent ip";
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(slider);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/sliders/{id} This method return single element
     * of slider at index in JSON. Example for JSON response: <br/>{<br/>
     * "contentUrl":
     * "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_dailymessage.jpg",<br/>
     * "redirectUrl": "page=home",<br/> "showForMsec": "5000",<br/>
     * "idCompetition": "null",<br/> "positionInSlider": "1",<br/>
     * "stopShowingAt": "2015-07-22 13:22:48.0",<br/> "updateAt": "2015-02-17
     * 15:27:38.0",<br/> "id": "1",<br/> "text":
     * "{@code <div style=\"position: absolute; font-size: 16px; padding: 10px; line-height: 18px; top: 160px;\"><div style=\"font-family: TitilliumWeb-Bold; font-size: 18px; margin-bottom: 8px;\">}Saisonpause{@code </div>}Wir
     * starten schon bald mit der neuen Saison
     * 2015/2016!{@code </a></div>}",<br/>
     * "startShowingAt": "2015-05-03 18:10:00.0",<br/> "createDate": "2015-02-17
     * 15:59:00.0"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of slider we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSliderById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        SliderContent slider = null;
        try {
            slider = (SliderContent) em.createNamedQuery("SliderContent.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Slider content at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Slider content at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(slider).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/sliders This method recieves JSON object,
     * and put it in the base. Example for JSON:<br/> {<br/> "contentUrl":
     * "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_jerseys_v04.jpg",<br/>
     * "redirectUrl": "page=shop;sub=nspyre",<br/> "showForMsec": "5000",<br/>
     * "positionInSlider": "4",<br/> "stopShowingAt": "2015-05-28
     * 12:05:00.0",<br/>
     * "updateAt": "2015-03-18 16:13:41.0",<br/> "text": "",<br/>
     * "startShowingAt": "2015-03-18 16:30:00.0"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param slider is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertSlider(@HeaderParam("authorization") String token, @Context HttpServletRequest request, SliderContent slider) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), slider);
        if (slider.getContentUrl() != null && slider.getStartShowingAt() != null && slider.getStopShowingAt() != null
                && validator.checkLenght(slider.getContentUrl(), 255, true) && validator.checkLenght(slider.getRedirectUrl(), 255, true)
                && validator.checkLenght(slider.getText(), 1023, true)) {
            if (slider.getPositionInSlider() == null) {
                slider.setPositionInSlider(0);
            }
            if (slider.getText() == null) {
                slider.setText("");
            }
            if (slider.getRedirectUrl() == null) {
                slider.setText("");
            }
            if (slider.getShowForMsec() == null) {
                slider.setShowForMsec(5000);
            }
            if (slider.getRedirectUrl() == null) {
                slider.setRedirectUrl("");
            }
            slider.setCreateDate(new Date());
            slider.setUpdateAt(new Date());
            helper.persistObject(em, slider);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for method: .../rest/sliders/{id} This method find slider with
     * defined id. Id is retrieved from URL. If slider content with that index
     * does not exist method throws exception. Otherwise method remove that
     * slider content.
     *
     * @param token is a header parameter for checking permission
     * @param id of News that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteSlider(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.DELETE, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        SliderContent slider = em.find(SliderContent.class, id);
        helper.removeObject(em, slider, id);
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/sliders This method recieves JSON object,
     * and update database. Example for JSON: <br/>{ "contentUrl":
     * "http://assets.bundesligamanager.htec.co.rs/home_slider/sl_jerseys_v04.jpg",<br/>
     * "redirectUrl": "page=shop;sub=nspyre",<br/> "showForMsec": "5000",<br/>
     * "positionInSlider": "4",<br/> "stopShowingAt": "2015-05-28
     * 12:05:00.0",<br/>
     * "updateAt": "2015-03-18 16:13:41.0",<br/> "text": "",<br/>
     * "startShowingAt": "2015-03-18 16:30:00.0"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param slider is an object that Jackson convert from JSON to object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Slider at index 54 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSlider(@HeaderParam("authorization") String token, @Context HttpServletRequest request, SliderContent slider) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), slider);
        SliderContent oldSlider = em.find(SliderContent.class, slider.getId());
        if (oldSlider != null) {
            if (slider.getContentUrl() != null && slider.getStartShowingAt() != null && slider.getStopShowingAt() != null
                    && validator.checkLenght(slider.getContentUrl(), 255, true) && validator.checkLenght(slider.getRedirectUrl(), 255, true)
                    && validator.checkLenght(slider.getText(), 1023, true)) {
                if (slider.getPositionInSlider() == null) {
                    slider.setPositionInSlider(0);
                }
                if (slider.getText() == null) {
                    slider.setText("");
                }
                if (slider.getRedirectUrl() == null) {
                    slider.setText("");
                }
                if (slider.getShowForMsec() == null) {
                    slider.setShowForMsec(5000);
                }
                if (slider.getRedirectUrl() == null) {
                    slider.setRedirectUrl("");
                }
                slider.setCreateDate(oldSlider.getCreateDate());
                slider.setUpdateAt(new Date());
                helper.mergeObject(em, slider);
                Response response = Response.ok().build();
                helper.setResponseToHistory(history, response, em);
                return response;
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Slider at index" + slider.getId() + " does not exits"), em);
            throw new DataNotFoundException("Slider at index" + slider.getId() + " does not exits");
        }
    }

    /**
     * API for this method: .../rest/sliders/count This method return number of
     * all sliders in database.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountSlider(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SLIDER_CONTENT, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "Select COUNT(ip) From SliderContent ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        Response response = Response.ok().entity(count).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

}
