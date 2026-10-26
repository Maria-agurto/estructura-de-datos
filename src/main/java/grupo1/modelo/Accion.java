package grupo1.modelo;

import java.util.ArrayList;

public class Accion {
    private TipoAccion tipo;
    private String codigoPedido;
    private ArrayList<ItemPedido> items;

    public Accion(TipoAccion tipo, String codigoPedido, ArrayList<ItemPedido> items) {
        setTipo(tipo);
        setCodigoPedido(codigoPedido);
        setItems(items);
    }

    public TipoAccion getTipo() { return tipo; }
    public String getCodigoPedido() { return codigoPedido; }
    public ArrayList<ItemPedido> getItems() { return items; }

    public void setTipo(TipoAccion tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de acción no puede ser nulo");
        }
        this.tipo = tipo;
    }

    public void setCodigoPedido(String codigoPedido) {
        if (codigoPedido == null || codigoPedido.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del pedido no puede estar vacío");
        }
        this.codigoPedido = codigoPedido.trim();
    }

    public void setItems(ArrayList<ItemPedido> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La acción debe tener al menos un ítem");
        }
        this.items = items;
    }
}
