package grupo1.estructuras;

public class TablaHash<K, V> {
    // Estructura de nodos internos con encadenamiento (listas enlazadas) para colisiones
    private static class NodoHash<K, V> {
        K clave;
        V valor;
        NodoHash<K, V> siguiente;

        public NodoHash(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private NodoHash<K, V>[] tabla;
    private int capacidad;
    private int tamanio;

    @SuppressWarnings("unchecked")
    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoHash[capacidad];
        this.tamanio = 0;
    }

    private int funcionHash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    public void insertar(K clave, V valor) {
        int indice = funcionHash(clave);
        NodoHash<K, V> actual = tabla[indice];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                actual.valor = valor; // Actualiza si ya existe
                return;
            }
            actual = actual.siguiente;
        }

        NodoHash<K, V> nuevoNodo = new NodoHash<>(clave, valor);
        nuevoNodo.siguiente = tabla[indice];
        tabla[indice] = nuevoNodo;
        tamanio++;
    }

    public V buscar(K clave) {
        int indice = funcionHash(clave);
        NodoHash<K, V> actual = tabla[indice];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor; // Retorna el objeto (ej. el objeto Pedido) en O(1) promedio
            }
            actual = actual.siguiente;
        }
        return null; // No encontrado
    }
    
    // Métodos adicionales según tu implementación (eliminar, tamaño, etc.)
}
