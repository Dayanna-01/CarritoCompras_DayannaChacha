package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CuestionarioDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Cuestionario;
import ec.edu.ups.poo.modelo.Respuesta;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.CuestionarioRecuperarView;
import ec.edu.ups.poo.vista.CuestionarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CuestionarioController {

    private final CuestionarioView cuestionarioView;
    private final CuestionarioRecuperarView recuperarView;
    private final CuestionarioDAO cuestionarioDAO;
    private Cuestionario cuestionario;
    private List<Respuesta> Aleatorio;
    private List<Respuesta> Correcto;
    private final MensajeInternacionalizacionHandler mi;
    private UsuarioDAO usuarioDAO;
    private boolean usuarioYaRegistrado;
    private Usuario usuario;


    public CuestionarioController(CuestionarioView vista, CuestionarioDAO dao,
                                  UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mi) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = dao;
        this.usuarioDAO = usuarioDAO;
        this.recuperarView = null;
        this.cuestionario = null;

        cargarComboPreguntas();
        configurarEventosCuestionario();
    }

    public CuestionarioController(CuestionarioRecuperarView recuperarView, CuestionarioDAO dao,
                                  String username, String contrasenia, MensajeInternacionalizacionHandler mi){//Constructor para recuperar contraseña de usuario
        this.mi = mi;
        this.cuestionarioDAO = dao;
        this.cuestionarioView = null;
        this.cuestionario = cuestionarioDAO.buscarPorUsername(username);
        this.recuperarView = recuperarView;
        this.Correcto = new ArrayList<>();

        if (cuestionario == null) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.noPreguntas"));
            recuperarView.dispose();
            return;
        }

        this.Aleatorio = cuestionario.getRespuestas();

        for (int i = 0; i < Aleatorio.size(); i++) {
            String etiqueta = mi.get("cuestionario.pregunta");
            recuperarView.getCbxPreguntas().addItem(etiqueta + " " + (i + 1));
        }

        if (!Aleatorio.isEmpty()) {
            recuperarView.getLblPregunta().setText(Aleatorio.get(0).getEnunciado());
        }

        configurarEventosRecuperar(contrasenia);

    }

    public CuestionarioController(CuestionarioView vista, CuestionarioDAO cuestionarioDAO,
                                  UsuarioDAO usuarioDAO, Usuario usuario,
                                  MensajeInternacionalizacionHandler mi, boolean usuarioYaRegistrado) {
        this.mi = mi;
        this.cuestionarioView = vista;
        this.cuestionarioDAO = cuestionarioDAO;
        this.usuarioDAO = usuarioDAO;
        this.recuperarView = null;
        this.usuarioYaRegistrado = usuarioYaRegistrado;
        this.usuario = usuario;

        if (usuarioYaRegistrado) {
            this.cuestionario = cuestionarioDAO.buscarPorUsername(usuario.getUsername());
            if (this.cuestionario == null) {
                this.cuestionario = new Cuestionario(usuario.getUsername());
            }
        } else {
            this.cuestionario = new Cuestionario(usuario.getUsername());
        }

        this.cuestionario.aplicarIdioma(mi);

        setearCamposVista(usuario);
        configurarEventosCuestionario();
    }




    private void configurarEventosCuestionario() {
        cuestionarioView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasCuestionario();
            }
        });

        cuestionarioView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        cuestionarioView.getBtnFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizar();
            }
        });
        cuestionarioView.getBtnIniciarCuestionario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCuestionario();
            }
        });
    }

    private void configurarEventosRecuperar(String contrasenia){
        recuperarView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preguntasRecuperar();
            }
        });

        recuperarView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRespuestasRecuperar();
            }
        });

        recuperarView.getBtnFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarRecuperar(contrasenia);
            }
        });
    }

    private void preguntasRecuperar(){
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < Aleatorio.size()) {
            Respuesta r = Aleatorio.get(index);
            recuperarView.getLblPregunta().setText(r.getEnunciado());

            if (Correcto.contains(r)) {
                recuperarView.getTxtRespuesta().setText(r.getRespuesta());
            } else {
                recuperarView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardarRespuestasRecuperar() {
        int index = recuperarView.getCbxPreguntas().getSelectedIndex();
        if (index < 0 || index >= Aleatorio.size()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.preguntaInvalida"));
            return;
        }

        Respuesta r = Aleatorio.get(index);
        String respuestaUsuario = recuperarView.getTxtRespuesta().getText().trim();

        if (respuestaUsuario.isEmpty()) {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.respuestaVacia"));
            return;
        }

        if (respuestaUsuario.equalsIgnoreCase(r.getRespuesta())) {
            if (!Correcto.contains(r)) {
                Correcto.add(r);
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.correcta"));
                r.setRespuesta(respuestaUsuario);
            } else {
                recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.yaRespondida"));
            }
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.incorrecta"));
        }
    }

    private void finalizarRecuperar(String contrasenia) {
        if (Correcto.size() >= 3) {
            recuperarView.mostrarMensaje(String.format(mi.get("cuestionario.recuperar.recuperada"), contrasenia));
            recuperarView.dispose();
        } else {
            recuperarView.mostrarMensaje(mi.get("cuestionario.recuperar.minimo"));
        }
    }

    private void preguntasCuestionario(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0) {
            Respuesta r = Aleatorio.get(index);
            cuestionarioView.getLblPregunta().setText(r.getEnunciado());

            Respuesta respondido = cuestionario.buscarRespuestaPorId(r.getId());
            if (respondido != null) {
                cuestionarioView.getTxtRespuesta().setText(respondido.getRespuesta());
            } else {
                cuestionarioView.getTxtRespuesta().setText("");
            }
        }
    }

    private void guardar(){
        int index = cuestionarioView.getCbxPreguntas().getSelectedIndex();
        if (index < 0) return;

        String texto = cuestionarioView.getTxtRespuesta().getText().trim();
        if (texto.isEmpty()) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.vacia"));
            return;
        }

        Respuesta seleccionada = Aleatorio.get(index);
        seleccionada.setRespuesta(texto);

        if (cuestionario.buscarRespuestaPorId(seleccionada.getId()) == null) {
            cuestionario.agregarRespuesta(seleccionada);
        }

        cuestionarioView.mostrarMensaje(mi.get("cuestionario.guardar.ok"));
    }

    private void finalizar() {
        if (Aleatorio == null || Aleatorio.isEmpty()) {
            cuestionarioView.mostrarMensaje("Error: No se han cargado preguntas.");
            return;
        }

        if (cuestionario == null) {
            // Esta parte ya no se usa más si el Cuestionario siempre se crea en el constructor
            return;
        }

        if (cuestionario.getRespuestas().size() < 3) {
            cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.minimo"));
            return;
        }

        if (!usuarioYaRegistrado) {
            // Solo si el usuario NO está registrado, crea el usuario

            String username = cuestionarioView.getTxtUsername().getText().trim();
            String contrasenia = cuestionarioView.getTxtContrasenia().getText().trim();
            String nombre = cuestionarioView.getTxtNombre().getText().trim();
            String celular = cuestionarioView.getTxtCelular().getText().trim();
            String correo = cuestionarioView.getTxtCorreo().getText().trim();
            int dia = (int) cuestionarioView.getSpnDia().getValue();
            int mes = (int) cuestionarioView.getSpnMes().getValue();
            int anio = (int) cuestionarioView.getSpnAnio().getValue();

            if (username.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
                cuestionarioView.mostrarMensaje("Los campos estan vacíos");
                return;
            }

            if (!celular.matches("\\d{7,15}")) {
                cuestionarioView.mostrarMensaje("Número de celular inválido.");
                return;
            }

            if (!correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
                cuestionarioView.mostrarMensaje("Correo electrónico inválido.");
                return;
            }

            if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || anio < 1) {
                cuestionarioView.mostrarMensaje("Fecha de nacimiento inválida.");
                return;
            }

            if (usuarioDAO.buscarPorUsername(username) != null) {
                cuestionarioView.mostrarMensaje(mi.get("login.mensaje.error_usuario_existente"));
                return;
            }

            GregorianCalendar fechaNacimiento = new GregorianCalendar(anio, mes - 1, dia);
            Usuario nuevoUsuario = new Usuario(username, contrasenia, Rol.USUARIO, nombre, celular, fechaNacimiento, correo);
            usuarioDAO.crear(nuevoUsuario);
        }

        // Guardar el cuestionario, ya sea usuario nuevo o ya registrado
        cuestionarioDAO.guardar(cuestionario);

        cuestionarioView.mostrarMensaje(mi.get("cuestionario.finalizar.ok"));
        cuestionarioView.dispose();
    }



    private void iniciarCuestionario(){
        String username = cuestionarioView.getTxtUsername().getText().trim();
        if (usuarioDAO.buscarPorUsername(username) != null) {
            cuestionarioView.mostrarMensaje(mi.get("login.mensaje.error_usuario_existente"));
            return;
        }
        cuestionario = new Cuestionario(username);
        cuestionario.aplicarIdioma(mi);
        cargarComboPreguntas();
        cuestionarioView.habilitarPreguntas(true);
    }

    private void cargarComboPreguntas() {
        if (Aleatorio != null && !Aleatorio.isEmpty()) return;

        Aleatorio = new ArrayList<>();

        Cuestionario temporal = new Cuestionario("");
        temporal.aplicarIdioma(mi);
        List<Respuesta> todasLasPreguntas = temporal.preguntasPorDefecto();

        boolean[] usadas = new boolean[todasLasPreguntas.size()];
        int cantidadDeseada = 6;
        int cantidadActual = 0;
        Random random = new Random();

        while (cantidadActual < cantidadDeseada) {
            int indice = random.nextInt(todasLasPreguntas.size());
            if (!usadas[indice]) {
                Aleatorio.add(todasLasPreguntas.get(indice));
                usadas[indice] = true;
                cantidadActual++;
            }
        }

        cuestionarioView.getCbxPreguntas().removeAllItems();
        for (Respuesta r : Aleatorio) {
            cuestionarioView.getCbxPreguntas().addItem(r.getEnunciado());
        }
    }

    private void setearCamposVista(Usuario usuario){
        cuestionarioView.getTxtUsername().setText(usuario.getUsername());
        cuestionarioView.getTxtContrasenia().setText(usuario.getContrasenia());
        cuestionarioView.getTxtNombre().setText(usuario.getNombre());
        cuestionarioView.getTxtCelular().setText(usuario.getCelular());
        cuestionarioView.getTxtCorreo().setText(usuario.getEmail());

        GregorianCalendar fecha = usuario.getFecha();
        cuestionarioView.getSpnDia().setValue(fecha.get(Calendar.DAY_OF_MONTH));
        cuestionarioView.getSpnMes().setValue(fecha.get(Calendar.MONTH) + 1); // Recuerda que enero = 0
        cuestionarioView.getSpnAnio().setValue(fecha.get(Calendar.YEAR));

        cuestionarioView.getTxtUsername().setEnabled(false);
        cuestionarioView.getTxtContrasenia().setEnabled(false);
        cuestionarioView.getTxtNombre().setEnabled(false);
        cuestionarioView.getTxtCelular().setEnabled(false);
        cuestionarioView.getSpnDia().setEnabled(false);
        cuestionarioView.getSpnMes().setEnabled(false);
        cuestionarioView.getSpnAnio().setEnabled(false);
        cuestionarioView.getTxtCorreo().setEnabled(false);

        cuestionarioView.getBtnIniciarCuestionario().setEnabled(false);

        cargarComboPreguntas();
        cuestionarioView.habilitarPreguntas(true);
    }
}