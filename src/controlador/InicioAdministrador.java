package controlador;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InicioAdministrador extends  Controlador implements Initializable {

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



    @FXML
    void catalogoTiposDeProductos(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/tipos_productos.fxml"));
        root.setPrefHeight(Pane.getHeight());
        root.setPrefWidth(Pane.getWidth());
        Pane.getChildren().setAll(root);


    }

    @FXML
    void catalogoPacientes(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", params.get("idClinica"));
        paramsVista.put("idPersonal", params.get("idPersonal"));
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource("/vista/pacientes.fxml"), paramsVista, new Pacientes());
    }
    @FXML
    void catalogoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource("/vista/tratamientos.fxml"), paramsVista, new Productos());
    }

    public void setUsuario(Personal p) {
        this.usuario = p;
    }

    public void init() {
        //Estado.setText(usuario.getNombre());
        ((Stage)Pane.getScene().getWindow()).setTitle(params.get("nombre").toString());


        Stage escenario = (Stage) Pane.getScene().getWindow();

        escenario.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(Pane.getChildren().size()>0) {
                AnchorPane root = (AnchorPane) Pane.getChildren().get(0);
                root.setPrefHeight(Pane.getHeight());
                root.setPrefWidth(Pane.getWidth());
            }
        });

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
