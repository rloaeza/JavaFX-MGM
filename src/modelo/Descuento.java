package modelo;

public class Descuento {
    private int idDescuento;
    private int idClinica;
    private int cantidad;
    private String descripcion;

    public Descuento(int idDescuento, int idClinica, int cantidad, String descripcion) {
        this.idDescuento = idDescuento;
        this.idClinica = idClinica;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public int getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(int idDescuento) {
        this.idDescuento = idDescuento;
    }

    public int getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(int idClinica) {
        this.idClinica = idClinica;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getDescripcion()+" ( desc: "+getCantidad()+"% )";
    }
}
