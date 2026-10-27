package grupo1;

import grupo1.estructuras.TablaHash;
import grupo1.estructuras.arbol.ArbolRojoNegro;
import grupo1.estructuras.cola.MaxHeap;
import grupo1.estructuras.cola.Pila;
import grupo1.modelo.Pedido;
import grupo1.persistencia.ArchivoProductos;
import grupo1.ui.consola.MenuInventario;
import grupo1.ui.consola.MenuPedidos;
import grupo1.ui.consola.MenuSeguridad;
import grupo1.estructuras.grafo.Grafo;
import grupo1.modelo.MapaCiudad;
import grupo1.ui.consola.MenuRutas;



import java.util.ArrayList;
import java.util.Scanner;

public class LogiMarket {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SISTEMA DE GESTIÓN LOGÍSTICA - LOGIMARKET (2026) ");
        System.out.println("==================================================");

        Scanner sc = new Scanner(System.in);   // un solo Scanner 

        // ESTRUCTURAS 
        // Daniela: ArbolRojoNegro
        ArbolRojoNegro arbolProductos = new ArbolRojoNegro();
        
        // Paolo: TablaHash de usuarios y de pedidos (Implementada y compartida)
        TablaHash<String, Pedido> rastreoHash = new TablaHash<>(100); 

        // María:   MaxHeap y Pila
        MaxHeap colaPedidos = new MaxHeap();
        Pila pilaAcciones = new Pila();
        
        // TODO Franklin: Grafo
        Grafo mapaCiudad = MapaCiudad.crear();
        
        // CARGA DE ARCHIVOS
        ArrayList<String> errores = ArchivoProductos.cargar(arbolProductos);   
        for (String e : errores) {
            System.out.println("Aviso al cargar productos: " + e);
        }

        // MENUS 
        MenuSeguridad menuSeguridad = new MenuSeguridad(sc, rastreoHash);
        MenuInventario menuInventario = new MenuInventario(arbolProductos, sc);
        MenuPedidos menuPedidos = new MenuPedidos(arbolProductos, rastreoHash, colaPedidos, pilaAcciones, sc, "vendedor", 1);
        
        // TODO Franklin: MenuDespacho
        MenuRutas menuRutas = new MenuRutas(mapaCiudad, sc);
        
        // MENU PRINCIPAL
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion(sc);
            switch (opcion) {
                case 1: menuSeguridad.mostrarMenu(); break;
                case 2: menuInventario.iniciar(); break;
                case 3: menuPedidos.iniciar(); break;
                case 4: menuRutas.iniciar(); break; 
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
