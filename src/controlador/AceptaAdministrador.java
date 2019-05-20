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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Personal;
import modelo.*;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AceptaAdministrador extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;


    @FXML
    private Label TituloCaja;





    @FXML
    private JFXComboBox<Personal> Supervisor;

    @FXML
    private JFXPasswordField SupervisorClave;

    @FXML
    private Label Error;

    @FXML
    void AceptarEnter(ActionEvent event) throws IOException {

    }

    @FXML
    void Aceptar(ActionEvent event) throws IOException {
        Error.setVisible(true);

        if(Supervisor.getSelectionModel().getSelectedIndex()==-1) {
            Error.setText(Configuraciones.corteCajaErrorNoSupervisorSeleccionado);
            return;
        }
        if(SupervisorClave.getText().isEmpty()) {
            Error.setText(Configuraciones.corteCajaErrorNoClaveSupervisor);
            return;
        }

        //if(VendedorClave.getText().equals(Configuraciones.clavePersonal) && SupervisorClave.getText().equals(Supervisor.getValue().getClave())) {
        if( SupervisorClave.getText().equals(Supervisor.getValue().getClave())) {
            Configuraciones.supervisorOK = true;
            cerrar();
        }

    }

    @FXML
    void Cancelar(ActionEvent event) {
        Configuraciones.supervisorOK = false;
        cerrar();
    }

    private void cargarDatos() throws IOException {



        ObservableList<Personal> listaPersonalSupervisor = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Lista con tipo");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        paramsJSON.put("tipo", 1);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonalSupervisor.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), Personal.class) );
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
            TituloCaja.setText(parametros.get(0).get("titulo").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }



        Error.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

