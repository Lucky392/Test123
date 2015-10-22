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

/**
 *
 * @author stefan
 */
public class MatchPlayerPOJO {

    private long id;
    private Date updateAt;
    private double rating;
    private Date ratingAt;
    private int blmPoints;
    private Date blmPointsUpdateAt;
    private short extra1;
    private short extra2;
    private Date createDate;
    private long idMatch;
    private String urlToMatch;
    private long idPlayer;
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
        this.urlToMatch = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/match/" + player.getIdMatch().getId();
        this.idPlayer = player.getIdPlayer().getId();
        this.urlToPlayer = "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/" + "rest/player/" + player.getIdPlayer().getId();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getRatingAt() {
        return ratingAt;
    }

    public void setRatingAt(Date ratingAt) {
        this.ratingAt = ratingAt;
    }

    public int getBlmPoints() {
        return blmPoints;
    }

    public void setBlmPoints(int blmPoints) {
        this.blmPoints = blmPoints;
    }

    public Date getBlmPointsUpdateAt() {
        return blmPointsUpdateAt;
    }

    public void setBlmPointsUpdateAt(Date blmPointsUpdateAt) {
        this.blmPointsUpdateAt = blmPointsUpdateAt;
    }

    public short getExtra1() {
        return extra1;
    }

    public void setExtra1(short extra1) {
        this.extra1 = extra1;
    }

    public short getExtra2() {
        return extra2;
    }

    public void setExtra2(short extra2) {
        this.extra2 = extra2;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(long idMatch) {
        this.idMatch = idMatch;
    }

    public String getUrlToMatch() {
        return urlToMatch;
    }

    public void setUrlToMatch(String urlToMatch) {
        this.urlToMatch = urlToMatch;
    }

    public long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(long idPlayer) {
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
