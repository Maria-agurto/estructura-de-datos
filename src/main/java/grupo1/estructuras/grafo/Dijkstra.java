package grupo1.estructuras.grafo;

import grupo1.modelo.Ruta;

// Algoritmo de Dijkstra sobre el Grafo: camino de menor tiempo entre dos nodos.
// Usa un min-heap propio (ColaMinima), por eso cuesta O((V + E) log V).
public class Dijkstra {
    private static final int INFINITO = Integer.MAX_VALUE;

    // Devuelve la ruta de menor tiempo de origen a destino, o null si no hay camino.
    public static Ruta calcular(Grafo grafo, int origen, int destino) {
        if (!grafo.existeNodo(origen) || !grafo.existeNodo(destino)) {
            throw new IllegalArgumentException("Nodo de origen o destino inexistente");
        }
        int n = grafo.getCantidadNodos();
        int[] distancia = new int[n];
        int[] previo = new int[n];
        boolean[] fijo = new boolean[n];
        for (int i = 0; i < n; i++) {
            distancia[i] = INFINITO;
            previo[i] = -1;
        }

        ColaMinima cola = new ColaMinima();
        distancia[origen] = 0;
        cola.insertar(origen, 0);

        while (!cola.estaVacia()) {
            int u = cola.extraerNodo();
            if (fijo[u]) {
                continue; // entrada vieja: el nodo ya se fijo con un tiempo menor
            }
            fijo[u] = true;
            if (u == destino) {
                break;
            }
            for (Arista a = grafo.getAdyacentes(u); a != null; a = a.getSiguiente()) {
                int v = a.getDestino();
                if (fijo[v]) {
                    continue;
                }
                int nueva = distancia[u] + a.getMinutos();
                if (nueva < distancia[v]) {
                    distancia[v] = nueva;
                    previo[v] = u;
                    cola.insertar(v, nueva);
                }
            }
        }

        if (distancia[destino] == INFINITO) {
            return null;
        }
        return construirRuta(distancia, previo, destino);
    }

    // Reconstruye el camino yendo de destino hacia origen con el arreglo 'previo'.
    private static Ruta construirRuta(int[] distancia, int[] previo, int destino) {
        int largo = 0;
        for (int v = destino; v != -1; v = previo[v]) {
            largo++;
        }
        int[] nodos = new int[largo];
        int[] acumulado = new int[largo];
        int i = largo - 1;
        for (int v = destino; v != -1; v = previo[v]) {
            nodos[i] = v;
            acumulado[i] = distancia[v];
            i--;
        }
        return new Ruta(nodos, acumulado);
    }

    // Min-heap de pares (nodo, tiempo); el de menor tiempo queda en la raiz.
    private static class ColaMinima {
        private int[] nodos = new int[16];
        private int[] tiempos = new int[16];
        private int tamano = 0;

        boolean estaVacia() { return tamano == 0; }

        void insertar(int nodo, int tiempo) {
            if (tamano == nodos.length) {
                crecer();
            }
            nodos[tamano] = nodo;
            tiempos[tamano] = tiempo;
            subir(tamano);
            tamano++;
        }

        int extraerNodo() {
            int raiz = nodos[0];
            tamano--;
            nodos[0] = nodos[tamano];
            tiempos[0] = tiempos[tamano];
            if (tamano > 0) {
                bajar(0);
            }
            return raiz;
        }

        private void subir(int i) {
            while (i > 0) {
                int padre = (i - 1) / 2;
                if (tiempos[i] >= tiempos[padre]) {
                    break;
                }
                intercambiar(i, padre);
                i = padre;
            }
        }

        private void bajar(int i) {
            while (true) {
                int izq = 2 * i + 1;
                int der = 2 * i + 2;
                int menor = i;
                if (izq < tamano && tiempos[izq] < tiempos[menor]) {
                    menor = izq;
                }
                if (der < tamano && tiempos[der] < tiempos[menor]) {
                    menor = der;
                }
                if (menor == i) {
                    break;
                }
                intercambiar(i, menor);
                i = menor;
            }
        }

        private void intercambiar(int i, int j) {
            int n = nodos[i]; nodos[i] = nodos[j]; nodos[j] = n;
            int t = tiempos[i]; tiempos[i] = tiempos[j]; tiempos[j] = t;
        }

        private void crecer() {
            int[] nuevosNodos = new int[nodos.length * 2];
            int[] nuevosTiempos = new int[tiempos.length * 2];
            for (int i = 0; i < tamano; i++) {
                nuevosNodos[i] = nodos[i];
                nuevosTiempos[i] = tiempos[i];
            }
            nodos = nuevosNodos;
            tiempos = nuevosTiempos;
        }
    }
}
