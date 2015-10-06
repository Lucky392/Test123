/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.domain.Formation;

/**
 *
 * @author stefan
 */
public class LineUpDifference {

    private JSONArray lineUpDifference;
    private Formation formationDifference;

    public JSONArray getLineUpDifference() {
        return lineUpDifference;
    }

    public void setLineUpDifference(JSONArray lineUpDifference) {
        this.lineUpDifference = lineUpDifference;
    }

    public Formation getFormationDifference() {
        return formationDifference;
    }

    public void setFormationDifference(Formation formationDifference) {
        this.formationDifference = formationDifference;
    }

}
