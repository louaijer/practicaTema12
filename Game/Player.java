package Game;

import Game.exceptions.ExistingCreatorNameException;
import Game.exceptions.PlayerCreatorNameConflictException;

import java.io.File;

/**
 * Represents a player in the game.
 */
public class Player {
    private String name;
    private int score;

    /**
     * Constructor for creating a player with a name.
     * @param name The name of the player.
     */
    public Player(String name) throws PlayerCreatorNameConflictException {
        File file = new File("./juegoAdivinar/" + name + "/juego_de_" + name + ".txt");
        if(file.exists()) {
            throw new PlayerCreatorNameConflictException();
        } else  {
            this.name = name;
            this.score = 0; // Initialize score to 0
        }
    }

    /**
     * Gets the name of the player.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     * @param name The new name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the player's score.
     * @return The current score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the player's score by adding points.
     * @param points The number of points to add.
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Provides a string representation of the player.
     * @return A string with the player's name and score.
     */
    @Override
    public String toString() {
        return "Player{name='" + name + "', score=" + score + "}";
    }
}
