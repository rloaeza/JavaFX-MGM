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
        Mensaje.setText(Configuraciones.fpColocarDedo1);
    }

    @FXML
    void eliminar(ActionEvent event) {
        Configuraciones.fpSTR=Configuraciones.fpEliminar;
        cerrar();
    }

    @Override
    public void init() {
        idVistaActual = Math.random();
        sigoPresente();
        if(!Configuraciones.fpActivo) {
            try {
                Funciones.inicializarFP();
            } catch (UnsatisfiedLinkError e) {

            }
        }
        t = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {

            if(!Configuraciones.fpActivo)
                return;

            Configuraciones.templateLen[0] = 2048;

            if (0 == (Configuraciones.ret = FingerprintSensorEx.AcquireFingerprint(Configuraciones.mhDevice, Configuraciones.imgbuf, Configuraciones.template, Configuraciones.templateLen))) {



                if (capturas > 0 && FingerprintSensorEx.DBMatch(Configuraciones.mhDB, Configuraciones.regtemparray[capturas-1], Configuraciones.template) <= 0)
                {
                    Mensaje.setText(Configuraciones.fpColocarDedoDiferente);
                    return;
                }
                System.arraycopy(Configuraciones.template, 0, Configuraciones.regtemparray[capturas], 0, 2048);
                System.out.println("Guardando huella: " +capturas);
                capturas++;

                if(capturas==1) {
                    Huella.setImage(new Image("imgs/fp1.jpg"));
                    Mensaje.setText(Configuraciones.fpColocarDedo2);
                }
                if(capturas==2) {
                    Huella.setImage(new Image("imgs/fp2.jpg"));
                    Mensaje.setText(Configuraciones.fpColocarDedo3);
                }
                if(capturas==3) {


                    Huella.setImage(new Image("imgs/fp3.jpg"));


                    int[] _retLen = new int[1];
                    _retLen[0] = 2048;
                    byte[] regTemp = new byte[_retLen[0]];
                    if (0 == ( FingerprintSensorEx.DBMerge(Configuraciones.mhDB, Configuraciones.regtemparray[0], Configuraciones.regtemparray[1], Configuraciones.regtemparray[2], regTemp, _retLen)) &&
                            0 == ( FingerprintSensorEx.DBAdd(Configuraciones.mhDB, Configuraciones.iFid, regTemp))) {

                        Configuraciones.cbRegTemp = _retLen[0];
                        System.arraycopy(regTemp, 0, Configuraciones.lastRegTemp, 0, Configuraciones.cbRegTemp);
                        huella = Base64.getEncoder().encodeToString(regTemp);
                        Mensaje.setText(Configuraciones.fpColocarDedoOK);
                    } else {
                        Mensaje.setText(Configuraciones.fpColocarDedoFail);
                    }

                    t.stop();
                }



            }

        } ));
        t.setCycleCount(Animation.INDEFINITE);
        addTimer(t);
    }

    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        Funciones.FreeSensor();
        Configuraciones.fpActivo = false;
        window.close();

    }

}

