package modelo;

public class Historial {
    private int idHistorial;
    private String fecha;
    private String descripcion;
    private int idPaciente;

    public Historial(int idHistorial, String fecha, String descripcion, int idPaciente) {
        this.idHistorial = idHistorial;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.idPaciente = idPaciente;
    }

    public Historial() {
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        return fecha;
    }
}
