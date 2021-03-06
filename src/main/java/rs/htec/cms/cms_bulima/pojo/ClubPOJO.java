/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.Date;
import rs.htec.cms.cms_bulima.domain.Club;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author marko
 */
public class ClubPOJO {

    private long id;
    private String idSport1Team;
    private String mediumName;
    private String shortName;
    private String logoUrl;
    private Date createDate;
    private byte[] logo;
    private String leagueUrl;
    private String leagueName;

    public ClubPOJO(Club club) {
        this.id = club.getId();
        this.idSport1Team = club.getIdSport1Team();
        this.mediumName = club.getMediumName();
        this.shortName = club.getShortName();
        this.logoUrl = club.getLogoUrl();
        this.createDate = club.getCreateDate();
        this.logo = club.getLogo();
        if (club.getIdLeague() != null) {
            this.leagueUrl = Util.getInstance().getUrl() + "rest/leagues/" + club.getIdLeague().getId();
            if (club.getIdLeague().getIdCompetition() != null){
                this.leagueName = club.getIdLeague().getIdCompetition().getName();
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdSport1Team() {
        return idSport1Team;
    }

    public void setIdSport1Team(String idSport1Team) {
        this.idSport1Team = idSport1Team;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLeagueUrl() {
        return leagueUrl;
    }

    public void setLeagueUrl(String leagueUrl) {
        this.leagueUrl = leagueUrl;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

}
