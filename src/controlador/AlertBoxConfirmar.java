package controlador;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Configuraciones;

public class AlertBoxConfirmar extends Controlador {


    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Texto;

    @FXML
    void aceptar(ActionEvent event) {
        Configuraciones.alertBoxConfirmar = true;
       cerrar();
    }

    @FXML
    void cancelar(ActionEvent event) {
        Configuraciones.alertBoxConfirmar = false;
        cerrar();
    }


    @FXML
    private JFXButton BotonAceptar;

    private void cerrar() {

        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }
    @Override
    public void init() {
        Texto.setText(parametros.get(0).get("texto").toString());

    }
}
