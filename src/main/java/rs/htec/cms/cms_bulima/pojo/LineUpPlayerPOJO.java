/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;

/**
 *
 * @author stefan
 */
public class LineUpPlayerPOJO {

    private String playerFullName;
    private Short isCaptain;
    private Long idPlayerSlot;
    private Long idLeaguePlayer;
    private Long idLineUp;

    public LineUpPlayerPOJO(String playerFullName, Short isCaptain, Long idPlayerSlot, Long idLeaguePlayer, Long idLineUp) {
        this.playerFullName = playerFullName;
        this.isCaptain = isCaptain;
        this.idPlayerSlot = idPlayerSlot;
        this.idLeaguePlayer = idLeaguePlayer;
        this.idLineUp = idLineUp;
    }

    public Short getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Short isCaptain) {
        this.isCaptain = isCaptain;
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

    public String getPlayerFullName() {
        return playerFullName;
    }

    public void setPlayerFullName(String playerFullName) {
        this.playerFullName = playerFullName;
    }
}
