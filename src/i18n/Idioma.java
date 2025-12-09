package i18n;

import javax.swing.*;
import java.util.List;

public class Idioma {

    private final String codigo;            // "es", "en", etc.
    private final List<String> cadenas;     // cadenas traducidas
    private final List<ImageIcon> imagenes; // imágenes del idioma

    public Idioma(String codigo, List<String> cadenas, List<ImageIcon> imagenes) {
        this.codigo = codigo;
        this.cadenas = cadenas;
        this.imagenes = imagenes;
    }

    public String getCodigo() {
        return codigo;
    }

    // -----------------------------------------------------
    // DEVOLVER CADENA DE FORMA SEGURA
    // -----------------------------------------------------
    public String getCadena(int index) {
        if (index < 0 || index >= cadenas.size()) {
            System.err.println("[WARN] Cadena inexistente en idioma " + codigo + " index=" + index);
            return "??";  // evita crash y muestra aviso visible
        }
        return cadenas.get(index);
    }

    // -----------------------------------------------------
    // DEVOLVER IMAGEN DE FORMA SEGURA
    // -----------------------------------------------------
    public ImageIcon getImagen(int index) {
        if (index < 0 || index >= imagenes.size()) {
            System.err.println("[WARN] Imagen inexistente en idioma " + codigo + " index=" + index);
            return null;
        }
        return imagenes.get(index);
    }

    public int getNumCadenas() {
        return cadenas.size();
    }

    public int getNumImagenes() {
        return imagenes.size();
    }
}
