package data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IdStorage {

    private static final Path FILE = Paths.get("id.txt");

    public static int loadLastId() {
        try {
            if (!Files.exists(FILE)) return 0;
            String s = Files.readString(FILE).trim();
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void saveLastId(int lastId) {
        try {
            Files.writeString(FILE, String.valueOf(lastId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

