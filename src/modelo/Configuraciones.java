package modelo;

public class Configuraciones {
    public static int idPersonal;
    public static String nombrePersonal;
    public static String clavePersonal;
    public static int idClinica;
    public static String clinicaDescripcion;


    public static String[] formasPago = new String[] {"Efectivo", "Crédito / Débito"};
    public static String[] tiposUsuarios = new String[] {"Administrador", "Supervisor", "Vendedor", "Cosmeatra"};


    public static boolean ventaAceptada;


    public static boolean corteCajaValido;
    public static String corteCajaAbrir = "Abriendo caja";
    public static String corteCajaCerrar = "Cerrando caja";
    public static boolean abriendoCaja;
    public static boolean cajaAbierta;
    public static double cierreCaja;
    public static double aperturaCaja;
    public static  int idCaja;



    public static double corteCajaMonto;
    public static String corteCajaErrorNoClaveVendedor = "Falta clave de vendedor";
    public static String corteCajaErrorNoSupervisorSeleccionado = "Falta seleccionar un supervisor";
    public static String corteCajaErrorNoClaveSupervisor = "Falta clave de supervisor";
    public static String corteCajaErrorNoMonto = "Falta monto inicial";
    public static String corteCajaErrorNoCajaSeleccionada = "Falta seleccionar caja";


    public static String formaPagoEfectivoRecibido = "Cantidad de efectivo recibido";
    public static String formaPagoIdTransaccion = "Id de transacción";
    public static String formaPagoFaltaEfectivo = "Falta efectivo";
    public static String formaPagoCambio = "Cambio: ";
    public static double formaPagoMonto;


    public static double ventaMostradorSubTotal;
    public static double ventaMostradorTotal;

    public static double ventaMostradorActual;





    //Finger print
    public static  long mhDevice = 0;
    public static  long mhDB = 0;
    public static  byte[] imgbuf = null;
    public static  byte[] template = new byte[2048];
    public static  int[] templateLen = new int[1];
    public static  int ret = 0;
    //the width of fingerprint image
    public static int fpWidth = 0;
    //the height of fingerprint image
    public static int fpHeight = 0;
    public static boolean bRegister = false;
    //Identify
    public static boolean bIdentify = true;
    //finger id
    public static int iFid = 1;

    public static int nFakeFunOn = 1;
    //must be 3
    public static int enroll_cnt = 3;
    //the index of pre-register function
    public static  int enroll_idx = 0;
    //for verify test
    //the length of lastRegTemp
    public static  int cbRegTemp = 0;

    public static boolean fpActivo;

}
