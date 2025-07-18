package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.CuestionarioDAO;
import ec.edu.ups.poo.modelo.Cuestionario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CuestionarioDAOArchivoBinario implements CuestionarioDAO {

    private String rutaArchivo;

    public CuestionarioDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                new ObjectOutputStream(new FileOutputStream(rutaArchivo)).close();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo binario de cuestionarios: " + e.getMessage());
            }
        }
    }

    private List<Cuestionario> cargarCuestionarios() {
        List<Cuestionario> cuestionarios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            cuestionarios = (List<Cuestionario>) ois.readObject();
        } catch (EOFException e) {
            // Archivo vacío, no hay problema
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo binario de cuestionarios: " + e.getMessage());
        }
        return cuestionarios;
    }

    private void guardarTodosCuestionarios(List<Cuestionario> cuestionarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(cuestionarios);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo binario de cuestionarios: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Cuestionario cuestionario) {
        List<Cuestionario> cuestionarios = cargarCuestionarios();
        boolean encontrado = false;
        for (int i = 0; i < cuestionarios.size(); i++) {
            if (cuestionarios.get(i).getUsername().equals(cuestionario.getUsername())) {
                cuestionarios.set(i, cuestionario);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            cuestionarios.add(cuestionario);
        }
        guardarTodosCuestionarios(cuestionarios);
    }

    @Override
    public Cuestionario buscarPorUsername(String username) {
        List<Cuestionario> cuestionarios = cargarCuestionarios();
        for (Cuestionario c : cuestionarios) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                return c;
            }
        }
        return null;
    }
}
