/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
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
@Table(name = "CMS_TABLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmsTables.findAll", query = "SELECT c FROM CmsTables c"),
    @NamedQuery(name = "CmsTables.findById", query = "SELECT c FROM CmsTables c WHERE c.id = :id"),
    @NamedQuery(name = "CmsTables.findByTableName", query = "SELECT c FROM CmsTables c WHERE c.tableName = :tableName")})
public class CmsTables implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "table_name")
    private String tableName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsTables")
    private List<CmsUserPrivileges> cmsUserPrivilegesList;

    public CmsTables() {
    }

    public CmsTables(Long id) {
        this.id = id;
    }

    public CmsTables(Long id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlTransient
    @JsonIgnore
    public List<CmsUserPrivileges> getCmsUserPrivilegesList() {
        return cmsUserPrivilegesList;
    }

    public void setCmsUserPrivilegesList(List<CmsUserPrivileges> cmsUserPrivilegesList) {
        this.cmsUserPrivilegesList = cmsUserPrivilegesList;
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
        if (!(object instanceof CmsTables)) {
            return false;
        }
        CmsTables other = (CmsTables) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.CmsTables[ id=" + id + " ]";
    }
    
}
