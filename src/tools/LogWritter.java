package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LogWritter {
    public final static String LOG_PATH = "src/data/logs.txt";  
    
    public static void writeLog(String errorMess){
       File logFile = new File(LOG_PATH);
        File logFather = logFile.getParentFile();
        if (logFather != null && !logFather.exists()) {
           logFather.mkdirs();
        }
        
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_PATH, true))) {
            bw.write("[" + time + "] " + errorMess);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
 
}
