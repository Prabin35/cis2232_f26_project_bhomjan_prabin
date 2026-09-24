package ca.hccis.files;

import ca.hccis.files.entity.Match;
import ca.hccis.files.util.CisUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
 * CIS 2232 | Assignment 1
 *
 * @author Prabin Bhomjan
 * @since 09242026
 */
public class Controller {

    public static final String MENU =
            "A) Add Match\n" +
                    "V) View Matches\n" +
                    "X) Exit\n";

    public static final String MESSAGE_ERROR = "Invalid menu option.";
    public static final String MESSAGE_EXIT = "Goodbye!";
    public static final String MESSAGE_SUCCESS = "Match saved successfully.";

    public static final String EXIT = "X";

    private static final HashMap<Integer, Match> matches = new HashMap<>();

    private static final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public static final String PATH_NAME =
            "c:\\cis2232\\data_bhomjan_prabin.json";


    /**
     * Starts the Valorant Performance Tracker application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        initialize();

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

        match.getInformation();

        int newId = getNextMatchId();

        match.setMatchId(newId);

        matches.put(newId, match);

        saveMatches();

        System.out.println(MESSAGE_SUCCESS);
    }


    /**
     * Determines the next available match ID.
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
     */
    public static void viewMatches() {

        loadMatches();

        System.out.println("\n--- Valorant Match History ---");

        if (matches.isEmpty()) {

            System.out.println("There are no matches saved.");

        } else {

            for (Match match : matches.values()) {

                System.out.println(match);
                System.out.println("------------------------------");
            }
        }
    }


    /**
     * Saves all matches to the JSON file.
     */
    public static void saveMatches() {

        try (FileWriter writer = new FileWriter(PATH_NAME)) {

            gson.toJson(matches, writer);

        } catch (IOException e) {

            System.out.println(
                    "Unable to save match data: " + e.getMessage()
            );
        }
    }


    /**
     * Loads matches from the JSON file.
     */
    public static void loadMatches() {

        File file = new File(PATH_NAME);

        if (!file.exists() || file.length() == 0) {
            return;
        }

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