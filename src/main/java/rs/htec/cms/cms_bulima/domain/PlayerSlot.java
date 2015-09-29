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
@Table(name = "PLAYER_SLOT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerSlot.findAll", query = "SELECT p FROM PlayerSlot p"),
    @NamedQuery(name = "PlayerSlot.findById", query = "SELECT p FROM PlayerSlot p WHERE p.id = :id"),
    @NamedQuery(name = "PlayerSlot.findByPosition", query = "SELECT p FROM PlayerSlot p WHERE p.position = :position"),
    @NamedQuery(name = "PlayerSlot.findByName", query = "SELECT p FROM PlayerSlot p WHERE p.name = :name"),
    @NamedQuery(name = "PlayerSlot.findByCreateDate", query = "SELECT p FROM PlayerSlot p WHERE p.createDate = :createDate")})
public class PlayerSlot implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPlayerSlot")
    private List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayerList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "position")
    private int position;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @OneToMany(mappedBy = "idPlayerSlot")
    private List<FantasyPlayer> fantasyPlayerList;

    public PlayerSlot() {
    }

    public PlayerSlot(Long id) {
        this.id = id;
    }

    public PlayerSlot(Long id, int position, Date createDate) {
        this.id = id;
        this.position = position;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
    public List<FantasyPlayer> getFantasyPlayerList() {
        return fantasyPlayerList;
    }

    public void setFantasyPlayerList(List<FantasyPlayer> fantasyPlayerList) {
        this.fantasyPlayerList = fantasyPlayerList;
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
        if (!(object instanceof PlayerSlot)) {
            return false;
        }
        PlayerSlot other = (PlayerSlot) object;
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
    public List<FantasyClubLineUpPlayer> getFantasyClubLineUpPlayerList() {
        return fantasyClubLineUpPlayerList;
    }

    public void setFantasyClubLineUpPlayerList(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayerList) {
        this.fantasyClubLineUpPlayerList = fantasyClubLineUpPlayerList;
    }
    
}
