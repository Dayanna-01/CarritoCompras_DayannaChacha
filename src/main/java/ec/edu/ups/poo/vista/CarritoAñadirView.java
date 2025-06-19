package ec.edu.ups.poo.vista;

import javax.swing.*;

public class CarritoAñadirView extends JInternalFrame {
    private JButton btnBuscar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAñadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;
    private JPanel PanelPrincipal;

    public class carritoAñadirView extends JInternalFrame {
        public carritoAñadirView() {
            super("Carrito de Compras", true, true, true, true);
            setContentPane(PanelPrincipal);
            setSize(600, 400);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            cargarDatos();
        }
    }


    private void cargarDatos(){
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++){
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnAnadir() {
        return btnAñadir;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnAñadir;
    }

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public JPanel getPanelPrincipal() {
        return PanelPrincipal;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
    }
}