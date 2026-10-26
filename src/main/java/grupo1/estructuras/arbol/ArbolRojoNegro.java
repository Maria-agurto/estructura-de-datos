package grupo1.estructuras.arbol;

import java.util.ArrayList;
import grupo1.modelo.ColorRB;
import grupo1.modelo.Producto;

public class ArbolRojoNegro {
    private NodoRB raiz;
    private final NodoRB NIL;
    private int tamano;
    
    public ArbolRojoNegro(){
        NIL = new NodoRB();
        raiz=NIL;
        tamano=0;
    }
    
    public int getTamano() {
        return tamano;
    }
    
    private void rotarIzquierda(NodoRB actual){
        NodoRB derecha = actual.der;
        actual.der =derecha.izq;
        if(derecha.izq !=NIL){
            derecha.izq.padre=actual;
        }
        derecha.padre=actual.padre;
        //si el nodo es raiz
        if(actual==raiz){
            raiz=derecha;
        //si el nodo es el hijo izquierdo
        }else if(actual == actual.padre.izq){
            actual.padre.izq = derecha;
        //si el nodo es el hijo derecho
        }else{
            actual.padre.der = derecha;
        }
        
        derecha.izq = actual;
        actual.padre = derecha;
    }
    
    private void rotarDerecha(NodoRB actual){
        NodoRB izquierda = actual.izq;
        actual.izq =izquierda.der;
        if(izquierda.der !=NIL){
            izquierda.der.padre=actual;
        }
        izquierda.padre=actual.padre;
        //si el nodo es raiz
        if(actual==raiz){
            raiz=izquierda;
        //si el nodo es el hijo derecho
        }else if(actual == actual.padre.der){
            actual.padre.der = izquierda;
        //si el nodo es el hijo izquierdo
        }else{
            actual.padre.izq = izquierda;
        }
        
        izquierda.der = actual;
        actual.padre = izquierda;
    }
    
    public void insertar(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede estar vacio");
        }

        // Busqueda en arbol binario convencional
        NodoRB padre = NIL;
        NodoRB actual = raiz;
            //actual va hasta las hojas y padre se queda en posicion para enlazar nuevo nodo
        while (actual != NIL) {
            padre = actual;
            if (producto.getId() < actual.producto.getId()) {
                actual = actual.izq;
            } else if (producto.getId() > actual.producto.getId()) {
                actual = actual.der;
            } else {
                throw new IllegalArgumentException("Ya existe un producto con el id " + producto.getId());
            }
        }

        // Crea e inserta el nuevo nodo
        NodoRB nuevo = new NodoRB(producto, NIL);
        nuevo.padre = padre;
        if (padre == NIL) {
            raiz = nuevo;   // árbol vacío
        } else if (producto.getId() < padre.producto.getId()) {
            padre.izq = nuevo;
        } else {
            padre.der = nuevo;
        }
        tamano++;

        // Verificar reglas Rojo-Negro
        corregirInsercion(nuevo);
    }
    
    private void corregirInsercion(NodoRB nodo) {
        // Mientras el padre sea rojo hay dos rojos seguidos
        while (nodo.padre.color == ColorRB.ROJO) {
            if (nodo.padre == nodo.padre.padre.izq) {//padre es hijo izquierdo
                
                NodoRB tio = nodo.padre.padre.der;
                if (tio.color == ColorRB.ROJO) { 
                    // Caso 1: tío rojo -> recolorear y subir
                    nodo.padre.color = ColorRB.NEGRO;
                    tio.color = ColorRB.NEGRO;
                    nodo.padre.padre.color = ColorRB.ROJO;
                    nodo = nodo.padre.padre;
                }else {
                    if (nodo == nodo.padre.der) {
                        // Caso 2: triángulo -> convertirlo en línea
                        nodo = nodo.padre;
                        rotarIzquierda(nodo);
                    }
                    // Caso 3: línea -> recolorear y rotar
                    nodo.padre.color = ColorRB.NEGRO;
                    nodo.padre.padre.color = ColorRB.ROJO;
                    rotarDerecha(nodo.padre.padre);
                }
            
            } else { //padre es hijo derecho

                NodoRB tio = nodo.padre.padre.izq;

                if (tio.color == ColorRB.ROJO) {
                    nodo.padre.color = ColorRB.NEGRO;
                    tio.color = ColorRB.NEGRO;
                    nodo.padre.padre.color = ColorRB.ROJO;
                    nodo = nodo.padre.padre;
                } else {
                    if (nodo == nodo.padre.izq) {
                        nodo = nodo.padre;
                        rotarDerecha(nodo);
                    }
                    nodo.padre.color = ColorRB.NEGRO;
                    nodo.padre.padre.color = ColorRB.ROJO;
                    rotarIzquierda(nodo.padre.padre);
                }
            }
        }
        raiz.color = ColorRB.NEGRO;// la raíz siempre es negra
    }
    
    public ArrayList<Producto> inorden() {
        ArrayList<Producto> lista = new ArrayList<>();
        recorrerInorden(raiz, lista);
        return lista;
    }

    private void recorrerInorden(NodoRB nodo, ArrayList<Producto> lista) {
        if (nodo == NIL) {
            return;
        }
        recorrerInorden(nodo.izq, lista);
        lista.add(nodo.producto);
        recorrerInorden(nodo.der, lista);
    }
    
    //devuelve el producto
    public Producto buscar(int id) {
        NodoRB nodo = buscarNodo(id);
        if (nodo == NIL) {
            return null;
        }
        return nodo.producto;
    }
    //devuelve nodo
    private NodoRB buscarNodo(int id) {
        NodoRB actual = raiz;
        while (actual != NIL) {
            if (id < actual.producto.getId()) {
                actual = actual.izq;
            } else if (id > actual.producto.getId()) {
                actual = actual.der;
            } else {
                return actual; // id encontrado
            }
        }
        return NIL; // no existe
    }
    
    public void descontarStock(int id, int cantidad) {
        Producto producto=buscar(id);
        if (producto == null) {
            throw new IllegalArgumentException("No existe un producto con el id " + id);
        }
        producto.descontarStock(cantidad);
    }
    public void reponerStock(int id, int cantidad) {
        Producto producto=buscar(id);
        if (producto == null) {
            throw new IllegalArgumentException("No existe un producto con el id " + id);
        }
        producto.reponerStock(cantidad);
    }

}
