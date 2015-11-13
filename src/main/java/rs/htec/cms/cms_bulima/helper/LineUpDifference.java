/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import com.sun.jersey.api.core.InjectParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.pojo.FantasyClubLineUpPlayerPOJO;
import rs.htec.cms.cms_bulima.pojo.LineUpPlayerPOJO;

/**
 *
 * @author stefan
 */
public class LineUpDifference {

    @InjectParam
    RestHelperClass helper;

    public static List<FantasyClubLineUpPlayer> getLineUpList(EntityManager em, long idFantasyClub, long idMatchday) {
        Long lineUpId = null;
        try {
            String query = "SELECT id FROM FantasyClubLineUp f WHERE f.idFantasyClub = " + idFantasyClub + " AND f.idMatchday = " + idMatchday;
            lineUpId = (Long) em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }
//        long startTime = System.currentTimeMillis();
        List<FantasyClubLineUpPlayer> lineUpPlayer = em.createNamedQuery("FantasyClubLineUpPlayer.findByLineUpId").setParameter("idLineUp", lineUpId).getResultList();

//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        System.out.println(elapsedTime);
        //List<LineUpPlayer> lineUpPlayer = toLineUpPlayer(fantasyClubLineUpPlayer);
        if (lineUpPlayer == null || lineUpPlayer.isEmpty()) {
            throw new DataNotFoundException("There is no LineUpPlayers for this search!");
        }
        return lineUpPlayer;
    }

    public static String getFormation(EntityManager em, long idFantasyClub, long idMatchday) {
        String formation = null;
        try {
            String query = "SELECT f.idFormation.name FROM FantasyClubLineUp f WHERE f.idFantasyClub = " + idFantasyClub + " AND f.idMatchday = " + idMatchday;
            formation = (String) em.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }
        return formation;
    }

    public static String returnDifferenceForFormation(String formation1, String formation2) {
        return (formation1.equals(formation2)) ? "Formations are same: " + formation1 : "Formations are different, frst formation: " + formation1 + ", second: " + formation2;
    }

    public static long getCount(List<FantasyClubLineUpPlayer> lineUp) {
        return lineUp.size();
    }

    public static boolean areEqual(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        return lineUp.equals(lineUpHistory);
    }

    public static List<FantasyClubLineUpPlayer> returnDifference(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
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

    public static List<FantasyClubLineUpPlayerPOJO> returnDifferencePOJO(List<FantasyClubLineUpPlayer> lineUp, List<FantasyClubLineUpPlayer> lineUpHistory) {
        List<FantasyClubLineUpPlayerPOJO> difference = new ArrayList<>();
        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : lineUpHistory) {
            boolean found = false;
            for (FantasyClubLineUpPlayer lineUpPlayer : lineUp) {
                if (lineUpHistoryPlayer.getIdPlayerSlot().equals(lineUpPlayer.getIdPlayerSlot())) {
                    found = true;
                    if (!lineUpHistoryPlayer.getIdLeaguePlayer().equals(lineUpPlayer.getIdLeaguePlayer()) || !lineUpHistoryPlayer.getIsCaptain().equals(lineUpPlayer.getIsCaptain())) {
                        FantasyClubLineUpPlayerPOJO player = new FantasyClubLineUpPlayerPOJO(lineUpHistoryPlayer);
                        difference.add(player);
                    }
                    break;
                }
            }
            if (!found) {
                FantasyClubLineUpPlayerPOJO player = new FantasyClubLineUpPlayerPOJO(lineUpHistoryPlayer);
                difference.add(player);
            }
        }
        return difference;
    }

    private static String getPositionNameForPlayer(FantasyClubLineUpPlayer player) {
        if (player == null) {
            return null;
        }
        String form = player.getIdLineUp().getIdFormation().getName();
        String[] formation = form.split("-");
        long playerPosition = player.getIdPlayerSlot().getId();
        long position = 1;
        if (playerPosition == position) {
            return "TW";
        }
        position += Long.valueOf(formation[0]);
        if (playerPosition <= position){
            return "AB";
        }
        position += Long.valueOf(formation[1]);
        if (playerPosition <= position){
            return "MF";
        }
        position += Long.valueOf(formation[2]);
        if (playerPosition <= position){
            return "ST";
        }
        return "SUB";
    }

//    public List<LineUpPlayerPOJO> toLineUpPlayerPOJO(List<FantasyClubLineUpPlayer> fantasyClubLineUpPlayer) {
//        List<LineUpPlayerPOJO> lineUpPlayer = null;
//        for (FantasyClubLineUpPlayer lineUpHistoryPlayer : fantasyClubLineUpPlayer) {
//            LineUpPlayerPOJO player = new LineUpPlayerPOJO(lineUpHistoryPlayer.getId(), lineUpHistoryPlayer.getIsCaptain(), lineUpHistoryPlayer.getIdPlayerSlot().getId(), lineUpHistoryPlayer.getIdLeaguePlayer().getId(), lineUpHistoryPlayer.getIdLeaguePlayer().getIdPlayer().getFullname(), lineUpHistoryPlayer.getIdLineUp().getId(), getPositionNameForPlayer(lineUpHistoryPlayer));
//            lineUpPlayer.add(player);
//        }
//        return lineUpPlayer;
//    }

    public static long getBlmPoints(EntityManager em, long idFantasyClub, long idMatchday) {
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
