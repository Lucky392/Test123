/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
import java.text.SimpleDateFormat;
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
import rs.htec.cms.cms_bulima.domain.PremiumItemPackage;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.PremiumItemPackagePOJO;

/**
 *
 * @author stefan
 */
@Path("/itemPackages")
public class PremiumItemPackageRESTEndpoint {

    @InjectParam
    RestHelperClass helper;

    @InjectParam
    Validator validator;

    /**
     * New fields added:[googlePlayStoreId,itunesStoreId,amazonStoreId] API for
     * method:
     * .../rest/itemPackages?page=VALUE&limit=VALUE&column=VALUE&search=VALUE&minDate=VALUE&maxDate=VALUE
     * This method returns JSON list. Default value for page is 1, and for limit
     * is 10. You can put your values for page, limit, orderColumn, searchWord,
     * start and end date,. It produces APPLICATION_JSON media type. Example for
     * JSON list for 1 page, 2 limit: <br/>
     * [<br/>
     * {<br/>
     * "createDate": 1388530800000,<br/>
     * "title": "Bietmanager",<br/>
     * "highlightUrl": "",<br/>
     * "additionalInfo": "",<br/>
     * "updateTimestamp": 1405029600000,<br/>
     * "pricePremiumCurrency": 15,<br/>
     * "amountPremiumItems": 1,<br/>
     * "active": 1,<br/>
     * "idPremiumItem": 6,<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "namePremiumItem": "Bietmanager",<br/>
     * "position": 6,<br/>
     * "name": "15 Fussi -Taler",<br/>
     * "id": 1<br/>
     * },<br/> { "createDate": 1388530800000,<br/>
     * "title": "Co-Trainer",<br/>
     * "highlightUrl": "",<br/>
     * "additionalInfo": "",<br/>
     * "updateTimestamp": 1405029600000,<br/>
     * "pricePremiumCurrency": 15,<br/>
     * "amountPremiumItems": 1,<br/>
     * "active": 0,<br/>
     * "idPremiumItem": 3,<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "namePremiumItem": "Co-Trainer",<br/>
     * "position": 4,<br/>
     * "name": "15 Fussi -Taler",<br/>
     * "id": 2<br/>
     * },<br/>
     * <br/>]
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for PremiumItemPackage
     * @param limit number of PremiumItemPackage method returns
     * @param orderingColumn column name for ordering, if you put "-" before
     * column name, that mean DESC ordering.
     * @param search word for searching name and title
     * @param minDate is a start date for filtering time in millis
     * @param maxDate is a end date for filtering time in millis
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     *
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackage(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate) {

        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<PremiumItemPackage> itemPackage;
        StringBuilder query = new StringBuilder("SELECT p FROM PremiumItemPackage p ");

        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append("WHERE p.createDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
        }

        if (search != null) {
            search = "%" + search + "%";
            query.append(minDate != 0 ? " AND" : "WHERE")
                    .append(" p.name LIKE '")
                    .append(search)
                    .append("' OR p.title LIKE '").append(search).append("'");
        }

        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        itemPackage = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        System.out.println(query);
        if (itemPackage == null || itemPackage.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("Requested page does not exist.."), em);
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = query.toString().replaceFirst("p", "count(p)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        List<PremiumItemPackagePOJO> pojos = PremiumItemPackagePOJO.toPremiumItemPackagePOJOList(itemPackage);
        go.setCount(count);
        go.setData(pojos);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/itemPackages/{id} This method return single
     * element of item package at index in JSON. Example for JSON response:
     * {<br/>
     * "createDate": 1388530800000,<br/>
     * "title": "Bietmanager",<br/>
     * "highlightUrl": "",<br/>
     * "additionalInfo": "",<br/>
     * "updateTimestamp": 1405029600000,<br/>
     * "pricePremiumCurrency": 15,<br/>
     * "amountPremiumItems": 1,<br/>
     * "active": 1,<br/>
     * "idPremiumItem": 6,<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "namePremiumItem": "Bietmanager",<br/>
     * "position": 6,<br/>
     * "name": "15 Fussi -Taler",<br/>
     * "id": 1<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param id of item package we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemPackageById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);

        PremiumItemPackagePOJO itemPackagePojo = null;
        try {
            PremiumItemPackage itemPackage = (PremiumItemPackage) em.createNamedQuery("PremiumItemPackage.findById").setParameter("id", id).getSingleResult();
            itemPackagePojo = new PremiumItemPackagePOJO(itemPackage);
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium item package at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Premium item package at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(itemPackagePojo).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/itemPackages This method recieves JSON
     * object, and put it in the DB. Example for JSON that you need to send:
     * {<br/>
     * "title": "Bietmanager",<br/>
     * "highlightUrl": "",<br/>
     * "additionalInfo": "",<br/>
     * "updateTimestamp": null,<br/>
     * "pricePremiumCurrency": 15,<br/>
     * "amountPremiumItems": 1,<br/>
     * "active": 1,<br/>
     * "idPremiumItem": 6,<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "namePremiumItem": "Bietmanager",<br/>
     * "position": 6,<br/>
     * "name": "15 Fussi -Taler"<br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @param itemPackage is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     *
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPackage(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumItemPackage itemPackage) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        itemPackage.setCreateDate(new Date());
//        PremiumItem item = em.find(PremiumItem.class, idPremiumItem);
//        itemPackage.setIdPremiumItem(item);
        if (validator.checkLenght(itemPackage.getName(), 255, true) && validator.checkLenght(itemPackage.getTitle(), 255, true)
                && validator.checkLenght(itemPackage.getHighlightUrl(), 255, true) && validator.checkLenght(itemPackage.getAdditionalInfo(), 255, true)) {
            helper.persistObject(em, itemPackage);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");
        }
    }

//    /**
//     * API for method: .../rest/itemPackages/{id} This method find package with
//     * defined id. Id is retrieved from URL. If Package with that index does not
//     * exist method throws exception. Otherwise method remove that Package.
//     *
//     * @param token is a header parameter for checking permission
//     * @param id of Package that should be deleted.
//     * @return Response 200 OK
//     */
//    @DELETE
//    @Path("/{id}")
//    public Response deleteItemPackage(@HeaderParam("authorization") String token, @PathParam("id") long id) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.DELETE, token);
//        PremiumItemPackage itemPackage = em.find(PremiumItemPackage.class, id);
//        helper.removeObject(em, itemPackage, id);
//        return Response.ok().build();
//    }
    /**
     * API for this method is .../rest/itemPackages This method recieves JSON
     * object, and update database. Example for JSON that you need to send:
     *
     * { <br/>
     * "highlightUrl": "",<br/>
     * "additionalInfo": "2",<br/>
     * "title": "Test3",<br/>
     * "pricePremiumCurrency": 15,<br/>
     * "amountPremiumItems": 2,<br/>
     * "active": 1,<br/>
     * "idPremiumItem": 4,<br/>
     * "position": 6,<br/>
     * "name": "Test3",<br/>
     * "googlePlayStoreId": null,<br/>
     * "itunesStoreId": null,<br/>
     * "amazonStoreId": null,<br/>
     * "id": 8<br/>
     * }<br/>
     *
     * @param token is a header parameter for checking permission
     * @param itemPackage is an object that Jackson convert from JSON to object
     * @return Response with status OK (200)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Premium items package at index 54 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItemPackage(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumItemPackage itemPackage) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);

        PremiumItemPackage oldPackage = em.find(PremiumItemPackage.class, itemPackage.getId());
        if (oldPackage != null) {
            if (validator.checkLenght(itemPackage.getName(), 255, true) && validator.checkLenght(itemPackage.getTitle(), 255, true)
                    && validator.checkLenght(itemPackage.getHighlightUrl(), 255, true) && validator.checkLenght(itemPackage.getAdditionalInfo(), 255, true)) {

                itemPackage.setUpdateTimestamp(new Date());
                itemPackage.setCreateDate(oldPackage.getCreateDate());
//                PremiumItem item = em.find(PremiumItem.class, idPremiumItem);
//                itemPackage.setIdPremiumItem(item);
                helper.mergeObject(em, itemPackage);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium items package at index " + itemPackage.getId() + " does not exits"), em);
            throw new DataNotFoundException("Premium items package at index " + itemPackage.getId() + " does not exits");
        }
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method: .../rest/itemPackages/count This method return
     * number of all item packages in database.
     *
     * @param token is a header parameter for checking permission
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountItemPackage(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "Select COUNT(ip) From PremiumItemPackage ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        Response response = Response.ok().entity(count).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
