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
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Cajas extends Controlador implements Initializable {




    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Caja> ListaDeCajas;

    @FXML
    private JFXTextField Caja;

    @FXML
    private JFXTextField Descripcion;

    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Caja: Actualizar");
        paramsJSON.put("nombre", Caja.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("idCaja", ListaDeCajas.getSelectionModel().getSelectedItem().getIdCaja());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Caja: Agregar");
        paramsJSON.put("nombre", Caja.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();

    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Caja: Eliminar");
        paramsJSON.put("idCaja", ListaDeCajas.getSelectionModel().getSelectedItem().getIdCaja());



        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();



    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Caja(-1, "","", -1,false));
        ListaDeCajas.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListaDeCajas.setOnMouseClicked(event -> {
            if(event.getClickCount()==1) {
                ListaDeCajas.getSelectionModel().getSelectedIndex();
                cargarDatosPantalla(ListaDeCajas.getSelectionModel().getSelectedItem());
            }
        });



    }
    private void cargarDatosPantalla(modelo.Caja p ) {
        Caja.setText(p.getNombre());
        Descripcion.setText(p.getDescripcion());

    }


    private void cargarDatos() throws IOException {

        ObservableList<modelo.Caja> listaDeCajas = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Caja: Lista");
        paramsJSON.put("idClinica", "1");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaDeCajas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Caja.class) );
            }
        }

        ListaDeCajas.setItems(listaDeCajas);

    }

    @Override
    public void init() {

    }
}
