package Clases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Celdas extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        c.setFont(new Font("SansSerif", Font.PLAIN, 26));
        
        if (isSelected) {
            c.setBackground(new Color(238, 150, 41)); // Naranja "El Buen Pan"
            c.setForeground(Color.WHITE);
        } else {
            if (row % 2 == 0) {
                c.setBackground(Color.WHITE);
            } else {
                c.setBackground(new Color(242, 242, 242)); // Gris sutil
            }
            c.setForeground(new Color(51, 51, 51));
        }
        
        // Centrar todas las columnas excepto la primera (Nombre del producto)
        if (column > 0) {
            this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        } else {
            this.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        }
        
        return c;
    }
}