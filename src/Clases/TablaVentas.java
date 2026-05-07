package Clases;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaVentas {

    // ESTO ES LO QUE TE FALTA (El método configurar)
    public static void configurar(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel(
            new Object [][] {},
            new String [] {"Producto", "Cantidad", "Precio Unit.", "Total"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que el usuario edite la tabla a mano
            }
        };
        tabla.setModel(modelo);
    }

    // Método para agregar con el ID (el que pide el Menu)
    public static void agregarProducto(JTable tabla, int id, String nombre, double precio) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        // Buscar si ya existe el producto (por ID) para sumar cantidad
        for (int i = 0; i < modelo.getRowCount(); i++) {
            ItemTicket itemExistente = (ItemTicket) modelo.getValueAt(i, 0);
            if (itemExistente.productoId == id) {
                itemExistente.cantidad++;
                itemExistente.total = itemExistente.cantidad * itemExistente.precioUnitario;
                
                // Actualizamos visualmente las celdas de cantidad y total
                modelo.setValueAt(itemExistente.cantidad, i, 1);
                modelo.setValueAt(itemExistente.total, i, 3);
                return;
            }
        }

        // Si es nuevo, creamos el ItemTicket y lo agregamos
        ItemTicket nuevoItem = new ItemTicket(id, nombre, precio, 1);
        modelo.addRow(new Object[]{nuevoItem, 1, precio, precio});
    }

    // Método para calcular el total general
    public static double calcularTotal(JTable tabla) {
        double totalVenta = 0;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            try {
                totalVenta += Double.parseDouble(tabla.getValueAt(i, 3).toString());
            } catch (Exception e) {
                System.err.println("Error al calcular fila: " + e.getMessage());
            }
        }
        return totalVenta;
    }

    // El molde de los datos
    public static class ItemTicket {
        public int productoId;
        public String nombre;
        public double precioUnitario;
        public int cantidad;
        public double total;

        public ItemTicket(int id, String nombre, double precio, int cant) {
            this.productoId = id;
            this.nombre = nombre;
            this.precioUnitario = precio;
            this.cantidad = cant;
            this.total = precio * cant;
        }

        @Override
        public String toString() {
            return nombre; // Esto es lo que se ve en la primera columna
        }
    }
}