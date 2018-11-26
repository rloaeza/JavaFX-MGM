package controlador;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioAdministrador implements Initializable {

    @FXML
    private Pane Pane;

    @FXML
    private Label Estado;

    private Personal usuario;

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicio_sesion.fxml"));
        Stage escenario = (Stage) Pane.getScene().getWindow();
        escenario.setScene(new Scene(root, Funciones.ancho, Funciones.alto));
    }

    @FXML
    void salir(ActionEvent event) {
        Platform.exit();
    }


    public void setUsuario(Personal p) {
        this.usuario = p;
    }

    public void init() {
        Estado.setText(usuario.getNombre());
        ((Stage)Pane.getScene().getWindow()).setTitle(usuario.getaPaterno()+ " "+usuario.getaMaterno()+", "+usuario.getNombre());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
