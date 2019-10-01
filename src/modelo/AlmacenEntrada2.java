package modelo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class AlmacenEntrada2 extends RecursiveTreeObject<AlmacenEntrada2> {

    private int idEntrada;
    private int idProducto;
    private String producto;
    private int existencia;
    private int cantidad;
    private int autorizado;
    private String fecha;


    public AlmacenEntrada2(int idEntrada, int idProducto, String producto, int existencia, int cantidad) {
        this.idEntrada = idEntrada;
        this.idProducto = idProducto;
        this.producto = producto;
        this.existencia = existencia;
        this.cantidad = cantidad;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}