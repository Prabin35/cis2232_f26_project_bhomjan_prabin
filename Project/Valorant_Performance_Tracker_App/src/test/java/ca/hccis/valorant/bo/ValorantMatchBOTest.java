package ca.hccis.valorant.bo;

import ca.hccis.valorant.jpa.entity.ValorantMatch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValorantMatchBOTest {

    private ValorantMatch createMatch(int kills, int deaths, int assists, int damage, int rounds, int kastRounds) {
        ValorantMatch match = new ValorantMatch();
        match.setKills(kills);
        match.setDeaths(deaths);
        match.setAssists(assists);
        match.setDamage(damage);
        match.setRounds(rounds);
        match.setKastRounds(kastRounds);
        return match;
    }

    // Worked example from the BA document: 20 kills, 14 deaths, 8 assists,
    // 3200 damage, 21 rounds, 16 KAST rounds.
    @Test
    void calculateKdaRatio_baExample() {
        ValorantMatch match = createMatch(20, 14, 8, 3200, 21, 16);
        assertEquals(2.00, ValorantMatchBO.calculateKdaRatio(match), 0.005);
    }

    @Test
    void calculateAcs_baExample() {
        ValorantMatch match = createMatch(20, 14, 8, 3200, 21, 16);
        assertEquals(314.29, ValorantMatchBO.calculateAcs(match), 0.005);
    }

    @Test
    void calculateKastPercentage_baExample() {
        ValorantMatch match = createMatch(20, 14, 8, 3200, 21, 16);
        assertEquals(76.19, ValorantMatchBO.calculateKastPercentage(match), 0.005);
    }

    @Test
    void calculateKdaRatio_zeroDeathsDoesNotDivideByZero() {
        ValorantMatch match = createMatch(10, 0, 5, 1500, 20, 15);
        assertEquals(15.0, ValorantMatchBO.calculateKdaRatio(match), 0.0001);
    }

    @Test
    void calculateAcsAndKast_zeroRoundsReturnsZero() {
        ValorantMatch match = createMatch(1, 1, 1, 100, 0, 0);
        assertEquals(0.0, ValorantMatchBO.calculateAcs(match), 0.0001);
        assertEquals(0.0, ValorantMatchBO.calculateKastPercentage(match), 0.0001);
    }

    @Test
    void validateKastRounds_greaterThanRoundsIsInvalid() {
        ValorantMatch match = createMatch(1, 1, 1, 100, 10, 11);
        assertEquals(1, ValorantMatchValidationBO.validateKastRounds(match).size());
    }

    @Test
    void validateKastRounds_validMatchHasNoErrors() {
        ValorantMatch match = createMatch(20, 14, 8, 3200, 21, 16);
        assertEquals(0, ValorantMatchValidationBO.validateKastRounds(match).size());
    }
}
