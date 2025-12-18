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

    public AltaDepartamentoWindow(Frame owner) {
        super(owner, I18n.t(Textos.ALTA_DEP), true);   // “Alta Departamento”
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // ---- Nombre ----
        c.gridx = 0; c.gridy = 0;
        add(new JLabel(I18n.t(Textos.NOMBRE) + ":"), c);

        txtNombre = new JTextField();
        c.gridx = 1;
        add(txtNombre, c);

        // ---- Localidad ----
        c.gridx = 0; c.gridy = 1;
        add(new JLabel(I18n.t(Textos.LOCALIDAD) + ":"), c);

        txtLocalidad = new JTextField();
        c.gridx = 1;
        add(txtLocalidad, c);

        // ---- Botones ----
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton(I18n.t(Textos.ACEPTAR));   // Aceptar
        JButton btnCancelar = new JButton(I18n.t(Textos.CANCELAR));  // Cancelar

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
                    I18n.t(7),  // Error
                    I18n.t(7),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación localidad
        if (localidad.isEmpty() || !localidad.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            JOptionPane.showMessageDialog(this,
                    I18n.t(7),
                    I18n.t(7),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //  NUEVO: comprobar si ya existe un departamento con mismo nombre + localidad
        boolean existeDuplicado = Data.getLista().stream().anyMatch(dep ->
                dep.getNombre().equalsIgnoreCase(nombre) &&
                        dep.getLocalidad().equalsIgnoreCase(localidad)
        );

        if (existeDuplicado) {
            JOptionPane.showMessageDialog(
                    this,
                    I18n.t(Textos.DEP_DUPLICADO_MSG),
                    I18n.t(Textos.DEP_DUPLICADO_TITLE),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }


        // Crear departamento
        Departamento d = new Departamento(nombre, localidad);
        Data.addDepartamento(d);

        JOptionPane.showMessageDialog(this,
                I18n.t(9),
                I18n.t(0),
                JOptionPane.INFORMATION_MESSAGE);

        //  Refrescar listado si está abierto
        if (getOwner() instanceof MainWindow main) {
            if (main.ventanaListado != null) {
                main.ventanaListado.refrescarTabla();
            }
        }

        dispose();
    }



}

