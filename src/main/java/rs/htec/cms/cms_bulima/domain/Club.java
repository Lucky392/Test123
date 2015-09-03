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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "CLUB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Club.findAll", query = "SELECT c FROM Club c"),
    @NamedQuery(name = "Club.findById", query = "SELECT c FROM Club c WHERE c.id = :id"),
    @NamedQuery(name = "Club.findByIdSport1Team", query = "SELECT c FROM Club c WHERE c.idSport1Team = :idSport1Team"),
    @NamedQuery(name = "Club.findByMediumName", query = "SELECT c FROM Club c WHERE c.mediumName = :mediumName"),
    @NamedQuery(name = "Club.findByShortName", query = "SELECT c FROM Club c WHERE c.shortName = :shortName"),
    @NamedQuery(name = "Club.findByLogoUrl", query = "SELECT c FROM Club c WHERE c.logoUrl = :logoUrl"),
    @NamedQuery(name = "Club.findByCreateDate", query = "SELECT c FROM Club c WHERE c.createDate = :createDate")})
public class Club implements Serializable {
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ID_SPORT1_TEAM")
    private String idSport1Team;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mediumName")
    private String mediumName;
    @Size(max = 255)
    @Column(name = "shortName")
    private String shortName;
    @Size(max = 255)
    @Column(name = "logoUrl")
    private String logoUrl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_LEAGUE", referencedColumnName = "ID")
    @ManyToOne
    private League idLeague;
    @OneToMany(mappedBy = "idClub")
    private List<Player> playerList;

    public Club() {
    }

    public Club(Long id) {
        this.id = id;
    }

    public Club(Long id, String idSport1Team, String mediumName, Date createDate) {
        this.id = id;
        this.idSport1Team = idSport1Team;
        this.mediumName = mediumName;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public League getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(League idLeague) {
        this.idLeague = idLeague;
    }

    @XmlTransient
    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
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
        if (!(object instanceof Club)) {
            return false;
        }
        Club other = (Club) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Club[ id=" + id + " ]";
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
    
}
