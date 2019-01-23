package modelo;

public class CitaPaciente {
    private int idCita;
    private String fecha;
    private boolean efectuada;
    private int idPaciente;
    private String nombre;

    public CitaPaciente() {
    }

    public CitaPaciente(int idCita, String fecha, boolean efectuada, int idPaciente, String nombre) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.efectuada = efectuada;
        this.idPaciente = idPaciente;
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

    public boolean isEfectuada() {
        return efectuada;
    }

    public void setEfectuada(boolean efectuada) {
        this.efectuada = efectuada;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        return fecha+ ": "+nombre;
    }
}
