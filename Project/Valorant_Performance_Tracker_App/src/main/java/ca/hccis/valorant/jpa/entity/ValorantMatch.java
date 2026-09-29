package ca.hccis.valorant.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * Maps a Valorant match performance record to the valorant_match table in
 * MySQL. The attribute names match the Match class from Assignment 1.
 * <p>
 * KDA ratio, simplified ACS and KAST percentage are NOT attributes of this
 * class. They are calculated from the attributes below by ValorantMatchBO.
 *
 * @author Prabin Bhomjan
 * @since 20260928
 */
@Entity
@Table(name = "valorant_match")
public class ValorantMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer matchId;

    @Size(min = 1, max = 100)
    @NotNull
    @Column(name = "player_name", nullable = false, length = 100)
    private String playerName;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "agent", nullable = false, length = 50)
    private String agent;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "map", nullable = false, length = 50)
    private String mapName;

    @NotNull
    @Min(0)
    @Column(name = "kills", nullable = false)
    private Integer kills;

    @NotNull
    @Min(0)
    @Column(name = "deaths", nullable = false)
    private Integer deaths;

    @NotNull
    @Min(0)
    @Column(name = "assists", nullable = false)
    private Integer assists;

    @NotNull
    @Min(0)
    @Column(name = "damage_dealt", nullable = false)
    private Integer damage;

    @NotNull
    @Min(1)
    @Column(name = "rounds_played", nullable = false)
    private Integer rounds;

    @NotNull
    @Min(0)
    @Column(name = "kast_rounds", nullable = false)
    private Integer kastRounds;

    /**
     * Default constructor required by JPA. Numeric values start at zero.
     */
    public ValorantMatch() {
        this.kills = 0;
        this.deaths = 0;
        this.assists = 0;
        this.damage = 0;
        this.rounds = 1;
        this.kastRounds = 0;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getRounds() {
        return rounds;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    public Integer getKastRounds() {
        return kastRounds;
    }

    public void setKastRounds(Integer kastRounds) {
        this.kastRounds = kastRounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValorantMatch that = (ValorantMatch) o;
        return Objects.equals(matchId, that.matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId);
    }

    /**
     * Displays the information for a Valorant match (same layout as the
     * Assignment 1 Match class).
     */
    @Override
    public String toString() {
        return "Match ID: " + matchId + "\n"
                + "Player Name: " + playerName + "\n"
                + "Agent: " + agent + "\n"
                + "Map: " + mapName + "\n"
                + "Kills: " + kills + "\n"
                + "Deaths: " + deaths + "\n"
                + "Assists: " + assists + "\n"
                + "Damage Dealt: " + damage + "\n"
                + "Rounds Played: " + rounds + "\n"
                + "KAST Rounds: " + kastRounds;
    }
}
