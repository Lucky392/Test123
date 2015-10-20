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
import javax.persistence.OneToOne;
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
@Table(name = "MATCHDAY_CHALLENGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchdayChallenge.findAll", query = "SELECT m FROM MatchdayChallenge m"),
    @NamedQuery(name = "MatchdayChallenge.findById", query = "SELECT m FROM MatchdayChallenge m WHERE m.id = :id"),
    @NamedQuery(name = "MatchdayChallenge.findByMatchdayChallengeTitle", query = "SELECT m FROM MatchdayChallenge m WHERE m.matchdayChallengeTitle = :matchdayChallengeTitle"),
    @NamedQuery(name = "MatchdayChallenge.findByMatchdayChallengeDescription", query = "SELECT m FROM MatchdayChallenge m WHERE m.matchdayChallengeDescription = :matchdayChallengeDescription"),
    @NamedQuery(name = "MatchdayChallenge.findByMatchdayChallengeType", query = "SELECT m FROM MatchdayChallenge m WHERE m.matchdayChallengeType = :matchdayChallengeType"),
    @NamedQuery(name = "MatchdayChallenge.findByCost", query = "SELECT m FROM MatchdayChallenge m WHERE m.cost = :cost"),
    @NamedQuery(name = "MatchdayChallenge.findByLogo", query = "SELECT m FROM MatchdayChallenge m WHERE m.logo = :logo"),
    @NamedQuery(name = "MatchdayChallenge.findByFormation", query = "SELECT m FROM MatchdayChallenge m WHERE m.formation = :formation"),
    @NamedQuery(name = "MatchdayChallenge.findByIsPublished", query = "SELECT m FROM MatchdayChallenge m WHERE m.isPublished = :isPublished"),
    @NamedQuery(name = "MatchdayChallenge.findByCreateDate", query = "SELECT m FROM MatchdayChallenge m WHERE m.createDate = :createDate"),
    @NamedQuery(name = "MatchdayChallenge.findByTarget", query = "SELECT m FROM MatchdayChallenge m WHERE m.target = :target"),
    @NamedQuery(name = "MatchdayChallenge.findBySubheadline", query = "SELECT m FROM MatchdayChallenge m WHERE m.subheadline = :subheadline"),
    @NamedQuery(name = "MatchdayChallenge.findBySquad", query = "SELECT m FROM MatchdayChallenge m WHERE m.squad = :squad")})
public class MatchdayChallenge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "matchdayChallengeTitle")
    private String matchdayChallengeTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "matchdayChallengeDescription")
    private String matchdayChallengeDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "matchdayChallengeType")
    private String matchdayChallengeType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost")
    private int cost;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "logo")
    private String logo;
    @Size(max = 255)
    @Column(name = "formation")
    private String formation;
    @Column(name = "isPublished")
    private Short isPublished;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 225)
    @Column(name = "target")
    private String target;
    @Size(max = 225)
    @Column(name = "subheadline")
    private String subheadline;
    @Size(max = 225)
    @Column(name = "squad")
    private String squad;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallenge")
    private MatchdayChallengeTargetCalculation matchdayChallengeTargetCalculation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallenge")
    private List<MatchdayChallengePlayer> matchdayChallengePlayerList;
    @JoinColumn(name = "ID_REWARD", referencedColumnName = "ID")
    @ManyToOne
    private Reward idReward;
    @JoinColumn(name = "ID_MATCHDAY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Matchday idMatchday;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallenge")
    private List<FantasyManagerMatchdayChallengeLineUp> fantasyManagerMatchdayChallengeLineUpList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallenge")
    private List<MatchdayChallengeResultTableElement> matchdayChallengeResultTableElementList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idMatchdayChallenge")
    private MatchdayChallengeScoreCalculation matchdayChallengeScoreCalculation;

    public MatchdayChallenge() {
    }

    public MatchdayChallenge(Long id) {
        this.id = id;
    }

    public MatchdayChallenge(Long id, String matchdayChallengeTitle, String matchdayChallengeDescription, String matchdayChallengeType, int cost, String logo, Date createDate) {
        this.id = id;
        this.matchdayChallengeTitle = matchdayChallengeTitle;
        this.matchdayChallengeDescription = matchdayChallengeDescription;
        this.matchdayChallengeType = matchdayChallengeType;
        this.cost = cost;
        this.logo = logo;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchdayChallengeTitle() {
        return matchdayChallengeTitle;
    }

    public void setMatchdayChallengeTitle(String matchdayChallengeTitle) {
        this.matchdayChallengeTitle = matchdayChallengeTitle;
    }

    public String getMatchdayChallengeDescription() {
        return matchdayChallengeDescription;
    }

    public void setMatchdayChallengeDescription(String matchdayChallengeDescription) {
        this.matchdayChallengeDescription = matchdayChallengeDescription;
    }

    public String getMatchdayChallengeType() {
        return matchdayChallengeType;
    }

    public void setMatchdayChallengeType(String matchdayChallengeType) {
        this.matchdayChallengeType = matchdayChallengeType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public Short getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Short isPublished) {
        this.isPublished = isPublished;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSubheadline() {
        return subheadline;
    }

    public void setSubheadline(String subheadline) {
        this.subheadline = subheadline;
    }

    public String getSquad() {
        return squad;
    }

    public void setSquad(String squad) {
        this.squad = squad;
    }

    public MatchdayChallengeTargetCalculation getMatchdayChallengeTargetCalculation() {
        return matchdayChallengeTargetCalculation;
    }

    public void setMatchdayChallengeTargetCalculation(MatchdayChallengeTargetCalculation matchdayChallengeTargetCalculation) {
        this.matchdayChallengeTargetCalculation = matchdayChallengeTargetCalculation;
    }

    @XmlTransient
    @JsonIgnore
    public List<MatchdayChallengePlayer> getMatchdayChallengePlayerList() {
        return matchdayChallengePlayerList;
    }

    public void setMatchdayChallengePlayerList(List<MatchdayChallengePlayer> matchdayChallengePlayerList) {
        this.matchdayChallengePlayerList = matchdayChallengePlayerList;
    }

    public Reward getIdReward() {
        return idReward;
    }

    public void setIdReward(Reward idReward) {
        this.idReward = idReward;
    }

    public Matchday getIdMatchday() {
        return idMatchday;
    }

    public void setIdMatchday(Matchday idMatchday) {
        this.idMatchday = idMatchday;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyManagerMatchdayChallengeLineUp> getFantasyManagerMatchdayChallengeLineUpList() {
        return fantasyManagerMatchdayChallengeLineUpList;
    }

    public void setFantasyManagerMatchdayChallengeLineUpList(List<FantasyManagerMatchdayChallengeLineUp> fantasyManagerMatchdayChallengeLineUpList) {
        this.fantasyManagerMatchdayChallengeLineUpList = fantasyManagerMatchdayChallengeLineUpList;
    }

    @XmlTransient
    @JsonIgnore
    public List<MatchdayChallengeResultTableElement> getMatchdayChallengeResultTableElementList() {
        return matchdayChallengeResultTableElementList;
    }

    public void setMatchdayChallengeResultTableElementList(List<MatchdayChallengeResultTableElement> matchdayChallengeResultTableElementList) {
        this.matchdayChallengeResultTableElementList = matchdayChallengeResultTableElementList;
    }

    public MatchdayChallengeScoreCalculation getMatchdayChallengeScoreCalculation() {
        return matchdayChallengeScoreCalculation;
    }

    public void setMatchdayChallengeScoreCalculation(MatchdayChallengeScoreCalculation matchdayChallengeScoreCalculation) {
        this.matchdayChallengeScoreCalculation = matchdayChallengeScoreCalculation;
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
        if (!(object instanceof MatchdayChallenge)) {
            return false;
        }
        MatchdayChallenge other = (MatchdayChallenge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchdayChallenge[ id=" + id + " ]";
    }
    
}
