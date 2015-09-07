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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "FANTASY_CLUB_LOGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FantasyClubLogo.findAll", query = "SELECT f FROM FantasyClubLogo f"),
    @NamedQuery(name = "FantasyClubLogo.findById", query = "SELECT f FROM FantasyClubLogo f WHERE f.id = :id"),
    @NamedQuery(name = "FantasyClubLogo.findByShape", query = "SELECT f FROM FantasyClubLogo f WHERE f.shape = :shape"),
    @NamedQuery(name = "FantasyClubLogo.findByElement", query = "SELECT f FROM FantasyClubLogo f WHERE f.element = :element"),
    @NamedQuery(name = "FantasyClubLogo.findByPattern", query = "SELECT f FROM FantasyClubLogo f WHERE f.pattern = :pattern"),
    @NamedQuery(name = "FantasyClubLogo.findByColour", query = "SELECT f FROM FantasyClubLogo f WHERE f.colour = :colour"),
    @NamedQuery(name = "FantasyClubLogo.findByCreateDate", query = "SELECT f FROM FantasyClubLogo f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FantasyClubLogo.findByIdEmblem", query = "SELECT f FROM FantasyClubLogo f WHERE f.idEmblem = :idEmblem"),
    @NamedQuery(name = "FantasyClubLogo.findBySecondColour", query = "SELECT f FROM FantasyClubLogo f WHERE f.secondColour = :secondColour")})
public class FantasyClubLogo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "shape")
    private int shape;
    @Basic(optional = false)
    @NotNull
    @Column(name = "element")
    private int element;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pattern")
    private int pattern;
    @Basic(optional = false)
    @NotNull
    @Column(name = "colour")
    private int colour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "ID_EMBLEM")
    private Integer idEmblem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "second_colour")
    private int secondColour;
    @OneToMany(mappedBy = "idFantasyClubLogo")
    private List<FantasyClub> fantasyClubList;

    public FantasyClubLogo() {
    }

    public FantasyClubLogo(Long id) {
        this.id = id;
    }

    public FantasyClubLogo(Long id, int shape, int element, int pattern, int colour, Date createDate, int secondColour) {
        this.id = id;
        this.shape = shape;
        this.element = element;
        this.pattern = pattern;
        this.colour = colour;
        this.createDate = createDate;
        this.secondColour = secondColour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIdEmblem() {
        return idEmblem;
    }

    public void setIdEmblem(Integer idEmblem) {
        this.idEmblem = idEmblem;
    }

    public int getSecondColour() {
        return secondColour;
    }

    public void setSecondColour(int secondColour) {
        this.secondColour = secondColour;
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
        if (!(object instanceof FantasyClubLogo)) {
            return false;
        }
        FantasyClubLogo other = (FantasyClubLogo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.FantasyClubLogo[ id=" + id + " ]";
    }
    
}
