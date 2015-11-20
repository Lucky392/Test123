/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.Season;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class SeasonPOJO {

    private Long id;
    private String idSport1Season;
    private String name;
    private Date createDate;
    private Long idFirstMatchday;
    private String firstMatchdayName;
    private String urlToFirstMatchday;
    private Long idLeague;
    private String leagueName;
    private String urlToLeague;

    public SeasonPOJO(Season season) {
        this.id = season.getId();
        this.idSport1Season = season.getIdSport1Season();
        this.name = season.getName();
        this.createDate = season.getCreateDate();
        if (season.getIdFirstMatchday() != null) {
            this.idFirstMatchday = season.getIdFirstMatchday().getId();
            firstMatchdayName = season.getIdFirstMatchday().getStartDate().getYear() + "/" + season.getIdFirstMatchday().getEndDate().getYear();
            this.urlToFirstMatchday = Util.getInstance().getUrl() + "rest/matchdays/" + season.getIdFirstMatchday().getId();
        }
        if (season.getIdLeague() != null) {
            this.idLeague = season.getIdLeague().getId();
            leagueName = season.getIdLeague().getIdSport1League();
            this.urlToLeague = Util.getInstance().getUrl() + "rest/leagues/" + season.getIdLeague().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdSport1Season() {
        return idSport1Season;
    }

    public void setIdSport1Season(String idSport1Season) {
        this.idSport1Season = idSport1Season;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdFirstMatchday() {
        return idFirstMatchday;
    }

    public void setIdFirstMatchday(Long idFirstMatchday) {
        this.idFirstMatchday = idFirstMatchday;
    }

    public Long getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(Long idLeague) {
        this.idLeague = idLeague;
    }

    public String getUrlToFirstMatchday() {
        return urlToFirstMatchday;
    }

    public void setUrlToFirstMatchday(String urlToFirstMatchday) {
        this.urlToFirstMatchday = urlToFirstMatchday;
    }

    public String getUrlToLeague() {
        return urlToLeague;
    }

    public void setUrlToLeague(String urlToLeague) {
        this.urlToLeague = urlToLeague;
    }

    public String getFirstMatchdayName() {
        return firstMatchdayName;
    }

    public void setFirstMatchdayName(String firstMatchdayName) {
        this.firstMatchdayName = firstMatchdayName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

}
