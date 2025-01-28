package Game.exceptions;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionsHandler {

    /**
     * Writes all error in error.log file.
     * @param c Writes the source of error.
     * @param error Gets message from this.
     */
    public static void writeLog(Class c, String error) {
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
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
            }
            if(!text.isEmpty()) {
                text.deleteCharAt(text.length() - 1);
            }
            br.close();
            fr.close();

            LocalDateTime currentTime = LocalDateTime.now();
            // Define a formatter to display the time in a human-readable format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // Format and print the current time
            String formattedTime = currentTime.format(formatter);

            FileWriter fw = new FileWriter(errorLog);
            fw.write(formattedTime + " [" + c.getName() + "] " + error + "\n" + text);
            fw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
