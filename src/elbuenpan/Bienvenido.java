package elbuenpan;
public class Bienvenido extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Bienvenido.class.getName());

    public Bienvenido() {
        initComponents();
        setLocationRelativeTo(null);
        this.setResizable(false);
        
        java.awt.Image bien = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bienvenido.jpeg")).getImage();
        java.awt.Image bien2 = bien.getScaledInstance(1150, 760, java.awt.Image.SCALE_SMOOTH);
        javax.swing.ImageIcon imageIcon5 = new javax.swing.ImageIcon(bien2);

        Bienvenido.setIcon(imageIcon5);
        
        setLocationRelativeTo(null); // Centrar la ventana

        // Creamos un Timer de 5000 milisegundos (5 segundos)
        javax.swing.Timer timer = new javax.swing.Timer(3000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Al cumplirse el tiempo:
                new Menu().setVisible(true); // Abrimos el Menu
                dispose(); // Cerramos este frame (Bienvenido)
            }
        });

        timer.setRepeats(false); // Importante: que solo ocurra una vez
        timer.start();
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Bienvenido = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1150, 760));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Bienvenido, javax.swing.GroupLayout.DEFAULT_SIZE, 1253, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Bienvenido, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Bienvenido().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bienvenido;
    // End of variables declaration//GEN-END:variables
}
