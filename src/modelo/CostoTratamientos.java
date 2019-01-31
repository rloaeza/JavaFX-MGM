package modelo;

public class CostoTratamientos {
    private int idCostoTratamiento;
    private double costo;
    private String fecha;
    private int idTratamiento;

    public CostoTratamientos() {
    }

    public CostoTratamientos(int idPrecio, double costo, String fecha, int idTratamiento) {
        this.idCostoTratamiento = idPrecio;
        this.costo = costo;
        this.fecha = fecha;
        this.idTratamiento = idTratamiento;
    }

    public int getIdCostoTratamiento() {
        return idCostoTratamiento;
    }

    public void setIdCostoTratamiento(int idCostoTratamiento) {
        this.idCostoTratamiento = idCostoTratamiento;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @Override
    public String toString() {
        return fecha+": "+ costo;
    }
}
