package modelo;

public class AlmacenEntrada {
    private int idAlmacen;
    private double cantidad;
    private String fecha;
    private String nombre;
    private int autorizado;

    public AlmacenEntrada() {
    }

    public AlmacenEntrada(int idAlmacen, double cantidad, String fecha, String nombre, int autorizado) {
        this.idAlmacen = idAlmacen;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.nombre = nombre;
        this.autorizado = autorizado;
    }

    public int getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(int autorizado) {
        this.autorizado = autorizado;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return fecha.substring(0,10)+": "+nombre+ ", "+ cantidad;
    }
}
