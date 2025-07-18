package ec.edu.ups.poo.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Respuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String enunciado;
    private String respuesta;

    public Respuesta(int id, String enunciado) {
        this.id = id;
        this.enunciado = enunciado;
        this.respuesta = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}