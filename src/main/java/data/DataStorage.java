package data;

import model.Departamento;

import java.io.*;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DataStorage {

    private static final Path BASE_DIR =
            Paths.get(System.getProperty("user.home"), ".departamentosApp");

    private static final Path FILE = BASE_DIR.resolve("departamentos.tsv");
    private static final Path LAST_ID_FILE = BASE_DIR.resolve("last_id.txt");
    static {
        try {
            Files.createDirectories(BASE_DIR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // ----------------------------------------------------
    // GUARDAR TODOS LOS DEPARTAMENTOS EN TSV
    // ----------------------------------------------------
    public static void save(List<Departamento> lista) {
        lista.sort(Comparator.comparingInt(Departamento::getId));

        try (BufferedWriter bw = Files.newBufferedWriter(FILE, StandardCharsets.UTF_8);
             PrintWriter pw = new PrintWriter(bw)) {

            for (Departamento d : lista) {
                pw.println(d.getId() + "\t" + d.getNombre() + "\t" + d.getLocalidad());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // ----------------------------------------------------
    // CARGAR DESDE TSV
    // ----------------------------------------------------
    public static void load(List<Departamento> lista) {
        if (!Files.exists(FILE)) return;

        try (BufferedReader br = Files.newBufferedReader(FILE, StandardCharsets.UTF_8)) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] p = linea.split("\t");
                if (p.length < 3) continue;

                int id = Integer.parseInt(p[0].trim());
                String nombre = p[1].trim();
                String localidad = p[2].trim();

                Departamento d = new Departamento(nombre, localidad);

                java.lang.reflect.Field field = Departamento.class.getDeclaredField("id");
                field.setAccessible(true);
                field.setInt(d, id);

                lista.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int max = lista.stream().mapToInt(Departamento::getId).max().orElse(0);
        saveLastId(max);
    }


    // ----------------------------------------------------
    // CARGAR ÚLTIMO ID
    // ----------------------------------------------------
    public static int loadLastId() {
        if (!Files.exists(LAST_ID_FILE)) return 0;

        try {
            String linea = Files.readString(LAST_ID_FILE, StandardCharsets.UTF_8).trim();
            return linea.isEmpty() ? 0 : Integer.parseInt(linea);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    // ----------------------------------------------------
    // GUARDAR ÚLTIMO ID
    // ----------------------------------------------------
    public static void saveLastId(int id) {
        try {
            Files.writeString(LAST_ID_FILE, String.valueOf(id), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------
// BUSCAR DEPARTAMENTO POR ID
// ----------------------------------------------------
    public static Departamento buscarDepartamento(int id, List<Departamento> lista) {
        for (Departamento d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }
    public static void saveLastIdFromList(List<Departamento> lista) {
        int maxId = 0;
        for (Departamento d : lista) {
            if (d.getId() > maxId) maxId = d.getId();
        }
        IdStorage.saveLastId(maxId);
    }


}
