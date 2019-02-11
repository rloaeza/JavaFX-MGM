package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXListView;
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

public class CostoProductos extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXListView<modelo.Productos> ListaDeProductos;

    @FXML
    private JFXTextField Clave;

    @FXML
    private JFXTextField Nombre;

    @FXML
    private JFXTextField Costo;

    @FXML
    private JFXListView<modelo.PrecioProductos> ListaDeCostos;



    @FXML
    private Label Titulo;


    @FXML
    void actualizar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Precio Productos: Actualizar");
        paramsJSON.put("precio", Costo.getText());
        paramsJSON.put("idPrecioProducto", ListaDeCostos.getSelectionModel().getSelectedItem().getIdPrecioProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPrecios(ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());

    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Precio Productos: Agregar");
        paramsJSON.put("precio", Costo.getText());
        paramsJSON.put("idProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPrecios(ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Precio Productos: Eliminar");
        paramsJSON.put("idPrecioProducto", ListaDeCostos.getSelectionModel().getSelectedItem().getIdPrecioProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarPrecios(ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Productos(-1, -1,"", "","",-1, ""));
        ListaDeProductos.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new TiposProductos());
    }


    @Override
    public void init() {

        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void cargarDatosPantalla(modelo.Productos p ) {
        Clave.setText(p.getClave());
        Nombre.setText(p.getNombre());

        try {
            cargarPrecios(p.getIdProducto());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cargarDatos() throws IOException {

        ObservableList<modelo.Productos> listaProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Precio Productos: Lista productos");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Productos.class) );
            }
        }

        ListaDeProductos.setItems(listaProductos);

    }

    private void cargarPrecios(int idProducto) throws IOException {

        ObservableList<modelo.PrecioProductos> listaPrecios = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Precio Productos: Lista precios de producto");
        paramsJSON.put("idProducto", idProducto);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaPrecios.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.PrecioProductos.class) );
            }
        }

        ListaDeCostos.setItems(listaPrecios);

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
