package modelo;

public class VentaLista {
    private int idVentaProductos;
    private String fecha;
    private int cantidadProductos;
    private double subtotoal;
    private double iva;
    private double total;
    private int idPersonal;
    private String paciente;

    private int nVenta;

    private int descuento;

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }



    public VentaLista(int idVentaProductos, String fecha, int cantidadProductos, double subtotoal, double iva, double total, int idPersonal, String paciente, int descuento) {
        this.idVentaProductos = idVentaProductos;
        this.fecha = fecha;
        this.cantidadProductos = cantidadProductos;
        this.subtotoal = subtotoal;
        this.iva = iva;
        this.total = total;
        this.idPersonal = idPersonal;
        this.paciente = paciente;
        this.descuento = descuento;
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

    public int getnVenta() {
        return nVenta;
    }

    public void setnVenta(int nVenta) {
        this.nVenta = nVenta;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        //return nVenta+ ": " + cantidadProductos + "  productos el   " + fecha + "  con un total de: $" + total+" de "+paciente;
        return "No. "+ nVenta+ "  " + paciente + "  " + fecha + "  con un total de: $" + total;
    }
}
