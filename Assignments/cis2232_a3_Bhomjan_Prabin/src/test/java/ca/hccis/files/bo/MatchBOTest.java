package ca.hccis.files.bo;

import ca.hccis.files.entity.Match;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MatchBO business object.
 *
 * CIS 2232 | Assignment 2
 *
 * @author Prabin Bhomjan
 * @since 20261002
 */
class MatchBOTest {

    /**
     * Test 1 following TDD.
     *
     * Tests the KDA calculation using the example provided
     * in the project README.
     *
     * KDA = (Kills + Assists) / Deaths
     *
     * This test was created using a test-driven development approach.
     *
     * @since 20261002
     * @author Prabin Bhomjan
     */
    @Test
    void testCalculateKDAExample() {

        Match match = new Match();

        match.setKills(20);
        match.setDeaths(14);
        match.setAssists(8);

        double actual = MatchBO.calculate(match);

        assertEquals(2.00, actual, 0.01);
    }

    /**
     * Test 2 following TDD.
     *
     * Tests the KDA calculation when a player has a high number
     * of kills and assists compared to deaths.
     *
     * This test was created using a test-driven development approach.
     *
     * @since 20261002
     * @author Prabin Bhomjan
     */
    @Test
    void testCalculateHighKDA() {

        Match match = new Match();

        match.setKills(30);
        match.setDeaths(5);
        match.setAssists(10);

        double actual = MatchBO.calculate(match);

        assertTrue(actual > 5.0);
    }

    /**
     * Test 3 following TDD.
     *
     * Tests the KDA calculation when the player has more deaths
     * than combined kills and assists.
     *
     * This test was created using a test-driven development approach.
     *
     * @since 20261002
     * @author Prabin Bhomjan
     */
    @Test
    void testCalculateLowKDA() {

        Match match = new Match();

        match.setKills(10);
        match.setDeaths(20);
        match.setAssists(5);

        double actual = MatchBO.calculate(match);

        assertEquals(0.75, actual, 0.01);
    }


    //****************************************************************************
    // The following unit tests were created by AI from the README requirements
    // and the Match entity class.
    //
    // These tests are intended to provide additional coverage of the
    // KDA, ACS, and KAST calculations described in the project requirements.
    //****************************************************************************

    /**
     * Helper method used to create a Match object for the AI-generated tests.
     *
     * @param kills number of kills
     * @param deaths number of deaths
     * @param assists number of assists
     * @param damage damage dealt
     * @param rounds number of rounds played
     * @param kastRounds number of KAST rounds
     * @return a Match object containing the supplied statistics
     */
    private Match buildMatch(int kills, int deaths, int assists,
                             int damage, int rounds, int kastRounds) {

        Match match = new Match();

        match.setKills(kills);
        match.setDeaths(deaths);
        match.setAssists(assists);
        match.setDamage(damage);
        match.setRounds(rounds);
        match.setKastRounds(kastRounds);

        return match;
    }

    /**
     * AI-generated test based on the README calculation requirements.
     *
     * Tests the KDA calculation using a second valid match.
     */
    @Test
    void calculate_kdaWithDifferentStatistics() {

        Match match = buildMatch(15, 10, 5, 2500, 20, 15);

        double actual = MatchBO.calculate(match);

        assertEquals(2.0, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests the KDA calculation when the player has zero deaths.
     * The business object should handle this case without
     * causing a division-by-zero error.
     */
    @Test
    void calculate_kdaWithZeroDeaths() {

        Match match = buildMatch(20, 0, 5, 3000, 20, 18);

        double actual = MatchBO.calculate(match);

        assertEquals(0.0, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests the simplified ACS calculation using the formula:
     *
     * ACS = ((Kills x 150) + (Assists x 50) + Damage) / Rounds
     */
    @Test
    void calculateACS_worksWithNormalMatch() {

        Match match = buildMatch(20, 14, 8, 3200, 21, 16);

        double actual = MatchBO.calculateACS(match);

        assertEquals(314.29, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests ACS when the player has no kills or assists.
     * This confirms that the damage component is still included.
     */
    @Test
    void calculateACS_withNoKillsOrAssists() {

        Match match = buildMatch(0, 10, 0, 2000, 20, 10);

        double actual = MatchBO.calculateACS(match);

        assertEquals(100.0, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests ACS when rounds played is zero.
     * The calculation must avoid division by zero.
     */
    @Test
    void calculateACS_withZeroRounds() {

        Match match = buildMatch(20, 10, 5, 3000, 0, 0);

        double actual = MatchBO.calculateACS(match);

        assertEquals(0.0, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests the KAST percentage calculation using the formula:
     *
     * KAST % = (KAST Rounds / Rounds Played) x 100
     */
    @Test
    void calculateKAST_withNormalMatch() {

        Match match = buildMatch(20, 14, 8, 3200, 21, 16);

        double actual = MatchBO.calculateKAST(match);

        assertEquals(76.19, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests KAST when every round has a KAST event.
     * The expected result should be 100%.
     */
    @Test
    void calculateKAST_allRoundsHaveKAST() {

        Match match = buildMatch(20, 10, 10, 3000, 20, 20);

        double actual = MatchBO.calculateKAST(match);

        assertEquals(100.0, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests KAST when no rounds have a KAST event.
     * The expected percentage should be 0%.
     */
    @Test
    void calculateKAST_noRoundsHaveKAST() {

        Match match = buildMatch(0, 20, 0, 1000, 20, 0);

        double actual = MatchBO.calculateKAST(match);

        assertEquals(0.0, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Tests KAST when rounds played is zero.
     * The calculation must avoid division by zero.
     */
    @Test
    void calculateKAST_withZeroRounds() {

        Match match = buildMatch(0, 0, 0, 0, 0, 0);

        double actual = MatchBO.calculateKAST(match);

        assertEquals(0.0, actual, 0.01);
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Uses assertAll to verify that the same match produces
     * the expected KDA, ACS, and KAST values.
     */
    @Test
    void calculate_allPerformanceStatistics() {

        Match match = buildMatch(20, 14, 8, 3200, 21, 16);

        double kda = MatchBO.calculate(match);
        double acs = MatchBO.calculateACS(match);
        double kast = MatchBO.calculateKAST(match);

        assertAll(
                () -> assertEquals(2.00, kda, 0.01),
                () -> assertEquals(314.29, acs, 0.01),
                () -> assertEquals(76.19, kast, 0.01)
        );
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Verifies that a Match object can contain all required
     * statistics used by the business calculations.
     */
    @Test
    void calculate_matchContainsRequiredStatistics() {

        Match match = buildMatch(20, 14, 8, 3200, 21, 16);

        assertAll(
                () -> assertEquals(20, match.getKills()),
                () -> assertEquals(14, match.getDeaths()),
                () -> assertEquals(8, match.getAssists()),
                () -> assertEquals(3200, match.getDamage()),
                () -> assertEquals(21, match.getRounds()),
                () -> assertEquals(16, match.getKastRounds())
        );
    }

    /**
     * AI-generated test based on the README requirements.
     *
     * Verifies that a valid Match object can be passed to all
     * three calculation methods without producing an invalid result.
     */
    @Test
    void calculate_validMatchProducesValidResults() {

        Match match = buildMatch(25, 15, 10, 3500, 25, 20);

        double kda = MatchBO.calculate(match);
        double acs = MatchBO.calculateACS(match);
        double kast = MatchBO.calculateKAST(match);

        assertTrue(kda >= 0);
        assertTrue(acs >= 0);
        assertTrue(kast >= 0 && kast <= 100);
    }
}

