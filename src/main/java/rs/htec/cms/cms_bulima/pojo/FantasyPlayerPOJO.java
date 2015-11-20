/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.FantasyPlayer;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class FantasyPlayerPOJO {

    private Long id;
    private Date joinFantasyClubTimestamp;
    private Date createDate;
    private Long idPlayerSlot;
    private Long idFantasyClub;
    private Long idLeaguePlayer;
    private String urlToPlaterSlot;
    private String urlToFantasyClub;
    private String urlToLeaguePlayer;
    private String fantasyClubName;
    private String leaguePlayerName;
    private String playerSlotName;

    public FantasyPlayerPOJO(FantasyPlayer player) {
        this.id = player.getId();
        this.joinFantasyClubTimestamp = player.getJoinFantasyClubTimestamp();
        this.createDate = player.getCreateDate();
        this.idPlayerSlot = player.getIdPlayerSlot().getId();
        this.idFantasyClub = player.getIdFantasyClub().getId();
        this.idLeaguePlayer = player.getIdLeaguePlayer().getId();
        if (player.getIdFantasyClub() != null) {
            this.urlToFantasyClub = Util.getInstance().getUrl() + "rest/fantasyClubs/" + player.getIdFantasyClub().getId();
            this.fantasyClubName = player.getIdFantasyClub().getName();
        }
        if (player.getIdLeaguePlayer() != null) {
            this.urlToLeaguePlayer = Util.getInstance().getUrl() + "rest/fantasyLeaguePlayers/" + player.getIdLeaguePlayer().getId();
            this.leaguePlayerName = player.getIdLeaguePlayer().getIdPlayer().getFirstName() + " " + player.getIdLeaguePlayer().getIdPlayer().getLastName();
        }
        if (player.getIdPlayerSlot() != null) {
            this.urlToPlaterSlot = Util.getInstance().getUrl() + "rest/playerSlots/" + player.getIdPlayerSlot().getId();
            this.playerSlotName = player.getIdPlayerSlot().getName();
        }
    }

    public String getUrlToPlaterSlot() {
        return urlToPlaterSlot;
    }

    public void setUrlToPlaterSlot(String urlToPlaterSlot) {
        this.urlToPlaterSlot = urlToPlaterSlot;
    }

    public String getUrlToFantasyClub() {
        return urlToFantasyClub;
    }

    public void setUrlToFantasyClub(String urlToFantasyClub) {
        this.urlToFantasyClub = urlToFantasyClub;
    }

    public String getUrlToLeaguePlayer() {
        return urlToLeaguePlayer;
    }

    public void setUrlToLeaguePlayer(String urlToLeaguePlayer) {
        this.urlToLeaguePlayer = urlToLeaguePlayer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getJoinFantasyClubTimestamp() {
        return joinFantasyClubTimestamp;
    }

    public void setJoinFantasyClubTimestamp(Date joinFantasyClubTimestamp) {
        this.joinFantasyClubTimestamp = joinFantasyClubTimestamp;
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

    public Long getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(Long idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    public Long getIdLeaguePlayer() {
        return idLeaguePlayer;
    }

    public void setIdLeaguePlayer(Long idLeaguePlayer) {
        this.idLeaguePlayer = idLeaguePlayer;
    }

    public String getFantasyClubName() {
        return fantasyClubName;
    }

    public void setFantasyClubName(String fantasyClubName) {
        this.fantasyClubName = fantasyClubName;
    }

    public String getLeaguePlayerName() {
        return leaguePlayerName;
    }

    public void setLeaguePlayerName(String leaguePlayerName) {
        this.leaguePlayerName = leaguePlayerName;
    }

    public String getPlayerSlotName() {
        return playerSlotName;
    }

    public void setPlayerSlotName(String playerSlotName) {
        this.playerSlotName = playerSlotName;
    }
}
