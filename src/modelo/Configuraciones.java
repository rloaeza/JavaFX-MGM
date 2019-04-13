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
    public static double cierreCaja;
    public static double aperturaCaja;
    public static  int idCaja;

    /**
     * Monto insertado al realizar corte de caja
     */
    public static double corteCajaMonto;

    public static String corteCajaErrorNoClaveVendedor = "Falta clave de vendedor";
    public static String corteCajaErrorNoSupervisorSeleccionado = "Falta seleccionar un supervisor";
    public static String corteCajaErrorNoClaveSupervisor = "Falta clave de supervisor";
    public static String corteCajaErrorNoMonto = "Falta monto inicial";
    public static String corteCajaErrorNoCajaSeleccionada = "Falta seleccionar caja";
}
