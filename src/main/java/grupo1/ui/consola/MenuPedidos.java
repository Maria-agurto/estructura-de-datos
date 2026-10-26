package grupo1.ui.consola;

import grupo1.estructuras.arbol.ArbolRojoNegro;
import grupo1.estructuras.cola.MaxHeap;
import grupo1.estructuras.cola.Pila;
import grupo1.estructuras.hash.TablaHash;
import grupo1.modelo.Accion;
import grupo1.modelo.EstadoPedido;
import grupo1.modelo.ItemPedido;
import grupo1.modelo.Pedido;
import grupo1.modelo.Producto;
import grupo1.modelo.TipoAccion;
import grupo1.modelo.TipoEnvio;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPedidos {
    private static final int PUNTAJE_EXPRESS = 50;
    private static final int PUNTAJE_ESTANDAR = 10;

    private final ArbolRojoNegro arbol;
    private final TablaHash<String, Pedido> tablaPedidos;
    private final MaxHeap heap;
    private final Pila pila;
    private final Scanner sc;
    private final String vendedor;
    private long siguienteSecuencia;

    public MenuPedidos(ArbolRojoNegro arbol, TablaHash<String, Pedido> tablaPedidos,
                       MaxHeap heap, Pila pila, Scanner sc, String vendedor,
                       long siguienteSecuencia){
        this.arbol = arbol;
        this.tablaPedidos = tablaPedidos;
        this.heap = heap;
        this.pila = pila;
        this.sc = sc;
        this.vendedor = vendedor;
        this.siguienteSecuencia = siguienteSecuencia;
    }

    private void mostrarMenu(){
        System.out.println(" ===== Menu de Pedidos ==== ");
        System.out.println("1. Registrar nuevo pedido");
        System.out.println("2. Ver pedido urgente en cima");
        System.out.println("3. Deshacer ultima venta");
        System.out.println("0. Regresar al menu principal");
    }

    public void iniciar(){
        int opc;
        do{
            mostrarMenu();
            opc=leerEntero("Seleccione una opcion -> ");
            try{
                switch(opc){
                    case 1: registrarPedido(); break;
                    case 2: verPedidoUrgente(); break;
                    case 3: deshacerUltimaVenta(); break;
                    case 0: System.out.println("Volviendo al menú principal..."); break;
                    default: System.out.println("Opción no válida.");

                }
            }catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            pausar();
        }while(opc!=0);
    }

    private void registrarPedido() {
        System.out.println("-- Registrar nuevo pedido --");
        String cliente = leerString("Cliente: ");
        int idNodoDestino = leerEntero("Id del nodo de destino (1-7): ");
        //el nodo 0 es el almacen, no puede ser destino
        if(idNodoDestino <= 0){
            System.out.println("El destino debe ser un nodo de entrega (el nodo 0 es el almacén).");
            return;
        }
        TipoEnvio tipoEnvio = leerTipoEnvio();

        ArrayList<ItemPedido> items = new ArrayList<>();
        boolean otro;
        do{
            int idProducto = leerEntero("Id del producto: ");
            Producto p = arbol.buscar(idProducto);
            if(p == null){
                System.out.println("No existe un producto con el id " + idProducto);
            }else{
                System.out.println(p);
                int cantidad = leerEntero("Cantidad: ");
                if(cantidad <= 0){
                    System.out.println("La cantidad debe ser mayor que 0.");
                }else{
                    agregarItem(items, idProducto, cantidad);
                }
            }
            otro = leerSiNo("¿Agregar otro producto? (s/n): ");
        }while(otro);

        if(items.isEmpty()){
            System.out.println("El pedido debe tener al menos un producto. No se registró nada.");
            return;
        }

        int puntaje = (tipoEnvio == TipoEnvio.EXPRESS) ? PUNTAJE_EXPRESS : PUNTAJE_ESTANDAR;
        long secuencia = siguienteSecuencia;
        String codigo = String.format("PED-%04d", secuencia);

        //se crea primero para validar los datos antes de tocar el stock
        Pedido pedido = new Pedido(codigo, cliente, idNodoDestino, tipoEnvio, puntaje,
                secuencia, items, vendedor);

        if(!descontarStock(items)){
            return;
        }

        tablaPedidos.put(codigo, pedido);
        heap.insertar(pedido);
        pila.push(new Accion(TipoAccion.REGISTRO_PEDIDO, codigo, copiarItems(items)));
        siguienteSecuencia++;

        System.out.println("Pedido registrado: " + pedido);
        System.out.println("Puntaje de urgencia: " + puntaje);
    }

    private void verPedidoUrgente() {
        System.out.println("-- Pedido más urgente --");
        Pedido max = heap.verMax();
        if(max == null){
            System.out.println("No hay pedidos pendientes en la cola.");
            return;
        }
        System.out.println(max);
        System.out.println("Pedidos en cola: " + heap.getTamano());
    }

    private void deshacerUltimaVenta() {
        System.out.println("-- Deshacer última venta --");
        Accion accion = pila.peek();
        if(accion == null){
            System.out.println("No hay acciones para deshacer.");
            return;
        }
        Pedido pedido = tablaPedidos.get(accion.getCodigoPedido());
        if(pedido == null){
            System.out.println("No se encontró el pedido " + accion.getCodigoPedido() + ".");
            return;
        }
        //RN-07: solo se deshace si el pedido sigue EN_COLA
        if(pedido.getEstado() != EstadoPedido.EN_COLA){
            System.out.println("No se puede deshacer: el pedido " + pedido.getCodigo()
                    + " ya está " + pedido.getEstado() + ".");
            return;
        }
        if(!heap.eliminar(pedido.getCodigo())){
            System.out.println("No se encontró el pedido " + pedido.getCodigo() + " en la cola.");
            return;
        }
        for (ItemPedido item : accion.getItems()) {
            arbol.reponerStock(item.getIdProducto(), item.getCantidad());
        }
        pedido.setEstado(EstadoPedido.ANULADO);
        pila.pop();
        System.out.println("Pedido " + pedido.getCodigo() + " anulado y stock repuesto.");
    }

    private void agregarItem(ArrayList<ItemPedido> items, int idProducto, int cantidad) {
        //si el producto ya esta en el pedido se suman las cantidades
        for (ItemPedido item : items) {
            if(item.getIdProducto() == idProducto){
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemPedido(idProducto, cantidad));
    }

    //si algun item falla se reponen los ya descontados y no se crea nada
    private boolean descontarStock(ArrayList<ItemPedido> items) {
        ArrayList<ItemPedido> descontados = new ArrayList<>();
        for (ItemPedido item : items) {
            try{
                arbol.descontarStock(item.getIdProducto(), item.getCantidad());
                descontados.add(item);
            }catch (IllegalArgumentException e) {
                for (ItemPedido d : descontados) {
                    arbol.reponerStock(d.getIdProducto(), d.getCantidad());
                }
                System.out.println("Stock insuficiente para el producto " + item.getIdProducto()
                        + " (" + e.getMessage() + "). No se registró el pedido.");
                return false;
            }
        }
        return true;
    }

    private ArrayList<ItemPedido> copiarItems(ArrayList<ItemPedido> items) {
        ArrayList<ItemPedido> copia = new ArrayList<>();
        for (ItemPedido item : items) {
            copia.add(new ItemPedido(item.getIdProducto(), item.getCantidad()));
        }
        return copia;
    }

    private TipoEnvio leerTipoEnvio() {
        while (true) {
            System.out.println("Tipo de envío: 1. Estándar  2. Express");
            int opcion = leerEntero("Seleccione -> ");
            if(opcion == 1){
                return TipoEnvio.ESTANDAR;
            }
            if(opcion == 2){
                return TipoEnvio.EXPRESS;
            }
            System.out.println("Opción no válida.");
        }
    }

    private boolean leerSiNo(String mensaje) {
        while (true) {
            String r = leerString(mensaje).toLowerCase();
            if(r.equals("s")){
                return true;
            }
            if(r.equals("n")){
                return false;
            }
            System.out.println("Responde s o n.");
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero válido.");
            }
        }
    }

    private String leerString(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();

    }

    private void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        sc.nextLine();
    }

}
