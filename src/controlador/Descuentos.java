package controlador;

import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import modelo.Datos;
import modelo.Funciones;
import modelo.PrinterService;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Descuentos extends Controlador implements Initializable {




    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Descuento> ListaDeDescuentos;

    @FXML
    private JFXTextField Cantidad;

    @FXML
    private JFXTextField Descripcion;


    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Descuentos: Actualizar");
        paramsJSON.put("cantidad", Cantidad.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("idDescuento", ListaDeDescuentos.getSelectionModel().getSelectedItem().getIdDescuento());


        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        Datos.cargarDecuentos();
        cargarDatos();
        limpiar(null);
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Descuentos: Agregar");
        paramsJSON.put("cantidad", Cantidad.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        Datos.cargarCajas();
        cargarDatos();
        limpiar(null);

    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Descuentos: Eliminar");
        paramsJSON.put("idDescuento", ListaDeDescuentos.getSelectionModel().getSelectedItem().getIdDescuento());



        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        Datos.cargarCajas();
        cargarDatos();

        limpiar(null        );

    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Descuento(-1, -1,0, ""));
        ListaDeDescuentos.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cargarDatos();

        ListaDeDescuentos.setOnMouseClicked(event -> {
            if(event.getClickCount()==1) {
                ListaDeDescuentos.getSelectionModel().getSelectedIndex();
                cargarDatosPantalla(ListaDeDescuentos.getSelectionModel().getSelectedItem());
            }
        });



    }
    private void cargarDatosPantalla(modelo.Descuento p ) {
        Cantidad.setText(p.getCantidad()+"");
        Descripcion.setText(p.getDescripcion());

    }


    private void cargarDatos() {

        ListaDeDescuentos.setItems(Datos.descuentos);

    }


    @Override
    public void init() {

    }
}
