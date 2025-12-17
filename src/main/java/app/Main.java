package app;

import i18n.I18n;
import ui.MainWindow;
import data.Data;
import util.Logger;

import javax.swing.SwingUtilities;

// Opcional: Modo oscuro
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {

    public static void main(String[] args) {

        // ======================================================
        // 1. ACTIVAR MODO OSCURO (si falla, no rompe la app)
        // ======================================================
        try {
            FlatDarkLaf.setup();
            Logger.log("FlatLaf Dark Mode cargado correctamente");
        } catch (Exception e) {
            Logger.log("Error cargando FlatLaf. Continuando con Look&Feel por defecto.");
        }

        Logger.log("Aplicación iniciando...");

        // ======================================================
        // 2. CARGAR DATOS DEL SISTEMA
        // ======================================================

        // --- Cargar listado de departamentos ---
        Data.load();
        Logger.log("Departamentos cargados desde archivo");

        // --- Cargar idiomas ---
        I18n.cargarIdiomas();
        I18n.autoDetect();
        Logger.log("Idiomas cargados correctamente");

        // ======================================================
        // 3. LANZAR UI (siempre desde EDT)
        // ======================================================
        SwingUtilities.invokeLater(() -> {
            new MainWindow();
            Logger.log("Ventana principal iniciada");
        });
    }
}
