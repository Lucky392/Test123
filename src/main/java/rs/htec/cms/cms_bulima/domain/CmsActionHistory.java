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
import javax.persistence.Lob;
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
@Table(name = "CMS_ACTION_HISTORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmsActionHistory.findAll", query = "SELECT c FROM CmsActionHistory c"),
    @NamedQuery(name = "CmsActionHistory.findById", query = "SELECT c FROM CmsActionHistory c WHERE c.id = :id"),
    @NamedQuery(name = "CmsActionHistory.findByMethodType", query = "SELECT c FROM CmsActionHistory c WHERE c.methodType = :methodType"),
    @NamedQuery(name = "CmsActionHistory.findByRequestUrl", query = "SELECT c FROM CmsActionHistory c WHERE c.requestUrl = :requestUrl"),
    @NamedQuery(name = "CmsActionHistory.findByCreateDate", query = "SELECT c FROM CmsActionHistory c WHERE c.createDate = :createDate"),
    @NamedQuery(name = "CmsActionHistory.findByResponseCode", query = "SELECT c FROM CmsActionHistory c WHERE c.responseCode = :responseCode")})
public class CmsActionHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "methodType")
    private String methodType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "requestUrl")
    private String requestUrl;
    @Lob
    @Size(max = 65535)
    @Column(name = "bodyContent")
    private String bodyContent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "responseCode")
    private int responseCode;
    @Lob
    @Size(max = 65535)
    @Column(name = "responseEntity")
    private String responseEntity;
    @JoinColumn(name = "ID_CMS_USER", referencedColumnName = "id")
    @ManyToOne
    private CmsUser idCmsUser;

    public CmsActionHistory() {
    }

    public CmsActionHistory(Long id) {
        this.id = id;
    }

    public CmsActionHistory(Long id, String methodType, String requestUrl, Date createDate, int responseCode) {
        this.id = id;
        this.methodType = methodType;
        this.requestUrl = requestUrl;
        this.createDate = createDate;
        this.responseCode = responseCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(String responseEntity) {
        this.responseEntity = responseEntity;
    }

    public CmsUser getIdCmsUser() {
        return idCmsUser;
    }

    public void setIdCmsUser(CmsUser idCmsUser) {
        this.idCmsUser = idCmsUser;
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
        if (!(object instanceof CmsActionHistory)) {
            return false;
        }
        CmsActionHistory other = (CmsActionHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.CmsActionHistory[ id=" + id + " ]";
    }
    
}
