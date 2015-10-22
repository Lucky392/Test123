/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.service;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.constants.TableConstants;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author marko
 */
public class MatchPlayerEventRESTEndpoint {
    
    RestHelperClass helper;

    public MatchPlayerEventRESTEndpoint() {
        helper = new RestHelperClass();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(@HeaderParam("authorization") String token, @QueryParam("type") String type, @QueryParam("min") int min, @QueryParam("matchPlayerID") long matchPlayerID){
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        StringBuilder query = new StringBuilder("SELECT m FROM MatchPlayerEvent m ");
        if (type != null){
            query.append("WHERE m.type = '")
                    .append(type)
                    .append("'");
        }
        if (min != 0){
            
        }
        return null;
    }
}
