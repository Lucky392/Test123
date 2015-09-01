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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

/**
 *
 * @author marko
 */
@Entity
@Table(name = "FANTASY_CLUB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyClub.findAll", query = "SELECT f FROM FantasyClub f"),
    @NamedQuery(name = "FantasyClub.findById", query = "SELECT f FROM FantasyClub f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyClub.findByName", query = "SELECT f FROM FantasyClub f WHERE f.name = :name"),
    @NamedQuery(name = "FantasyClub.findByCredit", query = "SELECT f FROM FantasyClub f WHERE f.credit = :credit"),
    @NamedQuery(name = "FantasyClub.findByTotalBLMPoints", query = "SELECT f FROM FantasyClub f WHERE f.totalBLMPoints = :totalBLMPoints"),
    @NamedQuery(name = "FantasyClub.findByUpdateTimestamp", query = "SELECT f FROM FantasyClub f WHERE f.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "FantasyClub.findByChangeLineUpTimestamp", query = "SELECT f FROM FantasyClub f WHERE f.changeLineUpTimestamp = :changeLineUpTimestamp"),
    @NamedQuery(name = "FantasyClub.findByCreateDate", query = "SELECT f FROM FantasyClub f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FantasyClub.findByIsLeagueFounder", query = "SELECT f FROM FantasyClub f WHERE f.isLeagueFounder = :isLeagueFounder"),
    @NamedQuery(name = "FantasyClub.findByAmountSubstituteBench", query = "SELECT f FROM FantasyClub f WHERE f.amountSubstituteBench = :amountSubstituteBench"),
    @NamedQuery(name = "FantasyClub.findByAmountCoTrainer", query = "SELECT f FROM FantasyClub f WHERE f.amountCoTrainer = :amountCoTrainer"),
    @NamedQuery(name = "FantasyClub.findByLastLogin", query = "SELECT f FROM FantasyClub f WHERE f.lastLogin = :lastLogin"),
    @NamedQuery(name = "FantasyClub.findByFirstLogin", query = "SELECT f FROM FantasyClub f WHERE f.firstLogin = :firstLogin"),
    @NamedQuery(name = "FantasyClub.findBySportDirectorActiveTimestamp", query = "SELECT f FROM FantasyClub f WHERE f.sportDirectorActiveTimestamp = :sportDirectorActiveTimestamp"),
    @NamedQuery(name = "FantasyClub.findByActivity", query = "SELECT f FROM FantasyClub f WHERE f.activity = :activity"),
    @NamedQuery(name = "FantasyClub.findByLastQuestionAnswered", query = "SELECT f FROM FantasyClub f WHERE f.lastQuestionAnswered = :lastQuestionAnswered"),
    @NamedQuery(name = "FantasyClub.findByQuestionRightAnswered", query = "SELECT f FROM FantasyClub f WHERE f.questionRightAnswered = :questionRightAnswered"),
    @NamedQuery(name = "FantasyClub.findByLastLoginWith", query = "SELECT f FROM FantasyClub f WHERE f.lastLoginWith = :lastLoginWith"),
    @NamedQuery(name = "FantasyClub.findByAmountSubstituteBenchSlots", query = "SELECT f FROM FantasyClub f WHERE f.amountSubstituteBenchSlots = :amountSubstituteBenchSlots"),
    @NamedQuery(name = "FantasyClub.findByIsLineUpChangedByCoTrainer", query = "SELECT f FROM FantasyClub f WHERE f.isLineUpChangedByCoTrainer = :isLineUpChangedByCoTrainer")})
public class FantasyClub implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "credit")
    private Integer credit;
    @Column(name = "totalBLMPoints")
    private Integer totalBLMPoints;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Column(name = "changeLineUpTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeLineUpTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "isLeagueFounder")
    private Short isLeagueFounder;
    @Column(name = "amountSubstituteBench")
    private Integer amountSubstituteBench;
    @Column(name = "amountCoTrainer")
    private Integer amountCoTrainer;
    @Column(name = "lastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Column(name = "firstLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstLogin;
    @Column(name = "sportDirectorActiveTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sportDirectorActiveTimestamp;
    @Size(max = 255)
    @Column(name = "activity")
    private String activity;
    @Column(name = "lastQuestionAnswered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastQuestionAnswered;
    @Column(name = "questionRightAnswered")
    private Integer questionRightAnswered;
    @Size(max = 255)
    @Column(name = "lastLoginWith")
    private String lastLoginWith;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amountSubstituteBenchSlots")
    private int amountSubstituteBenchSlots;
    @Column(name = "isLineUpChangedByCoTrainer")
    private Short isLineUpChangedByCoTrainer;
    @JoinColumn(name = "ID_ACTIVE_FORMATION", referencedColumnName = "ID")
    @ManyToOne
    private Formation idActiveFormation;
    @JoinColumn(name = "ID_FANTASY_CLUB_LOGO", referencedColumnName = "ID")
    @ManyToOne
    private FantasyClubLogo idFantasyClubLogo;
    @JoinColumn(name = "ID_EMBLEM", referencedColumnName = "ID")
    @ManyToOne
    private Emblem idEmblem;
    @JoinColumn(name = "ID_CAPTAIN", referencedColumnName = "ID")
    @ManyToOne
    private FantasyPlayer idCaptain;
    @JoinColumn(name = "ID_FANTASY_MANAGER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyManager idFantasyManager;
    @JoinColumn(name = "ID_FANTASY_LEAGUE", referencedColumnName = "ID")
    @ManyToOne
    private FantasyLeague idFantasyLeague;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFantasyClub")
    private List<FantasyPlayer> fantasyPlayerList;
    @OneToMany(mappedBy = "idFantasyClub")
    private List<News> newsList;

    public FantasyClub() {
    }

    public FantasyClub(Long id) {
        this.id = id;
    }

    public FantasyClub(Long id, Date createDate, int amountSubstituteBenchSlots) {
        this.id = id;
        this.createDate = createDate;
        this.amountSubstituteBenchSlots = amountSubstituteBenchSlots;
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

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getTotalBLMPoints() {
        return totalBLMPoints;
    }

    public void setTotalBLMPoints(Integer totalBLMPoints) {
        this.totalBLMPoints = totalBLMPoints;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Date getChangeLineUpTimestamp() {
        return changeLineUpTimestamp;
    }

    public void setChangeLineUpTimestamp(Date changeLineUpTimestamp) {
        this.changeLineUpTimestamp = changeLineUpTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getIsLeagueFounder() {
        return isLeagueFounder;
    }

    public void setIsLeagueFounder(Short isLeagueFounder) {
        this.isLeagueFounder = isLeagueFounder;
    }

    public Integer getAmountSubstituteBench() {
        return amountSubstituteBench;
    }

    public void setAmountSubstituteBench(Integer amountSubstituteBench) {
        this.amountSubstituteBench = amountSubstituteBench;
    }

    public Integer getAmountCoTrainer() {
        return amountCoTrainer;
    }

    public void setAmountCoTrainer(Integer amountCoTrainer) {
        this.amountCoTrainer = amountCoTrainer;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Date firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Date getSportDirectorActiveTimestamp() {
        return sportDirectorActiveTimestamp;
    }

    public void setSportDirectorActiveTimestamp(Date sportDirectorActiveTimestamp) {
        this.sportDirectorActiveTimestamp = sportDirectorActiveTimestamp;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getLastQuestionAnswered() {
        return lastQuestionAnswered;
    }

    public void setLastQuestionAnswered(Date lastQuestionAnswered) {
        this.lastQuestionAnswered = lastQuestionAnswered;
    }

    public Integer getQuestionRightAnswered() {
        return questionRightAnswered;
    }

    public void setQuestionRightAnswered(Integer questionRightAnswered) {
        this.questionRightAnswered = questionRightAnswered;
    }

    public String getLastLoginWith() {
        return lastLoginWith;
    }

    public void setLastLoginWith(String lastLoginWith) {
        this.lastLoginWith = lastLoginWith;
    }

    public int getAmountSubstituteBenchSlots() {
        return amountSubstituteBenchSlots;
    }

    public void setAmountSubstituteBenchSlots(int amountSubstituteBenchSlots) {
        this.amountSubstituteBenchSlots = amountSubstituteBenchSlots;
    }

    public Short getIsLineUpChangedByCoTrainer() {
        return isLineUpChangedByCoTrainer;
    }

    public void setIsLineUpChangedByCoTrainer(Short isLineUpChangedByCoTrainer) {
        this.isLineUpChangedByCoTrainer = isLineUpChangedByCoTrainer;
    }

    public Formation getIdActiveFormation() {
        return idActiveFormation;
    }

    public void setIdActiveFormation(Formation idActiveFormation) {
        this.idActiveFormation = idActiveFormation;
    }

    public FantasyClubLogo getIdFantasyClubLogo() {
        return idFantasyClubLogo;
    }

    public void setIdFantasyClubLogo(FantasyClubLogo idFantasyClubLogo) {
        this.idFantasyClubLogo = idFantasyClubLogo;
    }

    public Emblem getIdEmblem() {
        return idEmblem;
    }

    public void setIdEmblem(Emblem idEmblem) {
        this.idEmblem = idEmblem;
    }

    public FantasyPlayer getIdCaptain() {
        return idCaptain;
    }

    public void setIdCaptain(FantasyPlayer idCaptain) {
        this.idCaptain = idCaptain;
    }

    public FantasyManager getIdFantasyManager() {
        return idFantasyManager;
    }

    public void setIdFantasyManager(FantasyManager idFantasyManager) {
        this.idFantasyManager = idFantasyManager;
    }

    public FantasyLeague getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(FantasyLeague idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
    }

    @XmlTransient
    public List<FantasyPlayer> getFantasyPlayerList() {
        return fantasyPlayerList;
    }

    public void setFantasyPlayerList(List<FantasyPlayer> fantasyPlayerList) {
        this.fantasyPlayerList = fantasyPlayerList;
    }

    @XmlTransient
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
        if (!(object instanceof FantasyClub)) {
            return false;
        }
        FantasyClub other = (FantasyClub) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }
    
}
