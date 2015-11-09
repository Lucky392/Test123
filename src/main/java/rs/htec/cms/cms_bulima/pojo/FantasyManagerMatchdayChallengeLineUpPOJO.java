/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUpPlayer;

/**
 *
 * @author lazar
 */
@XmlRootElement
public class FantasyManagerMatchdayChallengeLineUpPOJO {

    long id;
    private Long IdFantasyManager;
    private Long IdMatchdayChallenge;
    private String fantasyManager;
    private String matchdayChallenge;
    private String formation;
    private Date createDate;

    public FantasyManagerMatchdayChallengeLineUpPOJO(FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp) {
        this.id = fantasyManagerMatchdayChallengeLineUp.getId();
        if (fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager() != null) {
            this.IdFantasyManager = fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager().getId();
            fantasyManager = fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager().getUsername();
        }
        if (fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge() != null) {
            this.IdMatchdayChallenge = fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge().getId();
            matchdayChallenge = fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge().getMatchdayChallengeTitle();
        }
        this.formation = fantasyManagerMatchdayChallengeLineUp.getFormation();
        this.createDate = fantasyManagerMatchdayChallengeLineUp.getCreateDate();
    }

    public FantasyManagerMatchdayChallengeLineUpPOJO(FantasyManagerMatchdayChallengeLineUpPlayer matchdayChallengeLineUpPlayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdFantasyManager() {
        return IdFantasyManager;
    }

    public void setIdFantasyManager(Long IdFantasyManager) {
        this.IdFantasyManager = IdFantasyManager;
    }

    public Long getIdMatchdayChallenge() {
        return IdMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(Long IdMatchdayChallenge) {
        this.IdMatchdayChallenge = IdMatchdayChallenge;
    }

    public String getFantasyManager() {
        return fantasyManager;
    }

    public void setFantasyManager(String fantasyManager) {
        this.fantasyManager = fantasyManager;
    }

    public String getMatchdayChallenge() {
        return matchdayChallenge;
    }

    public void setMatchdayChallenge(String matchdayChallenge) {
        this.matchdayChallenge = matchdayChallenge;
    }
    
    

}
