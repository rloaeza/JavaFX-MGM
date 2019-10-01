package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import modelo.AlmacenEntrada;
import modelo.Datos;
import modelo.Funciones;
import modelo.Productos;
import modelo.VentaMostrador;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AlmacenEntrada2 extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;


    @FXML
    private JFXListView<modelo.AlmacenEntrada> ListaDeEntradas;

    @FXML
    private JFXTreeTableView<modelo.AlmacenEntrada2> tvEntrada;


    private ObservableList<modelo.AlmacenEntrada2> listaEntrada;

    @FXML
    private Label Titulo;





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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTitulosTabla();

    }



    private void cargarTitulosTabla() {

        JFXTreeTableColumn<modelo.AlmacenEntrada2, String> columnProducto = new JFXTreeTableColumn<>("Producto");
        columnProducto.setPrefWidth(580);
        columnProducto.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getProducto()));


        JFXTreeTableColumn<modelo.AlmacenEntrada2, String> columnExistencia = new JFXTreeTableColumn<>("Existencia");
        columnExistencia.setPrefWidth(100);
        columnExistencia.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getExistencia()+""));
        columnExistencia.setStyle("-fx-alignment: CENTER;");



        JFXTreeTableColumn<modelo.AlmacenEntrada2, String> columnCantidad = new JFXTreeTableColumn<>("Cantidad");
        columnCantidad.setPrefWidth(100);
        columnCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.AlmacenEntrada2, String> param) ->  {

            if (columnCantidad.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getCantidad()+"");
            }
            else {
                return columnCantidad.getComputedValue(param);

            }

        });

        columnCantidad.setCellFactory((TreeTableColumn<modelo.AlmacenEntrada2, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );
        columnCantidad.setStyle("-fx-alignment: CENTER;");



        columnCantidad.setOnEditCommit((TreeTableColumn.CellEditEvent<modelo.AlmacenEntrada2, String> t) -> {

                    int index = t.getTreeTablePosition().getRow();
                    int cantidad = Integer.valueOf(t.getNewValue());

                    listaEntrada.get(index).setCantidad(cantidad);


                }

        );

        listaEntrada = FXCollections.observableArrayList();
        TreeItem<modelo.AlmacenEntrada2> root = new RecursiveTreeItem<>(listaEntrada, RecursiveTreeObject::getChildren);

        //tvEntrada = new JFXTreeTableView<>();

        tvEntrada.getColumns().addAll(columnProducto, columnExistencia, columnCantidad);



        tvEntrada.setRoot(root);
        tvEntrada.setEditable(true);
        tvEntrada.setShowRoot(false);

        for(Productos p : Datos.productosTotales) {
            listaEntrada.add(new modelo.AlmacenEntrada2(1,p.getIdProducto(),p.getNombre(), 3,0));
        }

        listaEntrada.add(new modelo.AlmacenEntrada2(1,2,"Producto falso", 3,0));
    }









}
