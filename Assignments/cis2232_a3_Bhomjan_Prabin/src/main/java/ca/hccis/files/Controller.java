package ca.hccis.files;

import ca.hccis.files.bo.MatchBO;
import ca.hccis.files.entity.Match;
import ca.hccis.files.threads.ConsoleThread;
import ca.hccis.files.threads.DialogThread;
import ca.hccis.files.util.CisUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Controller for the Valorant Performance Tracker application.
 *
 * CIS 2232 | Assignment 1, 2 & 3
 *
 * @author Prabin Bhomjan
 * @since 20261002
 * @updated 20261012 A3: the menu now runs in two threads at the same time
 *          (console + JOptionPane) sharing the same matches and file.
 */
public class Controller {

    public static final String MENU =
            "A) Add Match\n" +
                    "V) View Matches\n" +
                    "X) Exit\n";

    public static final String MESSAGE_ERROR = "Invalid menu option.";
    public static final String MESSAGE_EXIT = "Goodbye!";
    public static final String MESSAGE_SUCCESS = "Match saved successfully.";

    // A3: message for a non-numeric entry
    public static final String MESSAGE_BAD_NUMBER =
            "A number was expected. Match was not saved.";

    public static final String EXIT = "X";

    private static final HashMap<Integer, Match> matches = new HashMap<>();

    // A3: both threads lock on this before using matches or the file
    private static final Object LOCK = new Object();

    // A3: static accumulator - number of matches added by BOTH threads
    private static int totalEntriesAdded = 0;

    private static final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public static final String PATH_NAME =
            "c:\\cis2232\\data_bhomjan_prabin.json";


    /**
     * Starts the Valorant Performance Tracker application.
     * A3: starts a console thread and a JOptionPane thread, then waits
     * for both of them to finish.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        initialize();

        ConsoleThread consoleThread = new ConsoleThread();
        Thread dialogThread = new Thread(new DialogThread());

        consoleThread.start();
        dialogThread.start();

        try {
            consoleThread.join();
            dialogThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted!");
        }

        System.out.println("Total matches added by both threads: "
                + totalEntriesAdded);
    }


    /**
     * Console menu loop (this was the loop in main for A1/A2).
     * A3: moved into its own method so the console thread can run it.
     */
    public static void menuConsole() {

        String choice;

        do {

            choice = CisUtility.getInputString(MENU);

            switch (choice.toUpperCase()) {

                case "A":
                    addMatch();
                    break;

                case "V":
                    viewMatches();
                    break;

                case EXIT:
                    System.out.println(MESSAGE_EXIT);
                    break;

                default:
                    System.out.println(MESSAGE_ERROR);
            }

        } while (!choice.equalsIgnoreCase(EXIT));
    }


    /**
     * Adds a new Valorant match to the collection.
     */
    public static void addMatch() {

        System.out.println("\n--- Add Valorant Match ---");

        Match match = new Match();

        try {
            match.getInformation();
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_BAD_NUMBER);
            return;
        }

        int total = storeMatch(match);

        System.out.println(MESSAGE_SUCCESS);
        System.out.println("Matches added by both threads so far: " + total);
    }


    /**
     * A3: stores a match (gets the ID, puts it in the map, updates the
     * accumulator and saves the file). Synchronized so only one thread at
     * a time can do this.
     *
     * @param match the match to store
     * @return the accumulator value after this add
     */
    private static int storeMatch(Match match) {

        synchronized (LOCK) {

            int newId = getNextMatchId();

            match.setMatchId(newId);

            matches.put(newId, match);

            totalEntriesAdded++;

            saveMatches();

            return totalEntriesAdded;
        }
    }


    /**
     * Determines the next available match ID.
     * (Only called from storeMatch, which holds the lock.)
     *
     * @return next match ID
     */
    private static int getNextMatchId() {

        int highestId = 0;

        for (Integer id : matches.keySet()) {

            if (id > highestId) {
                highestId = id;
            }
        }

        return highestId + 1;
    }


    /**
     * Displays all saved Valorant matches.
     * A3: shows the KDA, ACS and KAST calculated by MatchBO and no longer
     * reloads the file (that would wipe matches the other thread just added).
     */
    public static void viewMatches() {

        System.out.println("\n--- Valorant Match History ---");

        synchronized (LOCK) {

            if (matches.isEmpty()) {

                System.out.println("There are no matches saved.");

            } else {

                for (Match match : matches.values()) {

                    System.out.println(describeMatch(match));
                    System.out.println("------------------------------");
                }
            }
        }
    }


    /**
     * A3: match details plus the calculations from MatchBO.
     *
     * @param match the match to describe
     * @return text with the match and its KDA, ACS and KAST
     */
    private static String describeMatch(Match match) {

        return match
                + String.format("%nKDA: %.2f", MatchBO.calculate(match))
                + String.format("%nACS: %.2f", MatchBO.calculateACS(match))
                + String.format("%nKAST: %.2f%%", MatchBO.calculateKAST(match));
    }


    // ------------------------------------------------------------------
    // A3: JOptionPane versions of the menu, add and view
    // ------------------------------------------------------------------

    /**
     * JOptionPane menu loop. Same options as the console menu.
     */
    public static void menuGUI() {

        String choice;

        do {

            choice = JOptionPane.showInputDialog(MENU);

            // Cancel or closing the dialog returns null: treat it as exit
            if (choice == null) {
                choice = EXIT;
            }

            switch (choice.trim().toUpperCase()) {

                case "A":
                    addMatchGUI();
                    break;

                case "V":
                    viewMatchesGUI();
                    break;

                case EXIT:
                    JOptionPane.showMessageDialog(null, MESSAGE_EXIT);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, MESSAGE_ERROR);
            }

        } while (!choice.trim().equalsIgnoreCase(EXIT));
    }

    /**
     * Adds a new match using JOptionPane dialogs.
     */
    public static void addMatchGUI() {

        Match match = new Match();

        try {

            if (!match.getInformationGUI()) {
                return; // user pressed Cancel
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, MESSAGE_BAD_NUMBER);
            return;
        }

        int total = storeMatch(match);

        JOptionPane.showMessageDialog(null,
                MESSAGE_SUCCESS + "\n"
                        + "Matches added by both threads so far: " + total);
    }

    /**
     * Shows all saved matches in one JOptionPane message.
     */
    public static void viewMatchesGUI() {

        StringBuilder history = new StringBuilder();

        synchronized (LOCK) {

            if (matches.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There are no matches saved.");
                return;
            }

            for (Match match : matches.values()) {

                history.append(describeMatch(match)).append("\n");
                history.append("------------------------------\n");
            }
        }

        JOptionPane.showMessageDialog(null,
                "Valorant Match History\n\n" + history);
    }


    /**
     * Saves all matches to the JSON file.
     * A3: synchronized so both threads never write the file at once.
     */
    public static void saveMatches() {

        synchronized (LOCK) {

            try (FileWriter writer = new FileWriter(PATH_NAME)) {

                gson.toJson(matches, writer);

            } catch (IOException e) {

                System.out.println(
                        "Unable to save match data: " + e.getMessage()
                );
            }
        }
    }


    /**
     * Loads matches from the JSON file.
     * A3: now only called once, from initialize(), before the threads start.
     */
    public static void loadMatches() {

        File file = new File(PATH_NAME);

        if (!file.exists() || file.length() == 0) {
            return;
        }

        synchronized (LOCK) {

            try (FileReader reader = new FileReader(file)) {

                Type mapType =
                        new TypeToken<HashMap<Integer, Match>>() {
                        }.getType();

                HashMap<Integer, Match> savedMatches =
                        gson.fromJson(reader, mapType);

                if (savedMatches != null) {

                    matches.clear();
                    matches.putAll(savedMatches);
                }

            } catch (IOException e) {

                System.out.println(
                        "Unable to load match data: " + e.getMessage()
                );
            }
        }
    }


    /**
     * Creates the required directory and JSON file if necessary.
     * Existing match information is loaded when the program starts.
     */
    public static void initialize() {

        Path filePath = Paths.get(PATH_NAME);

        Path directory = filePath.getParent();

        try {

            if (directory != null && !Files.exists(directory)) {

                Files.createDirectories(directory);

                System.out.println(
                        "Created directory: " + directory
                );
            }

            if (Files.exists(filePath)) {

                loadMatches();

                System.out.println("Match data loaded.");

            } else {

                saveMatches();

                System.out.println(
                        "Created new data file: " + PATH_NAME
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Unable to initialize application: "
                            + e.getMessage()
            );
        }
    }
}
