package grupo1.modelo;

// Resultado de Dijkstra: nodos del camino en orden (origen primero) y el tiempo
// acumulado en minutos al llegar a cada uno (el del origen es 0).
public class Ruta {
    private final int[] nodos;
    private final int[] acumulado;

    public Ruta(int[] nodos, int[] acumulado) {
        if (nodos == null || acumulado == null || nodos.length == 0
                || nodos.length != acumulado.length) {
            throw new IllegalArgumentException("Ruta invalida");
        }
        this.nodos = nodos;
        this.acumulado = acumulado;
    }

    public int getCantidadNodos() { return nodos.length; }
    public int getNodo(int posicion) { return nodos[posicion]; }
    public int getTiempoTotal() { return acumulado[acumulado.length - 1]; }

    // Minutos que toma el tramo que llega al nodo en 'posicion' (posicion >= 1).
    public int getTiempoTramo(int posicion) {
        return acumulado[posicion] - acumulado[posicion - 1];
    }
}
