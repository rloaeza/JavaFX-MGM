package controlador;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import modelo.Funciones;

import java.util.ArrayList;
import java.util.Map;

public abstract class  Controlador {

    public static ArrayList<Map<String, Object> > parametros;

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

    public abstract void init();


}
