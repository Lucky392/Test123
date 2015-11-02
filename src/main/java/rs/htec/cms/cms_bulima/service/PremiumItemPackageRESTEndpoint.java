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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
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
@Path("/itemPackage")
public class PremiumItemPackageRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    
    @InjectParam
    Validator validator;

    /**
     * API for method:
     * .../rest/itemPackage?page=VALUE&limit=VALUE&column=VALUE&search=VALUE&minDate=VALUE&maxDate=VALUE
     * This method returns JSON list. Default value for page is 1, and for limit
     * is 10. You can put your values for page, limit, orderColumn, searchWord,
     * start and end date,. It produces APPLICATION_JSON media type. Example for
     * JSON list for 1 page, 2 limit: <br/>
     * [
     * {<br/>
     * "amountPremiumItems": "1",<br/> "name": "15 Fussi -Taler",<br/>
     * "additionalInfo": "",<br/> "active": "1",<br/> "highlightUrl": "",<br/>
     * "idPremiumItem": "6",<br/> "id": "1",<br/>
     * "position": "6",<br/> "title": "Bietmanager",<br/>
     * "pricePremiumCurrency": "15",<br/>
     * "updateTimestamp": "2014-07-11 00:00:00.0",<br/> "createDate":
     * "2014-01-01 00:00:00.0" <br/>},<br/> {<br/> "amountPremiumItems":
     * "1",<br/> "name": "15 Fussi -Taler",<br/>
     * "additionalInfo": "",<br/> "active": "0",<br/>"highlightUrl": "",<br/>
     * "idPremiumItem": "3",<br/> "id": "2",<br/> "position": "4",<br/> "title":
     * "Co-Trainer",<br/>
     * "pricePremiumCurrency": "15",<br/> "updateTimestamp": "2014-07-11
     * 00:00:00.0",<br/>
     * "createDate": "2014-01-01 00:00:00.0" <br/>},<br/>
     * {<br/>"amountPremiumItems": "1",<br/>
     * "name": "40 Fussi -Taler",<br/> "additionalInfo": "",<br/> "active":
     * "1",<br/>
     * "highlightUrl": "",<br/> "idPremiumItem": "5", <br/>"id": "3",
     * <br/>"position": "3",<br/>
     * "title": "Sportdirektor", <br/>"pricePremiumCurrency": "40",<br/>
     * "updateTimestamp": "2014-07-11 00:00:00.0",<br/> "createDate":
     * "2014-01-01 00:00:00.0" <br/>}<br/>]
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for News
     * @param limit number of News method returns
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
    public Response getPackage(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn, @QueryParam("search") String search,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate) {

        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
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
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = query.toString().replaceFirst("p", "count(p)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        List<PremiumItemPackagePOJO> pojos = PremiumItemPackagePOJO.toPremiumItemPackagePOJOList(itemPackage);
        go.setCount(count);
        go.setData(pojos);
        return Response.ok().entity(go).build();
    }

    /**
     * API for method: .../rest/itemPackage/{id} This method return single
     * element of item package at index in JSON. Example for JSON response:
     * {<br/>
     * "amountPremiumItems": "1",<br/> "name": "15 Fussi -Taler",<br/>
     * "additionalInfo": "",<br/> "active": "1",<br/> "highlightUrl": "",<br/>
     * "idPremiumItem": "6",<br/> "id": "1",<br/>
     * "position": "6",<br/> "title": "Bietmanager",<br/>
     * "pricePremiumCurrency": "15",<br/>
     * "updateTimestamp": "2014-07-11 00:00:00.0",<br/> "createDate":
     * "2014-01-01 00:00:00.0" <br/>}
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
    public Response getItemPackageById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);

        PremiumItemPackagePOJO itemPackagePojo = null;
        try {
            PremiumItemPackage itemPackage = (PremiumItemPackage) em.createNamedQuery("PremiumItemPackage.findById").setParameter("id", id).getSingleResult();
            itemPackagePojo = new PremiumItemPackagePOJO(itemPackage);
        } catch (Exception e) {
            throw new DataNotFoundException("Premium item package at index " + id + " does not exist..");
        }
        return Response.ok().entity(itemPackagePojo).build();
    }

    /**
     * API for this method is .../rest/itemPackage  This method recieves JSON object,
     * and put it in the DB. Example for JSON that you need to send: 
     * {<br/>
     * "updateTimestamp": null,<br/>
     * "highlightUrl": "images/shop/ribbon.png",<br/>
     * "additionalInfo": "",<br/>
     * "title": "Test2",<br/>
     * "pricePremiumCurrency": 170,<br/>
     * "amountPremiumItems": 5,<br/>
     * "active": 3,<br/>
     * "idPremiumItem": 9,<br/>
     * "position": 1,<br/>
     * "name": "Test2"<br/>
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
    public Response insertPackage(@HeaderParam("authorization") String token, PremiumItemPackage itemPackage) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.ADD, token);
        itemPackage.setCreateDate(new Date());
//        PremiumItem item = em.find(PremiumItem.class, idPremiumItem);
//        itemPackage.setIdPremiumItem(item);
        if (validator.checkLenght(itemPackage.getName(), 255, true) && validator.checkLenght(itemPackage.getTitle(), 255, true)
                && validator.checkLenght(itemPackage.getHighlightUrl(), 255, true) && validator.checkLenght(itemPackage.getAdditionalInfo(), 255, true)) {
            helper.persistObject(em, itemPackage);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

//    /**
//     * API for method: .../rest/itemPackage/{id} This method find package with
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
     * API for this method is .../rest/itemPackage This method recieves JSON
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
    public Response updateItemPackage(@HeaderParam("authorization") String token, PremiumItemPackage itemPackage) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token);

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
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Premium items package at index " + itemPackage.getId() + " does not exits");
        }

        return Response.ok().build();
    }

    /**
     * API for this method: .../rest/itemPackage/count This method return number
     * of all item packages in database.
     *
     * @param token is a header parameter for checking permission
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountNews(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.NEWS, MethodConstants.SEARCH, token);
        String query = "Select COUNT(ip) From PremiumItemPackage ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        return Response.ok().entity(count).build();
    }
}
