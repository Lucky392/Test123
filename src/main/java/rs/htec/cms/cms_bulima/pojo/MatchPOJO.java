/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.BugReport;
import rs.htec.cms.cms_bulima.domain.Match;

/**
 *
 * @author marko
 */
public class MatchPOJO {

    private long id;
    private String idSport1Match;
    private Date updateAt;
    private Integer homeScore;
    private Integer guestScore;
    private Date createDate;
    private Date startTime;
    private Short isCalculated;
    private String clubHomeUrl;
    private String clubHomeName;
    private String clubGuesUrl;
    private String clubGuestName;
    private String matchdayUrl;

    public MatchPOJO(Match match) {
        this.id = match.getId();
        this.idSport1Match = match.getIdSport1Match();
        this.updateAt = match.getUpdateAt();
        this.homeScore = match.getHomeScore();
        this.guestScore = match.getGuestScore();
        this.createDate = match.getCreateDate();
        this.startTime = match.getStartTime();
        this.isCalculated = match.getIsCalculated();
        this.clubGuesUrl = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/club/" + match.getIdGuestClub().getId();
        this.clubGuestName = match.getIdGuestClub().getMediumName();
        this.clubHomeUrl = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/club/" + match.getIdHomeClub().getId();
        this.clubHomeName = match.getIdHomeClub().getMediumName();
        this.matchdayUrl = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/matchday/" + match.getIdMatchday().getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdSport1Match() {
        return idSport1Match;
    }

    public void setIdSport1Match(String idSport1Match) {
        this.idSport1Match = idSport1Match;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(Integer guestScore) {
        this.guestScore = guestScore;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Short getIsCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(Short isCalculated) {
        this.isCalculated = isCalculated;
    }

    public String getMatchdayUrl() {
        return matchdayUrl;
    }

    public void setMatchdayUrl(String matchdayUrl) {
        this.matchdayUrl = matchdayUrl;
    }

    public String getClubHomeUrl() {
        return clubHomeUrl;
    }

    public void setClubHomeUrl(String clubHomeUrl) {
        this.clubHomeUrl = clubHomeUrl;
    }

    public String getClubHomeName() {
        return clubHomeName;
    }

    public void setClubHomeName(String clubHomeName) {
        this.clubHomeName = clubHomeName;
    }

    public String getClubGuesUrl() {
        return clubGuesUrl;
    }

    public void setClubGuesUrl(String clubGuesUrl) {
        this.clubGuesUrl = clubGuesUrl;
    }

    public String getClubGuestName() {
        return clubGuestName;
    }

    public void setClubGuestName(String clubGuestName) {
        this.clubGuestName = clubGuestName;
    }
    
    public static List<MatchPOJO> toMatchPOJOList(List<Match> matches){
        MatchPOJO pojo;
        List<MatchPOJO> pojos = new ArrayList<>();
        for (Match match : matches) {
            pojo = new MatchPOJO(match);
            pojos.add(pojo);
        }
        return pojos;
    }
}
