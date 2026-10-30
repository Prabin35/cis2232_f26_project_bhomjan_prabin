package ca.hccis.valorant.report;

/** Holds one row returned by the JDBC player report. */
public class PlayerReportRow {
    private final String agent, map;
    private final int kills, deaths, assists;
    private final double kda, acs, kast;

    public PlayerReportRow(String agent, String map, int kills, int deaths,
                           int assists, double kda, double acs, double kast) {
        this.agent = agent;
        this.map = map;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.kda = kda;
        this.acs = acs;
        this.kast = kast;
    }
    public String getAgent() { return agent; }
    public String getMap() { return map; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public int getAssists() { return assists; }
    public double getKda() { return kda; }
    public double getAcs() { return acs; }
    public double getKast() { return kast; }
}
