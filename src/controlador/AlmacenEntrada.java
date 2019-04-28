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
import modelo.Funciones;
import modelo.Productos;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AlmacenEntrada extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTextField Patron;

    @FXML
    private JFXListView<Productos> ListaDeProductos;

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
        paramsJSON.put("Actividad", "Almacen: Autorizar entradas");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        cargarAlmacenEntradas();
    }

    @FXML
    void cargarPatron(ActionEvent event) throws IOException {
        cargarProductos(Patron.getText());
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        if(!Cantidad.getText().isEmpty())
            if(Double.valueOf(Cantidad.getText())<=0) {
                Map<String,Object> paramsAlert = new LinkedHashMap<>();
                paramsAlert.put("titulo", "Error");
                paramsAlert.put("texto", "Solo se aceptan valores positivos");
                paramsAlert.put("vista", "/vista/alert_box.fxml");
                Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
                return;
            }
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Agregar entradas");
        paramsJSON.put("cantidad", Cantidad.getText().isEmpty()?"1":Cantidad.getText());
        paramsJSON.put("idProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        Cantidad.setText("");
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
            cargarProductos("");
            cargarAlmacenEntradas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarProductos(String patron) throws IOException {
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


    private void cargarAlmacenEntradas() throws IOException {
        ObservableList<modelo.AlmacenEntrada> listaAlmacen = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Lista entradas");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaAlmacen.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.AlmacenEntrada.class) );
            }
        }

        ListaDeEntradas.setItems(listaAlmacen);
    }

    private void cargarProducto(Productos p) {
        Producto.setText(p.getNombre());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListaDeProductos.setOnMouseClicked(event -> {
            cargarProducto( ListaDeProductos.getSelectionModel().getSelectedItem() );

        });
    }
}
