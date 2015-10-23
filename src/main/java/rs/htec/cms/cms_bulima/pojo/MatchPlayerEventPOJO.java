/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.MatchPlayerEvent;

/**
 *
 * @author marko
 */
public class MatchPlayerEventPOJO {
    
    private long id;
    private String type;
    private Integer eventTimeMinute;
    private Date updateAt;
    private Date createDate;
    private String matchPlayerUrl;
    
    public MatchPlayerEventPOJO(MatchPlayerEvent mpe){
        this.id = mpe.getId();
        this.type = mpe.getType();
        this.eventTimeMinute = mpe.getEventTimeMinute();
        this.updateAt = mpe.getUpdateAt();
        this.createDate = mpe.getCreateDate();
        this.matchPlayerUrl = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/matchPlayer/" + mpe.getIdMatchPlayer().getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEventTimeMinute() {
        return eventTimeMinute;
    }

    public void setEventTimeMinute(Integer eventTimeMinute) {
        this.eventTimeMinute = eventTimeMinute;
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

    public String getMatchPlayerUrl() {
        return matchPlayerUrl;
    }

    public void setMatchPlayerUrl(String matchPlayerUrl) {
        this.matchPlayerUrl = matchPlayerUrl;
    }
    
    public static List<MatchPlayerEventPOJO> toMatchPlayerEventPOJOList(List<MatchPlayerEvent> events){
        MatchPlayerEventPOJO pojo;
        List<MatchPlayerEventPOJO> pojos = new ArrayList<>();
        for (MatchPlayerEvent event : events) {
            pojo = new MatchPlayerEventPOJO(event);
            pojos.add(pojo);
        }
        return pojos;
    }
}
