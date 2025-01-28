package Game.exceptions;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionsHandler {

    public static void writeLog(String error) {
        File errorLog = new File("./error.log");
        if (!errorLog.exists()) {
            try {
                errorLog.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            FileReader fr = new FileReader(errorLog);
            BufferedReader br = new BufferedReader(fr);
            String text = br.lines().toString();
            br.close();
            fr.close();

            LocalDateTime currentTime = LocalDateTime.now();
            // Define a formatter to display the time in a human-readable format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Format and print the current time
            String formattedTime = currentTime.format(formatter);

            FileWriter fw = new FileWriter(errorLog);
            fw.write(formattedTime + " " + error + "\n" + text);
            fw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
