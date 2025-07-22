package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

/**
 * Clase que extiende {@link JDesktopPane} para personalizar su apariencia
 * con un fondo gráfico que incluye cielo, suelo, flores, un carrito de compras y una señora.
 * Además, permite internacionalización mediante {@link MensajeInternacionalizacionHandler}.
 */
public class MiJDesktopPane extends JDesktopPane {
    /** Manejador de mensajes internacionalizados */
    private MensajeInternacionalizacionHandler mi;

    /**
     * Constructor que inicializa el panel con un manejador de internacionalización.
     *
     * @param mi el manejador de mensajes internacionalizados
     */
    public MiJDesktopPane(MensajeInternacionalizacionHandler mi) {
        super();
        this.mi = mi;
    }

    /**
     * Método sobrescrito para pintar el componente con gráficos personalizados:
     * cielo con degradado, suelo, flores, carrito, señora y eslogan.
     *
     * @param g el contexto gráfico proporcionado por Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Activar suavizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fondo: cielo azul claro con degradado
        GradientPaint fondoGradiente = new GradientPaint(
                0, 0, new Color(135, 206, 250),
                0, height, new Color(255, 255, 255)
        );
        g2d.setPaint(fondoGradiente);
        g2d.fillRect(0, 0, width, height);

        // Suelo verde en la parte inferior
        int sueloY = (int)(height * 0.7);
        g2d.setColor(new Color(34, 139, 34));
        g2d.fillRect(0, sueloY, width, height - sueloY);

        // Dibuja múltiples flores aleatorias sobre el suelo
        Random rand = new Random(1234);
        for (int i = 0; i < 300; i++) {
            int x = rand.nextInt(width);
            int y = sueloY + rand.nextInt(height - sueloY);
            drawFlower(g2d, x, y);
        }

        // Dibuja un carrito de compras
        int cartX = (int)(width * 0.7);
        int cartY = sueloY - 120;
        int cartWidth = 150;
        int cartHeight = 100;
        drawShoppingCart(g2d, cartX, cartY, cartWidth, cartHeight);

        // Dibuja una señora al lado izquierdo del carrito
        int ladyX = cartX - 160;
        int ladyY = cartY + 30;
        int ladyHeight = 110;
        drawLady(g2d, ladyX, ladyY, ladyHeight);

        // Eslogan (el texto no está definido, pero aquí va el diseño)
        Font fuenteEslogan = new Font("SansSerif", Font.BOLD, 24);
        g2d.setFont(fuenteEslogan);
        FontMetrics esloganMetrics = g2d.getFontMetrics();
        int esloganY = cartY + cartHeight + 40;

        // Sombra y color del eslogan (el texto se agregaría aquí)
        g2d.setColor(new Color(0, 0, 0, 100)); // sombra
        g2d.setColor(new Color(255, 140, 0)); // color principal
    }

    /**
     * Dibuja una flor simple con 5 pétalos alrededor de un centro.
     *
     * @param g2d contexto gráfico
     * @param x coordenada x del centro
     * @param y coordenada y del centro
     */
    private void drawFlower(Graphics2D g2d, int x, int y) {
        Color[] petalColors = {
                new Color(255, 182, 193),
                new Color(255, 105, 180),
                new Color(255, 255, 102),
                new Color(255, 160, 122),
                new Color(221, 160, 221)
        };

        Color petalColor = petalColors[(x + y) % petalColors.length];
        g2d.setColor(petalColor);
        int petalSize = 8;

        for (int i = 0; i < 5; i++) {
            double angle = 2 * Math.PI / 5 * i;
            int petalX = x + (int)(Math.cos(angle) * petalSize);
            int petalY = y + (int)(Math.sin(angle) * petalSize);
            g2d.fillOval(petalX, petalY, petalSize, petalSize);
        }

        g2d.setColor(new Color(255, 215, 0)); // centro dorado
        g2d.fillOval(x + 3, y + 3, petalSize, petalSize);
    }

    /**
     * Dibuja un carrito de compras con base, rejilla, ruedas, asa y productos.
     *
     * @param g2d contexto gráfico
     * @param x coordenada x inicial
     * @param y coordenada y inicial
     * @param width ancho del carrito
     * @param height alto del carrito
     */
    private void drawShoppingCart(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setColor(new Color(70, 130, 180)); // base azul acero
        g2d.fillRect(x, y + height / 3, width, height * 2 / 3);

        // Rejilla horizontal blanca
        g2d.setColor(Color.WHITE);
        for (int i = 1; i < 4; i++) {
            int lineY = y + height / 3 + i * (height / 9);
            g2d.drawLine(x, lineY, x + width, lineY);
        }

        // Rejilla vertical
        for (int i = 1; i < 6; i++) {
            int lineX = x + i * (width / 6);
            g2d.drawLine(lineX, y + height / 3, lineX, y + height);
        }

        // Ruedas
        g2d.setColor(new Color(105, 105, 105));
        int ruedaRadio = height / 6;
        g2d.fillOval(x + ruedaRadio, y + height, ruedaRadio * 2, ruedaRadio * 2);
        g2d.fillOval(x + width - 3 * ruedaRadio, y + height, ruedaRadio * 2, ruedaRadio * 2);

        // Asa
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(new Color(139, 69, 19)); // marrón
        g2d.drawLine(x, y + height / 3, x - width / 4, y);

        // Productos (rectángulos coloridos dentro del carrito)
        int prodX = x + width / 10;
        int prodY = y + height / 3 + 10;
        int prodWidth = width / 6;
        int prodHeight = height / 4;

        Color[] productos = {
                new Color(255, 99, 71),
                new Color(50, 205, 50),
                new Color(65, 105, 225),
                new Color(255, 215, 0)
        };

        for (Color prodColor : productos) {
            g2d.setColor(prodColor);
            g2d.fillRect(prodX, prodY, prodWidth, prodHeight);
            prodX += prodWidth + 5;
        }
    }

    /**
     * Dibuja una figura estilizada de una señora con vestido, rostro y cabello.
     *
     * @param g2d contexto gráfico
     * @param x coordenada x de inicio
     * @param y coordenada y de inicio
     * @param height altura total de la figura
     */
    private void drawLady(Graphics2D g2d, int x, int y, int height) {
        int bodyHeight = (int)(height * 0.6);
        int bodyWidth = bodyHeight / 2;
        int headDiameter = bodyWidth;

        // Vestido
        g2d.setColor(new Color(220, 20, 60));
        g2d.fillOval(x, y + height - bodyHeight, bodyWidth, bodyHeight);

        // Cara
        g2d.setColor(new Color(255, 224, 189));
        g2d.fillOval(x + bodyWidth / 4, y + height - bodyHeight - headDiameter / 2, headDiameter, headDiameter);

        // Ojos
        g2d.setColor(Color.BLACK);
        int eyeY = y + height - bodyHeight - headDiameter / 4;
        int eyeSpacing = headDiameter / 4;
        g2d.fillOval(x + bodyWidth / 4 + eyeSpacing, eyeY, 6, 4);
        g2d.fillOval(x + bodyWidth / 4 + eyeSpacing * 2, eyeY, 6, 4);

        // Boca
        g2d.setStroke(new BasicStroke(2));
        int bocaX1 = x + bodyWidth / 4 + eyeSpacing;
        int bocaX2 = bocaX1 + eyeSpacing;
        int bocaY = y + height - bodyHeight + headDiameter / 8;
        g2d.drawLine(bocaX1, bocaY, bocaX2, bocaY);

        // Cabello
        g2d.setColor(new Color(139, 69, 19));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawArc(x + bodyWidth / 4, y + height - bodyHeight - headDiameter / 2, headDiameter, headDiameter / 2, 0, 180);
        g2d.drawLine(x + bodyWidth / 4, y + height - bodyHeight - headDiameter / 4, x, y + height - bodyHeight + headDiameter / 4);
        g2d.drawLine(x + bodyWidth / 4 + headDiameter, y + height - bodyHeight - headDiameter / 4, x + bodyWidth + 10, y + height - bodyHeight + headDiameter / 4);
    }

    /**
     * Método público que actualiza el idioma al repintar el componente.
     */
    public void actualizarIdioma() {
        repaint();
    }
}
