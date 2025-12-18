package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import i18n.I18n;
import i18n.Idioma;
import i18n.Textos;
import util.IconManager;

public class MainWindow extends JFrame {

    private JLabel lblImagen;
    private Image imagenOriginal;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblCampus;
    public ListadoDepartamentoWindow ventanaListado;
    public MainWindow() {

        setTitle(I18n.t(Textos.APP_TITLE));  // mejor que I18n.t(0)
        setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());

        setSize(900, 550);
        setMinimumSize(new Dimension(750, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fix global de FlatLaf para iconos en menús
        UIManager.put("MenuItem.iconTextGap", 10);
        UIManager.put("MenuItem.minimumIconSize", new Dimension(18, 18));

        // ===== MENÚ + CABECERA =====
        crearMenu();
        crearHeader();

        // ===== PANEL CENTRAL (IMAGEN + TEXTO) =====
        // Aquí es donde debe existir y añadirse lblImagen (dentro de crearImagePanel)
        crearImagePanel();

        // ===== CARGAR IMAGEN DESDE I18N =====
        ImageIcon icon = I18n.img(1);  // 0 = bandera, 1 = campus

        lblCampus = new JLabel(icon);
        lblCampus.setHorizontalAlignment(SwingConstants.CENTER);
        lblCampus.setVerticalAlignment(SwingConstants.CENTER);

        // Añadir al contenedor principal
        getContentPane().add(lblCampus, BorderLayout.CENTER);
        imagenOriginal = (icon != null) ? icon.getImage() : null;


        // Escalar al arrancar y cuando se redimensione
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                escalarImagen();
            }
        });

        escalarImagen();

        setVisible(true);
    }

    private String nombreIdioma(String codigo) {
        switch (codigo.toLowerCase()) {
            case "es": return I18n.t(Textos.ESPAÑOL);
            case "en": return I18n.t(Textos.INGLES);
            case "fr": return I18n.t(Textos.FRANCES);
            case "it": return I18n.t(Textos.ITALIANO);
            case "de": return I18n.t(Textos.ALEMAN);
            case "pl": return I18n.t(Textos.POLACO);
            default:   return codigo; // si añades/quitas idiomas
        }
    }

    // =========================================================
    // HEADER MODERNO
    // =========================================================
    private void crearHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(28, 35, 48));
        header.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        lblTitulo = new JLabel(I18n.t(Textos.APP_TITLE));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));

        lblSubtitulo = new JLabel(I18n.t(Textos.APP_SUBTITLE));
        lblSubtitulo.setForeground(new Color(180, 180, 180));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);
        panelTextos.add(lblTitulo);
        panelTextos.add(lblSubtitulo);


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

        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblImagen, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
    }


    // =========================================================
    // MENÚ SUPERIOR
    // =========================================================
    private void crearMenu() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // ---- Operaciones ----
        JMenu menuOperaciones = new JMenu(I18n.t(Textos.OPERACIONES));

        menuOperaciones.add(crearMenuItemOper(
                () -> new AltaDepartamentoWindow(this).setVisible(true),
                Textos.ALTA, IconManager.ADD));

        menuOperaciones.add(crearMenuItemOper(
                () -> new BajaDepartamentoWindow(this).setVisible(true),
                Textos.BAJA, IconManager.DELETE));

        menuOperaciones.add(crearMenuItemOper(
                () -> new ConsultaDepartamentoWindow(this).setVisible(true),
                Textos.CONSULTA, IconManager.SEARCH));

        // Opción Modificación que abre primero el listado y luego modificar
        menuOperaciones.add(crearMenuItemOper(() -> {

            // Abrir listado si no está abierto
            if (ventanaListado == null || !ventanaListado.isVisible()) {
                ventanaListado = new ListadoDepartamentoWindow(this);
            }

            ventanaListado.setVisible(true);

            // Abrir Modificar pasando SIEMPRE LA MISMA ventanaListado
            new ModificarDepartamentoWindow(ventanaListado).setVisible(true);

        }, Textos.MODIFICACION, IconManager.EDIT));

        // Opción Listado
        menuOperaciones.add(crearMenuItemOper(() -> {
            if (ventanaListado == null || !ventanaListado.isVisible()) {
                ventanaListado = new ListadoDepartamentoWindow(this);
            }
            ventanaListado.setVisible(true);
        }, Textos.LISTADO, IconManager.LIST));


        // ---- Idioma ----
        JMenu menuIdioma = new JMenu(I18n.t(Textos.IDIOMA));

        for (Idioma id : I18n.getIdiomasList()) {
            final String codigo = id.getCodigo();

            JMenuItem item = new JMenuItem(nombreIdioma(codigo), I18n.getImagenIdioma(codigo));

            item.addActionListener(e -> {
                I18n.setIdioma(codigo);
                actualizarTextos(); // esto ya recrea el menú
            });

            menuIdioma.add(item);
        }


        // ---- Tema Claro/Oscuro ----
        JMenu menuTema = new JMenu(I18n.t(Textos.TEMA));

        JMenuItem itemClaro = new JMenuItem(I18n.t(Textos.CLARO), IconManager.SUN);
        itemClaro.addActionListener(e -> setTheme(false));

        JMenuItem itemOscuro = new JMenuItem(I18n.t(Textos.OSCURO), IconManager.MOON);
        itemOscuro.addActionListener(e -> setTheme(true));

        menuTema.add(itemClaro);
        menuTema.add(itemOscuro);

        // ---- Ayuda ----
        JMenu menuAyuda = new JMenu(I18n.t(Textos.AYUDA));
        JMenuItem itemAcerca = new JMenuItem(I18n.t(Textos.ACERCA_DE));

        itemAcerca.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Aplicación de Departamentos\nVersión 1.0\n© 2025",
                        I18n.t(Textos.ACERCA_DE),
                        JOptionPane.INFORMATION_MESSAGE)
        );

        menuAyuda.add(itemAcerca);

        // ---- Salida ----
        // Usa aquí el texto que quieras: Textos.SALIR o Textos.SALIDA según tu enum
        JMenu menuSalida = new JMenu(I18n.t(Textos.SALIDA));  // o Textos.SALIR si así lo tienes
        JMenuItem itemSalir = crearMenuItemOper(
                () -> System.exit(0),
                Textos.SALIR,          // este es el texto del ítem, tipo "Salir"
                IconManager.DELETE
        );
        menuSalida.add(itemSalir);

        // ---- Añadir menús a la barra en el ORDEN correcto ----
        menuBar.add(menuOperaciones);
        menuBar.add(menuIdioma);
        menuBar.add(menuTema);
        menuBar.add(menuAyuda);
        menuBar.add(menuSalida);   // último

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


    private void cambiarIdioma(String codigo) {
        I18n.setIdioma(codigo);

        // Recargar imagen del nuevo idioma
        ImageIcon icon = I18n.img(1);
        imagenOriginal = icon.getImage();
        escalarImagen();

        // Actualizar textos (títulos, botones, labels...)
        actualizarTextos();

        // Reconstruir menú para que cambie "Help/Ayuda", etc.
        crearMenu();

        // Forzar refresco visual
        revalidate();
        repaint();
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
    private void escalarImagen() {
        if (imagenOriginal == null || lblImagen == null) return;

        int ancho = lblImagen.getParent().getWidth();
        if (ancho <= 0) return;

        Image escalada = imagenOriginal.getScaledInstance(
                ancho,
                -1, // mantiene proporción
                Image.SCALE_SMOOTH
        );

        lblImagen.setIcon(new ImageIcon(escalada));
    }

    private void actualizarTextosOptionPane() {
        UIManager.put("OptionPane.okButtonText", I18n.t(Textos.ACEPTAR));
        UIManager.put("OptionPane.cancelButtonText", I18n.t(Textos.CANCELAR));

        // Si usas confirm dialogs (Sí/No), mejor crear Textos.SI y Textos.NO,
        // pero mientras puedes reutilizar:
        UIManager.put("OptionPane.yesButtonText", I18n.t(Textos.ACEPTAR));
        UIManager.put("OptionPane.noButtonText", I18n.t(Textos.CANCELAR));
    }

    private void actualizarTextos() {

        setTitle(I18n.t(Textos.APP_TITLE));

        if (lblTitulo != null) {
            lblTitulo.setText(I18n.t(Textos.APP_TITLE));
        }

        if (lblSubtitulo != null) {
            lblSubtitulo.setText(I18n.t(Textos.APP_SUBTITLE));
        }
        if (lblCampus != null) {
            lblCampus.setIcon(I18n.img(1));
            lblCampus.revalidate();
            lblCampus.repaint();
        }


        actualizarTextosOptionPane();

        crearMenu();
    }


}
