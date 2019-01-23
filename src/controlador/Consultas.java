package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;
import modelo.Tratamientos;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Consultas extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Paciente;

    @FXML
    private JFXListView<?> ListaDeHistorial;

    @FXML
    private JFXTextArea Diagnostico;

    @FXML
    private JFXListView<Tratamientos> ListaDeTratamientos;

    @FXML
    void agregarFoto(ActionEvent event) {

    }

    @FXML
    void agregarTratamiento(ActionEvent event) {

    }

    @FXML
    void cargarFoto(ActionEvent event) {

    }



    private void cargarDatos() throws IOException {

        ObservableList<Tratamientos> listaTratamientos = FXCollections.observableArrayList();

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


    @Override
    public void init() {
        try {
            cargarDatos();
            Paciente.setText(parametros.get(0).get("nombre").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
