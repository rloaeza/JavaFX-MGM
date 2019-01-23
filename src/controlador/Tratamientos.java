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

public class Tratamientos extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Tratamientos> ListaDeTratamientos;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextArea Descripcion;

    @FXML
    private JFXTextField Sesiones;

    @FXML
    private JFXTextField Caducidad;

    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos: Actualizar");
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("sesiones", Sesiones.getText());
        paramsJSON.put("caducidad", Caducidad.getText());
        paramsJSON.put("idTratamiento", ListaDeTratamientos.getSelectionModel().getSelectedItem().getIdTratamiento());

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos: Agregar");
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("sesiones", Sesiones.getText());
        paramsJSON.put("caducidad", Caducidad.getText());
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos: Eliminar");
        paramsJSON.put("idTratamiento", ListaDeTratamientos.getSelectionModel().getSelectedItem().getIdTratamiento());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Tratamientos(-1,"", "",-1, -1, -1));
        ListaDeTratamientos.getSelectionModel().clearSelection();

    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }

    private void cargarDatos() throws IOException {

        ObservableList<modelo.Tratamientos> listaTratamientos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos: Lista");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica").toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaTratamientos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Tratamientos.class) );
            }
        }

        ListaDeTratamientos.setItems(listaTratamientos);

    }

    private void cargarDatosPantalla(modelo.Tratamientos t ) {
        Nombre.setText(t.getNombre());
        Descripcion.setText(t.getDescripcion());
        Sesiones.setText(t.getSesiones()<0?"":t.getSesiones()+"");
        Caducidad.setText(t.getCaducidad()<0?"":t.getCaducidad()+"");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListaDeTratamientos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cargarDatosPantalla(ListaDeTratamientos.getSelectionModel().getSelectedItem());
            }
            else {
                try {
                    Map<String,Object> paramsView = new LinkedHashMap<>();
                    paramsView.put("TituloTratamiento", ListaDeTratamientos.getSelectionModel().getSelectedItem().getNombre());
                    paramsView.put("idTratamiento", ListaDeTratamientos.getSelectionModel().getSelectedItem().getIdTratamiento());
                    paramsView.put("vista", "/vista/detalle_tratamientos.fxml");

                    Funciones.CargarVista(Pane, getClass().getResource("/vista/detalle_tratamientos.fxml"), paramsView, new DetalleTratamientos());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void init() {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
