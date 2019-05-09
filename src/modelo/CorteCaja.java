package modelo;

public class CorteCaja {
    private int idCorteCaja;
    private int idCaja;
    private int idPersonal;
    private int tipo;
    private double monto;
    private String fecha;
    private String hora;
    private int idPersonalAut;

    private String impresoraTicket;
    private String impresoraReporte;



    public CorteCaja(int idCorteCaja, int idCaja, int idPersonal, int tipo, double monto, String fecha, String hora, int idPersonalAut, String impresoraTicket, String impresoraReporte) {
        this.idCorteCaja = idCorteCaja;
        this.idCaja = idCaja;
        this.idPersonal = idPersonal;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.hora = hora;
        this.idPersonalAut = idPersonalAut;
        this.impresoraTicket = impresoraTicket;
        this.impresoraReporte = impresoraReporte;
    }

    public int getIdCorteCaja() {
        return idCorteCaja;
    }

    public void setIdCorteCaja(int idCorteCaja) {
        this.idCorteCaja = idCorteCaja;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdPersonalAut() {
        return idPersonalAut;
    }

    public void setIdPersonalAut(int idPersonalAut) {
        this.idPersonalAut = idPersonalAut;
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
}
