package ui;

import javax.swing.*;
import java.awt.*;

import data.DataStorage;
import i18n.I18n;
import data.Data;
import i18n.Textos;
import model.Departamento;

public class ModificarDepartamentoWindow extends JDialog {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtLocalidad;

    private Departamento departamentoActual = null;
    private JButton btnGuardar;

    private ListadoDepartamentoWindow parentList;

    public ModificarDepartamentoWindow(ListadoDepartamentoWindow parent) {
        super(parent, I18n.t(0) + " - " + I18n.t(23), true);

        this.parentList = parent;   //  Guardamos referencia al listado

        setSize(420, 260);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel(I18n.t(19));        // ID
        JLabel lblNombre = new JLabel(I18n.t(20));    // Nombre
        JLabel lblLocalidad = new JLabel(I18n.t(21)); // Localidad

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtLocalidad = new JTextField();

        //  Campos bloqueados hasta encontrar el ID
        txtNombre.setEditable(false);
        txtLocalidad.setEditable(false);

        panelCampos.add(lblId);
        panelCampos.add(txtId);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblLocalidad);
        panelCampos.add(txtLocalidad);

        JPanel panelBotones = new JPanel();

        JButton btnBuscar = new JButton(I18n.t(26));  // Buscar
        btnGuardar = new JButton(I18n.t(5));          // Guardar/Aceptar
        JButton btnCancelar = new JButton(I18n.t(6)); // Cancelar

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

    public ModificarDepartamentoWindow(JFrame parent) {
        super(parent, I18n.t(0) + " - " + I18n.t(23), true);

        setSize(420, 260);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel(I18n.t(19));
        JLabel lblNombre = new JLabel(I18n.t(20));
        JLabel lblLocalidad = new JLabel(I18n.t(21));

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

        JButton btnBuscar = new JButton(I18n.t(26));
        btnGuardar = new JButton(I18n.t(5));
        JButton btnCancelar = new JButton(I18n.t(6));

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
                JOptionPane.showMessageDialog(this, I18n.t(27)); // "No encontrado"
                return;
            }

            txtNombre.setText(departamentoActual.getNombre());
            txtLocalidad.setText(departamentoActual.getLocalidad());

            txtNombre.setEditable(true);
            txtLocalidad.setEditable(true);
            btnGuardar.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, I18n.t(28)); // "ID inválido"
        }
    }


    private void guardar() {
        if (!btnGuardar.isEnabled()) return;

        int nuevoId;
        try {
            nuevoId = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(Textos.ERROR_ID_INVALIDO),
                    I18n.t(Textos.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar ID duplicado (excepto si es el mismo departamento)
        if (nuevoId != departamentoActual.getId()) {
            boolean existe = Data.getLista().stream().anyMatch(d -> d.getId() == nuevoId);
            if (existe) {
                JOptionPane.showMessageDialog(this,
                        I18n.t(Textos.ERROR_ID_DUPLICADO),
                        I18n.t(Textos.ERROR),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cambiar ID
            departamentoActual.setId(nuevoId);
        }

        // Actualizar nombre y localidad
        departamentoActual.setNombre(txtNombre.getText().trim());
        departamentoActual.setLocalidad(txtLocalidad.getText().trim());

        // Guardar en disco
        DataStorage.save(Data.getLista());

        JOptionPane.showMessageDialog(this, I18n.t(Textos.GUARDADO_OK));

        // Actualizar listado si está abierto
        if (parentList != null) parentList.refrescarTabla();

        dispose();
    }





    public void cargarId(int id) {
        txtId.setText(String.valueOf(id));
        buscar();  // ejecuta la búsqueda automáticamente
    }

}
