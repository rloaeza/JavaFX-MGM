package modelo;

public class DetalleTratamientos {
    private String producto;

    private double cantidad;
    private int idDetalleTratamiento;
    private int idProducto;
    private int idTratamiento;

    public DetalleTratamientos() {
    }

    public DetalleTratamientos(String producto, double cantidad, int idDetalleTratamiento, int idProducto, int idTratamiento) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.idDetalleTratamiento = idDetalleTratamiento;
        this.idProducto = idProducto;
        this.idTratamiento = idTratamiento;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdDetalleTratamiento() {
        return idDetalleTratamiento;
    }

    public void setIdDetalleTratamiento(int idDetalleTratamiento) {
        this.idDetalleTratamiento = idDetalleTratamiento;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @Override
    public String toString() {
        return producto + " ( "+cantidad+" )";
    }
}
