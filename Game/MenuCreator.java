package Game;

import java.io.*;
import java.util.Scanner;

public class MenuCreator {
    static Creator currentCreator;
    private static Console writer;

    public static void createCreator(String name) {
        currentCreator = new Creator(name);
    }

    public static void creatGame() {
        // show menu
        System.out.println("Create a game: ");
        System.out.println("enter your name: ");
        Scanner sc = new Scanner(System.in);
        String creatorName = sc.nextLine();

        createCreator(creatorName);

        System.out.println("please enter the CLAVE word: ");
        String palabraClave = sc.nextLine();

        System.out.println("please enter the adivinar words: ");
        String palabrasAdivinar = sc.nextLine();

        //aqui, teneis que llamar a funcion para escribir fichero
        writeToFile(creatorName, palabraClave, palabrasAdivinar);
        System.out.println("game data saved");
        sc.close();

    }

    public static void writeToFile(String creatorName, String palabraClave, String palabrasAdivinar) {
        File ficheroEscritura= new File("juego_de_"+ creatorName + ".txt");
        try {
            FileWriter fl = new FileWriter(ficheroEscritura, true);
            PrintWriter printWriter = new PrintWriter(fl);
            printWriter.println("palabra clave: " + palabraClave);
            printWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


