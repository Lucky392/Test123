/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import com.sun.jersey.api.core.InjectParam;
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
import rs.htec.cms.cms_bulima.domain.CmsTables;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivileges;
import rs.htec.cms.cms_bulima.domain.CmsUserPrivilegesPK;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.RolePOJO;

/**
 *
 * @author lazar
 */
@Path("/privileges")
public class UserPrivilegesRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    
    @InjectParam
    Validator validator;
    
    /**
     * API for method: .../rest/privileges This method gets authorization token
     * from HTTP header and list of user privileges in JSON format and insert
     * them into database. Example for JSON: <br/>{<br/>"roleName": "custom",<br/>
     * "permissions": {<br/> "SHOP": [<br/> "ADD",<br/> "SEARCH"<br/> ],<br/> "CMS_USER": [<br/> "ADD",<br/>
     * "SEARCH" <br/>],<br/> "CMS_USER_PRIVILEGES": [<br/> "ADD",<br/> "SEARCH"<br/> ],<br/> "SLIDER_CONTENT":
     * [<br/> "ADD",<br/> "SEARCH" <br/>],<br/> "QUESTION_OF_THE_DAY_PRIZE": [<br/> "ADD",<br/> "SEARCH" <br/>],<br/>
     * "STATISTICS": [<br/> "ADD",<br/> "SEARCH"<br/> ],<br/> "NEWS": [<br/> "ADD",<br/> "SEARCH"<br/> ],<br/>
     * "QUESTION_OF_THE_DAY": [ <br/>"ADD",<br/> "SEARCH"<br/> ], <br/>"CMS_ROLE": [ <br/>"ADD",<br/> "SEARCH"<br/>
     * ] <br/>}<br/>}
     *
     * @param token is a header parameter for checking permission
     * @param role is JSON object that Jackson convert to object
     * @return Response 201 CREATED
     * @throws RuntimeException
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPrivileges(@HeaderParam("authorization") String token, RolePOJO role) {
        EntityManager em = helper.getEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.USER_PRIVILEGES, MethodConstants.ADD, token);
        List<CmsUserPrivileges> userPrivileges = RolePOJO.createListPrivileges(role);
        if (userPrivileges.size() > 0 && userPrivileges.get(0).getCmsRole() != null) {
            helper.persistObject(em, userPrivileges.get(0).getCmsRole());
            CmsRole cmsRole = (CmsRole) em.createNamedQuery("CmsRole.findByName").setParameter("name", userPrivileges.get(0).getCmsRole().getName()).getSingleResult();
            long roleID = cmsRole.getId();
            for (CmsUserPrivileges cup : userPrivileges) {
                CmsTables table = (CmsTables) em.createNamedQuery("CmsTables.findByTableName").setParameter("tableName", cup.getCmsTables().getTableName()).getSingleResult();
                CmsUserPrivilegesPK pk = new CmsUserPrivilegesPK(roleID, table.getId());
//                cup.getCmsUserPrivilegesPK().setRoleId(roleID);
//                cup.getCmsUserPrivilegesPK().setTableId(table.getId());
                cup.setCmsUserPrivilegesPK(pk);
                cup.setCmsTables(null);
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
        if (!cup.isEmpty()) {
            return Response.ok().entity(cup).build();
        } else {
            throw new DataNotFoundException("There is no privileges!");
        }
    }

}
