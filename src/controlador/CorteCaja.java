package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Configuraciones;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CorteCaja extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTextField Monto;

    @FXML
    private JFXComboBox<Personal> Vendedor;

    @FXML
    private JFXPasswordField VendedorClave;

    @FXML
    private JFXComboBox<Personal> Supervisor;

    @FXML
    private JFXPasswordField SupervisorClave;

    @FXML
    void Aceptar(ActionEvent event) {

    }

    @FXML
    void Cancelar(ActionEvent event) {
        Configuraciones.corteCajaValido=false;
        cerrar();
    }

    private void cargarDatos() throws IOException {
        ObservableList<Personal> listaPersonalVentas = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Lista con tipo");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        paramsJSON.put("tipo", 2);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonalVentas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Personal.class) );
            }
        }
        Vendedor.setItems(listaPersonalVentas);


        ObservableList<Personal> listaPersonalSupervisor = FXCollections.observableArrayList();

        paramsJSON.clear();
        paramsJSON.put("Actividad", "Personal: Lista con tipo");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        paramsJSON.put("tipo", 1);
        rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonalSupervisor.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Personal.class) );
            }
        }
        Supervisor.setItems(listaPersonalSupervisor);

    }
    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
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

    }
}

