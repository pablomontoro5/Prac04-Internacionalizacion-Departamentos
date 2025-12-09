package data;

import java.io.*;

public class IdStorage {

    private static final String FILE = "id.txt";

    public static int loadLastId() {
        try {
            File f = new File(FILE);
            if (!f.exists()) return 0;

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                return Integer.parseInt(br.readLine().trim());
            }

        } catch (Exception e) {
            return 0;
        }
    }

    public static void saveLastId(int id) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            pw.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
