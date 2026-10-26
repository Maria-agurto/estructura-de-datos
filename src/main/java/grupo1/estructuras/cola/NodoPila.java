package grupo1.estructuras.cola;

import grupo1.modelo.Accion;

public class NodoPila {
    private Accion accion;
    private NodoPila siguiente;

    public NodoPila(Accion accion, NodoPila siguiente) {
        this.accion = accion;
        this.siguiente = siguiente;
    }

    public Accion getAccion() { return accion; }
    public NodoPila getSiguiente() { return siguiente; }

    public void setAccion(Accion accion) { this.accion = accion; }
    public void setSiguiente(NodoPila siguiente) { this.siguiente = siguiente; }
}
