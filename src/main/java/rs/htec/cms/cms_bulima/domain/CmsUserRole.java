/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author stefan
 */
@Entity
@Table(name = "CMS_USER_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmsUserRole.findAll", query = "SELECT c FROM CmsUserRole c"),
    @NamedQuery(name = "CmsUserRole.findById", query = "SELECT c FROM CmsUserRole c WHERE c.id = :id"),
    @NamedQuery(name = "CmsUserRole.findByRoleName", query = "SELECT c FROM CmsUserRole c WHERE c.roleName = :roleName")})
public class CmsUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ROLE_NAME")
    private String roleName;
    @OneToMany(mappedBy = "idRole", fetch = FetchType.LAZY)
    private Collection<CmsUser> cmsUserCollection;

    public CmsUserRole() {
    }

    public CmsUserRole(Long id) {
        this.id = id;
    }

    public CmsUserRole(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<CmsUser> getCmsUserCollection() {
        return cmsUserCollection;
    }

    public void setCmsUserCollection(Collection<CmsUser> cmsUserCollection) {
        this.cmsUserCollection = cmsUserCollection;
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
        if (!(object instanceof CmsUserRole)) {
            return false;
        }
        CmsUserRole other = (CmsUserRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.CmsUserRole[ id=" + id + " ]";
    }
    
}
