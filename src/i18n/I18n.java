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

            // -----------------------------
            // DEPURACIÓN: función auxiliar
            // -----------------------------
            java.util.function.Function<String, String> leerLinea = (descripcion) -> {
                try {
                    String linea = br.readLine();
                    System.out.println(descripcion + ": [" + linea + "]");
                    return linea;
                } catch (Exception e) {
                    throw new RuntimeException("Error leyendo " + descripcion + ": " + e.getMessage());
                }
            };

            // Leer número total de idiomas
            int numIdiomas = Integer.parseInt(leerLinea.apply("numIdiomas").trim());

            for (int i = 0; i < numIdiomas; i++) {

                // Código del idioma
                String codigo = leerLinea.apply("codigo").trim();

                // Número de cadenas
                int numCadenas = Integer.parseInt(leerLinea.apply("numCadenas").trim());

                // Leer cadenas
                List<String> cadenas = new ArrayList<>();
                for (int j = 0; j < numCadenas; j++) {
                    cadenas.add(leerLinea.apply("cadena " + j).trim());
                }

                // Número de imágenes
                int numImagenes = Integer.parseInt(leerLinea.apply("numImagenes").trim());

                // Leer imágenes
                List<ImageIcon> imagenes = new ArrayList<>();
                for (int k = 0; k < numImagenes; k++) {
                    String ruta = leerLinea.apply("rutaImagen " + k).trim();
                    imagenes.add(new ImageIcon(I18n.class.getResource(ruta)));
                }

                // Añadir idioma
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
