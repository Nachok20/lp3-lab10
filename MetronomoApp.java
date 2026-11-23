import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class MetronomoApp {
    private static class Compas {
        int tempo = 96;
        int pulsos = 4;
    }

    private final Compas modelo = new Compas();
    private final JFrame ventana = new JFrame("Sebastian | Metrónomo");
    private final JTextField tempo = new JTextField("96", 3);
    private final JComboBox<Integer> pulsos =
            new JComboBox<>(new Integer[] {2, 3, 4, 6});
    private final JLabel estado = new JLabel("Listo", SwingConstants.CENTER);
    private final JButton inicio = new JButton("Iniciar");
    private final Panel panel = new Panel();
    private final Timer reloj = new Timer(625, e -> marcar());
    private int actual = -1;
    private boolean derecha;
    private Clip clic;

    private class Panel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D pluma = (Graphics2D) g.create();
            pluma.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                   RenderingHints.VALUE_ANTIALIAS_ON);
            int cx = getWidth() / 2;
            for (int i = 0; i < modelo.pulsos; i++) {
                int x = cx + (2 * i - modelo.pulsos + 1) * 17;
                pluma.setColor(i == actual ? new Color(22, 127, 127)
                                           : new Color(205, 216, 220));
                pluma.fillOval(x - 8, 17, 16, 16);
            }
            pluma.translate(cx, getHeight() - 20);
            double grados = actual < 0 ? 0 : (derecha ? 30 : -30);
            pluma.rotate(Math.toRadians(grados));
            pluma.setColor(new Color(38, 69, 91));
            pluma.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND,
                                                BasicStroke.JOIN_ROUND));
            pluma.drawLine(0, 0, 0, -58);
            pluma.fillOval(-6, -6, 12, 12);
            pluma.dispose();
        }
    }

    private void marcar() {
        actual = (actual + 1) % modelo.pulsos;
        derecha = !derecha;
        clic.stop();
        clic.setFramePosition(0);
        clic.start();
        estado.setText(modelo.tempo + " ppm  |  Pulso " + (actual + 1)
                       + " de " + modelo.pulsos);
        panel.repaint();
    }

    private void aplicar() {
        try {
            int valor = Integer.parseInt(tempo.getText().trim());
            if (valor < 40 || valor > 240) {
                throw new NumberFormatException();
            }
            modelo.tempo = valor;
            modelo.pulsos = (Integer) pulsos.getSelectedItem();
            reloj.setDelay((int) Math.round(60000.0 / valor));
            actual = -1;
            panel.repaint();
            String ajuste = valor + " ppm, " + modelo.pulsos + " pulsos";
            estado.setText(ajuste);
            System.out.println("Compás actualizado: " + ajuste);
        } catch (NumberFormatException ex) {
            estado.setText("Ingrese un tempo entero entre 40 y 240.");
        }
    }

    private void alternar() {
        if (reloj.isRunning()) {
            reloj.stop();
            clic.stop();
            actual = -1;
            estado.setText("Detenido");
            inicio.setText("Iniciar");
            panel.repaint();
            System.out.println("Metrónomo detenido.");
            return;
        }
        try {
            if (clic == null) {
                try (AudioInputStream audio = AudioSystem.getAudioInputStream(
                        new File("recursos/clic.wav"))) {
                    clic = AudioSystem.getClip();
                    clic.open(audio);
                }
            }
            marcar();
            reloj.setInitialDelay(reloj.getDelay());
            reloj.start();
            inicio.setText("Detener");
            System.out.println("Metrónomo en marcha: " + modelo.tempo
                               + " ppm, " + modelo.pulsos + " pulsos.");
        } catch (Exception ex) {
            if (clic != null) {
                clic.close();
                clic = null;
            }
            estado.setText("No se pudo abrir el clic de audio.");
            System.err.println(ex);
        }
    }

    private void abrir() {
        JPanel controles = new JPanel();
        pulsos.setSelectedItem(modelo.pulsos);
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener(e -> aplicar());
        inicio.addActionListener(e -> alternar());
        controles.add(new JLabel("Tempo (ppm)"));
        controles.add(tempo);
        controles.add(new JLabel("Pulsos"));
        controles.add(pulsos);
        controles.add(aplicar);
        controles.add(inicio);
        panel.setPreferredSize(new Dimension(440, 136));
        panel.setBackground(new Color(247, 250, 249));
        ventana.add(controles, BorderLayout.NORTH);
        ventana.add(panel, BorderLayout.CENTER);
        ventana.add(estado, BorderLayout.SOUTH);
        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                reloj.stop();
                if (clic != null) clic.close();
            }
        });
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setLocationByPlatform(true);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MetronomoApp().abrir());
    }
}
