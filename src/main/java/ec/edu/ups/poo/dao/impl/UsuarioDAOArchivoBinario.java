package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Rol;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivoBinario implements UsuarioDAO {

    private String rutaArchivo;

    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                new ObjectOutputStream(new FileOutputStream(rutaArchivo)).close();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo binario de usuarios: " + e.getMessage());
            }
        }
    }

    private List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (EOFException e) {
            // Archivo vacío, no hay problema
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo binario de usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    private void guardarTodosUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo binario de usuarios: " + e.getMessage());
        }
    }

    @Override
    public Usuario autenticar(String username, String contraseña) {
        List<Usuario> usuarios = cargarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contraseña)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        List<Usuario> usuarios = cargarUsuarios();
        usuarios.add(usuario);
        guardarTodosUsuarios(usuarios);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        List<Usuario> usuarios = cargarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = cargarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        guardarTodosUsuarios(usuarios);
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = cargarUsuarios();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarTodosUsuarios(usuarios);
    }

    @Override
    public List<Usuario> listarTodos() {
        return cargarUsuarios();
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuarios = cargarUsuarios();
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}
