package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Cuestionario;

public interface CuestionarioDAO {

    void guardar(Cuestionario cuestionario);
    Cuestionario buscarPorUsername(String username);
}