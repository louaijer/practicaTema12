package Game;

import java.io.*;

public class ExceptionsHandler {

    private static void writeLog(String error) {
        File errorLog = new File("\\error.log");
        try {
            FileReader fr = new FileReader(errorLog);
            BufferedReader br = new BufferedReader(fr);
            String text = br.lines().toString();
            br.close();
            fr.close();

            FileWriter fw = new FileWriter(errorLog);
            fw.write(error + "\n" + text);
            fw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void handleKeywordNotEntered() {

    }

    public static void handleNoWordsToGuess() {

    }

    public static void handleExistingCreatorName() {

    }

    public static void handleEarlyGiving() {

    }

    public static void handleWrongText() {

    }
}
