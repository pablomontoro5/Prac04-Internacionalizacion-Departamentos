package ui;

import javax.swing.*;
import i18n.I18n;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle(I18n.t(0));  // "Aplicación de Departamentos"
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
        JMenu menuArchivo = new JMenu(I18n.t(10));     // Archivo
        JMenuItem itemSalir = new JMenuItem(I18n.t(11)); // Salir

        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);


        // ------------------------
// MENÚ OPERACIONES
// ------------------------
        JMenu menuOperaciones = new JMenu(I18n.t(12));  // Operaciones

        JMenuItem itemAlta = new JMenuItem(I18n.t(1));      // Alta
        JMenuItem itemBaja = new JMenuItem(I18n.t(2));      // Baja
        JMenuItem itemConsulta = new JMenuItem(I18n.t(3));  // Consulta
        JMenuItem itemMod = new JMenuItem(I18n.t(4));       // Modificación

// NUEVO → Listado de departamentos
        JMenuItem itemListado = new JMenuItem(I18n.t(25));  // "Listado Departamentos"

        itemAlta.addActionListener(e -> new AltaDepartamentoWindow(this).setVisible(true));
        itemBaja.addActionListener(e -> new BajaDepartamentoWindow(this).setVisible(true));
        itemConsulta.addActionListener(e -> new ConsultaDepartamentoWindow(this).setVisible(true));
        itemMod.addActionListener(e -> new ModificarDepartamentoWindow(this).setVisible(true));
        itemListado.addActionListener(e -> new ListadoDepartamentoWindow(this).setVisible(true));

// Añadir items al menú
        menuOperaciones.add(itemAlta);
        menuOperaciones.add(itemBaja);
        menuOperaciones.add(itemConsulta);
        menuOperaciones.add(itemMod);
        menuOperaciones.add(itemListado);   // ← aquí se añade



        // ------------------------
        // MENÚ IDIOMA
        // ------------------------
        JMenu menuIdioma = new JMenu(I18n.t(13)); // Idioma

        JMenuItem itemEs = new JMenuItem(I18n.t(14)); // Español
        JMenuItem itemEn = new JMenuItem(I18n.t(15)); // Inglés
        JMenuItem itemFr = new JMenuItem(I18n.t(16)); // Francés
        JMenuItem itemIt = new JMenuItem(I18n.t(17)); // Italiano
        JMenuItem itemDe = new JMenuItem(I18n.t(18)); // Alemán

        itemEs.addActionListener(e -> cambiarIdioma("es"));
        itemEn.addActionListener(e -> cambiarIdioma("en"));
        itemFr.addActionListener(e -> cambiarIdioma("fr"));
        itemIt.addActionListener(e -> cambiarIdioma("it"));
        itemDe.addActionListener(e -> cambiarIdioma("de"));

        menuIdioma.add(itemEs);
        menuIdioma.add(itemEn);
        menuIdioma.add(itemFr);
        menuIdioma.add(itemIt);
        menuIdioma.add(itemDe);


        // Añadir a la barra
        menuBar.add(menuArchivo);
        menuBar.add(menuOperaciones);
        menuBar.add(menuIdioma);

        setJMenuBar(menuBar);
    }

    private void cambiarIdioma(String codigo) {
        I18n.setIdioma(codigo);

        // Recargar la ventana completa
        dispose();
        new MainWindow();
    }
}
