/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.League;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author marko
 */
public class LeaguePOJO {

    private long id;
    private String idSport1League;
    private String sport;
    private Date createDate;
    private String competitionUrl;
    private String competitionName;

    public LeaguePOJO(League league) {
        this.id = league.getId();
        this.idSport1League = league.getIdSport1League();
        this.sport = league.getSport();
        this.createDate = league.getCreateDate();
        if (league.getIdCompetition() != null) {
            this.competitionUrl = Util.getInstance().getUrl() + "rest/competitions/" + league.getIdCompetition().getId();
            this.competitionName = league.getIdCompetition().getName();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdSport1League() {
        return idSport1League;
    }

    public void setIdSport1League(String idSport1League) {
        this.idSport1League = idSport1League;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCompetitionUrl() {
        return competitionUrl;
    }

    public void setCompetitionUrl(String competitionUrl) {
        this.competitionUrl = competitionUrl;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

}
