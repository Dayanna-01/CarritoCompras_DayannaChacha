package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAOMemoria implements ProductoDAO {

    private final List<Producto> productos = new ArrayList<>();

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(producto);
            }
        }
        return resultado;
    }

    @Override
    public void actualizar(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == productoActualizado.getCodigo()) {
                productos.set(i, productoActualizado);
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(producto -> producto.getCodigo() == codigo);
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}
