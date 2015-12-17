/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import rs.htec.cms.cms_bulima.domain.MatchdayChallengePlayer;
import rs.htec.cms.cms_bulima.domain.Player;

/**
 *
 * @author marko
 */
public class MatchdayChallengePlayerPOJO {
    
    private Long id;
    private Long idMatchdayChallenge;
    private String matchdayChallengeTitle;
    private Long idPlayer;
    private String playerName;

    public MatchdayChallengePlayerPOJO(MatchdayChallengePlayer player) {
        this.id = player.getId();
        if (player.getIdMatchdayChallenge() != null){
            this.idMatchdayChallenge = player.getIdMatchdayChallenge().getId();
            this.matchdayChallengeTitle = player.getIdMatchdayChallenge().getMatchdayChallengeTitle();
        }
        if (player.getIdPlayer() != null){
            this.idPlayer = player.getIdPlayer().getId();
            this.playerName = player.getIdPlayer().getFullname();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMatchdayChallenge() {
        return idMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(Long idMatchdayChallenge) {
        this.idMatchdayChallenge = idMatchdayChallenge;
    }

    public String getMatchdayChallengeTitle() {
        return matchdayChallengeTitle;
    }

    public void setMatchdayChallengeTitle(String matchdayChallengeTitle) {
        this.matchdayChallengeTitle = matchdayChallengeTitle;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
