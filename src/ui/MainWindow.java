package ui;

import javax.swing.*;
import java.awt.event.*;
import i18n.I18n;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle(I18n.t(0));  // cadena 0 = título de la aplicación
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearMenu();

        JLabel lbl = new JLabel(I18n.t(0), SwingConstants.CENTER);
        add(lbl);

        setVisible(true);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        // ------------------------
        // MENÚ ARCHIVO
        // ------------------------
        JMenu menuArchivo = new JMenu(I18n.t(10));     // "Archivo"
        JMenuItem itemSalir = new JMenuItem(I18n.t(11)); // "Salir"

        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemSalir);


        // ------------------------
        // MENÚ OPERACIONES
        // ------------------------
        JMenu menuOperaciones = new JMenu(I18n.t(12));  // "Operaciones"

        JMenuItem itemAlta = new JMenuItem(I18n.t(3));     // Alta
        JMenuItem itemBaja = new JMenuItem(I18n.t(4));     // Baja
        JMenuItem itemConsulta = new JMenuItem(I18n.t(5)); // Consulta
        JMenuItem itemMod = new JMenuItem(I18n.t(6));      // Modificación

        // (Aquí después abriremos ventanas específicas)
        itemAlta.addActionListener(e -> System.out.println("Ventana Alta"));
        itemBaja.addActionListener(e -> System.out.println("Ventana Baja"));
        itemConsulta.addActionListener(e -> System.out.println("Ventana Consulta"));
        itemMod.addActionListener(e -> System.out.println("Ventana Modificación"));

        menuOperaciones.add(itemAlta);
        menuOperaciones.add(itemBaja);
        menuOperaciones.add(itemConsulta);
        menuOperaciones.add(itemMod);


        // ------------------------
        // MENÚ IDIOMA
        // ------------------------
        // ------------------------
// MENÚ IDIOMA
// ------------------------
        JMenu menuIdioma = new JMenu(I18n.t(13)); // "Idioma"

        JMenuItem itemEs = new JMenuItem(I18n.t(14)); // "Español"
        JMenuItem itemEn = new JMenuItem(I18n.t(15)); // "Inglés"

        itemEs.addActionListener(e -> cambiarIdioma("es"));
        itemEn.addActionListener(e -> cambiarIdioma("en"));

        menuIdioma.add(itemEs);
        menuIdioma.add(itemEn);



        // ------------------------
        // Añadir menús a la barra
        // ------------------------
        menuBar.add(menuArchivo);
        menuBar.add(menuOperaciones);
        menuBar.add(menuIdioma);

        setJMenuBar(menuBar);
    }

    private void cambiarIdioma(String codigo) {
        I18n.setIdioma(codigo);

        // Refrescar la ventana entera
        dispose();
        new MainWindow();
    }
}