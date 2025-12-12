package i18n;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class I18n {

    private static final List<Idioma> idiomas = new ArrayList<>();
    private static Idioma idiomaActual;

    // ==============================================
    // CARGAR ARCHIVO idiomas.txt
    // ==============================================
    public static void cargarIdiomas() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                I18n.class.getResourceAsStream("/idiomas.txt"), StandardCharsets.UTF_8))) {

            int totalIdiomas = Integer.parseInt(br.readLine().trim());

            idiomas.clear();

            for (int i = 0; i < totalIdiomas; i++) {

                String codigo = br.readLine().trim();
                int numCadenas = Integer.parseInt(br.readLine().trim());

                List<String> cadenas = new ArrayList<>();
                for (int c = 0; c < numCadenas; c++)
                    cadenas.add(br.readLine());

                int numImagenes = Integer.parseInt(br.readLine().trim());
                List<ImageIcon> imagenes = new ArrayList<>();
                for (int j = 0; j < numImagenes; j++)
                    imagenes.add(new ImageIcon(I18n.class.getResource(br.readLine().trim())));

                idiomas.add(new Idioma(codigo, cadenas, imagenes));
            }

            idiomaActual = idiomas.get(0);

        } catch (Exception e) {
            throw new RuntimeException("Error cargando idiomas: " + e);
        }
    }


    // ==============================================
    // UTILIDAD: Cargar icono con comprobación
    // ==============================================
    private static ImageIcon cargarIcono(String ruta) {
        try {
            return new ImageIcon(I18n.class.getResource(ruta));
        } catch (Exception e) {
            System.err.println("⚠ No se pudo cargar la imagen: " + ruta);
            return null;
        }
    }

    // ==============================================
    // CAMBIO DE IDIOMA
    // ==============================================
    public static void setIdioma(String codigo) {
        for (Idioma id : idiomas) {
            if (id.getCodigo().equalsIgnoreCase(codigo)) {
                idiomaActual = id;
                return;
            }
        }
        throw new RuntimeException("Idioma no encontrado: " + codigo);
    }

    // ==============================================
    // OBTENER CADENA
    // ==============================================
    public static String t(int index) {
        return idiomaActual.getCadena(index);
    }

    // OBTENER IMAGEN
    public static ImageIcon img(int index) {
        return idiomaActual.getImagen(index);
    }

    // Obtener bandera de un idioma
    public static ImageIcon getImagenIdioma(String codigo) {
        for (Idioma id : idiomas) {
            if (id.getCodigo().equals(codigo))
                return id.getImagen(0);
        }
        return null;
    }

    public static ImageIcon getImagen(int index) {
        return idiomaActual.getImagen(index);
    }

    public static String getIdiomaActual() {
        return idiomaActual.getCodigo();
    }

    public static List<String> getCodigos() {
        List<String> lista = new ArrayList<>();
        for (Idioma id : idiomas)
            lista.add(id.getCodigo());
        return lista;
    }
    public static String t(Textos t) {
        return idiomaActual.getCadena(t.index);
    }

    public static void autoDetect() {
        String sys = System.getProperty("user.language");

        for (Idioma id : idiomas) {
            if (id.getCodigo().equalsIgnoreCase(sys)) {
                idiomaActual = id;
                return;
            }
        }

        // fallback español
        idiomaActual = idiomas.get(0);
    }
    public static List<Idioma> getIdiomasList() { return idiomas; }




}
