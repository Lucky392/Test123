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

/**
 *
 * @author stefan
 */
public class LineUpDifference {

    RestHelperClass helper;
    private JSONArray lineUpDifference;
    private Formation formationDifference;
    private long bulimaPointDifference;

    public LineUpDifference() {
        helper = new RestHelperClass();
    }

    public JSONArray getLineUpDifference() {
        return lineUpDifference;
    }

    public void setLineUpDifference(JSONArray lineUpDifference) {
        this.lineUpDifference = lineUpDifference;
    }

    public Formation getFormationDifference() {
        return formationDifference;
    }

    public void setFormationDifference(Formation formationDifference) {
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

    public Formation getFormation(long idFantasyClub, long idMatchday) {
        EntityManager em = helper.getEntityManager();
        Formation formation = null;
        try {
            String query = "SELECT f.idFormation FROM FantasyClubLineUp f WHERE f.idFantasyClub = " + idFantasyClub + " AND f.idMatchday = " + idMatchday;
            formation = em.createQuery(query, Formation.class).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }

        return formation;
    }
    
    public Formation returnDifferenceForFormation(Formation formation1, Formation formation2) {
        return (formation1 == formation2) ? null : formation2;
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

    public List<LineUpPlayer> toLineUpPlayer(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayer) {
        List<LineUpPlayer> lineUpPlayer = null;
        for (FantasyClubLineUpPlayer fantasyPlayer : fantasyClubLineUpPlayer) {
            LineUpPlayer player = new LineUpPlayer(fantasyPlayer.getId(), fantasyPlayer.getIsCaptain(), fantasyPlayer.getCreateDate(), fantasyPlayer.getIdPlayerSlot().getId(), fantasyPlayer.getIdLeaguePlayer().getId(), fantasyPlayer.getIdLineUp().getId());
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
