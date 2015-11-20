/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.MatchdayChallengeTargetCalculation;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author marko
 */
public class MatchdayChallengeTargetCalculationPOJO {
    
    private long id;
    private String calculationSql;
    private String operation;
    private String calculationType;
    private Double value;
    private Date updateAt;
    private Date createDate;
    private String matchdayChallenge;
    private String urlToMatchdayChallenge;

    public MatchdayChallengeTargetCalculationPOJO(MatchdayChallengeTargetCalculation mctc) {
        this.id = mctc.getId();
        this.calculationSql = mctc.getCalculationSql();
        this.operation = mctc.getOperation();
        this.calculationType = mctc.getCalculationType();
        this.value = mctc.getValue();
        this.updateAt = mctc.getUpdateAt();
        this.createDate = mctc.getCreateDate();
        matchdayChallenge = mctc.getIdMatchdayChallenge().getMatchdayChallengeTitle();
        this.urlToMatchdayChallenge = Util.getInstance().getUrl() + "rest/matchday_challenges/" + mctc.getIdMatchdayChallenge().getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCalculationSql() {
        return calculationSql;
    }

    public void setCalculationSql(String calculationSql) {
        this.calculationSql = calculationSql;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public void setCalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUrlToMatchdayChallenge() {
        return urlToMatchdayChallenge;
    }

    public void setUrlToMatchdayChallenge(String urlToMatchdayChallenge) {
        this.urlToMatchdayChallenge = urlToMatchdayChallenge;
    }
    
    public static List<MatchdayChallengeTargetCalculationPOJO> toMatchPOJOList(List<MatchdayChallengeTargetCalculation> targetCalculations){
        MatchdayChallengeTargetCalculationPOJO pojo;
        List<MatchdayChallengeTargetCalculationPOJO> pojos = new ArrayList<>();
        for (MatchdayChallengeTargetCalculation targetCalculation : targetCalculations) {
            pojo = new MatchdayChallengeTargetCalculationPOJO(targetCalculation);
            pojos.add(pojo);
        }
        return pojos;
    }

    public String getMatchdayChallenge() {
        return matchdayChallenge;
    }

    public void setMatchdayChallenge(String matchdayChallenge) {
        this.matchdayChallenge = matchdayChallenge;
    }
    
    
}
