package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TiposProductos implements Initializable {




    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.TiposProductos> ListaTiposDeProductos;

    @FXML
    private JFXTextField Clave;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextArea Descripcion;

    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("Actividad", "TiposProductos: Actualizar");
        params.put("clave", Clave.getText());
        params.put("nombre", Nombre.getText());
        params.put("descripcion", Descripcion.getText());
        params.put("idClinica", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdClinica());
        params.put("idTipoProducto", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdTipoProducto());
        JsonArray rootArray = Funciones.consultarBD(params);
        cargarDatos();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("Actividad", "TiposProductos: Agregar");
        params.put("clave", Clave.getText());
        params.put("nombre", Nombre.getText());
        params.put("descripcion", Descripcion.getText());
        params.put("idClinica", 1);
        JsonArray rootArray = Funciones.consultarBD(params);
        cargarDatos();

    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("Actividad", "TiposProductos: Eliminar");
        params.put("idTipoProducto", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdTipoProducto());



        JsonArray rootArray = Funciones.consultarBD(params);
        cargarDatos();



    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.TiposProductos(-1, -1,"", "",""));
        ListaTiposDeProductos.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) {

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListaTiposDeProductos.setOnMouseClicked(event -> {
            ListaTiposDeProductos.getSelectionModel().getSelectedIndex();
            cargarDatosPantalla(ListaTiposDeProductos.getSelectionModel().getSelectedItem());
        });

    }
    private void cargarDatosPantalla(modelo.TiposProductos p ) {
        Clave.setText(p.getClave());
        Nombre.setText(p.getNombre());
        Descripcion.setText(p.getDescripcion());
    }


    private void cargarDatos() throws IOException {

        ObservableList<modelo.TiposProductos> listaTiposProductos = FXCollections.observableArrayList();

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("Actividad", "TiposProductos: Lista");
        params.put("idClinica", "1");
        JsonArray rootArray = Funciones.consultarBD(params);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaTiposProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.TiposProductos.class) );
            }
        }

        ListaTiposDeProductos.setItems(listaTiposProductos);

    }

}
