package Clases;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class TablaMerma {

    public static void generarReporte(JTable tablaMerma, JTextArea areaReporte) {
        int totalExhibicion = 0;
        int totalRecepcion = 0;
        DefaultTableModel modelo = (DefaultTableModel) tablaMerma.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            try {
                Object valExhibicion = modelo.getValueAt(i, 1);
                Object valRecepcion = modelo.getValueAt(i, 2);

                if (valExhibicion != null && !valExhibicion.toString().isEmpty()) {
                    totalExhibicion += Integer.parseInt(valExhibicion.toString());
                }
                if (valRecepcion != null && !valRecepcion.toString().isEmpty()) {
                    totalRecepcion += Integer.parseInt(valRecepcion.toString());
                }
            } catch (NumberFormatException e) {
            }
        }

        int totalMerma = totalExhibicion + totalRecepcion;

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n          REPORTE DE MERMA\n");
        reporte.append("====================================\n\n");
        reporte.append(String.format("EXHIBICIÓN:      %10d pzs\n", totalExhibicion));
        reporte.append("------------------------------------\n\n");
        reporte.append(String.format("RECEPCIÓN:       %10d pzs\n", totalRecepcion));
        reporte.append("------------------------------------\n\n\n");
        reporte.append(String.format("TOTAL MERMA:     %10d pzs\n", totalMerma));
        reporte.append("====================================\n");

        areaReporte.setFont(new Font("Monospaced", Font.BOLD, 18));
        areaReporte.setEditable(false);
        areaReporte.setText(reporte.toString());
    }
}