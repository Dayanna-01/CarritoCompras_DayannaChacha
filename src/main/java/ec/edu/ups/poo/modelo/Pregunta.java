package ec.edu.ups.poo.modelo;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

public enum Pregunta {
    APODO_INFANCIA("pregunta.apodo_infancia"),
    CANCION_FAVORITA("pregunta.cancion_favorita"),
    CIUDAD_NACIMIENTO("pregunta.ciudad_nacimiento"),
    COLOR_FAVORITO("pregunta.color_favorito"),
    COMIDA_FAVORITA("pregunta.comida_favorita"),
    NOMBRE_HERMANO("pregunta.nombre_hermano"),
    NOMBRE_MADRE("pregunta.nombre_madre"),
    NOMBRE_PADRE("pregunta.nombre_padre"),
    NOMBRE_PRIMER_AMIGO("pregunta.nombre_primer_amigo"),
    NOMBRE_PRIMERA_ESCUELA("pregunta.nombre_primera_escuela"),
    OBJETO_PERSONAL("pregunta.objeto_personal"),
    PELICULA_FAVORITA("pregunta.pelicula_favorita"),
    PRIMERA_MASCOTA("pregunta.primera_mascota"),
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