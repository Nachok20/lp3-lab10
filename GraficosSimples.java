import java.awt.*;
import javax.swing.*;

public class GraficosSimples extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(30, 78, 151));
        g.drawLine(30, 95, 110, 35);
        g.setColor(new Color(173, 52, 54));
        g.drawRect(145, 35, 80, 60);
        g.setColor(new Color(29, 121, 83));
        g.drawOval(265, 35, 80, 60);
        g.setColor(new Color(222, 143, 43));
        g.fillOval(385, 35, 60, 60);
        g.setColor(Color.DARK_GRAY);
        g.drawString("Línea", 42, 123);
        g.drawString("Rectángulo", 150, 123);
        g.drawString("Óvalo", 280, 123);
        g.drawString("Círculo", 393, 123);
        g.drawString("Primitivas de Graphics en un JPanel", 30, 162);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Gráficos simples");
            GraficosSimples panel = new GraficosSimples();
            panel.setBackground(Color.WHITE);
            panel.setPreferredSize(new Dimension(485, 180));
            ventana.add(panel);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.pack();
            ventana.setMinimumSize(ventana.getSize());
            ventana.setLocationByPlatform(true);
            ventana.setVisible(true);
            System.out.println("Figuras: línea, rectángulo, óvalo y círculo.");
        });
    }
}
