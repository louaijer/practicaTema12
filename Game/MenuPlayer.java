package Game;

import Game.exceptions.ExceptionsHandler;
import Game.exceptions.PlayerCreatorNameConflictException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the menu functionality for a player.
 */
public class MenuPlayer extends Menu {

    private static Player player;
    private static List<String> guessedWords = new ArrayList<>();
    private static List<String> missedWords = new ArrayList<>();

    /**
     * Creates a new player instance by asking for the player's name.
     */
    public static int createPlayer() {
        while (true) {
            String playerName = getInput(MenuPlayer.class, "Enter player name: ");
            if (playerName == null) {
                return 0;
            }
            try {
                player = new Player(playerName);
            } catch (PlayerCreatorNameConflictException e) {
                System.out.println(e.getMessage());
                ExceptionsHandler.writeLog(MenuPlayer.class, e.getMessage());
                continue;
            }
            break;
        }
        System.out.println("Player created: " + player);
        return 1;
    }

    /**
     * Prints the list of available games by scanning the game directory.
     */
    public static void printGamesList() {
        File gameDirectory = new File("./juegoAdivinar/");
        if (!gameDirectory.exists() || !gameDirectory.isDirectory()) {
            System.out.println("No games available.");
            return;
        }

        File[] gameFiles = gameDirectory.listFiles();
        System.out.println("Available games: ");
        if (gameFiles != null) {
            for (File file : gameFiles) {
                if (file.isDirectory()) {
                    System.out.println("- " + file.getName());
                }
            }
        }
    }

    /**
     * Allows the player to choose a game by the creator's name.
     */
    public static int chooseGame() {
        String creatorName = getInput(MenuPlayer.class, "Choose game name: ");
        if (creatorName == null) {
            return 0;
        }
        File gameFile = new File("./juegoAdivinar/" + creatorName + "/juego_de_" + creatorName + ".txt");
        if (!gameFile.exists()) {
            System.out.println("Game by creator " + creatorName + " does not exist.");
            return 1;
        }

        System.out.println("Game chosen: " + gameFile.getName());
        // Further implementation to read game content can be added here.
        return 1;
    }

    /**
     * Finishes the current game by saving the player's progress to a file.
     */
    public static void finishCurrentGame() {
        if (player == null) {
            System.out.println("No player created. Please create a player first.");
            return;
        }

        String creatorName = getInput(MenuPlayer.class, "Enter game creator name to save progress: ");
        File progressFile = new File("./juegoAdivinar/" + creatorName + "/partidas/" + player.getName() + ".txt");

        try {
            if (!progressFile.getParentFile().exists()) {
                progressFile.getParentFile().mkdirs();
            }
            FileWriter writer = new FileWriter(progressFile, true);
            writer.write("Score: " + player.getScore() + "\n");
            writer.write("Guessed words: " + guessedWords + "\n");
            writer.write("Missed words: " + missedWords + "\n");
            writer.close();
            System.out.println("Progress saved for player " + player.getName() + ".");
        } catch (IOException e) {
            System.out.println("An error occurred while saving progress.");
        }
    }

    /**
     * Updates the list of guessed words.
     * @param word the word guessed correctly by the player.
     */
    public static void addGuessedWord(String word) {
        guessedWords.add(word);
    }

    /**
     * Updates the list of missed words.
     * @param word the word missed by the player.
     */
    public void addMissedWord(String word) {
        missedWords.add(word);
    }
}
