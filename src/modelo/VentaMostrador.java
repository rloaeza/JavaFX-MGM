package modelo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class VentaMostrador extends RecursiveTreeObject<VentaMostrador> {
    private int idProducto;

    private int cant;
    private String cantidad;
    private String producto;
    private double costo;
    private double total;

    public VentaMostrador(int idProducto, int cantidad, String producto, double costo) {
        this.idProducto = idProducto;
        this.cant = cantidad;
        this.producto = producto;
        this.costo = costo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCantidad() {
        return String.valueOf(cant);
    }


    public void setCantidad(int cantidad) {
        this.cant = cantidad;
    }

    public String getProducto() {
        return producto;
    }


    public void setProducto(String producto) {
        this.producto= producto;
    }

    public double getCosto() {
        return costo;
    }



    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTotal() {
        return cant*costo;
    }

    public int getCant(){
        return cant;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}


