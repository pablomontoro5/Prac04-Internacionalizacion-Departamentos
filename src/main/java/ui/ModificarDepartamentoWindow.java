package ui;

import javax.swing.*;
import java.awt.*;

import data.DataStorage;
import i18n.I18n;
import i18n.Textos;
import data.Data;
import model.Departamento;

public class ModificarDepartamentoWindow extends JDialog {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtLocalidad;

    private Departamento departamentoActual = null;
    private JButton btnGuardar;

    private ListadoDepartamentoWindow parentList;

    public ModificarDepartamentoWindow(ListadoDepartamentoWindow parent) {
        super(parent,
                I18n.t(Textos.APP_TITLE) + " - " + I18n.t(Textos.MOD_DEP),
                true);

        this.parentList = parent;   // Guardamos referencia al listado

        setSize(420, 260);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel(I18n.t(Textos.ID));
        JLabel lblNombre = new JLabel(I18n.t(Textos.NOMBRE));
        JLabel lblLocalidad = new JLabel(I18n.t(Textos.LOCALIDAD));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtLocalidad = new JTextField();

        // Campos bloqueados hasta encontrar el ID
        txtNombre.setEditable(false);
        txtLocalidad.setEditable(false);

        panelCampos.add(lblId);
        panelCampos.add(txtId);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblLocalidad);
        panelCampos.add(txtLocalidad);

        JPanel panelBotones = new JPanel();

        JButton btnBuscar = new JButton(I18n.t(Textos.BUSCAR));
        btnGuardar = new JButton(I18n.t(Textos.ACEPTAR));
        JButton btnCancelar = new JButton(I18n.t(Textos.CANCELAR));

        btnGuardar.setEnabled(false);

        btnBuscar.addActionListener(e -> buscar());
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Si tienes el constructor con JFrame, igual: usa Textos.*
    public ModificarDepartamentoWindow(JFrame parent) {
        super(parent,
                I18n.t(Textos.APP_TITLE) + " - " + I18n.t(Textos.MOD_DEP),
                true);

        setSize(420, 260);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel(I18n.t(Textos.ID));
        JLabel lblNombre = new JLabel(I18n.t(Textos.NOMBRE));
        JLabel lblLocalidad = new JLabel(I18n.t(Textos.LOCALIDAD));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtLocalidad = new JTextField();

        txtNombre.setEditable(false);
        txtLocalidad.setEditable(false);

        panelCampos.add(lblId);
        panelCampos.add(txtId);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblLocalidad);
        panelCampos.add(txtLocalidad);

        JPanel panelBotones = new JPanel();

        JButton btnBuscar = new JButton(I18n.t(Textos.BUSCAR));
        btnGuardar = new JButton(I18n.t(Textos.ACEPTAR));
        JButton btnCancelar = new JButton(I18n.t(Textos.CANCELAR));

        btnGuardar.setEnabled(false);

        btnBuscar.addActionListener(e -> buscar());
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void buscar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());

            departamentoActual = DataStorage.buscarDepartamento(id, Data.getLista());

            if (departamentoActual == null) {
                JOptionPane.showMessageDialog(this,
                        I18n.t(Textos.NO_ENCONTRADO),
                        I18n.t(Textos.ERROR),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            txtNombre.setText(departamentoActual.getNombre());
            txtLocalidad.setText(departamentoActual.getLocalidad());

            txtNombre.setEditable(true);
            txtLocalidad.setEditable(true);
            btnGuardar.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(Textos.ERROR),
                    I18n.t(Textos.ERROR),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardar() {
        if (!btnGuardar.isEnabled()) return;

        // aquí iría lo de nuevoId si lo estás usando
        departamentoActual.setNombre(txtNombre.getText().trim());
        departamentoActual.setLocalidad(txtLocalidad.getText().trim());

        DataStorage.save(Data.getLista());

        JOptionPane.showMessageDialog(this,
                I18n.t(Textos.OK),
                I18n.t(Textos.APP_TITLE),
                JOptionPane.INFORMATION_MESSAGE);

        if (parentList != null) {
            parentList.refrescarTabla();
        }

        dispose();
    }

    public void cargarId(int id) {
        txtId.setText(String.valueOf(id));
        buscar();
    }
}
