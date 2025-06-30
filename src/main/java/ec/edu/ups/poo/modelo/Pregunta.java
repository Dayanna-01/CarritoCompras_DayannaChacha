package ec.edu.ups.poo.modelo;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

public enum Pregunta {
    CIUDAD_NACIMIENTO("pregunta.ciudad_nacimiento"),
    NOMBRE_PRIMER_AMIGO("pregunta.nombre_primer_amigo"),
    PELICULA_FAVORITA("pregunta.pelicula_favorita"),
    NOMBRE_MADRE("pregunta.nombre_madre"),
    OBJETO_PERSONAL("pregunta.objeto_personal"),
    COLOR_FAVORITO("pregunta.color_favorito"),
    CANCION_FAVORITA("pregunta.cancion_favorita"),
    NOMBRE_PADRE("pregunta.nombre_padre"),
    APODO_INFANCIA("pregunta.apodo_infancia"),
    PRIMERA_MASCOTA("pregunta.primera_mascota"),
    NOMBRE_HERMANO("pregunta.nombre_hermano"),
    COMIDA_FAVORITA("pregunta.comida_favorita"),
    NOMBRE_PRIMERA_ESCUELA("pregunta.nombre_primera_escuela"),
    PROFESOR_FAVORITO("pregunta.profesor_favorito");


    private String enunciado;
    private MensajeInternacionalizacionHandler mi;

    Pregunta(String enunciado) {
        this.enunciado = enunciado;
    }
    Pregunta() {}

    public void setMensajeIdioma(MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
    }

    public String getEnunciado() {
        if (mi != null) {
            return mi.get(enunciado);
        } else {
            return enunciado;
        }
    }

}