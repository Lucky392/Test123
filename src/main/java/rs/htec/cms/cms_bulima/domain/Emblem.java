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
import javax.persistence.Lob;
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
@Table(name = "EMBLEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emblem.findAll", query = "SELECT e FROM Emblem e"),
    @NamedQuery(name = "Emblem.findById", query = "SELECT e FROM Emblem e WHERE e.id = :id"),
    @NamedQuery(name = "Emblem.findByUrl", query = "SELECT e FROM Emblem e WHERE e.url = :url"),
    @NamedQuery(name = "Emblem.findByCreateDate", query = "SELECT e FROM Emblem e WHERE e.createDate = :createDate")})
public class Emblem implements Serializable {
    @Lob
    @Column(name = "emblem")
    private byte[] emblem;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(mappedBy = "idEmblem")
    private List<FantasyClub> fantasyClubList;

    public Emblem() {
    }

    public Emblem(Long id) {
        this.id = id;
    }

    public Emblem(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
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
        if (!(object instanceof Emblem)) {
            return false;
        }
        Emblem other = (Emblem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.Emblem[ id=" + id + " ]";
    }

    public byte[] getEmblem() {
        return emblem;
    }

    public void setEmblem(byte[] emblem) {
        this.emblem = emblem;
    }
    
}
