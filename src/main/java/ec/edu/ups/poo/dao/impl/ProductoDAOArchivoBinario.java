package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoBinario implements ProductoDAO {

    private String rutaArchivo;

    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                new ObjectOutputStream(new FileOutputStream(rutaArchivo)).close();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo binario de productos: " + e.getMessage());
            }
        }
    }

    private List<Producto> cargarProductos() {
        List<Producto> productos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            productos = (List<Producto>) ois.readObject();
        } catch (EOFException e) {
            // Archivo vacío, no hay problema
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo binario de productos: " + e.getMessage());
        }
        return productos;
    }

    private void guardarTodosProductos(List<Producto> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo binario de productos: " + e.getMessage());
        }
    }

    @Override
    public void crear(Producto producto) {
        List<Producto> productos = cargarProductos();
        productos.add(producto);
        guardarTodosProductos(productos);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        List<Producto> productos = cargarProductos();
        for (Producto p : productos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = cargarProductos();
        List<Producto> resultados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    @Override
    public void actualizar(Producto producto) {
        List<Producto> productos = cargarProductos();
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                break;
            }
        }
        guardarTodosProductos(productos);
    }

    @Override
    public void eliminar(int codigo) {
        List<Producto> productos = cargarProductos();
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarTodosProductos(productos);
    }

    @Override
    public List<Producto> listarTodos() {
        return cargarProductos();
    }
}
