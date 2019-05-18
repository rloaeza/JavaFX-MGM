package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.*;
import modelo.Personal;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CorteCaja extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTextField Monto;

    @FXML
    private Label TituloCaja;

    @FXML
    private Label Vendedor;

    @FXML
    private JFXComboBox<Caja> CBCajas;

    @FXML
    private JFXPasswordField VendedorClave;

    @FXML
    private JFXComboBox<Personal> Supervisor;

    @FXML
    private JFXPasswordField SupervisorClave;

    @FXML
    private Label Error;

    @FXML
    void AceptarSupervisor(ActionEvent event) throws IOException {
        Aceptar(null);
    }

    @FXML
    void Aceptar(ActionEvent event) throws IOException {
        Error.setVisible(true);
        if(VendedorClave.getText().isEmpty()) {
            Error.setText(Configuraciones.corteCajaErrorNoClaveVendedor);
            return;
        }
        if(Supervisor.getSelectionModel().getSelectedIndex()==-1) {
            Error.setText(Configuraciones.corteCajaErrorNoSupervisorSeleccionado);
            return;
        }
        if(SupervisorClave.getText().isEmpty()) {
            Error.setText(Configuraciones.corteCajaErrorNoClaveSupervisor);
            return;
        }
        if(Monto.getText().isEmpty()) {
            Error.setText(Configuraciones.corteCajaErrorNoMonto);
            return;
        }
        if(CBCajas.getSelectionModel().getSelectedIndex()==-1) {
            Error.setText(Configuraciones.corteCajaErrorNoCajaSeleccionada);
            return;
        }


        if(VendedorClave.getText().equals(Configuraciones.clavePersonal) && SupervisorClave.getText().equals(Supervisor.getValue().getClave()))
        {


            PrinterService printerService = new PrinterService();
            List<String> listPrinters = printerService.getPrinters();
            boolean impresoraValida = false;
            for(String printer : listPrinters) {
                if( printer.contains(Configuraciones.impresoraTicket)) {
                    impresoraValida = true;

                }
            }

            Map<String,Object> paramsAlertImpresora = new LinkedHashMap<>();
            paramsAlertImpresora.put("titulo", "Error");
            paramsAlertImpresora.put("tiempo", "5");
            paramsAlertImpresora.put("vista", "/vista/alert_box.fxml");
            if(!impresoraValida) {
                paramsAlertImpresora.put("texto", "No existe la impresora: "+Configuraciones.impresoraTicket+ " en el sistema");

                Funciones.displayFP(paramsAlertImpresora, getClass().getResource("/vista/alert_box.fxml"), new AlertBox());

                return;
            }




            Configuraciones.aperturaCaja = Configuraciones.corteCajaMonto;
            Configuraciones.idCaja = CBCajas.getValue().getIdCaja();
            Map<String,Object> paramsJSON = new LinkedHashMap<>();
            paramsJSON.put("Actividad", "CajaCorte: Insertar");
            paramsJSON.put("idCaja", CBCajas.getValue().getIdCaja());
            paramsJSON.put("idPersonal", Configuraciones.idPersonal);
            paramsJSON.put("idPersonalAut", Supervisor.getValue().getIdPersonal());
            paramsJSON.put("monto", Configuraciones.corteCajaMonto);
            if(Configuraciones.abriendoCaja)
                paramsJSON.put("tipo", 1);
            else
                paramsJSON.put("tipo", 2);
            JsonArray rootArray = Funciones.consultarBD(paramsJSON);


            Configuraciones.impresoraTicket = CBCajas.getSelectionModel().getSelectedItem().getImpresoraTicket();
            Configuraciones.impresoraReporte = CBCajas.getSelectionModel().getSelectedItem().getImpresoraReporte();



            String corteCajaSTR = Configuraciones.ticketCorteCaja;
            String fecha = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
            corteCajaSTR=corteCajaSTR.replace("$tipo$", Configuraciones.abriendoCaja?Configuraciones.corteCajaAbrir:Configuraciones.corteCajaCerrar );
            corteCajaSTR=corteCajaSTR.replace("$caja$", CBCajas.getSelectionModel().getSelectedItem().toString() );
            corteCajaSTR=corteCajaSTR.replace("$fecha$", fecha );
            corteCajaSTR=corteCajaSTR.replace("$vendedor$", Vendedor.getText());
            corteCajaSTR=corteCajaSTR.replace("$supervisor$", Supervisor.getSelectionModel().getSelectedItem().toString());
            corteCajaSTR=corteCajaSTR.replace("$monto$", Monto.getText());

            //System.out.print(corteCajaSTR);







            try {
                printerService.printString(Configuraciones.impresoraTicket, corteCajaSTR);
                byte[] cutP = new byte[]{0x1d, 'V', 1};
                printerService.printBytes(Configuraciones.impresoraTicket, cutP);


                byte[] openCashDrawer = new byte[]{27, 112, 48, 55, 121};
                printerService.printBytes(Configuraciones.impresoraTicket, openCashDrawer);
            } catch (Exception e) {
                paramsAlertImpresora.put("texto", "Error en la impresora :(, "+Configuraciones.impresoraTicket);
                Funciones.displayFP(paramsAlertImpresora, getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
                return;
            }

            Configuraciones.corteCajaValido = true;

            cerrar();
        } else {
            Error.setText(Configuraciones.corteCajaErrorClavesIncorrectas);
            return;
        }
        Error.setVisible(false);

    }

    @FXML
    void Cancelar(ActionEvent event) {
        Configuraciones.corteCajaValido=false;
        cerrar();
    }

    private void cargarDatos() throws IOException {

        Vendedor.setText(Configuraciones.nombrePersonal);

        ObservableList<Personal> listaPersonalSupervisor = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Lista con tipo");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        paramsJSON.put("tipo", 1);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonalSupervisor.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Personal.class) );
            }
        }
        Supervisor.setItems(listaPersonalSupervisor);




        ObservableList<modelo.Caja> listaCajas = FXCollections.observableArrayList();

        paramsJSON.clear();
        paramsJSON.put("Actividad", "Caja: Lista");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaCajas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Caja.class) );
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
            if(Configuraciones.abriendoCaja)
                TituloCaja.setText(Configuraciones.corteCajaAbrir);
            else
                TituloCaja.setText(Configuraciones.corteCajaCerrar);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Configuraciones.abriendoCaja) {
            Monto.setText(Configuraciones.cierreCaja+"");


            Monto.setText(NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                    .format(Configuraciones.cierreCaja));


            Monto.setDisable(true);


            CBCajas.setDisable(true);
            for(int i=0; i<CBCajas.getItems().size(); i++ ) {
                if(CBCajas.getItems().get(i).getIdCaja()==Configuraciones.idCaja) {
                    CBCajas.getSelectionModel().select(i);
                    break;
                }
            }
        }


        Monto.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                Configuraciones.corteCajaMonto = Double.valueOf(Monto.getText());
                Monto.setText(NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                        .format(Configuraciones.corteCajaMonto));
            }
            else {
                Monto.setText(String.valueOf(Configuraciones.corteCajaMonto));
            }
        });

        Error.setVisible(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

