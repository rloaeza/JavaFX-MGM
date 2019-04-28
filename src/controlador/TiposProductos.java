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
import javafx.scene.layout.AnchorPane;
import modelo.Configuraciones;
import modelo.Funciones;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TiposProductos extends Controlador implements Initializable {




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
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "TiposProductos: Actualizar");
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("idClinica", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdClinica());
        paramsJSON.put("idTipoProducto", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdTipoProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);


        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Actualizar tipo de producto");
        paramsAlert.put("vista", "/vista/alert_box.fxml");
        if(insercionCorrectaSQL(rootArray) ) {
            paramsAlert.put("texto",  "Tipo de producto actualizado, clave: "+Clave.getText());
            limpiar(null);
            cargarDatos();

        }
        else {
            paramsAlert.put("texto",  "Error al actualizar, clave duplicada");

        }
        Funciones.display(paramsAlert, this.getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "TiposProductos: Agregar");
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("idClinica", 1);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);

        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Insertar tipo de producto");
        paramsAlert.put("vista", "/vista/alert_box.fxml");
        if(insercionCorrectaSQL(rootArray) ) {
            paramsAlert.put("texto",  "Tipo de producto insertado, clave: "+Clave.getText());
            limpiar(null);

        }
        else {
            paramsAlert.put("texto",  "Error al insertar, clave duplicada");

        }

        Funciones.display(paramsAlert, this.getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
        cargarDatos();

    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "TiposProductos: Eliminar");
        paramsJSON.put("idTipoProducto", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdTipoProducto());



        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Eliminar tipo de producto");
        paramsAlert.put("vista", "/vista/alert_box.fxml");
        if(insercionCorrectaSQL(rootArray) ) {
            paramsAlert.put("texto",  "Tipo de producto eliminado, clave: "+Clave.getText());
            limpiar(null);

        }
        else {
            paramsAlert.put("texto",  "Error al eliminar, contiene productos");

        }

        Funciones.display(paramsAlert, this.getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
        cargarDatos();



    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.TiposProductos(-1, -1,"", "",""));
        ListaTiposDeProductos.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        //parametros.remove(0);
        //Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
        cargarVista(Pane, new InicioAdministrador(), Configuraciones.panelInicial);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListaTiposDeProductos.setOnMouseClicked(event -> {
            if(event.getClickCount()==1) {
                ListaTiposDeProductos.getSelectionModel().getSelectedIndex();

                cargarDatosPantalla(ListaTiposDeProductos.getSelectionModel().getSelectedItem());

            }
            else if(event.getClickCount()==2) {
                try {
                    Map<String,Object> params = new LinkedHashMap<>();
                    params.put("TituloTipoProducto", ListaTiposDeProductos.getSelectionModel().getSelectedItem().toString());
                    params.put("idTipoProducto", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdTipoProducto());
                    params.put("idClinica", ListaTiposDeProductos.getSelectionModel().getSelectedItem().getIdClinica());
                    params.put("vista", "/vista/productos.fxml");
                    Configuraciones.panelAnterior = "/vista/tipos_productos.fxml";
                    Funciones.CargarVista(Pane, getClass().getResource(params.get("vista").toString()), params, new Productos());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });



    }
    private void cargarDatosPantalla(modelo.TiposProductos p ) {
        Clave.setText(p.getClave());
        Nombre.setText(p.getNombre());
        Descripcion.setText(p.getDescripcion());
    }


    private void cargarDatos() throws IOException {

        ObservableList<modelo.TiposProductos> listaTiposProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "TiposProductos: Lista");
        paramsJSON.put("idClinica", "1");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaTiposProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.TiposProductos.class) );
            }
        }

        ListaTiposDeProductos.setItems(listaTiposProductos);



    }

    @Override
    public void init() {

    }
}
