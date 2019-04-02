package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Configuraciones;

public class FormaPago extends Controlador {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Cantidad;

    @FXML
    private JFXComboBox<?> FormaPago;

    @FXML
    private JFXTextField Pago;

    @FXML
    private Label Cambio;

    @FXML
    void Aceptar(ActionEvent event) {

    }

    @FXML
    void Cancelar(ActionEvent event) {

    }

    @FXML
    void SeleccionarFormaPago(ActionEvent event) {

    }

    @Override
    public void init() {
        Cantidad.setText(String.valueOf(Configuraciones.ventaPago));
    }
}
