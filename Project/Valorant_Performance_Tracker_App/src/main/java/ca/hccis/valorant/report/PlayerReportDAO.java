package ca.hccis.valorant.report;

import ca.hccis.valorant.util.DatabaseUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Sprint 2: Gets report data using JDBC, not the JPA repository. */
public class PlayerReportDAO {
    public List<PlayerReportRow> findByPlayer(String playerName) throws SQLException {
        List<PlayerReportRow> rows = new ArrayList<>();
        // A question mark allows the user input to be safely passed to SQL.
        String sql = "SELECT agent, map, kills, deaths, assists, damage_dealt, "
                + "rounds_played, kast_rounds FROM valorant_match "
                + "WHERE LOWER(player_name) = LOWER(?) ORDER BY id";
        try (Connection connection = DatabaseUtility.getConnection("");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int kills = result.getInt("kills");
                    int deaths = result.getInt("deaths");
                    int assists = result.getInt("assists");
                    int rounds = result.getInt("rounds_played");
                    int damage = result.getInt("damage_dealt");
                    int kastRounds = result.getInt("kast_rounds");
                    // Use the same calculations as Sprint 1.
                    double kda = deaths == 0 ? kills + assists : (double) (kills + assists) / deaths;
                    double acs = rounds == 0 ? 0 : (kills * 150.0 + assists * 50.0 + damage) / rounds;
                    double kast = rounds == 0 ? 0 : kastRounds * 100.0 / rounds;
                    rows.add(new PlayerReportRow(result.getString("agent"), result.getString("map"),
                            kills, deaths, assists, kda, acs, kast));
                }
            }
        }
        return rows;
    }
}
