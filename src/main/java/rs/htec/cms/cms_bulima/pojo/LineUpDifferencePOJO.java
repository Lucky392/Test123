/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.List;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;

/**
 *
 * @author stefan
 */
public class LineUpDifferencePOJO {

    private List<FantasyClubLineUpPlayerPOJO> playerDifference;
    private String formationDifference;
    private Long bulimaPointDifference;

    public LineUpDifferencePOJO() {
    }

    public LineUpDifferencePOJO(List<FantasyClubLineUpPlayerPOJO> playerDifference, String formationDifference, Long bulimaPointDifference) {
        this.playerDifference = playerDifference;
        this.formationDifference = formationDifference;
        this.bulimaPointDifference = bulimaPointDifference;
    }

    public List<FantasyClubLineUpPlayerPOJO> getPlayerDifference() {
        return playerDifference;
    }

    public void setPlayerDifference(List<FantasyClubLineUpPlayerPOJO> playerDifference) {
        this.playerDifference = playerDifference;
    }

    public String getFormationDifference() {
        return formationDifference;
    }

    public void setFormationDifference(String formationDifference) {
        this.formationDifference = formationDifference;
    }

    public long getBulimaPointDifference() {
        return bulimaPointDifference;
    }

    public void setBulimaPointDifference(Long bulimaPointDifference) {
        this.bulimaPointDifference = bulimaPointDifference;
    }
    
}
