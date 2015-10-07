/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.simple.JSONArray;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.domain.Formation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.pojo.LineUpPlayerPOJO;

/**
 *
 * @author stefan
 */
public class LineUpDifference {

    RestHelperClass helper;
    private List<LineUpPlayerPOJO> lineUpDifference;
    private String formationDifference;
    private long bulimaPointDifference;

    public LineUpDifference() {
        helper = new RestHelperClass();
    }

    public List<LineUpPlayerPOJO> getLineUpDifference() {
        return lineUpDifference;
    }

    public void setLineUpDifference(List<LineUpPlayerPOJO> lineUpDifference) {
        this.lineUpDifference = lineUpDifference;
    }

    public String getFormationDifference() {
        return formationDifference;
    }

    public void setFormationDifference(String formationDifference) {
        this.formationDifference = formationDifference;
    }

    public long getBulimaPointDifference() {
        return bulimaPointDifference;
    }

    public void setBulimaPointDifference(long bulimaPointDifference) {
        this.bulimaPointDifference = bulimaPointDifference;
    }

    public List<FantasyClubLineUpPlayer> getLineUpList(long idFantasyClub, long idMatchday) {
        EntityManager em = helper.getEntityManager();
        FantasyClubLineUp lineUp = null;
        try {
            String query = "SELECT f FROM FantasyClubLineUp f WHERE f.idFantasyClub = " + idFantasyClub + " AND f.idMatchday = " + idMatchday;
            lineUp = (FantasyClubLineUp) em.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }
        List<FantasyClubLineUpPlayer> lineUpPlayer = em.createNamedQuery("FantasyClubLineUpPlayer.findByLineUpId").setParameter("idLineUp", lineUp.getId()).getResultList();
        //List<LineUpPlayer> lineUpPlayer = toLineUpPlayer(fantasyClubLineUpPlayer);
        if (lineUpPlayer == null || lineUpPlayer.isEmpty()) {
            throw new DataNotFoundException("There is no line up players for this search!");
        }

        return lineUpPlayer;
    }

    public String getFormation(long idFantasyClub, long idMatchday) {
        EntityManager em = helper.getEntityManager();
        String formation = null;
        try {
            String query = "SELECT f.idFormation.name FROM FantasyClubLineUp f WHERE f.idFantasyClub = " + idFantasyClub + " AND f.idMatchday = " + idMatchday;
            formation = (String) em.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }
        return formation;
    }

    public String returnDifferenceForFormation(String formation1, String formation2) {
        return (formation1.equals(formation2)) ? "Formations are same: " + formation1 : formation2;
    }

    public long getCount(List<FantasyClubLineUpPlayer> lineUp) {
        return lineUp.size();
    }

    public boolean areEqual(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        return lineUp.equals(lineUpHistory);
    }

    public List<FantasyClubLineUpPlayer> returnDifference(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        List<FantasyClubLineUpPlayer> difference = new ArrayList<>();
        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : lineUpHistory) {
            boolean find = false;
            for (FantasyClubLineUpPlayer lineUpPlayer : lineUp) {
                if (lineUpHistoryPlayer.getIdPlayerSlot().equals(lineUpPlayer.getIdPlayerSlot())) {
                    find = true;
                    if (!lineUpHistoryPlayer.getIdLeaguePlayer().equals(lineUpPlayer.getIdLeaguePlayer()) || !lineUpHistoryPlayer.getIsCaptain().equals(lineUpPlayer.getIsCaptain())) {
                        difference.add(lineUpHistoryPlayer);
                    }
                    break;
                }
            }
            if (!find) {
                difference.add(lineUpHistoryPlayer);
            }
        }
        return difference;
    }

    public List<LineUpPlayerPOJO> returnDifferencePOJO(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        List<LineUpPlayerPOJO> difference = new ArrayList<>();
        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : lineUpHistory) {
            boolean find = false;
            for (FantasyClubLineUpPlayer lineUpPlayer : lineUp) {
                if (lineUpHistoryPlayer.getIdPlayerSlot().equals(lineUpPlayer.getIdPlayerSlot())) {
                    find = true;
                    if (!lineUpHistoryPlayer.getIdLeaguePlayer().equals(lineUpPlayer.getIdLeaguePlayer()) || !lineUpHistoryPlayer.getIsCaptain().equals(lineUpPlayer.getIsCaptain())) {
                        LineUpPlayerPOJO player = new LineUpPlayerPOJO(lineUpHistoryPlayer.getId(), lineUpHistoryPlayer.getIsCaptain(), lineUpHistoryPlayer.getIdPlayerSlot().getId(), lineUpHistoryPlayer.getIdLeaguePlayer().getIdPlayer().getFullname(), lineUpHistoryPlayer.getIdLineUp().getId());
                        difference.add(player);
                    }
                    break;
                }
            }
            if (!find) {
                LineUpPlayerPOJO player = new LineUpPlayerPOJO(lineUpHistoryPlayer.getId(), lineUpHistoryPlayer.getIsCaptain(), lineUpHistoryPlayer.getIdPlayerSlot().getId(), lineUpHistoryPlayer.getIdLeaguePlayer().getIdPlayer().getFullname(), lineUpHistoryPlayer.getIdLineUp().getId());
                difference.add(player);
            }
        }
        return difference;
    }

    public List<LineUpPlayerPOJO> toLineUpPlayerPOJO(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayer) {
        List<LineUpPlayerPOJO> lineUpPlayer = null;
        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : fantasyClubLineUpPlayer) {
            LineUpPlayerPOJO player = new LineUpPlayerPOJO(lineUpHistoryPlayer.getId(), lineUpHistoryPlayer.getIsCaptain(), lineUpHistoryPlayer.getIdPlayerSlot().getId(), lineUpHistoryPlayer.getIdLeaguePlayer().getIdPlayer().getFullname(), lineUpHistoryPlayer.getIdLineUp().getId());
            lineUpPlayer.add(player);
        }
        return lineUpPlayer;
    }

    public long getBlmPoints(long idFantasyClub, long idMatchday) {
        EntityManager em = helper.getEntityManager();
        long points = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            Date date;
            String q = "SELECT lu.createDate FROM FantasyClubLineUp lu WHERE lu.idFantasyClub = " + idFantasyClub + " AND lu.idMatchday = " + idMatchday;
            date = (Date) em.createQuery(q).getSingleResult();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 7);

            String query = "SELECT SUM(mp.blmPoints) FROM FantasyClubLineUp lu, FantasyClubLineUpPlayer lup, FantasyLeaguePlayer flp, MatchPlayer mp"
                    + " WHERE lu.id=lup.idLineUp AND flp.id=lup.idLeaguePlayer AND flp.idPlayer=mp.idPlayer"
                    + " AND lu.idFantasyClub = " + idFantasyClub + " AND lu.idMatchday = " + idMatchday + " AND mp.ratingAt BETWEEN lu.createDate AND '" + sdf.format(calendar.getTime()) + "'";
            points = (long) em.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist.." + e.getMessage());
        }

        return points;
    }

}
