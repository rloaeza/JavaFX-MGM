package modelo;

public class CitaPaciente {
    private int idCita;
    private String fecha;
    private String nombre;

    public CitaPaciente() {
    }

    public CitaPaciente(int idCita, String fecha, String nombre) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.nombre = nombre;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
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
        return fecha+ ": "+nombre;
    }
}
