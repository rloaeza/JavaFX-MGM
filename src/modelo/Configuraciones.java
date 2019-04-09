package modelo;

public class Configuraciones {
    public static int idPersonal;
    public static String nombrePersonal;
    public static String clavePersonal;
    public static int idClinica;
    public static String clinicaDescripcion;


    public static String[] formasPago = new String[] {"Efectivo", "Crédito / Débito"};
    public static String[] tiposUsuarios = new String[] {"Administrador", "Supervisor", "Vendedor", "Cosmeatra"};

    public static double ventaPago;
    public static boolean ventaAceptada;


    public static boolean corteCajaValido;
    public static String corteCajaAbrir = "Abriendo caja";
    public static String corteCajaCerrar = "Cerrando caja";
    public static boolean abriendoCaja;
    public static boolean cajaAbierta;
}
