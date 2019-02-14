package modelo;

public class ReporteExistenciaAlmacen  extends  Reporte{
    private String producto;
    private String cantidadMinima;
    private String existencia;

    public ReporteExistenciaAlmacen(String producto, String cantidadMinima, String existencia) {
        this.producto = producto;
        this.cantidadMinima = cantidadMinima;
        this.existencia = existencia;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(String cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }
}
