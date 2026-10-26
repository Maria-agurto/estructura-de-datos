package grupo1.estructuras.arbol;

import grupo1.modelo.ColorRB;
import grupo1.modelo.Producto;

class NodoRB {
    Producto producto;
    ColorRB color;
    NodoRB izq, der, padre;
    
    //nodo convencional empieza en rojo
    NodoRB(Producto producto, NodoRB NIL){
        this.producto = producto;
        this.color = ColorRB.ROJO;
        this.izq = this.der = this.padre = NIL;
        
    }
    
    //nodo centinela NIL
    NodoRB(){
        this.producto=null;
        this.color=ColorRB.NEGRO;
    }
}
