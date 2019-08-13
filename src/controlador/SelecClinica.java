package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SelecClinica extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label TituloCaja;

    @FXML
    private JFXComboBox<Clinica> CBClinicas;



    @FXML
    private Label Error;

    @FXML
    void Aceptar2(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            Aceptar(null);
    }
    @FXML
    void Aceptar(ActionEvent event) {
        Error.setVisible(true);
        if(CBClinicas.getSelectionModel().getSelectedIndex()==-1) {
            Error.setText(Configuraciones.corteCajaErrorNoCajaSeleccionada);
            return;
        }

        Configuraciones.idClinica = CBClinicas.getValue().getIdClinica();
        Configuraciones.nombreClinica = CBClinicas.getValue().getNombre();
        Configuraciones.ticketTituloClinicaThermal = CBClinicas.getValue().getDescripcion();



        cerrar();
        Error.setVisible(false);

    }

    @FXML
    void Cancelar(ActionEvent event) {
        Configuraciones.corteCajaValido=false;
        cerrar();
    }

    private void cargarDatos()  {

        CBClinicas.setItems(Datos.clinicas);





    }
    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }

    @Override
    public void init() {
        cargarDatos();
        Error.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

