package grupo1.ui.consola;

import grupo1.estructuras.TablaHash;
import grupo1.modelo.Pedido;

import java.util.Scanner;

public class MenuSeguridad {
    private Scanner sc;
    private TablaHash<String, Pedido> rastreoHash;

    // Constructor que recibe el Scanner y la Tabla Hash compartida desde el Main
    public MenuSeguridad(Scanner sc, TablaHash<String, Pedido> rastreoHash) {
        this.sc = sc;
        this.rastreoHash = rastreoHash;
    }

    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\n--- MODULO DE SEGURIDAD Y RASTREO ---");
            System.out.println("1. Iniciar Sesion (Login por Roles)");
            System.out.println("2. Rastreo Rapido de Pedido (por Codigo)");
            System.out.println("3. Regresar al Menu Principal");
            System.out.print("Seleccione una opcion: ");

            String entrada = sc.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
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
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 3);
    }

    private void realizarLogin() {
        System.out.println("\n--- INICIO DE SESION ---");
        System.out.print("Ingrese usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = sc.nextLine();
        
        System.out.println("Verificando credenciales para el usuario: " + usuario + "...");
    }

    private void rastrearPedido() {
        System.out.println("\n--- RASTREO RAPIDO DE PEDIDO (O(1)) ---");
        System.out.print("Ingrese el codigo unico del pedido (ej. PED-0045): ");
        String codigo = sc.nextLine();

        Pedido pedidoEncontrado = rastreoHash.buscar(codigo);
        
        if (pedidoEncontrado != null) {
            System.out.println("Pedido localizado con exito.");
            System.out.println(pedidoEncontrado);
        } else {
            System.out.println("No se encontro ningun registro asociado al codigo: " + codigo);
        }
    }
}
