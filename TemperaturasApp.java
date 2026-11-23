import java.awt.*;
import java.util.Arrays;
import java.util.Locale;
import javax.swing.*;

public class TemperaturasApp {
    static final String[] DIAS = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};

    static class Grafico extends JPanel {
        private double[] valores;

        Grafico() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(580, 240));
        }

        void setValores(double[] semana) {
            valores = semana.clone();
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (valores == null) {
                g.drawString("Ingrese siete temperaturas y pulse Mostrar Gráfico.", 24, 42);
                return;
            }
            Graphics2D p = (Graphics2D) g.create();
            p.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int izq = 22, base = getHeight() - 34, alto = base - 36;
            int ancho = getWidth() - izq - 32;
            double min = Arrays.stream(valores).min().orElseThrow();
            double max = Arrays.stream(valores).max().orElseThrow();
            double escala = Math.max(Math.abs(min), Math.abs(max));
            if (escala == 0) escala = 1;
            p.setColor(Color.LIGHT_GRAY);
            p.drawLine(izq, 20, izq, base);
            p.drawLine(izq, base, izq + ancho, base);
            p.setColor(new Color(20, 89, 147));
            p.setStroke(new BasicStroke(2.5f));
            int previoX = 0, previoY = 0;
            for (int i = 0; i < valores.length; i++) {
                int x = izq + i * ancho / (DIAS.length - 1);
                double proporcion = min == max ? 0.5
                        : (valores[i] / escala - min / escala)
                        / (max / escala - min / escala);
                int y = base - 8 - (int) Math.round(proporcion * (alto - 8));
                if (i > 0) p.drawLine(previoX, previoY, x, y);
                p.fillOval(x - 4, y - 4, 8, 8);
                String dato = Double.toString(valores[i]) + "°";
                int textoX = x - p.getFontMetrics().stringWidth(dato) / 2;
                p.drawString(dato, Math.max(1, textoX), y - 8);
                p.drawString(DIAS[i], x - 11, base + 18);
                previoX = x;
                previoY = y;
            }
            p.dispose();
        }
    }

    private static void abrir() {
        JFrame ventana = new JFrame("Sebastian | Temperatura semanal (°C)");
        JPanel campos = new JPanel(new GridLayout(2, 7, 5, 3));
        JTextField[] entradas = new JTextField[7];
        String[] ejemplo = {"17", "19", "16", "21", "20", "18", "15"};
        for (String dia : DIAS) campos.add(new JLabel(dia));
        for (int i = 0; i < entradas.length; i++) {
            entradas[i] = new JTextField(ejemplo[i]);
            campos.add(entradas[i]);
        }
        Grafico grafico = new Grafico();
        JButton mostrar = new JButton("Mostrar Gráfico");
        mostrar.addActionListener(evento -> {
            double[] semana = new double[7];
            for (int i = 0; i < semana.length; i++) {
                try {
                    String dato = entradas[i].getText().strip().replace(',', '.');
                    semana[i] = Double.parseDouble(dato);
                    if (!Double.isFinite(semana[i])) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ventana, "Revise el dato de " + DIAS[i]);
                    entradas[i].requestFocusInWindow();
                    return;
                }
            }
            grafico.setValores(semana);
            for (int i = 0; i < semana.length; i++)
                System.out.printf(Locale.ROOT, "%s: %s °C%n", DIAS[i], semana[i]);
        });
        JPanel contenido = new JPanel(new BorderLayout(6, 8));
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        contenido.add(campos, BorderLayout.NORTH);
        contenido.add(grafico, BorderLayout.CENTER);
        contenido.add(mostrar, BorderLayout.SOUTH);
        ventana.setContentPane(contenido);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TemperaturasApp::abrir);
    }
}
