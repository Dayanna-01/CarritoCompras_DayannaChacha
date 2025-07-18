package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Rol; // Necesario para reconstruir Usuario
import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringJoiner;

public class CarritoDAOArchivoTexto implements CarritoDAO {

    private String rutaArchivo;
    // Necesitamos un DAO de Producto y Usuario para reconstruir los objetos
    private ProductoDAOArchivoTexto productoDAO;
    private UsuarioDAOArchivoTexto usuarioDAO;

    public CarritoDAOArchivoTexto(String rutaArchivo, ProductoDAOArchivoTexto productoDAO, UsuarioDAOArchivoTexto usuarioDAO) {
        this.rutaArchivo = rutaArchivo;
        this.productoDAO = productoDAO;
        this.usuarioDAO = usuarioDAO;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de carritos: " + e.getMessage());
            }
        }
    }

    private List<Carrito> cargarCarritos() {
        List<Carrito> carritos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|"); // Usamos | como separador principal
                if (partes.length >= 3) { // codigo,fecha,username_usuario|items
                    try {
                        int codigo = Integer.parseInt(partes[0]);
                        // Reconstruir fecha
                        String[] fechaDatos = partes[1].split("-"); // anio-mes-dia
                        int anio = Integer.parseInt(fechaDatos[0]);
                        int mes = Integer.parseInt(fechaDatos[1]);
                        int dia = Integer.parseInt(fechaDatos[2]);
                        GregorianCalendar fechaCreacion = new GregorianCalendar(anio, mes, dia);

                        // Reconstruir usuario
                        String usernameUsuario = partes[2];
                        Usuario usuario = usuarioDAO.buscarPorUsername(usernameUsuario);
                        if (usuario == null) {
                            System.err.println("Usuario no encontrado para el carrito con código " + codigo + ": " + usernameUsuario);
                            continue; // Saltar este carrito si el usuario no existe
                        }

                        Carrito carrito = new Carrito();
                        carrito.setCodigo(codigo);
                        carrito.setFechaCreacion(fechaCreacion);
                        carrito.setUsuario(usuario);

                        // Reconstruir items
                        if (partes.length > 3 && !partes[3].isEmpty()) {
                            String[] itemsData = partes[3].split(";"); // item1;item2
                            for (String itemStr : itemsData) {
                                String[] itemDatos = itemStr.split(":"); // codigoProducto:cantidad
                                if (itemDatos.length == 2) {
                                    int codigoProducto = Integer.parseInt(itemDatos[0]);
                                    int cantidad = Integer.parseInt(itemDatos[1]);
                                    Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
                                    if (producto != null) {
                                        carrito.agregarProducto(producto, cantidad);
                                    } else {
                                        System.err.println("Producto no encontrado para el item en carrito " + codigo + ": " + codigoProducto);
                                    }
                                }
                            }
                        }
                        carritos.add(carrito);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error al parsear datos de carrito en línea: " + linea + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de carritos: " + e.getMessage());
        }
        return carritos;
    }

    private void guardarTodosCarritos(List<Carrito> carritos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Carrito carrito : carritos) {
                StringJoiner sj = new StringJoiner("|");
                sj.add(String.valueOf(carrito.getCodigo()));
                sj.add(carrito.getFechaCreacion().get(GregorianCalendar.YEAR) + "-" +
                        carrito.getFechaCreacion().get(GregorianCalendar.MONTH) + "-" +
                        carrito.getFechaCreacion().get(GregorianCalendar.DAY_OF_MONTH));
                sj.add(carrito.getUsuario().getUsername());

                StringJoiner itemsJoiner = new StringJoiner(";");
                for (ItemCarrito item : carrito.obtenerItems()) {
                    itemsJoiner.add(item.getProducto().getCodigo() + ":" + item.getCantidad());
                }
                sj.add(itemsJoiner.toString());

                bw.write(sj.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de carritos: " + e.getMessage());
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
            if (c.getUsuario().getUsername().equals(usuario.getUsername())) {
                resultados.add(c);
            }
        }
        return resultados;
    }
}
