package Game;

import Game.exceptions.ExistingCreatorNameException;

import java.io.*;
import java.util.Scanner;

public class MenuCreator extends Menu {

    public static int createGame() {
        // show menu
        System.out.println("Create a game!");

        Creator creator;
        while (true) {
            String creatorName = getInput("enter your name: ");
            if (creatorName == null) {
                return 0;
            }
            try {
                creator = new Creator(creatorName);
            } catch (ExistingCreatorNameException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }

        String palabraClave = getInput("please enter the CLAVE word: ");
        if (palabraClave == null) {
            return 0;
        }
        String palabrasAdivinar = getInput("please enter the adivinar words: ");
        if (palabrasAdivinar == null) {
            return 0;
        }

        //aqui, teneis que llamar a funcion para escribir fichero
        writeToFile(creator, palabraClave, palabrasAdivinar);
        System.out.println("game data saved");
        return 1;
    }

    public static void writeToFile(Creator creator, String palabraClave, String palabrasAdivinar) {
        File dir = new File("./juegoAdivinar/" + creator.getName());
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File ficheroEscritura = new File(dir, "/juego_de_" + creator.getName() + ".txt");
        if(!ficheroEscritura.exists()) {
            try {
                ficheroEscritura.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            FileWriter fl = new FileWriter(ficheroEscritura, true);
            PrintWriter printWriter = new PrintWriter(fl);
            printWriter.println("palabra clave: " + palabraClave);
            printWriter.println("palabra adivinar: " + palabrasAdivinar);
            printWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }
}