package Clases;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ConfiguradorBotones {

    private static final Color[] COLORES_FONDO = {
        new Color(0xFFCC80), 
        new Color(0xFFF59D), 
        new Color(0xCE93D8), 
        new Color(0xFFAB91), 
        new Color(0xF48FB1), 
        new Color(0x80DEEA), 
        new Color(0xC5E1A5), 
        new Color(0xBCAAA4), 
        new Color(0xEF9A9A), 
        new Color(0xB39DDB), 
        new Color(0xA5D6A7), 
        new Color(0x90CAF9), 
    };

    private static final Color BORDE_HOVER   = new Color(0x5B8BD4);
    private static final Color BORDE_NORMAL  = new Color(0xBBBBBB);
    private static final Color TEXTO_COLOR   = new Color(0x1A3A6B);

    public static void configurar(Object frame, String[] nombres, double[] precios) {

        List<JButton> botonesPan = new ArrayList<>();
        for (java.lang.reflect.Field field : frame.getClass().getDeclaredFields()) {
            if (field.getName().startsWith("Pan")) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(frame);
                    if (obj instanceof JButton) {
                        JButton btn = (JButton) obj;
                        btn.setName(field.getName());
                        botonesPan.add(btn);
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Error accediendo campo: " + field.getName());
                }
            }
        }

        botonesPan.sort(Comparator.comparingInt(btn -> {
            try { return Integer.parseInt(btn.getName().replace("Pan", "")); }
            catch (Exception e) { return 0; }
        }));

        JButton[] arreglo = botonesPan.toArray(new JButton[0]);

        if (nombres == null) {
            nombres = new String[arreglo.length];
            java.util.Arrays.fill(nombres, "PAN");
        }
        if (precios == null) {
            precios = new double[arreglo.length];
            java.util.Arrays.fill(precios, 10.00);
        }

        for (int i = 0; i < arreglo.length; i++) {
            JButton btn          = arreglo[i];
            int      numImg       = i + 1;
            String  nombreActual = (i < nombres.length) ? nombres[i] : "PAN";
            Color   colorFondo   = COLORES_FONDO[i % COLORES_FONDO.length];

            btn.setText("<html><center>" + nombreActual.toUpperCase() + "</center></html>");
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setForeground(TEXTO_COLOR);

            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);

            btn.setBackground(colorFondo);
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(true);
            
            // --- ESTA ES LA LÍNEA QUE AÑADIMOS ---
            btn.setFocusable(false); 
            // -------------------------------------

            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.setBorder(new CompoundBorder(
                new LineBorder(BORDE_NORMAL, 1, true),
                new EmptyBorder(6, 6, 6, 6)
            ));

            ImageIcon iconNormalTmp = null;
            ImageIcon iconHoverTmp = null;

            try {
                java.net.URL url = frame.getClass().getResource("/Panes/" + numImg + ".jpeg");
                if (url != null) {
                    ImageIcon icon = new ImageIcon(url);
                    
                    Image imgNormal = icon.getImage().getScaledInstance(200, 160, Image.SCALE_SMOOTH);
                    Image imgHover = icon.getImage().getScaledInstance(230, 190, Image.SCALE_SMOOTH);
                    
                    iconNormalTmp = new ImageIcon(imgNormal);
                    iconHoverTmp = new ImageIcon(imgHover);
                    
                    btn.setIcon(iconNormalTmp);
                }
            } catch (Exception e) {
                System.out.println("Error en imagen: " + nombreActual);
            }

            final ImageIcon iconNormal = iconNormalTmp;
            final ImageIcon iconHover = iconHoverTmp;

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorFondo); 
                    btn.setBorder(new CompoundBorder(
                        new LineBorder(BORDE_HOVER, 2, true),
                        new EmptyBorder(5, 5, 5, 5)
                    ));
                    if (iconHover != null) {
                        btn.setIcon(iconHover);
                    }
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorFondo);
                    btn.setBorder(new CompoundBorder(
                        new LineBorder(BORDE_NORMAL, 1, true),
                        new EmptyBorder(6, 6, 6, 6)
                    ));
                    if (iconNormal != null) {
                        btn.setIcon(iconNormal);
                    }
                }
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorFondo.darker());
                }
                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorFondo);
                }
            });
        }
    }
}