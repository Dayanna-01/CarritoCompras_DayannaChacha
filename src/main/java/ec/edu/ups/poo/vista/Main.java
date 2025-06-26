package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controlador.CarritoController;
import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.controlador.UsuarioController;
import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();

            LoginView loginView = new LoginView();
            loginView.setVisible(true);

            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                    if (usuarioAutenticado != null) {
                        PrincipalView principalView = new PrincipalView();
                        CarritoAñadirView carritoAñadirView = new CarritoAñadirView();
                        CarritoListaView carritoListaView = new CarritoListaView();

                        ProductoAñadirView productoAñadirView = new ProductoAñadirView();
                        ProductoEditarView productoEditarView = new ProductoEditarView();
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                        ProductoListaView productoListaView = new ProductoListaView();

                        ProductoController productoController = new ProductoController(
                                productoDAO,
                                productoAñadirView,
                                productoListaView,
                                productoEditarView,
                                productoEliminarView,
                                carritoAñadirView
                        );

                        CarritoController carritoController = new CarritoController(
                                carritoAñadirView,
                                productoDAO,
                                carritoDAO,
                                usuarioAutenticado,
                                carritoListaView
                        );

                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!carritoAñadirView.isVisible()) {
                                    carritoAñadirView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoAñadirView);
                                }
                            }
                        });

                        principalView.getMenuItemListarCarrito().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!carritoListaView.isVisible()) {
                                    carritoListaView.setVisible(true);
                                    principalView.getjDesktopPane().add(carritoListaView);
                                }
                            }
                        });

                        principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!productoAñadirView.isVisible()) {
                                    productoAñadirView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoAñadirView);
                                }
                            }
                        });

                        principalView.getMenuItemActualizarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!productoEditarView.isVisible()) {
                                    productoEditarView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoEditarView);
                                }
                            }
                        });

                        principalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!productoEliminarView.isVisible()) {
                                    productoEliminarView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoEliminarView);
                                }
                            }
                        });

                        principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (!productoListaView.isVisible()) {
                                    productoListaView.setVisible(true);
                                    principalView.getjDesktopPane().add(productoListaView);
                                }
                            }
                        });

                        principalView.getBtnCerrarSesion().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                boolean confirmado = principalView.mostrarMensajePregunta("¿Desea cerrar sesión?");
                                if (confirmado) {
                                    principalView.dispose();
                                    usuarioController.setUsuarioAutenticado(null);
                                    loginView.setVisible(true);
                                }
                            }
                        });
                    }
                }
            });
        });
    }
}
