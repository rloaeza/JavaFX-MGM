package modelo;


public class VentaMostradorQuitar  {
    private int idVentaProductosDetalle;
    private int cantidad;

    public VentaMostradorQuitar(int idVentaProductosDetalle, int cantidad) {
        this.idVentaProductosDetalle = idVentaProductosDetalle;
        this.cantidad = cantidad;
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
}


