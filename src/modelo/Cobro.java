package modelo;

public class Cobro {
    private int idVentaCobro;
    private int idVentaProductos;
    private int formaPago;
    private double monto;
    private String descripcion;

    public Cobro(int idVentaCobro, int idVentaProductos, int formaPago, double monto, String descripcion) {
        this.idVentaCobro = idVentaCobro;
        this.idVentaProductos = idVentaProductos;
        this.formaPago = formaPago;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public int getIdVentaCobro() {
        return idVentaCobro;
    }

    public void setIdVentaCobro(int idVentaCobro) {
        this.idVentaCobro = idVentaCobro;
    }

    public int getIdVentaProductos() {
        return idVentaProductos;
    }

    public void setIdVentaProductos(int idVentaProductos) {
        this.idVentaProductos = idVentaProductos;
    }

    public int getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(int formaPago) {
        this.formaPago = formaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        if(getFormaPago()==1) {
            return  "Efectivo: " + Funciones.valorAmoneda(Funciones.fixN(getMonto(), 2) );
        }
        else if(getFormaPago()==2) {
            return "Transaccion (" + getDescripcion() + "): "+ Funciones.valorAmoneda(Funciones.fixN(getMonto(), 2) );
        }
        else if(getFormaPago()==3) {
            return "Depósito (" + getDescripcion() + "): "+ Funciones.valorAmoneda(Funciones.fixN(getMonto(), 2) );
        }
        else if(getFormaPago()==4) {
            return "Transferencia (" + getDescripcion() + "): "+ Funciones.valorAmoneda(Funciones.fixN(getMonto(), 2) );
        }
        else {
            return "Cambio: "+ Funciones.valorAmoneda(Funciones.fixN(getMonto(), 2) );
        }
    }
}
