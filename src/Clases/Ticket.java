package Clases;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Ticket {

    private double totalVentaActual = 0.0;
    private String efectivoIngresado = "";

    private JFrame ventanaPrincipal;
    private JTextField campoMonto;
    private JTable tablaVentas;

    public Ticket(JFrame ventanaPrincipal, JTextField campoMonto, JTable tablaVentas) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.campoMonto = campoMonto;
        this.tablaVentas = tablaVentas;

        // Mantenemos las fuentes grandes para los mensajes de error/aviso
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 22));
        UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.BOLD, 18));

        actualizarPantallaCobro(); 
    }

    public void setTotalVenta(double nuevoTotal) {
        this.totalVentaActual = nuevoTotal;
        actualizarPantallaCobro();
    }

    public void agregarNumeroEfectivo(String num) {
        efectivoIngresado += num;
        actualizarPantallaCobro();
    }

    public void borrarNumeroEfectivo() {
        if (!efectivoIngresado.isEmpty()) {
            efectivoIngresado = efectivoIngresado.substring(0, efectivoIngresado.length() - 1);
            actualizarPantallaCobro();
        }
    }

    private void actualizarPantallaCobro() {
        if (efectivoIngresado.isEmpty()) {
            campoMonto.setText(String.format("Total: $ %.2f", totalVentaActual).replace(".", ","));
        } else {
            campoMonto.setText(String.format("Total: $ %.2f   |   Pago con: $ %s", totalVentaActual, efectivoIngresado).replace(".", ","));
        }
    }

    // --- MÉTODOS PARA MODIFICAR PRODUCTOS DESDE EL TECLADO ---

    public void cambiarCantidadSeleccionada() {
        int filaSel = tablaVentas.getSelectedRow();
        if (filaSel == -1) {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Selecciona un producto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (efectivoIngresado.isEmpty()) return;

        try {
            int nuevaCant = Integer.parseInt(efectivoIngresado);
            DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
            
            // IMPORTANTE: Usamos Clases.TablaVentas.ItemTicket
            Clases.TablaVentas.ItemTicket item = (Clases.TablaVentas.ItemTicket) modelo.getValueAt(filaSel, 0);
            
            if (nuevaCant <= 0) {
                modelo.removeRow(filaSel);
            } else {
                item.cantidad = nuevaCant;
                item.total = item.cantidad * item.precioUnitario;
                modelo.fireTableRowsUpdated(filaSel, filaSel);
            }
            
            recalcularTotal();
            efectivoIngresado = "";
            actualizarPantallaCobro();
        } catch (Exception e) {
            efectivoIngresado = "";
        }
    }

    public void eliminarProductoSeleccionado() {
        int filaSel = tablaVentas.getSelectedRow();
        if (filaSel != -1) {
            ((DefaultTableModel) tablaVentas.getModel()).removeRow(filaSel);
            recalcularTotal();
            efectivoIngresado = "";
            actualizarPantallaCobro();
        }
    }

    private void recalcularTotal() {
        totalVentaActual = Clases.TablaVentas.calcularTotal(tablaVentas);
    }

    // --- PROCESAR PAGO E IMPRIMIR TICKET ---

    public void procesarPago() {
        if (totalVentaActual == 0) {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Agrega productos antes de cobrar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (efectivoIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Ingresa con cuánto paga el cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double efectivo = Double.parseDouble(efectivoIngresado);
            if (efectivo < totalVentaActual) {
                JOptionPane.showMessageDialog(ventanaPrincipal, "El efectivo es menor al monto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double cambio = efectivo - totalVentaActual;

            // Construcción del Ticket Visual
            StringBuilder ticket = new StringBuilder();
            ticket.append("       EL BUEN PAN       \n");
            ticket.append("     Ticket de Venta     \n");
            ticket.append("--------------------------------\n");

            DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                // Referencia corregida a la clase TablaVentas
                Clases.TablaVentas.ItemTicket item = (Clases.TablaVentas.ItemTicket) modelo.getValueAt(i, 0);

                ticket.append(item.nombre.toUpperCase()).append("\n");
                String detalleStr = String.format("%d x $%.2f", item.cantidad, item.precioUnitario).replace(".", ",");
                String totalStr = String.format("$%.2f", item.total).replace(".", ",");
                ticket.append(String.format("%-22s %9s\n", detalleStr, totalStr));
            }

            ticket.append("--------------------------------\n");
            ticket.append(String.format("%-20s %11s\n", "TOTAL:", String.format("$%.2f", totalVentaActual).replace(".", ",")));
            ticket.append(String.format("%-20s %11s\n", "PAGO CON:", String.format("$%.2f", efectivo).replace(".", ",")));
            ticket.append(String.format("%-20s %11s\n", "CAMBIO:", String.format("$%.2f", cambio).replace(".", ",")));
            ticket.append("--------------------------------\n");
            ticket.append("   ¡Gracias por su preferencia! \n");

            JTextArea areaTicket = new JTextArea(ticket.toString());
            areaTicket.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Monospaced para que las columnas alineen
            areaTicket.setEditable(false);
            areaTicket.setBackground(new Color(255, 255, 255));
            areaTicket.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            JOptionPane.showMessageDialog(ventanaPrincipal, areaTicket, "Ticket de Venta", JOptionPane.PLAIN_MESSAGE);

            // Limpieza
            modelo.setRowCount(0);
            totalVentaActual = 0.0;
            efectivoIngresado = "";
            actualizarPantallaCobro();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            efectivoIngresado = "";
            actualizarPantallaCobro();
        }
    }
}