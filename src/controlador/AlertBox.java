package controlador;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class AlertBox extends Controlador {


    Timeline tl= null;
    int tiempo = 5;


    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Texto;

    @FXML
    void aceptar(ActionEvent event) {
        tl.stop();
       cerrar();
    }



    @FXML
    private JFXButton BotonAceptar;

    private void cerrar() {
        tl.stop();
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }
    @Override
    public void init() {
        Texto.setText(parametros.get(0).get("texto").toString());


        tl = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            if(tiempo>0) {
                BotonAceptar.setText("Aceptar (" + tiempo + ")");
                tiempo--;
            }
            else {
                cerrar();
            }

        } ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();

    }
}
