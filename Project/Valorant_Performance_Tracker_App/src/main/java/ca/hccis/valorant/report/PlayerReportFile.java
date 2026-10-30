package ca.hccis.valorant.report;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/** Writes a text copy of the report to the folder requested by the instructor. */
public class PlayerReportFile {
    public static Path save(String playerName, List<PlayerReportRow> rows) throws IOException {
        Path folder = Paths.get("C:\\cis2232");
        Files.createDirectories(folder); // Create folder if it does not exist.
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        Path file = folder.resolve("ValorantPlayerReport" + timestamp + ".txt");
        StringBuilder output = new StringBuilder();
        output.append("VALORANT PLAYER PERFORMANCE REPORT\n");
        output.append("Player: ").append(playerName).append("\n");
        output.append("Generated: ").append(LocalDateTime.now()).append("\n\n");
        output.append("Agent | Map | Kills | Deaths | Assists | KDA | ACS | KAST %\n");
        for (PlayerReportRow row : rows) {
            output.append(String.format(Locale.US, "%s | %s | %d | %d | %d | %.2f | %.2f | %.2f%%\n",
                    row.getAgent(), row.getMap(), row.getKills(), row.getDeaths(),
                    row.getAssists(), row.getKda(), row.getAcs(), row.getKast()));
        }
        Files.writeString(file, output.toString(), StandardCharsets.UTF_8);
        return file;
    }
}
