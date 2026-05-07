package Clases;

import elbuenpan.Config;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TablaCorte {

    // Verificar si el corte ya se hizo hoy
    public static boolean corteYaRealizado() {
        try (java.net.Socket s = new java.net.Socket(Config.HOST, Config.PUERTO);
             java.io.DataOutputStream out = new java.io.DataOutputStream(s.getOutputStream());
             java.io.DataInputStream in = new java.io.DataInputStream(s.getInputStream())) {
            out.writeUTF("CORTE_YA_REALIZADO");
            boolean resultado = in.readBoolean();
            out.writeUTF("SALIR");
            System.out.println("Corte ya realizado: " + resultado);
            return resultado;
        } catch (Exception e) {
            return false;
        }
    }

    // Cargar datos del día en la tabla
    public static void cargarDatosCorte(JTable tablaCorte) {
        try (Socket s = new Socket(Config.HOST, Config.PUERTO);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {

            out.writeUTF("OBTENER_CORTE_HOY");
            String respuesta = in.readUTF();
            out.writeUTF("SALIR");

            DefaultTableModel modelo = (DefaultTableModel) tablaCorte.getModel();
            modelo.setRowCount(0);

            if (respuesta == null || respuesta.isEmpty()) return;

            for (String fila : respuesta.split(";")) {
                String[] d = fila.split("\\|");
                if (d.length >= 6) {
                    int    stock      = Integer.parseInt(d[2]);
                    int    vendido    = Integer.parseInt(d[3]);
                    int    sobrante   = Integer.parseInt(d[4]);
                    double precio     = Double.parseDouble(d[5]);
                    double total      = vendido * precio;

                    modelo.addRow(new Object[]{
                        d[1],      // Producto
                        stock,     // Cantidad inicial
                        vendido,   // Venta
                        sobrante,  // Sobrante
                        precio,    // Precio unitario
                        total      // Total
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando corte: " + e.getMessage());
        }
    }

    // Generar reporte y ejecutar el corte
    public static void generarReporte(JTable tablaCorte, JTable tablaEncargos, JTextArea areaReporte, JFrame ventana, elbuenpan.Menu menuPrincipal) {
        // Verificar si ya se hizo el corte hoy
        if (corteYaRealizado()) {
            JLabel msg = new JLabel("El corte del día ya fue realizado.");
            msg.setFont(new Font("SansSerif", Font.BOLD, 22));
            JOptionPane.showMessageDialog(ventana, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cargar datos frescos desde BD
        cargarDatosCorte(tablaCorte);

        // Calcular totales
        DefaultTableModel modeloCorte = (DefaultTableModel) tablaCorte.getModel();
        double totalVentas = 0;
        for (int i = 0; i < modeloCorte.getRowCount(); i++) {
            totalVentas += Double.parseDouble(modeloCorte.getValueAt(i, 5).toString());
        }

        DefaultTableModel modeloEncargos = (DefaultTableModel) tablaEncargos.getModel();
        double totalAnticipos = 0;
        for (int i = 0; i < modeloEncargos.getRowCount(); i++) {
            try {
                totalAnticipos += Double.parseDouble(modeloEncargos.getValueAt(i, 3).toString());
            } catch (Exception ignored) {}
        }

        double totalCaja = totalVentas + totalAnticipos;

        // Mostrar reporte
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n          RESUMEN DE CORTE\n");
        reporte.append("====================================\n\n");
        reporte.append(String.format("  Fecha: %s\n\n", 
            new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
        reporte.append(String.format("  VENTAS MOSTRADOR:   $ %,.2f\n", totalVentas));
        reporte.append("  ------------------------------------\n");
        reporte.append(String.format("  ANTICIPOS:          $ %,.2f\n", totalAnticipos));
        reporte.append("  ------------------------------------\n\n");
        reporte.append(String.format("  TOTAL EN CAJA:      $ %,.2f\n", totalCaja));
        reporte.append("====================================\n");

        areaReporte.setFont(new Font("Monospaced", Font.BOLD, 16));
        areaReporte.setEditable(false);
        areaReporte.setText(reporte.toString());

        // Confirmar cierre del día
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.BOLD, 22));
        UIManager.put("OptionPane.buttonFont",  new Font("SansSerif", Font.BOLD, 18));

        JLabel pregunta = new JLabel(
            "<html><center>Total en caja: <b style='color:green;'>$" + 
            String.format("%,.2f", totalCaja) + 
            "</b><br><br>¿Deseas cerrar el día?<br>" +
            "<small>Los sobrantes pasarán a merma y el stock se reiniciará.</small></center></html>");
        pregunta.setFont(new Font("SansSerif", Font.PLAIN, 20));

        int opcion = JOptionPane.showConfirmDialog(ventana, pregunta,
            "CERRAR DÍA", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            // Realizar corte en servidor
            try (Socket s = new Socket(Config.HOST, Config.PUERTO);
                 DataOutputStream out = new DataOutputStream(s.getOutputStream());
                 DataInputStream in = new DataInputStream(s.getInputStream())) {

                out.writeUTF("REALIZAR_CORTE,1," + totalVentas + "," + totalAnticipos);
                boolean exito = in.readBoolean();
                out.writeUTF("SALIR");

                if (exito) {
                    // Generar PDF ANTES de limpiar
                    String rutaPDF = generarPDF(totalVentas, totalAnticipos, totalCaja, modeloCorte);

                    // Limpiar tabla directamente
                    modeloCorte.setRowCount(0);
                    tablaCorte.repaint(); // Forzar redibujado
                    areaReporte.setText("\n   Corte realizado. Tabla reiniciada.");

                    menuPrincipal.actualizarTodo();

                    String msgPDF = rutaPDF != null ?
                        "<br><small>PDF guardado en Documentos</small>" :
                        "<br><small style='color:red;'>No se pudo generar el PDF</small>";

                    JLabel msgExito = new JLabel(
                        "<html><center><b style='color:green;'>✔ Corte realizado exitosamente</b>" +
                        "<br>El inventario ha sido reiniciado." + msgPDF + "</center></html>");
                    msgExito.setFont(new Font("SansSerif", Font.PLAIN, 20));
                    JOptionPane.showMessageDialog(ventana, msgExito,
                        "Corte Completado", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(ventana, 
                        "Error al realizar el corte. Intenta de nuevo.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventana,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private static String generarPDF(double totalVentas, double totalAnticipos, double totalCaja, DefaultTableModel modeloCorte) {
        try {
            // Ruta en Documentos del usuario
            String rutaDocumentos = System.getProperty("user.home") + java.io.File.separator + "Documents";
            String fecha = new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
            String rutaPDF = rutaDocumentos + java.io.File.separator + "Corte_" + fecha + ".pdf";

            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(doc, new java.io.FileOutputStream(rutaPDF));
            doc.open();

            // --- FUENTES ---
            com.itextpdf.text.Font fuenteTitulo = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 20, 
                com.itextpdf.text.Font.BOLD, 
                new com.itextpdf.text.BaseColor(78, 52, 46)); // Café oscuro

            com.itextpdf.text.Font fuenteSubtitulo = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 12,
                com.itextpdf.text.Font.NORMAL,
                com.itextpdf.text.BaseColor.GRAY);

            com.itextpdf.text.Font fuenteTablaHeader = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 11,
                com.itextpdf.text.Font.BOLD,
                com.itextpdf.text.BaseColor.WHITE);

            com.itextpdf.text.Font fuenteTabla = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 10,
                com.itextpdf.text.Font.NORMAL,
                com.itextpdf.text.BaseColor.BLACK);

            com.itextpdf.text.Font fuenteTotal = new com.itextpdf.text.Font(
                com.itextpdf.text.Font.FontFamily.HELVETICA, 13,
                com.itextpdf.text.Font.BOLD,
                new com.itextpdf.text.BaseColor(0, 100, 0));

            // --- TÍTULO ---
            com.itextpdf.text.Paragraph titulo = new com.itextpdf.text.Paragraph(
                "EL BUEN PAN\n", fuenteTitulo);
            titulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(titulo);

            com.itextpdf.text.Paragraph subtitulo = new com.itextpdf.text.Paragraph(
                "Reporte de Corte de Caja — " + fecha + "\n\n", fuenteSubtitulo);
            subtitulo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(subtitulo);

            // --- LÍNEA SEPARADORA ---
            com.itextpdf.text.pdf.draw.LineSeparator linea = 
                new com.itextpdf.text.pdf.draw.LineSeparator();
            linea.setLineColor(new com.itextpdf.text.BaseColor(78, 52, 46));
            doc.add(new com.itextpdf.text.Chunk(linea));
            doc.add(com.itextpdf.text.Chunk.NEWLINE);

            // --- TABLA DE PRODUCTOS ---
            com.itextpdf.text.pdf.PdfPTable tabla = 
                new com.itextpdf.text.pdf.PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);
            tabla.setWidths(new float[]{3f, 1.5f, 1.5f, 1.5f, 2f, 2f});

            // Headers
            com.itextpdf.text.BaseColor colorHeader = new com.itextpdf.text.BaseColor(78, 52, 46);
            String[] headers = {"Producto", "Cantidad", "Vendido", "Sobrante", "Precio Unit.", "Total"};
            for (String h : headers) {
                com.itextpdf.text.pdf.PdfPCell celda = 
                    new com.itextpdf.text.pdf.PdfPCell(
                        new com.itextpdf.text.Phrase(h, fuenteTablaHeader));
                celda.setBackgroundColor(colorHeader);
                celda.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                celda.setPadding(6);
                tabla.addCell(celda);
            }

            // Filas de productos
            com.itextpdf.text.BaseColor colorFila1 = new com.itextpdf.text.BaseColor(255, 253, 240);
            com.itextpdf.text.BaseColor colorFila2 = com.itextpdf.text.BaseColor.WHITE;

            for (int i = 0; i < modeloCorte.getRowCount(); i++) {
                com.itextpdf.text.BaseColor colorFila = (i % 2 == 0) ? colorFila1 : colorFila2;

                String[] valores = {
                    modeloCorte.getValueAt(i, 0).toString(),
                    modeloCorte.getValueAt(i, 1).toString(),
                    modeloCorte.getValueAt(i, 2).toString(),
                    modeloCorte.getValueAt(i, 3).toString(),
                    String.format("$%.2f", Double.parseDouble(modeloCorte.getValueAt(i, 4).toString())),
                    String.format("$%.2f", Double.parseDouble(modeloCorte.getValueAt(i, 5).toString()))
                };

                for (int j = 0; j < valores.length; j++) {
                    com.itextpdf.text.pdf.PdfPCell celda = 
                        new com.itextpdf.text.pdf.PdfPCell(
                            new com.itextpdf.text.Phrase(valores[j], fuenteTabla));
                    celda.setBackgroundColor(colorFila);
                    celda.setPadding(5);
                    celda.setHorizontalAlignment(j == 0 ? 
                        com.itextpdf.text.Element.ALIGN_LEFT : 
                        com.itextpdf.text.Element.ALIGN_CENTER);
                    tabla.addCell(celda);
                }
            }
            doc.add(tabla);

            // --- LÍNEA SEPARADORA ---
            doc.add(new com.itextpdf.text.Chunk(linea));
            doc.add(com.itextpdf.text.Chunk.NEWLINE);

            // --- RESUMEN FINAL ---
            com.itextpdf.text.pdf.PdfPTable resumen = 
                new com.itextpdf.text.pdf.PdfPTable(2);
            resumen.setWidthPercentage(50);
            resumen.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            resumen.setSpacingBefore(10f);
            resumen.setWidths(new float[]{3f, 2f});

            agregarFilaResumen(resumen, "Ventas mostrador:", 
                String.format("$%.2f", totalVentas), fuenteTabla, false);
            agregarFilaResumen(resumen, "Anticipos encargos:", 
                String.format("$%.2f", totalAnticipos), fuenteTabla, false);
            agregarFilaResumen(resumen, "TOTAL EN CAJA:", 
                String.format("$%.2f", totalCaja), fuenteTotal, true);

            doc.add(resumen);

            // --- PIE DE PÁGINA ---
            doc.add(com.itextpdf.text.Chunk.NEWLINE);
            com.itextpdf.text.Paragraph pie = new com.itextpdf.text.Paragraph(
                "Generado el " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()),
                fuenteSubtitulo);
            pie.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            doc.add(pie);

            doc.close();
            System.out.println("PDF generado en: " + rutaPDF);
            return rutaPDF;

        } catch (Exception e) {
            System.err.println("Error generando PDF: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static void agregarFilaResumen(com.itextpdf.text.pdf.PdfPTable tabla,
                                            String etiqueta, String valor,
                                            com.itextpdf.text.Font fuente, boolean esTotal) {
        com.itextpdf.text.BaseColor color = esTotal ? 
            new com.itextpdf.text.BaseColor(240, 255, 240) : 
            com.itextpdf.text.BaseColor.WHITE;

        com.itextpdf.text.pdf.PdfPCell celdaEtiqueta = 
            new com.itextpdf.text.pdf.PdfPCell(
                new com.itextpdf.text.Phrase(etiqueta, fuente));
        celdaEtiqueta.setBackgroundColor(color);
        celdaEtiqueta.setPadding(6);
        celdaEtiqueta.setBorder(com.itextpdf.text.pdf.PdfPCell.NO_BORDER);

        com.itextpdf.text.pdf.PdfPCell celdaValor = 
            new com.itextpdf.text.pdf.PdfPCell(
                new com.itextpdf.text.Phrase(valor, fuente));
        celdaValor.setBackgroundColor(color);
        celdaValor.setPadding(6);
        celdaValor.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
        celdaValor.setBorder(com.itextpdf.text.pdf.PdfPCell.NO_BORDER);

        tabla.addCell(celdaEtiqueta);
        tabla.addCell(celdaValor);
    }
    
}