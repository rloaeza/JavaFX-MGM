package controlador;

import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Configuraciones;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InicioAdministrador extends  Controlador implements Initializable {


    @FXML
    private Pane Pane;

    private Personal usuario;


    @FXML
    private JFXButton BarraFecha;

    @FXML
    private JFXButton BarraAlmacen;

    @FXML
    private JFXButton BarraPagosTratamientos;

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {

        if(Configuraciones.cajaAbierta)
            if(!cerrarCaja())
                return;




        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicio_sesion.fxml"));
        Stage escenario = (Stage) Pane.getScene().getWindow();
        escenario.setScene(new Scene(root, Funciones.ancho, Funciones.alto));



    }

    @FXML
    void salir(ActionEvent event) {
        //Platform.exit();
        System.exit(1);
    }



    @FXML
    void catalogoTiposDeProductos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);

        paramsVista.put("vista", "/vista/tipos_productos.fxml");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new controlador.TiposProductos());


    }
    @FXML
    void catalogoPersonal(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();

        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("idPersonal", Configuraciones.idPersonal);
        paramsVista.put("vista", "/vista/personal.fxml");


        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource("/vista/personal.fxml"), paramsVista, new controlador.Personal());
    }
    @FXML
    void catalogoPacientes(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("idPersonal", Configuraciones.idPersonal);

        paramsVista.put("vista", "/vista/pacientes.fxml");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Pacientes());
    }
    @FXML
    void catalogoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/tratamientos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Productos());
    }

    @FXML
    void costoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/costo_tratamientos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new CostoTratamientos());
    }

    @FXML
    void catalogoServicios(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/catalogo_servicios.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new CatalogoServicios());
    }

    @FXML
    void almacenEntradas(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/almacen_entrada.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new AlmacenEntrada());
    }

    @FXML
    void almacenSalidas(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/almacen_salida.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new AlmacenSalida());
    }

    @FXML
    void costoProductos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/costo_productos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new CostoProductos());
    }

    @FXML
    void relojChecadorEntradaSalida(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/reloj_checador.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new RelojChecador());
    }

    @FXML
    void VentaMostrador(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();


        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("idPersonal", Configuraciones.idPersonal);

        paramsVista.put("vista", "/vista/venta_mostrador2.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Citas());
    }

    @FXML
    void relojChecadorFijarHorarios(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/horarios.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Horarios());
    }

    @FXML
    void AgendarCitas(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/citas.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Citas());
    }

    @FXML
    void pagoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/deuda_tratamientos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new DeudaTratamientos());
    }


    @FXML
    void ReporteExistenciaAlmacen(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/vista_reporte.fxml" );
        paramsVista.put("clinicaDescripcion", Configuraciones.clinicaDescripcion );
        paramsVista.put("titulos", new String[]{"Producto:70:CENTER-LEFT", "Cantidad Minima:20:CENTER", "Existencia:10:CENTER"});
        paramsVista.put("pdf", "formatos/existencia.pdf");
        paramsVista.put("reporte", "Reportes: Existencia en almacen");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VistaReporte());
    }

    @FXML
    void catalogoCajas(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/cajas.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Cajas());
    }



    public void setUsuario(Personal p) {
        this.usuario = p;
    }

    public void init() {

        //Estado.setText(usuario.getProducto());
        ((Stage)Pane.getScene().getWindow()).setTitle(parametros.get(0).get("nombre").toString());





        /*
        Stage escenario = (Stage) Pane.getScene().getWindow();

        escenario.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(Pane.getChildren().size()>0) {
                AnchorPane root = (AnchorPane) Pane.getChildren().get(0);
                root.setPrefHeight(Pane.getHeight());
                root.setPrefWidth(Pane.getWidth());
            }
        });

        */

        Pane.getScene().addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            KeyCode kc = ke.getCode();
            try {
                if (kc == KeyCode.F1 ) {
                    VentaMostrador(null);
                    ke.consume();
                }
                else if( kc==KeyCode.F2) {
                    AgendarCitas(null);
                    ke.consume();
                }
                else if( kc==KeyCode.F5) {
                    ReporteExistenciaAlmacen(null);
                    ke.consume();
                }
                else if( kc==KeyCode.F6) {
                    relojChecadorEntradaSalida(null);
                    ke.consume();
                }

            }catch (IOException ioex) {

            }

        });



    }


    @FXML
    void f1(ActionEvent event) throws IOException {
        VentaMostrador(null);
    }
    @FXML
    void f2(ActionEvent event) throws IOException {
        AgendarCitas(null);
    }
    @FXML
    void f5(ActionEvent event) throws IOException {
        ReporteExistenciaAlmacen(null);
    }

    @FXML
    void f6(ActionEvent event) throws IOException {
        relojChecadorEntradaSalida(null);
    }

    @FXML
    void f7(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("vista", "/vista/venta_editar.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VentaEditar());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BarraFecha.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            BarraFecha.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        } ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();


        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(10000), ae -> {

            try {
                if(verificarAlmacen()) {
                    BarraAlmacen.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    BarraAlmacen.setBackground(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } ));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();

/*

        Timeline t3 = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            try {
                Map<String, Object> paramsVista = new LinkedHashMap<>();
                paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
                paramsVista.put("idPersonal", parametros.get(0).get("idPersonal"));
                paramsVista.put("clinicaDescripcion", parametros.get(0).get("clinicaDescripcion"));
                paramsVista.put("vista", "/vista/citas.fxml");
                Funciones.CargarVista((AnchorPane) Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Citas());
            }catch (IOException ioE) {

            }
        } ));
        t3.setCycleCount(1);
        t3.play();
*/


        Timeline tPagos = new Timeline(new KeyFrame(Duration.millis(10000), ae -> {

            try {
                if(verificarPagos()) {
                    BarraPagosTratamientos.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    BarraPagosTratamientos.setBackground(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } ));
        tPagos.setCycleCount(Animation.INDEFINITE);
        tPagos.play();





    }


    private boolean verificarAlmacen() throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Verificar minimos");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            return true;
        }
        return false;
    }

    private boolean verificarPagos() throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos recetados: Pagos pendientes");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            return true;
        }
        return false;
    }



    private boolean cerrarCaja() throws IOException {
        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Corte de caja");

        paramsAlert.put("vista", "/vista/corte_caja.fxml");

        Configuraciones.corteCajaValido = false;
        Configuraciones.abriendoCaja = false;
        Funciones.display(paramsAlert, getClass().getResource("/vista/corte_caja.fxml"), new CorteCaja() ,762, 418);
        return Configuraciones.corteCajaValido;
    }

}
