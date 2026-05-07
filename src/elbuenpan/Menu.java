package elbuenpan;

import java.awt.Color;
import java.awt.Font;
import Clases.ConfiguradorBotones;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;


public class Menu extends javax.swing.JFrame {
    
    private Clases.GestorCobro gestorCobro;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Menu.class.getName());
    
    
    private double totalVentaActual = 0.0;
    private String efectivoIngresado = "";
    
    public Menu() {

        
        
        initComponents();
        setLocationRelativeTo(null);
        this.setResizable(false);
        
        
        UIManager.put("ToolTip.background", new Color(255, 235, 200)); 
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(100, 50, 0), 1));
        UIManager.put("ToolTip.foreground", new Color(60, 30, 0));
        UIManager.put("ToolTip.font", new Font("SansSerif", Font.PLAIN, 18));
        
        BuscarProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        BuscarEncargo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        
        Clases.TablaVentas.configurar(TablaVentas);
        Clases.TablaVentas.configurar(TablaVentas);

        gestorCobro = new Clases.GestorCobro(this, txtPantallaCobro, TablaVentas);

        txtPantallaCobro.setBackground(java.awt.Color.WHITE);
        txtPantallaCobro.setForeground(java.awt.Color.BLACK);
        txtPantallaCobro.setEditable(false);
        txtPantallaCobro.setFocusable(false);
        txtPantallaCobro.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 28));
        txtPantallaCobro.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 120, 10, 10));

        txtPantallaCobro.setText(" TOTAL: $ 0,00\n EFECT: $ 0,00");

        Clases.EstiloTabla.aplicar(TablaInventario, TablaEncargo, TablaCorte, TablaMerma);
        
        /*----------------------------------------*/
        /* Codigo para las imagenes de la interfaz*/
        /*----------------------------------------*/
        
        java.awt.Image img5 = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo blanco.png")).getImage();
        java.awt.Image newimg5 = img5.getScaledInstance(85, 80, java.awt.Image.SCALE_SMOOTH);
        javax.swing.ImageIcon imageIcon5 = new javax.swing.ImageIcon(newimg5);

        Logo3.setIcon(imageIcon5);
        
        Pestañas.setSelectedIndex(0);


        
        
        
        
        
        /*----------------------------------------*/
        /* Codigo para las imagenes de los botones*/
        /*   Establecimiento de la base de datos  */
        /*----------------------------------------*/

        actualizarTodo();

        
        
        
        
        
        
        
        
        /*------------------------------------------*/
        /* Funciones de los botones de caga interfaz*/
        /*------------------------------------------*/

        boton1.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("1"));
        boton2.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("2"));
        boton3.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("3"));
        boto4.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("4")); 
        boton5.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("5"));
        boton6.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("6"));
        boton7.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("7"));
        boton8.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("8"));
        boton9.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("9"));
        boton0.addActionListener(e -> gestorCobro.agregarNumeroEfectivo("0"));
        
        Cantidad.addActionListener(e -> gestorCobro.cambiarCantidadSeleccionada());
        Borrar.addActionListener(e -> gestorCobro.eliminarProductoSeleccionado());

        // Botón Agregar (Verde)
        Agregar.addActionListener(e -> {
            Clases.TablaInventario.agregarStockRapido(this, TablaInventario);
            actualizarTodo(); // ← esto recarga tabla inventario Y botones de venta
        });

        Modificar.addActionListener(e -> {
            Clases.TablaInventario.modificarProducto(this, TablaInventario);
            actualizarTodo(); // ← cambiar cargarInventario() por actualizarTodo()
        });

        Eliminar.addActionListener(e -> {
            Clases.TablaInventario.restarStockRapido(this, TablaInventario);
            actualizarTodo(); // ← cambiar cargarInventario() por actualizarTodo()
        });

// EL BUSCADOR (Sigue funcionando igual para filtrar)
Clases.TablaInventario.configurarBuscador(TablaInventario, BuscarProducto);
        
        AgregarEncargo.addActionListener(e -> Clases.TablaEncargos.agregarEncargo(this, TablaEncargo));
        ModificarEncargo.addActionListener(e -> Clases.TablaEncargos.modificarEncargo(this, TablaEncargo));
        EliminarEncargo.addActionListener(e -> Clases.TablaEncargos.eliminarEncargo(this, TablaEncargo));
        Clases.TablaEncargos.configurarBuscador(TablaEncargo, BuscarEncargo);
        
        GenerarReporteCorte.addActionListener(e -> {
            Clases.TablaCorte.generarReporte(TablaCorte, TablaEncargo, jTextArea2, this, this);
        });
        
        GenerarReporteMerma.addActionListener(e -> {
            Clases.TablaMerma.generarReporte(TablaMerma, jTextArea1);
        });
        
        
        jButton19.addActionListener(e -> {
            if (!jTextArea1.getText().contains(".")) {
                gestorCobro.agregarNumeroEfectivo(".");
            }
        });
        
        borrar.addActionListener(e -> gestorCobro.borrarNumeroEfectivo());
        Pagar.addActionListener(e -> gestorCobro.procesarPago());
        
        jScrollPane3.getVerticalScrollBar().setUnitIncrement(20);
        Venta.setBackground(java.awt.Color.decode("#566174"));
        
        jButton17.addActionListener(e -> {
            java.awt.Font fuenteGrande = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 22);
            javax.swing.UIManager.put("OptionPane.messageFont", fuenteGrande);
            javax.swing.UIManager.put("OptionPane.buttonFont", fuenteGrande);

        if (TablaVentas.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "La lista ya está vacía", 
                "Aviso", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que deseas borrar toda la venta actual?", 
            "Confirmar Vaciado", 
            javax.swing.JOptionPane.YES_NO_OPTION, 
            javax.swing.JOptionPane.WARNING_MESSAGE);

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) TablaVentas.getModel();
            modelo.setRowCount(0);
            gestorCobro.setTotalVenta(0.0);
            txtPantallaCobro.setText(" TOTAL: $ 0,00\n EFECT: $ 0,00");
        }
    });
        
        

        /*--------------------------------*/
        /* Configuracion tabla inventario */
        /*--------------------------------*/
        
        
        // 1. Definición del modelo con 5 columnas (la última es para el ID)
        javax.swing.table.DefaultTableModel modeloInventario = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Productos", "Cantidad", "Venta", "Precio unitario", "ID"} // Añadimos ID
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda se edita escribiendo directamente
            }
        };

        // 2. Asignar el modelo a la tabla
        TablaInventario.setModel(modeloInventario);

        // 3. Ocultar la columna ID (opcional, para que el usuario no vea el número de ID)
        TablaInventario.getColumnModel().getColumn(4).setMinWidth(0);
        TablaInventario.getColumnModel().getColumn(4).setMaxWidth(0);
        TablaInventario.getColumnModel().getColumn(4).setPreferredWidth(0);

        // 4. BORRAR los addRow manuales y llamar al servidor
        cargarInventario();
        
        
        /*------------------------------*/
        /* Configuracion tabla encargos */
        /*------------------------------*/
    
        javax.swing.table.DefaultTableModel modeloEncargos = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Cliente", "Productos", "Total", "Anticipo", "Restante", "Fecha", "ID"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        TablaEncargo.setModel(modeloEncargos);

        // Ocultar columna ID
        TablaEncargo.getColumnModel().getColumn(6).setMinWidth(0);
        TablaEncargo.getColumnModel().getColumn(6).setMaxWidth(0);
        TablaEncargo.getColumnModel().getColumn(6).setPreferredWidth(0);

        // Cargar datos desde BD
        Clases.TablaEncargos.cargarEncargos(TablaEncargo);

        
        
        

        
        
        
        
        
        
        
        /*----------------------------*/
        /* Configuracion tabla merma  */
        /*----------------------------*/

        javax.swing.table.DefaultTableModel modeloMerma = new javax.swing.table.DefaultTableModel(
            new Object [][] {}, 
            new String [] {"Productos", "Merma exhibición", "Merma recepción"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        // Ejemplo de la tabla
        TablaMerma.setModel(modeloMerma);

        modeloMerma.addRow(new Object[]{"Panes", 40, 4});
        modeloMerma.addRow(new Object[]{"Tortas Grandes", 0, 4});
        modeloMerma.addRow(new Object[]{"Cocotazo", 45, 0});
        modeloMerma.addRow(new Object[]{"Súper", 3, 1});
        modeloMerma.addRow(new Object[]{"Pata de JYQ", 9, 3});
        modeloMerma.addRow(new Object[]{"Bolita de Filadelfia", 6, 0});
        modeloMerma.addRow(new Object[]{"Chispitas", 3, 1});
        modeloMerma.addRow(new Object[]{"Panque de Queso Bola", 6, 1});
        modeloMerma.addRow(new Object[]{"Pan de Nata", 5, 0});
        modeloMerma.addRow(new Object[]{"Trenzas", 8, 2});
        modeloMerma.addRow(new Object[]{"Pan de Leche", 0, 3});

    }

    
    
    
    
    
    
        /*-----------------------------------------------------*/
        /* Conexion del inventario con la base de datso     */
        /*-----------------------------------------------------*/
    
    private void cargarInventario() {
        try (java.net.Socket socket = new java.net.Socket(Config.HOST, Config.PUERTO);
             java.io.DataOutputStream salida = new java.io.DataOutputStream(socket.getOutputStream());
             java.io.DataInputStream entrada = new java.io.DataInputStream(socket.getInputStream())) {

            salida.writeUTF("MOSTRAR_TODO");
            String respuesta = entrada.readUTF();

            javax.swing.table.DefaultTableModel modeloInventario = (javax.swing.table.DefaultTableModel) TablaInventario.getModel();
            modeloInventario.setRowCount(0); 

            if (!respuesta.isEmpty()) {
                String[] filas = respuesta.split(";");
                for (String f : filas) {
                    String[] d = f.split("-"); 
                    // d[0]=ID, d[1]=Nombre, d[2]=Precio, d[3]=Stock, d[4]=Venta
                    // Acomodamos según tus columnas:
                    modeloInventario.addRow(new Object[]{
                        d[1], // Productos
                        d[3], // Cantidad (Stock)
                        d[4], // Venta
                        d[2], // Precio unitario
                        d[0]  // ID (Oculto)
                    });
                }
            }
        } catch (Exception e) { 
            javax.swing.JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    public void actualizarTodo() {
        try (java.net.Socket socket = new java.net.Socket(Config.HOST, Config.PUERTO);
             java.io.DataOutputStream salida = new java.io.DataOutputStream(socket.getOutputStream());
             java.io.DataInputStream entrada = new java.io.DataInputStream(socket.getInputStream())) {

            salida.writeUTF("MOSTRAR_TODO");
            String respuesta = entrada.readUTF();

            if (respuesta != null && !respuesta.isEmpty()) {
                String[] registros = respuesta.split(";");

                // --- LISTAS ---
                java.util.List<Integer> listaIds    = new java.util.ArrayList<>();
                java.util.List<String>  listaNombres = new java.util.ArrayList<>();
                java.util.List<Double>  listaPrecios = new java.util.ArrayList<>();
                java.util.List<Integer> listaStock   = new java.util.ArrayList<>();
                java.util.List<Integer> listaVenta   = new java.util.ArrayList<>();

                for (String reg : registros) {
                    String[] d = reg.split("-");
                    if (d.length >= 5) {
                        listaIds.add(Integer.parseInt(d[0]));
                        listaNombres.add(d[1]);
                        listaPrecios.add(Double.parseDouble(d[2]));
                        listaStock.add(Integer.parseInt(d[3]));
                        listaVenta.add(Integer.parseInt(d[4]));
                    }
                }

                String[] nombresBD = listaNombres.toArray(new String[0]);
                double[] preciosBD = listaPrecios.stream().mapToDouble(Double::doubleValue).toArray();

                // --- ESTO PONE LAS IMÁGENES ---
                ConfiguradorBotones.configurar(this, nombresBD, preciosBD);

                // --- LLENAR TABLA INVENTARIO ---
                DefaultTableModel modeloInventario = (DefaultTableModel) TablaInventario.getModel();
                modeloInventario.setRowCount(0);
                for (int i = 0; i < listaIds.size(); i++) {
                    modeloInventario.addRow(new Object[]{
                        listaNombres.get(i),
                        listaStock.get(i),
                        listaVenta.get(i),
                        listaPrecios.get(i),
                        listaIds.get(i)
                    });
                }

                // --- CONFIGURAR BOTONES DE VENTA ---
                javax.swing.JButton[] botonesPan = {
                    Pan1, Pan2, Pan3, Pan4, Pan5, Pan6,
                    Pan7, Pan8, Pan9, Pan10, Pan11, Pan12
                };

                for (int i = 0; i < botonesPan.length; i++) {
                    if (i < nombresBD.length) {
                        final int    idF    = listaIds.get(i);
                        final String nomF   = nombresBD[i];
                        final double preF   = preciosBD[i];
                        final int    stockF = listaStock.get(i);

                        // Quitar listeners viejos
                        for (java.awt.event.ActionListener al : botonesPan[i].getActionListeners())
                            botonesPan[i].removeActionListener(al);

                        if (stockF <= 0) {
                            botonesPan[i].setEnabled(false);
                            botonesPan[i].setText("<html><center><b style='color:red;'>AGOTADO</b><br>" 
                                + nomF + "</center></html>");
                        } else {
                            botonesPan[i].setEnabled(true);
                            botonesPan[i].setText("<html><center><b>" + nomF + "</b><br>$" 
                                + preF + "<br><small>Stock: " + stockF + "</small></center></html>");
                            botonesPan[i].addActionListener(e -> {
                                Clases.TablaVentas.agregarProducto(TablaVentas, idF, nomF, preF);
                                gestorCobro.setTotalVenta(Clases.TablaVentas.calcularTotal(TablaVentas));
                            });
                        }
                    } else {
                        botonesPan[i].setEnabled(false);
                        botonesPan[i].setText("---");
                        botonesPan[i].setIcon(null);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al sincronizar inventario:\n" + e.getMessage(),
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
    
    // Esto permite que GestorCobro vea la tabla de inventario para comparar el stock
    public JTable getTablaInventario() {
        return TablaInventario; // Asegúrate de que este sea el nombre de tu tabla de la izquierda
    }
    
    
    
    
    
    
        /*-----------------------------------------------------*/
        /* Codigo para los colores de los botones de los menus */
        /*-----------------------------------------------------*/
    
     private void resetMenu(){
    Venta.setBackground(java.awt.Color.decode("#363F4E"));
    Inventario.setBackground(java.awt.Color.decode("#363F4E"));
    Encargos.setBackground(java.awt.Color.decode("#363F4E"));
    Corte.setBackground(java.awt.Color.decode("#363F4E"));
    Merma.setBackground(java.awt.Color.decode("#363F4E"));
    Cerrarsesion.setBackground(java.awt.Color.decode("#363F4E"));
    }  

     
     
     
     
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        Venta = new javax.swing.JButton();
        Inventario = new javax.swing.JButton();
        Encargos = new javax.swing.JButton();
        Corte = new javax.swing.JButton();
        Cerrarsesion = new javax.swing.JButton();
        Panel_Usuario = new javax.swing.JPanel();
        Logo3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Merma = new javax.swing.JButton();
        Pestañas = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        boton1 = new javax.swing.JButton();
        boton2 = new javax.swing.JButton();
        boton3 = new javax.swing.JButton();
        Cantidad = new javax.swing.JButton();
        Borrar = new javax.swing.JButton();
        boton6 = new javax.swing.JButton();
        boton5 = new javax.swing.JButton();
        boto4 = new javax.swing.JButton();
        boton7 = new javax.swing.JButton();
        boton8 = new javax.swing.JButton();
        boton9 = new javax.swing.JButton();
        Pagar = new javax.swing.JButton();
        borrar = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        boton0 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaVentas = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        Pan2 = new javax.swing.JButton();
        Pan3 = new javax.swing.JButton();
        Pan1 = new javax.swing.JButton();
        Pan4 = new javax.swing.JButton();
        Pan5 = new javax.swing.JButton();
        Pan6 = new javax.swing.JButton();
        Pan7 = new javax.swing.JButton();
        Pan8 = new javax.swing.JButton();
        Pan9 = new javax.swing.JButton();
        Pan10 = new javax.swing.JButton();
        Pan11 = new javax.swing.JButton();
        Pan12 = new javax.swing.JButton();
        Pan13 = new javax.swing.JButton();
        Pan14 = new javax.swing.JButton();
        Pan15 = new javax.swing.JButton();
        Pan16 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtPantallaCobro = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Agregar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        BuscarProducto = new javax.swing.JTextField();
        Eliminar = new javax.swing.JButton();
        Modificar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaInventario = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaEncargo = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        AgregarEncargo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        BuscarEncargo = new javax.swing.JTextField();
        EliminarEncargo = new javax.swing.JButton();
        ModificarEncargo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TablaCorte = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        GenerarReporteCorte = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaMerma = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        GenerarReporteMerma = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(54, 63, 78));
        jPanel3.setPreferredSize(new java.awt.Dimension(290, 899));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Venta.setBackground(new java.awt.Color(54, 63, 78));
        Venta.setFont(new java.awt.Font("Aileron Black", 1, 36)); // NOI18N
        Venta.setForeground(new java.awt.Color(255, 255, 255));
        Venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/venta.png"))); // NOI18N
        Venta.setText("    Venta");
        Venta.setToolTipText("Piso de venta actual");
        Venta.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Venta.setBorderPainted(false);
        Venta.setContentAreaFilled(false);
        Venta.setFocusPainted(false);
        Venta.setMargin(new java.awt.Insets(10, 30, 30, 14));
        Venta.setMinimumSize(new java.awt.Dimension(167, 46));
        Venta.setOpaque(true);
        Venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentaActionPerformed(evt);
            }
        });
        jPanel3.add(Venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 240, 370, 62));
        Venta.getAccessibleContext().setAccessibleDescription("Hola\n");

        Inventario.setBackground(new java.awt.Color(54, 63, 78));
        Inventario.setFont(new java.awt.Font("Aileron Black", 1, 36)); // NOI18N
        Inventario.setForeground(new java.awt.Color(255, 255, 255));
        Inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Inventario.png"))); // NOI18N
        Inventario.setText("    Inventario");
        Inventario.setToolTipText("Administrar existencias y precios");
        Inventario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Inventario.setBorderPainted(false);
        Inventario.setContentAreaFilled(false);
        Inventario.setFocusPainted(false);
        Inventario.setOpaque(true);
        Inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InventarioActionPerformed(evt);
            }
        });
        jPanel3.add(Inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 290, 62));

        Encargos.setBackground(new java.awt.Color(54, 63, 78));
        Encargos.setFont(new java.awt.Font("Aileron Black", 1, 36)); // NOI18N
        Encargos.setForeground(new java.awt.Color(255, 255, 255));
        Encargos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Encargos.png"))); // NOI18N
        Encargos.setText("    Encargos");
        Encargos.setToolTipText("Ver pedidos especiales y anticipos");
        Encargos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Encargos.setBorderPainted(false);
        Encargos.setContentAreaFilled(false);
        Encargos.setFocusPainted(false);
        Encargos.setOpaque(true);
        Encargos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EncargosActionPerformed(evt);
            }
        });
        jPanel3.add(Encargos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 290, 66));

        Corte.setBackground(new java.awt.Color(54, 63, 78));
        Corte.setFont(new java.awt.Font("Aileron Black", 1, 36)); // NOI18N
        Corte.setForeground(new java.awt.Color(255, 255, 255));
        Corte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Corte.png"))); // NOI18N
        Corte.setText("    Corte");
        Corte.setToolTipText("Realizar el cierre de caja del día");
        Corte.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Corte.setBorderPainted(false);
        Corte.setContentAreaFilled(false);
        Corte.setFocusPainted(false);
        Corte.setOpaque(true);
        Corte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CorteActionPerformed(evt);
            }
        });
        jPanel3.add(Corte, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 630, 380, 66));

        Cerrarsesion.setBackground(new java.awt.Color(54, 63, 78));
        Cerrarsesion.setFont(new java.awt.Font("Aileron Black", 1, 24)); // NOI18N
        Cerrarsesion.setForeground(new java.awt.Color(255, 255, 255));
        Cerrarsesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Cerrar sesion.png"))); // NOI18N
        Cerrarsesion.setText("Cerrar sesion");
        Cerrarsesion.setToolTipText("Registrar pan dañado o no vendido");
        Cerrarsesion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Cerrarsesion.setBorderPainted(false);
        Cerrarsesion.setContentAreaFilled(false);
        Cerrarsesion.setFocusPainted(false);
        Cerrarsesion.setOpaque(true);
        Cerrarsesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarsesionActionPerformed(evt);
            }
        });
        jPanel3.add(Cerrarsesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 900, 350, 66));

        Panel_Usuario.setBackground(new java.awt.Color(43, 43, 43));

        jLabel4.setFont(new java.awt.Font("Aileron", 2, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Bienvenido");

        jLabel1.setFont(new java.awt.Font("Aileron Black", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("---- Usuario ----");

        javax.swing.GroupLayout Panel_UsuarioLayout = new javax.swing.GroupLayout(Panel_Usuario);
        Panel_Usuario.setLayout(Panel_UsuarioLayout);
        Panel_UsuarioLayout.setHorizontalGroup(
            Panel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_UsuarioLayout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addGroup(Panel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_UsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_UsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_UsuarioLayout.createSequentialGroup()
                        .addComponent(Logo3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))))
        );
        Panel_UsuarioLayout.setVerticalGroup(
            Panel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_UsuarioLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(Logo3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.add(Panel_Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 170));

        Merma.setBackground(new java.awt.Color(54, 63, 78));
        Merma.setFont(new java.awt.Font("Aileron Black", 1, 36)); // NOI18N
        Merma.setForeground(new java.awt.Color(255, 255, 255));
        Merma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Merma.png"))); // NOI18N
        Merma.setText("    Merma");
        Merma.setToolTipText("Registrar pan dañado o no vendido");
        Merma.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Merma.setBorderPainted(false);
        Merma.setContentAreaFilled(false);
        Merma.setFocusPainted(false);
        Merma.setOpaque(true);
        Merma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MermaActionPerformed(evt);
            }
        });
        jPanel3.add(Merma, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 750, 350, 66));

        getContentPane().add(jPanel3, java.awt.BorderLayout.LINE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        boton1.setBackground(new java.awt.Color(189, 183, 183));
        boton1.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton1.setText("1");

        boton2.setBackground(new java.awt.Color(189, 183, 183));
        boton2.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton2.setText("2");

        boton3.setBackground(new java.awt.Color(189, 183, 183));
        boton3.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton3.setText("3");

        Cantidad.setBackground(new java.awt.Color(153, 153, 255));
        Cantidad.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        Cantidad.setText("Cant");
        Cantidad.setToolTipText("Cambiar la cantidad del producto seleccionado");
        Cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CantidadActionPerformed(evt);
            }
        });

        Borrar.setBackground(new java.awt.Color(255, 102, 102));
        Borrar.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        Borrar.setText("Borrar");
        Borrar.setToolTipText("Eliminar el producto seleccionado de la lista");

        boton6.setBackground(new java.awt.Color(189, 183, 183));
        boton6.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton6.setText("6");

        boton5.setBackground(new java.awt.Color(189, 183, 183));
        boton5.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton5.setText("5");

        boto4.setBackground(new java.awt.Color(189, 183, 183));
        boto4.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boto4.setText("4");

        boton7.setBackground(new java.awt.Color(189, 183, 183));
        boton7.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton7.setText("7");

        boton8.setBackground(new java.awt.Color(189, 183, 183));
        boton8.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton8.setText("8");

        boton9.setBackground(new java.awt.Color(189, 183, 183));
        boton9.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton9.setText("9");

        Pagar.setBackground(new java.awt.Color(153, 255, 153));
        Pagar.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        Pagar.setText("Pagar");
        Pagar.setToolTipText("Finalizar la venta y calcular cambio");

        borrar.setBackground(new java.awt.Color(189, 183, 183));
        borrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/borrar.png"))); // NOI18N
        borrar.setToolTipText("Borrar último número tecleado");

        jButton19.setBackground(new java.awt.Color(189, 183, 183));
        jButton19.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        jButton19.setText(".");

        boton0.setBackground(new java.awt.Color(189, 183, 183));
        boton0.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        boton0.setText("0");

        jButton17.setBackground(new java.awt.Color(255, 153, 51));
        jButton17.setFont(new java.awt.Font("Aileron", 1, 18)); // NOI18N
        jButton17.setText("VACIAR");

        TablaVentas.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        TablaVentas.setFont(new java.awt.Font("Adwaita Sans", 1, 14)); // NOI18N
        TablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TablaVentas.setToolTipText("Aquí se muestran los productos seleccionados para la venta y puedes seleccionarlos haciendo clic sobre ellos para cambiar su cantidad o borrarlos de la lista según lo necesites.");
        jScrollPane1.setViewportView(TablaVentas);

        jPanel2.setBackground(new java.awt.Color(254, 249, 231));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Pan13, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Pan14, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Pan15, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Pan16, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(Pan9, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Pan10, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Pan11, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Pan12, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(Pan5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Pan6, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Pan7, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Pan8, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(Pan1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Pan2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Pan3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Pan4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(27, 56, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Pan4, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Pan8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan7, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan6, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan5, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Pan12, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan11, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan10, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan9, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Pan16, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan15, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan14, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pan13, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel2);

        txtPantallaCobro.setColumns(1);
        txtPantallaCobro.setRows(2);
        txtPantallaCobro.setToolTipText("Resumen de la cuenta actual e ingreso de efectivo");
        jScrollPane9.setViewportView(txtPantallaCobro);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(boton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boton0, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(boto4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boton5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boton6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(boton7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(boton9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boto4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boton0, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );

        Pestañas.addTab("Venta", jPanel6);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        Agregar.setBackground(new java.awt.Color(204, 255, 204));
        Agregar.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        Agregar.setText("Agregar");
        Agregar.setToolTipText("Registrar un nuevo tipo de pan con su precio y cantidad inicial");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Inconsolata Condensed", 1, 48)); // NOI18N
        jLabel2.setText("Buscar producto");

        BuscarProducto.setFont(new java.awt.Font("Adwaita Sans", 0, 24)); // NOI18N
        BuscarProducto.setToolTipText("Escribe el nombre del pan para filtrar la lista rápidamente");
        BuscarProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        Eliminar.setBackground(new java.awt.Color(255, 153, 153));
        Eliminar.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        Eliminar.setText("Eliminar");
        Eliminar.setToolTipText("Quitar permanentemente el producto del sistema");

        Modificar.setBackground(new java.awt.Color(204, 204, 255));
        Modificar.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        Modificar.setText("Modificar");
        Modificar.setToolTipText("Cambiar el nombre o el precio unitario del producto seleccionado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BuscarProducto)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(54, 54, 54))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        TablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Productos", "Cantidad", "Venta", "Precio unitario"
            }
        ));
        TablaInventario.setToolTipText("Lista de existencias actuales. Haz clic en un producto para habilitar las opciones de modificar o eliminar");
        jScrollPane2.setViewportView(TablaInventario);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );

        Pestañas.addTab("Inventario", jPanel7);

        TablaEncargo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cliente", "Productos", "Total", "Anticipo", "Restante", "Fecha"
            }
        ));
        TablaEncargo.setToolTipText("Lista de pedidos pendientes. Verifica la columna 'Restante' para saber cuánto debe pagar el cliente al recoger");
        jScrollPane5.setViewportView(TablaEncargo);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));

        AgregarEncargo.setBackground(new java.awt.Color(204, 255, 204));
        AgregarEncargo.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        AgregarEncargo.setText("Agregar");
        AgregarEncargo.setToolTipText("Registrar un nuevo pedido especial, definiendo el anticipo y la fecha de entrega");

        jLabel3.setFont(new java.awt.Font("Inconsolata Condensed", 1, 48)); // NOI18N
        jLabel3.setText("Buscar Encargo");

        BuscarEncargo.setFont(new java.awt.Font("Adwaita Sans", 0, 24)); // NOI18N
        BuscarEncargo.setToolTipText("Busca pedidos por nombre del cliente o fecha de entrega");
        BuscarEncargo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        EliminarEncargo.setBackground(new java.awt.Color(255, 153, 153));
        EliminarEncargo.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        EliminarEncargo.setText("Eliminar");
        EliminarEncargo.setToolTipText("Cancelar el encargo seleccionado y quitarlo de la lista");

        ModificarEncargo.setBackground(new java.awt.Color(204, 204, 255));
        ModificarEncargo.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        ModificarEncargo.setText("Modificar");
        ModificarEncargo.setToolTipText("Actualizar datos del pedido, como aumentar el anticipo o cambiar la fecha de entrega");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BuscarEncargo)
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EliminarEncargo, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ModificarEncargo, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgregarEncargo, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(54, 54, 54))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BuscarEncargo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(AgregarEncargo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(ModificarEncargo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(EliminarEncargo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5)
        );

        Pestañas.addTab("Encargo", jPanel11);

        TablaCorte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Productos", "Cantidad", "Venta", "Sobrante", "Precio unitario", "Total"
            }
        ));
        TablaCorte.setToolTipText("Detalle por producto: Cantidad inicial vs. Vendidos. El 'Total' es lo que debería haber en caja por cada tipo de pan");
        jScrollPane7.setViewportView(TablaCorte);

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));

        GenerarReporteCorte.setBackground(new java.awt.Color(204, 255, 204));
        GenerarReporteCorte.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        GenerarReporteCorte.setText("Generar Reporte");
        GenerarReporteCorte.setToolTipText("Calcular el total de ventas del día y mostrar el resumen detallado en el cuadro de texto");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setToolTipText("Vista previa del ticket de cierre. Aquí aparecerá el resumen listo para imprimir o guardar");
        jScrollPane8.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(GenerarReporteCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(GenerarReporteCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );

        Pestañas.addTab("Corte", jPanel4);

        TablaMerma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Productos", "Merma exhibición", "Merma recepción"
            }
        ));
        TablaMerma.setToolTipText("Registro de piezas no aptas para la venta. Los datos aquí restan directamente al inventario final");
        jScrollPane4.setViewportView(TablaMerma);

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        GenerarReporteMerma.setBackground(new java.awt.Color(204, 255, 204));
        GenerarReporteMerma.setFont(new java.awt.Font("Aileron", 1, 36)); // NOI18N
        GenerarReporteMerma.setText("Generar Reporte");
        GenerarReporteMerma.setToolTipText("Crear un resumen de pérdidas totales para ajustar el inventario y analizar desperdicios");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setToolTipText("Resumen detallado del desperdicio por categoría listo para revisión");
        jScrollPane6.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(GenerarReporteMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(GenerarReporteMerma, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );

        Pestañas.addTab("Merma", jPanel10);

        getContentPane().add(Pestañas, java.awt.BorderLayout.CENTER);
        Pestañas.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int placement, int horizRunCount, int maxTabHeight) {
                return 0;
            }
            @Override
            protected void paintTabArea(java.awt.Graphics g, int tabPlacement, int selectedIndex) {
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentaActionPerformed
        resetMenu();
        Venta.setBackground(java.awt.Color.decode("#566174"));

        Pestañas.setSelectedIndex(0);
    }//GEN-LAST:event_VentaActionPerformed

    private void InventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InventarioActionPerformed
        resetMenu();
        Inventario.setBackground(java.awt.Color.decode("#566174"));

        Pestañas.setSelectedIndex(1);
    }//GEN-LAST:event_InventarioActionPerformed

    private void EncargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EncargosActionPerformed
        resetMenu();
        Encargos.setBackground(java.awt.Color.decode("#566174"));
        
        Pestañas.setSelectedIndex(2);
    }//GEN-LAST:event_EncargosActionPerformed

                /*----------------------------*/
        /* Configuracion tabla corte  */
        /*----------------------------*/

        // En el listener del botón Corte:
        // En el listener del botón Corte:
    
    private void CorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CorteActionPerformed
        resetMenu();
        Corte.setBackground(java.awt.Color.decode("#566174"));
        Pestañas.setSelectedIndex(3);

        if (Clases.TablaCorte.corteYaRealizado()) {
            // Forzar limpieza de tabla
            javax.swing.table.DefaultTableModel m = 
                (javax.swing.table.DefaultTableModel) TablaCorte.getModel();
            m.setRowCount(0);
            TablaCorte.repaint();
            jTextArea2.setText("\n   El corte del día ya fue realizado.");
        } else {
            Clases.TablaCorte.cargarDatosCorte(TablaCorte);
        }
    }//GEN-LAST:event_CorteActionPerformed

    private void CerrarsesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarsesionActionPerformed
    resetMenu();
        Cerrarsesion.setBackground(java.awt.Color.decode("#566174"));

        java.awt.Font fuenteGrande = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 22);
        javax.swing.UIManager.put("OptionPane.messageFont", fuenteGrande);
        javax.swing.UIManager.put("OptionPane.buttonFont", fuenteGrande);

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que deseas cerrar sesión?", 
            "Cerrar Sesión", 
            javax.swing.JOptionPane.YES_NO_OPTION, 
            javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {

            // --- LA LÍNEA QUE DEBES AGREGAR ---
            Clases.ManejadorSesion.borrarSesion(); 
            // ----------------------------------

            this.dispose(); 
            new Login().setVisible(true); 
        }
        
    }//GEN-LAST:event_CerrarsesionActionPerformed

    private void CantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CantidadActionPerformed

    private void MermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MermaActionPerformed
        // TODO add your handling code here:
                resetMenu();
        Merma.setBackground(java.awt.Color.decode("#566174"));
        
        Pestañas.setSelectedIndex(4);
    }//GEN-LAST:event_MermaActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarActionPerformed

    

    
    
    
    
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
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JButton AgregarEncargo;
    private javax.swing.JButton Borrar;
    private javax.swing.JTextField BuscarEncargo;
    private javax.swing.JTextField BuscarProducto;
    private javax.swing.JButton Cantidad;
    private javax.swing.JButton Cerrarsesion;
    private javax.swing.JButton Corte;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton EliminarEncargo;
    private javax.swing.JButton Encargos;
    private javax.swing.JButton GenerarReporteCorte;
    private javax.swing.JButton GenerarReporteMerma;
    private javax.swing.JButton Inventario;
    private javax.swing.JLabel Logo3;
    private javax.swing.JButton Merma;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton ModificarEncargo;
    private javax.swing.JButton Pagar;
    private javax.swing.JButton Pan1;
    private javax.swing.JButton Pan10;
    private javax.swing.JButton Pan11;
    private javax.swing.JButton Pan12;
    private javax.swing.JButton Pan13;
    private javax.swing.JButton Pan14;
    private javax.swing.JButton Pan15;
    private javax.swing.JButton Pan16;
    private javax.swing.JButton Pan2;
    private javax.swing.JButton Pan3;
    private javax.swing.JButton Pan4;
    private javax.swing.JButton Pan5;
    private javax.swing.JButton Pan6;
    private javax.swing.JButton Pan7;
    private javax.swing.JButton Pan8;
    private javax.swing.JButton Pan9;
    private javax.swing.JPanel Panel_Usuario;
    private javax.swing.JTabbedPane Pestañas;
    private javax.swing.JTable TablaCorte;
    private javax.swing.JTable TablaEncargo;
    private javax.swing.JTable TablaInventario;
    private javax.swing.JTable TablaMerma;
    private javax.swing.JTable TablaVentas;
    private javax.swing.JButton Venta;
    private javax.swing.JButton borrar;
    private javax.swing.JButton boto4;
    private javax.swing.JButton boton0;
    private javax.swing.JButton boton1;
    private javax.swing.JButton boton2;
    private javax.swing.JButton boton3;
    private javax.swing.JButton boton5;
    private javax.swing.JButton boton6;
    private javax.swing.JButton boton7;
    private javax.swing.JButton boton8;
    private javax.swing.JButton boton9;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton19;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea txtPantallaCobro;
    // End of variables declaration//GEN-END:variables
}
