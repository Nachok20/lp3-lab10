import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.Path;
import javax.swing.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.*;

public class VideoDemo {
    private static void abrir() {
        JFrame ventana = new JFrame("Reproducción de MP4");
        JFXPanel pantalla = new JFXPanel();
        pantalla.setPreferredSize(new Dimension(480, 270));
        JButton repetir = new JButton("Repetir video");
        repetir.setEnabled(false);
        ventana.add(pantalla);
        ventana.add(repetir, BorderLayout.SOUTH);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setLocationByPlatform(true);
        ventana.setVisible(true);
        Platform.runLater(() -> {
            try {
                String uri = Path.of("recursos/video.mp4").toUri().toString();
                Media medio = new Media(uri);
                MediaPlayer player = new MediaPlayer(medio);
                MediaView vista = new MediaView(player);
                StackPane panel = new StackPane(vista);
                vista.fitWidthProperty().bind(panel.widthProperty());
                vista.fitHeightProperty().bind(panel.heightProperty());
                pantalla.setScene(new Scene(panel));
                medio.setOnError(() -> error(ventana, medio.getError()));
                player.setOnError(() -> error(ventana, player.getError()));
                player.setOnPlaying(() -> System.out.printf(
                        "Video MP4 en reproducción: %d x %d, %.1f s%n",
                        medio.getWidth(), medio.getHeight(),
                        medio.getDuration().toSeconds()));
                player.setOnReady(() -> SwingUtilities.invokeLater(() -> {
                    repetir.addActionListener(e -> Platform.runLater(() -> {
                        player.stop();
                        player.play();
                    }));
                    repetir.setEnabled(true);
                }));
                player.setAutoPlay(true);
            } catch (MediaException ex) {
                error(ventana, ex);
            }
        });
    }

    private static void error(JFrame ventana, MediaException ex) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                ventana, "No se pudo reproducir el MP4: " + ex.getMessage()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VideoDemo::abrir);
    }
}
