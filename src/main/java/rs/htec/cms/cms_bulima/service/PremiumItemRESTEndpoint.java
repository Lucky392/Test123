/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

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
import rs.htec.cms.cms_bulima.domain.PremiumItem;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author marko
 */
@Path("/items")
public class PremiumItemRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public PremiumItemRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method: .../rest/items?page=VALUE&limit=VALUE&search=VALUE This
     * method returns JSON list. Default value for page is 1, and for limit is
     * 10. You can put your values for page, limit and search. It produces
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2 limit:
     * <br/>[ { <br/>"imageUrl": "images/shop/Icon_Ersatzbank.png",<br/>
     * "shopName": "Ersatzbank",<br/> "descriptionLong": "Falls ein Spieler aus
     * deiner gepeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt, können Spieler von der Ersatzbank nachrücken. Diese Spieler müssen
     * ebenso, wie die Spieler in der Aufstellung, vor Spieltagsbegin
     * aufgestellt und mit der Aufstellung abgespeichert werden. ",<br/>
     * "updateTimestamp": 1405029600000, <br/>"showInItemShop": 0,
     * <br/>"slotNumber": 0,<br/>
     * "directPurchasePrice": 0,<br/> "positionInPackage": null,
     * <br/>"createDate": 1388530800000,<br/> "description": "Falls ein Spieler
     * aus deiner gespeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt, können Spieler von der Ersatzbank nachrücken. Die Spieler können
     * nur dann in deine Aufstellung nachrücken, wenn sie auch an dem Spieltag
     * zum Einsatz kommen.",<br/> "name": "Ersatzbank",<br/> "id": 2<br/> },
     * <br/>{<br/> "imageUrl": "images/shop/benchslots_icon.png",<br/>
     * "shopName": "Ersatzbank-Platz",<br/>
     * "descriptionLong": "Falls ein Spieler aus deiner gepeicherten Aufstellung
     * an einem Spieltag nicht zum Einsatz kommt, können Spieler von der
     * Ersatzbank nachrücken. Diese Spieler müssen ebenso, wie die Spieler in
     * der Aufstellung, vor Spieltagsbeginn aufgestellt und mit der Aufstellung
     * abgespeichert werden. ",<br/> "updateTimestamp": 1418641200000,<br/>
     * "showInItemShop": 1,<br/> "slotNumber": 0,<br/> "directPurchasePrice":
     * 40,<br/>
     * "positionInPackage": null, <br/>"createDate": 1418641200000,<br/>
     * "description": "Falls ein Spieler aus deiner gespeicherten Aufstellung an
     * einem Spieltag nicht zum Einsatz kommt, können Spieler von der Ersatzbank
     * nachrücken. Die Spieler können nur dann in deine Aufstellung nachrücken,
     * wenn sie auch an dem Spieltag zum Einsatz kommen.",<br/> "name":
     * "Ersatzbank-Platz",<br/>
     * "id": 10<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param page number of page at which we search for
     * @param limit number of Objects method returns
     * @param search word for searching name, shopName
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumItems(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        List<PremiumItem> items;
        StringBuilder query = new StringBuilder("SELECT p FROM PremiumItem p ");
        if (search != null) {
            search = "%" + search + "%";
            query.append(" WHERE p.name LIKE '")
                    .append(search)
                    .append("' OR p.shopName LIKE '")
                    .append(search).append("'");
        }
        items = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (items == null || items.isEmpty()) {
            helper.setResponseToHistory(history, new DataNotFoundException("Requested page does not exist.."), em);
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = query.toString().replaceFirst("p", "count(p)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        System.out.println(countQuery);
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(items);
        Response response = Response.ok().entity(go).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for method: .../rest/items/{id} This method return single element of
     * premium items at index in JSON. Example for JSON response: {
     * <br/>"imageUrl": "images/shop/Icon_Ersatzbank.png",<br/>
     * "shopName": "Ersatzbank",<br/> "descriptionLong": "Falls ein Spieler aus
     * deiner gepeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt, können Spieler von der Ersatzbank nachrücken. Diese Spieler müssen
     * ebenso, wie die Spieler in der Aufstellung, vor Spieltagsbegin
     * aufgestellt und mit der Aufstellung abgespeichert werden. ",<br/>
     * "updateTimestamp": 1405029600000, <br/>"showInItemShop": 0,
     * <br/>"slotNumber": 0,<br/>
     * "directPurchasePrice": 0,<br/> "positionInPackage": null,
     * <br/>"createDate": 1388530800000,<br/> "description": "Falls ein Spieler
     * aus deiner gespeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt, können Spieler von der Ersatzbank nachrücken. Die Spieler können
     * nur dann in deine Aufstellung nachrücken, wenn sie auch an dem Spieltag
     * zum Einsatz kommen.",<br/> "name": "Ersatzbank",<br/> "id": 2<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param id of premium items we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremiumItemById(@HeaderParam("authorization") String token, @Context HttpServletRequest request, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        PremiumItem item = null;
        try {
            item = (PremiumItem) em.createNamedQuery("PremiumItem.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium item at index " + id + " does not exist.."), em);
            throw new DataNotFoundException("Premium item at index " + id + " does not exist..");
        }
        Response response = Response.ok().entity(item).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method is .../rest/items This method recieves JSON object,
     * and put it in the base. Example for JSON that you need to send:
     * <br/>{<br/>
     * "updateTimestamp": 1405029600000,<br/> "imageUrl":
     * "images/shop/Icon_Ersatzbank.png",<br/> "showInItemShop": 0,<br/>
     * "shopName": "Ersatzbank",<br/> "descriptionLong": "Falls ein Spieler aus
     * deiner gepeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt, können Spieler von der Ersatzbank nachrücken. Diese Spieler müssen
     * ebenso, wie die Spieler in der Aufstellung, vor Spieltagsbegin
     * aufgestellt und mit der Aufstellung abgespeichert werden. ",<br/>
     * "slotNumber": 0,<br/> "directPurchasePrice": 0,<br/> "positionInPackage":
     * null,<br/>
     * "createDate": 1388530800000,<br/> "description": "Falls ein Spieler aus
     * deiner gespeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt,<br/>
     * können Spieler von der Ersatzbank nachrücken. Die Spieler können nur dann
     * in deine Aufstellung nachrücken, wenn sie auch an dem Spieltag zum
     * Einsatz kommen.",<br/> "name": "Ersatzbank"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param item is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertItem(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumItem item) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.ADD, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), item);
        item.setCreateDate(new Date());
        if (validator.checkLenght(item.getName(), 255, true) && validator.checkLenght(item.getImageUrl(), 255, true)
                && validator.checkLenght(item.getShopName(), 255, true) && validator.checkLenght(item.getDescription(), 639, true)
                && validator.checkLenght(item.getDescriptionLong(), 639, true)) {
            helper.persistObject(em, item);
            Response response = Response.status(Response.Status.CREATED).build();
            helper.setResponseToHistory(history, response, em);
            return response;
        } else {
            helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
            throw new InputValidationException("Validation failed");

        }
    }

//    /**
//     * API for method: .../rest/items/{id} This method find package with defined
//     * id. Id is retrieved from URL. If Item with that index does not exist
//     * method throws exception. Otherwise method remove that Item.
//     *
//     * @param token is a header parameter for checking permission
//     * @param id of Premium Item that should be deleted.
//        * @return Response 200 OK
//     */
//    @DELETE
//    @Path("/{id}")
//    public Response deleteItem(@HeaderParam("authorization") String token, @PathParam("id") long id) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.DELETE, token);
//        PremiumItem item = em.find(PremiumItem.class, id);
//        if (item == null){
//            throw new DataNotFoundException("There is no item ont this index!");
//        }
//        helper.removeObject(em, item, id);
//        return Response.ok().build();
//    }
    /**
     * API for this method is .../rest/items This method recieves JSON object,
     * and update database. Example for JSON that you need to send:<br/> {<br/>
     * "updateTimestamp": 1405029600000,<br/> "imageUrl":
     * "images/shop/Icon_Ersatzbank.png",<br/> "showInItemShop": 0,<br/>
     * "shopName": "Ersatzbank",<br/> "descriptionLong": "Falls ein Spieler aus
     * deiner gepeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt, können Spieler von der Ersatzbank nachrücken. Diese Spieler müssen
     * ebenso, wie die Spieler in der Aufstellung, vor Spieltagsbegin
     * aufgestellt und mit der Aufstellung abgespeichert werden. ",<br/>
     * "slotNumber": 0,<br/> "directPurchasePrice": 0,<br/> "positionInPackage":
     * null,<br/>
     * "createDate": 1388530800000,<br/> "description": "Falls ein Spieler aus
     * deiner gespeicherten Aufstellung an einem Spieltag nicht zum Einsatz
     * kommt, können Spieler von der Ersatzbank nachrücken. Die Spieler können
     * nur dann in deine Aufstellung nachrücken, wenn sie auch an dem Spieltag
     * zum Einsatz kommen.",<br/> "name": "Ersatzbank",<br/> "id": 2<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @param item is an object that Jackson convert from JSON to object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Premium item at index 2 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(@HeaderParam("authorization") String token, @Context HttpServletRequest request, PremiumItem item) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), item);
        PremiumItem oldItem = em.find(PremiumItem.class, item.getId());
        if (oldItem != null) {
            if (validator.checkLenght(item.getName(), 255, true) && validator.checkLenght(item.getImageUrl(), 255, true)
                    && validator.checkLenght(item.getShopName(), 255, true) && validator.checkLenght(item.getDescription(), 639, true)
                    && validator.checkLenght(item.getDescriptionLong(), 639, true)) {
                helper.mergeObject(em, item);
            } else {
                helper.setResponseToHistory(history, new InputValidationException("Validation failed"), em);
                throw new InputValidationException("Validation failed");
            }
        } else {
            helper.setResponseToHistory(history, new DataNotFoundException("Premium item at index" + item.getId() + " does not exits"), em);
            throw new DataNotFoundException("Premium item at index" + item.getId() + " does not exits");
        }
        Response response = Response.ok().build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }

    /**
     * API for this method: .../rest/items/count This method return number of
     * all items in database.
     *
     * @param token is a header parameter for checking permission
     * @param request
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountPremiumItem(@HeaderParam("authorization") String token, @Context HttpServletRequest request) {
        EntityManager em = helper.getEntityManager();
        CmsActionHistory history = helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token, request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), null);
        String query = "Select COUNT(ip) From PremiumItem ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
         Response response = Response.ok().entity(count).build();
        helper.setResponseToHistory(history, response, em);
        return response;
    }
}
