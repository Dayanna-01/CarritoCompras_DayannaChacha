package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.CuestionarioDAO;
import ec.edu.ups.poo.modelo.Cuestionario;
import ec.edu.ups.poo.modelo.Respuesta;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CuestionarioDAOArchivoTexto implements CuestionarioDAO {

    private String rutaArchivo;

    public CuestionarioDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de cuestionarios: " + e.getMessage());
            }
        }
    }

    private List<Cuestionario> cargarCuestionarios() {
        List<Cuestionario> cuestionarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|"); // username|respuestas
                if (partes.length >= 1) {
                    try {
                        String username = partes[0];
                        Cuestionario cuestionario = new Cuestionario(username);

                        if (partes.length > 1 && !partes[1].isEmpty()) {
                            String[] respuestasData = partes[1].split(";"); // id:enunciado:respuesta;id:enunciado:respuesta
                            for (String respuestaStr : respuestasData) {
                                String[] respuestaDatos = respuestaStr.split(":");
                                if (respuestaDatos.length == 3) {
                                    int id = Integer.parseInt(respuestaDatos[0]);
                                    String enunciado = respuestaDatos[1];
                                    String respuestaTexto = respuestaDatos[2];
                                    Respuesta respuesta = new Respuesta(id, enunciado);
                                    respuesta.setRespuesta(respuestaTexto);
                                    cuestionario.agregarRespuesta(respuesta);
                                }
                            }
                        }
                        cuestionarios.add(cuestionario);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error al parsear datos de cuestionario en línea: " + linea + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de cuestionarios: " + e.getMessage());
        }
        return cuestionarios;
    }

    private void guardarTodosCuestionarios(List<Cuestionario> cuestionarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Cuestionario cuestionario : cuestionarios) {
                StringJoiner sj = new StringJoiner("|");
                sj.add(cuestionario.getUsername());

                StringJoiner respuestasJoiner = new StringJoiner(";");
                for (Respuesta respuesta : cuestionario.getRespuestas()) {
                    respuestasJoiner.add(respuesta.getId() + ":" + respuesta.getEnunciado() + ":" + respuesta.getRespuesta());
                }
                sj.add(respuestasJoiner.toString());

                bw.write(sj.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de cuestionarios: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Cuestionario cuestionario) {
        List<Cuestionario> cuestionarios = cargarCuestionarios();
        // Buscar si ya existe un cuestionario para este usuario y actualizarlo
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
