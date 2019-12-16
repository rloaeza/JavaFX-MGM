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
import modelo.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AlmacenExistencias extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;


    @FXML
    JFXTreeTableView<VistaReporte> tvProductos;

    private ObservableList<VistaReporte> listaProductos = FXCollections.observableArrayList();


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


        JFXTreeTableColumn<VistaReporte, String> columnClave = new JFXTreeTableColumn<>("Clave");
        columnClave.setPrefWidth(100);
        columnClave.setCellValueFactory((TreeTableColumn.CellDataFeatures<VistaReporte, String> param) ->  {
            if (columnClave.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getDato("Clave"));
            }
            else {
                return columnClave.getComputedValue(param);

            }
        });
        columnClave.setStyle("-fx-alignment: CENTER;");




        JFXTreeTableColumn<VistaReporte, String> columnNombre = new JFXTreeTableColumn<>("Producto");
        columnNombre.setPrefWidth(500);
        columnNombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<VistaReporte, String> param) ->  {
            if (columnNombre.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getDato("Producto"));
            }
            else {
                return columnNombre.getComputedValue(param);

            }
        });
        //columnNombre.setStyle("-fx-alignment: CENTER;");


        JFXTreeTableColumn<VistaReporte, String> columnExistencia = new JFXTreeTableColumn<>("Existencia");
        columnExistencia.setPrefWidth(100);
        columnExistencia.setCellValueFactory((TreeTableColumn.CellDataFeatures<VistaReporte, String> param) ->  {
            if (columnExistencia.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getDato("Existencia"));

            }
            else {
                return columnExistencia.getComputedValue(param);

            }
        });
        columnExistencia.setStyle("-fx-alignment: CENTER;");

        columnExistencia.setCellFactory((TreeTableColumn<VistaReporte, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );

        columnExistencia.setOnEditCommit((TreeTableColumn.CellEditEvent<VistaReporte, String> t) -> {
                int index = t.getTreeTablePosition().getRow();
                //modelo.AlmacenExistencias productosConCosto = listaProductos.get(index);



            }
        );







        listaProductos =  FXCollections.observableArrayList();
        cargarProductos();

        TreeItem<VistaReporte> root = new RecursiveTreeItem<>(listaProductos, RecursiveTreeObject::getChildren);



        tvProductos.getColumns().addAll( columnClave, columnNombre, columnExistencia);
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
        paramsJSON.put("Actividad", "Almacen: Existencias");
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                System.out.println(i+" >> "+rootArray.get(i).getAsJsonObject());

                Map<String, Object> v = new LinkedHashMap<>();
                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());
                listaProductos.add(new VistaReporte(v));
            }
        }

    }
}
