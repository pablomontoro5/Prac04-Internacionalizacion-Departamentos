package data;

import model.Departamento;
import util.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private static final String FILE_NAME = "departamentos.tsv";
    private static final List<Departamento> lista = new ArrayList<>();

    // --------------------------------------------------
    // CARGAR DESDE ARCHIVO (solo se llama al iniciar)
    // --------------------------------------------------
    public static void load() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;

        int maxId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] p = linea.split("\t",-1);
                Logger.log("Cargados " + lista.size() + " departamentos desde archivo");
                if (p.length < 3) continue;

                int id = Integer.parseInt(p[0].trim());
                String nombre = p[1].trim();
                String localidad = p[2].trim();

                Departamento d = new Departamento(nombre, localidad);
                d.setId(id);                  // Restaurar ID
                lista.add(d);

                if (id > maxId) maxId = id;
            }

            // Actualizar contador para nuevos departamentos
            Departamento.setContador(maxId + 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // --------------------------------------------------
    // GUARDAR A ARCHIVO (UTF-8)
    // --------------------------------------------------
    private static void save() {

        // ---- COPIA DE SEGURIDAD ----
        try {
            File original = new File(FILE_NAME);
            File backup = new File("departamentos_backup.tsv");

            if (original.exists()) {
                try (InputStream in = new FileInputStream(original);
                     OutputStream out = new FileOutputStream(backup)) {
                    in.transferTo(out);
                }
                Logger.log("Backup realizado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---- GUARDAR ARCHIVO ----
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(FILE_NAME), StandardCharsets.UTF_8))) {

            for (Departamento d : lista) {
                pw.println(d.getId() + "\t" + d.getNombre() + "\t" + d.getLocalidad());
            }

            Logger.log("Archivo departamentos.tsv actualizado");

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

    public static void removeDepartamento(Departamento d) {
        lista.remove(d);
        save();
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

        // 1. Eliminar el departamento con ese id
        lista.removeIf(d -> d.getId() == id);

        // 2. Guardar la lista tal cual (sin tocar los demás IDs)
        DataStorage.save(lista);

        // 3. (Opcional pero recomendable) actualizar último ID al máximo id existente
        DataStorage.saveLastIdFromList(lista);
    }


    public static int loadLastId() {
        return IdStorage.loadLastId();
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
