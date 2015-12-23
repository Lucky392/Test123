/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
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
import rs.htec.cms.cms_bulima.domain.PremiumPackageProperties;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.PremiumPackagePropertiesPOJO;

/**
 *
 * @author lazar
 */
@Path("/premium_package_properties")
public class PremiumPackagePropertiesRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * New fields added:[googlePlayStoreId,itunesStoreId,amazonStoreId] API for
     * method:
     * .../rest/premium_package_properties?forPayingUser=VALUE&forNonPayingUser=VALUE
     * This method returns JSON list. Default value for forPayingUser is 0, and
     * for forNonPayingUser is 0. You can put your values for forPayingUser,
     * forNonPayingUser. It produces APPLICATION_JSON media type. Example for
     * JSON list: <br/>
     * {<br/>
     * "count": 1,<br/>
     * "data": [<br/>
     * {<br/>
     * "createDate": 1427204490000,<br/>
     * "redirectUrl": "",<br/>
     * "charityDescription": "",<br/>
     * "charityDonation": null,<br/>
     * "forNonPayingUsers": 0,<br/>
     * "forPayingUsers": 0,<br/>
     * "highlightImageUrl": "",<br/>
     * "showUntil": null,<br/>
     * "idFavoriteClub": null,<br/>
     * "idPremiumPackageSuccessor": 8,<br/>
     * "idPremiumPackageUpgrade": null,<br/>
     * "imageUrlSpecial": "",<br/>
     * "maxPurchasesPerUser": null,<br/>
     * "redirectImageUrl": "",<br/>
     * "redirectPositionLeft": null,<br/>
     * "redirectPositionTop": null,<br/>
     * "updateTimestamp": null,<br/>
     * "showOnlySpecial": 0,<br/>
     * "showFrom": null,<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "id": 1<br/>
     * }<br/>
     * }]<br/>
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param forPayingUser - true if you want only premium package properties
     * for paying users
     * @param forNonPayingUser - true if you want only premium package
     * properties for non paying users
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     *
     */
    @GET  //question?page=1&limit=10&minDate=1438387200000&maxDate=1439164800000&search=Viktor&column=id
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumPackageProperties(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("false") @QueryParam("forPayingUser") boolean forPayingUser,
            @DefaultValue("false") @QueryParam("forNonPayingUser") boolean forNonPayingUser) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<PremiumPackageProperties> premiumPackageProperties;
        if (forPayingUser) {
            premiumPackageProperties = em.createNamedQuery("PremiumPackageProperties.findByForPayingUsers").setParameter("forPayingUsers", Short.parseShort("1")).getResultList();
        } else {
            if (forNonPayingUser) {
                premiumPackageProperties = em.createNamedQuery("PremiumPackageProperties.findByForNonPayingUsers").setParameter("forNonPayingUsers", Short.parseShort("1")).getResultList();
            } else {
                premiumPackageProperties = em.createNamedQuery("PremiumPackageProperties.findAll").getResultList();
            }
        }
        if (premiumPackageProperties == null || premiumPackageProperties.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("Requested page does not exist.."), em);
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = "Select COUNT(p) From PremiumPackageProperties p";
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(PremiumPackagePropertiesPOJO.toPremiumPackagePropertiesPOJOList(premiumPackageProperties));
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/premium_package_properties/{id} This method
     * return single element of package properties at index in JSON. Example for
     * JSON response:  <br/>
     * {<br/>
     * "createDate": 1427204490000,<br/>
     * "redirectUrl": "",<br/>
     * "charityDescription": "",<br/>
     * "charityDonation": null,<br/>
     * "forNonPayingUsers": 0,<br/>
     * "forPayingUsers": 0,<br/>
     * "highlightImageUrl": "",<br/>
     * "showUntil": null,<br/>
     * "idFavoriteClub": null,<br/>
     * "idPremiumPackageSuccessor": 8,<br/>
     * "idPremiumPackageUpgrade": null,<br/>
     * "imageUrlSpecial": "",<br/>
     * "maxPurchasesPerUser": null,<br/>
     * "redirectImageUrl": "",<br/>
     * "redirectPositionLeft": null,<br/>
     * "redirectPositionTop": null,<br/>
     * "updateTimestamp": null,<br/>
     * "showOnlySpecial": 0,<br/>
     * "showFrom": null,<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "id": 1<br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of premium package properties we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackagePropertiesById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        PremiumPackageProperties properties = null;
        try {
            properties = (PremiumPackageProperties) em.createNamedQuery("PremiumPackageProperties.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium package properties at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Premium package properties at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(new PremiumPackagePropertiesPOJO(properties)).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/premium_package_properties This method
     * recieves JSON object, and put it in the base. Example for JSON that you
     * need to send some of this attributes not to be default values:
     * <br/>
     * {<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "forNonPayingUsers": 0, <br/>
     * "redirectPositionTop": null,<br/>
     * "redirectPositionLeft": null, <br/>
     * "redirectImageUrl": "", "charityDonation": null, <br/>
     * "charityDescription": "",<br/>
     * "showUntil": null,<br/>
     * "maxPurchasesPerUser": null,<br/>
     * "idPremiumPackageUpgrade": null, <br/>
     * "idFavoriteClub": null,<br/>
     * "highlightImageUrl": "",<br/>
     * "showOnlySpecial": 0,<br/>
     * "imageUrlSpecial": "",<br/>
     * "forPayingUsers": 0,<br/>
     * "showFrom": null,<br/>
     * "updateTimestamp": null,<br/>
     * "redirectUrl": "", <br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param premiumPackageProperties
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     *
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPremiumAction(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumPackageProperties premiumPackageProperties) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), premiumPackageProperties);
        premiumPackageProperties.setCreateDate(new Date());
        if (validator.checkLenght(premiumPackageProperties.getCharityDescription(), 255, true) && someAttributeIsNotNull(premiumPackageProperties)) {
            premiumPackageProperties.setCreateDate(new Date());
            helper.persistObject(em, premiumPackageProperties);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

//    /**
//     * API for method: .../rest/premium_package_properties/{id} This method find
//     * premium_package_properties with defined id. Id is retrieved from URL. If
//     * Premium package property with that index does not exist method throws
//     * exception. Otherwise method remove that Premium package property.
//     *
//     * @param token is a header parameter for checking permission
//     * @param id of Premium action that should be deleted.
//     * @return Response 200 OK
//     */
//    @DELETE
//    @Path("/{id}")
//    public Response deletePremiumAction(@HeaderParam("authorization") String token, @PathParam("id") long id) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.DELETE, token);
//        PremiumPackageProperties premiumPackageProperties = em.find(PremiumPackageProperties.class, id);
//        helper.removeObject(em, premiumPackageProperties, id);
//        return Response.ok().build();
//    }
    /**
     * API for this method is .../rest/premium_package_properties This method
     * recieves JSON object, and update database. Example for JSON that you
     * need. Required filed is id.
     *
     * { <br/>
     * "forNonPayingUsers": 0,<br/>
     * "redirectPositionTop": null,<br/>
     * "redirectPositionLeft": null,<br/>
     * "redirectImageUrl": "",<br/>
     * "charityDonation": null, <br/>
     * "charityDescription": "",<br/>
     * "showUntil": null,<br/>
     * "maxPurchasesPerUser": null, <br/>
     * "idPremiumPackageUpgrade": null,<br/>
     * "idFavoriteClub": null,<br/>
     * "highlightImageUrl": "",<br/>
     * "showOnlySpecial": 0,<br/>
     * "imageUrlSpecial": "",<br/>
     * "forPayingUsers": 0, <br/>
     * "showFrom": null, <br/>
     * "updateTimestamp": null,<br/>
     * "createDate": 1427204490000, <br/>
     * "redirectUrl": "", <br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "id": 1 <br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param premiumPackageProperty
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Premium package properies at index 15 does not
     * exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePremiumAction(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumPackageProperties premiumPackageProperty) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), premiumPackageProperty);
        PremiumPackageProperties oldPremiumPackageProperty = em.find(PremiumPackageProperties.class, premiumPackageProperty.getId());
        if (oldPremiumPackageProperty != null) {
            if (validator.checkLenght(premiumPackageProperty.getCharityDescription(), 255, true) && someAttributeIsNotNull(premiumPackageProperty)
                    && oldPremiumPackageProperty.getId() != 0) {
                premiumPackageProperty.setCreateDate(oldPremiumPackageProperty.getCreateDate());
                helper.mergeObject(em, premiumPackageProperty);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium package properies at index" + premiumPackageProperty.getId() + " does not exits"), em);
            throw new DataNotFoundException("Premium package properies at index" + premiumPackageProperty.getId() + " does not exits");
        }
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    private boolean someAttributeIsNotNull(PremiumPackageProperties premiumPackageProperties) {
        return premiumPackageProperties.getCharityDescription() != null
                || premiumPackageProperties.getCharityDonation() != BigDecimal.valueOf(0)
                || premiumPackageProperties.getForNonPayingUsers() != 0 || premiumPackageProperties.getForPayingUsers() != Short.parseShort("0")
                || premiumPackageProperties.getHighlightImageUrl() != null || premiumPackageProperties.getIdFavoriteClub() != null
                || premiumPackageProperties.getIdPremiumPackageSuccessor() != null || premiumPackageProperties.getIdPremiumPackageUpgrade() != null
                || premiumPackageProperties.getImageUrlSpecial() != null || premiumPackageProperties.getMaxPurchasesPerUser() != 0
                || premiumPackageProperties.getPremiumPackageList() != null || premiumPackageProperties.getRedirectImageUrl() != null
                || premiumPackageProperties.getRedirectPositionLeft() != 0 || premiumPackageProperties.getRedirectPositionTop() != 0
                || premiumPackageProperties.getRedirectUrl() != null || premiumPackageProperties.getUpdateTimestamp() != null
                || premiumPackageProperties.getShowUntil() != null || premiumPackageProperties.getShowOnlySpecial() != 0
                || premiumPackageProperties.getShowFrom() != null;
    }

    /**
     * API for this method: .../rest/premium_package_properties/count This
     * method return number of all package properties in database.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountPackageProperties(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "Select COUNT(ip) From PremiumPackageProperties ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        Response response = Response.ok().entity(count).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

}
