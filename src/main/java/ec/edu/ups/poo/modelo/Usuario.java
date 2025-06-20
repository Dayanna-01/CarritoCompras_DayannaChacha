package ec.edu.ups.poo.modelo;

public class Usuario {
    private String userName;
    private String contraseña;
    private Rol rol;

    public Usuario(String nombreDelUsuario, String contraseña, Rol rol) {
        this.userName = nombreDelUsuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreDelUsuario='" + userName + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", rol=" + rol + '}';
    }
}