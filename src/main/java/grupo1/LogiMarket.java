package grupo1;

import grupo1.estructuras.arbol.ArbolRojoNegro;
import grupo1.persistencia.ArchivoProductos;
import grupo1.ui.consola.MenuInventario;
import grupo1.ui.consola.MenuSeguridad;

import java.util.ArrayList;
import java.util.Scanner;

public class LogiMarket {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SISTEMA DE GESTIÓN LOGÍSTICA - LOGIMARKET (2026) ");
        System.out.println("==================================================");

        Scanner sc = new Scanner(System.in);   // un solo Scanner 

        // ESTRUCTURAS 
        ArbolRojoNegro arbolProductos = new ArbolRojoNegro();
        // TODO Paolo:    TablaHash de usuarios y de pedidos
        // TODO María:    MaxHeap y Pila
        // TODO Franklin: Grafo

        // CARGA DE ARCHIVOS
        ArrayList<String> errores = ArchivoProductos.cargar(arbolProductos);   
        for (String e : errores) {
            System.out.println("Aviso al cargar productos: " + e);
        }

        // MENUS
        MenuSeguridad menuSeguridad = new MenuSeguridad();
        MenuInventario menuInventario = new MenuInventario(arbolProductos, sc);
        // TODO María:    MenuPedidos
        // TODO Franklin: MenuDespacho

        //MENU PRINCIPAL
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion(sc);
            switch (opcion) {
                case 1: menuSeguridad.mostrarMenu(); break;
                case 2: menuInventario.iniciar(); break;
                case 3:  break;  // TODO María
                case 4:  break; // TODO Franklin
                case 0: System.out.println("Hasta luego."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Seguridad");
        System.out.println("2. Inventario");
        System.out.println("3. Pedidos");
        System.out.println("4. Despacho");
        System.out.println("0. Salir");
    }

    private static int leerOpcion(Scanner sc) {
        System.out.print("Seleccione una opción -> ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}