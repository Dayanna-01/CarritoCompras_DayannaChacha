package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import java.util.List;

public interface UsuarioDao {
    void autenticar(String userName, String contraseña);

    void crear (Usuario usuario);

    Usuario buscarporUserName (String userName);

    void  eliminar (String userName);

    void actualizar (Usuario usuario);

    List<Usuario> listarTodos();

    List<Usuario> listarAdministradores();

    List<Usuario> listarUsuarios();

}
