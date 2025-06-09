package ec.edu.ups.poo;
import ec.edu.ups.poo.Models.CarritoCompra;
import ec.edu.ups.poo.Models.Producto;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarritoCompra carrito = new CarritoCompra();

        System.out.println("======= Carrito de Compras =======");

        while (true) {
            System.out.println("Ingrese los datos del producto:");

            System.out.print("Código: ");
            String codigo = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Precio: ");
            double precio = Double.parseDouble(scanner.nextLine());

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            Producto producto = new Producto(codigo, nombre, precio);
            carrito.agregarProducto(producto, cantidad);

            System.out.print("¿Desea agregar otro producto? si - no: ");
            String opcion = scanner.nextLine().toLowerCase();
            if (!opcion.equals("si")) {
                break;
            }
        }

        System.out.println("======= Carrito Final =======");
        carrito.mostrarCarrito();
        scanner.close();
    }
}
