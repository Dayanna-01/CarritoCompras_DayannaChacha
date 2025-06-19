package ec.edu.ups.poo.vista;

import javax.swing.*;

public class CarritoAñadirView extends JInternalFrame {
    private JPanel PanelPrincipal;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton buscarButton;
    private JButton añadirButton;
    private JTable table1;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox cbxCantidad;

    public CarritoAñadirView() {
        super("Carrito de Compras", true, true, false, true);
        setContentPane(PanelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        cargarDatos();
    }

    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setContentPane(new CarritoAñadirView().getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Getters y Setters

    public JPanel getPanel1() {
        return PanelPrincipal;
    }

    public void setPanel1(JPanel panel1) {
        this.PanelPrincipal = panel1;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public void setTextField2(JTextField textField2) {
        this.textField2 = textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public void setTextField3(JTextField textField3) {
        this.textField3 = textField3;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public void setBuscarButton(JButton buscarButton) {
        this.buscarButton = buscarButton;
    }

    public JButton getAñadirButton() {
        return añadirButton;
    }

    public void setAñadirButton(JButton añadirButton) {
        this.añadirButton = añadirButton;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public void setGuardarButton(JButton guardarButton) {
        this.guardarButton = guardarButton;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public void setCancelarButton(JButton cancelarButton) {
        this.cancelarButton = cancelarButton;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public void setTextField4(JTextField textField4) {
        this.textField4 = textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public void setTextField5(JTextField textField5) {
        this.textField5 = textField5;
    }

    public JTextField getTextField6() {
        return textField6;
    }

    public void setTextField6(JTextField textField6) {
        this.textField6 = textField6;
    }

    public JComboBox getComboBox1() {
        return cbxCantidad;
    }

    public void setComboBox1(JComboBox comboBox1) {
        this.cbxCantidad = comboBox1;
    }
}
