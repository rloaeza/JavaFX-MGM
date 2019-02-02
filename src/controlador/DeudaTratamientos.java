package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.DeudasTratamientos;
import modelo.Funciones;
import modelo.Productos;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeudaTratamientos extends Controlador{

    @FXML
    private JFXListView<DeudasTratamientos> ListaDePacientes;

    @FXML
    private JFXListView<modelo.PagoTratamiento> ListaDePagos;

    @FXML
    private Label Titulo;
    @FXML
    private JFXTextField Buscar;

    @FXML
    private JFXTextField Cantidad;

    @FXML
    private AnchorPane Pane;

    public DeudaTratamientos() {
        super();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        DeudasTratamientos p = ListaDePacientes.getSelectionModel().getSelectedItem();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos recetados: Realizar pago");
        paramsJSON.put("idTratamientoRecetado", p.getIdTratamientoRecetado());
        paramsJSON.put("cantidad", Cantidad.getText());


        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPagos();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos recetados: Eliminar pago");
        paramsJSON.put("idPagoTratamiento", ListaDePagos.getSelectionModel().getSelectedItem().getIdPagoTratamiento());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPagos();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new Tratamientos());
    }




    @Override
    public void init() {
        //Titulo.setText(parametros.get(0).get("TituloTratamiento").toString());

        ListaDePacientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                try {
                    Cantidad.setText(ListaDePacientes.getSelectionModel().getSelectedItem().getCantidad()+"");
                    cargarPagos();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            cargarDeudas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDeudas() throws IOException {

        ObservableList<DeudasTratamientos> listaPacientes = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos recetados: Pagos pendientes con datos");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPacientes.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), DeudasTratamientos.class) );
            }
        }

        ListaDePacientes.setItems(listaPacientes);

    }

    private void cargarPagos() throws IOException {

        ObservableList<modelo.PagoTratamiento> listaTratamiento = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos recetados: Pagos realizados");
        paramsJSON.put("idTratamientoRecetado", ListaDePacientes.getSelectionModel().getSelectedItem().getIdTratamientoRecetado());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaTratamiento.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.PagoTratamiento.class) );
            }
        }

        ListaDePagos.setItems(listaTratamiento);

    }
}
