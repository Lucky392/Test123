/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.UserPremiumItem;

/**
 *
 * @author stefan
 */
public class UserPremiumItemPOJO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private Integer charges;
    private Date updateTimestamp;
    private Date createDate;
    private Long idUser;
    private Long idPremiumItem;

    public UserPremiumItemPOJO(UserPremiumItem item) {
        this.id = item.getId();
        this.startDate = item.getStartDate();
        this.endDate = item.getEndDate();
        this.charges = item.getCharges();
        this.updateTimestamp = item.getUpdateTimestamp();
        this.createDate = item.getCreateDate();
        if (item.getIdUser() != null) {
            this.idUser = item.getIdUser().getId();
        }
        if (item.getIdPremiumItem() != null) {
            this.idPremiumItem = item.getIdPremiumItem().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdPremiumItem() {
        return idPremiumItem;
    }

    public void setIdPremiumItem(Long idPremiumItem) {
        this.idPremiumItem = idPremiumItem;
    }
    
        public static List<UserPremiumItemPOJO> toUserPremiumItemPOJOList(List<UserPremiumItem> list) {
        UserPremiumItemPOJO pojo;
        List<UserPremiumItemPOJO> pojos = new ArrayList<>();
        for (UserPremiumItem ph : list) {
            pojo = new UserPremiumItemPOJO(ph);
            pojos.add(pojo);
        }
        return pojos;
    }

}
