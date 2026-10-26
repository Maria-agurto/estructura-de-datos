package grupo1.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pedido {
    private String codigo;
    private String cliente;
    private int idNodoDestino;
    private TipoEnvio tipoEnvio;
    private int puntaje;
    private long secuencia;
    private ArrayList<ItemPedido> items;
    private EstadoPedido estado;
    private String vendedor;
    private String repartidor;
    private LocalDateTime fechaRegistro;
    private Integer tiempoEstimadoMin;

    //pedido nuevo: nace EN_COLA, sin repartidor ni tiempo estimado
    public Pedido(String codigo, String cliente, int idNodoDestino, TipoEnvio tipoEnvio,
                  int puntaje, long secuencia, ArrayList<ItemPedido> items, String vendedor) {
        this(codigo, cliente, idNodoDestino, tipoEnvio, puntaje, secuencia, items,
             EstadoPedido.EN_COLA, vendedor, null, LocalDateTime.now(), null);
    }

    //constructor completo (por ejemplo para cargar desde pedidos.txt)
    public Pedido(String codigo, String cliente, int idNodoDestino, TipoEnvio tipoEnvio,
                  int puntaje, long secuencia, ArrayList<ItemPedido> items, EstadoPedido estado,
                  String vendedor, String repartidor, LocalDateTime fechaRegistro,
                  Integer tiempoEstimadoMin) {
        setCodigo(codigo);
        setCliente(cliente);
        setIdNodoDestino(idNodoDestino);
        setTipoEnvio(tipoEnvio);
        setPuntaje(puntaje);
        setSecuencia(secuencia);
        setItems(items);
        setEstado(estado);
        setVendedor(vendedor);
        setRepartidor(repartidor);
        setFechaRegistro(fechaRegistro);
        setTiempoEstimadoMin(tiempoEstimadoMin);
    }

    public String getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public int getIdNodoDestino() { return idNodoDestino; }
    public TipoEnvio getTipoEnvio() { return tipoEnvio; }
    public int getPuntaje() { return puntaje; }
    public long getSecuencia() { return secuencia; }
    public ArrayList<ItemPedido> getItems() { return items; }
    public EstadoPedido getEstado() { return estado; }
    public String getVendedor() { return vendedor; }
    public String getRepartidor() { return repartidor; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public Integer getTiempoEstimadoMin() { return tiempoEstimadoMin; }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío");
        }
        this.codigo = codigo.trim();
    }

    public void setCliente(String cliente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El cliente no puede estar vacío");
        }
        this.cliente = cliente.trim();
    }

    public void setIdNodoDestino(int idNodoDestino) {
        if (idNodoDestino < 0) {
            throw new IllegalArgumentException("El nodo de destino no puede ser negativo");
        }
        this.idNodoDestino = idNodoDestino;
    }

    public void setTipoEnvio(TipoEnvio tipoEnvio) {
        if (tipoEnvio == null) {
            throw new IllegalArgumentException("El tipo de envío no puede ser nulo");
        }
        this.tipoEnvio = tipoEnvio;
    }

    public void setPuntaje(int puntaje) {
        if (puntaje < 0) {
            throw new IllegalArgumentException("El puntaje no puede ser negativo");
        }
        this.puntaje = puntaje;
    }

    public void setSecuencia(long secuencia) {
        if (secuencia < 0) {
            throw new IllegalArgumentException("La secuencia no puede ser negativa");
        }
        this.secuencia = secuencia;
    }

    public void setItems(ArrayList<ItemPedido> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un producto");
        }
        this.items = items;
    }

    public void setEstado(EstadoPedido estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
    }

    public void setVendedor(String vendedor) {
        if (vendedor == null || vendedor.trim().isEmpty()) {
            throw new IllegalArgumentException("El vendedor no puede estar vacío");
        }
        this.vendedor = vendedor.trim();
    }

    //el repartidor es nulo hasta que se asigna el pedido
    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        if (fechaRegistro == null) {
            throw new IllegalArgumentException("La fecha de registro no puede ser nula");
        }
        this.fechaRegistro = fechaRegistro;
    }

    //el tiempo estimado es nulo hasta que Dijkstra lo calcula
    public void setTiempoEstimadoMin(Integer tiempoEstimadoMin) {
        if (tiempoEstimadoMin != null && tiempoEstimadoMin < 0) {
            throw new IllegalArgumentException("El tiempo estimado no puede ser negativo");
        }
        this.tiempoEstimadoMin = tiempoEstimadoMin;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | nodo %d | %s | pts: %d | %s | items: %s",
                codigo, cliente, idNodoDestino, tipoEnvio, puntaje, estado, items);
    }
}
