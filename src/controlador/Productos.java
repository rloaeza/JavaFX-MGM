package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Productos extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Productos> ListaDeProductos;

    @FXML
    private JFXTextField Clave;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextArea Descripcion;

    @FXML
    private JFXTextField BarCode;

    @FXML
    private Label Titulo;

    @FXML
    void actualizar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Actualizar");
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("barCode", BarCode.getText());
        paramsJSON.put("idTipoProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdTipoProducto());
        paramsJSON.put("idProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();

    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Agregar");
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("barCode", BarCode.getText());
        paramsJSON.put("idTipoProducto", params.get("idTipoProducto"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Eliminar");
        paramsJSON.put("idProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarDatos();
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Productos(-1, -1,"", "","",""));
        ListaDeProductos.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) {

    }


    @Override
    public void init() {
        super.init();
        Titulo.setText(params.get("TituloTipoProducto").toString());
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cargarDatosPantalla(modelo.Productos p ) {
        Clave.setText(p.getClave());
        Nombre.setText(p.getNombre());
        Descripcion.setText(p.getDescripcion());
        BarCode.setText(p.getBarCode());
    }

    private void cargarDatos() throws IOException {

        ObservableList<modelo.Productos> listaProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Lista");
        paramsJSON.put("idTipoProducto", params.get("idTipoProducto").toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Productos.class) );
            }
        }

        ListaDeProductos.setItems(listaProductos);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ListaDeProductos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cargarDatosPantalla(ListaDeProductos.getSelectionModel().getSelectedItem());
            }
        });
    }
}
