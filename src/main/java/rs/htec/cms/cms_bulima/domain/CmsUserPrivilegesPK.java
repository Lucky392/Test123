/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lazar
 */
@Embeddable
public class CmsUserPrivilegesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "role_id")
    private long roleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "table_id")
    private long tableId;

    public CmsUserPrivilegesPK() {
    }

    public CmsUserPrivilegesPK(long roleId, long tableId) {
        this.roleId = roleId;
        this.tableId = tableId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) roleId;
        hash += (int) tableId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsUserPrivilegesPK)) {
            return false;
        }
        CmsUserPrivilegesPK other = (CmsUserPrivilegesPK) object;
        if (this.roleId != other.roleId) {
            return false;
        }
        if (this.tableId != other.tableId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.htec.cms.cms_bulima.domain.CmsUserPrivilegesPK[ roleId=" + roleId + ", tableId=" + tableId + " ]";
    }
    
}
