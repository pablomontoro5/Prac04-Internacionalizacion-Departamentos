package util;

import javax.swing.*;

public class IconManager {

    public static final ImageIcon ADD    = load("add.png");
    public static final ImageIcon DELETE = load("delete.png");
    public static final ImageIcon EDIT   = load("edit.png");
    public static final ImageIcon SEARCH = load("search.png");
    public static final ImageIcon LIST   = load("list.png");
    public static final ImageIcon SUN    = load("sun.png");
    public static final ImageIcon MOON   = load("moon.png");


    private static ImageIcon load(String name) {

        String[] paths = {
                "/icons/" + name,          // recursos estándar
                "icons/" + name,           // sin slash
                "/resources/icons/" + name // fallback IntelliJ
        };

        for (String p : paths) {
            var url = IconManager.class.getResource(p);
            if (url != null) return new ImageIcon(url);
        }

        System.err.println("⚠ ICONO NO ENCONTRADO en ninguna ruta: " + name);
        return new ImageIcon(); // icono vacío, no crashea
    }
}
