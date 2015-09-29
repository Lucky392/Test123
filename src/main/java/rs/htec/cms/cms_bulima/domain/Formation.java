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
@Table(name = "FORMATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formation.findAll", query = "SELECT f FROM Formation f"),
    @NamedQuery(name = "Formation.findById", query = "SELECT f FROM Formation f WHERE f.id = :id"),
    @NamedQuery(name = "Formation.findByName", query = "SELECT f FROM Formation f WHERE f.name = :name"),
    @NamedQuery(name = "Formation.findByPremium", query = "SELECT f FROM Formation f WHERE f.premium = :premium"),
    @NamedQuery(name = "Formation.findByCreateDate", query = "SELECT f FROM Formation f WHERE f.createDate = :createDate")})
public class Formation implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFormation")
    private List<FantasyClubLineUp> fantasyClubLineUpList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "premium")
    private short premium;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(mappedBy = "idActiveFormation")
    private List<FantasyClub> fantasyClubList;

    public Formation() {
    }

    public Formation(Long id) {
        this.id = id;
    }

    public Formation(Long id, short premium, Date createDate) {
        this.id = id;
        this.premium = premium;
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

    public short getPremium() {
        return premium;
    }

    public void setPremium(short premium) {
        this.premium = premium;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    @JsonIgnore
    public List<FantasyClub> getFantasyClubList() {
        return fantasyClubList;
    }

    public void setFantasyClubList(List<FantasyClub> fantasyClubList) {
        this.fantasyClubList = fantasyClubList;
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
        if (!(object instanceof Formation)) {
            return false;
        }
        Formation other = (Formation) object;
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
    public List<FantasyClubLineUp> getFantasyClubLineUpList() {
        return fantasyClubLineUpList;
    }

    public void setFantasyClubLineUpList(List<FantasyClubLineUp> fantasyClubLineUpList) {
        this.fantasyClubLineUpList = fantasyClubLineUpList;
    }
    
}
