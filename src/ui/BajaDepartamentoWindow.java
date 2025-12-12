package ui;

import javax.swing.*;
import java.awt.*;
import i18n.I18n;
import data.Data;
import model.Departamento;

public class BajaDepartamentoWindow extends JDialog {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtLocalidad;

    private Departamento deptoEncontrado = null;
    private JButton btnEliminar;
    private ListadoDepartamentoWindow listado;

    public BajaDepartamentoWindow(JFrame parent) {
        super(parent, I18n.t(0) + " - " + I18n.t(24), true); // Título consistente
        this.listado = listado;

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

        txtNombre.setEditable(false);
        txtLocalidad.setEditable(false);

        panelCampos.add(lblId);
        panelCampos.add(txtId);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblLocalidad);
        panelCampos.add(txtLocalidad);

        JPanel panelBotones = new JPanel();

        JButton btnBuscar = new JButton(I18n.t(26));   // Buscar
        btnEliminar = new JButton(I18n.t(27));        // Eliminar (mejor que Aceptar)
        JButton btnCancelar = new JButton(I18n.t(6)); // Cancelar

        btnEliminar.setEnabled(false);

        btnBuscar.addActionListener(e -> buscar());
        btnEliminar.addActionListener(e -> onEliminar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
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

        deptoEncontrado = null;
        for (Departamento d : Data.getDepartamentos()) {
            if (d.getId() == id) {
                deptoEncontrado = d;
                txtNombre.setText(d.getNombre());
                txtLocalidad.setText(d.getLocalidad());
                btnEliminar.setEnabled(true);
                return;
            }
        }

        txtNombre.setText("");
        txtLocalidad.setText("");
        btnEliminar.setEnabled(false);

        JOptionPane.showMessageDialog(this,
                I18n.t(8),  // No encontrado
                ERROR,
                JOptionPane.ERROR_MESSAGE);
    }

    private void onEliminar() {
        String idTexto = txtId.getText().trim();

        if (idTexto.isEmpty() || !idTexto.matches("\\d+")) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(7),  // Error
                    I18n.t(7),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(idTexto);

        Departamento d = Data.buscarPorId(id);

        if (d == null) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(8),  // Departamento no encontrado
                    I18n.t(7),  // Error
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmación
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar departamento?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) return;

        // Eliminar
        Data.eliminarDepartamento(d.getId());

        JOptionPane.showMessageDialog(this,
                I18n.t(9),   // Operación completada
                I18n.t(0),
                JOptionPane.INFORMATION_MESSAGE);

        // REFRESCAR LISTADO SI ESTÁ ABIERTO
        if (getOwner() instanceof MainWindow main && main.ventanaListado != null) {
            main.ventanaListado.refrescarTabla();
        }

        dispose();
    }


}
