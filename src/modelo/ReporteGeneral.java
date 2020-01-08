package modelo;

public class ReporteGeneral extends Personal{


    private double total;

    public ReporteGeneral(int idPersonal, String nombre, String apellidos, String email, String telefono, String movil, String usuario, String clave, String huella0, String huella1, String huella2, String huella3, String huella4, int idClinica, int tipo, int inhabilitado, double total) {
        super(idPersonal, nombre, apellidos, email, telefono, movil, usuario, clave, huella0, huella1, huella2, huella3, huella4, idClinica, tipo, inhabilitado);
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

