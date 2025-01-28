import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que representa la funcionalidad del menú para el jugador.
 */
public class MenuPlayer {

    private Player player;
    private List<String> guessedWords = new ArrayList<>();
    private List<String> missedWords = new ArrayList<>();

    /**
     * Crea un nuevo jugador solicitando su nombre.
     */
    public void createPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el nombre del jugador: ");
        String playerName = scanner.nextLine();
        this.player = new Player(playerName);
        System.out.println("Jugador creado: " + player);
    }

    /**
     * Muestra la lista de juegos disponibles escaneando el directorio de juegos.
     */
    public void printGamesList() {
        File gameDirectory = new File("./juegoAdivinar/");
        if (!gameDirectory.exists() || !gameDirectory.isDirectory()) {
            System.out.println("No hay juegos disponibles.");
            return;
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
    }

    /**
     * Permite al jugador elegir un juego por el nombre del creador y comienza el juego.
     * @param creatorName el nombre del creador del juego.
     */
    public void chooseGame(String creatorName) {
        File gameFile = new File("./juegoAdivinar/" + creatorName + "/juego_de_" + creatorName + ".txt");
        if (!gameFile.exists()) {
            System.out.println("El juego del creador " + creatorName + " no existe.");
            return;
        }

        System.out.println("Juego elegido: " + gameFile.getName());

        try (Scanner fileScanner = new Scanner(gameFile)) {
            String surrenderWord = ""; // Palabra para rendirse
            List<String> wordsToGuess = new ArrayList<>(); // Palabras que hay que adivinar

            // Lectura del archivo del juego
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("Palabra finalizar:")) {
                    surrenderWord = line.split(": ")[1].trim(); // Extraer la palabra para rendirse
                } else if (line.startsWith("Palabras que hay que acertar:")) {
                    String[] words = line.split(": ")[1].trim().split(" "); // Extraer las palabras para adivinar
                    wordsToGuess.addAll(List.of(words));
                }
            }

            if (surrenderWord.isEmpty() || wordsToGuess.isEmpty()) {
                System.out.println("Formato de archivo de juego inválido.");
                return;
            }

            // Comienzo del juego
            Scanner inputScanner = new Scanner(System.in);
            List<String> remainingWords = new ArrayList<>(wordsToGuess); // Palabras restantes para adivinar

            System.out.println("¡El juego ha comenzado! Introduce palabras para adivinar. Para rendirte, escribe: " + surrenderWord);

            while (!remainingWords.isEmpty()) {
                System.out.print("Introduce palabras separadas por espacios: ");
                String input = inputScanner.nextLine();

                if (input.equalsIgnoreCase(surrenderWord)) {
                    System.out.println("¡Te has rendido!");
                    break;
                }

                String[] enteredWords = input.split(" ");
                for (String word : enteredWords) {
                    if (remainingWords.contains(word)) {
                        guessedWords.add(word); // Añadir palabra acertada
                        remainingWords.remove(word); // Eliminar de las palabras restantes
                        System.out.println("¡Correcto! Has acertado: " + word);
                    } else {
                        missedWords.add(word); // Añadir palabra fallada
                        System.out.println("¡Incorrecto! Has fallado: " + word);
                    }
                }

                System.out.println("Palabras restantes para adivinar: " + remainingWords);
            }

            if (remainingWords.isEmpty()) {
                System.out.println("¡Felicidades! ¡Has adivinado todas las palabras!");
            }

            // Guardar los resultados del juego
            saveGameResults(creatorName, guessedWords, missedWords);

        } catch (IOException e) {
            System.out.println("Se produjo un error al leer el archivo del juego.");
        }
    }

    /**
     * Guarda los resultados del juego en un archivo.
     * @param creatorName el nombre del creador del juego.
     * @param guessedWords la lista de palabras acertadas.
     * @param missedWords la lista de palabras falladas.
     */
    private void saveGameResults(String creatorName, List<String> guessedWords, List<String> missedWords) {
        File progressFile = new File("./juegoAdivinar/" + creatorName + "/partidas/" + player.getName() + ".txt");

        try {
            if (!progressFile.getParentFile().exists()) {
                progressFile.getParentFile().mkdirs(); // Crear directorio si no existe
            }
            FileWriter writer = new FileWriter(progressFile, true);
            writer.write("Palabras acertadas: " + guessedWords + "
");
            writer.write("Palabras falladas: " + missedWords + "
");
            writer.close();
            System.out.println("Progreso guardado para el jugador " + player.getName() + ".");
        } catch (IOException e) {
            System.out.println("Se produjo un error al guardar el progreso.");
        }
    }

    /**
     * Añade una palabra a la lista de palabras acertadas.
     * @param word la palabra acertada.
     */
    public void addGuessedWord(String word) {
        guessedWords.add(word);
    }

    /**
     * Añade una palabra a la lista de palabras falladas.
     * @param word la palabra fallada.
     */
    public void addMissedWord(String word) {
        missedWords.add(word);
    }
}
