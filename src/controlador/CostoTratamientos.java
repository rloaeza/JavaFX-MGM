package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CostoTratamientos extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Tratamientos> ListaDeTratamientos;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextField Costo;

    @FXML
    private JFXListView<modelo.CostoTratamientos> ListaDeCostos;



    @FXML
    private Label Titulo;


    @FXML
    void actualizar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Costo Tratamientos: Actualizar");
        paramsJSON.put("costo", Costo.getText());
        paramsJSON.put("idCostoTratamiento", ListaDeCostos.getSelectionModel().getSelectedItem().getIdCostoTratamiento());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPrecios(ListaDeTratamientos.getSelectionModel().getSelectedItem().getIdTratamiento());

    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Costo Tratamientos: Agregar");
        paramsJSON.put("costo", Costo.getText());
        paramsJSON.put("idTratamiento", ListaDeTratamientos.getSelectionModel().getSelectedItem().getIdTratamiento());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPrecios(ListaDeTratamientos.getSelectionModel().getSelectedItem().getIdTratamiento());
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Costo Tratamientos: Eliminar");
        paramsJSON.put("idCostoTratamiento", ListaDeCostos.getSelectionModel().getSelectedItem().getIdCostoTratamiento());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPrecios(ListaDeTratamientos.getSelectionModel().getSelectedItem().getIdTratamiento());
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Tratamientos(-1, "","", -1,-1, -1));
        ListaDeTratamientos.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new TiposProductos());
    }


    @Override
    public void init() {

        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cargarDatosPantalla(modelo.Tratamientos p ) {
        Nombre.setText(p.getNombre());

        try {
            cargarPrecios(p.getIdTratamiento());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cargarDatos() throws IOException {

        ObservableList<modelo.Tratamientos> listaTratamientos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Costo Tratamientos: Lista tratamientos");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaTratamientos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Tratamientos.class) );
            }
        }

        ListaDeTratamientos.setItems(listaTratamientos);

    }

    private void cargarPrecios(int idTratamiento) throws IOException {

        ObservableList<modelo.CostoTratamientos> listaCostos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Costo Tratamientos: Lista costos de tratamientos");
        paramsJSON.put("idTratamiento", idTratamiento);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaCostos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.CostoTratamientos.class) );
            }
        }

        ListaDeCostos.setItems(listaCostos);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ListaDeTratamientos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cargarDatosPantalla(ListaDeTratamientos.getSelectionModel().getSelectedItem());
            }
        });
    }
}
