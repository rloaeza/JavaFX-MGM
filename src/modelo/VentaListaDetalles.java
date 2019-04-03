package modelo;

public class VentaListaDetalles {
    private int idVentaProductosDetalle;
    private int cantidad;
    private String nombre;
    private double costo;
    private double total;
    private int idProducto;

    public VentaListaDetalles(int idVentaProductosDetalle, int cantidad, String nombre, double costo, double total, int idProducto) {
        this.idVentaProductosDetalle = idVentaProductosDetalle;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.costo = costo;
        this.total = total;
        this.idProducto = idProducto;
    }

    public int getIdVentaProductosDetalle() {
        return idVentaProductosDetalle;
    }

    public void setIdVentaProductosDetalle(int idVentaProductosDetalle) {
        this.idVentaProductosDetalle = idVentaProductosDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
