package modelo;

public class PagoTratamiento {
    private int idPagoTratamiento;
    private double cantidad;
    private String fecha;
    private int idTratamientoRecetado;


    public PagoTratamiento(int idPagoTratamiento, double cantidad, String fecha, int idTratamientoRecetado) {
        this.idPagoTratamiento = idPagoTratamiento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idTratamientoRecetado = idTratamientoRecetado;
    }

    public PagoTratamiento() {
    }

    public int getIdPagoTratamiento() {
        return idPagoTratamiento;
    }

    public void setIdPagoTratamiento(int idPagoTratamiento) {
        this.idPagoTratamiento = idPagoTratamiento;
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

    public int getIdTratamientoRecetado() {
        return idTratamientoRecetado;
    }

    public void setIdTratamientoRecetado(int idTratamientoRecetado) {
        this.idTratamientoRecetado = idTratamientoRecetado;
    }

    @Override
    public String toString() {
        return fecha+" "+cantidad;
    }
}
