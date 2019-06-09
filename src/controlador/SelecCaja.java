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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Personal;
import modelo.*;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SelecCaja extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label TituloCaja;

    @FXML
    private JFXComboBox<Caja> CBCajas;



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
        if(CBCajas.getSelectionModel().getSelectedIndex()==-1) {
            Error.setText(Configuraciones.corteCajaErrorNoCajaSeleccionada);
            return;
        }



        Configuraciones.idCaja = CBCajas.getValue().getIdCaja();
        Configuraciones.impresoraTicket = CBCajas.getSelectionModel().getSelectedItem().getImpresoraTicket();
        Configuraciones.impresoraReporte = CBCajas.getSelectionModel().getSelectedItem().getImpresoraReporte();

        Configuraciones.corteCajaValido = true;
        cerrar();
        Error.setVisible(false);

    }

    @FXML
    void Cancelar(ActionEvent event) {
        Configuraciones.corteCajaValido=false;
        cerrar();
    }

    private void cargarDatos() throws IOException {


        ObservableList<Caja> listaCajas = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Caja: Lista");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaCajas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), Caja.class) );
            }
        }
        CBCajas.setItems(listaCajas);





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

            TituloCaja.setText("Estoy en caja");
        } catch (IOException e) {
            e.printStackTrace();
        }


        Error.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

