package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoTexto implements ProductoDAO {

    private String rutaArchivo;

    public ProductoDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de productos: " + e.getMessage());
            }
        }
    }

    private List<Producto> cargarProductos() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) { // codigo,nombre,precio
                    try {
                        int codigo = Integer.parseInt(datos[0]);
                        String nombre = datos[1];
                        double precio = Double.parseDouble(datos[2]);
                        productos.add(new Producto(codigo, nombre, precio));
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear datos de producto en línea: " + linea + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de productos: " + e.getMessage());
        }
        return productos;
    }

    private void guardarTodosProductos(List<Producto> productos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto producto : productos) {
                bw.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getPrecio());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de productos: " + e.getMessage());
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
