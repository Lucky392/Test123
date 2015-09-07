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
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "FANTASY_MANAGER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyManager.findAll", query = "SELECT f FROM FantasyManager f"),
    @NamedQuery(name = "FantasyManager.findById", query = "SELECT f FROM FantasyManager f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyManager.findByUsername", query = "SELECT f FROM FantasyManager f WHERE f.username = :username"),
    @NamedQuery(name = "FantasyManager.findByFirstname", query = "SELECT f FROM FantasyManager f WHERE f.firstname = :firstname"),
    @NamedQuery(name = "FantasyManager.findByLastname", query = "SELECT f FROM FantasyManager f WHERE f.lastname = :lastname"),
    @NamedQuery(name = "FantasyManager.findBySecondLeague", query = "SELECT f FROM FantasyManager f WHERE f.secondLeague = :secondLeague"),
    @NamedQuery(name = "FantasyManager.findByUpdateTimestamp", query = "SELECT f FROM FantasyManager f WHERE f.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "FantasyManager.findByCreateDate", query = "SELECT f FROM FantasyManager f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FantasyManager.findByProfilePhotoUrl", query = "SELECT f FROM FantasyManager f WHERE f.profilePhotoUrl = :profilePhotoUrl")})
public class FantasyManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Size(max = 255)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 255)
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "secondLeague")
    private Short secondLeague;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 255)
    @Column(name = "profilePhotoUrl")
    private String profilePhotoUrl;
    @JoinColumn(name = "ID_FAV_CLUB", referencedColumnName = "ID")
    @ManyToOne
    private FavoriteClub idFavClub;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User idUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFantasyManager")
    private List<FantasyClub> fantasyClubList;

    public FantasyManager() {
    }

    public FantasyManager(Long id) {
        this.id = id;
    }

    public FantasyManager(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Short getSecondLeague() {
        return secondLeague;
    }

    public void setSecondLeague(Short secondLeague) {
        this.secondLeague = secondLeague;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public FavoriteClub getIdFavClub() {
        return idFavClub;
    }

    public void setIdFavClub(FavoriteClub idFavClub) {
        this.idFavClub = idFavClub;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
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
        if (!(object instanceof FantasyManager)) {
            return false;
        }
        FantasyManager other = (FantasyManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FantasyManager[ id=" + id + " ]";
    }
    
}
