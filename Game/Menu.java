package Game;

import java.util.Scanner;

public class Menu {

    // esto es para hacer pruebas,
    // voy directamente al MenuCreator
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! For quit, type 'q'.");
        while (true) {
            System.out.println("--------------------");
            System.out.println("Creator or Player? (c/p)");
            System.out.print(">");
            String answer = scanner.nextLine();
            boolean isCreator = answer.equalsIgnoreCase("c");
            boolean isPlayer = answer.equalsIgnoreCase("p");
            if (isCreator || isPlayer) {
                if (isCreator) {
                    MenuCreator.createGame(scanner);
                } else if (isPlayer) {
                    MenuPlayer.createPlayer(scanner);
                    MenuPlayer.printGamesList();

                    System.out.println("Choose game name:");
                    System.out.print(">");
                    String creatorName = scanner.nextLine();
                    MenuPlayer.chooseGame(creatorName);
                }
            } else if (answer.equalsIgnoreCase("q")) {
                break;
            }
        }
        scanner.close();
    }
}
