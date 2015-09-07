/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "CMS_USER_PRIVILEGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmsUserPrivileges.findAll", query = "SELECT c FROM CmsUserPrivileges c"),
    @NamedQuery(name = "CmsUserPrivileges.findByRoleId", query = "SELECT c FROM CmsUserPrivileges c WHERE c.cmsUserPrivilegesPK.roleId = :roleId"),
    @NamedQuery(name = "CmsUserPrivileges.findByTableId", query = "SELECT c FROM CmsUserPrivileges c WHERE c.cmsUserPrivilegesPK.tableId = :tableId"),
    @NamedQuery(name = "CmsUserPrivileges.findBySearch", query = "SELECT c FROM CmsUserPrivileges c WHERE c.search = :search"),
    @NamedQuery(name = "CmsUserPrivileges.findByEdit", query = "SELECT c FROM CmsUserPrivileges c WHERE c.edit = :edit"),
    @NamedQuery(name = "CmsUserPrivileges.findByAdd", query = "SELECT c FROM CmsUserPrivileges c WHERE c.add = :add"),
    @NamedQuery(name = "CmsUserPrivileges.findByRemove", query = "SELECT c FROM CmsUserPrivileges c WHERE c.remove = :remove")})
public class CmsUserPrivileges implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CmsUserPrivilegesPK cmsUserPrivilegesPK;
    @Column(name = "search")
    private Boolean search;
    @Column(name = "edit")
    private Boolean edit;
    @Column(name = "add")
    private Boolean add;
    @Column(name = "remove")
    private Boolean remove;
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CmsRole cmsRole;
    @JoinColumn(name = "table_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CmsTables cmsTables;

    public CmsUserPrivileges() {
    }

    public CmsUserPrivileges(CmsUserPrivilegesPK cmsUserPrivilegesPK) {
        this.cmsUserPrivilegesPK = cmsUserPrivilegesPK;
    }

    public CmsUserPrivileges(long roleId, long tableId) {
        this.cmsUserPrivilegesPK = new CmsUserPrivilegesPK(roleId, tableId);
    }

    public CmsUserPrivilegesPK getCmsUserPrivilegesPK() {
        return cmsUserPrivilegesPK;
    }

    public void setCmsUserPrivilegesPK(CmsUserPrivilegesPK cmsUserPrivilegesPK) {
        this.cmsUserPrivilegesPK = cmsUserPrivilegesPK;
    }

    public Boolean getSearch() {
        return search;
    }

    public void setSearch(Boolean search) {
        this.search = search;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public Boolean getRemove() {
        return remove;
    }

    public void setRemove(Boolean remove) {
        this.remove = remove;
    }

    public CmsRole getCmsRole() {
        return cmsRole;
    }

    public void setCmsRole(CmsRole cmsRole) {
        this.cmsRole = cmsRole;
    }

    public CmsTables getCmsTables() {
        return cmsTables;
    }

    public void setCmsTables(CmsTables cmsTables) {
        this.cmsTables = cmsTables;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cmsUserPrivilegesPK != null ? cmsUserPrivilegesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsUserPrivileges)) {
            return false;
        }
        CmsUserPrivileges other = (CmsUserPrivileges) object;
        if ((this.cmsUserPrivilegesPK == null && other.cmsUserPrivilegesPK != null) || (this.cmsUserPrivilegesPK != null && !this.cmsUserPrivilegesPK.equals(other.cmsUserPrivilegesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.CmsUserPrivileges[ cmsUserPrivilegesPK=" + cmsUserPrivilegesPK + " ]";
    }
    
}
