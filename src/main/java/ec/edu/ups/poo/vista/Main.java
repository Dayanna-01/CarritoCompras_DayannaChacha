package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        ProductoAnadirView productoView = new ProductoAnadirView();
        ProductoListaView productoListaView = new ProductoListaView();
        ProductoDAO productoDAO = new ProductoDAOMemoria();
        ProductoEditarView productoGestionView = new ProductoEditarView();
        ProductoEliminarView productoEliminarView = new ProductoEliminarView();

        new ProductoController(productoDAO, productoView, productoListaView, productoGestionView, productoEliminarView);
    }
}
