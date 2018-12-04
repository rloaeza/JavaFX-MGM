package modelo;

public class Personal {
    private int idPersonal;
    private String nombre;
    private String apellidos;

    private String email;
    private String telefono;
    private String movil;
    private String usuario;
    private String clave;
    private int idClinica;

    public Personal() {
    }

    public Personal(int idPersonal, String nombre, String apellidos, String email, String telefono, String movil, String usuario, String clave, int idClinica) {
        this.idPersonal = idPersonal;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.movil = movil;
        this.usuario = usuario;
        this.clave = clave;
        this.idClinica = idClinica;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
        return nombre+ " "+apellidos;
    }
}
