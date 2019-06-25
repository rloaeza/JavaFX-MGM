package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Configuraciones;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VistaReportes extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label titulo;

    @FXML
    void aceptar(ActionEvent event) throws IOException {
        quitarVistas();
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }

    @FXML
    void reporteExistencias(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/vista_reporte_existencia.fxml");
        paramsVista.put("Titulo", Configuraciones.reporteEncabezado+"Reporte: Existencia");
        paramsVista.put("titulos", new String[]{"Producto:70:CENTER-LEFT", "Cantidad Minima:20:CENTER", "Existencia:10:CENTER"});
        paramsVista.put("pdf", "formatos/existencia.pdf");
        paramsVista.put("reporte", "Reportes: Existencia en almacen");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VistaReporteExistencia());
    }
    @FXML
    void reporteExistenciasVerificar(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/vista_reporte_existencia_verificar.fxml");
        paramsVista.put("Titulo", Configuraciones.reporteEncabezado+"Reporte: Existencia");
        paramsVista.put("Descripcion", "Reportes: Existencia en almacen verificar");

        paramsVista.put("titulos", new String[]{"Clave:20:CENTER", "Producto:70:CENTER-LEFT", "Existencia:20:CENTER", "Fisico:10:CENTER", "Diferencias:10:CENTER"});
        paramsVista.put("pdf", "formatos/reporte_existencias_verificar.pdf");
        paramsVista.put("reporte", "Reportes: Existencia en almacen verificar");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VistaReporteExistencia());
    }
    @FXML
    void reporteVentasGenerales(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/vista_reporte_general.fxml" );
        paramsVista.put("Titulo", Configuraciones.reporteEncabezado+"Reporte: Ventas general");
        paramsVista.put("titulos", new String[]{"Id:10:CENTER","Vendedor:70:CENTER-LEFT", "Ventas:20:CENTER"});
        paramsVista.put("pdf", "formatos/reporte_general.pdf");
        paramsVista.put("reporte", "Reporte: General");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VistaReporteGeneral());
    }

    @FXML
    void reporteVentasGeneralesCompleto(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/vista_reporte_general_completo.fxml" );
        paramsVista.put("Titulo", Configuraciones.reporteEncabezado+"Reporte: Ventas general completo");
        paramsVista.put("titulos", new String[]{"Id Venta:10:CENTER","Paciente:40:CENTER-LEFT","Tratamiento:20:CENTER", "Producto:20:CENTER", "Efectivo:20:CENTER", "Tarjeta:20:CENTER", "Total:20:CENTER"});
        paramsVista.put("pdf", "formatos/reporte_venta.pdf");
        paramsVista.put("reporte", "Reporte: General completo");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VistaReporteGeneralCompleto());
    }

    @FXML
    void reporteVentasPorPersonal(ActionEvent event) throws IOException {

        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", Configuraciones.idClinica);
        paramsVista.put("vista", "/vista/vista_reporte_personal.fxml" );
        paramsVista.put("Titulo", Configuraciones.reporteEncabezado+"Reporte: Personal");
        paramsVista.put("titulos", new String[]{"Fecha:30:CENTER","Id Venta:20:CENTER","Cliente:70:CENTER-LEFT", "Productos:20:CENTER", "Total:20:CENTER"});
        paramsVista.put("pdf", "formatos/reporte_personal.pdf");
        paramsVista.put("reporte", "Reporte: Personal");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VistaReportePersonal());

    }

    @Override
    public void init() {



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
