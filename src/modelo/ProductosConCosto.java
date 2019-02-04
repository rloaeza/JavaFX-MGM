package modelo;

public class ProductosConCosto {
    private int idProducto;
    private int idTipoProducto;
    private String clave;
    private String nombre;
    private String descripcion;
    private double cantidadMinima;
    private String barCode;

    private double precio;
    private int idPrecioProducto;

    public ProductosConCosto(int idProducto, int idTipoProducto, String clave, String nombre, String descripcion, double cantidadMinima, String barCode, double precio, int idPrecioProducto) {
        this.idProducto = idProducto;
        this.idTipoProducto = idTipoProducto;
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadMinima = cantidadMinima;
        this.barCode = barCode;
        this.precio = precio;
        this.idPrecioProducto = idPrecioProducto;
    }

    public ProductosConCosto() {
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

    public double getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(double cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdPrecioProducto() {
        return idPrecioProducto;
    }

    public void setIdPrecioProducto(int idPrecioProducto) {
        this.idPrecioProducto = idPrecioProducto;
    }

    @Override
    public String toString() {
        return clave+": "+nombre;
    }
}
