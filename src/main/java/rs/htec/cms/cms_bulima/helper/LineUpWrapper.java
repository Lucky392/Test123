/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cms.cms_bulima.helper;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUp;
import rs.htec.cms.cms_bulima.domain.FantasyClubLineUpPlayer;
import rs.htec.cms.cms_bulima.domain.Formation;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;

/**
 *
 * @author stefan
 */
public class LineUpWrapper {

    RestHelperClass helper;

    public LineUpWrapper() {
        helper = new RestHelperClass();
    }

    public List<FantasyClubLineUpPlayer> getLineUpList(long idFantasyClub, long idMatchday) {
        EntityManager em = helper.getEntityManager();
        FantasyClubLineUp lineUp = null;
        try {
            StringBuilder query = new StringBuilder("SELECT f FROM FantasyClubLineUp f WHERE f.idFantasyClub = ")
                    .append(idFantasyClub).append(" AND f.idMatchday = ").append(idMatchday);
            lineUp = (FantasyClubLineUp) em.createQuery(query.toString()).getSingleResult();
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
        FantasyClubLineUp lineUp = null;
        try {
            StringBuilder query = new StringBuilder("SELECT f FROM FantasyClubLineUp f WHERE f.idFantasyClub = ")
                    .append(idFantasyClub).append(" AND f.idMatchday = ").append(idMatchday);
            lineUp = (FantasyClubLineUp) em.createQuery(query.toString()).getSingleResult();
        } catch (Exception e) {
            throw new DataNotFoundException("LineUp with idFantasyClub " + idFantasyClub + " for matchday " + idMatchday + " does not exist..");
        }

        Formation formation = (Formation) em.createNamedQuery("Formation.findById").setParameter("id", lineUp.getIdFormation().getId()).getSingleResult();

        return formation;
    }
    
    public Formation returnDifferenceForFormation(Formation formation1, Formation formation2) {
        return (formation1==formation2)?null:formation2;
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
}
