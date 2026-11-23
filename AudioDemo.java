import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class AudioDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Reproducción de WAV");
            JButton boton = new JButton("Reproducir audio");
            boton.addActionListener(e -> reproducir(ventana));
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(22, 32, 22, 32));
            panel.add(boton);
            ventana.add(panel);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.pack();
            ventana.setLocationByPlatform(true);
            ventana.setVisible(true);
        });
    }

    private static void reproducir(JFrame ventana) {
        File archivo = new File("recursos/audio.wav");
        try (AudioInputStream entrada = AudioSystem.getAudioInputStream(archivo)) {
            Clip clip = AudioSystem.getClip();
            try {
                clip.open(entrada);
                clip.addLineListener(evento -> {
                    if (evento.getType() == LineEvent.Type.STOP) clip.close();
                });
                clip.start();
                System.out.printf("Reproducción de %s (%.2f s)%n",
                        archivo.getName(), clip.getMicrosecondLength() / 1e6);
            } catch (LineUnavailableException | IOException | RuntimeException ex) {
                clip.close();
                throw ex;
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException |
                 IOException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ventana,
                    "No se pudo reproducir el WAV: " + ex.getMessage());
        }
    }
}
