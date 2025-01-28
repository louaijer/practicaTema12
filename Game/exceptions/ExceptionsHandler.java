package Game.exceptions;

import java.io.*;

public class ExceptionsHandler {

    public static void writeLog(String error) {
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
}
