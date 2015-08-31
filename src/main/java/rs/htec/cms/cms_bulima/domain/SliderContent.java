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
@Table(name = "SLIDER_CONTENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SliderContent.findAll", query = "SELECT s FROM SliderContent s"),
    @NamedQuery(name = "SliderContent.findById", query = "SELECT s FROM SliderContent s WHERE s.id = :id"),
    @NamedQuery(name = "SliderContent.findByContentUrl", query = "SELECT s FROM SliderContent s WHERE s.contentUrl = :contentUrl"),
    @NamedQuery(name = "SliderContent.findByRedirectUrl", query = "SELECT s FROM SliderContent s WHERE s.redirectUrl = :redirectUrl"),
    @NamedQuery(name = "SliderContent.findByPositionInSlider", query = "SELECT s FROM SliderContent s WHERE s.positionInSlider = :positionInSlider"),
    @NamedQuery(name = "SliderContent.findByShowForMsec", query = "SELECT s FROM SliderContent s WHERE s.showForMsec = :showForMsec"),
    @NamedQuery(name = "SliderContent.findByStartShowingAt", query = "SELECT s FROM SliderContent s WHERE s.startShowingAt = :startShowingAt"),
    @NamedQuery(name = "SliderContent.findByStopShowingAt", query = "SELECT s FROM SliderContent s WHERE s.stopShowingAt = :stopShowingAt"),
    @NamedQuery(name = "SliderContent.findByUpdateAt", query = "SELECT s FROM SliderContent s WHERE s.updateAt = :updateAt"),
    @NamedQuery(name = "SliderContent.findByCreateDate", query = "SELECT s FROM SliderContent s WHERE s.createDate = :createDate"),
    @NamedQuery(name = "SliderContent.findByText", query = "SELECT s FROM SliderContent s WHERE s.text = :text")})
public class SliderContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "contentUrl")
    private String contentUrl;
    @Size(max = 255)
    @Column(name = "redirectUrl")
    private String redirectUrl;
    @Column(name = "positionInSlider")
    private Integer positionInSlider;
    @Column(name = "showForMsec")
    private Integer showForMsec;
    @Column(name = "startShowingAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startShowingAt;
    @Column(name = "stopShowingAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopShowingAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updateAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 1023)
    @Column(name = "text")
    private String text;
    @JoinColumn(name = "ID_COMPETITION", referencedColumnName = "ID")
    @ManyToOne
    private Competition idCompetition;

    public SliderContent() {
    }

    public SliderContent(Long id) {
        this.id = id;
    }

    public SliderContent(Long id, Date updateAt, Date createDate) {
        this.id = id;
        this.updateAt = updateAt;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getPositionInSlider() {
        return positionInSlider;
    }

    public void setPositionInSlider(Integer positionInSlider) {
        this.positionInSlider = positionInSlider;
    }

    public Integer getShowForMsec() {
        return showForMsec;
    }

    public void setShowForMsec(Integer showForMsec) {
        this.showForMsec = showForMsec;
    }

    public Date getStartShowingAt() {
        return startShowingAt;
    }

    public void setStartShowingAt(Date startShowingAt) {
        this.startShowingAt = startShowingAt;
    }

    public Date getStopShowingAt() {
        return stopShowingAt;
    }

    public void setStopShowingAt(Date stopShowingAt) {
        this.stopShowingAt = stopShowingAt;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Competition getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(Competition idCompetition) {
        this.idCompetition = idCompetition;
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
        if (!(object instanceof SliderContent)) {
            return false;
        }
        SliderContent other = (SliderContent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.SliderContent[ id=" + id + " ]";
    }
    
}
