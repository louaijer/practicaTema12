package Game;

import Game.Player;
import Game.exceptions.ExceptionsHandler;
import Game.exceptions.PlayerCreatorNameConflictException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que representa la funcionalidad del menú para el jugador.
 */
public class MenuPlayer extends Menu {

    private static final int POINTS_FOR_GUESSED = 10;
    private static final int POINTS_FOR_MISSED = 2;

    private static Player player;
    private static List<String> guessedWords = new ArrayList<>();
    private static List<String> missedWords = new ArrayList<>();

    /**
     * Crea un nuevo jugador solicitando su nombre.
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
     * Muestra la lista de juegos disponibles escaneando el directorio de juegos.
     */
    public static int printGamesList() {
        File gameDirectory = new File("./juegoAdivinar/");
        if (!gameDirectory.exists() || !gameDirectory.isDirectory()) {
            System.out.println("No hay juegos disponibles.");
            return 0;
        }

        File[] gameFiles = gameDirectory.listFiles();
        System.out.println("Juegos disponibles:");
        if (gameFiles != null) {
            for (File file : gameFiles) {
                if (file.isDirectory()) {
                    System.out.println("- " + file.getName());
                }
            }
        }
        return 1;
    }

    /**
     * Permite al jugador elegir un juego por el nombre del creador y comienza el juego.
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

        System.out.println("Juego elegido: " + gameFile.getName());

        try (Scanner fileScanner = new Scanner(gameFile)) {
            String keyWord = ""; // Palabra clave
            List<String> wordsToGuess = new ArrayList<>(); // Palabras que hay que adivinar

            // Lectura del archivo del juego
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("Palabra finalizar: ")) {
                    keyWord = line.split(": ")[1].trim(); // Extraer la palabra para rendirse
                } else if (line.startsWith("Palabras que hay que acertar: ")) {
                    String[] words = line.split(": ")[1].trim().split(" "); // Extraer las palabras para adivinar
                    wordsToGuess.addAll(List.of(words));
                }
            }

            if (keyWord.isEmpty() || wordsToGuess.isEmpty()) {
                System.out.println("Formato de archivo de juego inválido.");
                return 1;
            }

            // Comienzo del juego
            List<String> remainingWords = new ArrayList<>(wordsToGuess); // Palabras restantes para adivinar

            System.out.println("¡El juego ha comenzado! Introduce palabras asociadas para adivinar. Palabra clave es: " + keyWord);

            while (!remainingWords.isEmpty()) {
                String input = getInput(MenuPlayer.class, "Introduce palabras separadas por espacios: ");
                if (input == null) {
                    return 0;
                }

                String[] enteredWords = input.split(" ");
                for (String word : enteredWords) {
                    if (remainingWords.contains(word)) {
                        guessedWords.add(word); // Añadir palabra acertada
                        remainingWords.remove(word); // Eliminar de las palabras restantes
                        player.addScore(POINTS_FOR_GUESSED);
                        System.out.print("¡Correcto! Has acertado: " + word + ", ");
                        System.out.println("+" + POINTS_FOR_GUESSED + " puntos");
                    } else if (guessedWords.contains(word)) {
                        System.out.println("Ya has adivinado esta palabra: " + word);
                    } else {
                        missedWords.add(word); // Añadir palabra fallada
                        player.addScore(-POINTS_FOR_MISSED);
                        System.out.print("¡Incorrecto! Has fallado: " + word + ", ");
                        System.out.println("-" + POINTS_FOR_MISSED + " puntos");
                    }
                }

                // Para deputar
//                System.out.println("Palabras restantes para adivinar: " + remainingWords);
            }
            System.out.println("¡Felicidades! ¡Has adivinado todas las palabras! Puntos totales: " + player.getScore());

            // Guardar los resultados del juego
            saveGameResults(creatorName, guessedWords, missedWords);

        } catch (IOException e) {
            System.out.println("Se produjo un error al leer el archivo del juego.");
        }

        return 1;
    }

    /**
     * Guarda los resultados del juego en un archivo.
     */
    private static void saveGameResults(String creatorName, List<String> guessedWords, List<String> missedWords) {
        if (player == null) {
            System.out.println("No player created. Please create a player first.");
            return;
        }

        File progressFile = new File("./juegoAdivinar/" + creatorName + "/partidas/" + player.getName() + ".txt");

        try {
            if (!progressFile.getParentFile().exists()) {
                progressFile.getParentFile().mkdirs(); // Crear directorio si no existe
            }
            FileWriter writer = new FileWriter(progressFile, true);
            writer.write("Palabras acertadas: " + guessedWords + " ");
            writer.write("Palabras falladas: " + missedWords + " ");
            writer.write("Puntos: " + player.getScore());
            writer.close();
            System.out.println("Progreso guardado para el jugador '" + player.getName() + "'.");
        } catch (IOException e) {
            System.out.println("Se produjo un error al guardar el progreso.");
        }
    }
}
