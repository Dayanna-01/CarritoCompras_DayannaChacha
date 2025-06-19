package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;

import java.util.HashMap;
import java.util.Map;

public class ProductoDAOMemoria implements ProductoDAO {

    private Map<Integer, Producto> productos = new HashMap<>();

    @Override
    public void crear(Producto producto) {
        productos.put(producto.getCodigo(), producto);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productos.get(codigo);
    }

    @Override
    public void actualizar(Producto producto) {
        productos.put(producto.getCodigo(), producto);
    }

    @Override
    public void eliminar(int codigo) {
        productos.remove(codigo);
    }

    // Métodos no implementados aquí (puedes dejar vacío o implementar según necesites)
    @Override public java.util.List<Producto> buscarPorNombre(String nombre) { return null; }
    @Override public java.util.List<Producto> listarTodos() { return null; }
}
