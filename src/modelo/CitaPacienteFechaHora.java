package modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CitaPacienteFechaHora {
    private int idCita;
    private String fecha;
    private boolean efectuada;
    private int idPaciente;
    private String nombre;
    private String apellidos;



    public CitaPacienteFechaHora(int idCita, String fecha, boolean efectuada, int idPaciente, String nombre, String apellidos) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.efectuada = efectuada;
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEfectuada() {
        return efectuada;
    }

    public void setEfectuada(boolean efectuada) {
        this.efectuada = efectuada;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        String f="";
        try {
            Date d = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(fecha);
            f=new SimpleDateFormat("dd/mm/yyyy  hh:mm a").format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return f;
    }
}
