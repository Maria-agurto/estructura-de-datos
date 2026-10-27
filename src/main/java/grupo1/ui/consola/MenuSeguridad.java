package grupo1.ui.consola;

import grupo1.estructuras.TablaHash;
import grupo1.modelo.Pedido;

import java.util.Scanner;

public class MenuSeguridad {
    private Scanner sc;
    private TablaHash<String, Pedido> rastreoHash; // Tabla compartida con María y Franklin

    // Constructor que recibe el Scanner y la Tabla Hash compartida desde el Main
    public MenuSeguridad(Scanner sc, TablaHash<String, Pedido> rastreoHash) {
        this.sc = sc;
        this.rastreoHash = rastreoHash;
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MÓDULO DE SEGURIDAD Y RASTREO ---");
            System.out.println("1. Iniciar Sesión (Login por Roles)");
            System.out.println("2. Rastreo Rápido de Pedido (por Código)");
            System.out.println("3. Regresar al Menú Principal");
            System.out.print("Seleccione una opción: ");

            String entrada = sc.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error: Ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    realizarLogin();
                    break;
                case 2:
                    rastrearPedido();
                    break;
                case 3:
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }

    private void realizarLogin() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = sc.nextLine();
        
        // Aquí validas con tu tabla hash de usuarios si la manejas de forma independiente
        System.out.println("Verificando credenciales para el usuario: " + usuario + "...");
    }

    private void rastrearPedido() {
        System.out.println("\n--- RASTREO RÁPIDO DE PEDIDO (O(1)) ---");
        System.out.print("Ingrese el código único del pedido (ej. PED-0045): ");
        String codigo = sc.nextLine();

        // Búsqueda directa en la tabla hash compartida con María y Franklin
        Pedido pedidoEncontrado = rastreoHash.buscar(codigo);
        
        if (pedidoEncontrado != null) {
            System.out.println("¡Pedido localizado con éxito!");
            System.out.println(pedidoEncontrado); // Asegúrate de que la clase Pedido tenga implementado un buen toString()
        } else {
            System.out.println("❌ No se encontró ningún registro asociado al código: " + codigo);
        }
    }
}
