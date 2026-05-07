package elbuenpan;
import Clases.ManejadorSesion;
import Clases.SyncroAlert;
import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

// Codigo del equipo QAM
public class Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    public Login() {
        
    initComponents();
    setLocationRelativeTo(null);
    this.setResizable(false);
        
    java.awt.Image img = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo.png")).getImage();
    java.awt.Image newimg = img.getScaledInstance(666, 700, java.awt.Image.SCALE_SMOOTH);
    javax.swing.ImageIcon imageIcon = new javax.swing.ImageIcon(newimg);

    Fondo.setIcon(imageIcon);
    
    java.awt.Image img2 = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo.png")).getImage();
    java.awt.Image newimg2 = img2.getScaledInstance(120, 110, java.awt.Image.SCALE_SMOOTH);
    javax.swing.ImageIcon imageIcon2 = new javax.swing.ImageIcon(newimg2);

    Logo.setIcon(imageIcon2);
    
    java.awt.Image img3 = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Datos.png")).getImage();
    java.awt.Image newimg3 = img3.getScaledInstance(380, 130, java.awt.Image.SCALE_SMOOTH);
    javax.swing.ImageIcon imageIcon3 = new javax.swing.ImageIcon(newimg3);

    Datos.setIcon(imageIcon3);
    
    java.awt.Image img4 = new javax.swing.ImageIcon(getClass().getResource("/Iconos/Ocultar.png")).getImage();
    java.awt.Image newimg4 = img4.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
    javax.swing.ImageIcon imageIcon4 = new javax.swing.ImageIcon(newimg4);

    Mostrar.setIcon(imageIcon4);
    
    Usuario.setText("Usuario");
    Usuario.setForeground(new Color(153,153,153));

    Contra.setText("Contraseña");
    Contra.setForeground(new Color(153,153,153));
    Contra.setEchoChar((char)0);

    jPanel1.requestFocusInWindow();

    UIManager.put("ToolTip.background", new Color(255, 235, 200)); 
    UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(100, 50, 0), 1));
    UIManager.put("ToolTip.foreground", new Color(60, 30, 0));
    UIManager.put("ToolTip.font", new Font("SansSerif", Font.PLAIN, 18));

    }
    

    
    
    
    
    
    
    
    boolean mostrar = false;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Usuario = new javax.swing.JTextField();
        Contra = new javax.swing.JPasswordField();
        Fondo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        Mostrar = new javax.swing.JButton();
        Datos = new javax.swing.JLabel();
        checkrecordar = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        OlvidarC = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Usuario.setFont(new java.awt.Font("Aileron Thin", 1, 20)); // NOI18N
        Usuario.setToolTipText("Ingresa tu nombre de usuario asignado");
        Usuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Usuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                UsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                UsuarioFocusLost(evt);
            }
        });
        Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioActionPerformed(evt);
            }
        });
        jPanel1.add(Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 320, 340, 30));

        Contra.setFont(new java.awt.Font("Aileron Thin", 1, 20)); // NOI18N
        Contra.setToolTipText("Ingrese su contraseña");
        Contra.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Contra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ContraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ContraFocusLost(evt);
            }
        });
        jPanel1.add(Contra, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 390, 340, 30));
        jPanel1.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 666, 700));

        jLabel1.setFont(new java.awt.Font("Aileron Thin", 1, 18)); // NOI18N
        jLabel1.setText("!Por favor introduce los datos¡");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 260, -1, -1));

        jLabel2.setFont(new java.awt.Font("Aileron Black", 1, 40)); // NOI18N
        jLabel2.setText("Bienvenido otra vez");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 170, 30, 30));
        jPanel1.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 80, 120, 110));

        Mostrar.setFont(new java.awt.Font("Aileron Thin", 1, 18)); // NOI18N
        Mostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mostrar.png"))); // NOI18N
        Mostrar.setToolTipText("Mostrar/Ocultar contraseña");
        Mostrar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Mostrar.setBorderPainted(false);
        Mostrar.setContentAreaFilled(false);
        Mostrar.setFocusPainted(false);
        Mostrar.setOpaque(true);
        Mostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarActionPerformed(evt);
            }
        });
        jPanel1.add(Mostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 390, 20, 20));
        jPanel1.add(Datos, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 320, 380, 130));

        checkrecordar.setBackground(new java.awt.Color(255, 255, 255));
        checkrecordar.setFont(new java.awt.Font("Aileron Thin", 1, 18)); // NOI18N
        checkrecordar.setText("Recordar");
        checkrecordar.setToolTipText("Mantener sesión iniciada en este equipo");
        checkrecordar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        checkrecordar.setContentAreaFilled(false);
        checkrecordar.setFocusPainted(false);
        checkrecordar.setFocusable(false);
        checkrecordar.setOpaque(true);
        jPanel1.add(checkrecordar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 450, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Iniciar Sesion");
        jButton1.setToolTipText("Entrar al sistema de ventas");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 520, 220, 40));

        OlvidarC.setFont(new java.awt.Font("Aileron Thin", 1, 18)); // NOI18N
        OlvidarC.setText("Olvide mi contraseña");
        OlvidarC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        OlvidarC.setBorderPainted(false);
        OlvidarC.setContentAreaFilled(false);
        OlvidarC.setFocusPainted(false);
        OlvidarC.setOpaque(true);
        OlvidarC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                OlvidarCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                OlvidarCMouseExited(evt);
            }
        });
        OlvidarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OlvidarCActionPerformed(evt);
            }
        });
        jPanel1.add(OlvidarC, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 450, -1, 30));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 60, 150, 130));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsuarioFocusGained
    // Quitamos el recuadro rojo y cualquier rastro de línea
    Usuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    
    if (Usuario.getText().equals("Usuario") || Usuario.getForeground().equals(Color.RED)) {
        Usuario.setText("");
        Usuario.setForeground(Color.BLACK);
    }
    }//GEN-LAST:event_UsuarioFocusGained

    private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioActionPerformed
    }//GEN-LAST:event_UsuarioActionPerformed

    private void UsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsuarioFocusLost
    if (Usuario.getText().isEmpty()) {
        Usuario.setText("Usuario");
        Usuario.setForeground(new Color(153,153,153));
    }
    }//GEN-LAST:event_UsuarioFocusLost

    private void ContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ContraFocusGained
    if (Contra.getText().equals("Contraseña")) {
        Contra.setText("");
        Contra.setForeground(Color.BLACK);
        Contra.setEchoChar('•');
    }
    }//GEN-LAST:event_ContraFocusGained

    private void ContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ContraFocusLost
    // Quitamos el borde rojo y lo dejamos "invisible"
    Contra.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    if (Contra.getText().equals("Contraseña") || Contra.getForeground().equals(Color.RED)) {
        Contra.setText("");
        Contra.setForeground(Color.BLACK);
        Contra.setEchoChar('•');
    }
    }//GEN-LAST:event_ContraFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // 1. Usamos Config.HOST y Config.PUERTO para la conexión
        try (Socket socket = new Socket(Config.HOST, Config.PUERTO);
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
             DataInputStream entrada = new DataInputStream(socket.getInputStream())) {

            String usuario = Usuario.getText().trim();
            String password = new String(Contra.getPassword());
            boolean recordar = checkrecordar.isSelected(); 

            // Limpieza visual previa
            pintarBordeRojo(Usuario, false);
            pintarBordeRojo(Contra, false);

            if (usuario.isEmpty() || password.isEmpty()) {
                if(usuario.isEmpty()) pintarBordeRojo(Usuario, true);
                if(password.isEmpty()) pintarBordeRojo(Contra, true);
                return;
            }

            // 2. Enviamos la petición al servidor
            salida.writeUTF("LOGIN," + usuario + "," + password + "," + recordar);

            // 3. Recibimos la respuesta (Token, "OK" o "ERROR")
            String respuesta = entrada.readUTF();

            if (respuesta.equals("ERROR")) {
                pintarBordeRojo(Usuario, true);
                pintarBordeRojo(Contra, true);
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            } else {
                // 4. Si el servidor mandó un Token, lo guardamos localmente
                if (!respuesta.equals("OK")) {
                    Clases.ManejadorSesion.guardarSesion(respuesta);
                }

                // 5. Abrimos tu ventana 'Menu'
                Bienvenido principal = new Bienvenido(); // Cambiado a Menu como pediste
                principal.setVisible(true);

                this.dispose(); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar con el servidor: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void pintarBordeRojo(javax.swing.JTextField campo, boolean error) {
        if (error) {
            // Ponemos un fondo blanco sólido para tapar lo que haya atrás (la línea negra)
            campo.setOpaque(true);
            campo.setBackground(Color.WHITE);
            // El recuadro rojo
            campo.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED, 2));
            campo.setForeground(Color.RED);
        } else {
            // Cuando no hay error, lo hacemos transparente para que se vea la línea negra original
            campo.setOpaque(false); 
            campo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
            campo.setForeground(Color.BLACK);
        }
        campo.repaint();
    }
    
    private void OlvidarCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OlvidarCMouseEntered
        OlvidarC.setForeground(new Color(153,153,153));    
    }//GEN-LAST:event_OlvidarCMouseEntered

    private void OlvidarCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OlvidarCMouseExited
        OlvidarC.setForeground(Color.black);
        
    }//GEN-LAST:event_OlvidarCMouseExited

    private void OlvidarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OlvidarCActionPerformed
        // Abrimos la conexión solo para el proceso de recuperación
        try (Socket socket = new Socket(Config.HOST, Config.PUERTO);
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
             DataInputStream entrada = new DataInputStream(socket.getInputStream())) {

            // Llamamos a la alerta pasando los flujos que acabamos de crear
            // Importante: El flujo se mantendrá abierto mientras la alerta esté visible
            SyncroAlert.showRecuperacion(this, 1, entrada, salida);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage(), 
                                          "Error de Servidor", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_OlvidarCActionPerformed

    private void MostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarActionPerformed
     if (mostrar) {
         Contra.setEchoChar('•');
         mostrar = false;
         
    java.awt.Image img = new javax.swing.ImageIcon(getClass().getResource("/Iconos/Ocultar.png")).getImage();
    java.awt.Image newimg = img.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
    javax.swing.ImageIcon imageIcon = new javax.swing.ImageIcon(newimg);

    Mostrar.setIcon(imageIcon);
    
    } else {
        Contra.setEchoChar((char)0);
        mostrar = true;
        
    java.awt.Image img = new javax.swing.ImageIcon(getClass().getResource("/Iconos/Mostrar.png")).getImage();
    java.awt.Image newimg = img.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
    javax.swing.ImageIcon imageIcon = new javax.swing.ImageIcon(newimg);

    Mostrar.setIcon(imageIcon);
    }
    }//GEN-LAST:event_MostrarActionPerformed


    
    
    
    
    
    
    
    
    private static boolean validarSesionConServidor(String token) {
        try (Socket socket = new Socket()) {
            // Timeout de 3 segundos — si el servidor no responde, seguimos al Login normal
            socket.connect(new java.net.InetSocketAddress(Config.HOST, Config.PUERTO), 3000);

            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            DataInputStream entrada = new DataInputStream(socket.getInputStream());

            salida.writeUTF("AUTO_LOGIN," + token);
            boolean resultado = entrada.readBoolean();

            // Cerrar limpiamente
            salida.writeUTF("SALIR");
            return resultado;

        } catch (Exception e) {
            System.out.println("Servidor no disponible, yendo al Login: " + e.getMessage());
            return false; // Si no hay servidor, simplemente va al Login
        }
    }
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
    // 1. Intentar leer el token guardado en la PC
    String tokenGuardado = Clases.ManejadorSesion.leerToken();

    if (tokenGuardado != null) {
        // 2. Si existe un token, preguntar al servidor si aún es válido
        // Aquí deberías abrir un socket rápido para validar
        boolean sesionValida = validarSesionConServidor(tokenGuardado);

        if (sesionValida) {
            // 3. Si el servidor dice que OK, saltamos al Menú
            Bienvenido splash = new Bienvenido();
            splash.setVisible(true);
        } else {
            // Si el token expiró (pasaron los 15 días), borramos y vamos al Login
            Clases.ManejadorSesion.borrarSesion();
            new Login().setVisible(true);
        }
    } else {
        // 4. Si no hay archivo, Login normal
        new Login().setVisible(true);
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField Contra;
    private javax.swing.JLabel Datos;
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel Logo;
    private javax.swing.JButton Mostrar;
    private javax.swing.JButton OlvidarC;
    private javax.swing.JTextField Usuario;
    private javax.swing.JCheckBox checkrecordar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
