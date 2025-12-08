package i18n;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class I18n {

    private static List<Idioma> idiomas = new ArrayList<>();
    private static Idioma idiomaActual;

    public static void cargarIdiomas() {
        try {
            InputStream is = I18n.class.getResourceAsStream("/idiomas.txt");
            if (is == null) {
                throw new RuntimeException("No se encontró el archivo idiomas.txt");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Número de idiomas
            int numIdiomas = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < numIdiomas; i++) {

                // Código del idioma ("es", "en")
                String codigo = br.readLine().trim();

                // Número de cadenas
                int numCadenas = Integer.parseInt(br.readLine().trim());

                List<String> cadenas = new ArrayList<>();
                for (int c = 0; c < numCadenas; c++) {
                    cadenas.add(br.readLine());
                }

                // Número de imágenes
                int numImagenes = Integer.parseInt(br.readLine().trim());

                List<ImageIcon> imagenes = new ArrayList<>();
                for (int im = 0; im < numImagenes; im++) {
                    String ruta = br.readLine().trim();
                    ImageIcon img = new ImageIcon(I18n.class.getResource(ruta));
                    imagenes.add(img);
                }

                idiomas.add(new Idioma(codigo, cadenas, imagenes));
            }

            idiomaActual = idiomas.get(0); // primero por defecto

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
