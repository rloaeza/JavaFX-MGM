package controlador;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class RelojChecador extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Hora;

    @FXML
    private JFXTextField UsuarioEntrada;

    @FXML
    void entrar(ActionEvent event) throws IOException {
        System.out.println(UsuarioEntrada.getText());
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));





        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Entrada / Salida");
        paramsAlert.put("texto", "Bienvenido Roberto");
        paramsAlert.put("vista", "/vista/alert_box.fxml");
        Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );



    }

    @Override
    public void init() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Hora.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        Hora.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            Hora.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        } ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }
}
