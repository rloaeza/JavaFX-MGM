package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Configuraciones;
import modelo.Funciones;

public class FormaPago extends Controlador {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Cantidad;

    @FXML
    private JFXTextField Pago;

    @FXML
    private Label Cambio;

    @FXML
    private ToggleButton FormaEfectivo;

    @FXML
    private ToggleButton FormaTarjeta;

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
        if(FormaEfectivo.isSelected()) {
            Cambio.setVisible(true);
            Pago.setPromptText(Configuraciones.formaPagoEfectivoRecibido);
        }
        else {
            FormaTarjeta.setSelected(true);
            Cambio.setVisible(false);
            Pago.setPromptText(Configuraciones.formaPagoIdTransaccion);
        }
    }

    @FXML
    void pagando(KeyEvent event) {
        pagoValido = false;
        if(Pago.getText().length()==0)
            return;
        if(FormaEfectivo.isSelected()) {
            if (Double.valueOf(Pago.getText()) >= Configuraciones.formaPagoMonto) {
                Cambio.setVisible(true);
                double cambio = Double.valueOf(Pago.getText()) - Configuraciones.formaPagoMonto;
                Cambio.setText(Configuraciones.formaPagoCambio + String.valueOf(cambio));
                pagoValido = true;
            } else {
                Cambio.setText(Configuraciones.formaPagoFaltaEfectivo);
                return;
            }
        }
        else {
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

        Cantidad.setText(Funciones.valorAmoneda(Configuraciones.formaPagoMonto));



    }

    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }
}
