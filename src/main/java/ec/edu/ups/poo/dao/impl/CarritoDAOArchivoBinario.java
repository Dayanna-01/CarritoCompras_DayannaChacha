package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAOArchivoBinario implements CarritoDAO {

    private String rutaArchivo;

    public CarritoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                new ObjectOutputStream(new FileOutputStream(rutaArchivo)).close();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo binario de carritos: " + e.getMessage());
            }
        }
    }

    private List<Carrito> cargarCarritos() {
        List<Carrito> carritos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            carritos = (List<Carrito>) ois.readObject();
        } catch (EOFException e) {
            // Archivo vacío, no hay problema
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo binario de carritos: " + e.getMessage());
        }
        return carritos;
    }

    private void guardarTodosCarritos(List<Carrito> carritos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo binario de carritos: " + e.getMessage());
        }
    }

    @Override
    public void crear(Carrito carrito) {
        List<Carrito> carritos = cargarCarritos();
        carritos.add(carrito);
        guardarTodosCarritos(carritos);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        List<Carrito> carritos = cargarCarritos();
        for (Carrito c : carritos) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> carritos = cargarCarritos();
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
        guardarTodosCarritos(carritos);
    }

    @Override
    public boolean eliminar(int codigo) {
        List<Carrito> carritos = cargarCarritos();
        boolean eliminado = carritos.removeIf(c -> c.getCodigo() == codigo);
        if (eliminado) {
            guardarTodosCarritos(carritos);
        }
        return eliminado;
    }

    @Override
    public List<Carrito> listarTodos() {
        return cargarCarritos();
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> carritos = cargarCarritos();
        List<Carrito> resultados = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getUsuario() != null && c.getUsuario().getUsername().equals(usuario.getUsername())) {
                resultados.add(c);
            }
        }
        return resultados;
    }
}
