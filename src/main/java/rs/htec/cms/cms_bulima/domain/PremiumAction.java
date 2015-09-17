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
 * @author marko
 */
@Entity
@Table(name = "PREMIUM_ACTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumAction.findAll", query = "SELECT p FROM PremiumAction p"),
    @NamedQuery(name = "PremiumAction.findById", query = "SELECT p FROM PremiumAction p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumAction.findByName", query = "SELECT p FROM PremiumAction p WHERE p.name = :name"),
    @NamedQuery(name = "PremiumAction.findByCreateDate", query = "SELECT p FROM PremiumAction p WHERE p.createDate = :createDate")})
public class PremiumAction implements Serializable {
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
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(mappedBy = "idPremiumAction")
    private List<PremiumHistory> premiumHistoryList;

    public PremiumAction() {
    }

    public PremiumAction(Long id) {
        this.id = id;
    }

    public PremiumAction(Long id, Date createDate) {
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    @JsonIgnore
    public List<PremiumHistory> getPremiumHistoryList() {
        return premiumHistoryList;
    }

    public void setPremiumHistoryList(List<PremiumHistory> premiumHistoryList) {
        this.premiumHistoryList = premiumHistoryList;
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
        if (!(object instanceof PremiumAction)) {
            return false;
        }
        PremiumAction other = (PremiumAction) object;
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
