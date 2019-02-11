package controlador;

import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Funciones;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TratamientoRecetadoDetalle extends Controlador {


    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXButton BotonAceptar;

    @FXML
    private JFXButton BotonAumentar;

    @FXML
    private JFXTextField Sesiones;

    @FXML
    private JFXTextField Cobro;

    @FXML
    private Label Titulo;


    @FXML
    void aumentar(ActionEvent event) {
        Sesiones.setText( (Integer.valueOf(Sesiones.getText())+1) +"");
        BotonAumentar.setDisable(true);
    }

    @FXML
    void aceptar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos recetados: Actualizar");
        paramsJSON.put("sesiones",  Sesiones.getText());
        paramsJSON.put("cantidad", Cobro.getText());
        paramsJSON.put("idTratamientoRecetado",  parametros.get(0).get("idTratamientoRecetado").toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);

       cerrar();
    }

    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }
    @Override
    public void init() {
        Titulo.setText(parametros.get(0).get("titulo").toString());
        Sesiones.setText(parametros.get(0).get("sesiones").toString());
        Cobro.setText(parametros.get(0).get("cobro").toString());

    }
}
