package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;
import modelo.Productos;
import modelo.Servicios;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CatalogoServicios extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Servicios> ListaDeServicios;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextArea Descripcion;

    @FXML
    private JFXTextField Costo;

    @FXML
    private Label Titulo;

    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Servicios: Actualizar");
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("costo", Costo.getText());
        paramsJSON.put("idServicio", ListaDeServicios.getSelectionModel().getSelectedItem().getIdServicio());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Servicios: Agregar");
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("costo", Costo.getText());
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Servicios: Eliminar");
        paramsJSON.put("idServicio", ListaDeServicios.getSelectionModel().getSelectedItem().getIdServicio());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new Servicios());
        ListaDeServicios.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }
    private void cargarDatosPantalla(Servicios s ) {
        Nombre.setText(s.getNombre());
        Descripcion.setText(s.getDescripcion());
        Costo.setText(String.valueOf(s.getCosto()));
    }
    private void cargarDatos() throws IOException {
        ObservableList<Servicios> listaServicios = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Servicios: Lista");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica").toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaServicios.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Servicios.class) );
            }
        }

        ListaDeServicios.setItems(listaServicios);
    }

    @Override
    public void init() {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListaDeServicios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cargarDatosPantalla(ListaDeServicios.getSelectionModel().getSelectedItem());
            }
        });
    }
}
