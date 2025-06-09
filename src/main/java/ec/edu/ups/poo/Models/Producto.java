package ec.edu.ups.poo.Models;

public class Producto {
    private final String codigo;
    private final String nombre;
    private final double precio;

    public Producto(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() {
        return "Producto: " + nombre + "\n" +
                "Código: " + codigo + "\n" +
                "Precio: " + precio;
    }
}