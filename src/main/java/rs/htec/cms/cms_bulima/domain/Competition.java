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

/**
 *
 * @author marko
 */
@Entity
@Table(name = "COMPETITION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competition.findAll", query = "SELECT c FROM Competition c"),
    @NamedQuery(name = "Competition.findById", query = "SELECT c FROM Competition c WHERE c.id = :id"),
    @NamedQuery(name = "Competition.findByName", query = "SELECT c FROM Competition c WHERE c.name = :name"),
    @NamedQuery(name = "Competition.findByType", query = "SELECT c FROM Competition c WHERE c.type = :type"),
    @NamedQuery(name = "Competition.findByCreateDate", query = "SELECT c FROM Competition c WHERE c.createDate = :createDate"),
    @NamedQuery(name = "Competition.findBySport", query = "SELECT c FROM Competition c WHERE c.sport = :sport"),
    @NamedQuery(name = "Competition.findByGender", query = "SELECT c FROM Competition c WHERE c.gender = :gender")})
public class Competition implements Serializable {
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
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "sport")
    private String sport;
    @Size(max = 6)
    @Column(name = "gender")
    private String gender;
    @OneToMany(mappedBy = "idCompetition")
    private List<SliderContent> sliderContentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompetition")
    private List<League> leagueList;

    public Competition() {
    }

    public Competition(Long id) {
        this.id = id;
    }

    public Competition(Long id, Date createDate) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @XmlTransient
    public List<SliderContent> getSliderContentList() {
        return sliderContentList;
    }

    public void setSliderContentList(List<SliderContent> sliderContentList) {
        this.sliderContentList = sliderContentList;
    }

    @XmlTransient
    public List<League> getLeagueList() {
        return leagueList;
    }

    public void setLeagueList(List<League> leagueList) {
        this.leagueList = leagueList;
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
        if (!(object instanceof Competition)) {
            return false;
        }
        Competition other = (Competition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Competition[ id=" + id + " ]";
    }
    
}
