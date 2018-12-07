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
import javafx.scene.layout.StackPane;
import modelo.Funciones;
import modelo.Productos;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DetalleTratamientos extends Controlador{

    @FXML
    private JFXListView<modelo.Productos> ListaDeProductos;

    @FXML
    private JFXListView<modelo.DetalleTratamientos> Tratamiento;

    @FXML
    private Label Titulo;
    @FXML
    private JFXTextField Buscar;

    @FXML
    private JFXTextField Cantidad;

    @FXML
    private AnchorPane Pane;

    public DetalleTratamientos() {
        super();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Productos p = ListaDeProductos.getSelectionModel().getSelectedItem();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Detalle tratamiento: Agregar");

        paramsJSON.put("idProducto", p.getIdProducto());
        paramsJSON.put("idTratamiento", parametros.get(0).get("idTratamiento"));
        paramsJSON.put("cantidad", Cantidad.getText());


        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarTratamiento();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Detalle tratamiento: Eliminar");
        paramsJSON.put("idDetalleTratamiento", Tratamiento.getSelectionModel().getSelectedItem().getIdDetalleTratamiento());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarTratamiento();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        System.out.println("Tam->"+parametros.size());
        Funciones.CargarVista2((AnchorPane)Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new Tratamientos());
    }


    @FXML
    void buscar(ActionEvent event) throws IOException {
        String patron = Buscar.getText().toString();
        if(patron.length()!=0)
            cargarProductosConPatron(patron);
        else
            cargarProductos();
    }

    @Override
    public void init() {
        Titulo.setText(parametros.get(0).get("TituloTratamiento").toString());
        try {
            cargarProductos();
            cargarTratamiento();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarProductos() throws IOException {

        ObservableList<Productos> listaProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Lista total");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Productos.class) );
            }
        }

        ListaDeProductos.setItems(listaProductos);

    }
    private void cargarProductosConPatron(String patron) throws IOException {

        ObservableList<Productos> listaProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Lista total con patron");
        paramsJSON.put("patron", patron);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Productos.class) );
            }
        }

        ListaDeProductos.setItems(listaProductos);

    }
    private void cargarTratamiento() throws IOException {

        ObservableList<modelo.DetalleTratamientos> listaTratamiento = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Detalle tratamiento: Lista");
        paramsJSON.put("idTratamiento", parametros.get(0).get("idTratamiento"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaTratamiento.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.DetalleTratamientos.class) );
            }
        }

        Tratamiento.setItems(listaTratamiento);

    }
}
