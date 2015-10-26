/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.MatchdayChallenge;

/**
 *
 * @author lazar
 */
public class MatchdayChallengePOJO {
    
    private Long id;
    private String matchdayChallengeTitle;
    private String matchdayChallengeDescription;
    private String matchdayChallengeType;
    private int cost;
    private String logo;
    private String formation;
    private Short isPublished;
    private Date createDate;
    private String target;
    private String subheadline;
    private String squad;
    private Long idReward;
    private long idMatchday;

    public MatchdayChallengePOJO(MatchdayChallenge matchdayChallenge) {
        id = matchdayChallenge.getId();
        matchdayChallengeTitle = matchdayChallenge.getMatchdayChallengeTitle();
        matchdayChallengeDescription = matchdayChallenge.getMatchdayChallengeDescription();
        matchdayChallengeType = matchdayChallenge.getMatchdayChallengeType();
        cost = matchdayChallenge.getCost();
        logo = matchdayChallenge.getLogo();
        formation = matchdayChallenge.getFormation();
        isPublished = matchdayChallenge.getIsPublished();
        createDate = matchdayChallenge.getCreateDate();
        target = matchdayChallenge.getTarget();
        subheadline = matchdayChallenge.getSubheadline();
        squad = matchdayChallenge.getSquad();
        idReward = matchdayChallenge.getIdReward().getId();
        idMatchday = matchdayChallenge.getIdMatchday().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchdayChallengeTitle() {
        return matchdayChallengeTitle;
    }

    public void setMatchdayChallengeTitle(String matchdayChallengeTitle) {
        this.matchdayChallengeTitle = matchdayChallengeTitle;
    }

    public String getMatchdayChallengeDescription() {
        return matchdayChallengeDescription;
    }

    public void setMatchdayChallengeDescription(String matchdayChallengeDescription) {
        this.matchdayChallengeDescription = matchdayChallengeDescription;
    }

    public String getMatchdayChallengeType() {
        return matchdayChallengeType;
    }

    public void setMatchdayChallengeType(String matchdayChallengeType) {
        this.matchdayChallengeType = matchdayChallengeType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Short getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Short isPublished) {
        this.isPublished = isPublished;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSubheadline() {
        return subheadline;
    }

    public void setSubheadline(String subheadline) {
        this.subheadline = subheadline;
    }

    public String getSquad() {
        return squad;
    }

    public void setSquad(String squad) {
        this.squad = squad;
    }

    public Long getIdReward() {
        return idReward;
    }

    public void setIdReward(Long idReward) {
        this.idReward = idReward;
    }

    public long getIdMatchday() {
        return idMatchday;
    }

    public void setIdMatchday(long idMatchday) {
        this.idMatchday = idMatchday;
    }
    
}
