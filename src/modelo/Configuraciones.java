package modelo;

import javafx.collections.ObservableList;

import java.net.URL;

public class Configuraciones {
    public static int versionId=25;
    public static String versionText = "Código: 2019.";
    public static int versionIdActualizar=-1;
    public static String versionActualizar;
    public static int idPersonal;
    public static String nombrePersonal;
    public static String clavePersonal;
    public static int idClinica;
    public static String nombreClinica;
    public static String clinicaDescripcion;


    public static boolean clinicaOK = false;

    public static String[] formasPago = new String[] {"Efectivo", "Crédito / Débito"};
    public static final  int  tipoAdministrador = 0;
    public static final int tipoSupervisor = 1;
    public static final int tipoVendedor = 2;
    public static final int tipoCosmeatra = 3;
    public static int tipoUsuarioActivo = -1;

    public static int[] tiposUsuarioInt = new int[]{tipoAdministrador, tipoSupervisor, tipoVendedor, tipoCosmeatra};
    public static String[] tiposUsuarios = new String[] {"Administrador", "Supervisor", "Vendedor", "Cosmeatra"};


    public static String inicioVendedor = "/vista/inicio_venta.fxml";
    public static String inicioAdministrador = "/vista/inicio_administrador.fxml";

    public static String panelInicial = "/vista/inicio_resumen.fxml";
    public static String panelAnterior = "";




    public static String[] motd = new String[]{
            "Nada es particularmente difícil si lo divides en pequeños trabajos",
            "La confianza en sí mismo es el primer secreto del éxito",
            "El triunfo no está en vencer siempre, sino en nunca desanimarse",
            "El trabajo que nunca se empieza es el que tarda más en finalizarse",
            "El éxito en la vida consiste en siempre seguir adelante",
            "Si puedes soñarlo, puedes hacerlo",
            "El éxito es la suma de pequeños esfuerzos repetidos un día sí y otro también",
            "Si crees que puedes, ya estás a medio camino"
    };




    //public static String impresoraTicket = "EC-PM-80360";
    public static String impresoraTicket = "";
    public static String impresoraReporte= "";



    //public static String impresoraTicket = "GP-5890X Series";
    public static int ticketMaxLinea = 50;
    public static String ticketTituloClinica =
                    "MGM \n" +
                    "RFC: MANL-810923-R37 Calle: Artilleros 1847 #1585 Colonia: Chapultepec Oriente" +
                    " C.P.: 58260, Tel: 527-25-23";


    public static String ticketTituloClinicaThermal =
            "                MGM PLUS SA de CV\n" +
            "RFC: MPL940701T9A\n"+
            "Calle: Boulevard Adolfo Ruiz Cortines #18\n" +
            "Colonia: Lomas de Atizapan  CP: 52977\n" +
            "Atizapan Edo de México\n\n" +

            "                   MGM Morelia\n" +
            "RFC: MPL940701T9A\n"+
            "Calle: Artilleros 1847 #1585 \n" +
            "Colonia: Chapultepec Oriente CP: 58260\n" +
            "Morelia Michoacán Tel: 4436887322\n\n";


    public static String ticketCorteCaja=
            "              MGM \n\n" +
            "Tipo:       $tipo$\n" +
            "Caja:       $caja$\n" +
            "Fecha:      $fecha$\n" +
            "Vendedor:   $vendedor$\n" +
            "Supervisor: $supervisor$\n" +
            "Monto:      $monto$\n\n\n\n\n";


    //"12  Crema exfoliante    25   2003";
    public static int ticketCant = 5;
    public static int ticketProducto = 23;
    public static int ticketCU  = 7;
    public static int ticketTotal = 13;


    public static boolean ventaAceptada;


    public static boolean corteCajaValido;
    public static String corteCajaAbrir = "Abriendo caja";
    public static String corteCajaCerrar = "Cerrando caja";
    public static boolean abriendoCaja;
    public static boolean cajaAbierta;
    public static double cierreCaja;
    public static double cierreCajaE;
    public static double cierreCajaT;
    public static double aperturaCaja;
    public static  int idCaja;



    public static double corteCajaMonto;
    public static String corteCajaErrorNoClaveVendedor = "Falta clave de vendedor";
    public static String corteCajaErrorNoSupervisorSeleccionado = "Falta seleccionar un supervisor";
    public static String corteCajaErrorNoClaveSupervisor = "Falta clave de supervisor";
    public static String corteCajaErrorNoMonto = "Falta monto inicial";
    public static String corteCajaErrorNoCajaSeleccionada = "Falta seleccionar caja";
    public static String corteCajaErrorClavesIncorrectas = "Las claves son incorrectas";

    public static String corteCajaErrorClaveIncorrectaVenta = "Clave de vendedor errónea.";
    public static String corteCajaErrorClaveIncorrectaSupervisor = "Clave de supervisor errómea";


    public static String formaPagoMontoRecibido = "Monto recibido";
    public static String formaPagoIdTransaccion = "Id de transacción";

    public static String formaPagoFaltaEfectivo = "Falta efectivo";
    public static String formaPagoFaltaTransaccion = "Falta transacción";
    public static String formaPagoVerificar = "Verifique los valores";
    public static String formaPagoCambio = "Cambio: ";
    public static double formaPagoMonto;
    public static ObservableList<Cobro> formaPagoCobros;
    public static double formaPagoMontoCambio;





    public static double ventaMostradorSubTotal;
    public static double ventaMostradorTotal;

    public static double ventaMostradorActual;





    public static String ventaEditarNoMasProductos = "Error, no se pueden agregar mas productos";


    //Finger print
    public static  long mhDevice = 0;
    public static  long mhDB = 0;
    public static  byte[] imgbuf = null;
    public static  byte[] template = new byte[2048];
    public static byte[][] regtemparray = new byte[3][2048];
    public static byte[] lastRegTemp = new byte[2048];

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

    public static boolean fpActivo=false;

    public static String fpSTR;
    public static String fpEliminar = "ELIMINAR";
    public static String fpColocarDedo1 = "Presione 3 veces el mismo dedo";
    public static String fpColocarDedo2 = "Presione 2 veces el mismo dedo";
    public static String fpColocarDedo3 = "Presione 1 vez el mismo dedo";
    public static String fpColocarDedoOK = "Huella capturada";
    public static String fpColocarDedoFail= "Error al capturar huella";
    public static String fpColocarDedoDiferente= "Debe ser el mismo dedo";




    public static double idVistaActual;



    public static boolean supervisorOK;
    public static String MAC;


    public static String reporteEncabezado =
            "MGM PLUS SA de CV\n" +
            "\n";



    public static int lineasPorReporte = 23;

    public static URL urlAlertBox;
    public static double descuento;
    public static int idVentaProductos;
}
