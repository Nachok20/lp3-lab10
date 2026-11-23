import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class Transformaciones extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dibujo = (Graphics2D) g.create();
        dibujo.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform inicial = dibujo.getTransform();

        dibujo.setColor(Color.GRAY);
        dibujo.fillRect(40, 45, 64, 36);
        dibujo.translate(200, 0);
        dibujo.setColor(new Color(43, 105, 176));
        dibujo.fillRect(40, 45, 64, 36);
        dibujo.setTransform(inicial);

        dibujo.translate(0, 110);
        dibujo.rotate(Math.toRadians(45), 72, 63);
        dibujo.setColor(new Color(32, 135, 97));
        dibujo.fillRect(40, 45, 64, 36);
        dibujo.setTransform(inicial);

        dibujo.translate(185, 140);
        dibujo.scale(1.5, 0.5);
        dibujo.setColor(new Color(222, 143, 43));
        dibujo.fillRect(40, 45, 64, 36);
        dibujo.setTransform(inicial);

        dibujo.translate(0, 220);
        dibujo.shear(0.5, 0);
        dibujo.setColor(new Color(159, 73, 143));
        dibujo.fillRect(40, 45, 64, 36);
        dibujo.setTransform(inicial);

        dibujo.setColor(Color.DARK_GRAY);
        dibujo.drawString("Original", 40, 105);
        dibujo.drawString("Traslación de 200 px", 240, 105);
        dibujo.drawString("Rotación de 45°", 40, 229);
        dibujo.drawString("Escala (1,5 x 0,5)", 240, 229);
        dibujo.drawString("Sesgado horizontal de 0,5", 40, 329);
        dibujo.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Transformaciones con Graphics2D");
            Transformaciones panel = new Transformaciones();
            panel.setBackground(Color.WHITE);
            panel.setPreferredSize(new Dimension(445, 350));
            ventana.add(panel);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.pack();
            ventana.setMinimumSize(ventana.getSize());
            ventana.setLocationByPlatform(true);
            ventana.setVisible(true);
            System.out.println("Traslación, rotación, escala y sesgado.");
        });
    }
}
