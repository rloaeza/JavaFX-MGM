package modelo;

public class Caja {
    private int idCaja;
    private String nombre;
    private String descripcion;
    private double monto;
    private boolean abierto;
    private String impresoraTicket;
    private String impresoraReporte;

    public Caja(int idCaja, String nombre, String descripcion, double monto, boolean abierto, String impresoraTicket, String impresoraReporte) {
        this.idCaja = idCaja;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monto = monto;
        this.abierto = abierto;
        this.impresoraTicket = impresoraTicket;
        this.impresoraReporte = impresoraReporte;
    }

    public String getImpresoraTicket() {
        return impresoraTicket;
    }

    public void setImpresoraTicket(String impresoraTicket) {
        this.impresoraTicket = impresoraTicket;
    }

    public String getImpresoraReporte() {
        return impresoraReporte;
    }

    public void setImpresoraReporte(String impresoraReporte) {
        this.impresoraReporte = impresoraReporte;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
