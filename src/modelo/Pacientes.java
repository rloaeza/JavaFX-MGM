package modelo;

public class Pacientes {
    private int idPaciente;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String movil;
    private String clave;
    private String huella0;
    private String huella1;
    private String huella2;
    private String huella3;
    private String huella4;
    private int idClinica;

    public Pacientes() {
    }

    public Pacientes(int idPaciente, String nombre, String apellidos, String email, String telefono, String movil, String clave, String huella0, String huella1, String huella2, String huella3, String huella4, int idClinica) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.movil = movil;
        this.clave = clave;
        this.huella0 = huella0;
        this.huella1 = huella1;
        this.huella2 = huella2;
        this.huella3 = huella3;
        this.huella4 = huella4;
        this.idClinica = idClinica;
    }

    public String getHuella0() {
        return huella0;
    }

    public void setHuella0(String huella0) {
        this.huella0 = huella0;
    }

    public String getHuella1() {
        return huella1;
    }

    public void setHuella1(String huella1) {
        this.huella1 = huella1;
    }

    public String getHuella2() {
        return huella2;
    }

    public void setHuella2(String huella2) {
        this.huella2 = huella2;
    }

    public String getHuella3() {
        return huella3;
    }

    public void setHuella3(String huella3) {
        this.huella3 = huella3;
    }

    public String getHuella4() {
        return huella4;
    }

    public void setHuella4(String huella4) {
        this.huella4 = huella4;
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
        return nombre +" "+ apellidos;
    }
}
