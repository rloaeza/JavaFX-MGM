package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn.CellEditEvent;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import modelo.Funciones;
import modelo.ProductosConCosto;
import modelo.Tratamientos;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VentaMostrador extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTreeTableView<modelo.VentaMostrador> TablaVenta;

    @FXML
    private JFXListView<ProductosConCosto> ListaDeProductos;

    @FXML
    private JFXTextField Busqueda;

    @FXML
    private JFXTextField Cantidad;

    @FXML
    private Label CantidadProductos;

    @FXML
    private Label Subtotal;

    @FXML
    private Label IVA;

    @FXML
    private Label Total;

    private ObservableList<modelo.VentaMostrador> listaVentaMostrador;

    @Override
    public void init() {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFXTreeTableColumn<modelo.VentaMostrador, String> columnCantidad = new JFXTreeTableColumn<>("Cant");
        columnCantidad.setPrefWidth(100);
        columnCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.VentaMostrador, String> param) ->  {
            if (columnCantidad.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getCantidad());
            }
            else {
                return columnCantidad.getComputedValue(param);

            }
            });

        columnCantidad.setCellFactory((TreeTableColumn<modelo.VentaMostrador, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );



        JFXTreeTableColumn<modelo.VentaMostrador, String> columnProducto = new JFXTreeTableColumn<>("Producto");
        columnProducto.setPrefWidth(380);
        columnProducto.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<modelo.VentaMostrador, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<modelo.VentaMostrador, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getProducto()) ;
            }
        });

        JFXTreeTableColumn<modelo.VentaMostrador, Double> columnCosto = new JFXTreeTableColumn<>("Costo");
        columnCosto.setPrefWidth(150);
        columnCosto.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<modelo.VentaMostrador, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TreeTableColumn.CellDataFeatures<modelo.VentaMostrador, Double> param) {
                return new ReadOnlyObjectWrapper<Double>(param.getValue().getValue().getCosto()) ;
            }
        });
        columnCosto.setStyle("-fx-alignment: CENTER;");


        JFXTreeTableColumn<modelo.VentaMostrador, Double> columnSubTotal = new JFXTreeTableColumn<>("SubTotal");
        columnSubTotal.setPrefWidth(150);
        columnSubTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<modelo.VentaMostrador, Double> param) ->
                 new ReadOnlyObjectWrapper<>(param.getValue().getValue().getTotal())

        );
        columnSubTotal.setStyle("-fx-alignment: CENTER;");





        columnCantidad.setStyle("-fx-alignment: CENTER;");
        columnCantidad.setOnEditCommit((CellEditEvent<modelo.VentaMostrador, String> t) -> {
            /*
            t.getTreeTableView()
                            .getTreeItem(t.getTreeTablePosition()
                                    .getRow())
                            .getValue().setCantidad(t.getNewValue());
                    */

                    int index = t.getTreeTablePosition().getRow();
                    double costo = Double.valueOf(listaVentaMostrador.get(index).getCosto());
                    int cantidad = Integer.valueOf(t.getNewValue());
                    String producto = listaVentaMostrador.get(index).getProducto();

                    modelo.VentaMostrador vM = new modelo.VentaMostrador(cantidad,producto,costo);
                    listaVentaMostrador.remove(index);
                    listaVentaMostrador.add( vM);
                    t.getTreeTableView().getSelectionModel().select(listaVentaMostrador.size()-1);

                    if(cantidad<=0) {
                        listaVentaMostrador.remove(listaVentaMostrador.size()-1);
                    }

                }
        );



        listaVentaMostrador = FXCollections.observableArrayList();
/*
        listaVentaMostrador.addListener((ListChangeListener) change -> {
            System.out.println("cambiando...");
            while(change.next()) {
                if(change.wasUpdated()) {
                    listaVentaMostrador.get(change.getFrom()).setTotal("22");
                }
            }
        });
*/

        listaVentaMostrador.add(new modelo.VentaMostrador(1,"Crema I",10));
        listaVentaMostrador.add(new modelo.VentaMostrador(2,"Crema II",22));
        listaVentaMostrador.add(new modelo.VentaMostrador(3,"Crema III",33));


        TreeItem<modelo.VentaMostrador> root = new RecursiveTreeItem<>(listaVentaMostrador, RecursiveTreeObject::getChildren);



        TablaVenta.getColumns().addAll(columnCantidad, columnProducto, columnCosto, columnSubTotal);
        TablaVenta.setRoot(root);
        TablaVenta.setEditable(true);
        TablaVenta.setShowRoot(false);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void cargarDatos() throws IOException {

        ObservableList<ProductosConCosto> listaDeProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Lista con precio");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica").toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaDeProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.ProductosConCosto.class) );
            }
        }

        ListaDeProductos.setItems(listaDeProductos);

    }

}
