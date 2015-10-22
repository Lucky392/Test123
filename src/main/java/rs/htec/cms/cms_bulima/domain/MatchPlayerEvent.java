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

/**
 *
 * @author marko
 */
@Entity
@Table(name = "MATCH_PLAYER_EVENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchPlayerEvent.findAll", query = "SELECT m FROM MatchPlayerEvent m"),
    @NamedQuery(name = "MatchPlayerEvent.findById", query = "SELECT m FROM MatchPlayerEvent m WHERE m.id = :id"),
    @NamedQuery(name = "MatchPlayerEvent.findByType", query = "SELECT m FROM MatchPlayerEvent m WHERE m.type = :type"),
    @NamedQuery(name = "MatchPlayerEvent.findByEventTimeMinute", query = "SELECT m FROM MatchPlayerEvent m WHERE m.eventTimeMinute = :eventTimeMinute"),
    @NamedQuery(name = "MatchPlayerEvent.findByUpdateAt", query = "SELECT m FROM MatchPlayerEvent m WHERE m.updateAt = :updateAt"),
    @NamedQuery(name = "MatchPlayerEvent.findByCreateDate", query = "SELECT m FROM MatchPlayerEvent m WHERE m.createDate = :createDate")})
public class MatchPlayerEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "type")
    private String type;
    @Column(name = "eventTimeMinute")
    private Integer eventTimeMinute;
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "ID_MATCH_PLAYER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MatchPlayer idMatchPlayer;

    public MatchPlayerEvent() {
    }

    public MatchPlayerEvent(Long id) {
        this.id = id;
    }

    public MatchPlayerEvent(Long id, Date createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEventTimeMinute() {
        return eventTimeMinute;
    }

    public void setEventTimeMinute(Integer eventTimeMinute) {
        this.eventTimeMinute = eventTimeMinute;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public MatchPlayer getIdMatchPlayer() {
        return idMatchPlayer;
    }

    public void setIdMatchPlayer(MatchPlayer idMatchPlayer) {
        this.idMatchPlayer = idMatchPlayer;
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
        if (!(object instanceof MatchPlayerEvent)) {
            return false;
        }
        MatchPlayerEvent other = (MatchPlayerEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchPlayerEvent[ id=" + id + " ]";
    }
    
}
