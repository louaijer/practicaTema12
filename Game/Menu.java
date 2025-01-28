package Game;

import Game.exceptions.ExceptionsHandler;

import java.util.Scanner;

public class Menu {

    private static Scanner scanner;

    // esto es para hacer pruebas,
    // voy directamente al MenuCreator
    public static void main(String[] args){
        scanner = new Scanner(System.in);
        System.out.println("Welcome! For quit, type 'q'.");
        while (true) {
            System.out.println("--------------------");
            String answer = getInput(Menu.class, "Creator or Player? (c/p)\n>");
            if (answer == null) {
                break;
            }
            boolean isCreator = answer.equalsIgnoreCase("c");
            boolean isPlayer = answer.equalsIgnoreCase("p");
            if (isCreator || isPlayer) {
                int resultCode;
                if (isCreator) {
                    resultCode = MenuCreator.createGame();
                    if(resultCode == 0) { break; }
                } else if (isPlayer) {
                    resultCode = MenuPlayer.createPlayer();
                    if(resultCode == 0) { break; }
                    MenuPlayer.printGamesList();

                    resultCode = MenuPlayer.chooseGame();
                    if(resultCode == 0) { break; }
                }
            } else if (answer.equalsIgnoreCase("q")) {
                break;
            }
        }
        scanner.close();
    }

    protected static String getInput(Class c, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Bye-bye!");
                return null;
            }
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
            ExceptionsHandler.writeLog(c, "Empty input.");
        }
    }
}
