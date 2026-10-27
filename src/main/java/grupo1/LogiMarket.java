package grupo1;

import grupo1.estructuras.TablaHash;
import grupo1.estructuras.arbol.ArbolRojoNegro;
import grupo1.modelo.Pedido;
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
        
        // TODO Paolo: TablaHash de usuarios y de pedidos (Implementada y compartida)
        TablaHash<String, Pedido> rastreoHash = new TablaHash<>(100); 

        // TODO María:   MaxHeap y Pila
        MaxHeap colaPedidos = new MaxHeap();
        Pila pilaAcciones = new Pila();
        
        // TODO Franklin: Grafo

        // CARGA DE ARCHIVOS
        ArrayList<String> errores = ArchivoProductos.cargar(arbolProductos);   
        for (String e : errores) {
            System.out.println("Aviso al cargar productos: " + e);
        }

        // MENUS (Actualizado para inyectar el Scanner y la Tabla Hash en MenuSeguridad)
        MenuSeguridad menuSeguridad = new MenuSeguridad(sc, rastreoHash);
        MenuInventario menuInventario = new MenuInventario(arbolProductos, sc);
        // TODO María:   MenuPedidos
        MenuPedidos menuPedidos = new MenuPedidos(arbolProductos, rastreoHash, colaPedidos, pilaAcciones, sc, "vendedor", 1);
        
        // TODO Franklin: MenuDespacho

        // MENU PRINCIPAL
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion(sc);
            switch (opcion) {
                case 1: menuSeguridad.mostrarMenu(); break;
                case 2: menuInventario.iniciar(); break;
                case 3: menuPedidos.iniciar(); break;
                case 4:  break; // TODO Franklin
                case 0: System.out.println("Hasta luego."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        
        sc.close();
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
