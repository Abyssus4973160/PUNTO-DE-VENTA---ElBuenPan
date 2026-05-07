package Clases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class EncabezadoTabla implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel((String) value);
        
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(45, 58, 72)); // Azul oscuro tipo sidebar
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setPreferredSize(new Dimension(30, 60));
        label.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
        
        return label;
    }
}