import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class EfectosApp {
    private final JLabel estado = new JLabel("Seleccione un efecto.");
    private Clip actual;

    private void reproducir(String nombre, String archivo) {
        File wav = new File("recursos", archivo);
        try (AudioInputStream audio = AudioSystem.getAudioInputStream(wav)) {
            if (actual != null) actual.close();
            actual = AudioSystem.getClip();
            actual.open(audio);
            actual.start();
            estado.setText("Efecto seleccionado: " + nombre);
            System.out.println("Reproduciendo " + nombre + " (" + archivo + ")");
        } catch (IOException | UnsupportedAudioFileException
                 | LineUnavailableException | IllegalArgumentException ex) {
            if (actual != null) actual.close();
            actual = null;
            estado.setText("No se pudo reproducir " + archivo);
            System.err.println(estado.getText() + ": " + ex.getMessage());
        }
    }

    private void abrir() {
        JFrame ventana = new JFrame("Sebastian | Efectos de sonido");
        JPanel botones = new JPanel(new GridLayout(1, 3, 8, 0));
        String[] nombres = {"Aplausos", "Campana", "Explosión"};
        String[] archivos = {"aplausos.wav", "campana.wav", "explosion.wav"};
        for (int i = 0; i < nombres.length; i++) {
            String nombre = nombres[i], archivo = archivos[i];
            JButton boton = new JButton(nombre);
            boton.addActionListener(evento -> reproducir(nombre, archivo));
            botones.add(boton);
        }
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
        SwingUtilities.invokeLater(() -> new EfectosApp().abrir());
    }
}
