package grupo1.estructuras.grafo;

// Arista dirigida de la lista de adyacencia: va hacia 'destino' y cuesta 'minutos'.
// Cada arista apunta a la siguiente del mismo nodo origen (lista enlazada).
public class Arista {
    private final int destino;
    private int minutos;
    private Arista siguiente;

    public Arista(int destino, int minutos) {
        this.destino = destino;
        this.minutos = minutos;
        this.siguiente = null;
    }

    public int getDestino() { return destino; }
    public int getMinutos() { return minutos; }
    public Arista getSiguiente() { return siguiente; }

    void setMinutos(int minutos) { this.minutos = minutos; }
    void setSiguiente(Arista siguiente) { this.siguiente = siguiente; }
}
