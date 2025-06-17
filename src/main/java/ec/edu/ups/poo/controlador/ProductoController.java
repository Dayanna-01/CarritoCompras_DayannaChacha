package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.ProductoAnadirView;
import ec.edu.ups.poo.vista.ProductoEditarView;
import ec.edu.ups.poo.vista.ProductoEliminarView;
import ec.edu.ups.poo.vista.ProductoListaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEditarView;
    private final ProductoEliminarView productoEliminarView;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEditarView productoEditarView,
                              ProductoEliminarView productoEliminarView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEditarView = productoEditarView;
        this.productoEliminarView = productoEliminarView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());
        productoListaView.getBtnListar().addActionListener(e -> listarProductos());
        productoEditarView.getBtnBuscar().addActionListener(e -> buscarProductoEdicion());
        productoEditarView.getBtnActualizar().addActionListener(e -> actualizarProducto());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());
        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoEliminar());
    }

    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje("Producto guardado correctamente");
            productoAnadirView.limpiarCampos();
            productoAnadirView.mostrarProductos(productoDAO.listarTodos());
        } catch (NumberFormatException e) {
            productoAnadirView.mostrarMensaje("Código o precio inválido");
        }
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        productoListaView.cargarDatos(productoDAO.listarTodos());
    }

    private void buscarProductoEdicion() {
        try {
            int codigo = Integer.parseInt(productoEditarView.getTxtBuscar().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEditarView.setTxtNombre(producto.getNombre());
                productoEditarView.setTxtPrecio(String.valueOf(producto.getPrecio()));
            } else {
                productoEditarView.mostrarMensaje("Producto no encontrado");
                productoEditarView.limpiarCampos();
            }
        } catch (NumberFormatException e) {
            productoEditarView.mostrarMensaje("Código inválido");
        }
    }

    private void actualizarProducto() {
        try {
            int codigo = Integer.parseInt(productoEditarView.getTxtBuscar().getText());
            String nombre = productoEditarView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoEditarView.getTxtPrecio().getText());

            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoEditarView.mostrarMensajePregunta("¿Desea actualizar el producto?");
                if (confirmado) {
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    productoDAO.actualizar(producto);
                    productoEditarView.mostrarMensaje("Producto actualizado correctamente");
                } else {
                    productoEditarView.mostrarMensaje("Actualización cancelada");
                }
            } else {
                productoEditarView.mostrarMensaje("Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            productoEditarView.mostrarMensaje("Código o precio inválido");
        }
    }

    private void eliminarProducto() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtBuscar().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoEliminarView.mostrarMensajePregunta("¿Desea eliminar el producto?");
                if (confirmado) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje("Producto eliminado correctamente");
                    productoEliminarView.limpiarCampos();
                } else {
                    productoEliminarView.mostrarMensaje("Eliminación cancelada");
                }
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            productoEliminarView.mostrarMensaje("Código inválido");
        }
    }

    private void buscarProductoEliminar() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtBuscar().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEliminarView.setTxtNombre(producto.getNombre());
                productoEliminarView.setTxtPrecio(String.valueOf(producto.getPrecio()));
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado");
                productoEliminarView.limpiarCampos();
            }
        } catch (NumberFormatException e) {
            productoEliminarView.mostrarMensaje("Código inválido");
        }
    }
}
