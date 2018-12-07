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
        /*
        AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/tipos_productos.fxml"));
        root.setPrefHeight(Pane.getHeight());
        root.setPrefWidth(Pane.getWidth());
        Pane.getChildren().setAll(root);

        */
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
        paramsVista.put("vista", "/vista/tipos_productos.fxml");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new controlador.TiposProductos());


    }
    @FXML
    void catalogoPersonal(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
        paramsVista.put("idPersonal", parametros.get(0).get("idPersonal"));
        paramsVista.put("vista", "/vista/personal.fxml");


        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource("/vista/personal.fxml"), paramsVista, new controlador.Personal());
    }
    @FXML
    void catalogoPacientes(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
        paramsVista.put("idPersonal", parametros.get(0).get("idPersonal"));
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource("/vista/pacientes.fxml"), paramsVista, new Pacientes());
    }
    @FXML
    void catalogoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/tratamientos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Productos());
    }

    public void setUsuario(Personal p) {
        this.usuario = p;
    }

    public void init() {
        //Estado.setText(usuario.getNombre());
        ((Stage)Pane.getScene().getWindow()).setTitle(parametros.get(0).get("nombre").toString());


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
