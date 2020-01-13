package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.*;
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

public class CostoProductosTodos extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;


    @FXML
    JFXTreeTableView<ProductosConCosto> tvProductos;

    private ObservableList<ProductosConCosto> listaProductos = FXCollections.observableArrayList();


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


        JFXTreeTableColumn<ProductosConCosto, String> columnCantidad = new JFXTreeTableColumn<>("Clave");
        columnCantidad.setPrefWidth(100);
        columnCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.ProductosConCosto, String> param) ->  {
            if (columnCantidad.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getClave()+"");
            }
            else {
                return columnCantidad.getComputedValue(param);

            }
        });
        columnCantidad.setStyle("-fx-alignment: CENTER;");




        JFXTreeTableColumn<ProductosConCosto, String> columnNombre = new JFXTreeTableColumn<>("Producto");
        columnNombre.setPrefWidth(300);
        columnNombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.ProductosConCosto, String> param) ->  {
            if (columnNombre.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getNombre());
            }
            else {
                return columnNombre.getComputedValue(param);

            }
        });
        columnNombre.setStyle("-fx-alignment: CENTER;");

        JFXTreeTableColumn<ProductosConCosto, String> columnDescripcion = new JFXTreeTableColumn<>("Descripción");
        columnDescripcion.setPrefWidth(500);
        columnDescripcion.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.ProductosConCosto, String> param) ->  {
            if (columnDescripcion.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getDescripcion());
            }
            else {
                return columnDescripcion.getComputedValue(param);

            }
        });
        columnDescripcion.setStyle("-fx-alignment: CENTER;");



        JFXTreeTableColumn<ProductosConCosto, String> columnPrecioActual = new JFXTreeTableColumn<>("Precio Actual");
        columnPrecioActual.setPrefWidth(100);
        columnPrecioActual.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.ProductosConCosto, String> param) ->  {
            if (columnPrecioActual.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getPrecio()==-1?"":Funciones.valorAmoneda(param.getValue().getValue().getPrecio()));
            }
            else {
                return columnPrecioActual.getComputedValue(param);

            }
        });
        columnPrecioActual.setStyle("-fx-alignment: CENTER;");

        columnPrecioActual.setCellFactory((TreeTableColumn<modelo.ProductosConCosto, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );

        columnPrecioActual.setOnEditCommit((TreeTableColumn.CellEditEvent<ProductosConCosto, String> t) -> {
                int index = t.getTreeTablePosition().getRow();
                ProductosConCosto productosConCosto = listaProductos.get(index);
                try {
                    if(actualizarPrecio(productosConCosto, Double.valueOf(t.getNewValue()))) {
                        listaProductos.get(index).setPrecio(Double.valueOf(t.getNewValue()));
                        Funciones.mostrarMSG_No_Modal("Costos", "Costo agregado satisfactoriamente", 5);
                    }
                    else {
                        listaProductos.get(index).setPrecio(Double.valueOf(t.getOldValue()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        );







        //listaProductos = Datos.productosConCostos;
        cargarProductos();
        TreeItem<ProductosConCosto> root = new RecursiveTreeItem<>(listaProductos, RecursiveTreeObject::getChildren);



        tvProductos.getColumns().addAll( columnCantidad, columnNombre, columnDescripcion, columnPrecioActual);
        tvProductos.setRoot(root);
        tvProductos.setEditable(true);
        tvProductos.setShowRoot(false);




    }


    public void cargarProductos() {
        listaProductos.clear();

        String clase = "";
        int t = Datos.productosConCostos.size();
        for (int i = 0; i < t; i++) {

            String nuevaClase = Datos.productosConCostos.get(i).getClase() + "";
            if (!clase.equalsIgnoreCase(nuevaClase)) {
                clase = nuevaClase;
                ProductosConCosto pc = new ProductosConCosto(-1, -1, "******", clase, "", -1, -1, "", -1, -1, -1, "");
                listaProductos.add(pc);
            }

            listaProductos.add(Datos.productosConCostos.get(i));
        }
        tvProductos.refresh();
    }



    boolean actualizarPrecio(ProductosConCosto p, Double nuevoPrecio) throws IOException {
        if( p.getPrecio() == -1)
            return  false;

        if ( p.getPrecio() == nuevoPrecio)
            return false;
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
}
