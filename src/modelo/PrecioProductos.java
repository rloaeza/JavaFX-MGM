package modelo;

public class PrecioProductos {
    private int idPrecioProducto;
    private double precio;
    private String fecha;
    private int idProducto;

    public PrecioProductos() {
    }

    public PrecioProductos(int idPrecio, double precio, String fecha, int idProducto) {
        this.idPrecioProducto = idPrecio;
        this.precio = precio;
        this.fecha = fecha;
        this.idProducto = idProducto;
    }

    public int getIdPrecioProducto() {
        return idPrecioProducto;
    }

    public void setIdPrecioProducto(int idPrecioProducto) {
        this.idPrecioProducto = idPrecioProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public String toString() {
        return fecha+": "+precio;
    }
}
