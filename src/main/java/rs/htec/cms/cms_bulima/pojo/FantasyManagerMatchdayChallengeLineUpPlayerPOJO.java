/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.FantasyManagerMatchdayChallengeLineUpPlayer;

/**
 *
 * @author stefan
 */
public class FantasyManagerMatchdayChallengeLineUpPlayerPOJO {

    
    private Long id;
    private Integer slot;
    private Date createDate;
    private long idMatchdayChallengePlayer;
    private long idMatchdayChallengeLineUp;

    public FantasyManagerMatchdayChallengeLineUpPlayerPOJO(Long id, Integer slot, Date createDate, long idMatchdayChallengePlayer, long idMatchdayChallengeLineUp) {
        this.id = id;
        this.slot = slot;
        this.createDate = createDate;
        this.idMatchdayChallengePlayer = idMatchdayChallengePlayer;
        this.idMatchdayChallengeLineUp = idMatchdayChallengeLineUp;
    }

    public FantasyManagerMatchdayChallengeLineUpPlayerPOJO(FantasyManagerMatchdayChallengeLineUpPlayer player) {
        this.id = player.getId();
        this.slot = player.getSlot();
        this.createDate = player.getCreateDate();
        this.idMatchdayChallengePlayer = player.getIdMatchdayChallengePlayer().getId();
        this.idMatchdayChallengeLineUp = player.getIdMatchdayChallengeLineUp().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getIdMatchdayChallengePlayer() {
        return idMatchdayChallengePlayer;
    }

    public void setIdMatchdayChallengePlayer(long idMatchdayChallengePlayer) {
        this.idMatchdayChallengePlayer = idMatchdayChallengePlayer;
    }

    public long getIdMatchdayChallengeLineUp() {
        return idMatchdayChallengeLineUp;
    }

    public void setIdMatchdayChallengeLineUp(long idMatchdayChallengeLineUp) {
        this.idMatchdayChallengeLineUp = idMatchdayChallengeLineUp;
    }

    public static List<FantasyManagerMatchdayChallengeLineUpPlayerPOJO> toFantasyManagerMatchdayChallengeLineUpPlayerPOJOList(List<FantasyManagerMatchdayChallengeLineUpPlayer> challengeLineUpPlayerList) {
        FantasyManagerMatchdayChallengeLineUpPlayerPOJO pojo;
        List<FantasyManagerMatchdayChallengeLineUpPlayerPOJO> pojos = new ArrayList<>();
        for (FantasyManagerMatchdayChallengeLineUpPlayer player : challengeLineUpPlayerList) {
            pojo = new FantasyManagerMatchdayChallengeLineUpPlayerPOJO(player);
            pojos.add(pojo);
        }
        return pojos;
    }
    
}
