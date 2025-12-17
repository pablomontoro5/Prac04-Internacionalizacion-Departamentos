package util;

import java.io.FileWriter;
import java.time.LocalDateTime;

public class Logger {

    private static final String LOG_FILE = "app.log";

    public static void log(String msg) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(LocalDateTime.now() + " - " + msg + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
