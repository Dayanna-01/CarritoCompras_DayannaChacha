package ec.edu.ups.poo.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarritoCompra {
    private List<ItemCarrito> items;

    public CarritoCompra() {
        this.items = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (Objects.equals(item.getProducto().getCodigo(), producto.getCodigo())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public boolean eliminarProducto(String codigoProducto) {
        return items.removeIf(item -> Objects.equals(item.getProducto().getCodigo(), codigoProducto));
    }

    public boolean modificarCantidad(String codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : items) {
            if (Objects.equals(item.getProducto().getCodigo(), codigoProducto)) {
                if (nuevaCantidad <= 0) {
                    items.remove(item);
                } else {
                    item.setCantidad(nuevaCantidad);
                }
                return true;
            }
        }
        return false; // Producto no encontrado
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public List<ItemCarrito> getItems() {
        return new ArrayList<>(items);
    }

    public void mostrarCarrito() {
        for (ItemCarrito item : items) {
            System.out.println(item);
        }
        System.out.println("---------------------");
        System.out.printf("TOTAL: $%.1f\n", calcularTotal());
    }

}