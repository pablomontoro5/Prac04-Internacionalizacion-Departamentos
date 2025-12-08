package i18n;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class I18n {

    private static List<Idioma> idiomas = new ArrayList<>();
    private static Idioma idiomaActual;

    public static void cargarIdiomas() {
        try {
            InputStream is = I18n.class.getResourceAsStream("/idiomas.txt");
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo idiomas.txt");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*
            // ==========================================================
            // DEPURACIÓN PROFUNDA: muestra caracteres con sus códigos
            // ==========================================================
            System.out.println("=== DEPURANDO LÍNEAS (ASCII) ===");
            String lineaDebug;
            int n = 1;
            while ((lineaDebug = br.readLine()) != null) {
                System.out.print("Línea " + n + ": ");
                if (lineaDebug.isEmpty()) {
                    System.out.print("[VACÍA]");
                }
                for (char c : lineaDebug.toCharArray()) {
                    System.out.print("[" + c + "=" + (int) c + "] ");
                }
                System.out.println();
                n++;
            }
            System.out.println("================================");

            // Reabrir para la lectura REAL

 */
            is = I18n.class.getResourceAsStream("/idiomas.txt");
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            // -----------------------------------------------------------
            // Empieza la carga normal
            // -----------------------------------------------------------

            int numIdiomas = Integer.parseInt(br.readLine().trim());
            System.out.println("numIdiomas: " + numIdiomas);

            for (int i = 0; i < numIdiomas; i++) {
                String codigo = br.readLine().trim();
                System.out.println("codigo: [" + codigo + "]");

                int numCadenas = Integer.parseInt(br.readLine().trim());
                System.out.println("numCadenas: [" + numCadenas + "]");

                List<String> cadenas = new ArrayList<>();
                for (int j = 0; j < numCadenas; j++) {
                    String c = br.readLine().trim();
                    cadenas.add(c);
                    System.out.println("cadena " + j + ": [" + c + "]");
                }

                int numImagenes = Integer.parseInt(br.readLine().trim());
                System.out.println("numImagenes: [" + numImagenes + "]");

                List<ImageIcon> imagenes = new ArrayList<>();
                for (int k = 0; k < numImagenes; k++) {
                    String ruta = br.readLine().trim();
                    System.out.println("rutaImagen " + k + ": [" + ruta + "]");
                    imagenes.add(new ImageIcon(I18n.class.getResource(ruta)));
                }

                idiomas.add(new Idioma(codigo, cadenas, imagenes));
            }

            idiomaActual = idiomas.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error cargando idiomas: " + e.getMessage());
        }
    }


    // Cambiar idioma por código, ej: "es"
    public static void setIdioma(String codigo) {
        for (Idioma id : idiomas) {
            if (id.getCodigo().equalsIgnoreCase(codigo)) {
                idiomaActual = id;
                return;
            }
        }
        throw new RuntimeException("Idioma no encontrado: " + codigo);
    }

    // Obtener una cadena por índice
    public static String t(int index) {
        return idiomaActual.getCadena(index);
    }

    // Obtener una imagen por índice
    public static ImageIcon img(int index) {
        return idiomaActual.getImagen(index);
    }

    // Obtener lista de códigos de idioma (para menú)
    public static List<String> getCodigos() {
        List<String> codigos = new ArrayList<>();
        for (Idioma id : idiomas) {
            codigos.add(id.getCodigo());
        }
        return codigos;
    }

    public static String getIdiomaActual() {
        return idiomaActual.getCodigo();
    }
}
