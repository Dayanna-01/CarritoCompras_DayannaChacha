package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            PrincipalView principalView = new PrincipalView();
            ProductoDAO productoDAO = new ProductoDAOMemoria();

            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
            ProductoEditarView productoEditarView = new ProductoEditarView();
            ProductoEliminarView productoEliminarView = new ProductoEliminarView();
            ProductoListaView productoListaView = new ProductoListaView();
            /*
            ProductoController productoController = new ProductoController(productoDAO);
            productoController.setProductoAnadirView(productoAnadirView);
            productoController.setProductoEditarView(productoEditarView);
            productoController.setProductoEliminarView(productoEliminarView);
            productoController.setProductoListaView(productoListaView);
            */
            ProductoController productoController = new ProductoController(productoDAO, productoAnadirView,
                    productoListaView, productoEditarView, productoEliminarView);

            principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    principalView.getjDesktopPane().add(productoAnadirView);
                    productoAnadirView.toFront();
                }
            });
            principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    principalView.getjDesktopPane().add(productoEditarView);
                    productoAnadirView.toFront();
                }
            });
            principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    principalView.getjDesktopPane().add(productoEliminarView);
                    productoAnadirView.toFront();
                }
            });
            principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    principalView.getjDesktopPane().add(productoListaView);
                    productoAnadirView.toFront();
                }
            });
        });
    }
}