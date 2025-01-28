package Game;

import Game.exceptions.ExistingCreatorNameException;

import java.io.*;
import java.util.Scanner;

public class MenuCreator {

    public static void createGame(Scanner scanner) {
        // show menu
        System.out.println("Create a game!");

        Creator creator;
        while (true) {
            System.out.print("enter your name: ");
            String creatorName = scanner.nextLine();
            try {
                creator = new Creator(creatorName);
            } catch (ExistingCreatorNameException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }

        System.out.print("please enter the CLAVE word: ");
        String palabraClave = scanner.nextLine();

        System.out.print("please enter the adivinar words: ");
        String palabrasAdivinar = scanner.nextLine();

        //aqui, teneis que llamar a funcion para escribir fichero
        writeToFile(creator, palabraClave, palabrasAdivinar);
        System.out.println("game data saved");
    }

    public static void writeToFile(Creator creator, String palabraClave, String palabrasAdivinar) {
        File ficheroEscritura= new File("./juegoAdivinar/" + creator.getName() + "/juego_de_" + creator.getName() + ".txt");
        try {
            FileWriter fl = new FileWriter(ficheroEscritura, true);
            PrintWriter printWriter = new PrintWriter(fl);
            printWriter.println("palabra clave: " + palabraClave);
            printWriter.println("palabra adivinar: " + palabrasAdivinar);
            printWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


