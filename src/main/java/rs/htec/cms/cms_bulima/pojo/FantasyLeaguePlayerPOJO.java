/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.FantasyLeaguePlayer;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class FantasyLeaguePlayerPOJO {

    private Long id;
    private short hasClub;
    private short isInAuction;
    private Date createDate;
    private Long idFantasyLeague;
    private Long idPlayer;
    private String urlToFantasyLeague;
    private String urlToPlayer;

    public FantasyLeaguePlayerPOJO(FantasyLeaguePlayer player) {
        this.id = player.getId();
        this.hasClub = player.getHasClub();
        this.isInAuction = player.getIsInAuction();
        this.createDate = player.getCreateDate();
        this.idFantasyLeague = player.getIdFantasyLeague().getId();
        this.idPlayer = player.getIdPlayer().getId();
        if (player.getIdFantasyLeague() != null) {
            this.urlToFantasyLeague = Util.getInstance().getUrl() + "rest/fantasyLeagues/" + player.getIdFantasyLeague().getId();
        }
        if (player.getIdPlayer() != null) {
            this.urlToPlayer = Util.getInstance().getUrl() + "rest/players/" + player.getIdPlayer().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getHasClub() {
        return hasClub;
    }

    public void setHasClub(short hasClub) {
        this.hasClub = hasClub;
    }

    public short getIsInAuction() {
        return isInAuction;
    }

    public void setIsInAuction(short isInAuction) {
        this.isInAuction = isInAuction;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(Long idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getUrlToFantasyLeague() {
        return urlToFantasyLeague;
    }

    public void setUrlToFantasyLeague(String urlToFantasyLeague) {
        this.urlToFantasyLeague = urlToFantasyLeague;
    }

    public String getUrlToPlayer() {
        return urlToPlayer;
    }

    public void setUrlToPlayer(String urlToPlayer) {
        this.urlToPlayer = urlToPlayer;
    }
}
