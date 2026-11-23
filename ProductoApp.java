import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;

public class ProductoApp {
    static class Producto {
        private String nombre, categoria;
        private BigDecimal precio;
        private int cantidadStock;

        void setDatos(String nombre, BigDecimal precio, int cantidadStock,
                      String categoria) {
            this.nombre = nombre;
            this.precio = precio;
            this.cantidadStock = cantidadStock;
            this.categoria = categoria;
        }

        String getResumen() {
            return String.format("%s | S/ %s | Stock: %d | %s",
                    nombre, precio.toPlainString(), cantidadStock, categoria);
        }
    }

    private static void abrir() {
        JFrame ventana = new JFrame("Sebastian | Inventario de tienda");
        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 6));
        JTextField nombre = new JTextField("Cuaderno", 17);
        JTextField precio = new JTextField("8.50");
        JTextField stock = new JTextField("12");
        JTextField categoria = new JTextField("Papelería");
        formulario.add(new JLabel("Nombre del producto"));
        formulario.add(nombre);
        formulario.add(new JLabel("Precio (S/)"));
        formulario.add(precio);
        formulario.add(new JLabel("Cantidad en stock"));
        formulario.add(stock);
        formulario.add(new JLabel("Categoría"));
        formulario.add(categoria);

        Producto producto = new Producto();
        JLabel resumen = new JLabel("Aún no se ha registrado un producto.");
        JButton actualizar = new JButton("Actualizar Producto");
        actualizar.addActionListener(evento -> {
            try {
                String n = nombre.getText().strip();
                String c = categoria.getText().strip();
                String importe = precio.getText().strip().replace(',', '.');
                if (!importe.matches("[0-9]+([.][0-9]{1,2})?"))
                    throw new IllegalArgumentException();
                BigDecimal p = new BigDecimal(importe).setScale(2);
                int unidades = Integer.parseInt(stock.getText().strip());
                if (n.isEmpty() || c.isEmpty() || unidades < 0)
                    throw new IllegalArgumentException();
                producto.setDatos(n, p, unidades, c);
                resumen.setText(producto.getResumen());
                System.out.println("Producto actualizado: " + producto.getResumen());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ventana,
                        "Complete los textos. Ingrese un precio con hasta dos\n"
                        + "decimales y una cantidad entera, ambos no negativos.");
            }
        });

        JPanel contenido = new JPanel(new BorderLayout(10, 12));
        contenido.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        contenido.add(formulario, BorderLayout.NORTH);
        contenido.add(actualizar, BorderLayout.CENTER);
        contenido.add(resumen, BorderLayout.SOUTH);
        ventana.setContentPane(contenido);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductoApp::abrir);
    }
}
