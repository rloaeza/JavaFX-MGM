package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import modelo.Funciones;
import modelo.Productos;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DetalleTratamientos extends Controlador {

    @FXML
    private JFXListView<modelo.Productos> ListaDeProductos;

    @FXML
    private JFXListView<modelo.Productos> Tratamiento;

    @FXML
    private Label Titulo;

    @FXML
    void agregar(ActionEvent event) {

    }

    @FXML
    void eliminar(ActionEvent event) {

    }

    @FXML
    void regresar(ActionEvent event) {

    }

    @Override
    public void init() {
        super.init();
        Titulo.setText(params.get("TituloTratamiento").toString());
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

    private void cargarTratamiento() throws IOException {

        ObservableList<Productos> listaTratamiento = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Detalle Tratamiento: Lista");
        paramsJSON.put("idTratamiento", params.get("idTratamiento"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaTratamiento.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Productos.class) );
            }
        }

        Tratamiento.setItems(listaTratamiento);

    }
}
