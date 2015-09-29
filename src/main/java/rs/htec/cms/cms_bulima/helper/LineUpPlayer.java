/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.Date;

/**
 *
 * @author stefan
 */
public class LineUpPlayer {

    private Long id;
    private Short isCaptain;
    private Date createDate;
    private Long idPlayerSlot;

    public LineUpPlayer(Long id, Short isCaptain, Date createDate, Long idPlayerSlot, Long idLeaguePlayer, Long idLineUp) {
        this.id = id;
        this.isCaptain = isCaptain;
        this.createDate = createDate;
        this.idPlayerSlot = idPlayerSlot;
        this.idLeaguePlayer = idLeaguePlayer;
        this.idLineUp = idLineUp;
    }
    private Long idLeaguePlayer;
    private Long idLineUp;

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

}
