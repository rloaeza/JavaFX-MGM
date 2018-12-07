package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Pacientes extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Pacientes> ListaDePacientes;

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
    private JFXPasswordField Clave;


    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Actualizar");

        paramsJSON.put("idPaciente",ListaDePacientes.getSelectionModel().getSelectedItem().getIdPaciente());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("apellidos", Apellidos.getText());
        paramsJSON.put("email", Email.getText());
        paramsJSON.put("telefono", Telefono.getText());
        paramsJSON.put("movil", Celular.getText());
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("idPersonal", parametros.get(0).get("idPersonal"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Agregar");

        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("apellidos", Apellidos.getText());
        paramsJSON.put("email", Email.getText());
        paramsJSON.put("telefono", Telefono.getText());
        paramsJSON.put("movil", Celular.getText());
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("idPersonal", parametros.get(0).get("idPersonal"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Eliminar");
        paramsJSON.put("idPaciente", ListaDePacientes.getSelectionModel().getSelectedItem().getIdPaciente());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Pacientes(-1, "", "","","","","",-1 ));
        ListaDePacientes.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }

    private void cargarDatosPantalla(modelo.Pacientes p ) {
        Nombre.setText(p.getNombre());
        Apellidos.setText(p.getApellidos());
        Email.setText(p.getEmail());
        Telefono.setText(p.getTelefono());
        Celular.setText(p.getMovil());
        Clave.setText(p.getClave());
    }

    private void cargarDatos() throws IOException {

        ObservableList<modelo.Pacientes> listaPersonal = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Lista");
        paramsJSON.put("idPersonal", parametros.get(0).get("idPersonal"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonal.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Pacientes.class) );
            }
        }

        ListaDePacientes.setItems(listaPersonal);

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
        ListaDePacientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cargarDatosPantalla(ListaDePacientes.getSelectionModel().getSelectedItem());
            }
        });
    }
}
