package modelo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class VentaMostrador extends RecursiveTreeObject<VentaMostrador> {
    private int cant;
    private String cantidad;
    private String producto;
    private double costo;
    private double total;

    public VentaMostrador(int cantidad, String producto, double costo) {
        this.cant = cantidad;
        this.producto = producto;
        this.costo =costo;
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


    public void setTotal(double total) {
        this.total = total;
    }


}


