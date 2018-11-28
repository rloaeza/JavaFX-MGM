package modelo;

public class TiposProductos {
    private int idTipoProducto;
    private int idClinica;
    private String clave;
    private String nombre;
    private String descripcion;

    public TiposProductos() {
    }

    public TiposProductos(int idTipoProducto, int idClinica, String clave, String nombre, String descripcion) {
        this.idTipoProducto = idTipoProducto;
        this.idClinica = idClinica;
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public int getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(int idClinica) {
        this.idClinica = idClinica;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return clave+": "+nombre;
    }
}
