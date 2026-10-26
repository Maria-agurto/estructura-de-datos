package grupo1.ui.consola;

import grupo1.estructura.hash.TablaHash;
import java.util.Scanner;

public class MenuSeguridad {
    private TablaHash<String, String> usuariosHash; // Clave: Usuario -> Valor: Rol
    private TablaHash<String, String> rastreoHash;  // Clave: Código Pedido -> Valor: Detalles

    public MenuSeguridad() {
        usuariosHash = new TablaHash<>();
        rastreoHash = new TablaHash<>();
        precargarDatosPrueba();
    }

    private void precargarDatosPrueba() {
        // Carga de usuarios permitidos según reglas de negocio
        usuariosHash.insertar("admin", "Administrador");
        usuariosHash.insertar("vendedor1", "Vendedor");
        usuariosHash.insertar("repartidor1", "Repartidor");

        // Carga de pedidos iniciales para el rastreo instantáneo O(1)
        rastreoHash.insertar("PED-001", "Destino: San Miguel | Estado: En Almacén (Listo para despacho)");
        rastreoHash.insertar("PED-002", "Destino: Pueblo Libre | Estado: En Ruta de Entrega");
        rastreoHash.insertar("PED-003", "Destino: Breña | Estado: Entregado con éxito");
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n========================================");
            System.out.println("    LOGIMARKET - MÓDULO DE SEGURIDAD    ");
            System.out.println("========================================");
            System.out.println("1. Iniciar Sesión (Login por Roles)");
            System.out.println("2. Rastreo Rápido de Pedido O(1)");
            System.out.println("3. Salir / Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada

                switch (opcion) {
                    case 1:
                        ejecutarLogin(scanner);
                        break;
                    case 2:
                        ejecutarRastreo(scanner);
                        break;
                    case 3:
                        System.out.println("Saliendo del Módulo de Seguridad...");
                        break;
                    default:
                        System.out.println("[Error] Opción no válida. Intente de nuevo.");
                }
            } else {
                System.out.println("[Error] Por favor, ingrese un número válido.");
                scanner.next(); // Limpiar entrada errónea
            }
        } while (opcion != 3);
    }

    private void ejecutarLogin(Scanner scanner) {
        System.out.println("\n--- AUTENTICACIÓN DE USUARIO ---");
        System.out.print("Ingrese usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine(); // Validación conceptual en la Hash

        // Búsqueda directa en tiempo O(1) usando tu Tabla Hash
        String rolAsignado = usuariosHash.buscar(usuario);

        if (rolAsignado != null) {
            System.out.println("\n[ÉXITO] ¡Acceso concedido!");
            System.out.println("Usuario: " + usuario + " | Rol Operativo: " + rolAsignado);

            // Aquí posteriormente podrás derivar al submenú del rol correspondiente
        } else {
            System.out.println("\n[DENEGADO] Credenciales incorrectas o usuario no registrado.");
        }
    }

    private void ejecutarRastreo(Scanner scanner) {
        System.out.println("\n--- RASTREO RÁPIDO DE PEDIDOS ---");
        System.out.print("Ingrese el código único del comprobante (ej. PED-001): ");
        String codigoPedido = scanner.nextLine();

        // Búsqueda instantánea O(1) en la tabla hash de rastreo
        String infoPedido = rastreoHash.buscar(codigoPedido);

        if (infoPedido != null) {
            System.out.println("\n[RESULTADO EN TIEMPO O(1)]");
            System.out.println("Código: " + codigoPedido);
            System.out.println("Detalles: " + infoPedido);
        } else {
            System.out.println("\n[AVISO] No se encontró ningún registro para el código: " + codigoPedido);
        }
    }
}