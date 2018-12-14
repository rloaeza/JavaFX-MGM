package modelo;

public class AlmacenProductos {
    private int idProducto;
    private int idTipoProducto;
    private String clave;
    private String nombre;
    private String descripcion;
    private double cantidadMinima;
    private double cantidad;
    private String barCode;


    public AlmacenProductos(int idProducto, int idTipoProducto, String clave, String nombre, String descripcion, double cantidadMinima, String barCode) {
        this.idProducto = idProducto;
        this.idTipoProducto = idTipoProducto;
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadMinima = cantidadMinima;
        this.barCode = barCode;
    }

    public AlmacenProductos() {
    }

    public double getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(double cantidadMinima) {
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
