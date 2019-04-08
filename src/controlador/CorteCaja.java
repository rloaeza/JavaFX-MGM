package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CorteCaja extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTextField Monto;

    @FXML
    private JFXComboBox<?> Vendedor;

    @FXML
    private JFXPasswordField VendedorClave;

    @FXML
    private JFXComboBox<?> Supervisor;

    @FXML
    private JFXPasswordField SupervisorClave;

    @FXML
    void Aceptar(ActionEvent event) {

    }

    @FXML
    void Cancelar(ActionEvent event) {

    }

    @Override
    public void init() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

