package grupo1.ui.consola;

import grupo1.estructuras.grafo.Arista;
import grupo1.estructuras.grafo.Dijkstra;
import grupo1.estructuras.grafo.Grafo;
import grupo1.modelo.MapaCiudad;
import grupo1.modelo.Ruta;
import java.util.Scanner;

public class MenuRutas {
    private final Grafo grafo;
    private final Scanner sc;

    public MenuRutas(Grafo grafo, Scanner sc) {
        this.grafo = grafo;
        this.sc = sc;
    }

    private void mostrarMenu() {
        System.out.println("\n===== Menu de Rutas y Despachos =====");
        System.out.println("1. Ver mapa de la ciudad");
        System.out.println("2. Solicitar despacho (hoja de ruta)");
        System.out.println("0. Regresar al menu principal");
    }

    public void iniciar() {
        int opc;
        do {
            mostrarMenu();
            opc = leerEntero("Seleccione una opcion -> ");
            switch (opc) {
                case 1: verMapa(); break;
                case 2: solicitarDespacho(); break;
                case 0: System.out.println("Volviendo al menú principal..."); break;
                default: System.out.println("Opción no válida.");
            }
        } while (opc != 0);
    }

    // Muestra cada nodo con las aristas que salen de el (la lista de adyacencia).
    private void verMapa() {
        System.out.println("\n--- Mapa de la ciudad (tiempos en minutos) ---");
        System.out.println("Nodos: " + grafo.getCantidadNodos()
                + " | Conexiones: " + grafo.getCantidadAristas());
        for (int i = 0; i < grafo.getCantidadNodos(); i++) {
            System.out.println("[" + i + "] " + grafo.getNombre(i));
            Arista a = grafo.getAdyacentes(i);
            if (a == null) {
                System.out.println("      (sin salidas)");
            }
            while (a != null) {
                System.out.println("      -> " + grafo.getNombre(a.getDestino())
                        + " (" + a.getMinutos() + " min)");
                a = a.getSiguiente();
            }
        }
    }

    // El repartidor elige el destino; el origen siempre es el Almacen Central (RN-08).
    private void solicitarDespacho() {
        System.out.println("\n--- Solicitud de despacho ---");
        System.out.println("Destinos disponibles:");
        for (int i = 0; i < grafo.getCantidadNodos(); i++) {
            if (i != MapaCiudad.ALMACEN) {
                System.out.println("  " + i + ". " + grafo.getNombre(i));
            }
        }
        int destino = leerEntero("Ingrese el numero del destino -> ");
        if (!grafo.existeNodo(destino) || destino == MapaCiudad.ALMACEN) {
            System.out.println("Destino no válido.");
            return;
        }
        Ruta ruta = Dijkstra.calcular(grafo, MapaCiudad.ALMACEN, destino);
        if (ruta == null) {
            System.out.println("No existe un camino hacia " + grafo.getNombre(destino) + ".");
            return;
        }
        imprimirHojaDeRuta(ruta);
    }

    private void imprimirHojaDeRuta(Ruta ruta) {
        int ultimo = ruta.getCantidadNodos() - 1;
        System.out.println("\n========== HOJA DE RUTA ==========");
        System.out.println("Origen : " + grafo.getNombre(ruta.getNodo(0)));
        System.out.println("Destino: " + grafo.getNombre(ruta.getNodo(ultimo)));
        System.out.println("----------------------------------");
        for (int i = 1; i < ruta.getCantidadNodos(); i++) {
            System.out.println("Paso " + i + ": " + grafo.getNombre(ruta.getNodo(i - 1))
                    + " -> " + grafo.getNombre(ruta.getNodo(i))
                    + " (" + ruta.getTiempoTramo(i) + " min)");
        }
        System.out.println("----------------------------------");
        StringBuilder camino = new StringBuilder();
        for (int i = 0; i < ruta.getCantidadNodos(); i++) {
            if (i > 0) {
                camino.append(" -> ");
            }
            camino.append(grafo.getNombre(ruta.getNodo(i)));
        }
        System.out.println("Recorrido: " + camino);
        System.out.println("Tiempo total estimado: " + ruta.getTiempoTotal() + " minutos");
        System.out.println("==================================");
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
