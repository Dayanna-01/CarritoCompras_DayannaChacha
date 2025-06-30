package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.CarritoAñadirView;
import ec.edu.ups.poo.vista.ProductoAñadirView;
import ec.edu.ups.poo.vista.ProductoEditarView;
import ec.edu.ups.poo.vista.ProductoEliminarView;
import ec.edu.ups.poo.vista.ProductoListaView;

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
    private final MensajeInternacionalizacionHandler mi;


    public ProductoController(ProductoDAO productoDAO,
                              ProductoAñadirView productoAñadirView,
                              ProductoListaView productoListaView,
                              ProductoEditarView productoGestionView, ProductoEliminarView productoEliminarView,
                              CarritoAñadirView carritoAñadirView, MensajeInternacionalizacionHandler mi) {
        this.productoDAO = productoDAO;
        this.productoAñadirView = productoAñadirView;
        this.productoListaView = productoListaView;
        this.productoEditarView = productoGestionView;
        this.productoEliminarView = productoEliminarView;
        this.carritoAñadirView = carritoAñadirView;
        this.mi = mi;
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
        String codigoTexto = productoAñadirView.getTxtCodigo().getText().trim();
        String nombre = productoAñadirView.getTxtNombre().getText().trim();
        String precioTexto = productoAñadirView.getTxtPrecio().getText().trim();

        if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty()) {
            productoAñadirView.mostrarMensaje(mi.get("producto.mensaje.campos.vacios"));
            return;
        }

        if (!codigoTexto.matches("[1-9]\\d*|0")) {//sin letras, sin decimales
            productoAñadirView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(codigoTexto);
        if (!precioTexto.matches("\\d+(\\.\\d+)?")) {//número decimal positivo
            productoAñadirView.mostrarMensaje(mi.get("producto.mensaje.precio.invalido"));
            return;
        }

        double precio = Double.parseDouble(precioTexto);
        if (productoDAO.buscarPorCodigo(codigo) != null) {
            productoAñadirView.mostrarMensaje(mi.get("producto.mensaje.error.codigo.existe"));
            return;
        }

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAñadirView.mostrarMensaje(mi.get("producto.mensaje.guardado.correctamente"));
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
        String txtCod = productoEditarView.getTxtBuscar().getText().trim();
        String nombre = productoEditarView.getTxtNombre().getText().trim();
        String txtPrecio = productoEditarView.getTxtPrecio().getText().trim();

        if (txtCod.isEmpty() || nombre.isEmpty() || txtPrecio.isEmpty()) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.campos.vacios"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);

        if (!txtPrecio.matches("\\d+(\\.\\d+)?")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.precio.invalido"));
            return;
        }

        double precio = Double.parseDouble(txtPrecio);

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            return;
        }
        boolean confirmado = productoEditarView.mostrarMensajePregunta(mi.get("producto.mensaje.actualizar.pregunta"));
        if (!confirmado) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.actualizacion.cancelada"));
            return;
        }
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        productoDAO.actualizar(producto);
        productoEditarView.mostrarMensaje(mi.get("producto.mensaje.actualizado.correctamente"));
    }

    private void eliminarProducto() {
        String textCodigo = productoEliminarView.getTxtBuscar().getText().trim();
        if (textCodigo.isEmpty()) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.ingresar.codigo"));
            return;
        }
        if (!textCodigo.matches("[1-9]\\d*|0")) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }
        int codigo = Integer.parseInt(textCodigo);
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            return;
        }
        boolean confirmado = productoEliminarView.mostrarMensajePregunta(mi.get("producto.mensaje.eliminar.pregunta"));
        if (!confirmado) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminacion.cancelada"));
            return;
        }
        productoDAO.eliminar(codigo);
        productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.eliminado.correctamente"));
        productoEliminarView.limpiarCampos();
    }


    private void buscarProductoEliminar() {
        String txtCod = productoEliminarView.getTxtBuscar().getText().trim();

        if (txtCod.isEmpty()) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoEliminarView.getTxtNombre().setText(producto.getNombre());
            productoEliminarView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            productoEliminarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            productoEliminarView.limpiarCampos();
        }
    }


    private void buscarProductoEdicion() {
        String txtCod = productoEditarView.getTxtBuscar().getText().trim();

        if (txtCod.isEmpty()) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            productoEditarView.getTxtNombre().setText(producto.getNombre());
            productoEditarView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            productoEditarView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            productoEditarView.limpiarCampos();
        }
    }

    private void buscarProductoCarrito() {
        String txtCod = carritoAñadirView.getTxtBuscar().getText().trim();

        if (txtCod.isEmpty()) {
            carritoAñadirView.mostrarMensaje(mi.get("producto.mensaje.error.ingresar.codigo"));
            return;
        }

        if (!txtCod.matches("[1-9]\\d*|0")) {
            carritoAñadirView.mostrarMensaje(mi.get("producto.mensaje.codigo.invalido"));
            return;
        }

        int codigo = Integer.parseInt(txtCod);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            carritoAñadirView.getTxtNombre().setText(producto.getNombre());
            carritoAñadirView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(producto.getPrecio(), mi.getLocale()));
        } else {
            carritoAñadirView.mostrarMensaje(mi.get("producto.mensaje.no.encontrado"));
            carritoAñadirView.limpiarCampos();
        }
    }

    public void actualizarIdiomaEnVistas() {
        productoAñadirView.cambiarIdioma();
        productoEditarView.cambiarIdioma();
        productoEliminarView.cambiarIdioma();
        productoListaView.cambiarIdioma();
    }

}