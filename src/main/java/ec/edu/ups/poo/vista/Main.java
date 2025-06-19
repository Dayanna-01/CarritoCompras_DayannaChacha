package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // Crear ventana principal
            PrincipalView principalView = new PrincipalView();

            // Crear DAO
            ProductoDAO productoDAO = new ProductoDAOMemoria();

            // Crear vistas internas
            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
            ProductoEliminarView productoEliminarView = new ProductoEliminarView();
            ProductoEditarView productoEditarView = new ProductoEditarView();
            ProductoListaView productoListaView = new ProductoListaView();
            CarritoAñadirView carritoView = new CarritoAñadirView();

            // Crear controlador
            ProductoController productoController = new ProductoController(
                    productoDAO,
                    productoAnadirView,
                    productoListaView,
                    productoEditarView,
                    productoEliminarView
            );

            // Asociar eventos menú Crear Producto
            principalView.getMenuItemCrearProducto().addActionListener(e -> {
                if (!productoAnadirView.isVisible()) {
                    principalView.getjDesktopPane().add(productoAnadirView);
                    productoAnadirView.setVisible(true);
                }
                productoAnadirView.toFront();
            });

            // Eliminar Producto
            principalView.getMenuItemEliminarProducto().addActionListener(e -> {
                if (!productoEliminarView.isVisible()) {
                    principalView.getjDesktopPane().add(productoEliminarView);
                    productoEliminarView.setVisible(true);
                }
                productoEliminarView.toFront();
            });

            // Actualizar Producto
            principalView.getMenuItemActualizarProducto().addActionListener(e -> {
                if (!productoEditarView.isVisible()) {
                    principalView.getjDesktopPane().add(productoEditarView);
                    productoEditarView.setVisible(true);
                }
                productoEditarView.toFront();
            });

            // Buscar Producto
            principalView.getMenuItemBuscarProducto().addActionListener(e -> {
                if (!productoListaView.isVisible()) {
                    principalView.getjDesktopPane().add(productoListaView);
                    productoListaView.setVisible(true);
                }
                productoListaView.toFront();
            });

        });
    }
}
