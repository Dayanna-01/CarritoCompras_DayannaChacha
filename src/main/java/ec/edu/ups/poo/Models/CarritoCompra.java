package ec.edu.ups.poo.Models;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompra {
    private List<ItemCarrito> items;

    public CarritoCompra() {
        this.items = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo().equals(producto.getCodigo())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void mostrarCarrito() {
        for (ItemCarrito item : items) {
            System.out.println(item);
        }
        System.out.println("TOTAL: $" + calcularTotal());
    }
}
