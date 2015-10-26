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
import rs.htec.cms.cms_bulima.domain.MatchdayChallenge;
import rs.htec.cms.cms_bulima.domain.MatchdayChallengeTargetCalculation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.exception.InputValidationException;
import rs.htec.cms.cms_bulima.helper.EMF;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.pojo.MatchdayChallengeTargetCalculationPOJO;

/**
 *
 * @author marko
 */
@Path("/targetCalculation")
public class MatchdayChallengeTaqrgetCalculationRESTEndpoint {

    @InjectParam
    RestHelperClass helper;
    @InjectParam
    Validator validator;

    public MatchdayChallengeTaqrgetCalculationRESTEndpoint() {
    }

    /**
     * API for method:
     * .../rest/targetCalculation?page=VALUE&limit=VALUE&search=VALUE&minUpdateDate=VALUE&maxUpdateDate=VALUE&matchdayChallengeID=VALUE
     * This method returns JSON list and count number. Default value for page is
     * 1, and for limit is 10. There is a possibility for search by
     * calculationSql and matchdayChallenge. Filtering by updateAt. It produces
     * APPLICATION_JSON media type. Example for JSON list for 1 page, 2 limit:
     * <br/>{<br/>
     * "count": 18,<br/> "data": [ {<br/> "urlToMatchdayChallenge":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchday_challenge/1",<br/>
     * "createDate": 1437421634000,<br/> "updateAt": null,<br/>
     * "calculationSql": null,<br/>
     * "operation": ">=",<br/> "calculationType": null,<br/> "value": 20,<br/>
     * "id": 1<br/> },<br/> {<br/>
     * "urlToMatchdayChallenge":
     * "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/matchday_challenge/2",<br/>
     * "createDate": 1437421641000,<br/> "updateAt": null,<br/>
     * "calculationSql": null,<br/>
     * "operation": ">=",<br/> "calculationType": null,<br/> "value": 4,<br/>
     * "id": 2<br/> } ]<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param page number of page at which we search for target calculation
     * @param limit number of target calculation method returns
     * @param search word for searching calculationSql
     * @param matchChallengeID id of Matchday challenge
     * @param minDate is a start date for filtering time in millis
     * @param maxDate is a end date for filtering time in millis
     * @return Response 200 OK with JSON body
     * @throw DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "There is no target calculation for this search!",<br/>
     * "errorCode": 404<br/> }
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTargetCalculation(@HeaderParam("authorization") String token, @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("limit") int limit, @QueryParam("search") String search,
            @QueryParam("matchdayChallengeID") long matchChallengeID,
            @QueryParam("minUpdateDate") long minDate, @QueryParam("maxUpdateDate") long maxDate) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.SEARCH, token);
        StringBuilder query = new StringBuilder("SELECT m FROM MatchdayChallengeTargetCalculation m ");
        if (search != null) {
            search = "%" + search + "%";
            query.append("WHERE m.calculationSql LIKE '")
                    .append(search)
                    .append("'");
        }
        if (matchChallengeID != 0) {
            query.append(search != null ? " AND" : "WHERE")
                    .append(" m.idMatchdayChallenge.id = ")
                    .append(matchChallengeID);
        }
        if (minDate != 0 && maxDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 = new Date(minDate);
            Date d2 = new Date(maxDate);
            query.append(search != null || matchChallengeID != 0 ? " AND" : "WHERE")
                    .append(" m.updateAt BETWEEN '")
                    .append(sdf.format(d1))
                    .append("' AND '")
                    .append(sdf.format(d2))
                    .append("'");
        }
        List<MatchdayChallengeTargetCalculation> targetCalculation = em.createQuery(query.toString()).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
        if (targetCalculation == null || targetCalculation.isEmpty()) {
            throw new DataNotFoundException("There is no target calculation for this search!");
        }
        String countQuery = query.toString().replaceFirst("m", "count(m)");
        System.out.println(countQuery);
        long count = (long) em.createQuery(countQuery).getSingleResult();
        GetObject go = new GetObject();
        go.setCount(count);
        go.setData(MatchdayChallengeTargetCalculationPOJO.toMatchPOJOList(targetCalculation));
        return Response.ok().entity(go).build();
    }

    /**
     * API for this method is .../rest/targetCalculation/{id} This method
     * recieves JSON object, and put it in the base. Example for JSON that you
     * need to send:<br/> {<br/> "calculationSql": null,<br/> "operation":
     * ">=",<br/>
     * "calculationType": null,<br/> "value": 4<br/> }
     *
     * @param token is a header parameter for checking permission
     * @param idMatchdayChallenge id of Matchday challenge
     * @param mctc is an object that Jackson convert from JSON to object
     * @return Response with status CREATED (201)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throw DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Matchday challenge at index 20 does not exits!",<br/>
     * "errorCode": 404<br/> }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response insertTargetCalculation(@HeaderParam("authorization") String token, @PathParam("id") long idMatchdayChallenge, MatchdayChallengeTargetCalculation mctc) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        MatchdayChallenge mc = em.find(MatchdayChallenge.class, idMatchdayChallenge);
        if (mc == null) {
            throw new DataNotFoundException("Matchday challenge at index " + idMatchdayChallenge + " does not exits!");
        }
        mctc.setIdMatchdayChallenge(mc);
        mctc.setCreateDate(new Date());
        if (mctc.getCalculationSql() != null) {
            mctc.setUpdateAt(new Date());
        }
        if (validator.checkLenght(mctc.getCalculationSql(), 255, true) && validator.checkLenght(mctc.getCalculationType(), 255, true) && validator.checkLenght(mctc.getOperation(), 255, true)) {
            helper.persistObject(em, mctc);
            return Response.status(Response.Status.CREATED).build();
        } else {
            throw new InputValidationException("Validation failed");
        }
    }

    /**
     * API for this method is .../rest/targetCalculation This method recieves
     * JSON object, and update database. Example for JSON that you need to send: <br/>
     * {<br/> "calculationSql": "SUM(match_shotsTotal)",<br/> "calculationType":
     * "SUM(match_foulsSuffered)",<br/> "id": 6 <br/>}
     *
     * @param token is a header parameter for checking permission
     * @param mctc is an object that Jackson convert from JSON to object
     * @return Response with status OK (200)
     * @throws InputValidationException Example for this exception: <br/> {<br/>
     * "errorMessage": "Validation failed",<br/>
     * "errorCode": 400<br/> }
     * @throws DataNotFoundException Example for exception:<br/> {<br/>
     * "errorMessage": "Target calculation at index 10 does not exits",<br/>
     * "errorCode": 404<br/> }
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTargetCalculation(@HeaderParam("authorization") String token, MatchdayChallengeTargetCalculation mctc) {
        EntityManager em = EMF.createEntityManager();
        helper.checkUserAndPrivileges(em, TableConstants.STATISTICS, MethodConstants.ADD, token);
        MatchdayChallengeTargetCalculation oldMctc = em.find(MatchdayChallengeTargetCalculation.class, mctc.getId());
        if (oldMctc != null) {
            oldMctc.setUpdateAt(new Date());
            if (validator.checkLenght(mctc.getCalculationSql(), 255, true) && validator.checkLenght(mctc.getCalculationType(), 255, true) && validator.checkLenght(mctc.getOperation(), 255, true)) {
                if (mctc.getCalculationSql() != null) {
                    oldMctc.setCalculationSql(mctc.getCalculationSql());
                }
                if (mctc.getCalculationType() != null) {
                    oldMctc.setCalculationType(mctc.getCalculationType());
                }
                if (mctc.getOperation() != null) {
                    oldMctc.setOperation(mctc.getOperation());
                }
                if (mctc.getValue() != null) {
                    oldMctc.setValue(mctc.getValue());
                }
                helper.mergeObject(em, oldMctc);
            } else {
                throw new InputValidationException("Validation failed");
            }
            return Response.ok().build();
        } else {
            throw new DataNotFoundException("Target calculation at index " + mctc.getId() + " does not exits");
        }
    }
}
