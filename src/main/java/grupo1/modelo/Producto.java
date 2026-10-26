package grupo1.modelo;

public class Producto {
    private final int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private EstadoProducto estado;
    
    public Producto(int id, String nombre, String categoria, double precio, int stock){
        //valida id mayor a 0
        if(id<=0){
            throw new IllegalArgumentException("El id debe ser mayor que 0");
        }

        //valida stock positivo
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        
        this.id = id;
        setNombre(nombre);
        setCategoria(categoria);
        setPrecio(precio);
        this.stock =stock;
        recalcularEstado();
    }
    
    public int getId()  { return id; }
    public String getNombre()   { return nombre; }
    public String getCategoria()    { return categoria; }
    public double getPrecio()   {return precio;}
    public int getStock()  { return stock; }
    public EstadoProducto getEstado() { return estado; }
    
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }
    
    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoria no puede estar vacía");
        }
        this.categoria = categoria.trim();
    }
    
    public void setPrecio(double precio) {
        if (precio < 0){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }
    
    @Override
    public String toString() {
        return String.format("%d | %s | %s | S/ %.2f | stock: %d | %s",
                            id, nombre, categoria, precio, stock, estado);
    }
    
    public void descontarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        if (cantidad > stock) {
            throw new IllegalArgumentException("Cantidad de unidades a descontar no puede ser mayor al stock disponible");
        }
        stock -= cantidad;
        recalcularEstado();
    }
    public void reponerStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        
        stock += cantidad;
        recalcularEstado();
    }
    
    private void recalcularEstado() {
        estado = (stock == 0) ? EstadoProducto.AGOTADO : EstadoProducto.DISPONIBLE;
    }
}
