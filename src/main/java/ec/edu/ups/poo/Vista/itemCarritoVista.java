package ec.edu.ups.poo.Vista;

import javax.swing.*;

public class itemCarritoVista extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JSpinner spinner1;
    private JButton listarProductoButton;
    private JButton mostrarTotalButton;
    private JButton buscarProductoButton;
    private JButton aplicarDescuentoButton;

    public itemCarritoVista() {
        setTitle("Vista Items Carrito");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
    public static void main(String[] args) {
        new itemCarritoVista();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
