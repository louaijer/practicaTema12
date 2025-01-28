package Game;

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
    public static void createPlayer() {
        String playerName = getInput("Enter player name: ");
        player = new Player(playerName);
        System.out.println("Player created: " + player);
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
     * @param creatorName the name of the creator of the game.
     */
    public static void chooseGame(String creatorName) {
        File gameFile = new File("./juegoAdivinar/" + creatorName + "/juego_de_" + creatorName + ".txt");
        if (!gameFile.exists()) {
            System.out.println("Game by creator " + creatorName + " does not exist.");
            return;
        }

        System.out.println("Game chosen: " + gameFile.getName());
        // Further implementation to read game content can be added here.
    }

    /**
     * Finishes the current game by saving the player's progress to a file.
     */
    public static void finishCurrentGame() {
        if (player == null) {
            System.out.println("No player created. Please create a player first.");
            return;
        }

        String creatorName = getInput("Enter game creator name to save progress: ");
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
