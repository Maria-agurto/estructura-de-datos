package grupo1.estructuras.cola;

import grupo1.modelo.Pedido;

public class MaxHeap {
    private static final int CAPACIDAD_INICIAL = 16;

    private Pedido[] datos;
    private int tamano;

    public MaxHeap() {
        datos = new Pedido[CAPACIDAD_INICIAL];
        tamano = 0;
    }

    //RN-06: mayor puntaje primero; si empatan, menor secuencia (mas antiguo)
    private boolean tienePrioridad(Pedido a, Pedido b) {
        if (a.getPuntaje() != b.getPuntaje()) {
            return a.getPuntaje() > b.getPuntaje();
        }
        return a.getSecuencia() < b.getSecuencia();
    }

    public void insertar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        if (tamano == datos.length) {
            crecer();
        }
        datos[tamano] = pedido;
        subir(tamano);
        tamano++;
    }

    public Pedido extraerMax() {
        if (tamano == 0) {
            return null;
        }
        Pedido max = datos[0];
        //el ultimo pedido pasa a la raiz y baja a su lugar
        tamano--;
        datos[0] = datos[tamano];
        datos[tamano] = null;
        if (tamano > 0) {
            bajar(0);
        }
        return max;
    }

    public Pedido verMax() {
        return tamano == 0 ? null : datos[0];
    }

    public boolean eliminar(String codigo) {
        int indice = -1;
        for (int i = 0; i < tamano; i++) {
            if (datos[i].getCodigo().equals(codigo)) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            return false;
        }
        tamano--;
        if (indice == tamano) {
            datos[tamano] = null;
            return true;
        }
        //el ultimo pedido ocupa el lugar eliminado y se reubica
        datos[indice] = datos[tamano];
        datos[tamano] = null;
        subir(indice);
        bajar(indice);
        return true;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    public int getTamano() {
        return tamano;
    }

    private void subir(int i) {
        while (i > 0) {
            //padre de i en (i-1)/2
            int padre = (i - 1) / 2;
            if (!tienePrioridad(datos[i], datos[padre])) {
                break;
            }
            intercambiar(i, padre);
            i = padre;
        }
    }

    private void bajar(int i) {
        while (true) {
            //hijos de i en 2i+1 y 2i+2
            int izq = 2 * i + 1;
            int der = 2 * i + 2;
            int mayor = i;
            if (izq < tamano && tienePrioridad(datos[izq], datos[mayor])) {
                mayor = izq;
            }
            if (der < tamano && tienePrioridad(datos[der], datos[mayor])) {
                mayor = der;
            }
            if (mayor == i) {
                break;
            }
            intercambiar(i, mayor);
            i = mayor;
        }
    }

    private void intercambiar(int i, int j) {
        Pedido tmp = datos[i];
        datos[i] = datos[j];
        datos[j] = tmp;
    }

    private void crecer() {
        Pedido[] nuevo = new Pedido[datos.length * 2];
        for (int i = 0; i < tamano; i++) {
            nuevo[i] = datos[i];
        }
        datos = nuevo;
    }
}
