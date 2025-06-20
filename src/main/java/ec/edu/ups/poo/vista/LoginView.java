package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.dao.UsuarioDao;
import ec.edu.ups.poo.dao.impl.UsuarioDAOMemoria;

import javax.swing.*;

public class LoginView extends JDialog {

    private JTextField textUsuario;
    private JPasswordField txtcontraseña;
    private JButton iniciarButton;
    private JButton btnCancelar;
    private JPanel panelPrincipal;

    private UsuarioDao usuarioDao;
    private boolean autenticado = false;

    public LoginView(JFrame parent, UsuarioDao usuarioDao) {
        super(parent, "Iniciar Sesión", true); // true = modal
        this.usuarioDao = usuarioDao;

        setContentPane(panelPrincipal);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Eventos
        iniciarButton.addActionListener(e -> iniciarSesion());
        btnCancelar.addActionListener(e -> cancelarSesion());
    }

    private void iniciarSesion() {
        String usuario = textUsuario.getText().trim();
        String contraseña = new String(txtcontraseña.getPassword()).trim();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña.");
            return;
        }

        usuarioDao.autenticar(usuario, contraseña);

        if (usuarioDao instanceof UsuarioDAOMemoria dao) {
            Usuario u = dao.getUsuarioAutenticado();
            if (u != null) {
                autenticado = true;
                dispose(); // cerrar login
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error: DAO no compatible.");
        }
    }

    private void cancelarSesion() {
        textUsuario.setText("");
        txtcontraseña.setText("");
        textUsuario.requestFocus();
    }

    public boolean isAutenticado() {
        return autenticado;
    }
}