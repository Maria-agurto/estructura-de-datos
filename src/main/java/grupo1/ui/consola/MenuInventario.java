
package grupo1.ui.consola;

import grupo1.estructuras.arbol.ArbolRojoNegro;
import grupo1.modelo.Producto;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuInventario {
    private final ArbolRojoNegro arbol;
    private final Scanner sc; 
    
    public MenuInventario(ArbolRojoNegro arbol, Scanner sc){
        this.arbol =arbol;
        this.sc=sc;
    }
    
    private void mostrarMenu(){
        System.out.println("\n\n ===== Menu de Inventario ==== ");
        System.out.println("1. Registrar producto");
        System.out.println("2. Buscar producto por ID");
        System.out.println("3. Reponer stock");
        System.out.println("4. Descontar stock");
        System.out.println("5. Actualizar datos");
        System.out.println("6. Listar catalogo por ID");
        System.out.println("7. Listar catalogo alfabeticamente(pendiente)");
        System.out.println("8. Eliminar producto (pendiente)");
        System.out.println("0. Volver a menu principal");

    }
    
    public void iniciar(){
        int opc;
        do{
            mostrarMenu();
            opc=leerEntero("\nSeleccione una opcion -> ");
            try{
                switch(opc){
                    case 1: registrarProducto(); break;
                    case 2: buscarProducto(); break;
                    case 3: reponerStock(); break;
                    case 4: descontarStock(); break;
                    case 5: actualizarDatos(); break;
                    case 6: listarId(); break;
                    case 7: listarAlfabeto(); break;
                    case 8: eliminarProducto(); break;
                    case 0: System.out.println("\nVolviendo al menú principal..."); break;
                    default: System.out.println("\nOpción no válida.");
                    
                }
            }catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            pausar();
        }while(opc!=0);
    }
    
    private void registrarProducto() {
        System.out.println("\n-- Registrar producto --");
        int id = leerEntero("Id: ");
        String nombre = leerString("Nombre: ");
        String categoria = leerString("Categoría: ");
        double precio = leerDecimal("Precio: ");
        int stock = leerEntero("Stock inicial: ");

        Producto p = new Producto(id, nombre, categoria, precio, stock);
        arbol.insertar(p);
        System.out.println("\nProducto registrado: " + p);
    }
    
    private void buscarProducto(){
        System.out.println("\n-- Buscar producto por ID --");
        int id = leerEntero("Ingrese id de producto a buscar: ");
        Producto p = arbol.buscar(id);
        if(p == null){
            System.out.println("\nNo existe un producto con el id " + id);
        }else{
            System.out.println(p);
        }
    }
    
    private void reponerStock(){
        System.out.println("\n -- Reponer stock --");
        int id = leerEntero("Id: ");
        int cantidad = leerEntero("Cantidad a reponer: ");
        arbol.reponerStock(id, cantidad);
        Producto p = arbol.buscar(id);
        System.out.println(p);
        System.out.println("\nStock repuesto.");
        
    }
    
    private void descontarStock(){
        System.out.println("\n -- Descontar stock --");
        int id = leerEntero("Id: ");
        int cantidad = leerEntero("Cantidad a descontar: ");
        arbol.descontarStock(id, cantidad);
        Producto p = arbol.buscar(id);
        System.out.println(p);
        System.out.println("\nStock descontado.");
    }
    
    private void actualizarDatos(){
        System.out.println("\n -- Actualizar datos --");
        int id = leerEntero("Ingrese id de producto a actualizar: ");
        Producto p = arbol.buscar(id);
        if(p == null){
            System.out.println("\nNo existe un producto con el id " + id);
        }else{
            System.out.println(p);
            int opcion;
            do{
                System.out.println("\n1. Nombre");
                System.out.println("2. Categoria");
                System.out.println("3. Precio");
                System.out.println("0. Volver");
                opcion = leerEntero("\nSeleccione qué modificará -> ");
                try{
                    switch(opcion){
                        case 1:{
                            String nombre = leerString("Nuevo nombre: ");
                            p.setNombre(nombre);
                            System.out.println("Nombre actualizado.");
                            break;
                        }
                        case 2:{
                            String categoria = leerString("Nueva categoria: ");
                            p.setCategoria(categoria);
                            System.out.println("Categoria actualizada.");
                            break;
                        }
                        case 3:{
                            double precio = leerDecimal("Nuevo precio: ");
                            p.setPrecio(precio);
                            System.out.println("Precio actualizado.");
                            break;
                        }
                        case 0: System.out.println("\nVolviendo a menu..."); break;
                        default: System.out.println("\nOpcion no valida"); break;
                    }
                }catch(IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }while(opcion!=0);
        }    
    }
    
    private void listarId(){
        System.out.println("\n-- Catálogo ordenado por ID --");
        ArrayList<Producto> lista = arbol.inorden();
        if (lista.isEmpty()) {
            System.out.println("\nCatálogo vacío.");
            return;
        }
        for (Producto p : lista) {
            System.out.println(p);
        }
        System.out.println("\nTotal de productos: " + arbol.getTamano());
    }
    
    private void listarAlfabeto() {
        System.out.println("Opción pendiente de implementar.");
    }

    private void eliminarProducto() {
        System.out.println("Opción pendiente de implementar.");
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
    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                String linea = sc.nextLine().trim().replace(',', '.');
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Escribe un decimal válido.");
            }
        }
    }
    
    private void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        sc.nextLine();
    }

}
