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
 * @author marko
 */
@Entity
@Table(name = "PREMIUM_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PremiumHistory.findAll", query = "SELECT p FROM PremiumHistory p"),
    @NamedQuery(name = "PremiumHistory.findById", query = "SELECT p FROM PremiumHistory p WHERE p.id = :id"),
    @NamedQuery(name = "PremiumHistory.findByCreateDate", query = "SELECT p FROM PremiumHistory p WHERE p.createDate = :createDate"),
    @NamedQuery(name = "PremiumHistory.findByCharges", query = "SELECT p FROM PremiumHistory p WHERE p.charges = :charges"),
    @NamedQuery(name = "PremiumHistory.findByUpdatedCharges", query = "SELECT p FROM PremiumHistory p WHERE p.updatedCharges = :updatedCharges"),
    @NamedQuery(name = "PremiumHistory.findByPremiumCurrency", query = "SELECT p FROM PremiumHistory p WHERE p.premiumCurrency = :premiumCurrency"),
    @NamedQuery(name = "PremiumHistory.findByUpdatedPremiumCurrency", query = "SELECT p FROM PremiumHistory p WHERE p.updatedPremiumCurrency = :updatedPremiumCurrency")})
public class PremiumHistory implements Serializable {
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
    @Column(name = "charges")
    private Integer charges;
    @Column(name = "updatedCharges")
    private Integer updatedCharges;
    @Column(name = "premiumCurrency")
    private Integer premiumCurrency;
    @Column(name = "updatedPremiumCurrency")
    private Integer updatedPremiumCurrency;
    @JoinColumn(name = "ID_REWARD", referencedColumnName = "ID")
    @ManyToOne
    private Reward idReward;
    @JoinColumn(name = "ID_FANTASY_MANAGER", referencedColumnName = "ID")
    @ManyToOne
    private FantasyManager idFantasyManager;
    @JoinColumn(name = "ID_FANTASY_CLUB", referencedColumnName = "ID")
    @ManyToOne
    private FantasyClub idFantasyClub;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID")
    @ManyToOne
    private User idUser;
    @JoinColumn(name = "ID_PREMIUM_ACTION", referencedColumnName = "ID")
    @ManyToOne
    private PremiumAction idPremiumAction;
    @JoinColumn(name = "ID_PREMIUM_ITEM", referencedColumnName = "ID")
    @ManyToOne
    private PremiumItem idPremiumItem;

    public PremiumHistory() {
    }

    public PremiumHistory(Long id) {
        this.id = id;
    }

    public PremiumHistory(Long id, Date createDate) {
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

    public Integer getCharges() {
        return charges;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

    public Integer getUpdatedCharges() {
        return updatedCharges;
    }

    public void setUpdatedCharges(Integer updatedCharges) {
        this.updatedCharges = updatedCharges;
    }

    public Integer getPremiumCurrency() {
        return premiumCurrency;
    }

    public void setPremiumCurrency(Integer premiumCurrency) {
        this.premiumCurrency = premiumCurrency;
    }

    public Integer getUpdatedPremiumCurrency() {
        return updatedPremiumCurrency;
    }

    public void setUpdatedPremiumCurrency(Integer updatedPremiumCurrency) {
        this.updatedPremiumCurrency = updatedPremiumCurrency;
    }

    public Reward getIdReward() {
        return idReward;
    }

    public void setIdReward(Reward idReward) {
        this.idReward = idReward;
    }

    public FantasyManager getIdFantasyManager() {
        return idFantasyManager;
    }

    public void setIdFantasyManager(FantasyManager idFantasyManager) {
        this.idFantasyManager = idFantasyManager;
    }

    public FantasyClub getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(FantasyClub idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public PremiumAction getIdPremiumAction() {
        return idPremiumAction;
    }

    public void setIdPremiumAction(PremiumAction idPremiumAction) {
        this.idPremiumAction = idPremiumAction;
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
        if (!(object instanceof PremiumHistory)) {
            return false;
        }
        PremiumHistory other = (PremiumHistory) object;
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
