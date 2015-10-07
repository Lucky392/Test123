/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

/**
 *
 * @author stefan
 */
public class LineUpPlayerPOJO {

    private Long id;
    private String playerFullName;
    private Short isCaptain;
    private Long idPlayerSlot;
    private Long idLineUp;

    public LineUpPlayerPOJO(long id, Short isCaptain, Long idPlayerSlot, String playerFullName, Long idLineUp) {
        this.playerFullName = playerFullName;
        this.isCaptain = isCaptain;
        this.idPlayerSlot = idPlayerSlot;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
