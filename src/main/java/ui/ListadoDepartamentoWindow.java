package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;

import data.DataStorage;
import i18n.I18n;
import data.Data;
import i18n.Textos;
import model.Departamento;

public class ListadoDepartamentoWindow extends JDialog {

    private JTable tabla;
    private DefaultTableModel modelo;

    private void mostrarPopupAcciones(int id) {

        String[] opciones = {
                I18n.t(Textos.CONSULTA),  // Consulta
                I18n.t(Textos.MODIFICACION),  // Modificación
                I18n.t(Textos.CANCELAR)   // Cancelar
        };

        int seleccion = JOptionPane.showOptionDialog(
                this,
                I18n.t(Textos.LISTADO) + " (ID: " + id + ")",
                I18n.t(Textos.LISTADO),
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
        super(parent, I18n.t(Textos.LISTADO) + " - " + I18n.t(Textos.LISTADO), true);
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn(I18n.t(Textos.ID)); // ID
        modelo.addColumn(I18n.t(Textos.NOMBRE)); // Nombre
        modelo.addColumn(I18n.t(Textos.LOCALIDAD)); // Localidad

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

        JButton btnCerrar = new JButton(I18n.t(Textos.CANCELAR));
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

    public void refrescarTabla() {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);

        for (Departamento d : Data.getLista()) {
            model.addRow(new Object[]{
                    d.getId(),
                    d.getNombre(),
                    d.getLocalidad()
            });
        }
    }




}
