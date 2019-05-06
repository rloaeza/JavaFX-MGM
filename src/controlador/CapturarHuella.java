package controlador;

import com.zkteco.biometric.FingerprintSensorEx;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Configuraciones;
import modelo.Funciones;

import java.util.Base64;

public class CapturarHuella extends Controlador {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Mensaje;

    @FXML
    private ImageView Huella;

    private String huella;
    private Timeline t;
    private int capturas=0;


    @FXML
    void aceptar(ActionEvent event) {

        Configuraciones.fpSTR=huella;
        cerrar();
    }

    @FXML
    void cancelar(ActionEvent event) {
        Configuraciones.fpSTR="";
        cerrar();
    }

    @FXML
    void capturar(ActionEvent event) {
        if(!Configuraciones.fpActivo)
            Funciones.inicializarFP();
        t.play();
        Huella.setImage(new Image("imgs/fp0.jpg"));
        huella = "";
        capturas = 0;
        Mensaje.setText(Configuraciones.fpColocarDedo);
    }

    @FXML
    void eliminar(ActionEvent event) {
        Configuraciones.fpSTR=Configuraciones.fpEliminar;
        cerrar();
    }

    @Override
    public void init() {

        t = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {

            if(!Configuraciones.fpActivo)
                return;

            Configuraciones.templateLen[0] = 2048;

            if (0 == (Configuraciones.ret = FingerprintSensorEx.AcquireFingerprint(Configuraciones.mhDevice, Configuraciones.imgbuf, Configuraciones.template, Configuraciones.templateLen))) {

                huella = Base64.getEncoder().encodeToString(Configuraciones.template);

                capturas++;
                if(capturas==1) {
                    Huella.setImage(new Image("imgs/fp3.jpg"));
                    Mensaje.setText("");
                }
                t.stop();
            }

        } ));
        t.setCycleCount(Animation.INDEFINITE);

    }

    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        Funciones.FreeSensor();
        Configuraciones.fpActivo = false;
        window.close();

    }

}

