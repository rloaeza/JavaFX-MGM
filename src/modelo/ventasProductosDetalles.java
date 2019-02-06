package modelo;

public class ventasProductosDetalles {
    private int idVentaProductosDetalle;
    private int cantidad;
    private double costo;
    private double total;
    private int idProducto;
    private int idVentaProductos;

    public ventasProductosDetalles(int idVentaProductosDetalle, int cantidad, double costo, double total, int idProducto, int idVentaProductos) {
        this.idVentaProductosDetalle = idVentaProductosDetalle;
        this.cantidad = cantidad;
        this.costo = costo;
        this.total = total;
        this.idProducto = idProducto;
        this.idVentaProductos = idVentaProductos;
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

    public int getIdVentaProductos() {
        return idVentaProductos;
    }

    public void setIdVentaProductos(int idVentaProductos) {
        this.idVentaProductos = idVentaProductos;
    }
}
