/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeResult;

/**
 *
 * @author lazar
 */
public class FantasyManagerMatchdayChallengeResultPOJO {
    
    private Long id;
    private Double score;
    private short isSucceed;
    private Date updateAt;
    private Date createDate;
    private Long idMatchdayChallengeLineUp;
    private String matchdayChallengeLineUp;

    public FantasyManagerMatchdayChallengeResultPOJO(FantasyManagerMatchdayChallengeResult fantasyManagerMatchdayChallengeResult) {
        id = fantasyManagerMatchdayChallengeResult.getId();
        score = fantasyManagerMatchdayChallengeResult.getScore();
        isSucceed = fantasyManagerMatchdayChallengeResult.getIsSucceed();
        updateAt = fantasyManagerMatchdayChallengeResult.getUpdateAt();
        createDate = fantasyManagerMatchdayChallengeResult.getCreateDate();
        if (fantasyManagerMatchdayChallengeResult.getIdMatchdayChallengeLineUp() != null) {
            idMatchdayChallengeLineUp = fantasyManagerMatchdayChallengeResult.getIdMatchdayChallengeLineUp().getId();
            matchdayChallengeLineUp = fantasyManagerMatchdayChallengeResult.getIdMatchdayChallengeLineUp().getFormation();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public short getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(short isSucceed) {
        this.isSucceed = isSucceed;
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

    public Long getIdMatchdayChallengeLineUp() {
        return idMatchdayChallengeLineUp;
    }

    public void setIdMatchdayChallengeLineUp(Long idMatchdayChallengeLineUp) {
        this.idMatchdayChallengeLineUp = idMatchdayChallengeLineUp;
    }

    public String getMatchdayChallengeLineUp() {
        return matchdayChallengeLineUp;
    }

    public void setMatchdayChallengeLineUp(String matchdayChallengeLineUp) {
        this.matchdayChallengeLineUp = matchdayChallengeLineUp;
    }
    
}
