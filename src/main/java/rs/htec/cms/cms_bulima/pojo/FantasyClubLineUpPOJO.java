/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class FantasyClubLineUpPOJO {

    private Long id;
    private Date createDate;
    private Integer marketValue;
    private Integer credit;
    private Long idMatchday;
    private String urlToMatchday;
    private Long idFantasyClub;
    private String fantasyClubName;
    private Long idFormation;
    private String formationName;
    private Long idFantasyLeague;
    private String fantasyLeagueName;
    private String urlToFantasyLeague;
    private String urlToFormation;
    private String urlToFantasyClub;

    public FantasyClubLineUpPOJO(FantasyClubLineUp lineup) {
        this.id = lineup.getId();
        this.createDate = lineup.getCreateDate();
        this.marketValue = lineup.getMarketValue();
        this.credit = lineup.getCredit();
        if (lineup.getIdMatchday() != null) {
            this.idMatchday = lineup.getIdMatchday().getId();
            this.urlToMatchday = Util.getInstance().getUrl() + "rest/matchdays/" + lineup.getIdMatchday().getId();
        }
        if (lineup.getIdFantasyClub() != null) {
            this.idFantasyClub = lineup.getIdFantasyClub().getId();
            this.fantasyClubName = lineup.getIdFantasyClub().getName();
            this.urlToFantasyClub = Util.getInstance().getUrl() + "rest/fantasyClubs/" + lineup.getIdFantasyClub().getId();
        }
        if (lineup.getIdFormation() != null) {
            this.idFormation = lineup.getIdFormation().getId();
            this.formationName = lineup.getIdFormation().getName();
            this.urlToFormation = Util.getInstance().getUrl() + "rest/formations/" + lineup.getIdFormation().getId();
        }
        if (lineup.getIdFantasyLeague() != null) {
            this.idFantasyLeague = lineup.getIdFantasyLeague().getId();
            this.fantasyLeagueName = lineup.getIdFantasyLeague().getName();
            this.urlToFantasyLeague = Util.getInstance().getUrl() + "rest/fantasyLeagues/" + lineup.getIdFantasyLeague().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Integer marketValue) {
        this.marketValue = marketValue;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Long getIdMatchday() {
        return idMatchday;
    }

    public void setIdMatchday(Long idMatchday) {
        this.idMatchday = idMatchday;
    }

    public Long getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(Long idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    public Long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Long idFormation) {
        this.idFormation = idFormation;
    }


    public Long getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(Long idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    public String getFantasyClubName() {
        return fantasyClubName;
    }

    public void setFantasyClubName(String fantasyClubName) {
        this.fantasyClubName = fantasyClubName;
    }

    public String getFormationName() {
        return formationName;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }

    public String getFantasyLeagueName() {
        return fantasyLeagueName;
    }

    public void setFantasyLeagueName(String fantasyLeagueName) {
        this.fantasyLeagueName = fantasyLeagueName;
    }

    public static List<FantasyClubLineUpPOJO> toFantasyClubLineUpPOJOList(List<FantasyClubLineUp> list) {
        FantasyClubLineUpPOJO pojo;
        List<FantasyClubLineUpPOJO> pojos = new ArrayList<>();
        for (FantasyClubLineUp lineup : list) {
            pojo = new FantasyClubLineUpPOJO(lineup);
            pojos.add(pojo);
        }
        return pojos;
    }

    public String getUrlToFantasyLeague() {
        return urlToFantasyLeague;
    }

    public void setUrlToFantasyLeague(String urlToFantasyLeague) {
        this.urlToFantasyLeague = urlToFantasyLeague;
    }

    public String getUrlToFormation() {
        return urlToFormation;
    }

    public void setUrlToFormation(String urlToFormation) {
        this.urlToFormation = urlToFormation;
    }

    public String getUrlToFantasyClub() {
        return urlToFantasyClub;
    }

    public void setUrlToFantasyClub(String urlToFantasyClub) {
        this.urlToFantasyClub = urlToFantasyClub;
    }

    public String getUrlToMatchday() {
        return urlToMatchday;
    }

    public void setUrlToMatchday(String urlToMatchday) {
        this.urlToMatchday = urlToMatchday;
    }

    
}
