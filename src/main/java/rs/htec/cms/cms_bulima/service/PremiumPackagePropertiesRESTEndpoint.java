/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.PremiumAction;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author lazar
 */
@Path("/premium_package_content")
public class PremiumPackagePropertiesRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public PremiumPackagePropertiesRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method: .../rest/premium_action?page=VALUE&limit=VALUE This
     * method returns JSON list. Default value for page is 1, and for limit is
     * 10. You can put your values for page, limit. It produces APPLICATION_JSON
     * media type. Example for JSON list for 1 page, 2 limit: <br/>
     * [{<br/>
     * "createDate": 1418727045000,<br/>
     * "name": "Sofortverkauf",<br/>
     * "id": 1<br/>
     * },<br/>
     * {<br/>
     * "createDate": 1418727045000,<br/>
     * "name": "Question of the Day",<br/>
     * "id": 2 <br/>
     * }]<br/>
     * You can also
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for Premium actions
     * @param limit number of Premium actions method returns
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     *
     */
    @GET  //question?page=1&limit=10&minDate=1438387200000&maxDate=1439164800000&search=Viktor&column=id
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumActions(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
        List<PremiumAction> premiumActions = em.createNamedQuery("PremiumAction.findAll").getResultList();
        if (premiumActions == null || premiumActions.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        return Response.ok().entity(premiumActions).build();
    }

    /**
     * API for this method is .../rest/premium_action This method recieves JSON
     * object, and put it in the base. Example for JSON that you need to send:
     * <br/>
     * {<br/>
     * "name": "bla bla" <br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPremiumAction(@HeaderParam("authorization") String token, PremiumAction premiumActions) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.ADD, token);
        premiumActions.setCreateDate(new Date());
        if (validator.checkLenght(premiumActions.getName(), 255, true)) {
            helper.persistObject(em, premiumActions);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for method: .../rest/premium_action/{id} This method find
     * premium_action with defined id. Id is retrieved from URL. If Premium
     * actions with that index does not exist method throws exception. Otherwise
     * method remove that Premium action. If Premium action is used, it can't be
     * deleted.
     *
     * @param token is a header parameter for checking permission
     * @param id of Premium action that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deletePremiumAction(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.DELETE, token);
        PremiumAction premiumActions = em.find(PremiumAction.class, id);
        helper.removeObject(em, premiumActions, id);
        return Response.ok().build();
    }

    /**
     * API for this method is .../rest/premium_action This method recieves JSON
     * object, and update database. Example for JSON that you need to send:<br/>
     * {<br/>
     * "name": "bla bla 1123",<br/>
     * "id": 15<br/> }
     *
     * @param token is a header parameter for checking permission
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "News at index 15 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePremiumAction(@HeaderParam("authorization") String token, PremiumAction premiumAction) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token);
        PremiumAction oldPremiumAction = em.find(PremiumAction.class, premiumAction.getId());
        if (oldPremiumAction != null) {
            if (validator.checkLenght(premiumAction.getName(), 255, true)) {
                premiumAction.setCreateDate(oldPremiumAction.getCreateDate());
                helper.mergeObject(em, premiumAction);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Premium action at index" + premiumAction.getId() + " does not exits");
        }

        return Response.ok("Successfully updated!").build();
    }

}
