package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {

    public MiJDesktopPane() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Fondo blanco
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // -----------------------
        // DIBUJO DEL SEÑOR
        // -----------------------

        // Cabeza
        g2d.setColor(new Color(255, 220, 180)); // color piel
        g2d.fillOval(centerX - 140, centerY - 60, 30, 30);

        // Cuerpo
        g2d.setColor(new Color(139, 69, 19)); // marrón para suéter
        g2d.fillRect(centerX - 140, centerY - 30, 30, 40);

        // Piernas
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(centerX - 140, centerY + 10, 10, 30); // pierna izq
        g2d.fillRect(centerX - 120, centerY + 10, 10, 30); // pierna der

        // Brazos
        g2d.setColor(new Color(139, 69, 19));
        g2d.fillRect(centerX - 160, centerY - 20, 20, 10); // brazo al carrito

        // -----------------------
        // DIBUJO DEL CARRITO
        // -----------------------
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(centerX - 100, centerY - 30, 120, 60); // marco carrito

        // Rejilla horizontal
        for (int i = 1; i <= 2; i++) {
            g2d.drawLine(centerX - 100, centerY - 30 + i * 20, centerX + 20, centerY - 30 + i * 20);
        }

        // Rejilla vertical
        for (int i = 1; i <= 3; i++) {
            g2d.drawLine(centerX - 100 + i * 30, centerY - 30, centerX - 100 + i * 30, centerY + 30);
        }

        // Ruedas
        g2d.fillOval(centerX - 90, centerY + 40, 15, 15); // izquierda
        g2d.fillOval(centerX + 5, centerY + 40, 15, 15);  // derecha

        // Productos dentro del carrito
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(centerX - 90, centerY - 25, 20, 20); // caja
        g2d.setColor(Color.GREEN);
        g2d.fillRect(centerX - 60, centerY - 25, 20, 25); // lechuga
        g2d.setColor(Color.BLUE);
        g2d.fillRect(centerX - 30, centerY - 20, 15, 30); // botella

        // Texto "Compras"
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        String texto = "Compras";
        int textoWidth = g2d.getFontMetrics().stringWidth(texto);
        g2d.drawString(texto, centerX - textoWidth / 2, centerY+80);
    }
}