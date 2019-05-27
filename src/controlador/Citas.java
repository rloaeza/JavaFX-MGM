package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import modelo.*;
import modelo.Pacientes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Citas extends  Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<CitaPacienteFechaHora> ListaHistorial;

    @FXML
    private JFXListView<CitaPaciente> ListaDeCitas;

    @FXML
    private JFXListView<modelo.Pacientes> ListaDePacientes;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextField Apellidos;

    @FXML
    private JFXTextField Telefono;

    @FXML
    private JFXTextField Celular;

    @FXML
    private JFXTextField Email;

    @FXML
    private JFXDatePicker Fecha;

    @FXML
    private JFXTimePicker Hora;

    @FXML
    private Button Tratamientos;

    private int agregarPaciente() throws IOException {
        int idPaciente=-1;
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Agregar rapido");

        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("apellidos", Apellidos.getText());
        paramsJSON.put("email", Email.getText());
        paramsJSON.put("telefono", Telefono.getText());
        paramsJSON.put("movil", Celular.getText());
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));

        //System.out.println(paramsJSON.toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        idPaciente = rootArray.get(1).getAsJsonObject().get(Funciones.ultimoInsertado).getAsInt();
        Datos.cargarPacientes();
        cargarPacientes();
        return idPaciente;
    }


    @FXML
    void agregar(ActionEvent event) throws IOException {
        int idPaciente;
        if(ListaDePacientes.getSelectionModel().getSelectedIndex()==-1) {
            idPaciente = agregarPaciente();
        } else {
           idPaciente = ListaDePacientes.getSelectionModel().getSelectedItem().getIdPaciente();
        }


        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Citas: Agregar");
        paramsJSON.put("idPaciente", idPaciente);
        String f = Fecha.getValue().toString()+" " + Hora.getValue().toString();
        paramsJSON.put("fecha", f);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarCitas();
        limpiar(null);
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        if(ListaDeCitas.getSelectionModel().getSelectedIndex()==-1)
            return;

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Citas: Eliminar");
        paramsJSON.put("idCita", ListaDeCitas.getSelectionModel().getSelectedItem().getIdCita());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarCitas();
    }

    @FXML
    void limpiar(ActionEvent event) throws IOException {
        cargarDatosPantalla(new Pacientes());
        cargarPacientes();
    }

    @FXML
    void buscarPaciente(KeyEvent event)  {

            cargarPacientes(Nombre.getText().replace(" ", ""));

    }

    @FXML
    void cambiarFecha(ActionEvent event) throws IOException {
        cargarCitas();

    }

    private void cargarPacientes(String patron)  {
/*
        ObservableList<Pacientes> listaPersonal = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Lista con patron");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        paramsJSON.put("patron", patron);

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonal.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Pacientes.class) );
            }
        }

        ListaDePacientes.setItems(listaPersonal);

        */
        ListaDePacientes.getSelectionModel().clearSelection();
        ListaDePacientes.setItems(Datos.buscarPacientes(patron));


    }

    private void cargarPacientes()  {
/*
        ObservableList<Pacientes> listaPersonal = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Lista");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPersonal.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Pacientes.class) );
            }
        }

        ListaDePacientes.setItems(listaPersonal);
        */
        ListaDePacientes.setItems(Datos.pacientes);


    }

    private void cargarCitasFechaHora(int idPaciente) throws IOException {

        ObservableList<CitaPacienteFechaHora> listaCitas = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Citas: Lista historial");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        paramsJSON.put("idPaciente", idPaciente);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaCitas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.CitaPacienteFechaHora.class) );
            }
        }

        ListaHistorial.setItems(listaCitas);

    }

    private void cargarCitas() throws IOException {

        ObservableList<CitaPaciente> listaCitas = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Citas: Lista");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        paramsJSON.put("fecha", Fecha.getValue().toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaCitas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.CitaPaciente.class) );
            }
        }

        ListaDeCitas.setItems(listaCitas);

    }

    @Override
    public void init() {
        try {
            cargarPacientes();
            Fecha.setValue(LocalDate.now() );
            cargarCitas();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListaDePacientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                try {
                    cargarDatosPantalla(ListaDePacientes.getSelectionModel().getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        });
        ListaDeCitas.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                Map<String,Object> paramsVista = new LinkedHashMap<>();
                paramsVista.put("idClinica", 1);
                paramsVista.put("idPaciente", ListaDeCitas.getSelectionModel().getSelectedItem().getIdPaciente());
                paramsVista.put("idCita", ListaDeCitas.getSelectionModel().getSelectedItem().getIdCita());
                paramsVista.put("nombre", ListaDeCitas.getSelectionModel().getSelectedItem().getNombre());
                paramsVista.put("fecha", ListaDeCitas.getSelectionModel().getSelectedItem().getFecha());
                paramsVista.put("vista", "/vista/consultas.fxml" );
                try {
                    Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Productos());
                } catch (IOException e) {

                }
            }
        });



    }

    private void cargarDatosPantalla(modelo.Pacientes p ) throws IOException {
        Nombre.setText(p.getNombre());
        Apellidos.setText(p.getApellidos());
        Telefono.setText(p.getTelefono());
        Celular.setText(p.getMovil());
        Email.setText(p.getEmail());
        Hora.setValue(null);
        if(p.getIdPaciente()!=-1)
            cargarCitasFechaHora(p.getIdPaciente());

    }
}
