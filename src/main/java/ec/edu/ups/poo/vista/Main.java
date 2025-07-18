package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controlador.CarritoController;
import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.controlador.UsuarioController;
import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.CuestionarioDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.CuestionarioDAOMemoria;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.modelo.ExcepcionValidacion;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MensajeInternacionalizacionHandler mi = new MensajeInternacionalizacionHandler("es", "EC");

            // Inicializar DAOs
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();
            CuestionarioDAO cuestionarioDAO = new CuestionarioDAOMemoria();
            UsuarioDAO usuarioDAO = null;
            try {
                usuarioDAO = new UsuarioDAOMemoria();
            } catch (ExcepcionValidacion e) {
                throw new RuntimeException(e);
            }

            // Crear vista de login
            LoginView loginView = new LoginView(mi);
            loginView.setVisible(true);

            // Controlador de usuario
            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, cuestionarioDAO, mi);

            // Manejar el cierre de la ventana de login
            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                    if (usuarioAutenticado != null) {
                        // Crear vista principal
                        PrincipalView principalView = new PrincipalView(mi, usuarioAutenticado.getUsername());

                        // Crear vistas para cada funcionalidad
                        CarritoAñadirView carritoAñadirView = new CarritoAñadirView(mi);
                        CarritoListaView carritoListaView = new CarritoListaView(mi);
                        CarritoModificarView carritoModificarView = new CarritoModificarView(mi);
                        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mi);

                        ProductoAñadirView productoAñadirView = new ProductoAñadirView(mi);
                        ProductoEditarView productoEditarView = new ProductoEditarView(mi);
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mi);
                        ProductoListaView productoListaView = new ProductoListaView(mi);

                        UsuarioCrearView usuarioCrearView = new UsuarioCrearView(mi);
                        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mi);
                        UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mi);
                        UsuarioListarView usuarioListarView = new UsuarioListarView(mi);

                        // Controladores
                        ProductoController productoController = new ProductoController(productoDAO, productoAñadirView,
                                productoListaView, productoEditarView, productoEliminarView, carritoAñadirView, mi);

                        CarritoController carritoController = new CarritoController(carritoAñadirView, productoDAO, carritoDAO, usuarioAutenticado,
                                carritoListaView, carritoModificarView, carritoEliminarView, mi);

                        // Actualizar mensaje de bienvenida
                        principalView.mostrarMensaje(mi.get("principal.bienvenido") + usuarioAutenticado.getUsername());
                        principalView.setTitle(mi.get("principal.titulo") + " - " + usuarioAutenticado.getUsername());

                        // Deshabilitar menús para usuarios normales
                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        // Configurar acciones de menú
                        configurarMenu(principalView, usuarioCrearView, usuarioEliminarView, usuarioModificarView, usuarioListarView,
                                carritoAñadirView, carritoListaView, carritoModificarView, carritoEliminarView,
                                productoAñadirView, productoEditarView, productoEliminarView, productoListaView,
                                usuarioController, productoController, carritoController, mi, loginView);
                    }
                }
            });
        });
    }

    private static void configurarMenu(PrincipalView principalView,
                                       UsuarioCrearView usuarioCrearView,
                                       UsuarioEliminarView usuarioEliminarView,
                                       UsuarioModificarView usuarioModificarView,
                                       UsuarioListarView usuarioListarView,
                                       CarritoAñadirView carritoAñadirView,
                                       CarritoListaView carritoListaView,
                                       CarritoModificarView carritoModificarView,
                                       CarritoEliminarView carritoEliminarView,
                                       ProductoAñadirView productoAñadirView,
                                       ProductoEditarView productoEditarView,
                                       ProductoEliminarView productoEliminarView,
                                       ProductoListaView productoListaView,
                                       UsuarioController usuarioController,
                                       ProductoController productoController,
                                       CarritoController carritoController,
                                       MensajeInternacionalizacionHandler mi,
                                       LoginView loginView) {
        // Configurar acciones para el menú de usuarios
        principalView.getMenuItemCrearUsuario().addActionListener(e -> {
            if (!usuarioCrearView.isVisible()) {
                usuarioCrearView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioCrearView);
            }
        });
        principalView.getMenuItemEliminarUsuario().addActionListener(e -> {
            if (!usuarioEliminarView.isVisible()) {
                usuarioEliminarView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioEliminarView);
            }
        });
        principalView.getMenuItemEditarUsuario().addActionListener(e -> {
            if (!usuarioModificarView.isVisible()) {
                usuarioModificarView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioModificarView);
            }
        });
        principalView.getMenuItemListarUsuario().addActionListener(e -> {
            if (!usuarioListarView.isVisible()) {
                usuarioListarView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioListarView);
            }
        });

        // Configurar acciones para el menú de carrito
        principalView.getMenuItemCrearCarrito().addActionListener(e -> {
            if (!carritoAñadirView.isVisible()) {
                carritoAñadirView.setVisible(true);
                principalView.getjDesktopPane().add(carritoAñadirView);
            }
        });
        principalView.getMenuItemListarCarrito().addActionListener(e -> {
            if (!carritoListaView.isVisible()) {
                carritoListaView.setVisible(true);
                principalView.getjDesktopPane().add(carritoListaView);
            }
        });
        principalView.getMenuItemEditarCarrito().addActionListener(e -> {
            if (!carritoModificarView.isVisible()) {
                carritoModificarView.setVisible(true);
                principalView.getjDesktopPane().add(carritoModificarView);
            }
        });
        principalView.getMenuItemEliminarCarrito().addActionListener(e -> {
            if (!carritoEliminarView.isVisible()) {
                carritoEliminarView.setVisible(true);
                principalView.getjDesktopPane().add(carritoEliminarView);
            }
        });

        // Configurar acciones para el menú de productos
        principalView.getMenuItemCrearProducto().addActionListener(e -> {
            if (!productoAñadirView.isVisible()) {
                productoAñadirView.setVisible(true);
                principalView.getjDesktopPane().add(productoAñadirView);
            }
        });
        principalView.getMenuItemActualizarProducto().addActionListener(e -> {
            if (!productoEditarView.isVisible()) {
                productoEditarView.setVisible(true);
                principalView.getjDesktopPane().add(productoEditarView);
            }
        });
        principalView.getMenuItemEliminarProducto().addActionListener(e -> {
            if (!productoEliminarView.isVisible()) {
                productoEliminarView.setVisible(true);
                principalView.getjDesktopPane().add(productoEliminarView);
            }
        });
        principalView.getMenuItemBuscarProducto().addActionListener(e -> {
            if (!productoListaView.isVisible()) {
                productoListaView.setVisible(true);
                principalView.getjDesktopPane().add(productoListaView);
            }
        });

        // Configurar acciones de cierre de sesión y salida
        principalView.getMenuItemCerrarSesion().addActionListener(e -> {
            boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.cerrar"));
            if (confirmado) {
                principalView.dispose();
                usuarioController.setUsuarioAutenticado(null);
                loginView.actualizarTextos();
                loginView.setVisible(true);
            }
        });
        principalView.getMenuItemSalir().addActionListener(e -> {
            boolean confirmado = principalView.mostrarMensajePregunta(mi.get("principal.salir"));
            if (confirmado) {
                principalView.dispose();
                System.exit(0);
            }
        });

        // Configurar acciones para cambiar idioma
        principalView.getMenuItemEspanol().addActionListener(e -> cambiarIdioma("es", "EC", principalView, usuarioController, productoController, carritoController, mi));
        principalView.getMenuItemIngles().addActionListener(e -> cambiarIdioma("en", "US", principalView, usuarioController, productoController, carritoController, mi));
        principalView.getMenuItemFrances().addActionListener(e -> cambiarIdioma("fr", "FR", principalView, usuarioController, productoController, carritoController, mi));
        principalView.getMenuItemkichwa().addActionListener(e -> cambiarIdioma("qu", "EC", principalView, usuarioController, productoController, carritoController, mi));
    }

    private static void cambiarIdioma(String lang, String country, PrincipalView principalView,
                                      UsuarioController usuarioController,
                                      ProductoController productoController,
                                      CarritoController carritoController,
                                      MensajeInternacionalizacionHandler mi) {
        mi.setLenguaje(lang, country);
        principalView.cambiarIdioma();
        usuarioController.actualizarIdiomaEnVistas();
        productoController.actualizarIdiomaEnVistas();
        carritoController.actualizarIdiomaEnVistas();
    }
}
