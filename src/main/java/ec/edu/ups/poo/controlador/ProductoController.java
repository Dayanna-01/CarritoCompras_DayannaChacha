package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.ProductoAñadirView;
import ec.edu.ups.poo.vista.ProductoListaView;
import ec.edu.ups.poo.vista.ProductoEditarView;
import ec.edu.ups.poo.vista.ProductoEliminarView;
import ec.edu.ups.poo.vista.CarritoAñadirView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAñadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEditarView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAñadirView carritoAnadirView;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAñadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEditarView productoEditarView,
                              ProductoEliminarView productoEliminarView,
                              CarritoAñadirView carritoAnadirView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEditarView = productoEditarView;
        this.productoEliminarView = productoEliminarView;
        this.carritoAnadirView = carritoAnadirView;
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

        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoCarrito());
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void actualizarProducto() {
        String txtCod = productoEditarView.getTxtBuscar().getText();
        int codigo = Integer.parseInt(txtCod);
        String nombre = productoEditarView.getTxtNombre().getText();
        String txtPrecio = productoEditarView.getTxtPrecio().getText();

        if (codigo != -1) {
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoEditarView.mostrarMensajePregunta("¿Desea actualizar el producto?");
                if (confirmado) {
                    double precio = Double.parseDouble(txtPrecio);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    productoDAO.actualizar(producto);
                    productoEditarView.mostrarMensaje("Producto actualizado correctamente");
                } else {
                    productoEditarView.mostrarMensaje("Actualización cancelada");
                }
            }
        } else {
            productoEditarView.mostrarMensaje("Ingrese un código de producto válido");
        }
    }

    private void eliminarProducto() {
        String textCodigo = productoEliminarView.getTxtBuscar().getText();
        int codigo = Integer.parseInt(textCodigo);
        String nombre = productoEliminarView.getTxtNombre().getText();
        String txtPrecio = productoEliminarView.getTxtPrecio().getText();

        if (codigo != -1 && !nombre.isEmpty() && !txtPrecio.isEmpty()) {
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
            }
        } else {
            productoEliminarView.mostrarMensaje("Ingrese un código de producto válido");
        }
    }

    private void buscarProductoEliminar() {
        String txtCod = productoEliminarView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado");
                productoEliminarView.limpiarCampos();
            }
        } else {
            productoEliminarView.mostrarMensaje("Ingresa un código para buscar");
        }
    }

    private void buscarProductoEdicion() {
        String txtCod = productoEditarView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoEditarView.getTxtNombre().setText(producto.getNombre());
                productoEditarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoEditarView.mostrarMensaje("Producto no encontrado");
                productoEditarView.limpiarCampos();
            }
        } else {
            productoEditarView.mostrarMensaje("Ingresa un código para buscar");
        }
    }

    private void buscarProductoCarrito() {
        String txtCod = carritoAnadirView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                buscarPorCodigo(codigo);
            } else {
                carritoAnadirView.mostrarMensaje("Producto no encontrado");
                carritoAnadirView.limpiarCampos();
            }
        } else {
            carritoAnadirView.mostrarMensaje("Ingresa un código para buscar");
        }
    }

    public Producto buscarPorCodigo(int codigo) {
        return productoDAO.buscarPorCodigo(codigo);
    }
}
