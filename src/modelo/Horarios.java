package modelo;

public class Horarios {
    private int idHorarios;
    private String lunes;
    private String martes;
    private String miercoles;
    private String jueves;
    private String viernes;
    private String sabado;
    private String domingo;
    private int toleranciaEntrada;
    private int toleranciaSalida;

    private int idPersonal;
    private String nombre;
    private String apellidos;

    public Horarios(int idHorarios, String lunes, String martes, String miercoles, String jueves, String viernes, String sabado, String domingo, int toleranciaEntrada, int toleranciaSalida, int idPersonal, String nombre, String apellidos) {
        this.idHorarios = idHorarios;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
        this.toleranciaEntrada = toleranciaEntrada;
        this.toleranciaSalida = toleranciaSalida;
        this.idPersonal = idPersonal;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Horarios() {
    }

    public int getIdHorarios() {
        return idHorarios;
    }

    public void setIdHorarios(int idHorarios) {
        this.idHorarios = idHorarios;
    }

    public String getLunes() {
        return lunes;
    }

    public void setLunes(String lunes) {
        this.lunes = lunes;
    }

    public String getMartes() {
        return martes;
    }

    public void setMartes(String martes) {
        this.martes = martes;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(String miercoles) {
        this.miercoles = miercoles;
    }

    public String getJueves() {
        return jueves;
    }

    public void setJueves(String jueves) {
        this.jueves = jueves;
    }

    public String getViernes() {
        return viernes;
    }

    public void setViernes(String viernes) {
        this.viernes = viernes;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }

    public int getToleranciaEntrada() {
        return toleranciaEntrada;
    }

    public void setToleranciaEntrada(int toleranciaEntrada) {
        this.toleranciaEntrada = toleranciaEntrada;
    }

    public int getToleranciaSalida() {
        return toleranciaSalida;
    }

    public void setToleranciaSalida(int toleranciaSalida) {
        this.toleranciaSalida = toleranciaSalida;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return apellidos+", "+nombre;
    }
}
