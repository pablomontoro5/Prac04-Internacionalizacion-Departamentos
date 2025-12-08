package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import i18n.I18n;
import data.Data;
import model.Departamento;

public class ListadoDepartamentoWindow extends JDialog {

    private JTable tabla;
    private DefaultTableModel modelo;

    private void mostrarPopupAcciones(int id) {

        String[] opciones = {
                I18n.t(3),  // Consulta
                I18n.t(4),  // Modificación
                I18n.t(6)   // Cancelar
        };

        int seleccion = JOptionPane.showOptionDialog(
                this,
                I18n.t(25) + " (ID: " + id + ")",
                I18n.t(25),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == 0) {          // CONSULTAR
            ConsultaDepartamentoWindow win =
                    new ConsultaDepartamentoWindow((JFrame) getParent());
            win.cargarId(id);
            win.setVisible(true);
        }
        else if (seleccion == 1) {     // MODIFICAR
            ModificarDepartamentoWindow win =
                    new ModificarDepartamentoWindow((JFrame) getParent());
            win.cargarId(id);
            win.setVisible(true);
        }
    }


    public ListadoDepartamentoWindow(JFrame parent) {
        super(parent, I18n.t(0) + " - " + I18n.t(25), true);
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn(I18n.t(19)); // ID
        modelo.addColumn(I18n.t(20)); // Nombre
        modelo.addColumn(I18n.t(21)); // Localidad

        tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null); // no editable pero sí clicable

        cargarDatos();

        //  AÑADIR AQUÍ EL DOBLE CLIC
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow();
                    if (fila >= 0) {
                        int id = (int) modelo.getValueAt(fila, 0);
                        mostrarPopupAcciones(id);
                    }
                }
            }
        });


        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCerrar = new JButton(I18n.t(6));
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);

        add(panelBoton, BorderLayout.SOUTH);
    }


    private void cargarDatos() {
        modelo.setRowCount(0); // Vaciar tabla

        for (Departamento d : Data.getDepartamentos()) {
            modelo.addRow(new Object[]{
                    d.getId(),
                    d.getNombre(),
                    d.getLocalidad()
            });
        }
    }
}
