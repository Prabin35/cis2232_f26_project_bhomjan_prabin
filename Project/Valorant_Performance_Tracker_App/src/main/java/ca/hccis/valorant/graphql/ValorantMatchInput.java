package ca.hccis.valorant.graphql;

import ca.hccis.valorant.jpa.entity.ValorantMatch;

public class ValorantMatchInput {
    public String playerName;
    public String agent;
    public String mapName;
    public Integer kills;
    public Integer deaths;
    public Integer assists;
    public Integer damage;
    public Integer rounds;
    public Integer kastRounds;

    public ValorantMatch toEntity() {
        ValorantMatch e = new ValorantMatch();
        e.setPlayerName(this.playerName);
        e.setAgent(this.agent);
        e.setMapName(this.mapName);
        e.setKills(this.kills);
        e.setDeaths(this.deaths);
        e.setAssists(this.assists);
        e.setDamage(this.damage);
        e.setRounds(this.rounds);
        e.setKastRounds(this.kastRounds);
        return e;
    }
}
