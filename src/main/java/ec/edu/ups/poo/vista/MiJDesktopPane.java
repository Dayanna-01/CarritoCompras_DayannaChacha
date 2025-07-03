package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

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

        g2d.setColor(new Color(135, 206, 235));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.YELLOW);
        g2d.fillOval(30, 30, 100, 100);

        g2d.setColor(Color.BLACK);
        int[][] birdPositions = {{200, 100}, {250, 120}, {300, 90}, {350, 130}};
        for (int[] pos : birdPositions) {
            int x = pos[0], y = pos[1];
            g2d.drawLine(x, y, x + 10, y - 10);
            g2d.drawLine(x + 10, y - 10, x + 20, y);
        }

        int sueloY = centerY + 100;
        g2d.setColor(new Color(85, 170, 85));
        g2d.fillRect(0, sueloY, getWidth(), getHeight() - sueloY);

        Random rand = new Random();
        for (int i = 0; i < 150; i++) {
            int x = rand.nextInt(getWidth());
            int y = sueloY + rand.nextInt(getHeight() - sueloY - 10);

            g2d.setColor(new Color(0, 100, 0));
            g2d.drawLine(x, y, x, y - 10);

            g2d.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g2d.fillOval(x - 3, y - 16, 6, 6);
            g2d.fillOval(x - 6, y - 13, 6, 6);
            g2d.fillOval(x + 1, y - 13, 6, 6);
            g2d.fillOval(x - 3, y - 10, 6, 6);
        }

        int marketX = centerX - 300;
        int marketY = centerY - 100;
        int marketWidth = 300;
        int marketHeight = 200;

        g2d.setColor(new Color(240, 240, 240)); // cuerpo
        g2d.fillRect(marketX, marketY, marketWidth, marketHeight);

        g2d.setColor(new Color(200, 0, 0)); // techo
        g2d.fillRect(marketX - 10, marketY - 30, marketWidth + 20, 30);

        g2d.setColor(new Color(100, 100, 100)); // puerta
        g2d.fillRect(marketX + marketWidth / 2 - 25, marketY + marketHeight - 60, 50, 60);

        g2d.setColor(new Color(180, 220, 255)); // ventanas
        g2d.fillRect(marketX + 30, marketY + 40, 60, 40);
        g2d.fillRect(marketX + marketWidth - 90, marketY + 40, 60, 40);

        int manX = marketX + marketWidth + 40;
        int manY = marketY + marketHeight - 80;

        g2d.setColor(new Color(255, 220, 180)); // cabeza
        g2d.fillOval(manX, manY - 30, 30, 30);

        g2d.setColor(new Color(139, 69, 19)); // cuerpo
        g2d.fillRect(manX, manY, 30, 40);

        g2d.setColor(Color.DARK_GRAY); // piernas
        g2d.fillRect(manX, manY + 40, 10, 30);
        g2d.fillRect(manX + 20, manY + 40, 10, 30);

        g2d.setColor(new Color(139, 69, 19)); // brazo
        g2d.fillRect(manX - 20, manY + 10, 20, 10);

        int cartX = manX + 40;
        int cartY = manY;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(cartX, cartY, 120, 60);

        for (int i = 1; i <= 2; i++) // rejilla horizontal
            g2d.drawLine(cartX, cartY + i * 20, cartX + 120, cartY + i * 20);

        for (int i = 1; i <= 3; i++) // rejilla vertical
            g2d.drawLine(cartX + i * 30, cartY, cartX + i * 30, cartY + 60);

        g2d.fillOval(cartX + 10, cartY + 70, 15, 15); // ruedas
        g2d.fillOval(cartX + 95, cartY + 70, 15, 15);

        g2d.setColor(Color.ORANGE); // productos
        g2d.fillRect(cartX + 10, cartY + 5, 20, 20); // caja
        g2d.setColor(Color.GREEN);
        g2d.fillRect(cartX + 40, cartY + 5, 20, 25); // lechuga
        g2d.setColor(Color.BLUE);
        g2d.fillRect(cartX + 70, cartY + 10, 15, 30); // botella
    }
}
