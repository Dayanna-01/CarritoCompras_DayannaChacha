package ec.edu.ups.poo.Service;
import ec.edu.ups.poo.Models.ItemCarrito;
import ec.edu.ups.poo.Models.Producto;
import java.util.List;

public interface CarritoService {

    void agregarProducto(Producto producto, int cantidad);

    void eliminarProducto(int codigoProducto);

    void vaciarCarrito();

    double calcularTotal();

    List<ItemCarrito> obtenerItems();

    boolean estaVacio();


}

