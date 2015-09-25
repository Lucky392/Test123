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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author stefan
 */
@Entity
@Table(name = "FANTASY_CLUB_CREDIT_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyClubCreditHistory.findAll", query = "SELECT f FROM FantasyClubCreditHistory f"),
    @NamedQuery(name = "FantasyClubCreditHistory.findById", query = "SELECT f FROM FantasyClubCreditHistory f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyClubCreditHistory.findByCredit", query = "SELECT f FROM FantasyClubCreditHistory f WHERE f.credit = :credit"),
    @NamedQuery(name = "FantasyClubCreditHistory.findByAction", query = "SELECT f FROM FantasyClubCreditHistory f WHERE f.action = :action"),
    @NamedQuery(name = "FantasyClubCreditHistory.findByCreateDate", query = "SELECT f FROM FantasyClubCreditHistory f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FantasyClubCreditHistory.findByUpdatedCredit", query = "SELECT f FROM FantasyClubCreditHistory f WHERE f.updatedCredit = :updatedCredit")})
public class FantasyClubCreditHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "credit")
    private int credit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "action")
    private String action;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updatedCredit")
    private int updatedCredit;
    @JoinColumn(name = "ID_AUCTION", referencedColumnName = "ID")
    @ManyToOne
    private Auction idAuction;
    @JoinColumn(name = "ID_FANTASY_CLUB", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FantasyClub idFantasyClub;

    public FantasyClubCreditHistory() {
    }

    public FantasyClubCreditHistory(Long id) {
        this.id = id;
    }

    public FantasyClubCreditHistory(Long id, int credit, String action, Date createDate, int updatedCredit) {
        this.id = id;
        this.credit = credit;
        this.action = action;
        this.createDate = createDate;
        this.updatedCredit = updatedCredit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getUpdatedCredit() {
        return updatedCredit;
    }

    public void setUpdatedCredit(int updatedCredit) {
        this.updatedCredit = updatedCredit;
    }

    @XmlTransient
    @JsonIgnore
    public Auction getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(Auction idAuction) {
        this.idAuction = idAuction;
    }

    @XmlTransient
    @JsonIgnore
    public FantasyClub getIdFantasyClub() {
        return idFantasyClub;
    }

    public void setIdFantasyClub(FantasyClub idFantasyClub) {
        this.idFantasyClub = idFantasyClub;
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
        if (!(object instanceof FantasyClubCreditHistory)) {
            return false;
        }
        FantasyClubCreditHistory other = (FantasyClubCreditHistory) object;
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
