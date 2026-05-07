/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author ferna
 */


public class SyncroAlert {
    // COLORES EXTRAÍDOS DE TU LOGO "EL BUEN PAN"
    private static final Color CAFE_OSCURO = new Color(78, 52, 46);   // #4E342E
    private static final Color NARANJA_PAN = new Color(245, 166, 35);  // #F5A623
    private static final Color CREMA_FONDO = new Color(255, 253, 240); // Blanco crema
    private static final Color BLANCO = Color.WHITE;
    
    private static String correoTemporal = "";

    public static void showRecuperacion(JFrame parent, int paso, DataInputStream entrada, DataOutputStream salida) {
        JDialog dialog = new JDialog(parent, true);
        dialog.setUndecorated(true);

        // Panel Principal con borde café grueso
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBorder(BorderFactory.createLineBorder(CAFE_OSCURO, 3));
        panel.setBackground(BLANCO);

        // --- CABECERA ESTILO "EL BUEN PAN" ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CAFE_OSCURO);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel title = new JLabel();
        title.setForeground(NARANJA_PAN); // Título en naranja
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(title, BorderLayout.WEST);

        JButton btnCerrar = new JButton("✕");
        btnCerrar.setForeground(Color.LIGHT_GRAY);
        btnCerrar.setBackground(CAFE_OSCURO);
        btnCerrar.setBorder(null);
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 18));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dialog.dispose());
        header.add(btnCerrar, BorderLayout.EAST);
        
        // --- CUERPO ---
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(BLANCO);
        body.setBorder(new EmptyBorder(20, 25, 20, 25));

        switch (paso) {
            case 1:
                title.setText("RECUPERAR CUENTA");
                body.add(crearLabelInstruccion("Introduce tu correo electrónico:"));
                JTextField txtCorreo = crearTextFieldEstilizado();
                body.add(txtCorreo);
                
                agregarBotonAccion(panel, "ENVIAR CÓDIGO", e -> {
                    String correo = txtCorreo.getText().trim();
                    if(correo.isEmpty()) {
                        mostrarMensaje(dialog, "Por favor, escribe tu correo.");
                        return;
                    }
                    try {
                        salida.writeUTF("SOLICITAR_RECUPERACION," + correo);
                        String respuesta = entrada.readUTF();
                        if (respuesta.equals("CODIGO_ENVIADO")) {
                            correoTemporal = correo;
                            dialog.dispose();
                            showRecuperacion(parent, 2, entrada, salida);
                        } else {
                            mostrarMensaje(dialog, "El correo no está registrado.");
                        }
                    } catch (Exception ex) { ex.printStackTrace(); }
                });
                break;

            case 2:
                title.setText("CAMBIAR CONTRASEÑA");
                JLabel info = new JLabel("<html>Código enviado a:<br><font color='#F5A623'>" + correoTemporal + "</font></html>");
                info.setAlignmentX(Component.CENTER_ALIGNMENT);
                body.add(info);
                body.add(Box.createVerticalStrut(15));
                
                body.add(crearLabelInstruccion("Código de verificación:"));
                JTextField txtCodigo = crearTextFieldEstilizado();
                body.add(txtCodigo);
                
                body.add(Box.createVerticalStrut(10));
                body.add(crearLabelInstruccion("Nueva Contraseña:"));
                JPasswordField txtPass1 = crearPasswordFieldEstilizado();
                body.add(txtPass1);
                
                body.add(Box.createVerticalStrut(10));
                body.add(crearLabelInstruccion("Confirmar Contraseña:"));
                JPasswordField txtPass2 = crearPasswordFieldEstilizado();
                body.add(txtPass2);

                agregarBotonAccion(panel, "ACTUALIZAR DATOS", e -> {
                    String p1 = new String(txtPass1.getPassword());
                    String p2 = new String(txtPass2.getPassword());
                    String cod = txtCodigo.getText().trim();

                    if(cod.isEmpty() || p1.isEmpty()) {
                        mostrarMensaje(dialog, "Completa todos los campos.");
                        return;
                    }
                    if(!p1.equals(p2)) {
                        mostrarMensaje(dialog, "Las contraseñas no coinciden.");
                        return;
                    }

                    try {
                        salida.writeUTF("CAMBIAR_PASSWORD," + correoTemporal + "," + cod + "," + p1);
                        if(entrada.readBoolean()) {
                            dialog.dispose();
                            showRecuperacion(parent, 3, entrada, salida);
                        } else {
                            mostrarMensaje(dialog, "Código incorrecto o expirado.");
                        }
                    } catch (Exception ex) { ex.printStackTrace(); }
                });
                break;

            case 3:
                title.setText("¡PROCESO EXITOSO!");
                JLabel msg = new JLabel("<html><center><font color='#4E342E' size='5'><b>¡Listo!</b></font><br><br>Tu contraseña ha sido<br>actualizada correctamente.</center></html>", SwingConstants.CENTER);
                msg.setAlignmentX(Component.CENTER_ALIGNMENT);
                body.add(msg);
                agregarBotonAccion(panel, "VOLVER AL LOGIN", e -> dialog.dispose());
                break;
        }

        panel.add(header, BorderLayout.NORTH);
        panel.add(body, BorderLayout.CENTER);
        dialog.add(panel);
        dialog.pack();
        
        // Ajuste de tamaño según el paso
        if (paso == 2) dialog.setSize(380, 480); else dialog.setSize(380, 280);
        
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    // --- MÉTODOS AUXILIARES DE DISEÑO ---

    private static JLabel crearLabelInstruccion(String texto) {
        JLabel lb = new JLabel(texto);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lb.setForeground(CAFE_OSCURO);
        lb.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lb;
    }

    private static JTextField crearTextFieldEstilizado() {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(300, 35));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return tf;
    }

    private static JPasswordField crearPasswordFieldEstilizado() {
        JPasswordField pf = new JPasswordField();
        pf.setMaximumSize(new Dimension(300, 35));
        pf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return pf;
    }

    private static void agregarBotonAccion(JPanel panel, String texto, ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setBackground(CAFE_OSCURO); // Fondo café
        btn.setForeground(NARANJA_PAN); // Texto naranja
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 40));
        btn.addActionListener(accion);
        
        JPanel btnContainer = new JPanel();
        btnContainer.setBackground(BLANCO);
        btnContainer.setBorder(new EmptyBorder(0, 0, 20, 0));
        btnContainer.add(btn);
        panel.add(btnContainer, BorderLayout.SOUTH);
    }

    private static void mostrarMensaje(JDialog parent, String msg) {
        UIManager.put("OptionPane.background", BLANCO);
        UIManager.put("Panel.background", BLANCO);
        JOptionPane.showMessageDialog(parent, msg, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}
