package modelo;

public class Clinica {

    private int idClinica;
    private String nombre;
    private String calle;
    private String colonia;
    private String cp;
    private String telefono;
    private String estado;
    private String pais;
    private String titulo;
    private String descripcion;

    public Clinica(int idClinica, String nombre, String calle, String colonia, String cp, String telefono, String estado, String pais, String titulo, String descripcion) {
        this.idClinica = idClinica;
        this.nombre = nombre;
        this.calle = calle;
        this.colonia = colonia;
        this.cp = cp;
        this.telefono = telefono;
        this.estado = estado;
        this.pais = pais;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(int idClinica) {
        this.idClinica = idClinica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
