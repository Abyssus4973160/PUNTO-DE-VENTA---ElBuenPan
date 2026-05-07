package Clases;

import elbuenpan.Config;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TablaInventario {

    private static void aplicarEstiloGigante() {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 22));
        UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.BOLD, 18));
    }

    private static JTextField crearCajaTexto(String texto) {
        JTextField txt = new JTextField(texto);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 24));
        txt.setPreferredSize(new Dimension(300, 45));
        return txt;
    }

    public static void modificarProducto(JFrame ventana, JTable tabla) {
        aplicarEstiloGigante();

        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(ventana, "Selecciona un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int filaReal = tabla.convertRowIndexToModel(filaSeleccionada);

        // Obtenemos los datos actuales de la tabla
        String nombreActual = modelo.getValueAt(filaReal, 0).toString();
        String stockActual = modelo.getValueAt(filaReal, 1).toString();
        String precioActual = modelo.getValueAt(filaReal, 3).toString();
        String idProd = modelo.getValueAt(filaReal, 4).toString(); // El ID oculto

        // Creamos los campos solo para Stock y Precio
        JTextField txtStock = crearCajaTexto(stockActual);
        JTextField txtPrecio = crearCajaTexto(precioActual);

        // Diseñamos el cuerpo de la ventana emergente
        Object[] mensaje = {
            "Producto: " + nombreActual, // Solo informativo, no se edita
            " ",
            "Nuevo Stock:", txtStock,
            " ",
            "Nuevo Precio:", txtPrecio
        };

        int opcion = JOptionPane.showConfirmDialog(
                ventana, 
                mensaje, 
                "MODIFICAR STOCK Y PRECIO", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                // Validamos que los datos sean correctos
                int nuevoStock = Integer.parseInt(txtStock.getText().trim());
                double nuevoPrecio = Double.parseDouble(txtPrecio.getText().trim());

                // Comando para el servidor (Nombre se queda igual, enviamos stock y precio nuevos)
                String comando = "MODIFICAR_COMPLETO," + idProd + "," + nombreActual + "," + nuevoPrecio + "," + nuevoStock;

                if (enviarAlServidor(comando)) {
                    JOptionPane.showMessageDialog(ventana, "¡Actualización exitosa!");
                    // El refresco de la tabla se hace desde el ActionListener del botón en el JFrame
                } else {
                    JOptionPane.showMessageDialog(ventana, "Error al guardar en el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ventana, "Por favor, ingresa valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Botón para Sumar Stock (Botón Agregar de tu imagen)
    public static void agregarStockRapido(JFrame ventana, JTable tabla) {
        aplicarEstiloGigante();
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(ventana, "Selecciona un producto primero.");
            return;
        }

        String nombre = tabla.getValueAt(fila, 0).toString();
        String idProd = tabla.getModel().getValueAt(tabla.convertRowIndexToModel(fila), 4).toString();
        JTextField txtCant = crearCajaTexto("");

        Object[] mensaje = {"Producto: " + nombre, "Cantidad a RECIBIR (Sumar):", txtCant};

        int opcion = JOptionPane.showConfirmDialog(ventana, mensaje, "SURTIR STOCK", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION && !txtCant.getText().isEmpty()) {
            if (enviarAlServidor("ACTUALIZAR_STOCK_RAPIDO," + idProd + "," + txtCant.getText().trim())) {
                JOptionPane.showMessageDialog(ventana, "Stock actualizado correctamente.");
            }
        }
    }

    // Botón para Dar de Baja Stock (Botón Eliminar de tu imagen)
    public static void restarStockRapido(JFrame ventana, JTable tabla) {
        aplicarEstiloGigante();
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(ventana, "Selecciona un producto primero.");
            return;
        }

        // Datos actuales
        String nombre = tabla.getValueAt(fila, 0).toString();
        int stockActual = Integer.parseInt(tabla.getValueAt(fila, 1).toString());
        String idProd = tabla.getModel().getValueAt(tabla.convertRowIndexToModel(fila), 4).toString();

        JTextField txtCant = crearCajaTexto("");
        Object[] mensaje = {"Producto: " + nombre, "Stock actual: " + stockActual, "Cantidad a ELIMINAR (Merma):", txtCant};

        int opcion = JOptionPane.showConfirmDialog(ventana, mensaje, "DAR DE BAJA STOCK", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION && !txtCant.getText().isEmpty()) {
            try {
                int cantidadARestar = Integer.parseInt(txtCant.getText().trim());

                // VALIDACIÓN: No puedes eliminar más de lo que tienes
                if (cantidadARestar > stockActual) {
                    JOptionPane.showMessageDialog(ventana, 
                        "Error: No puedes eliminar " + cantidadARestar + " piezas porque solo hay " + stockActual + " en inventario.", 
                        "Stock Insuficiente", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Enviamos el número negativo al servidor para que reste
                if (enviarAlServidor("ACTUALIZAR_STOCK_RAPIDO," + idProd + "," + (cantidadARestar * -1))) {
                    JOptionPane.showMessageDialog(ventana, "Se han eliminado " + cantidadARestar + " piezas por merma.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ventana, "Ingresa un número válido.");
            }
        }
    }

    // --- LÓGICA DE COMUNICACIÓN ---
    private static boolean enviarAlServidor(String comando) {
        try (Socket socket = new Socket(Config.HOST, Config.PUERTO);
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
             DataInputStream entrada = new DataInputStream(socket.getInputStream())) {

            salida.writeUTF(comando);
            return entrada.readBoolean(); // El servidor responde true/false

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void configurarBuscador(JTable tabla, JTextField campoBusqueda) {
        campoBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String textoBusqueda = campoBusqueda.getText().trim();
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
                tabla.setRowSorter(sorter);
                
                if (textoBusqueda.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
                }
            }
        });
    }
}