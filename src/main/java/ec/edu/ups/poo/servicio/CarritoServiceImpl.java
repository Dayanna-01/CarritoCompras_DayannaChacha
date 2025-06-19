package ec.edu.ups.poo.servicio;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class CarritoServiceImpl   {
    private final List<ItemCarrito> items;

    public CarritoServiceImpl() {
        items = new ArrayList<>();
    }


    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }


    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }


    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }


    public List<ItemCarrito> obtenerItems() {
        return items;
    }


    public boolean estaVacio() {
        return items.isEmpty();
    }
}