package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import modelo.Configuraciones;
import modelo.Funciones;
import modelo.SubirFoto;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Productos extends Controlador implements Initializable {

    File archivoOrigen = null;


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
    private JFXTextField CantidadMinima;


    @FXML
    private JFXCheckBox Tratamiento;

    @FXML
    private JFXProgressBar ImagenScroll;

    @FXML
    private ImageView ImagenProducto;


    @FXML
    private Label Titulo;


    @FXML
    void actualizar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Actualizar");
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("cantidadMinima", CantidadMinima.getText());
        paramsJSON.put("tratamiento", Tratamiento.isSelected()?1:0);
        paramsJSON.put("barCode", BarCode.getText());
        paramsJSON.put("idTipoProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdTipoProducto());
        paramsJSON.put("idProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);


        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Actualizar producto");
        paramsAlert.put("vista", "/vista/alert_box.fxml");

        if(insercionCorrectaSQL(rootArray) || archivoOrigen!=null) {
            paramsAlert.put("texto",  "Producto actualizado, clave: "+Clave.getText());
            if(archivoOrigen!=null) {
                String archivoDestino = "P" + ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto() + ".JPG";
                SubirFoto subirFoto = new SubirFoto(archivoOrigen.getAbsolutePath(), archivoDestino, "../fotos/productos", "200");
                subirFoto.setOnRunning(e -> {
                    ImagenScroll.setVisible(true);
                    ImagenProducto.setVisible(false);
                });
                subirFoto.setOnSucceeded(e -> {
                    ImagenScroll.setVisible(false);
                    ImagenProducto.setVisible(true);
                });
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(subirFoto);
                executorService.shutdown();
            }
            limpiar(null);
            cargarDatos();
        } else {
            paramsAlert.put("texto",  "Error al actualizar, clave o código de barras duplicado");

        }
        Funciones.display(paramsAlert, this.getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );



    }

    @FXML
    void agregar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Agregar");
        paramsJSON.put("clave", Clave.getText());
        paramsJSON.put("nombre", Nombre.getText());
        paramsJSON.put("descripcion", Descripcion.getText());
        paramsJSON.put("cantidadMinima", CantidadMinima.getText());
        paramsJSON.put("tratamiento", Tratamiento.isSelected()?1:0);
        paramsJSON.put("barCode", BarCode.getText());
        paramsJSON.put("idTipoProducto", parametros.get(0).get("idTipoProducto"));
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);

        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Insertar producto");
        paramsAlert.put("vista", "/vista/alert_box.fxml");

        if(insercionCorrectaSQL(rootArray) ) {
            if(archivoOrigen!=null) {
                String archivoDestino = "P" + rootArray.get(1).getAsJsonObject().get(Funciones.ultimoInsertado).getAsInt() + ".JPG";
                SubirFoto subirFoto = new SubirFoto(archivoOrigen.getAbsolutePath(), archivoDestino, "../fotos/productos", "200");
                subirFoto.setOnRunning(e -> {
                    ImagenScroll.setVisible(true);
                    ImagenProducto.setVisible(false);
                });
                subirFoto.setOnSucceeded(e -> {
                    ImagenScroll.setVisible(false);
                    ImagenProducto.setVisible(true);
                });
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(subirFoto);
                executorService.shutdown();

            }


            paramsAlert.put("texto",  "Producto insertado correctamente, clave: "+Clave.getText());
            limpiar(null);
            cargarDatos();
        }
        else {
            paramsAlert.put("texto",  "Error al insertar, clave o código de barras duplicado");

        }
        Funciones.display(paramsAlert, this.getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );

    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Eliminar");
        paramsJSON.put("idProducto", ListaDeProductos.getSelectionModel().getSelectedItem().getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        Map<String,Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "Eliminar producto");
        paramsAlert.put("vista", "/vista/alert_box.fxml");

        if(insercionCorrectaSQL(rootArray) ) {
            paramsAlert.put("texto",  "Producto eliminado, clave: "+Clave.getText());
            limpiar(null);
            cargarDatos();
        } else {
            paramsAlert.put("texto",  "Error al eliminar");

        }
        Funciones.display(paramsAlert, this.getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );


    }

    @FXML
    void limpiar(ActionEvent event) {
        cargarDatosPantalla(new modelo.Productos(-1, -1,"", "","",-1,0, ""));
        ListaDeProductos.getSelectionModel().clearSelection();
    }

    @FXML
    void regresar(ActionEvent event) throws IOException {
        //parametros.remove(0);
        //Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new TiposProductos());
        cargarVista(Pane, new InicioAdministrador(), Configuraciones.panelAnterior);
    }


    @FXML
    void cargarFoto(ActionEvent event)  {
        final FileChooser fileChooser = new FileChooser();
        archivoOrigen = fileChooser.showOpenDialog(Pane.getScene().getWindow()) ;
        if (archivoOrigen != null) {
            Image image = new Image(archivoOrigen.toURI().toString());
            ImagenProducto.setImage(image);
        }

    }

    @Override
    public void init() {
        Titulo.setText(parametros.get(0).get("TituloTipoProducto").toString());
        ImagenScroll.setVisible(false);
        ImagenProducto.setVisible(true);
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
        if(p.getCantidadMinima()==-1)
            CantidadMinima.setText("");
        else
            CantidadMinima.setText(String.valueOf( p.getCantidadMinima() ));
        BarCode.setText(p.getBarCode());


        if(p.getTratamiento()==0)
            Tratamiento.setSelected(false);
        else
            Tratamiento.setSelected(true);

        ImagenProducto.setImage(new Image("imgs/nofotoproducto.png"));
        archivoOrigen = null;
        Image image = new Image(Funciones.sitio+"../fotos/productos/P"+p.getIdProducto()+".JPG");
        ImagenProducto.setImage(image);

    }

    private void cargarDatos() throws IOException {

        ObservableList<modelo.Productos> listaProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Lista");
        paramsJSON.put("idTipoProducto", parametros.get(0).get("idTipoProducto").toString());
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
