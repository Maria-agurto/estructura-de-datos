package grupo1.modelo;

public class ItemPedido {
    private int idProducto;
    private int cantidad;

    public ItemPedido(int idProducto, int cantidad) {
        setIdProducto(idProducto);
        setCantidad(cantidad);
    }

    public int getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }

    public void setIdProducto(int idProducto) {
        if (idProducto <= 0) {
            throw new IllegalArgumentException("El id del producto debe ser mayor que 0");
        }
        this.idProducto = idProducto;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return idProducto + " x" + cantidad;
    }
}
