package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import i18n.I18n;
import i18n.Textos;
import model.Departamento;
import data.Data;

public class AltaDepartamentoWindow extends JDialog {

    private JTextField txtNombre;
    private JTextField txtLocalidad;
    private ListadoDepartamentoWindow listado;

    public AltaDepartamentoWindow(Frame owner) {
        super(owner, I18n.t(22), true);   // “Alta Departamento”
        this.listado = listado;
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // ---- Nombre ----
        c.gridx = 0; c.gridy = 0;
        add(new JLabel(I18n.t(20) + ":"), c);

        txtNombre = new JTextField();
        c.gridx = 1;
        add(txtNombre, c);

        // ---- Localidad ----
        c.gridx = 0; c.gridy = 1;
        add(new JLabel(I18n.t(21) + ":"), c);

        txtLocalidad = new JTextField();
        c.gridx = 1;
        add(txtLocalidad, c);

        // ---- Botones ----
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton(I18n.t(5));   // Aceptar
        JButton btnCancelar = new JButton(I18n.t(6));  // Cancelar

        btnAceptar.addActionListener(this::onAceptar);
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        add(panelBotones, c);

        setVisible(true);
    }

    private void onAceptar(ActionEvent e) {
        String nombre = txtNombre.getText().trim().replaceAll("\\s+", " ");
        String localidad = txtLocalidad.getText().trim().replaceAll("\\s+", " ");

        // Validación nombre
        if (nombre.length() < 2 || !nombre.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(Textos.ERROR_NOMBRE_INVALIDO),
                    I18n.t(Textos.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación localidad
        if (localidad.isEmpty() || !localidad.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(Textos.ERROR_LOCALIDAD_INVALIDA),
                    I18n.t(Textos.ERROR),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 🔥 Comprobar duplicado (nombre + localidad)
        boolean existe = data.Data.getLista().stream().anyMatch(
                d -> d.getNombre().equalsIgnoreCase(nombre)
                        && d.getLocalidad().equalsIgnoreCase(localidad)
        );

        if (existe) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(Textos.ERROR_DEPARTAMENTO_DUPLICADO),  // texto distinto por idioma
                    I18n.t(Textos.TITULO_DEPARTAMENTO_DUPLICADO), // título distinto por idioma
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear departamento
        Departamento d = new Departamento(nombre, localidad);
        Data.addDepartamento(d);  // ya guarda en TSV

        // 🔄 Refrescar listado si está abierto
        if (listado != null) {
            listado.refrescarTabla();
        }

        JOptionPane.showMessageDialog(this,
                I18n.t(Textos.OPERACION_OK),
                I18n.t(Textos.APP_TITULO),
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

}


}

