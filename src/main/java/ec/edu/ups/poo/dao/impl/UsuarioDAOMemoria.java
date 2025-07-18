package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.CuestionarioDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.ExcepcionValidacion;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import java.util.*;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;
    private CuestionarioDAO cuestionarioDAO;

    public UsuarioDAOMemoria() throws ExcepcionValidacion {
        this.usuarios = new ArrayList<>();
        this.cuestionarioDAO = cuestionarioDAO;

        // Usuarios por defecto con todos los campos
        Usuario admin = new Usuario(
                "admin",
                "12345",
                Rol.ADMINISTRADOR,
                "Administrador General",
                "0987654321",
                new GregorianCalendar(1975, Calendar.JANUARY, 6),
                "admin@gmail.com"
        );

        Usuario user = new Usuario(
                "user",
                "12345",
                Rol.USUARIO,
                "Usuario de Prueba",
                "0982654738",
                new GregorianCalendar(1999, Calendar.JUNE, 21),
                "user@gmail.com"
        );
        crear(admin);
        crear(user);
    }


    @Override
    public Usuario autenticar(String username, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contraseña)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}