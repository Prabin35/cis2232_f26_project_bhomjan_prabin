package ca.hccis.files.entity;

import ca.hccis.files.util.CisUtility;

/**
 * Represents a Valorant match recorded by the user.
 *
 * CIS 2232 | Assignment 1
 *
 * @author Prabin Bhomjan
 * @since 09242026
 */
public class Match {

    private int matchId;
    private String playerName;
    private String agent;
    private String mapName;
    private int kills;
    private int deaths;
    private int assists;
    private int damage;
    private int rounds;
    private int kastRounds;


    /**
     * Default constructor.
     */
    public Match() {
    }


    /**
     * Creates a Match object with the supplied information.
     *
     * @param matchId match ID
     * @param playerName player name or Riot ID
     * @param agent agent used
     * @param mapName map played
     * @param kills total kills
     * @param deaths total deaths
     * @param assists total assists
     * @param damage total damage dealt
     * @param rounds total rounds played
     * @param kastRounds total KAST rounds
     */
    public Match(int matchId,
                 String playerName,
                 String agent,
                 String mapName,
                 int kills,
                 int deaths,
                 int assists,
                 int damage,
                 int rounds,
                 int kastRounds) {

        this.matchId = matchId;
        this.playerName = playerName;
        this.agent = agent;
        this.mapName = mapName;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.damage = damage;
        this.rounds = rounds;
        this.kastRounds = kastRounds;
    }


    /**
     * Collects match information from the user.
     */
    public void getInformation() {

        playerName = CisUtility.getInputString(
                "Player Name: "
        );

        agent = CisUtility.getInputString(
                "Agent: "
        );

        mapName = CisUtility.getInputString(
                "Map: "
        );

        kills = CisUtility.getInputInt(
                "Kills: "
        );

        deaths = CisUtility.getInputInt(
                "Deaths: "
        );

        assists = CisUtility.getInputInt(
                "Assists: "
        );

        damage = CisUtility.getInputInt(
                "Damage Dealt: "
        );

        rounds = CisUtility.getInputInt(
                "Rounds Played: "
        );

        kastRounds = CisUtility.getInputInt(
                "KAST Rounds: "
        );
    }


    public int getMatchId() {
        return matchId;
    }


    public void setMatchId(int matchId) {
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


    public int getKills() {
        return kills;
    }


    public void setKills(int kills) {
        this.kills = kills;
    }


    public int getDeaths() {
        return deaths;
    }


    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }


    public int getAssists() {
        return assists;
    }


    public void setAssists(int assists) {
        this.assists = assists;
    }


    public int getDamage() {
        return damage;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }


    public int getRounds() {
        return rounds;
    }


    public void setRounds(int rounds) {
        this.rounds = rounds;
    }


    public int getKastRounds() {
        return kastRounds;
    }


    public void setKastRounds(int kastRounds) {
        this.kastRounds = kastRounds;
    }


    /**
     * Displays the information for a Valorant match.
     *
     * @return formatted match information
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