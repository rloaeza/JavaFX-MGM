package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Horarios extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Horarios> ListaDeHorarios;

    @FXML
    private JFXTimePicker LunesEntrada;

    @FXML
    private JFXTimePicker LunesSalida;

    @FXML
    private JFXTimePicker MartesEntrada;

    @FXML
    private JFXTimePicker MartesSalida;

    @FXML
    private JFXTimePicker MiercolesEntrada;

    @FXML
    private JFXTimePicker MiercolesSalida;

    @FXML
    private JFXTimePicker JuevesEntrada;

    @FXML
    private JFXTimePicker JuevesSalida;

    @FXML
    private JFXTimePicker ViernesEntrada;

    @FXML
    private JFXTimePicker ViernesSalida;

    @FXML
    private JFXTimePicker SabadoEntrada;

    @FXML
    private JFXTimePicker SabadoSalida;

    @FXML
    private JFXTimePicker DomingoEntrada;

    @FXML
    private JFXTimePicker DomingoSalida;

    @FXML
    private JFXTextField Tolerancia;

    @FXML
    private JFXComboBox<modelo.Personal> ListaDePersonal;

    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Reloj: Actualizar horarios de personal");

        paramsJSON.put("Lunes", LunesEntrada.getValue() + "@" + LunesSalida.getValue());
        paramsJSON.put("Martes", MartesEntrada.getValue() + "@" + MartesSalida.getValue());
        paramsJSON.put("Miercoles", MiercolesEntrada.getValue() + "@" + MiercolesSalida.getValue());
        paramsJSON.put("Jueves", JuevesEntrada.getValue() + "@" + JuevesSalida.getValue()) ;
        paramsJSON.put("Viernes", ViernesEntrada.getValue() + "@" + ViernesSalida.getValue());
        paramsJSON.put("Sabado", SabadoEntrada.getValue() + "@" + SabadoSalida.getValue());
        paramsJSON.put("Domingo", DomingoEntrada.getValue() + "@" + DomingoSalida.getValue());
        paramsJSON.put("Tolerancia", Tolerancia.getText());

        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        paramsJSON.put("idPersonal", ListaDePersonal.getSelectionModel().getSelectedItem().getIdPersonal());
        paramsJSON.put("idHorarios", ListaDeHorarios.getSelectionModel().getSelectedItem().getIdHorarios());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Reloj: Agregar horarios de personal");

        paramsJSON.put("Lunes", LunesEntrada.getValue() + "@" + LunesSalida.getValue());
        paramsJSON.put("Martes", MartesEntrada.getValue() + "@" + MartesSalida.getValue());
        paramsJSON.put("Miercoles", MiercolesEntrada.getValue() + "@" + MiercolesSalida.getValue());
        paramsJSON.put("Jueves", JuevesEntrada.getValue() + "@" + JuevesSalida.getValue()) ;
        paramsJSON.put("Viernes", ViernesEntrada.getValue() + "@" + ViernesSalida.getValue());
        paramsJSON.put("Sabado", SabadoEntrada.getValue() + "@" + SabadoSalida.getValue());
        paramsJSON.put("Domingo", DomingoEntrada.getValue() + "@" + DomingoSalida.getValue());
        paramsJSON.put("Tolerancia", Tolerancia.getText());

        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        paramsJSON.put("idPersonal", ListaDePersonal.getSelectionModel().getSelectedItem().getIdPersonal());

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Reloj: Eliminar horarios de personal");
        paramsJSON.put("idHorarios", ListaDeHorarios.getSelectionModel().getSelectedItem().getIdHorarios());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void limpiar(ActionEvent event) {


    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }




    private void cargarDatos() throws IOException {

        ObservableList<modelo.Horarios> listaHorarios = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Reloj: Horarios de personal");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaHorarios.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Horarios.class) );
            }
        }

        ListaDeHorarios.setItems(listaHorarios);

    }

    private void cargarDatosPersonal() throws IOException {

        ObservableList<modelo.Personal> listaPersonal = FXCollections.observableArrayList();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Lista");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonal.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Personal.class) );
            }
        }
        ListaDePersonal.setItems(listaPersonal);

    }

    private void cargarDatosPantalla(modelo.Horarios horarios) {

        LunesEntrada.setValue(LocalTime.parse(horarios.getLunes().split("@")[0]));
        LunesSalida.setValue(LocalTime.parse(horarios.getLunes().split("@")[1]));

        MartesEntrada.setValue(LocalTime.parse(horarios.getMartes().split("@")[0]));
        MartesSalida.setValue(LocalTime.parse(horarios.getMartes().split("@")[1]));

        MiercolesEntrada.setValue(LocalTime.parse(horarios.getMiercoles().split("@")[0]));
        MiercolesSalida.setValue(LocalTime.parse(horarios.getMiercoles().split("@")[1]));

        JuevesEntrada.setValue(LocalTime.parse(horarios.getJueves().split("@")[0]));
        JuevesSalida.setValue(LocalTime.parse(horarios.getJueves().split("@")[1]));

        ViernesEntrada.setValue(LocalTime.parse(horarios.getViernes().split("@")[0]));
        ViernesSalida.setValue(LocalTime.parse(horarios.getViernes().split("@")[1]));

        SabadoEntrada.setValue(LocalTime.parse(horarios.getSabado().split("@")[0]));
        SabadoSalida.setValue(LocalTime.parse(horarios.getSabado().split("@")[1]));

        DomingoEntrada.setValue(LocalTime.parse(horarios.getDomingo().split("@")[0]));
        DomingoSalida.setValue(LocalTime.parse(horarios.getDomingo().split("@")[1]));

        Tolerancia.setText(horarios.getToleranciaEntrada()+"");

        for(int i=0; i<ListaDePersonal.getItems().size(); i++ ){
            if(ListaDePersonal.getItems().get(i).getIdPersonal() == horarios.getIdPersonal()) {
                ListaDePersonal.getSelectionModel().select(i);
                break;
            }
        }
    }

    @Override
    public void init() {
        try {
            cargarDatos();
            cargarDatosPersonal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListaDeHorarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cargarDatosPantalla(ListaDeHorarios.getSelectionModel().getSelectedItem());
            }
        });
    }
}
