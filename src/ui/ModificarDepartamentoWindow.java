package ui;

import javax.swing.*;
import java.awt.*;
import i18n.I18n;
import data.Data;
import model.Departamento;

public class ModificarDepartamentoWindow extends JDialog {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtLocalidad;

    private Departamento departamentoActual = null;
    private JButton btnGuardar;

    public ModificarDepartamentoWindow(JFrame parent) {
        super(parent, I18n.t(0) + " - " + I18n.t(23), true);
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

        // 🔒 Campos bloqueados hasta encontrar el ID
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


    private void buscar() {
        String idTexto = txtId.getText().trim();
        String ERROR = I18n.t(7);

        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, ERROR, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, ERROR, ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar departamento
        departamentoActual = null;
        for (Departamento d : Data.getDepartamentos()) {
            if (d.getId() == id) {
                departamentoActual = d;
                txtNombre.setText(d.getNombre());
                txtLocalidad.setText(d.getLocalidad());

                // Activar edición
                txtNombre.setEditable(true);
                txtLocalidad.setEditable(true);
                btnGuardar.setEnabled(true);
                return;
            }
        }

        JOptionPane.showMessageDialog(this,
                I18n.t(8),  // “Departamento no encontrado”
                ERROR,
                JOptionPane.ERROR_MESSAGE);
    }

    private void guardar() {
        if (departamentoActual == null) return;

        String nombre = txtNombre.getText().trim();
        String localidad = txtLocalidad.getText().trim();

        if (nombre.isEmpty() || localidad.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(7),
                    I18n.t(7),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar cambios
        departamentoActual.setNombre(nombre);
        departamentoActual.setLocalidad(localidad);

        JOptionPane.showMessageDialog(this,
                I18n.t(9),        // “Operación completada”
                I18n.t(0),
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
    public void cargarId(int id) {
        txtId.setText(String.valueOf(id));
        buscar();  // ejecuta la búsqueda automáticamente
    }

}
