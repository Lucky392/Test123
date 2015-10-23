/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.Matchday;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class MatchdayPOJO {

    private Long id;
    private String idSport1Matchday;
    private Integer matchday;
    private Short isCurrent;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Short isCalculated;
    private Short isCompleted;
    private Long idSeason;
    private String urlToSeason;

    public MatchdayPOJO(Matchday matchdayDb) {
        this.id = matchdayDb.getId();
        this.idSport1Matchday = matchdayDb.getIdSport1Matchday();
        this.matchday = matchdayDb.getMatchday();
        this.isCurrent = matchdayDb.getIsCurrent();
        this.startDate = matchdayDb.getStartDate();
        this.endDate = matchdayDb.getEndDate();
        this.createDate = matchdayDb.getCreateDate();
        this.isCalculated = matchdayDb.getIsCalculated();
        this.isCompleted = matchdayDb.getIsCompleted();
        this.idSeason = matchdayDb.getIdSeason().getId();
        this.urlToSeason = Util.getInstance().getUrl() + "rest/season/" + matchdayDb.getIdSeason().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1Matchday() {
        return idSport1Matchday;
    }

    public void setIdSport1Matchday(String idSport1Matchday) {
        this.idSport1Matchday = idSport1Matchday;
    }

    public Integer getMatchday() {
        return matchday;
    }

    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }

    public Short getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Short isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getIsCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(Short isCalculated) {
        this.isCalculated = isCalculated;
    }

    public Short getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Short isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Long getIdSeason() {
        return idSeason;
    }

    public String getUrlToSeason() {
        return urlToSeason;
    }

    public void setUrlToSeason(String urlToSeason) {
        this.urlToSeason = urlToSeason;
    }

    public void setIdSeason(Long idSeason) {
        this.idSeason = idSeason;
    }

    public static List<MatchdayPOJO> toMatchdayPOJOList(List<Matchday> list) {
        MatchdayPOJO pojo;
        List<MatchdayPOJO> pojos = new ArrayList<>();
        for (Matchday matchday : list) {
            pojo = new MatchdayPOJO(matchday);
            pojos.add(pojo);
        }
        return pojos;
    }

}
