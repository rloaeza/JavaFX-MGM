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
    private String huella;
    private String huella0;
    private String huella1;
    private String huella2;
    private String huella3;
    private String huella4;
    private int idClinica;
    private String titulo;

    private int tipo;

    public Personal() {
    }

    public Personal(int idPersonal, String nombre, String apellidos, String email, String telefono, String movil, String usuario, String clave, String huella0, String huella1, String huella2, String huella3, String huella4, int idClinica, int tipo) {
        this.idPersonal = idPersonal;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.movil = movil;
        this.usuario = usuario;
        this.clave = clave;
        this.huella0 = huella0;
        this.huella1 = huella1;
        this.huella2 = huella2;
        this.huella3 = huella3;
        this.huella4 = huella4;
        this.idClinica = idClinica;
        this.tipo = tipo;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public String getHuella() {
        return huella;
    }

    public void setHuella(String huella) {
        this.huella = huella;
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

    @Override
    public String toString() {
        return nombre+ " "+apellidos;
    }
}

