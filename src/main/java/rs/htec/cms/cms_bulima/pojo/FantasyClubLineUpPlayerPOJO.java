/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class FantasyClubLineUpPlayerPOJO {

    private Long id;
    private Short isCaptain;
    private Date createDate;
    private Long idPlayerSlot;
    private String urlToPlayerSlot;
    private Long idLeaguePlayer;
    private String urlToLeaguePlayer;
    private Long idLineUp;
    private String urlToLineUp;

    public FantasyClubLineUpPlayerPOJO(FantasyClubLineUpPlayer player) {
        this.id = player.getId();
        this.isCaptain = player.getIsCaptain();
        this.createDate = player.getCreateDate();
        if (player.getIdPlayerSlot() != null) {
            this.idPlayerSlot = player.getIdPlayerSlot().getId();
            this.urlToPlayerSlot = Util.getInstance().getUrl() + "rest/playerSlot/" + player.getIdPlayerSlot().getId();
        }
        if (player.getIdLeaguePlayer() != null) {
            this.idLeaguePlayer = player.getIdLeaguePlayer().getId();
            this.urlToLeaguePlayer = Util.getInstance().getUrl() + "rest/fantasyLeaguePlayer/" + player.getIdLeaguePlayer().getId();
        }
        if (player.getIdLineUp() != null) {
            this.idLineUp = player.getIdLineUp().getId();
            this.urlToLineUp = Util.getInstance().getUrl() + "rest/lineup/" + player.getIdLineUp().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Short isCaptain) {
        this.isCaptain = isCaptain;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdPlayerSlot() {
        return idPlayerSlot;
    }

    public void setIdPlayerSlot(Long idPlayerSlot) {
        this.idPlayerSlot = idPlayerSlot;
    }

    public Long getIdLeaguePlayer() {
        return idLeaguePlayer;
    }

    public void setIdLeaguePlayer(Long idLeaguePlayer) {
        this.idLeaguePlayer = idLeaguePlayer;
    }

    public Long getIdLineUp() {
        return idLineUp;
    }

    public void setIdLineUp(Long idLineUp) {
        this.idLineUp = idLineUp;
    }

    public String getUrlToPlayerSlot() {
        return urlToPlayerSlot;
    }

    public void setUrlToPlayerSlot(String urlToPlayerSlot) {
        this.urlToPlayerSlot = urlToPlayerSlot;
    }

    public String getUrlToLeaguePlayer() {
        return urlToLeaguePlayer;
    }

    public void setUrlToLeaguePlayer(String urlToLeaguePlayer) {
        this.urlToLeaguePlayer = urlToLeaguePlayer;
    }

    public String getUrlToLineUp() {
        return urlToLineUp;
    }

    public void setUrlToLineUp(String urlToLineUp) {
        this.urlToLineUp = urlToLineUp;
    }

}