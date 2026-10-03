package ca.hccis.files.bo;

import ca.hccis.files.entity.Match;

/**
 * Business Object class for calculating Valorant match performance.
 *
 * CIS 2232 | Assignment 2
 *
 * @author Prabin Bhomjan
 * @since 20261002
 */
public class MatchBO {

    /**
     * Calculates the KDA ratio for a Valorant match.
     *
     * KDA = (Kills + Assists) / Deaths
     *
     * @param match The Match object containing the player's statistics.
     * @return The calculated KDA ratio.
     */
    public static double calculate(Match match) {

        if (match == null) {
            return 0.0;
        }

        if (match.getDeaths() == 0) {
            return 0.0;
        }

        return (double) (match.getKills() + match.getAssists())
                / match.getDeaths();
    }

    /**
     * Calculates the simplified Average Combat Score (ACS).
     *
     * ACS = ((Kills x 150) + (Assists x 50) + Damage Dealt) / Rounds Played
     *
     * @param match The Match object containing the player's statistics.
     * @return The calculated simplified ACS.
     */
    public static double calculateACS(Match match) {

        if (match == null) {
            return 0.0;
        }

        if (match.getRounds() == 0) {
            return 0.0;
        }

        return (double) ((match.getKills() * 150)
                + (match.getAssists() * 50)
                + match.getDamage())
                / match.getRounds();
    }

    /**
     * Calculates the KAST percentage.
     *
     * KAST % = (KAST Rounds / Rounds Played) x 100
     *
     * @param match The Match object containing the player's statistics.
     * @return The calculated KAST percentage.
     */
    public static double calculateKAST(Match match) {

        if (match == null) {
            return 0.0;
        }

        if (match.getRounds() == 0) {
            return 0.0;
        }

        return ((double) match.getKastRounds()
                / match.getRounds()) * 100;
    }
}