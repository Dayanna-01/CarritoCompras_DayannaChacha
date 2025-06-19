package ec.edu.ups.poo.vista;

import javax.swing.*;

public class PrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JDesktopPane jDesktopPane;
    private JMenuItem menuItemVerCarrito;

    public PrincipalView() {
        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();
        jDesktopPane = new JDesktopPane();
        setContentPane(jDesktopPane);
        // resto de configuración...
        setSize(900, 600); // tamaño de la ventana principal
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menú Producto
        menuProducto = new JMenu("🛍️ Producto");
        menuItemCrearProducto = new JMenuItem("➕ Crear Producto");
        menuItemActualizarProducto = new JMenuItem("✏️ Actualizar Producto");
        menuItemEliminarProducto = new JMenuItem("🗑️ Eliminar Producto");
        menuItemBuscarProducto = new JMenuItem("🔍 Buscar Producto");


        menuBar.add(menuProducto);
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito = new JMenu("🛒 Carrito");
        menuItemVerCarrito = new JMenuItem("📋 Ver Carrito");
        menuCarrito.add(menuItemVerCarrito);
        menuBar.add(menuCarrito);



        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Carrito de Compras");
        setLocationRelativeTo(null);
        setVisible(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);


    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public JMenuItem getMenuItemVerCarrito() {
        return menuItemVerCarrito;
    }

    public void setMenuItemVerCarrito(JMenuItem menuItemVerCarrito) {
        this.menuItemVerCarrito = menuItemVerCarrito;
    }


}