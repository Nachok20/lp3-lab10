import java.awt.*;
import java.io.*;
import java.util.Locale;
import javax.sound.sampled.*;
import javax.swing.*;

public class MusicaApp {
    private final JLabel estado = new JLabel("Pista: musica.wav");
    private final JButton reproducir = new JButton("Reproducir");
    private final JButton pausar = new JButton("Pausar");
    private final JButton reanudar = new JButton("Reanudar");
    private Clip pista;

    private void informar(String accion) {
        double segundos = pista.getMicrosecondPosition() / 1_000_000.0;
        String mensaje = String.format(Locale.ROOT, "%s · %.2f s", accion, segundos);
        estado.setText(mensaje);
        System.out.println(mensaje);
    }

    private void abrir() {
        JFrame ventana = new JFrame("Sebastian | Reproductor de música");
        JPanel botones = new JPanel(new GridLayout(1, 3, 8, 0));
        botones.add(reproducir);
        botones.add(pausar);
        botones.add(reanudar);
        pausar.setEnabled(false);
        reanudar.setEnabled(false);
        try (AudioInputStream audio = AudioSystem.getAudioInputStream(
                new File("recursos/musica.wav"))) {
            pista = AudioSystem.getClip();
            pista.open(audio);
        } catch (IOException | UnsupportedAudioFileException
                 | LineUnavailableException | IllegalArgumentException ex) {
            if (pista != null) pista.close();
            reproducir.setEnabled(false);
            estado.setText("No se pudo abrir la pista o la salida de audio.");
            System.err.println(estado.getText() + " " + ex.getMessage());
        }

        reproducir.addActionListener(evento -> {
            pista.stop();
            pista.setFramePosition(0);
            informar("Reproducción desde el inicio");
            pista.start();
            pausar.setEnabled(true);
            reanudar.setEnabled(false);
        });
        pausar.addActionListener(evento -> {
            pista.stop();
            informar("En pausa");
            pausar.setEnabled(false);
            reanudar.setEnabled(true);
        });
        reanudar.addActionListener(evento -> {
            informar("Reanudación");
            pista.start();
            pausar.setEnabled(true);
            reanudar.setEnabled(false);
        });
        Timer fin = new Timer(150, evento -> {
            if (pausar.isEnabled() && !pista.isRunning()
                    && pista.getFramePosition() >= pista.getFrameLength()) {
                pausar.setEnabled(false);
                informar("Pista finalizada");
            }
        });
        if (reproducir.isEnabled()) fin.start();
        JPanel contenido = new JPanel(new BorderLayout(8, 12));
        contenido.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        contenido.add(botones, BorderLayout.CENTER);
        contenido.add(estado, BorderLayout.SOUTH);
        ventana.setContentPane(contenido);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicaApp().abrir());
    }
}
