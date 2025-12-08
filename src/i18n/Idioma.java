package i18n;

import javax.swing.*;
import java.util.List;

public class Idioma {

    private String codigo;            // "es", "en", etc
    private List<String> cadenas;     // todas las cadenas del idioma
    private List<ImageIcon> imagenes; // imágenes internacionalizadas

    public Idioma(String codigo, List<String> cadenas, List<ImageIcon> imagenes) {
        this.codigo = codigo;
        this.cadenas = cadenas;
        this.imagenes = imagenes;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCadena(int index) {
        return cadenas.get(index);
    }

    public ImageIcon getImagen(int index) {
        return imagenes.get(index);
    }

    public int getNumCadenas() {
        return cadenas.size();
    }

    public int getNumImagenes() {
        return imagenes.size();
    }
}
