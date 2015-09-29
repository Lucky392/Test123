/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "FANTASY_LEAGUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyLeague.findAll", query = "SELECT f FROM FantasyLeague f"),
    @NamedQuery(name = "FantasyLeague.findById", query = "SELECT f FROM FantasyLeague f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyLeague.findByName", query = "SELECT f FROM FantasyLeague f WHERE f.name = :name"),
    @NamedQuery(name = "FantasyLeague.findByPassword", query = "SELECT f FROM FantasyLeague f WHERE f.password = :password"),
    @NamedQuery(name = "FantasyLeague.findByLogo", query = "SELECT f FROM FantasyLeague f WHERE f.logo = :logo"),
    @NamedQuery(name = "FantasyLeague.findByDescription", query = "SELECT f FROM FantasyLeague f WHERE f.description = :description"),
    @NamedQuery(name = "FantasyLeague.findByOptions", query = "SELECT f FROM FantasyLeague f WHERE f.options = :options"),
    @NamedQuery(name = "FantasyLeague.findByStartDate", query = "SELECT f FROM FantasyLeague f WHERE f.startDate = :startDate"),
    @NamedQuery(name = "FantasyLeague.findByNumMembers", query = "SELECT f FROM FantasyLeague f WHERE f.numMembers = :numMembers"),
    @NamedQuery(name = "FantasyLeague.findByActivity", query = "SELECT f FROM FantasyLeague f WHERE f.activity = :activity"),
    @NamedQuery(name = "FantasyLeague.findByLeaguehash", query = "SELECT f FROM FantasyLeague f WHERE f.leaguehash = :leaguehash"),
    @NamedQuery(name = "FantasyLeague.findBySecondLeague", query = "SELECT f FROM FantasyLeague f WHERE f.secondLeague = :secondLeague"),
    @NamedQuery(name = "FantasyLeague.findByCreateDate", query = "SELECT f FROM FantasyLeague f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FantasyLeague.findByDayOneActivity", query = "SELECT f FROM FantasyLeague f WHERE f.dayOneActivity = :dayOneActivity"),
    @NamedQuery(name = "FantasyLeague.findByDayTwoActivity", query = "SELECT f FROM FantasyLeague f WHERE f.dayTwoActivity = :dayTwoActivity"),
    @NamedQuery(name = "FantasyLeague.findByDayThreeActivity", query = "SELECT f FROM FantasyLeague f WHERE f.dayThreeActivity = :dayThreeActivity"),
    @NamedQuery(name = "FantasyLeague.findByDayFourActivity", query = "SELECT f FROM FantasyLeague f WHERE f.dayFourActivity = :dayFourActivity"),
    @NamedQuery(name = "FantasyLeague.findByDayFiveActivity", query = "SELECT f FROM FantasyLeague f WHERE f.dayFiveActivity = :dayFiveActivity"),
    @NamedQuery(name = "FantasyLeague.findByDaySixActivity", query = "SELECT f FROM FantasyLeague f WHERE f.daySixActivity = :daySixActivity"),
    @NamedQuery(name = "FantasyLeague.findByDaySevenActivity", query = "SELECT f FROM FantasyLeague f WHERE f.daySevenActivity = :daySevenActivity"),
    @NamedQuery(name = "FantasyLeague.findByLastSevenDaysActivity", query = "SELECT f FROM FantasyLeague f WHERE f.lastSevenDaysActivity = :lastSevenDaysActivity"),
    @NamedQuery(name = "FantasyLeague.findByLeagueType", query = "SELECT f FROM FantasyLeague f WHERE f.leagueType = :leagueType"),
    @NamedQuery(name = "FantasyLeague.findByLeagueStatus", query = "SELECT f FROM FantasyLeague f WHERE f.leagueStatus = :leagueStatus"),
    @NamedQuery(name = "FantasyLeague.findByInactive", query = "SELECT f FROM FantasyLeague f WHERE f.inactive = :inactive"),
    @NamedQuery(name = "FantasyLeague.findByWarnedAt", query = "SELECT f FROM FantasyLeague f WHERE f.warnedAt = :warnedAt"),
    @NamedQuery(name = "FantasyLeague.findByUpdatedForSeason", query = "SELECT f FROM FantasyLeague f WHERE f.updatedForSeason = :updatedForSeason")})
public class FantasyLeague implements Serializable {
    @OneToMany(mappedBy = "idFantasyLeague")
    private List<FantasyClubLineUp> fantasyClubLineUpList;
    @OneToMany(mappedBy = "idFantasyLeague")
    private List<Auction> auctionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 255)
    @Column(name = "logo")
    private String logo;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "options")
    private int options;
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numMembers")
    private int numMembers;
    @Size(max = 255)
    @Column(name = "activity")
    private String activity;
    @Size(max = 255)
    @Column(name = "leaguehash")
    private String leaguehash;
    @Column(name = "secondLeague")
    private Short secondLeague;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "dayOneActivity")
    private Integer dayOneActivity;
    @Column(name = "dayTwoActivity")
    private Integer dayTwoActivity;
    @Column(name = "dayThreeActivity")
    private Integer dayThreeActivity;
    @Column(name = "dayFourActivity")
    private Integer dayFourActivity;
    @Column(name = "dayFiveActivity")
    private Integer dayFiveActivity;
    @Column(name = "daySixActivity")
    private Integer daySixActivity;
    @Column(name = "daySevenActivity")
    private Integer daySevenActivity;
    @Column(name = "lastSevenDaysActivity")
    private Integer lastSevenDaysActivity;
    @Size(max = 255)
    @Column(name = "leagueType")
    private String leagueType;
    @Size(max = 45)
    @Column(name = "leagueStatus")
    private String leagueStatus;
    @Column(name = "inactive")
    private Short inactive;
    @Column(name = "warnedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date warnedAt;
    @Column(name = "updated_for_season")
    private Short updatedForSeason;
    @OneToMany(mappedBy = "idFantasyLeague")
    private List<FantasyClub> fantasyClubList;
    @OneToMany(mappedBy = "idFantasyLeague")
    private List<FantasyLeaguePlayer> fantasyLeaguePlayerList;
    @OneToMany(mappedBy = "idFantasyLeague")
    private List<News> newsList;

    public FantasyLeague() {
    }

    public FantasyLeague(Long id) {
        this.id = id;
    }

    public FantasyLeague(Long id, int options, int numMembers, Date createDate) {
        this.id = id;
        this.options = options;
        this.numMembers = numMembers;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLeaguehash() {
        return leaguehash;
    }

    public void setLeaguehash(String leaguehash) {
        this.leaguehash = leaguehash;
    }

    public Short getSecondLeague() {
        return secondLeague;
    }

    public void setSecondLeague(Short secondLeague) {
        this.secondLeague = secondLeague;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDayOneActivity() {
        return dayOneActivity;
    }

    public void setDayOneActivity(Integer dayOneActivity) {
        this.dayOneActivity = dayOneActivity;
    }

    public Integer getDayTwoActivity() {
        return dayTwoActivity;
    }

    public void setDayTwoActivity(Integer dayTwoActivity) {
        this.dayTwoActivity = dayTwoActivity;
    }

    public Integer getDayThreeActivity() {
        return dayThreeActivity;
    }

    public void setDayThreeActivity(Integer dayThreeActivity) {
        this.dayThreeActivity = dayThreeActivity;
    }

    public Integer getDayFourActivity() {
        return dayFourActivity;
    }

    public void setDayFourActivity(Integer dayFourActivity) {
        this.dayFourActivity = dayFourActivity;
    }

    public Integer getDayFiveActivity() {
        return dayFiveActivity;
    }

    public void setDayFiveActivity(Integer dayFiveActivity) {
        this.dayFiveActivity = dayFiveActivity;
    }

    public Integer getDaySixActivity() {
        return daySixActivity;
    }

    public void setDaySixActivity(Integer daySixActivity) {
        this.daySixActivity = daySixActivity;
    }

    public Integer getDaySevenActivity() {
        return daySevenActivity;
    }

    public void setDaySevenActivity(Integer daySevenActivity) {
        this.daySevenActivity = daySevenActivity;
    }

    public Integer getLastSevenDaysActivity() {
        return lastSevenDaysActivity;
    }

    public void setLastSevenDaysActivity(Integer lastSevenDaysActivity) {
        this.lastSevenDaysActivity = lastSevenDaysActivity;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }

    public String getLeagueStatus() {
        return leagueStatus;
    }

    public void setLeagueStatus(String leagueStatus) {
        this.leagueStatus = leagueStatus;
    }

    public Short getInactive() {
        return inactive;
    }

    public void setInactive(Short inactive) {
        this.inactive = inactive;
    }

    public Date getWarnedAt() {
        return warnedAt;
    }

    public void setWarnedAt(Date warnedAt) {
        this.warnedAt = warnedAt;
    }

    public Short getUpdatedForSeason() {
        return updatedForSeason;
    }

    public void setUpdatedForSeason(Short updatedForSeason) {
        this.updatedForSeason = updatedForSeason;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyClub> getFantasyClubList() {
        return fantasyClubList;
    }

    public void setFantasyClubList(List<FantasyClub> fantasyClubList) {
        this.fantasyClubList = fantasyClubList;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyLeaguePlayer> getFantasyLeaguePlayerList() {
        return fantasyLeaguePlayerList;
    }

    public void setFantasyLeaguePlayerList(List<FantasyLeaguePlayer> fantasyLeaguePlayerList) {
        this.fantasyLeaguePlayerList = fantasyLeaguePlayerList;
    }

    @XmlTransient
    @JsonIgnore
    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FantasyLeague)) {
            return false;
        }
        FantasyLeague other = (FantasyLeague) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }

    @XmlTransient
    @JsonIgnore
    public List<Auction> getAuctionList() {
        return auctionList;
    }

    public void setAuctionList(List<Auction> auctionList) {
        this.auctionList = auctionList;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyClubLineUp> getFantasyClubLineUpList() {
        return fantasyClubLineUpList;
    }

    public void setFantasyClubLineUpList(List<FantasyClubLineUp> fantasyClubLineUpList) {
        this.fantasyClubLineUpList = fantasyClubLineUpList;
    }
    
}
