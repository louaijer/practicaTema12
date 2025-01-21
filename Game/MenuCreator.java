package Game;

import java.io.*;
import java.util.Scanner;

public class MenuCreator {

    public static void createGame(Scanner scanner) {
        // show menu
        System.out.println("Create a game!");
        System.out.print("enter your name: ");
        String creatorName = scanner.nextLine();

        System.out.print("please enter the CLAVE word: ");
        String palabraClave = scanner.nextLine();

        System.out.print("please enter the adivinar words: ");
        String palabrasAdivinar = scanner.nextLine();

        //aqui, teneis que llamar a funcion para escribir fichero
        writeToFile(creatorName, palabraClave, palabrasAdivinar);
        System.out.println("game data saved");
    }

    public static void writeToFile(String creatorName, String palabraClave, String palabrasAdivinar) {
        File ficheroEscritura= new File("juego_de_"+ creatorName + ".txt");
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


