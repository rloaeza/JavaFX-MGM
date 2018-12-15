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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import modelo.AlmacenProductos;
import modelo.Funciones;
import modelo.Productos;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AlmacenSalida extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTextField Patron;

    @FXML
    private JFXListView<AlmacenProductos> ListaDeProductos;

    @FXML
    private JFXListView<modelo.AlmacenEntrada> ListaDeEntradas;

    @FXML
    private Label Producto;

    @FXML
    private JFXTextField Cantidad;

    @FXML
    private Button Agregar;

    @FXML
    private Label Titulo;

    @FXML
    private ImageView Eliminar;

    @FXML
    void imprimir(ActionEvent event) throws IOException {

    }

    @FXML
    void aceptar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Autorizar salidas");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarAlmacenEntradas();
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        double cantidadDouble;
        if(Cantidad.getText().isEmpty())
            cantidadDouble = 1;
        else
            cantidadDouble = Double.valueOf(Cantidad.getText());

        if (cantidadDouble <= 0) {
            Map<String, Object> paramsAlert = new LinkedHashMap<>();
            paramsAlert.put("titulo", "Error");
            paramsAlert.put("texto", "Solo se aceptan valores positivos");
            paramsAlert.put("vista", "/vista/alert_box.fxml");
            Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox());
            return;
        }
        if(cantidadDouble>ListaDeProductos.getSelectionModel().getSelectedItem().getCantidad()) {
            Map<String, Object> paramsAlert = new LinkedHashMap<>();
            paramsAlert.put("titulo", "Error");
            paramsAlert.put("texto", "No es posible sacar mas productos de los existentes");
            paramsAlert.put("vista", "/vista/alert_box.fxml");
            Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox());
            return;
        }


        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Agregar salidas");
        paramsJSON.put("cantidad", Cantidad.getText().isEmpty()?"1":Cantidad.getText());
        paramsJSON.put("idProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarAlmacenEntradas();
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Eliminar");
        paramsJSON.put("idAlmacen", ListaDeEntradas.getSelectionModel().getSelectedItem().getIdAlmacen());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarAlmacenEntradas();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        parametros.remove(0);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
    }


    @Override
    public void init() {
        try {
            cargarProductos();
            cargarAlmacenEntradas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarProductos() throws IOException {
        ObservableList<AlmacenProductos> listaProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Lista con cantidad");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), AlmacenProductos.class) );
            }
        }

        ListaDeProductos.setItems(listaProductos);
    }


    private void cargarAlmacenEntradas() throws IOException {
        ObservableList<modelo.AlmacenEntrada> listaAlmacen = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Lista");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaAlmacen.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.AlmacenEntrada.class) );
            }
        }

        ListaDeEntradas.setItems(listaAlmacen);
        cargarProductos();
    }

    private void cargarProducto(AlmacenProductos p) {
        Producto.setText(p.getNombre());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListaDeProductos.setOnMouseClicked(event -> {
            cargarProducto( ListaDeProductos.getSelectionModel().getSelectedItem() );

        });
    }
}
