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
import rs.htec.cms.cms_bulima.domain.PremiumPackage;
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
@Path("/package")
public class PremiumPackageRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    
    @InjectParam
    Validator validator;

    /**
     * API for method: .../rest/package?page=VALUE&limit=VALUE&search=VALUE This
     * method returns JSON list. Default value for page is 1, and for limit is
     * 10. You can put your values for page, limit and search. It produces
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2 limit:
     * <br/>[ { <br/>"imageUrl": "",<br/> "updateTimestamp": null,<br/>
     * "createDate": 1427204474000,<br/>
     * "platform": "ALL",<br/> "price": 19.99,<br/> "isActive": 1,<br/>
     * "premiumStatusDuration": "season",<br/> "amountPremiumCurrency": 0,<br/>
     * "title": "Saison",<br/> "position": null,<br/> "name": "Premium-Account -
     * Saison-Paket",<br/>
     * "id": 8 <br/>},<br/> { <br/>"imageUrl": "",<br/> "updateTimestamp":
     * null,<br/> "createDate": 1427204474000,<br/> "platform": "ALL",<br/>
     * "price": 10.99,<br/> "isActive": 1,<br/>
     * "premiumStatusDuration": "halfSeason",<br/> "amountPremiumCurrency":
     * 0,<br/>
     * "title": "Halbserie",<br/> "position": null,<br/> "name":
     * "Premium-Account - Halbserie",<br/> "id": 9<br/> } ]
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for Package
     * @param limit number of Package method returns
     * @param search word for searching name, title and premiumStatusDuration
     * @return Response 200 OK with JSON body
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPackage(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
        List<PremiumPackage> packages;
        StringBuilder query = new StringBuilder("SELECT p FROM PremiumPackage p ");
        if (search != null) {
            search = "%" + search + "%";
            query.append(" WHERE p.name LIKE '")
                    .append(search)
                    .append("' OR p.title LIKE '")
                    .append(search)
                    .append("' OR p.premiumStatusDuration LIKE '")
                    .append(search).append("'");
        }
        packages = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (packages == null || packages.isEmpty()) {
            throw new DataNotFoundException("Requested page does not exist..");
        }
        String countQuery = query.toString().replaceFirst("p", "count(p)");
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(packages);
        return Response.ok().entity(go).build();
    }

    /**
     * API for method: .../rest/package/{id} This method return single element of package at index
     * in JSON. Example for JSON response: { <br/>"imageUrl": "",<br/> "updateTimestamp": null,<br/>
     * "createDate": 1427204474000,<br/>
     * "platform": "ALL",<br/> "price": 19.99,<br/> "isActive": 1,<br/>
     * "premiumStatusDuration": "season",<br/> "amountPremiumCurrency": 0,<br/>
     * "title": "Saison",<br/> "position": null,<br/> "name": "Premium-Account -
     * Saison-Paket",<br/>
     * "id": 8 <br/>}
     * @param token is a header parameter for checking permission
     * @param id of premium package we are searching for
     * @throws DataNotFoundException DataNotFoundException Example for
     * exception:<br/> {<br/>
     * "errorMessage": "Requested page does not exist..",<br/>
     * "errorCode": 404<br/> }
     * @return Response 200 OK status with JSON body
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackageById(@HeaderParam("authorization") String token, @PathParam("id") long id) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
        PremiumPackage premiumPackage = null;
        try {
            premiumPackage = (PremiumPackage) em.createNamedQuery("PremiumPackage.findById").setParameter("id", id).getSingleResult();   
        } catch (Exception e) {
            throw new DataNotFoundException("Premium package at index " + id + " does not exist..");
        }
        return Response.ok().entity(premiumPackage).build();
    }
    
    /**
     * API for this method is .../rest/package This method recieves JSON object,
     * and put it in the base. Example for JSON that you need to send:
     * <br/>{<br/>
     * "imageUrl": "",<br/> "updateTimestamp": null,<br/> "platform":
     * "ALL",<br/> "price": 19.99,<br/> "isActive": 1,<br/>
     * "premiumStatusDuration": "season",<br/>
     * "amountPremiumCurrency": 0,<br/> "title": "Saison",<br/> "position":
     * null,<br/> "name": "Premium-Account - Saison-Paket"<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param premiumPackage is an object that Jackson convert from JSON to
     * object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPackage(@HeaderParam("authorization") String token, PremiumPackage premiumPackage) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.ADD, token);
        premiumPackage.setCreateDate(new Date());
        if (validator.checkLenght(premiumPackage.getName(), 255, true) && validator.checkLenght(premiumPackage.getImageUrl(), 255, true)
                && validator.checkLenght(premiumPackage.getPlatform(), 255, true) && validator.checkLenght(premiumPackage.getTitle(), 255, true)) {
            helper.persistObject(em, premiumPackage);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

//    /**
//     * API for method: .../rest/package/{id} This method find package with
//     * defined id. Id is retrieved from URL. If Item with that index does not
//     * exist method throws exception. Otherwise method remove that package.
//     *
//     * @param token is a header parameter for checking permission
//     * @param id of Premium Package that should be deleted.
//     * @return Response 200 OK
//     */
//    @DELETE
//    @Path("{id}")
//    public Response deletePackage(@HeaderParam("authorization") String token, @PathParam("id") long id) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.DELETE, token);
//        PremiumPackage premiumPackage = em.find(PremiumPackage.class, id);
//        helper.removeObject(em, premiumPackage, id);
//        return Response.ok().build();
//    }

    /**
     * API for this method is .../rest/package This method recieves JSON object,
     * and update database. Example for JSON that you need to send: <br/>{ <br/>"title":
     * "Saison",<br/> "updateTimestamp": null,<br/> "createDate": 1427204474000,<br/>
     * "imageUrl": "",<br/> "platform": "ALL",<br/> "price": 19.99,<br/>
     * "amountPremiumCurrency": 0,<br/> "isActive": 1,<br/> "premiumStatusDuration":
     * "season",<br/> "position": null,<br/> "name": "Premium-Account - Saison-Paket",<br/>
     * "id": 8<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param premiumPackage is an object that Jackson convert from JSON to object
     * @return Response with status OK (200) "Successfully updated!"
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Premium package at index 2 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePackage(@HeaderParam("authorization") String token, PremiumPackage premiumPackage) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.EDIT, token);
        PremiumPackage oldPackage = em.find(PremiumPackage.class, premiumPackage.getId());
        if (oldPackage != null) {
            if (validator.checkLenght(premiumPackage.getName(), 255, true) && validator.checkLenght(premiumPackage.getImageUrl(), 255, true)
                    && validator.checkLenght(premiumPackage.getPlatform(), 255, true) && validator.checkLenght(premiumPackage.getTitle(), 255, true)) {
                helper.mergeObject(em, premiumPackage);
            } else {
                throw new InputValidationException("Validation failed");
            }
        } else {
            throw new DataNotFoundException("Premium package at index " + premiumPackage.getId() + " does not exits");
        }
        return Response.ok().build();
    }
    
    /**
     * API for this method: .../rest/package/count
     * This method return number of all packages in database.
     * @param token is a header parameter for checking permission
     * @return Response 200 OK with JSON body
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/count")
    public Response getCountPackage(@HeaderParam("authorization") String token){
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.SHOP, MethodConstants.SEARCH, token);
        String query = "Select COUNT(ip) From PremiumPackage ip";
        CountWrapper count = new CountWrapper((long) em.createQuery(query).getSingleResult());
        return Response.ok().entity(count).build();
    }
}
