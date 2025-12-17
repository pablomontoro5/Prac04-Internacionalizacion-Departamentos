package data;

import model.Departamento;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class DataStorage {

    private static final String FILE = "departamentos.tsv";
    private static final String LAST_ID_FILE = "last_id.txt";

    // ----------------------------------------------------
    // GUARDAR TODOS LOS DEPARTAMENTOS EN TSV
    // ----------------------------------------------------
    public static void save(List<Departamento> lista) {

        // Ordenar antes de guardar
        lista.sort(Comparator.comparingInt(Departamento::getId));

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {

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
        File f = new File(FILE);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] p = linea.split("\t");

                if (p.length < 3) continue;

                int id = Integer.parseInt(p[0].trim());
                String nombre = p[1].trim();
                String localidad = p[2].trim();

                // Crear usando el constructor existente
                Departamento d = new Departamento(nombre, localidad);

                // Cambiar el ID mediante reflection
                java.lang.reflect.Field field = Departamento.class.getDeclaredField("id");
                field.setAccessible(true);
                field.setInt(d, id);

                lista.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Actualizar el ID persistente
        int max = lista.stream().mapToInt(Departamento::getId).max().orElse(0);
        saveLastId(max);
    }

    // ----------------------------------------------------
    // CARGAR ÚLTIMO ID
    // ----------------------------------------------------
    public static int loadLastId() {
        File f = new File(LAST_ID_FILE);
        if (!f.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea = br.readLine();
            if (linea != null)
                return Integer.parseInt(linea.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ----------------------------------------------------
    // GUARDAR ÚLTIMO ID
    // ----------------------------------------------------
    public static void saveLastId(int id) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LAST_ID_FILE))) {
            pw.println(id);
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
