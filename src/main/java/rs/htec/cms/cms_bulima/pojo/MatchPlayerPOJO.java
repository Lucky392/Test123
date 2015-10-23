/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.htec.cms.cms_bulima.domain.MatchPlayer;
import rs.htec.cms.cms_bulima.helper.Util;

/**
 *
 * @author stefan
 */
public class MatchPlayerPOJO {

    private Long id;
    private Date updateAt;
    private Double rating;
    private Date ratingAt;
    private Integer blmPoints;
    private Date blmPointsUpdateAt;
    private Short extra1;
    private Short extra2;
    private Date createDate;
    private Long idMatch;
    private String urlToMatch;
    private Long idPlayer;
    private String urlToPlayer;

    public MatchPlayerPOJO(MatchPlayer player) {
        this.id = player.getId();
        this.updateAt = player.getUpdateAt();
        this.rating = player.getRating();
        this.ratingAt = player.getRatingAt();
        this.blmPoints = player.getBlmPoints();
        this.blmPointsUpdateAt = player.getBlmPointsUpdateAt();
        this.extra1 = player.getExtra1();
        this.extra2 = player.getExtra2();
        this.createDate = player.getCreateDate();
        this.idMatch = player.getIdMatch().getId();
        this.urlToMatch = Util.getInstance().getUrl() + "rest/match/" + player.getIdMatch().getId();
        this.idPlayer = player.getIdPlayer().getId();
        this.urlToPlayer = Util.getInstance().getUrl() + "rest/player/" + player.getIdPlayer().getId();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getRatingAt() {
        return ratingAt;
    }

    public void setRatingAt(Date ratingAt) {
        this.ratingAt = ratingAt;
    }

    public Integer getBlmPoints() {
        return blmPoints;
    }

    public void setBlmPoints(Integer blmPoints) {
        this.blmPoints = blmPoints;
    }

    public Date getBlmPointsUpdateAt() {
        return blmPointsUpdateAt;
    }

    public void setBlmPointsUpdateAt(Date blmPointsUpdateAt) {
        this.blmPointsUpdateAt = blmPointsUpdateAt;
    }

    public Short getExtra1() {
        return extra1;
    }

    public void setExtra1(Short extra1) {
        this.extra1 = extra1;
    }

    public Short getExtra2() {
        return extra2;
    }

    public void setExtra2(Short extra2) {
        this.extra2 = extra2;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public String getUrlToMatch() {
        return urlToMatch;
    }

    public void setUrlToMatch(String urlToMatch) {
        this.urlToMatch = urlToMatch;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getUrlToPlayer() {
        return urlToPlayer;
    }

    public void setUrlToPlayer(String urlToPlayer) {
        this.urlToPlayer = urlToPlayer;
    }

    public static List<MatchPlayerPOJO> toMatchdayPOJOList(List<MatchPlayer> list) {
        MatchPlayerPOJO pojo;
        List<MatchPlayerPOJO> pojos = new ArrayList<>();
        for (MatchPlayer player : list) {
            pojo = new MatchPlayerPOJO(player);
            pojos.add(pojo);
        }
        return pojos;
    }
}
