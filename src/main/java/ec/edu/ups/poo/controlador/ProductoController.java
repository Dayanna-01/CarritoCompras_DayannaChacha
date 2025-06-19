package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductoController {
    private final ProductoAñadirView productoAñadirView;
    private final ProductoListaView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEditarView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAñadirView carritoAñadirView;


    public ProductoController(ProductoDAO productoDAO,
                              ProductoAñadirView productoAñadirView,
                              ProductoListaView productoListaView,
                              ProductoEditarView productoGestionView, ProductoEliminarView productoEliminarView, CarritoAñadirView carritoAñadirView) {
        this.productoDAO = productoDAO;
        this.productoAñadirView = productoAñadirView;
        this.productoListaView = productoListaView;
        this.productoEditarView = productoGestionView;
        this.productoEliminarView = productoEliminarView;
        this.carritoAñadirView = carritoAñadirView;
        configurarEventos();
    }


    private void configurarEventos() {
        productoAñadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        productoEditarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEdicion();
            }
        });

        productoEditarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoEliminar();
            }
        });
        carritoAñadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoCarrito();
            }
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAñadirView.getTxtCodigo().getText());
        String nombre = productoAñadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAñadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAñadirView.mostrarMensaje("Producto guardado correctamente");
        productoAñadirView.limpiarCampos();
        productoAñadirView.mostrarProductos(productoDAO.listarTodos());
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
        if (codigo != -1 ) {
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                boolean confirmado = productoEditarView.mostrarMensajePregunta("¿Desea actualizar el producto?");
                if (confirmado) {
                    double precio = Double.parseDouble(txtPrecio);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    productoDAO.actualizar(producto);
                    productoEditarView.mostrarMensaje("Producto actualizado correctamente");
                }else{
                    productoEliminarView.mostrarMensaje("Actualización cancelada");
                }
            }
        } else {
            productoEditarView.mostrarMensaje("Ingrese un código de producto válido");
        }
    }

    private void eliminarProducto() {
        String text_codigo = productoEliminarView.getTxtBuscar().getText();
        int codigo = Integer.parseInt(text_codigo);
        String nombre = productoEliminarView.getTxtNombre().getText();
        String txtPrecio = productoEliminarView.getTxtPrecio().getText();
        if(codigo != -1 && !nombre.isEmpty() && !txtPrecio.isEmpty()){
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
        }else{
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
        String txtCod = carritoAñadirView.getTxtBuscar().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carritoAñadirView.getTxtNombre().setText(producto.getNombre());
                carritoAñadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                buscarPorCodigo(codigo);
            } else {
                carritoAñadirView.mostrarMensaje("Producto no encontrado");
                carritoAñadirView.limpiarCampos();
            }
        } else {
            carritoAñadirView.mostrarMensaje("Ingresa un código para buscar");
        }
    }
    public Producto buscarPorCodigo(int codigo) {
        return productoDAO.buscarPorCodigo(codigo);
    }

}