/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "MATCHDAY_CHALLENGE_RESULT_TABLE_ELEMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatchdayChallengeResultTableElement.findAll", query = "SELECT m FROM MatchdayChallengeResultTableElement m"),
    @NamedQuery(name = "MatchdayChallengeResultTableElement.findById", query = "SELECT m FROM MatchdayChallengeResultTableElement m WHERE m.id = :id"),
    @NamedQuery(name = "MatchdayChallengeResultTableElement.findByElementPosition", query = "SELECT m FROM MatchdayChallengeResultTableElement m WHERE m.elementPosition = :elementPosition"),
    @NamedQuery(name = "MatchdayChallengeResultTableElement.findByElementName", query = "SELECT m FROM MatchdayChallengeResultTableElement m WHERE m.elementName = :elementName"),
    @NamedQuery(name = "MatchdayChallengeResultTableElement.findByElementType", query = "SELECT m FROM MatchdayChallengeResultTableElement m WHERE m.elementType = :elementType"),
    @NamedQuery(name = "MatchdayChallengeResultTableElement.findByElementTable", query = "SELECT m FROM MatchdayChallengeResultTableElement m WHERE m.elementTable = :elementTable"),
    @NamedQuery(name = "MatchdayChallengeResultTableElement.findByElementField", query = "SELECT m FROM MatchdayChallengeResultTableElement m WHERE m.elementField = :elementField")})
public class MatchdayChallengeResultTableElement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "elementPosition")
    private int elementPosition;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "elementName")
    private String elementName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "elementType")
    private String elementType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "elementTable")
    private String elementTable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "elementField")
    private String elementField;
    @JoinColumn(name = "ID_MATCHDAY_CHALLENGE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MatchdayChallenge idMatchdayChallenge;

    public MatchdayChallengeResultTableElement() {
    }

    public MatchdayChallengeResultTableElement(Long id) {
        this.id = id;
    }

    public MatchdayChallengeResultTableElement(Long id, int elementPosition, String elementName, String elementType, String elementTable, String elementField) {
        this.id = id;
        this.elementPosition = elementPosition;
        this.elementName = elementName;
        this.elementType = elementType;
        this.elementTable = elementTable;
        this.elementField = elementField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getElementPosition() {
        return elementPosition;
    }

    public void setElementPosition(int elementPosition) {
        this.elementPosition = elementPosition;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementTable() {
        return elementTable;
    }

    public void setElementTable(String elementTable) {
        this.elementTable = elementTable;
    }

    public String getElementField() {
        return elementField;
    }

    public void setElementField(String elementField) {
        this.elementField = elementField;
    }

    public MatchdayChallenge getIdMatchdayChallenge() {
        return idMatchdayChallenge;
    }

    public void setIdMatchdayChallenge(MatchdayChallenge idMatchdayChallenge) {
        this.idMatchdayChallenge = idMatchdayChallenge;
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
        if (!(object instanceof MatchdayChallengeResultTableElement)) {
            return false;
        }
        MatchdayChallengeResultTableElement other = (MatchdayChallengeResultTableElement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.MatchdayChallengeResultTableElement[ id=" + id + " ]";
    }
    
}
