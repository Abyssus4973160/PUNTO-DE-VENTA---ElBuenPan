package Clases;

import elbuenpan.Config;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

public class TablaEncargos {

    private static void mostrarAlertaGrande(JFrame ventana, String texto, String titulo, int tipo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 22));
        JOptionPane.showMessageDialog(ventana, label, titulo, tipo);
    }

    private static void aplicarEstiloGigante() {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.PLAIN, 18));
    }

    private static JTextField crearCampo(String texto) {
        JTextField txt = new JTextField(texto);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 20));
        txt.setPreferredSize(new Dimension(350, 40));
        return txt;
    }

    // Obtener lista de productos desde servidor
    private static String[][] obtenerProductosBD() {
        try (Socket s = new Socket(Config.HOST, Config.PUERTO);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {
            out.writeUTF("MOSTRAR_TODO");
            String resp = in.readUTF();
            out.writeUTF("SALIR");
            if (resp == null || resp.isEmpty()) return new String[0][0];

            String[] registros = resp.split(";");
            String[][] productos = new String[registros.length][3];
            for (int i = 0; i < registros.length; i++) {
                String[] d = registros[i].split("-");
                if (d.length >= 3) {
                    productos[i][0] = d[0]; // id
                    productos[i][1] = d[1]; // nombre
                    productos[i][2] = d[2]; // precio
                }
            }
            return productos;
        } catch (Exception e) {
            return new String[0][0];
        }
    }

    private static boolean enviarComando(String comando) {
        try (Socket s = new Socket(Config.HOST, Config.PUERTO);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {
            out.writeUTF(comando);
            boolean resultado = in.readBoolean();
            out.writeUTF("SALIR");
            return resultado;
        } catch (Exception e) {
            System.err.println("Error conexión: " + e.getMessage());
            return false;
        }
    }

    public static void cargarEncargos(JTable tabla) {
        try (Socket s = new Socket(Config.HOST, Config.PUERTO);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {
            out.writeUTF("MOSTRAR_ENCARGOS");
            String respuesta = in.readUTF();
            out.writeUTF("SALIR");

            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);

            if (respuesta == null || respuesta.isEmpty()) return;

            for (String fila : respuesta.split(";")) {
                String[] d = fila.split("\\|");
                if (d.length >= 7) {
                    modelo.addRow(new Object[]{
                        d[1],                        // Cliente
                        d[2],                        // Productos desc
                        Double.parseDouble(d[3]),    // Total
                        Double.parseDouble(d[4]),    // Anticipo
                        Double.parseDouble(d[5]),    // Restante
                        d[6],                        // Fecha entrega
                        d[0]                         // ID oculto
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando encargos: " + e.getMessage());
        }
    }

    // Formulario con buscador de productos
    private static Object[] crearFormulario(String[][] productos,
                                             String clienteInicial,
                                             String fechaInicial,
                                             String anticipoInicial,
                                             int[] idSeleccionado,
                                             int[] cantidadRef) {
        JTextField txtCliente   = crearCampo(clienteInicial);
        JTextField txtBuscar    = crearCampo("");
        txtBuscar.setToolTipText("Escribe para buscar producto");
        JTextField txtCantidad  = crearCampo("1");
        JTextField txtAnticipo  = crearCampo(anticipoInicial);
        JTextField txtFecha     = crearCampo(fechaInicial);
        JLabel     lblTotal     = new JLabel("$ 0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTotal.setForeground(new Color(0, 120, 0));

        // Modelo de lista de productos
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaProductos = new JList<>(listModel);
        listaProductos.setFont(new Font("SansSerif", Font.PLAIN, 18));
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaProductos);
        scrollLista.setPreferredSize(new Dimension(350, 150));

        // Llenar lista inicial
        for (String[] p : productos) {
            if (p[0] != null) listModel.addElement(p[1] + "  —  $" + p[2]);
        }

        // Recalcular total
        Runnable recalcular = () -> {
            try {
                int idx = listaProductos.getSelectedIndex();
                if (idx >= 0 && idx < productos.length) {
                    double precio = Double.parseDouble(productos[idx][2]);
                    int cant = Integer.parseInt(txtCantidad.getText().trim());
                    double total = precio * cant;
                    lblTotal.setText(String.format("$ %.2f", total));
                    idSeleccionado[0] = Integer.parseInt(productos[idx][0]);
                    cantidadRef[0] = cant;
                }
            } catch (Exception ignored) {}
        };

        // Buscador filtra la lista
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            void filtrar() {
                String texto = txtBuscar.getText().trim().toLowerCase();
                listModel.clear();
                for (String[] p : productos) {
                    if (p[0] != null && p[1].toLowerCase().contains(texto)) {
                        listModel.addElement(p[1] + "  —  $" + p[2]);
                    }
                }
            }
            public void insertUpdate(DocumentEvent e)  { filtrar(); }
            public void removeUpdate(DocumentEvent e)  { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        // Al seleccionar producto recalcula
        listaProductos.addListSelectionListener(e -> recalcular.run());

        // Al cambiar cantidad recalcula
        txtCantidad.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { recalcular.run(); }
            public void removeUpdate(DocumentEvent e)  { recalcular.run(); }
            public void changedUpdate(DocumentEvent e) { recalcular.run(); }
        });

        return new Object[]{
            "Cliente:", txtCliente, " ",
            "Buscar producto:", txtBuscar,
            scrollLista, " ",
            "Cantidad:", txtCantidad,
            "Total:", lblTotal, " ",
            "Anticipo ($):", txtAnticipo,
            "Fecha de entrega:", txtFecha,
            // Referencias para recuperar valores
            txtCliente, txtAnticipo, txtFecha, lblTotal
        };
    }

    public static void agregarEncargo(JFrame ventana, JTable tabla) {
        aplicarEstiloGigante();
        String[][] productos = obtenerProductosBD();
        if (productos.length == 0) {
            mostrarAlertaGrande(ventana, "No se pudo cargar la lista de productos.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[] idSeleccionado = {-1};
        int[] cantidadRef    = {1};

        JTextField txtCliente  = crearCampo("");
        JTextField txtBuscar   = crearCampo("");
        JTextField txtCantidad = crearCampo("1");
        JTextField txtAnticipo = crearCampo("0");
        JTextField txtFecha    = crearCampo("");
        JLabel     lblTotal    = new JLabel("$ 0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTotal.setForeground(new Color(0, 120, 0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaProductos = new JList<>(listModel);
        listaProductos.setFont(new Font("SansSerif", Font.PLAIN, 18));
        listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaProductos);
        scrollLista.setPreferredSize(new Dimension(350, 150));

        for (String[] p : productos) {
            if (p[0] != null) listModel.addElement(p[1] + "  —  $" + p[2]);
        }

        Runnable recalcular = () -> {
            try {
                int idx = listaProductos.getSelectedIndex();
                if (idx >= 0) {
                    // Encontrar el producto real según filtro
                    String textoFiltro = txtBuscar.getText().trim().toLowerCase();
                    int realIdx = 0, contador = 0;
                    for (int i = 0; i < productos.length; i++) {
                        if (productos[i][0] != null && 
                            productos[i][1].toLowerCase().contains(textoFiltro)) {
                            if (contador == idx) { realIdx = i; break; }
                            contador++;
                        }
                    }
                    double precio = Double.parseDouble(productos[realIdx][2]);
                    int cant = Integer.parseInt(txtCantidad.getText().trim());
                    lblTotal.setText(String.format("$ %.2f", precio * cant));
                    idSeleccionado[0] = Integer.parseInt(productos[realIdx][0]);
                    cantidadRef[0] = cant;
                }
            } catch (Exception ignored) {}
        };

        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            void filtrar() {
                String texto = txtBuscar.getText().trim().toLowerCase();
                listModel.clear();
                for (String[] p : productos) {
                    if (p[0] != null && p[1].toLowerCase().contains(texto))
                        listModel.addElement(p[1] + "  —  $" + p[2]);
                }
            }
            public void insertUpdate(DocumentEvent e)  { filtrar(); }
            public void removeUpdate(DocumentEvent e)  { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        listaProductos.addListSelectionListener(e -> recalcular.run());
        txtCantidad.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { recalcular.run(); }
            public void removeUpdate(DocumentEvent e)  { recalcular.run(); }
            public void changedUpdate(DocumentEvent e) { recalcular.run(); }
        });

        Object[] mensaje = {
            "Cliente:", txtCliente, " ",
            "Buscar producto:", txtBuscar,
            scrollLista, " ",
            "Cantidad:", txtCantidad,
            "Total:", lblTotal, " ",
            "Anticipo ($):", txtAnticipo,
            "Fecha de entrega:", txtFecha
        };

        int opcion = JOptionPane.showConfirmDialog(ventana, mensaje,
            "NUEVO ENCARGO", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String cliente  = txtCliente.getText().trim();
                String fecha    = txtFecha.getText().trim();
                double anticipo = Double.parseDouble(txtAnticipo.getText().trim());

                if (cliente.isEmpty() || fecha.isEmpty())
                    throw new Exception("Cliente y fecha son obligatorios.");
                if (idSeleccionado[0] == -1)
                    throw new Exception("Selecciona un producto de la lista.");

                // Obtener nombre y precio del producto seleccionado
                String nombreProd = "";
                double precio = 0;
                for (String[] p : productos) {
                    if (p[0] != null && Integer.parseInt(p[0]) == idSeleccionado[0]) {
                        nombreProd = p[1];
                        precio = Double.parseDouble(p[2]);
                        break;
                    }
                }
                double total = precio * cantidadRef[0];
                String desc  = cantidadRef[0] + " " + nombreProd;

                String comando = "AGREGAR_ENCARGO," + cliente + "," + desc + "," +
                                 total + "," + anticipo + "," + fecha + "," +
                                 idSeleccionado[0] + "," + cantidadRef[0];

                if (enviarComando(comando)) {
                    cargarEncargos(tabla);
                    mostrarAlertaGrande(ventana, "Encargo registrado.", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    mostrarAlertaGrande(ventana, "Error al guardar.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                mostrarAlertaGrande(ventana, e.getMessage(), 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static void modificarEncargo(JFrame ventana, JTable tabla) {
        aplicarEstiloGigante();
        int filaSel = tabla.getSelectedRow();
        if (filaSel == -1) {
            mostrarAlertaGrande(ventana, "Selecciona un encargo.", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int filaReal = tabla.convertRowIndexToModel(filaSel);
        String id = modelo.getValueAt(filaReal, 6).toString();

        JTextField txtCliente  = crearCampo(modelo.getValueAt(filaReal, 0).toString());
        JTextField txtAnticipo = crearCampo(modelo.getValueAt(filaReal, 3).toString());
        JTextField txtFecha    = crearCampo(modelo.getValueAt(filaReal, 5).toString());

        Object[] mensaje = {
            "Cliente:", txtCliente, " ",
            "Anticipo ($):", txtAnticipo,
            "Fecha:", txtFecha
        };

        int opcion = JOptionPane.showConfirmDialog(ventana, mensaje,
            "MODIFICAR ENCARGO", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String cliente  = txtCliente.getText().trim();
                double anticipo = Double.parseDouble(txtAnticipo.getText().trim());
                String fecha    = txtFecha.getText().trim();
                double total    = Double.parseDouble(modelo.getValueAt(filaReal, 2).toString());
                String desc     = modelo.getValueAt(filaReal, 1).toString();

                // Para modificar mantenemos el mismo producto_id y cantidad
                // Solo actualizamos cliente, anticipo y fecha
                String comando = "MODIFICAR_ENCARGO," + id + "," + cliente + "," + desc + "," +
                                 total + "," + anticipo + "," + fecha + ",0,0";

                if (enviarComando(comando)) {
                    cargarEncargos(tabla);
                    mostrarAlertaGrande(ventana, "Encargo actualizado.", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    mostrarAlertaGrande(ventana, "Error al actualizar.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                mostrarAlertaGrande(ventana, "Error: verifica los datos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void eliminarEncargo(JFrame ventana, JTable tabla) {
        aplicarEstiloGigante();
        int filaSel = tabla.getSelectedRow();
        if (filaSel == -1) {
            mostrarAlertaGrande(ventana, "Selecciona un encargo para eliminar.", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int filaReal = tabla.convertRowIndexToModel(filaSel);
        String id    = modelo.getValueAt(filaReal, 6).toString();
        String nombre = modelo.getValueAt(filaReal, 0).toString();

        JLabel pregunta = new JLabel("¿Eliminar encargo de: " + nombre + "?");
        pregunta.setFont(new Font("SansSerif", Font.PLAIN, 22));

        int opcion = JOptionPane.showConfirmDialog(ventana, pregunta,
            "ELIMINAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            if (enviarComando("ELIMINAR_ENCARGO," + id)) {
                cargarEncargos(tabla);
                mostrarAlertaGrande(ventana, "Encargo eliminado.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarAlertaGrande(ventana, "Error al eliminar.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void configurarBuscador(JTable tabla, JTextField campoBusqueda) {
        campoBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String texto = campoBusqueda.getText().trim();
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
                tabla.setRowSorter(sorter);
                sorter.setRowFilter(texto.isEmpty() ? null : RowFilter.regexFilter("(?i)" + texto));
            }
        });
    }
}