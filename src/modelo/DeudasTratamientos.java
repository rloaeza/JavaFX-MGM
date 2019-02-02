package modelo;

public class DeudasTratamientos {
    private int idTratamientoRecetado;
    private double cantidad;
    private int idPaciente;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String movil;
    private String clave;
    private int idClinica;

    public DeudasTratamientos(int idTratamientoRecetado, double cantidad, int idPaciente, String nombre, String apellidos, String email, String telefono, String movil, String clave, int idClinica) {
        this.idTratamientoRecetado = idTratamientoRecetado;
        this.cantidad = cantidad;
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.movil = movil;
        this.clave = clave;
        this.idClinica = idClinica;
    }

    public DeudasTratamientos() {
    }

    public int getIdTratamientoRecetado() {
        return idTratamientoRecetado;
    }

    public void setIdTratamientoRecetado(int idTratamientoRecetado) {
        this.idTratamientoRecetado = idTratamientoRecetado;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(int idClinica) {
        this.idClinica = idClinica;
    }

    @Override
    public String toString() {
        return apellidos+", "+nombre + " ("+cantidad+")";
    }
}