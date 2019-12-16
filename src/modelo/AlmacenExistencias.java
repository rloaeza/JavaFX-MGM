package modelo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class AlmacenExistencias extends RecursiveTreeObject<AlmacenExistencias> {
    private String idProducto;
    private String Clave;
    private String Producto;
    private String Clase;
    private String Existencia;
    private String idProduct;

    public AlmacenExistencias(String idProducto, String clave, String producto, String clase, String existencia) {
        this.idProducto = idProducto;
        Clave = clave;
        Producto = producto;
        Clase = clase;
        Existencia = existencia;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdProducto() {

        return idProducto==null?"":idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getClave() {
        return Clave==null?"":Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getProducto() {
        return Producto==null?"":Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getClase() {
        return Clase==null?"":Clase;
    }

    public void setClase(String clase) {
        Clase = clase;
    }

    public String getExistencia() {
        return Existencia==null?"":Existencia;
    }

    public void setExistencia(String existencia) {
        Existencia = existencia;
    }

    @Override
    public String toString() {
        return getProducto()+": "+getClave();
    }
}
