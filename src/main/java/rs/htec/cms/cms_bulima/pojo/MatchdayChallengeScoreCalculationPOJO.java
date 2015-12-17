/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.MatchdayChallengeScoreCalculation;
import rs.htec.cms.cms_bulima.domain.News;

/**
 *
 * @author stefan
 */
public class MatchdayChallengeScoreCalculationPOJO {
    
    private Long id;
    private String calculationSql;
    private Date createDate;
    private Long idMatchdayChallenge;

    public MatchdayChallengeScoreCalculationPOJO(MatchdayChallengeScoreCalculation calculation) {
        this.id = calculation.getId();
        this.calculationSql = calculation.getCalculationSql();
        this.createDate = calculation.getCreateDate();
        if (calculation.getIdMatchdayChallenge() != null) {
            this.idMatchdayChallenge = calculation.getIdMatchdayChallenge().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalculationSql() {
        return calculationSql;
    }

    public void setCalculationSql(String calculationSql) {
        this.calculationSql = calculationSql;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdMatchdayChallenge() {
        return idMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(Long idMatchdayChallenge) {
        this.idMatchdayChallenge = idMatchdayChallenge;
    }
    
    public static List<MatchdayChallengeScoreCalculationPOJO> toNewsPOJOList(List<MatchdayChallengeScoreCalculation> list) {
        MatchdayChallengeScoreCalculationPOJO pojo;
        List<MatchdayChallengeScoreCalculationPOJO> pojos = new ArrayList<>();
        for (MatchdayChallengeScoreCalculation calc : list) {
            pojo = new MatchdayChallengeScoreCalculationPOJO(calc);
            pojos.add(pojo);
        }
        return pojos;
    }
    
    
    
}
