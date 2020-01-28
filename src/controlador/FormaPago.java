package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
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
import modelo.*;

public class FormaPago extends Controlador {



    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Cantidad;

    @FXML
    private JFXListView<Cobro> ListaPagos;
    @FXML
    private JFXTextField Pago;

    @FXML
    private JFXTextField Descripcion;


    @FXML
    private ToggleButton FormaEfectivo;

    @FXML
    private ToggleButton FormaTarjeta;

    @FXML
    private ToggleButton FormaDeposito;

    @FXML
    private ToggleButton FormaTransferencia;

    @FXML
    private JFXComboBox<modelo.Descuento> Descuento;
    @FXML
    private Label Error;

    private double cambio=0;

    private  double montoInicial;

    @FXML
    void fijarDescuento(ActionEvent event) {
        Configuraciones.descuento = ((double) Descuento.getValue().getCantidad())/100;
        Configuraciones.formaPagoMonto = montoInicial - montoInicial*(Configuraciones.descuento);
        Cantidad.setText(Funciones.valorAmoneda(Configuraciones.formaPagoMonto));
        if(!ListaPagos.getItems().isEmpty())
            calcular();
    }

    @FXML
    void agregarTeclado(ActionEvent event) {
        if(Pago.getText().isEmpty()) {
            Aceptar(null);
        } else {
            agregar(null);
        }
    }

    @FXML
    void agregar(ActionEvent event) {
        int formaPago = FormaEfectivo.isSelected()?1:2;
        if ( FormaEfectivo.isSelected())
            formaPago = 1;
        else if( FormaTarjeta.isSelected())
            formaPago = 2;
        else if( FormaDeposito.isSelected())
            formaPago = 3;
        else if( FormaTransferencia.isSelected() )
            formaPago = 4;


        Configuraciones.formaPagoCobros.add(new Cobro(
                -1, -1, formaPago, Double.valueOf(Pago.getText()), Descripcion.getText()
        ));
        SeleccionarFormaPago(null);
        calcular();
    }

    @FXML
    void eliminar(ActionEvent event) {
        Configuraciones.formaPagoCobros.remove(ListaPagos.getSelectionModel().getSelectedIndex() );
        SeleccionarFormaPago(null);
        calcular();
    }

    private boolean calcular() {
        double total = 0;
        double totalTarjeta = 0;
        cambio = 0;
        for(Cobro c : Configuraciones.formaPagoCobros) {
            total += c.getMonto();
            if( c.getFormaPago() == 2) {
                totalTarjeta += c.getMonto();
            }
        }
        if (totalTarjeta > Configuraciones.formaPagoMonto) {
            Error.setText(Configuraciones.formaPagoExcesoTarjeta);
            Error.setVisible(true);
            Error.setStyle("-fx-background-color: #ef5728;");
            pagoValido=false;
            return pagoValido;
        }

        if( total < Configuraciones.formaPagoMonto) {
            Error.setText(Configuraciones.formaPagoFaltaEfectivo+ ", " + Funciones.fixN(Configuraciones.formaPagoMonto-total,2));
            Error.setVisible(true);
            Error.setStyle("-fx-background-color: #ef5728;");
            pagoValido=false;
            return pagoValido;
        }


        if(total > Configuraciones.formaPagoMonto) {
            Error.setVisible(true);
            cambio = total- Configuraciones.formaPagoMonto;
            Error.setText(Configuraciones.formaPagoCambio+Funciones.fixN(cambio, 2));
            Error.setStyle("-fx-background-color: green;");
            pagoValido=true;
            return pagoValido;
        }

        if(total == Configuraciones.formaPagoMonto) {
            Error.setVisible(false);
            pagoValido=true;
            return pagoValido;

        }
        pagoValido=false;
        return pagoValido;


    }
    private boolean pagoValido;

    @FXML
    void Aceptar(ActionEvent event) {
        Error.setVisible(false);
        if( !pagoValido ) {
            Error.setVisible(true);
            Error.setText(Configuraciones.formaPagoVerificar);
            return;
        }
        Configuraciones.formaPagoMontoCambio=0;
        if(cambio!=0) {
            Configuraciones.formaPagoCobros.add(new Cobro(
                    -1, -1, 1, -cambio, "Cambio"
            ));
            Configuraciones.formaPagoMontoCambio = cambio;
        }

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
        Descripcion.setText("");
        Pago.setText("");
        Pago.setPromptText(Configuraciones.formaPagoMontoRecibido);
        /*
        if(event!=null)
            if( event.getSource().equals(FormaEfectivo) )
                FormaEfectivo.setSelected(true);
            else
                FormaTarjeta.setSelected(true);
*/
        if(FormaEfectivo.isSelected()) {
            Descripcion.setPromptText("Efectivo");
            Descripcion.setEditable(false);
            Descripcion.setVisible(false);
        }
        else {
            //FormaTarjeta.setSelected(true);
            Descripcion.setPromptText(Configuraciones.formaPagoIdTransaccion);
            Descripcion.setEditable(true);
            Descripcion.setVisible(true);
        }
    }

    @FXML
    void pagando(KeyEvent event) {
        Error.setVisible(false);
        pagoValido = calcular();




    }
    @Override
    public void init() {

        Cantidad.setText(Funciones.valorAmoneda(Configuraciones.formaPagoMonto));
        Error.setVisible(false);

        if(Configuraciones.formaPagoCobros==null) {
            Configuraciones.formaPagoCobros = FXCollections.observableArrayList();
        }
        else {
            Configuraciones.formaPagoCobros.clear();
        }

        ListaPagos.setItems(Configuraciones.formaPagoCobros);
        SeleccionarFormaPago(null);

        Descuento.setItems(Datos.descuentos);
        Descuento.getSelectionModel().select(0);

        montoInicial = Configuraciones.formaPagoMonto;
        Configuraciones.descuento=0;

    }

    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }
}
