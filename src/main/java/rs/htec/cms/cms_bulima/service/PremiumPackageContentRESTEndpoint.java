/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.text.SimpleDateFormat;
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
import rs.htec.cms.cms_bulima.domain.PremiumItem;
import rs.htec.cms.cms_bulima.domain.PremiumPackage;
import rs.htec.cms.cms_bulima.domain.PremiumPackageContent;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.CountWrapper;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;

/**
 *
 * @author stefan
 */
@Path("/packageContent")
public class PremiumPackageContentRESTEndpoint {

    RestHelperClass helper;
    Validator validator;

    public PremiumPackageContentRESTEndpoint() {
        helper = new RestHelperClass();
        validator = new Validator();
    }

    /**
     * API for method:
     * .../rest/packageContent?page=VALUE&limit=VALUE&column=VALUE&maxDate=VALUE
     * This method returns JSON list. Default value for page is 1, and for limit
     * is 10. You can put your values for page, limit, orderColumn, searchWord,
     * start and end date,. It produces APPLICATION_JSON media type. Example for
     * JSON list for 1 page, 2 limit: <br/>
     * [<br/>
     * {<br/>
     * "idPremiumPackage": "rs.htec.cms.cms_bulima.domain.PremiumPackage[ id=8
     * ]",<br/> "amount": "null",<br/> "idPremiumItem": "16", <br/>"id":
     * "45",<br/>
     * "updateTimestamp": "null",<br/> "createDate": "2015-03-24
     * 14:41:22.0"<br/> },<br/> {<br/>
     * "idPremiumPackage": "rs.htec.cms.cms_bulima.domain.PremiumPackage[ id=8
     * ]", <br/>"amount": "null", <br/>"idPremiumItem": "15",<br/> "id":
     * "46",<br/>
     * "updateTimestamp": "null",<br/> "createDate": "2015-03-24 14:41:22.0"
     * <br/>}<br/>]
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search
     * @param limit number method returns
     * @param orderingColumn column name for ordering, if you put "-" before
     * column name, that mean DESC ordering.
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
    public Response getPackageContent(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("column") String orderingColumn,
            @QueryParam("minDate") long minDate, @QueryParam("maxDate") long maxDate) {

        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
        List<PremiumPackageContent> packageContent;
        StringBuilder query = new StringBuilder("SELECT p FROM PremiumPackageContent p ");

        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append("WHERE p.createDate BETWEEN '").append(sdf.format(d1)).append("' AND '").append(sdf.format(d2)).append("'");
        }

        if (orderingColumn != null) {
            if (orderingColumn.startsWith("-")) {
                orderingColumn = orderingColumn.substring(1) + " desc";
            }
            query.append(" ORDER BY ").append(orderingColumn);
        }
        packageContent = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        System.out.println(query);
        if (packageContent == null || packageContent.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = query.toString().replaceFirst("p", "count(p)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(packageContent);
        return Response.ok().entity(go).build();
    }

    /**
     * API for method: .../rest/packageContent/{id} This method return single
     * element of package content at index in JSON. Example for JSON response:
     * {<br/>
     * "idPremiumPackage": "rs.htec.cms.cms_bulima.domain.PremiumPackage[ id=8
     * ]", <br/>"amount": "null", <br/>"idPremiumItem": "15",<br/> "id":
     * "46",<br/>
     * "updateTimestamp": "null",<br/> "createDate": "2015-03-24 14:41:22.0"
     * <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param id of premium package content we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackageContentById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
        PremiumPackageContent content = null;
        try {
            content = (PremiumPackageContent) em.createNamedQuery("PremiumPackageContent.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("Premium package content at index " + id + " does not exist..");
        }
        return Response.ok().entity(content).build();
    }

    /**
     * API for this method is .../rest/packageContent/{idItem}/{idPackage} This
     * method recieves JSONobject, and put it in the base. Example for JSON that
     * you need to send: {<br/>
     * "createDate": 1427204482000,<br/> "updateTimestamp": null,<br/> "amount":
     * "5"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param packageContent object that we want to insert in database
     * @param idItem id of premium item we add to packageContent object
     * @param idPackage id of premium item package we add to packageContent
     * object
     * @return Response with status CREATED (201)
     *
     */
    @POST
    @Path("/{idItem}/{idPackage}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPackageContent(@HeaderParam("authorization") String token, PremiumPackageContent packageContent,
            @PathParam("idItem") long idItem, @PathParam("idPackage") long idPackage) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.ADD, token);
        packageContent.setCreateDate(new Date());
        PremiumItem item = em.find(PremiumItem.class, idItem);
        packageContent.setIdPremiumItem(item);
        PremiumPackage premiumPackage = em.find(PremiumPackage.class, idPackage);
        packageContent.setIdPremiumPackage(premiumPackage);

        helper.persistObject(em, packageContent);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * API for method: .../rest/packageContent/{id} This method find package
     * content with defined id and delete it. Id is retrieved from URL. If
     * package content with that index does not exist method throws exception.
     * Otherwise method remove that package content.
     *
     * @param token is a header parameter for checking permission
     * @param id of Package Content that should be deleted.
     * @return Response 200 OK
     */
    @DELETE
    @Path("/{id}")
    public Response deleteItemPackageContent(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.DELETE, token);
        PremiumPackageContent packageContent = em.find(PremiumPackageContent.class, id);
        helper.removeObject(em, packageContent, id);
        return Response.ok().build();
    }

    /**
     * API for this method is .../rest/packageContent/{idItem}/{idPackage} This
     * method recieves JSON object, and update database. Example for JSON that
     * you need to send: {<br/>
     * "amount": "10",<br/> "id":"100",<br/>}
     *
     * @param token is a header parameter for checking permission
     * @param packageContent
     * @param idItem id of premium item we add to packageContent object
     * @param idPackage id of premium item package we add to packageContent
     * object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Premium items package at index 54 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Path("/{idItem}/{idPackage}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItemPackage(@HeaderParam("authorization") String token, PremiumPackageContent packageContent,
            @PathParam("idItem") long idItem, @PathParam("idPackage") long idPackage) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token);

        PremiumPackageContent oldPackageContent = em.find(PremiumPackageContent.class, packageContent.getId());
        if (oldPackageContent != null) {
            packageContent.setUpdateTimestamp(new Date());
            packageContent.setCreateDate(oldPackageContent.getCreateDate());
            PremiumItem item = em.find(PremiumItem.class, idItem);
            packageContent.setIdPremiumItem(item);
            PremiumPackage premiumPackage = em.find(PremiumPackage.class, idPackage);
            packageContent.setIdPremiumPackage(premiumPackage);
            helper.mergeObject(em, packageContent);
        } else {
            throw new DataNotFoundException("Premium items package content at index " + packageContent.getId() + " does not exits");
        }

        return Response.ok("Successfully updated!").build();
    }
    
    /**
     * API for this method: .../rest/packageContent/count
     * This method return number of all package contents in database.
     * @param token is a header parameter for checking permission
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountNews(@HeaderParam("authorization") String token){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
        String query = "Select COUNT(ip) From PremiumPackageContent ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        return Response.ok().entity(count).build();
    }
}
