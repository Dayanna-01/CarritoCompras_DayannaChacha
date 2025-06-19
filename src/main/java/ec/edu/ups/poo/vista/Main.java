package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controlador.CarritoController;
import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            PrincipalView principalView = new PrincipalView();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();
            CarritoAñadirView carritoAñadirView = new CarritoAñadirView();

            ProductoAñadirView productoAnadirView = new ProductoAñadirView();
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
                    productoListaView, productoEditarView, productoEliminarView, carritoAñadirView);

            CarritoController carritoController = new CarritoController(carritoAñadirView, productoController, carritoDAO);

            principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!carritoAñadirView.isVisible()) {
                        carritoAñadirView.setVisible(true);
                        principalView.getjDesktopPane().add(carritoAñadirView);
                    }
                }
            });
            principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoAnadirView.isVisible()) {
                        productoAnadirView.setVisible(true);
                        principalView.getjDesktopPane().add(productoAnadirView);
                    }
                }
            });
            principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoEditarView.isVisible()) {
                        productoEditarView.setVisible(true);
                        principalView.getjDesktopPane().add(productoEditarView);
                    }
                }
            });
            principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoEliminarView.isVisible()) {
                        productoEliminarView.setVisible(true);
                        principalView.getjDesktopPane().add(productoEliminarView);
                    }
                }
            });
            principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!productoListaView.isVisible()) {
                        productoListaView.setVisible(true);
                        principalView.getjDesktopPane().add(productoListaView);
                    }
                }
            });
        });
    }
}