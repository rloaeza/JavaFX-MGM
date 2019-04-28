package modelo;

public class Productos {
    private int idProducto;
    private int idTipoProducto;
    private String clave;
    private String nombre;
    private String descripcion;
    private int cantidadMinima;
    private String barCode;


    public Productos(int idProducto, int idTipoProducto, String clave, String nombre, String descripcion, int cantidadMinima, String barCode) {
        this.idProducto = idProducto;
        this.idTipoProducto = idTipoProducto;
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadMinima = cantidadMinima;
        this.barCode = barCode;
    }

    public Productos() {
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Override
    public String toString() {
        return clave+": "+nombre;
    }
}
