package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Configuraciones;

public class FormaPago extends Controlador {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Cantidad;

    @FXML
    private JFXComboBox<String> FormaPago;

    @FXML
    private JFXTextField Pago;

    @FXML
    private Label Cambio;

    private boolean pagoValido;

    @FXML
    void Aceptar(ActionEvent event) {
        if(!pagoValido)
            return;
        Configuraciones.ventaAceptada=true;
        cerrar();
    }

    @FXML
    void Cancelar(ActionEvent event) {
        Configuraciones.ventaAceptada=false;
        cerrar();
    }

    @FXML
    void SeleccionarFormaPago(ActionEvent event) {
        switch(FormaPago.getSelectionModel().getSelectedIndex()) {
            case 0:
                Cambio.setVisible(true);
                Pago.setPromptText("Cantidad de Efectivo recibida");
                break;
            case 1:
                Cambio.setVisible(false);
                Pago.setPromptText("ID de Transacción");
        }
    }

    @FXML
    void pagando(KeyEvent event) {
        pagoValido = false;
        switch (FormaPago.getSelectionModel().getSelectedIndex()) {

            case 0:
                if( Double.valueOf(Pago.getText()) >= Configuraciones.ventaPago ) {
                    Cambio.setVisible(true);
                    double cambio =   Double.valueOf(Pago.getText()) - Configuraciones.ventaPago;
                    Cambio.setText("Cambio: "+String.valueOf(cambio));
                    pagoValido = true;
                }
                else {
                    Cambio.setText("Falta efectivo");
                    return;
                }
                break;

            case 1:
                if( !Pago.getText().isEmpty()) {
                    Cambio.setVisible(false);
                    pagoValido = true;
                }
                else {
                    pagoValido = false;
                }
        }



    }
    @Override
    public void init() {

        Cantidad.setText(String.valueOf(Configuraciones.ventaPago));
        ObservableList<String> valoresTipoPago;
        valoresTipoPago = FXCollections.observableArrayList();
        valoresTipoPago.addAll(Configuraciones.formasPago);
        FormaPago.setItems(valoresTipoPago);
    }

    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }
}
