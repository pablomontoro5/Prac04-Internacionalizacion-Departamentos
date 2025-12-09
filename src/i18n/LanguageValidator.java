package i18n;

import javax.swing.*;

public class LanguageValidator {

    public static void validate() {
        for (Idioma lang : I18n.getIdiomasList()) {

            int expectedStrings = 28;
            int expectedImages = 2;

            if (lang.getNumCadenas() != expectedStrings) {
                JOptionPane.showMessageDialog(null,
                        "Error en idioma " + lang.getCodigo() +
                                "\nCadenas esperadas: " + expectedStrings +
                                "\nCadenas encontradas: " + lang.getNumCadenas(),
                        "Error i18n", JOptionPane.ERROR_MESSAGE);
            }

            if (lang.getNumImagenes() != expectedImages) {
                JOptionPane.showMessageDialog(null,
                        "Error en idioma " + lang.getCodigo() +
                                "\nImágenes esperadas: " + expectedImages +
                                "\nImágenes encontradas: " + lang.getNumImagenes(),
                        "Error i18n", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
