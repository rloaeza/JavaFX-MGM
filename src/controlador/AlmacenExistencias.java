package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import modelo.Clinica;
import modelo.Datos;
import modelo.Funciones;
import modelo.ProductosConCosto;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AlmacenExistencias extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;


    @FXML
    JFXTreeTableView<modelo.AlmacenExistencias> tvProductos;

    private ObservableList<modelo.AlmacenExistencias> listaProductos = FXCollections.observableArrayList();


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




    private void cargarDatos() throws IOException {


        JFXTreeTableColumn<modelo.AlmacenExistencias, String> columnCantidad = new JFXTreeTableColumn<>("Clave");
        columnCantidad.setPrefWidth(100);
        columnCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.AlmacenExistencias, String> param) ->  {
            if (columnCantidad.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getClave()+"");
            }
            else {
                return columnCantidad.getComputedValue(param);

            }
        });
        columnCantidad.setStyle("-fx-alignment: CENTER;");




        JFXTreeTableColumn<modelo.AlmacenExistencias, String> columnNombre = new JFXTreeTableColumn<>("Producto");
        columnNombre.setPrefWidth(300);
        columnNombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.AlmacenExistencias, String> param) ->  {
            if (columnNombre.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getProducto());
            }
            else {
                return columnNombre.getComputedValue(param);

            }
        });
        columnNombre.setStyle("-fx-alignment: CENTER;");


        JFXTreeTableColumn<modelo.AlmacenExistencias, String> columnPrecioActual = new JFXTreeTableColumn<>("Existencia");
        columnPrecioActual.setPrefWidth(100);
        columnPrecioActual.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.AlmacenExistencias, String> param) ->  {
            if (columnPrecioActual.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getExistencia()+"");
            }
            else {
                return columnPrecioActual.getComputedValue(param);

            }
        });
        columnPrecioActual.setStyle("-fx-alignment: CENTER;");

        columnPrecioActual.setCellFactory((TreeTableColumn<modelo.AlmacenExistencias, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );

        columnPrecioActual.setOnEditCommit((TreeTableColumn.CellEditEvent<modelo.AlmacenExistencias, String> t) -> {
                int index = t.getTreeTablePosition().getRow();
                modelo.AlmacenExistencias productosConCosto = listaProductos.get(index);



            }
        );







        listaProductos =  FXCollections.observableArrayList();
        cargarProductos();

        TreeItem<modelo.AlmacenExistencias> root = new RecursiveTreeItem<>(listaProductos, RecursiveTreeObject::getChildren);



        tvProductos.getColumns().addAll( columnCantidad, columnNombre, columnPrecioActual);
        tvProductos.setRoot(root);
        tvProductos.setEditable(true);
        tvProductos.setShowRoot(false);







    }


    boolean actualizarPrecio(ProductosConCosto p, Double nuevoPrecio) throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Precio Productos: Agregar");
        paramsJSON.put("precio", nuevoPrecio);
        paramsJSON.put("idProducto", p.getIdProducto());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);

        if(insercionCorrectaSQL(rootArray) ) {
            Datos.cargarProductosConCosto();
            return true;
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void cargarProductos() throws IOException {
        listaProductos.clear();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Reportes: Existencia en almacen verificar");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                System.out.println(i+" >> "+rootArray.get(i).getAsJsonObject());
                listaProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.AlmacenExistencias.class) );
            }
        }
    }
}
