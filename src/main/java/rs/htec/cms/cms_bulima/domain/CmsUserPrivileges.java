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
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author marko
 */
@Entity
@Table(name = "CMS_USER_PRIVILEGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmsUserPrivileges.findAll", query = "SELECT c FROM CmsUserPrivileges c"),
    @NamedQuery(name = "CmsUserPrivileges.findByRoleId", query = "SELECT c FROM CmsUserPrivileges c WHERE c.cmsUserPrivilegesPK.roleId = :roleId"),
    @NamedQuery(name = "CmsUserPrivileges.findByTableId", query = "SELECT c FROM CmsUserPrivileges c WHERE c.cmsUserPrivilegesPK.tableId = :tableId"),
    @NamedQuery(name = "CmsUserPrivileges.findBySearchAction", query = "SELECT c FROM CmsUserPrivileges c WHERE c.searchAction = :searchAction"),
    @NamedQuery(name = "CmsUserPrivileges.findByEditAction", query = "SELECT c FROM CmsUserPrivileges c WHERE c.editAction = :editAction"),
    @NamedQuery(name = "CmsUserPrivileges.findByAddAction", query = "SELECT c FROM CmsUserPrivileges c WHERE c.addAction = :addAction"),
    @NamedQuery(name = "CmsUserPrivileges.findByDeleteAction", query = "SELECT c FROM CmsUserPrivileges c WHERE c.deleteAction = :deleteAction"),
    @NamedQuery(name = "CmsUserPrivileges.findByPK", query = "SELECT c FROM CmsUserPrivileges c WHERE c.cmsUserPrivilegesPK.roleId = :roleId AND c.cmsUserPrivilegesPK.tableId = :tableId")})
public class CmsUserPrivileges implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CmsUserPrivilegesPK cmsUserPrivilegesPK;
    @Column(name = "search_action")
    private Boolean searchAction;
    @Column(name = "edit_action")
    private Boolean editAction;
    @Column(name = "add_action")
    private Boolean addAction;
    @Column(name = "delete_action")
    private Boolean deleteAction;
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

    @XmlTransient
    @JsonIgnore
    public CmsUserPrivilegesPK getCmsUserPrivilegesPK() {
        return cmsUserPrivilegesPK;
    }

    public void setCmsUserPrivilegesPK(CmsUserPrivilegesPK cmsUserPrivilegesPK) {
        this.cmsUserPrivilegesPK = cmsUserPrivilegesPK;
    }

    public Boolean getSearchAction() {
        return searchAction;
    }

    public void setSearchAction(Boolean searchAction) {
        this.searchAction = searchAction;
    }

    public Boolean getEditAction() {
        return editAction;
    }

    public void setEditAction(Boolean editAction) {
        this.editAction = editAction;
    }

    public Boolean getAddAction() {
        return addAction;
    }

    public void setAddAction(Boolean addAction) {
        this.addAction = addAction;
    }

    public Boolean getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(Boolean deleteAction) {
        this.deleteAction = deleteAction;
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
        return cmsUserPrivilegesPK + "";
    }
    
}
