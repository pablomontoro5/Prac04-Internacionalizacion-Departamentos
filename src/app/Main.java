package app;

import i18n.I18n;
import ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        System.out.println(">> Intentando cargar idiomas desde: " +
                I18n.class.getResource("/idiomas.txt"));

        I18n.cargarIdiomas(); // ← carga los idiomas

        new MainWindow();     // ← abre la ventana principal
    }
}
