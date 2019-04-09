package modelo;

public class VentaLista {
    private int idVentaProductos;
    private String fecha;
    private int cantidadProductos;
    private double subtotoal;
    private double iva;
    private double total;
    private int idPersonal;

    public VentaLista(int idVentaProductos, String fecha, int cantidadProductos, double subtotoal, double iva, double total, int idPersonal) {
        this.idVentaProductos = idVentaProductos;
        this.fecha = fecha;
        this.cantidadProductos = cantidadProductos;
        this.subtotoal = subtotoal;
        this.iva = iva;
        this.total = total;
        this.idPersonal = idPersonal;
    }

    public int getIdVentaProductos() {
        return idVentaProductos;
    }

    public void setIdVentaProductos(int idVentaProductos) {
        this.idVentaProductos = idVentaProductos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public double getSubtotoal() {
        return subtotoal;
    }

    public void setSubtotoal(double subtotoal) {
        this.subtotoal = subtotoal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    @Override
    public String toString() {
        return idVentaProductos+ ": " + cantidadProductos + "  productos el   " + fecha + "  con un total de: $" + total;
    }
}