package grupo1.estructuras.cola;

import grupo1.modelo.Accion;

public class Pila {
    private NodoPila tope;
    private int tamano;

    public Pila() {
        tope = null;
        tamano = 0;
    }

    public void push(Accion accion) {
        if (accion == null) {
            throw new IllegalArgumentException("La acción no puede ser nula");
        }
        //el nuevo nodo apunta al tope anterior y pasa a ser el tope
        tope = new NodoPila(accion, tope);
        tamano++;
    }

    public Accion pop() {
        if (tope == null) {
            return null;
        }
        Accion accion = tope.getAccion();
        tope = tope.getSiguiente();
        tamano--;
        return accion;
    }

    public Accion peek() {
        return tope == null ? null : tope.getAccion();
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int getTamano() {
        return tamano;
    }
}
