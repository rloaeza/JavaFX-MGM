package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.zkteco.biometric.FingerprintSensorEx;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modelo.Configuraciones;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
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
    private ImageView FP0;

    @FXML
    private ImageView FP1;

    @FXML
    private ImageView FP2;

    @FXML
    private ImageView FP3;

    @FXML
    private ImageView FP4;


    @FXML
    private JFXComboBox<String> TipoUsuario;


    private String[] huellas = new String[]{"","","","",""};


    @FXML
    void capturarHuella0(ActionEvent event) {
        capturarHuella(0, FP0);
    }
    @FXML
    void capturarHuella1(ActionEvent event) {
        capturarHuella(1, FP1);
    }
    @FXML
    void capturarHuella2(ActionEvent event) {
        capturarHuella(2, FP2);
    }
    @FXML
    void capturarHuella3(ActionEvent event) {
        capturarHuella(3, FP3);
    }
    @FXML
    void capturarHuella4(ActionEvent event) {
        capturarHuella(4, FP4);
    }
    private void capturarHuella(int id, ImageView imageView) {

        Configuraciones.fpSTR = huellas[id];

        //cargar huella
        Map<String, Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Capturar Huella");
        paramsAlert.put("vista", "/vista/capturar_huella.fxml");
        try {
            Funciones.display(paramsAlert, getClass().getResource("/vista/capturar_huella.fxml"), new CapturarHuella(), 762, 418);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // huella cargada
        if(Configuraciones.fpSTR.equalsIgnoreCase(Configuraciones.fpEliminar)) {
            huellas[id] = "";
        } else if(!Configuraciones.fpSTR.isEmpty()) {
            huellas[id] = Configuraciones.fpSTR;
        }
        //Fijar imagen
        if(huellas[id].isEmpty()) {
            imageView.setImage(new Image("imgs/fps0.png"));
        }else {
            imageView.setImage(new Image("imgs/fps1.png"));
        }
    }

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
        paramsJSON.put("huella0", huellas[0]);
        paramsJSON.put("huella1", huellas[1]);
        paramsJSON.put("huella2", huellas[2]);
        paramsJSON.put("huella3", huellas[3]);
        paramsJSON.put("huella4", huellas[4]);
        paramsJSON.put("tipo", TipoUsuario.getSelectionModel().getSelectedIndex());
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
        limpiar(null);
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
        paramsJSON.put("huella0", huellas[0]);
        paramsJSON.put("huella1", huellas[1]);
        paramsJSON.put("huella2", huellas[2]);
        paramsJSON.put("huella3", huellas[3]);
        paramsJSON.put("huella4", huellas[4]);
        paramsJSON.put("tipo", TipoUsuario.getSelectionModel().getSelectedIndex());
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
        limpiar(null);
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Eliminar");
        paramsJSON.put("idPersonal", ListaDePersonal.getSelectionModel().getSelectedItem().getIdPersonal());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
        limpiar(null);
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Personal(-1, "", "","","","","","","","","","","",-1,-1 ));
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
        cargarImagen(p.getHuella0(), FP0);
        cargarImagen(p.getHuella1(), FP1);
        cargarImagen(p.getHuella2(), FP2);
        cargarImagen(p.getHuella3(), FP3);
        cargarImagen(p.getHuella4(), FP4);
    }
    private void cargarImagen(String huella, ImageView imageView) {
        if(huella.isEmpty())
            imageView.setImage(new Image("imgs/fps0.png"));
        else
            imageView.setImage(new Image("imgs/fps1.png"));
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
