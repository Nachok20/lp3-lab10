import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class Ventas extends JPanel {
    private final String[] productos = {
        "Producto A", "Producto B", "Producto C", "Producto D"
    };
    private final int[] ventas = {50, 120, 80, 150};

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dibujo = (Graphics2D) g.create();
        dibujo.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int maximo = Arrays.stream(ventas).max().orElse(1);
        int base = getHeight() - 42;
        int paso = (getWidth() - 68) / ventas.length;
        dibujo.setColor(Color.DARK_GRAY);
        dibujo.drawString("Ventas (unidades)", 18, 23);
        dibujo.drawLine(38, 39, 38, base);
        dibujo.drawLine(38, base, getWidth() - 18, base);
        for (int i = 0; i < ventas.length; i++) {
            int centro = 38 + paso * i + paso / 2;
            int altura = (base - 58) * ventas[i] / Math.max(1, maximo);
            int y = base - altura;
            dibujo.setColor(new Color(54, 109, 159));
            dibujo.fillRect(centro - paso / 4, y, paso / 2, altura);
            dibujo.setColor(Color.DARK_GRAY);
            centrar(dibujo, Integer.toString(ventas[i]), centro, y - 6);
            centrar(dibujo, productos[i], centro, base + 19);
        }
        dibujo.dispose();
    }

    private void centrar(Graphics g, String texto, int x, int y) {
        int ancho = g.getFontMetrics().stringWidth(texto);
        g.drawString(texto, x - ancho / 2, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Ventas por producto");
            Ventas panel = new Ventas();
            panel.setPreferredSize(new Dimension(490, 280));
            panel.setBackground(Color.WHITE);
            ventana.add(panel);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.pack();
            ventana.setMinimumSize(ventana.getSize());
            ventana.setLocationByPlatform(true);
            ventana.setVisible(true);
            for (int i = 0; i < panel.productos.length; i++)
                System.out.printf("%s: %d unidades%n",
                        panel.productos[i], panel.ventas[i]);
        });
    }
}
