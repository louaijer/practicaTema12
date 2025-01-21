package Game;

import java.util.Scanner;

public class Menu {

    // esto es para hacer pruebas,
    // voy directamente al MenuCreator
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Bienvenido! Para salir usa 'q'.");
        while (true) {
            System.out.println("--------------------");
            System.out.println("Creador o Jugador? (c/j)");
            System.out.print(">");
            String answer = input.nextLine();
            boolean isCreator = answer.equalsIgnoreCase("c");
            boolean isPlayer = answer.equalsIgnoreCase("j");
            if (isCreator || isPlayer) {
                if (isCreator) {
                    MenuCreator.createGame();
                } else if (isPlayer) {

                }
            } else if (answer.equalsIgnoreCase("q")) {
                break;
            }
        }
        input.close();
    }
}
