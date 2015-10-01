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

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "USER_PREMIUM_ITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPremiumItem.findAll", query = "SELECT u FROM UserPremiumItem u"),
    @NamedQuery(name = "UserPremiumItem.findById", query = "SELECT u FROM UserPremiumItem u WHERE u.id = :id"),
    @NamedQuery(name = "UserPremiumItem.findByStartDate", query = "SELECT u FROM UserPremiumItem u WHERE u.startDate = :startDate"),
    @NamedQuery(name = "UserPremiumItem.findByEndDate", query = "SELECT u FROM UserPremiumItem u WHERE u.endDate = :endDate"),
    @NamedQuery(name = "UserPremiumItem.findByCharges", query = "SELECT u FROM UserPremiumItem u WHERE u.charges = :charges"),
    @NamedQuery(name = "UserPremiumItem.findByUpdateTimestamp", query = "SELECT u FROM UserPremiumItem u WHERE u.updateTimestamp = :updateTimestamp"),
    @NamedQuery(name = "UserPremiumItem.findByCreateDate", query = "SELECT u FROM UserPremiumItem u WHERE u.createDate = :createDate")})
public class UserPremiumItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "charges")
    private Integer charges;
    @Column(name = "updateTimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User idUser;
    @JoinColumn(name = "ID_PREMIUM_ITEM", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PremiumItem idPremiumItem;

    public UserPremiumItem() {
    }

    public UserPremiumItem(Long id) {
        this.id = id;
    }

    public UserPremiumItem(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
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

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public PremiumItem getIdPremiumItem() {
        return idPremiumItem;
    }

    public void setIdPremiumItem(PremiumItem idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
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
        if (!(object instanceof UserPremiumItem)) {
            return false;
        }
        UserPremiumItem other = (UserPremiumItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.UserPremiumItem[ id=" + id + " ]";
    }
    
}
