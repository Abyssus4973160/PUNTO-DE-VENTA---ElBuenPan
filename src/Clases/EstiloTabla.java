package Clases;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.BorderFactory;

public class EstiloTabla {

public static void aplicar(JTable... tablas) {
    for (JTable t : tablas) {
        if (t == null) continue;
        try {
            t.getTableHeader().setDefaultRenderer(new EncabezadoTabla());
            t.setDefaultRenderer(Object.class, new Celdas());
            
            // FILA MUCHO MÁS GRANDE
            t.setRowHeight(65); 
            
            // AJUSTE DEL ENCABEZADO
            t.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 60));
            
            t.setShowVerticalLines(false);
            t.setGridColor(new java.awt.Color(230, 230, 230));
            
            if (t.getParent() instanceof javax.swing.JViewport) {
                Object parent = t.getParent().getParent();
                if (parent instanceof javax.swing.JScrollPane) {
                    ((javax.swing.JScrollPane) parent).setBorder(javax.swing.BorderFactory.createEmptyBorder());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
