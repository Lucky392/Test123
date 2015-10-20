/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUp;

/**
 *
 * @author lazar
 */
@XmlRootElement
public class FantasyManagerMatchdayChallengeLineUpPOJO {

    long id;
    String ID_FANTASY_MANAGER;
    String ID_MATCHDAY_CHALLENGE;
    String formation;
    Date createDate;

    public FantasyManagerMatchdayChallengeLineUpPOJO(FantasyManagerMatchdayChallengeLineUp fantasyManagerMatchdayChallengeLineUp) {
        this.id = fantasyManagerMatchdayChallengeLineUp.getId();
        //TODO:
        //ADD URL FOR FANTASY MANAGER AND MATCHDAY
        this.ID_FANTASY_MANAGER = "" + fantasyManagerMatchdayChallengeLineUp.getIdFantasyManager().getId();
        this.ID_MATCHDAY_CHALLENGE = "" + fantasyManagerMatchdayChallengeLineUp.getIdMatchdayChallenge().getId();
        this.formation = fantasyManagerMatchdayChallengeLineUp.getFormation();
        this.createDate = fantasyManagerMatchdayChallengeLineUp.getCreateDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getID_FANTASY_MANAGER() {
        return ID_FANTASY_MANAGER;
    }

    public void setID_FANTASY_MANAGER(String ID_FANTASY_MANAGER) {
        this.ID_FANTASY_MANAGER = ID_FANTASY_MANAGER;
    }

    public String getID_MATCHDAY_CHALLENGE() {
        return ID_MATCHDAY_CHALLENGE;
    }

    public void setID_MATCHDAY_CHALLENGE(String ID_MATCHDAY_CHALLENGE) {
        this.ID_MATCHDAY_CHALLENGE = ID_MATCHDAY_CHALLENGE;
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
    
    

}
