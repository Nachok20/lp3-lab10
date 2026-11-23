import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class PersonaDemo {
    static class Persona {
        private String nombre, sexo;
        private int edad;

        Persona(String nombre, int edad, String sexo) {
            setNombre(nombre);
            setEdad(edad);
            setSexo(sexo);
        }

        String getNombre() { return nombre; }
        int getEdad() { return edad; }
        String getSexo() { return sexo; }
        void setNombre(String valor) { nombre = valor; }
        void setEdad(int valor) { edad = valor; }
        void setSexo(String valor) { sexo = valor; }
    }

    private static void abrir() {
        Persona persona = new Persona("John Doe", 30, "Masculino");
        JFrame ventana = new JFrame("Modelo de persona");
        JTextField nombre = new JTextField(persona.getNombre(), 18);
        JTextField edad = new JTextField(String.valueOf(persona.getEdad()));
        JTextField sexo = new JTextField(persona.getSexo());
        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        formulario.add(new JLabel("Nombre"));
        formulario.add(nombre);
        formulario.add(new JLabel("Edad"));
        formulario.add(edad);
        formulario.add(new JLabel("Sexo"));
        formulario.add(sexo);
        JButton actualizar = new JButton("Actualizar Modelo");
        actualizar.addActionListener(e -> {
            String nuevoNombre = nombre.getText().trim();
            String nuevoSexo = sexo.getText().trim();
            try {
                int nuevaEdad = Integer.parseInt(edad.getText().trim());
                if (nuevaEdad < 0 || nuevoNombre.isBlank() || nuevoSexo.isBlank())
                    throw new IllegalArgumentException();
                persona.setNombre(nuevoNombre);
                persona.setEdad(nuevaEdad);
                persona.setSexo(nuevoSexo);
                System.out.printf("Modelo actualizado: %s | %d años | %s%n",
                        persona.getNombre(), persona.getEdad(), persona.getSexo());
            } catch (IllegalArgumentException error) {
                JOptionPane.showMessageDialog(ventana,
                        "Completa los campos y escribe una edad entera no negativa.");
            }
        });
        ventana.add(formulario);
        ventana.add(actualizar, BorderLayout.SOUTH);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setLocationByPlatform(true);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PersonaDemo::abrir);
    }
}
