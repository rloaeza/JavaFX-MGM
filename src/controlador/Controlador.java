package controlador;

import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.zkteco.biometric.FingerprintSensorEx;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Configuraciones;
import modelo.Funciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

public abstract class  Controlador {



    public static ArrayList<Map<String, Object> > parametros;
    protected static double idVistaActual;

    public static ArrayList<Timeline> timers;

    public void setParams(Map<String, Object> params)  {
        if(parametros==null)
            parametros = new ArrayList<>();
        parametros.add(0, params);

    }


    public void quitarVistas() {
        while (parametros.size()>2)
            parametros.remove(0);

    }

    public void quitarVistas(int n) {
        while (parametros.size()>n)
            parametros.remove(0);

    }


    public void cargarVista(AnchorPane Pane, Controlador controlador, String vista) throws IOException {
        Pane.getChildren().removeAll();
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( vista ), controlador );
    }


    @FXML
    void capitalizarEntrada(KeyEvent event) {
        if(event.getSource() instanceof  JFXTextField) {
            JFXTextField jft = (JFXTextField) event.getSource();
            jft.setText(Funciones.capitalize(jft.getText()));
            jft.positionCaret(jft.getText().length());
        }
        else if(event.getSource() instanceof JFXTextArea) {
            JFXTextArea jft = (JFXTextArea) event.getSource();
            jft.setText(Funciones.capitalize(jft.getText()));
            jft.positionCaret(jft.getText().length());
        }

    }

    @FXML
    void capitalizarEntradaCompleta(KeyEvent event) {
        if(event.getSource() instanceof  JFXTextField) {
            JFXTextField jft = (JFXTextField) event.getSource();
            jft.setText(Funciones.capitalizeAll(jft.getText()));
            jft.positionCaret(jft.getText().length());
        }
        else if(event.getSource() instanceof  JFXTextArea) {
            JFXTextArea jft = (JFXTextArea) event.getSource();
            jft.setText(Funciones.capitalizeAll(jft.getText()));
            jft.positionCaret(jft.getText().length());
        }

    }

    public boolean insercionCorrectaSQL(JsonArray jsonArray) {
        if(jsonArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            if(jsonArray.get(1).getAsJsonObject().get(Funciones.ultimoInsertado).getAsInt()>0)
                return true;
        }
        return false;
    }

    public void crearIdVistaActual() {
        Configuraciones.idVistaActual = Math.random();
    }

    public boolean sigoPresente() {
        if(Configuraciones.idVistaActual == idVistaActual)
            return true;
        else {
            if( timers != null) {
                for (Timeline t : timers) {
                    t.stop();
                }
                timers.clear();
            }
            if(Configuraciones.fpActivo) {
                Funciones.FreeSensor();
                Configuraciones.fpActivo = false;
            }
            return false;
        }
    }

    public void addTimer(Timeline t) {
        if(timers == null) {
            timers = new ArrayList<>();
        }
        timers.add(t);
    }

    int fid = 0;
    ArrayList<Integer> idHuellas = new ArrayList<>();
    protected void agregarHuella(String huella, int idLista) {
        if(!huella.isEmpty()) {
            FingerprintSensorEx.DBAdd(Configuraciones.mhDB, fid++, Base64.getDecoder().decode(huella));
            idHuellas.add(idLista);
        }


    }

    public abstract void init();


}
