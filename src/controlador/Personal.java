package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import modelo.Configuraciones;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Personal extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Personal> ListaDePersonal;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextField Apellidos;

    @FXML
    private JFXTextField Email;

    @FXML
    private JFXTextField Telefono;

    @FXML
    private JFXTextField Celular;

    @FXML
    private JFXTextField Usuario;

    @FXML
    private JFXPasswordField Clave;


    @FXML
    private JFXComboBox<String> TipoUsuario;


    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Actualizar");

        paramsJSON.put("idPersonal",ListaDePersonal.getSelectionModel().getSelectedItem().getIdPersonal());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("apellidos", Apellidos.getText());
        paramsJSON.put("email", Email.getText());
        paramsJSON.put("telefono", Telefono.getText());
        paramsJSON.put("movil", Celular.getText());
        paramsJSON.put("usuario", Usuario.getText());
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("tipo", TipoUsuario.getSelectionModel().getSelectedIndex());
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Agregar");

        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("apellidos", Apellidos.getText());
        paramsJSON.put("email", Email.getText());
        paramsJSON.put("telefono", Telefono.getText());
        paramsJSON.put("movil", Celular.getText());
        paramsJSON.put("usuario", Usuario.getText());
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("tipo", TipoUsuario.getSelectionModel().getSelectedIndex());
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Eliminar");
        paramsJSON.put("idPersonal", ListaDePersonal.getSelectionModel().getSelectedItem().getIdPersonal());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Personal(-1, "", "","","","","","",-1,-1 ));
        ListaDePersonal.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {

        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());

    }


    private void cargarDatosPantalla(modelo.Personal p ) {
        Nombre.setText(p.getNombre());
        Apellidos.setText(p.getApellidos());
        Email.setText(p.getEmail());
        Telefono.setText(p.getTelefono());
        Celular.setText(p.getMovil());
        Usuario.setText(p.getUsuario());
        Clave.setText(p.getClave());
        TipoUsuario.getSelectionModel().select(p.getTipo());
    }

    private void cargarDatos() throws IOException {

        ObservableList<modelo.Personal> listaPersonal = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Lista");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonal.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Personal.class) );
            }
        }

        ListaDePersonal.setItems(listaPersonal);

    }

    @Override
    public void init() {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListaDePersonal.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cargarDatosPantalla(ListaDePersonal.getSelectionModel().getSelectedItem());
            }
        });
        ObservableList<String> valoresTipoUsuarios;
        valoresTipoUsuarios = FXCollections.observableArrayList();
        valoresTipoUsuarios.addAll(Configuraciones.tiposUsuarios);
        TipoUsuario.setItems(valoresTipoUsuarios);
    }
}
