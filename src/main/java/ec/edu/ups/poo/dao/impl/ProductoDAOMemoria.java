package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> listaProductos;

    public ProductoDAOMemoria() {
        listaProductos = new ArrayList<>();
        crear(new Producto(1, "Laptop Dell Inspiron", 650.99));
        crear(new Producto(2, "Smartphone Samsung Galaxy", 450.50));
        crear(new Producto(3, "Teclado Mecánico Logitech", 75.25));
        crear(new Producto(4, "Monitor LG 24 pulgadas", 180.75));
        crear(new Producto(5, "Mouse Inalámbrico Microsoft", 25.99));
        crear(new Producto(6, "Impresora HP DeskJet", 120.00));
        crear(new Producto(7, "Auriculares Bose QC35", 299.99));
        crear(new Producto(8, "Disco Duro Externo Seagate 1TB", 85.40));
        crear(new Producto(9, "Router WiFi TP-Link", 50.00));
        crear(new Producto(10, "Smartwatch Fitbit Versa", 199.95));
    }

    @Override
    public void crear(Producto producto) {
        listaProductos.add(producto);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto p : listaProductos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto p : listaProductos) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getCodigo() == producto.getCodigo()) {
                listaProductos.set(i, producto);
                break;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        listaProductos.removeIf(p -> p.getCodigo() == codigo);
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(listaProductos);
    }
}
