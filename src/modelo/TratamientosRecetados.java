package modelo;

public class TratamientosRecetados {
    private int idTratamientoRecetado;
    private int idHistorial;
    private int idTratamiento;
    private int sesiones;
    private String caducidad;
    private String nombre;

    public TratamientosRecetados(int idTratamientoRecetado, int idHistorial, int idTratamiento, int sesiones, String caducidad, String nombre) {
        this.idTratamientoRecetado = idTratamientoRecetado;
        this.idHistorial = idHistorial;
        this.idTratamiento = idTratamiento;
        this.sesiones = sesiones;
        this.caducidad = caducidad;
        this.nombre = nombre;
    }

    public TratamientosRecetados() {
    }

    public int getIdTratamientoRecetado() {
        return idTratamientoRecetado;
    }

    public void setIdTratamientoRecetado(int idTratamientoRecetado) {
        this.idTratamientoRecetado = idTratamientoRecetado;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getSesiones() {
        return sesiones;
    }

    public void setSesiones(int sesiones) {
        this.sesiones = sesiones;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

