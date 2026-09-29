package ca.hccis.valorant.bo;

import ca.hccis.valorant.jpa.entity.ValorantMatch;

/**
 * Business logic for a Valorant match. The three performance statistics are
 * calculated here from the stored data; they are not stored in the database.
 *
 * @author Prabin Bhomjan
 * @since 20260928
 */
public class ValorantMatchBO {

    public static final int POINTS_PER_KILL = 150;
    public static final int POINTS_PER_ASSIST = 50;

    /**
     * KDA ratio = (Kills + Assists) / Deaths.
     * If the player has zero deaths the division is avoided and the value
     * returned is Kills + Assists.
     *
     * @param match the match
     * @return the KDA ratio
     */
    public static double calculateKdaRatio(ValorantMatch match) {
        int kills = valueOf(match.getKills());
        int assists = valueOf(match.getAssists());
        int deaths = valueOf(match.getDeaths());

        if (deaths == 0) {
            return kills + assists;
        }
        return (double) (kills + assists) / deaths;
    }

    /**
     * Simplified ACS = ((Kills x 150) + (Assists x 50) + Damage Dealt) / Rounds Played.
     * Returns 0 if no rounds were played.
     *
     * @param match the match
     * @return the simplified average combat score
     */
    public static double calculateAcs(ValorantMatch match) {
        int rounds = valueOf(match.getRounds());
        if (rounds == 0) {
            return 0;
        }
        double total = (valueOf(match.getKills()) * POINTS_PER_KILL)
                + (valueOf(match.getAssists()) * POINTS_PER_ASSIST)
                + valueOf(match.getDamage());
        return total / rounds;
    }

    /**
     * KAST % = (KAST Rounds / Rounds Played) x 100.
     * Returns 0 if no rounds were played.
     *
     * @param match the match
     * @return the KAST percentage
     */
    public static double calculateKastPercentage(ValorantMatch match) {
        int rounds = valueOf(match.getRounds());
        if (rounds == 0) {
            return 0;
        }
        return ((double) valueOf(match.getKastRounds()) / rounds) * 100;
    }

    private static int valueOf(Integer value) {
        return value == null ? 0 : value;
    }
}
