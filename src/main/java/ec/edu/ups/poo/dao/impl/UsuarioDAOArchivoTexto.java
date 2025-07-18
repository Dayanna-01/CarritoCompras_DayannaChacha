package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.ExcepcionValidacion;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Rol;
import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {

    private String rutaArchivo;

    public UsuarioDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de usuarios: " + e.getMessage());
            }
        }
    }

    private List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 9) { // Verificar que haya suficientes campos
                    try {
                        String username = datos[0].trim();
                        String contrasenia = datos[1].trim();
                        Rol rol = Rol.valueOf(datos[2].trim());
                        String nombre = datos[3].trim();
                        String celular = datos[4].trim();
                        int anio = Integer.parseInt(datos[5].trim());
                        int mes = Integer.parseInt(datos[6].trim()) - 1; // Ajustar mes (0-11)
                        int dia = Integer.parseInt(datos[7].trim());
                        String email = datos[8].trim();

                        // Validación de fecha básica
                        if (mes < 0 || mes > 11 || dia < 1 || dia > 31) {
                            throw new IllegalArgumentException("Fecha inválida. Mes: " + (mes+1) + ", Día: " + dia);
                        }

                        GregorianCalendar fecha = new GregorianCalendar(anio, mes, dia);
                        Usuario usuario = new Usuario(username, contrasenia, rol, nombre, celular, fecha, email);
                        usuarios.add(usuario);
                    } catch (NumberFormatException e) {
                        System.err.println("Error en formato numérico en línea: " + linea + " - " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error en valores de datos en línea: " + linea + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Línea mal formateada (faltan datos): " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura del archivo: " + e.getMessage());
        }
        return usuarios;
    }

    private void guardarTodosUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.getUsername() + "," +
                        usuario.getContrasenia() + "," +
                        usuario.getRol().name() + "," +
                        usuario.getNombre() + "," +
                        usuario.getCelular() + "," +
                        usuario.getFecha().get(GregorianCalendar.YEAR) + "," +
                        (usuario.getFecha().get(GregorianCalendar.MONTH) + 1) + "," + // Ajustar mes al guardar
                        usuario.getFecha().get(GregorianCalendar.DAY_OF_MONTH) + "," +
                        usuario.getEmail());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    @Override
    public Usuario autenticar(String username, String contraseña) {
        List<Usuario> usuarios = cargarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) &&
                    usuario.getContrasenia().equals(contraseña)) {
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
