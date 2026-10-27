package grupo1.modelo;

import grupo1.estructuras.grafo.Grafo;

// Mapa de prueba de la ciudad: 9 nodos y tiempos en minutos inventados para la simulacion.
// Mas adelante se puede reemplazar por la carga desde un archivo .txt.
public class MapaCiudad {
    public static final int ALMACEN = 0;

    public static Grafo crear() {
        Grafo g = new Grafo();
        int almacen = g.agregarNodo("Almacén Central");   // 0 = ALMACEN (RN-08)
        int unmsm = g.agregarNodo("UNMSM");               // 1
        int sanMiguel = g.agregarNodo("San Miguel");      // 2
        int puebloLibre = g.agregarNodo("Pueblo Libre");  // 3
        int jesusMaria = g.agregarNodo("Jesús María");    // 4
        int lince = g.agregarNodo("Lince");               // 5
        int sanIsidro = g.agregarNodo("San Isidro");      // 6
        int miraflores = g.agregarNodo("Miraflores");     // 7
        int surco = g.agregarNodo("Surco");               // 8

        // agregarAristaDoble(a, b, minutos de a a b, minutos de b a a)
        g.agregarAristaDoble(almacen, unmsm, 12, 12);
        g.agregarAristaDoble(almacen, puebloLibre, 15, 17);
        g.agregarAristaDoble(almacen, jesusMaria, 18, 18);
        g.agregarAristaDoble(unmsm, sanMiguel, 14, 14);
        g.agregarAristaDoble(unmsm, puebloLibre, 10, 10);
        g.agregarAristaDoble(sanMiguel, puebloLibre, 9, 9);
        g.agregarAristaDoble(puebloLibre, jesusMaria, 8, 8);
        g.agregarAristaDoble(jesusMaria, lince, 7, 9);
        g.agregarAristaDoble(jesusMaria, sanIsidro, 14, 12);
        g.agregarAristaDoble(lince, sanIsidro, 6, 6);
        g.agregarAristaDoble(lince, miraflores, 16, 15);
        g.agregarAristaDoble(sanIsidro, miraflores, 11, 10);
        g.agregarAristaDoble(miraflores, surco, 13, 15);

        // tramos de un solo sentido
        g.agregarArista(almacen, lince, 28);
        g.agregarArista(sanIsidro, surco, 20);
        return g;
    }
}
