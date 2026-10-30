package ca.hccis.valorant.controllers;

import ca.hccis.valorant.report.PlayerReportDAO;
import ca.hccis.valorant.report.PlayerReportFile;
import ca.hccis.valorant.report.PlayerReportRow;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/** Handles the input form and the report results page for Sprint 2. */
@Controller
public class PlayerReportController {
    private final PlayerReportDAO dao = new PlayerReportDAO();

    @GetMapping("/report/player")
    public String showInput() {
        return "report/player-input";
    }

    @PostMapping("/report/player")
    public String createReport(@RequestParam("playerName") String playerName, Model model) {
        String name = playerName.trim();
        if (name.isEmpty() || name.length() > 100) {
            model.addAttribute("error", "Please enter a player name (up to 100 characters).");
            return "report/player-input";
        }
        try {
            List<PlayerReportRow> rows = dao.findByPlayer(name);
            model.addAttribute("playerName", name);
            model.addAttribute("rows", rows);
            model.addAttribute("hasRows", !rows.isEmpty());
            List<String> labels = new ArrayList<>();
            List<Double> acsValues = new ArrayList<>();
            for (PlayerReportRow row : rows) {
                labels.add(row.getAgent() + " - " + row.getMap());
                acsValues.add(row.getAcs());
            }
            model.addAttribute("labels", labels);
            model.addAttribute("acsValues", acsValues);
            // Only save a file when matching data exists.
            if (!rows.isEmpty()) {
                model.addAttribute("filePath", PlayerReportFile.save(name, rows).toString());
            }
            return "report/player-results";
        } catch (Exception exception) {
            // Show a friendly message rather than an error page.
            model.addAttribute("error", "Report could not be created. Check the database and C:\\cis2232 folder permissions.");
            return "report/player-input";
        }
    }
}
