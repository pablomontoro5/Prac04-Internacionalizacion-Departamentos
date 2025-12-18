package data;

import model.Departamento;
import util.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

public class Data {


    private static final Path BASE_DIR =
            Paths.get(System.getProperty("user.home"), ".departamentosApp");

    private static final Path DATA_FILE = BASE_DIR.resolve("departamentos.tsv");
    private static final Path BACKUP_FILE = BASE_DIR.resolve("departamentos_backup.tsv");
    private static final Path ID_FILE = BASE_DIR.resolve("last_id.txt");

    private static final List<Departamento> lista = new ArrayList<>();

    static {
        try {
            Files.createDirectories(BASE_DIR);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de datos", e);
        }
    }

        // --------------------------------------------------
    // CARGAR DESDE ARCHIVO (solo se llama al iniciar)
    // --------------------------------------------------
        public static void load() {
            if (!Files.exists(DATA_FILE)) return;

            int maxId = 0;

            try (BufferedReader br = Files.newBufferedReader(DATA_FILE, StandardCharsets.UTF_8)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.isBlank()) continue;

                    String[] p = linea.split("\t", -1);
                    if (p.length < 3) continue;

                    int id = Integer.parseInt(p[0].trim());
                    String nombre = p[1].trim();
                    String localidad = p[2].trim();

                    Departamento d = new Departamento(nombre, localidad);
                    d.setId(id);
                    lista.add(d);

                    if (id > maxId) maxId = id;
                }

                Departamento.setContador(maxId + 1);

                // opcional: también persistes el último id para otras partes
                saveLastId(maxId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        // --------------------------------------------------
    // GUARDAR A ARCHIVO (UTF-8)
    // --------------------------------------------------
        private static void save() {
            // backup
            try {
                if (Files.exists(DATA_FILE)) {
                    Files.copy(DATA_FILE, BACKUP_FILE, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    Logger.log("Backup realizado");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // guardar
            try (BufferedWriter bw = Files.newBufferedWriter(DATA_FILE, StandardCharsets.UTF_8);
                 PrintWriter pw = new PrintWriter(bw)) {

                for (Departamento d : lista) {
                    pw.println(d.getId() + "\t" + d.getNombre() + "\t" + d.getLocalidad());
                }

                Logger.log("Archivo departamentos.tsv actualizado");

                // guarda también el último id
                int maxId = lista.stream().mapToInt(Departamento::getId).max().orElse(0);
                saveLastId(maxId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    // --------------------------------------------------
    // CRUD
    // --------------------------------------------------
    public static void addDepartamento(Departamento d) {
        lista.add(d);
        save();
    }

    public static boolean removeDepartamento(int id) {
        boolean removed = lista.removeIf(d -> d.getId() == id);
        if (removed) save();
        return removed;
    }


    public static List<Departamento> getDepartamentos() {
        return lista;
    }

    public static void update() {
        save();
    }
    public static List<Departamento> getLista() {
        return lista;  // el nombre REAL de tu lista en Data.java
    }
    // =====================================================
    //  BUSCAR DEPARTAMENTO POR ID
    // =====================================================
    public static Departamento buscarPorId(int id) {
        for (Departamento d : lista) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    // =====================================================
    //  ELIMINAR DEPARTAMENTO Y REORDENAR IDS
    // =====================================================
    public static void eliminarDepartamento(int id) {
        lista.removeIf(d -> d.getId() == id);
        save(); // esto ya guarda y actualiza last_id.txt dentro de BASE_DIR
    }



    public static int loadLastId() {
        if (!Files.exists(ID_FILE)) return 0;
        try {
            String s = Files.readString(ID_FILE, StandardCharsets.UTF_8).trim();
            return s.isEmpty() ? 0 : Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void saveLastId(int id) {
        try {
            Files.writeString(ID_FILE, String.valueOf(id), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean existeDepartamento(String nombre, String localidad) {
        for (Departamento d : lista) {
            if (d.getNombre().equalsIgnoreCase(nombre)
                    && d.getLocalidad().equalsIgnoreCase(localidad)) {
                return true;
            }
        }
        return false;
    }


}
