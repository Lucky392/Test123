/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.domain.CmsRole;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author lazar
 */
@Path("/privileges")
public class UserPrivilegesRESTEndpoint {

    RestHelperClass helper;

    public UserPrivilegesRESTEndpoint() {
        helper = new RestHelperClass();
    }

    /**
     * API for method: .../rest/privileges This method gets authorization token
     * from HTTP header and list of user privileges in JSON format and insert
     * them into database. Example for JSON
     * <br/>
     * [<br/>{<br/> "searchAction": true,<br/> "editAction": true,<br/>
     * "addAction": true,<br/>
     * "deleteAction": true,<br/> "cmsUserPrivilegesPK": <br/>{<br/> "tableId":
     * 1 <br/>},<br/> "cmsRole":
     * <br/>{<br/> "name": "custom1" <br/>} <br/>},<br/> {<br/> "searchAction":
     * true,<br/> "editAction": true,<br/>
     * "addAction": true,<br/> "deleteAction": true,<br/> "cmsUserPrivilegesPK":
     * <br/>{<br/>
     * "tableId": 2 <br/>},<br/> "cmsRole": <br/>{<br/> "name": "custom1" <br/>}
     * <br/>}<br/> ]
     *
     * @param token is a header parameter for checking permission
     * @param userPrivileges is JSON object that Jackson convert to object
     * @return Response 201 CREATED
     * @throws RuntimeException
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPrivileges(@HeaderParam("authorization") String token, List<CmsUserPrivileges> userPrivileges) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.USER_PRIVILEGES, MethodConstants.ADD, token);
        if (userPrivileges.size() > 0 && userPrivileges.get(0).getCmsRole() != null) {
            helper.persistObject(em, userPrivileges.get(0).getCmsRole());
            CmsRole role = (CmsRole) em.createNamedQuery("CmsRole.findByName").setParameter("name", userPrivileges.get(0).getCmsRole().getName()).getSingleResult();
            long roleID = role.getId();
            for (CmsUserPrivileges cup : userPrivileges) {
                cup.getCmsUserPrivilegesPK().setRoleId(roleID);
                cup.setCmsRole(null);
                helper.persistObject(em, cup);
            }
            return Response.status(Response.Status.CREATED).build();
        } else {
            //napravi novi izuzetak i baci ga!!!!
            throw new RuntimeException("Something went wrong!");
        }
    }

//    /**
//     * API for method: /privileges<br/>
//     * This method gets authorization token from HTTP header and list of user
//     * privileges in JSON format and updates them into database. Example for
//     * JSON
//     * <br/>
//     * [<br/>{<br/> "searchAction": true,<br/> "editAction": true,<br/>
//     * "addAction": true,<br/>
//     * "deleteAction": true,<br/> "cmsUserPrivilegesPK": <br/>{<br/> "tableId":
//     * 1 <br/>},<br/> "cmsRole":
//     * <br/>{<br/> "name": "custom1" <br/>} <br/>},<br/> {<br/> "searchAction":
//     * true,<br/> "editAction": true,<br/>
//     * "addAction": true,<br/> "deleteAction": true,<br/> "cmsUserPrivilegesPK":
//     * <br/>{<br/>
//     * "tableId": 2 <br/>},<br/> "cmsRole": <br/>{<br/> "name": "custom1" <br/>}
//     * <br/>}<br/> ]
//     *
//     * @param token
//     * @param userPrivileges
//     * @return Response 201 CREATED
//     *
//     */
//    @PUT
//    @Path("/")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response insertPrivileges2(@HeaderParam("authorization") String token, List<CmsUserPrivileges> userPrivileges) {
//        EntityManager em = helper.getEntityManager();
//        helper.checkUserAndPrivileges(em, TableConstants.USER_PRIVILEGES, MethodConstants.ADD, token);
//
//        helper.persistObject(em, userPrivileges.get(0).getCmsRole());
//
//        CmsRole role = (CmsRole) em.createNamedQuery("CmsRole.findByName").setParameter("name", userPrivileges.get(0).getCmsRole().getName()).getSingleResult();
//        for (CmsUserPrivileges cup : userPrivileges) {
//            cup.getCmsUserPrivilegesPK().setRoleId(role.getId());
//            helper.persistObject(em, cup);
//        }
//        return Response.status(Response.Status.CREATED).build();
//    }
    /**
     * API for method: .../rest/privileges This method gets authorization token
     * from HTTP header privileges in JSON format and insert them into database.
     * Example for JSON
     * <br/>
     * [<br/>{<br/> "searchAction": true,<br/> "editAction": true,<br/>
     * "addAction": true,<br/>
     * "deleteAction": true,<br/> "cmsUserPrivilegesPK": <br/>{<br/> "tableId":
     * 1 <br/>},<br/> "cmsRole":
     * <br/>{<br/> "name": "custom1" <br/>} <br/>},<br/> {<br/> "searchAction":
     * true,<br/> "editAction": true,<br/>
     * "addAction": true,<br/> "deleteAction": true,<br/> "cmsUserPrivilegesPK":
     * <br/>{<br/>
     * "tableId": 2 <br/>},<br/> "cmsRole": <br/>{<br/> "name": "custom1" <br/>}
     * <br/>}<br/> ]
     *
     * @param token is a header parameter for checking permission
     * @return Response 200 CREATED
     * @throws DataNotFoundException Example for this exception: <br/> {<br/>
     * "errorMessage": "There is no privileges!",<br/>
     * "errorCode": 404<br/> }
     *
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrivileges(@HeaderParam("authorization") String token) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.USER_PRIVILEGES, MethodConstants.SEARCH, token);
        List<CmsUserPrivileges> cup = em.createNamedQuery("CmsUserPrivileges.findAll").getResultList();
        if (cup.isEmpty()) {
            return Response.ok().entity(cup).build();
        } else {
            throw new DataNotFoundException("There is no privileges!");
        }
    }

}
