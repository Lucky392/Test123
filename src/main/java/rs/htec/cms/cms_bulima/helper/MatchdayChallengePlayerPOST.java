/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.List;

/**
 *
 * @author marko
 */
public class MatchdayChallengePlayerPOST {
    
    private Long matchdayID;
    private List<Long> listOfPlayersID;

    public Long getMatchdayID() {
        return matchdayID;
    }

    public void setMatchdayID(Long matchdayID) {
        this.matchdayID = matchdayID;
    }

    public List<Long> getListOfPlayersID() {
        return listOfPlayersID;
    }

    public void setListOfPlayersID(List<Long> listOfPlayersID) {
        this.listOfPlayersID = listOfPlayersID;
    }
    
    
}
