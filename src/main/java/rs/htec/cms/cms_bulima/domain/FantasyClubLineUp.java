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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "FANTASY_CLUB_LINE_UP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyClubLineUp.findAll", query = "SELECT f FROM FantasyClubLineUp f"),
    @NamedQuery(name = "FantasyClubLineUp.findById", query = "SELECT f FROM FantasyClubLineUp f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyClubLineUp.findByCreateDate", query = "SELECT f FROM FantasyClubLineUp f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FantasyClubLineUp.findByMarketValue", query = "SELECT f FROM FantasyClubLineUp f WHERE f.marketValue = :marketValue"),
    @NamedQuery(name = "FantasyClubLineUp.findByCredit", query = "SELECT f FROM FantasyClubLineUp f WHERE f.credit = :credit")})
public class FantasyClubLineUp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "marketValue")
    private Integer marketValue;
    @Column(name = "credit")
    private Integer credit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLineUp")
    private List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayerList;
    @JoinColumn(name = "ID_MATCHDAY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Matchday idMatchday;
    @JoinColumn(name = "ID_FANTASY_CLUB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyClub idFantasyClub;
    @JoinColumn(name = "ID_FORMATION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Formation idFormation;
    @JoinColumn(name = "ID_FANTASY_LEAGUE", referencedColumnName = "ID")
    @ManyToOne
    private FantasyLeague idFantasyLeague;

    public FantasyClubLineUp() {
    }

    public FantasyClubLineUp(Long id) {
        this.id = id;
    }

    public FantasyClubLineUp(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
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

    public List<FantasyClubLineUpPlayer> getFantasyClubLineUpPlayerList() {
        return fantasyClubLineUpPlayerList;
    }

    public void setFantasyClubLineUpPlayerList(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayerList) {
        this.fantasyClubLineUpPlayerList = fantasyClubLineUpPlayerList;
    }

    public Matchday getIdMatchday() {
        return idMatchday;
    }

    public void setIdMatchday(Matchday idMatchday) {
        this.idMatchday = idMatchday;
    }

    public FantasyClub getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(FantasyClub idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    public Formation getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(Formation idFormation) {
        this.idFormation = idFormation;
    }

    public FantasyLeague getIdFantasyLeague() {
        return idFantasyLeague;
    }

    public void setIdFantasyLeague(FantasyLeague idFantasyLeague) {
        this.idFantasyLeague = idFantasyLeague;
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
        if (!(object instanceof FantasyClubLineUp)) {
            return false;
        }
        FantasyClubLineUp other = (FantasyClubLineUp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id+"";
    }
    
}
