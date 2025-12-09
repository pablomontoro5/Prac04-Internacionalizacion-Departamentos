package ui;

import javax.swing.*;
import java.awt.*;

import i18n.I18n;
import i18n.Textos;
import util.IconManager;

public class MainWindow extends JFrame {

    private JLabel lblImagen;

    public MainWindow() {
        /*System.out.println("ADD = " + IconManager.ADD);
        System.out.println("DELETE = " + IconManager.DELETE);
        System.out.println("EDIT = " + IconManager.EDIT);
        System.out.println("SEARCH = " + IconManager.SEARCH);
        System.out.println("LIST = " + IconManager.LIST);
        System.out.println("SUN = " + IconManager.SUN);
        System.out.println("MOON = " + IconManager.MOON);
        */


        setTitle(I18n.t(0));
        setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());
        setSize(900, 550);
        setMinimumSize(new Dimension(750, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔥 Fix global de FlatLaf para iconos en menús
        UIManager.put("MenuItem.iconTextGap", 10);
        UIManager.put("MenuItem.minimumIconSize", new Dimension(18, 18));

        crearMenu();
        crearHeader();
        crearImagePanel();

        setVisible(true);
    }

    // =========================================================
    // HEADER MODERNO
    // =========================================================
    private void crearHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(28, 35, 48));
        header.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel titulo = new JLabel(I18n.t(0));
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));

        JLabel subtitulo = new JLabel("Práctica 4 – IPO | Internacionalización completa");
        subtitulo.setForeground(new Color(180, 180, 180));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);
        panelTextos.add(titulo);
        panelTextos.add(subtitulo);

        header.add(panelTextos, BorderLayout.WEST);
        // ---- Añadir logo al header ----
        try {
            ImageIcon iconLogo = new ImageIcon(getClass().getResource("/img/logo.png"));
            Image logoEscalado = iconLogo.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(logoEscalado));
            lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // margen derecha
            header.add(lblLogo, BorderLayout.EAST);
        } catch (Exception e) {
            System.err.println("No se pudo cargar logo: " + e.getMessage());
        }


        add(header, BorderLayout.NORTH);
    }

    // =========================================================
    // PANEL CENTRAL CON IMAGEN
    // =========================================================
    private void crearImagePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));

        lblImagen = new JLabel("", SwingConstants.CENTER);

        ImageIcon img = I18n.getImagen(1);
        if (img != null) {
            Image scaled = img.getImage().getScaledInstance(400, 260, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(scaled));
        } else {
            lblImagen.setText(I18n.t(0));
            lblImagen.setFont(new Font("Segoe UI", Font.BOLD, 24));
        }

        panel.add(lblImagen, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
    }

    // =========================================================
    // MENÚ SUPERIOR
    // =========================================================
    private void crearMenu() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // ---- Archivo ----
        JMenu menuArchivo = new JMenu(I18n.t(10));
        JMenuItem itemSalir = crearMenuItemOper(() -> System.exit(0), Textos.SALIR, IconManager.DELETE);
        menuArchivo.add(itemSalir);

        // ---- Operaciones ----
        JMenu menuOperaciones = new JMenu(I18n.t(Textos.OPERACIONES));

        menuOperaciones.add(crearMenuItemOper(() -> new AltaDepartamentoWindow(this).setVisible(true), Textos.ALTA, IconManager.ADD));
        menuOperaciones.add(crearMenuItemOper(() -> new BajaDepartamentoWindow(this).setVisible(true), Textos.BAJA, IconManager.DELETE));
        menuOperaciones.add(crearMenuItemOper(() -> new ConsultaDepartamentoWindow(this).setVisible(true), Textos.CONSULTA, IconManager.SEARCH));
        menuOperaciones.add(crearMenuItemOper(() -> new ModificarDepartamentoWindow(this).setVisible(true), Textos.MODIFICACION, IconManager.EDIT));
        menuOperaciones.add(crearMenuItemOper(() -> new ListadoDepartamentoWindow(this).setVisible(true), Textos.LISTADO, IconManager.LIST));

        // ---- Idioma ----
        JMenu menuIdioma = new JMenu(I18n.t(13));
        menuIdioma.add(crearItemIdioma("es", 14));
        menuIdioma.add(crearItemIdioma("en", 15));
        menuIdioma.add(crearItemIdioma("fr", 16));
        menuIdioma.add(crearItemIdioma("it", 17));
        menuIdioma.add(crearItemIdioma("de", 18));

        // ---- Tema Claro/Oscuro ----
        JMenu menuTema = new JMenu("Tema");

        // Tema claro
        JMenuItem itemClaro = new JMenuItem("Claro", IconManager.SUN);
        itemClaro.addActionListener(e -> setTheme(false));

        // Tema oscuro
        JMenuItem itemOscuro = new JMenuItem("Oscuro", IconManager.MOON);
        itemOscuro.addActionListener(e -> setTheme(true));

        menuTema.add(itemClaro);
        menuTema.add(itemOscuro);

        // ---- Ayuda ----
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de…");

        itemAcerca.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Aplicación de Departamentos\nVersión 1.0\n© 2025",
                        "Acerca de",
                        JOptionPane.INFORMATION_MESSAGE)
        );

        menuAyuda.add(itemAcerca);
        menuBar.add(menuAyuda);

        menuTema.add(itemClaro);
        menuTema.add(itemOscuro);

        menuBar.add(menuArchivo);
        menuBar.add(menuOperaciones);
        menuBar.add(menuIdioma);
        menuBar.add(menuTema);

        setJMenuBar(menuBar);
    }

    // =========================================================
    // ITEMS DEL MENÚ (con icono escalado y bugfix)
    // =========================================================
    private JMenuItem crearMenuItemOper(Runnable action, Textos texto, ImageIcon icon) {
        JMenuItem item = new JMenuItem(I18n.t(texto));
        if (icon != null) item.setIcon(icon);
        item.addActionListener(e -> action.run());
        return item;
    }



    // =========================================================
    // MENÚ DE IDIOMA
    // =========================================================
    private JMenuItem crearItemIdioma(String codigo, int textoIndex) {

        JMenuItem item = new JMenuItem(I18n.t(textoIndex));

        ImageIcon icon = I18n.getImagenIdioma(codigo);
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(20, 14, Image.SCALE_SMOOTH);
            item.setIcon(new ImageIcon(scaled));
        }

        item.setIconTextGap(10);
        item.addActionListener(e -> cambiarIdioma(codigo));

        return item;
    }

    // Recargar interfaz completa
    private void cambiarIdioma(String codigo) {
        I18n.setIdioma(codigo);
        dispose();
        new MainWindow();
    }

    // Tema claro/oscuro
    private void setTheme(boolean dark) {
        try {
            if (dark)
                com.formdev.flatlaf.FlatDarkLaf.setup();
            else
                com.formdev.flatlaf.FlatLightLaf.setup();

            dispose();
            new MainWindow();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
