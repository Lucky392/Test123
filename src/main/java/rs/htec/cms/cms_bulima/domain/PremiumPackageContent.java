/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "PREMIUM_PACKAGE_CONTENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumPackageContent.findAll", query = "SELECT p FROM PremiumPackageContent p"),
    @NamedQuery(name = "PremiumPackageContent.findById", query = "SELECT p FROM PremiumPackageContent p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumPackageContent.findByAmount", query = "SELECT p FROM PremiumPackageContent p WHERE p.amount = :amount"),
    @NamedQuery(name = "PremiumPackageContent.findByUpdateTimestamp", query = "SELECT p FROM PremiumPackageContent p WHERE p.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "PremiumPackageContent.findByCreateDate", query = "SELECT p FROM PremiumPackageContent p WHERE p.createDate = :createDate")})
public class PremiumPackageContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_PREMIUM_ITEM", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PremiumItem idPremiumItem;
    @JoinColumn(name = "ID_PREMIUM_PACKAGE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PremiumPackage idPremiumPackage;

    public PremiumPackageContent() {
    }

    public PremiumPackageContent(Long id) {
        this.id = id;
    }

    public PremiumPackageContent(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public PremiumItem getIdPremiumItem() {
        return idPremiumItem;
    }

    public void setIdPremiumItem(PremiumItem idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
    }

    public PremiumPackage getIdPremiumPackage() {
        return idPremiumPackage;
    }

    public void setIdPremiumPackage(PremiumPackage idPremiumPackage) {
        this.idPremiumPackage = idPremiumPackage;
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
        if (!(object instanceof PremiumPackageContent)) {
            return false;
        }
        PremiumPackageContent other = (PremiumPackageContent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.PremiumPackageContent[ id=" + id + " ]";
    }
    
}
