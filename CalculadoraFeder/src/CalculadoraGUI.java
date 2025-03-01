import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraGUI extends JFrame implements ActionListener {

    private JTextField pantalla;
    private JButton[] botonesNumeros;
    private JButton[] botonesOperaciones;
    private JButton botonIgual, botonLimpiar;
    private String operador;
    private double operando1, operando2;

    public CalculadoraGUI() {
        setTitle("Calculadora Colorida");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pantalla
        pantalla = new JTextField();
        pantalla.setPreferredSize(new Dimension(300, 100)); // Pantalla más grande
        pantalla.setFont(new Font("Arial", Font.BOLD, 40)); // Números más grandes
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setBackground(new Color(255, 240, 245)); // Rosa pálido
        pantalla.setForeground(new Color(139, 0, 139)); // Morado oscuro
        pantalla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 112, 214)), // Orquídea claro
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        add(pantalla, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 4, 10, 10));
        panelBotones.setBackground(new Color(240, 255, 240)); // Verde claro
        add(panelBotones, BorderLayout.CENTER);

        // Botones numéricos
        botonesNumeros = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botonesNumeros[i] = crearBotonEstilizado(String.valueOf(i), new Color(255, 255, 224), new Color(70, 130, 180)); // Amarillo claro, azul acero
            panelBotones.add(botonesNumeros[i]);
            botonesNumeros[i].addActionListener(this);
        }

        // Botones de operaciones
        botonesOperaciones = new JButton[4];
        String[] operaciones = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            botonesOperaciones[i] = crearBotonEstilizado(operaciones[i], new Color(255, 228, 181), new Color(205, 92, 92)); // Melocotón, rojo indio
            panelBotones.add(botonesOperaciones[i]);
            botonesOperaciones[i].addActionListener(this);
        }

        // Botón igual
        botonIgual = crearBotonEstilizado("=", new Color(176, 224, 230), new Color(0, 128, 128)); // Azul polvo, verde azulado
        panelBotones.add(botonIgual);
        botonIgual.addActionListener(this);

        // Botón limpiar
        botonLimpiar = crearBotonEstilizado("C", new Color(255, 182, 193), new Color(178, 34, 34)); // Rosa claro, rojo ladrillo
        panelBotones.add(botonLimpiar);
        botonLimpiar.addActionListener(this);

        setVisible(true);
    }

    private JButton crearBotonEstilizado(String texto, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(60, 60));
        boton.setFont(new Font("Verdana", Font.BOLD, 22)); // Fuente diferente y tamaño
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        try {
            int numero = Integer.parseInt(comando);
            pantalla.setText(pantalla.getText() + numero);
        } catch (NumberFormatException ex) {
            if (comando.equals("C")) {
                pantalla.setText("");
                operador = null;
                operando1 = 0;
                operando2 = 0;
            } else if (comando.equals("=")) {
                operando2 = Double.parseDouble(pantalla.getText());
                double resultado = calcularResultado();
                pantalla.setText(String.valueOf(resultado));
                operador = null;
            } else {
                operando1 = Double.parseDouble(pantalla.getText());
                operador = comando;
                pantalla.setText("");
            }
        }
    }

    private double calcularResultado() {
        switch (operador) {
            case "+":
                return operando1 + operando2;
            case "-":
                return operando1 - operando2;
            case "*":
                return operando1 * operando2;
            case "/":
                if (operando2 != 0) {
                    return operando1 / operando2;
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede dividir por cero", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
            default:
                return operando2;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraGUI());
    }
}