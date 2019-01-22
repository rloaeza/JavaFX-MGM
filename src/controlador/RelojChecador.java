package controlador;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class RelojChecador extends Controlador {

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Hora;

    @FXML
    private JFXTextField UsuarioEntrada;

    @FXML
    void entrar(ActionEvent event) {
        System.out.println(UsuarioEntrada.getText());


    }

    @Override
    public void init() {

    }
}
