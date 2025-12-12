package ui;

import javax.swing.*;
import java.awt.*;
import i18n.I18n;
import data.Data;
import i18n.Textos;
import model.Departamento;

public class ConsultaDepartamentoWindow extends JDialog {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtLocalidad;

    public ConsultaDepartamentoWindow(JFrame parent) {
        super(parent, I18n.t(Textos.CONSULTA), true); // Consulta Departamento
        setSize(420, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel(I18n.t(Textos.ID));        // ID
        JLabel lblNombre = new JLabel(I18n.t(Textos.NOMBRE));    // Nombre
        JLabel lblLocalidad = new JLabel(I18n.t(Textos.LOCALIDAD)); // Localidad

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
        JButton btnAceptar = new JButton(I18n.t(Textos.BUSCAR));  // Buscar
        JButton btnCancelar = new JButton(I18n.t(Textos.CANCELAR)); // Cancelar

        btnAceptar.addActionListener(e -> consultar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void consultar() {
        String idTexto = txtId.getText().trim();

        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(7),  // Error
                    I18n.t(7),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(7),
                    I18n.t(7),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar departamento
        for (Departamento d : Data.getDepartamentos()) {
            if (d.getId() == id) {
                txtNombre.setText(d.getNombre());
                txtLocalidad.setText(d.getLocalidad());
                return;
            }
        }

        // No encontrado
        JOptionPane.showMessageDialog(this,
                I18n.t(Textos.NO_ENCONTRADO), // Departamento no encontrado
                I18n.t(7),
                JOptionPane.ERROR_MESSAGE);
    }
    public void setIdValue(int id) {
        txtId.setText(String.valueOf(id));
        consultar(); // ejecuta automáticamente la búsqueda
    }
    public void cargarId(int id) {
        txtId.setText(String.valueOf(id));
        consultar();
    }

}
