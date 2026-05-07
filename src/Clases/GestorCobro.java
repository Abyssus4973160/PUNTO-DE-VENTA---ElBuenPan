package Clases;

import elbuenpan.Config;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class GestorCobro {
    private JFrame ventana;
    private JTextArea pantalla;
    private JTable tabla;
    private double totalVenta = 0;
    private String efectivoTexto = "";

    public GestorCobro(JFrame ventana, JTextArea pantalla, JTable tabla) {
        this.ventana = ventana;
        this.pantalla = pantalla;
        this.tabla = tabla;
    }

    public void setTotalVenta(double total) {
        this.totalVenta = total;
        actualizarPantalla();
    }

    // --- MANEJO DE TECLADO ---
    public void agregarNumeroEfectivo(String numero) {
        if (numero.equals(".") && efectivoTexto.contains(".")) return;
        efectivoTexto += numero;
        actualizarPantalla();
    }

    public void borrarNumeroEfectivo() {
        if (!efectivoTexto.isEmpty()) {
            efectivoTexto = efectivoTexto.substring(0, efectivoTexto.length() - 1);
            actualizarPantalla();
        }
    }

    private void actualizarPantalla() {
        double efectivo = efectivoTexto.isEmpty() ? 0 : Double.parseDouble(efectivoTexto);
        pantalla.setText(String.format(" TOTAL: $ %.2f\n EFECT: $ %.2f", totalVenta, efectivo));
    }

    // --- PROCESAR PAGO CON VALIDACIONES ---
    public void procesarPago() {
        ConfigurarFuentesMensajes(25);

        // 1. Validar tabla vacía
        if (tabla.getRowCount() == 0) {
            mostrarError("ERROR: La lista está vacía.");
            return;
        }

        // 2. Validación de stock
        if (ventana instanceof elbuenpan.Menu) {
            elbuenpan.Menu menuPrincipal = (elbuenpan.Menu) ventana;
            JTable tablaInv = menuPrincipal.getTablaInventario();
            javax.swing.table.DefaultTableModel modeloInv =
                (javax.swing.table.DefaultTableModel) tablaInv.getModel();

            for (int i = 0; i < tabla.getRowCount(); i++) {
                Clases.TablaVentas.ItemTicket item =
                    (Clases.TablaVentas.ItemTicket) ((DefaultTableModel) tabla.getModel()).getValueAt(i, 0);

                int cantidadPedida = item.cantidad;
                int idBuscado = item.productoId;

                for (int j = 0; j < modeloInv.getRowCount(); j++) {
                    Object idInv = modeloInv.getValueAt(j, 4);
                    if (idInv != null && Integer.parseInt(idInv.toString()) == idBuscado) {
                        int stockDisponible = Integer.parseInt(modeloInv.getValueAt(j, 1).toString());
                        if (cantidadPedida > stockDisponible) {
                            mostrarError("<html><b style='color:red;'>STOCK INSUFICIENTE</b><br>" +
                                       "Pediste: " + cantidadPedida + " " + item.nombre + "<br>" +
                                       "Solo hay: " + stockDisponible + " en existencia.</html>");
                            return;
                        }
                        break;
                    }
                }
            }
        }

        // 3. Validar efectivo (declarada UNA sola vez aquí)
        double efectivo = efectivoTexto.isEmpty() ? 0 : Double.parseDouble(efectivoTexto);
        if (efectivo < totalVenta) {
            mostrarError("Efectivo insuficiente. Faltan: $" + String.format("%.2f", totalVenta - efectivo));
            return;
        }

        // 4. Procesar pago y mostrar resumen
        double totalFinal = totalVenta; // Guardar antes de limpiar
        double cambio = efectivo - totalFinal;

        enviarVentaAlServidor();

        JOptionPane.showMessageDialog(ventana,
            String.format("<html>" +
                "<h2 style='color:green;'>✔ VENTA REALIZADA</h2>" +
                "<table style='font-size:20px'>" +
                "<tr><td>Total:&nbsp;&nbsp;</td><td><b>$ %.2f</b></td></tr>" +
                "<tr><td>Efectivo:</td><td><b>$ %.2f</b></td></tr>" +
                "<tr><td style='color:green;'>Cambio:&nbsp;</td><td><b style='color:green;'>$ %.2f</b></td></tr>" +
                "</table></html>",
                totalFinal, efectivo, cambio),
            "Venta exitosa",
            JOptionPane.INFORMATION_MESSAGE);

        limpiarVenta();
    }

    // Función auxiliar para no repetir código de errores grandes
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Error en Venta", JOptionPane.ERROR_MESSAGE);
    }

    // --- CAMBIAR CANTIDAD ---
    public void cambiarCantidadSeleccionada() {
        ConfigurarFuentesMensajes(20);
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(ventana, "Selecciona un producto en la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String input = JOptionPane.showInputDialog(ventana, "Nueva cantidad:");
        if (input != null && !input.isEmpty()) {
            try {
                int nCant = Integer.parseInt(input);
                if (nCant <= 0) throw new Exception();

                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

                // Actualizar el ItemTicket real (esto es lo que faltaba)
                Clases.TablaVentas.ItemTicket item = 
                    (Clases.TablaVentas.ItemTicket) modelo.getValueAt(fila, 0);
                item.cantidad = nCant;
                item.total = nCant * item.precioUnitario;

                // Actualizar las celdas visuales
                modelo.setValueAt(nCant, fila, 1);
                modelo.setValueAt(item.total, fila, 3);

                setTotalVenta(Clases.TablaVentas.calcularTotal(tabla));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void eliminarProductoSeleccionado() {
        // Configuramos la fuente grande antes de mostrar el aviso
        java.awt.Font fuenteGrande = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 22);
        UIManager.put("OptionPane.messageFont", fuenteGrande);
        UIManager.put("OptionPane.buttonFont", fuenteGrande);

        int fila = tabla.getSelectedRow();

        if (fila != -1) {
            // Si hay algo seleccionado, lo borramos
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.removeRow(fila);

            // Recalculamos el total de la venta inmediatamente
            double nuevoTotal = Clases.TablaVentas.calcularTotal(tabla);
            setTotalVenta(nuevoTotal);
        } else {
            // MENSAJE DE ERROR: Si no seleccionó nada
            JOptionPane.showMessageDialog(ventana, 
                "<html><b style='color:red;'>AVISO:</b><br>Selecciona un producto de la tabla para poder borrarlo.</html>", 
                "Atención", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public void limpiarVenta() {
        ((DefaultTableModel) tabla.getModel()).setRowCount(0); // Limpia tabla derecha
        totalVenta = 0;
        efectivoTexto = "";
        actualizarPantalla(); // Pone el total en $0.00

        // ESTO ES LO MÁS IMPORTANTE:
        if (ventana instanceof elbuenpan.Menu) {
            // Llama al método que ya tienes en Menu que borra los botones y los vuelve a crear con el stock nuevo
            ((elbuenpan.Menu) ventana).actualizarTodo(); 
        }
    }

    private void ConfigurarFuentesMensajes(int tam) {
        Font f = new Font("SansSerif", Font.BOLD, tam);
        UIManager.put("OptionPane.messageFont", f);
        UIManager.put("OptionPane.buttonFont", f);
    }

    private void enviarVentaAlServidor() {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        try (java.net.Socket s = new java.net.Socket(Config.HOST, Config.PUERTO);
             java.io.DataOutputStream out = new java.io.DataOutputStream(s.getOutputStream());
             java.io.DataInputStream in   = new java.io.DataInputStream(s.getInputStream())) {

            for (int i = 0; i < modelo.getRowCount(); i++) {
                // El ID está dentro del ItemTicket en la columna 0, NO en columna 4
                Clases.TablaVentas.ItemTicket item = 
                    (Clases.TablaVentas.ItemTicket) modelo.getValueAt(i, 0);

                String idProducto      = String.valueOf(item.productoId);
                String cantidadVendida = String.valueOf(item.cantidad);

                out.writeUTF("VENTA," + idProducto + "," + cantidadVendida);

                String respuesta = in.readUTF();
                if (!respuesta.equals("EXITO")) {
                    JOptionPane.showMessageDialog(ventana,
                        "Error en " + item.nombre + ": " + respuesta,
                        "Error de venta", JOptionPane.ERROR_MESSAGE);
                }
            }

            out.writeUTF("SALIR");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana,
                "Error de conexión al procesar pago:\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}