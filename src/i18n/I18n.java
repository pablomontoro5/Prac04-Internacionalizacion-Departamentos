package i18n;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class I18n {

    private static final List<Idioma> idiomas = new ArrayList<>();
    private static Idioma idiomaActual;

    // ==============================================
    // CARGAR ARCHIVO idiomas.txt
    // ==============================================
    public static void cargarIdiomas() {

        try (InputStream is = I18n.class.getResourceAsStream("/idiomas.txt")) {

            if (is == null)
                throw new RuntimeException("❌ No se pudo cargar idiomas.txt desde resources");

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            // 1) Número de idiomas
            int numIdiomas = Integer.parseInt(br.readLine().trim());
            System.out.println("Cargando " + numIdiomas + " idiomas...");

            // 2) Leer cada idioma
            for (int i = 0; i < numIdiomas; i++) {

                // Código
                String codigo = br.readLine().trim();

                // Cadenas
                int numCadenas = Integer.parseInt(br.readLine().trim());
                List<String> cadenas = new ArrayList<>();

                for (int j = 0; j < numCadenas; j++)
                    cadenas.add(br.readLine().trim());

                // Imágenes
                int numImgs = Integer.parseInt(br.readLine().trim());
                List<ImageIcon> imgs = new ArrayList<>();

                for (int k = 0; k < numImgs; k++) {
                    String ruta = br.readLine().trim();
                    ImageIcon icon = cargarIcono(ruta);
                    imgs.add(icon);
                }

                idiomas.add(new Idioma(codigo, cadenas, imgs));
            }

            idiomaActual = idiomas.get(0); // idioma por defecto
            System.out.println("Idioma cargado por defecto: " + idiomaActual.getCodigo());

        } catch (Exception e) {
            throw new RuntimeException("❌ Error cargando idiomas: " + e.getMessage(), e);
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
