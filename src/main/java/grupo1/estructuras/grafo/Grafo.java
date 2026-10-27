package grupo1.estructuras.grafo;

// Grafo dirigido ponderado con listas de adyacencia.
// Los nodos se identifican con enteros (0, 1, 2...) y el peso de cada arista son minutos (RN-10).
// El tamano no es fijo: el arreglo de nodos crece solo, asi se pueden agregar distritos despues.
public class Grafo {
    private static final int CAPACIDAD_INICIAL = 8;

    private String[] nombres;      // nombres[i] = nombre del nodo i
    private Arista[] adyacencia;   // adyacencia[i] = primera arista que sale del nodo i
    private int cantidadNodos;
    private int cantidadAristas;

    public Grafo() {
        nombres = new String[CAPACIDAD_INICIAL];
        adyacencia = new Arista[CAPACIDAD_INICIAL];
        cantidadNodos = 0;
        cantidadAristas = 0;
    }

    // Agrega un nodo y devuelve su id (el primero que se agrega es el 0).
    public int agregarNodo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del nodo no puede estar vacio");
        }
        if (cantidadNodos == nombres.length) {
            crecer();
        }
        nombres[cantidadNodos] = nombre.trim();
        adyacencia[cantidadNodos] = null;
        cantidadNodos++;
        return cantidadNodos - 1;
    }

    // Agrega la arista origen -> destino. Si ya existia, actualiza su tiempo.
    public void agregarArista(int origen, int destino, int minutos) {
        validarNodo(origen);
        validarNodo(destino);
        if (origen == destino) {
            throw new IllegalArgumentException("Un nodo no puede conectarse consigo mismo");
        }
        if (minutos <= 0) {
            throw new IllegalArgumentException("El tiempo de la arista debe ser mayor que 0");
        }
        Arista actual = adyacencia[origen];
        if (actual == null) {
            adyacencia[origen] = new Arista(destino, minutos);
            cantidadAristas++;
            return;
        }
        // recorre la lista hasta el final por si la arista ya existe
        while (true) {
            if (actual.getDestino() == destino) {
                actual.setMinutos(minutos);
                return;
            }
            if (actual.getSiguiente() == null) {
                break;
            }
            actual = actual.getSiguiente();
        }
        actual.setSiguiente(new Arista(destino, minutos));
        cantidadAristas++;
    }

    // Agrega a -> b y b -> a con tiempos distintos (ida y vuelta).
    public void agregarAristaDoble(int a, int b, int minutosIda, int minutosVuelta) {
        agregarArista(a, b, minutosIda);
        agregarArista(b, a, minutosVuelta);
    }

    // Primera arista que sale del nodo; las demas se recorren con getSiguiente().
    public Arista getAdyacentes(int nodo) {
        validarNodo(nodo);
        return adyacencia[nodo];
    }

    public String getNombre(int nodo) {
        validarNodo(nodo);
        return nombres[nodo];
    }

    public boolean existeNodo(int nodo) {
        return nodo >= 0 && nodo < cantidadNodos;
    }

    public int getCantidadNodos() { return cantidadNodos; }
    public int getCantidadAristas() { return cantidadAristas; }

    private void validarNodo(int nodo) {
        if (!existeNodo(nodo)) {
            throw new IllegalArgumentException("El nodo " + nodo + " no existe");
        }
    }

    private void crecer() {
        String[] nuevosNombres = new String[nombres.length * 2];
        Arista[] nuevaAdyacencia = new Arista[adyacencia.length * 2];
        for (int i = 0; i < cantidadNodos; i++) {
            nuevosNombres[i] = nombres[i];
            nuevaAdyacencia[i] = adyacencia[i];
        }
        nombres = nuevosNombres;
        adyacencia = nuevaAdyacencia;
    }
}
