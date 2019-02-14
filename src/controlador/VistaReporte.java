package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import modelo.Funciones;
import modelo.ProductosConCosto;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VistaReporte extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTreeTableView<modelo.VistaReporte> TablaReporte;



    @FXML
    private Label titulo;

    @FXML
    private Label descripcon;

    @FXML
    void imprimir(ActionEvent event) {

    }

    @FXML
    void descargar(ActionEvent event) {

    }

    @FXML
    void aceptar(ActionEvent event) {

    }
    private ObservableList<modelo.VistaReporte> listaReporte;

    @Override
    public void init() {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listaReporte = FXCollections.observableArrayList();
        TreeItem<modelo.VistaReporte> root = new RecursiveTreeItem<>(listaReporte, RecursiveTreeObject::getChildren);

        for(int i = 0; i<10; i++) {
            JFXTreeTableColumn<modelo.VistaReporte, String> column = new JFXTreeTableColumn<>("Cant");
            column.setPrefWidth(100);
            column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<modelo.VistaReporte, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<modelo.VistaReporte, String> param) {
                    return new SimpleStringProperty(param.getValue().getValue().getDato("Cant")) ;
                }
            });
            column.setStyle("-fx-alignment: CENTER;");
            TablaReporte.getColumns().add(column);
        }


        TablaReporte.setRoot(root);

        TablaReporte.setEditable(true);
        TablaReporte.setShowRoot(false);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void cargarDatos() throws IOException {
/*
        ObservableList<ProductosConCosto> listaDeProductos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Lista con precio");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica").toString());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listaDeProductos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), ProductosConCosto.class) );
            }
        }

        ListaDeProductos.setItems(listaDeProductos);
*/
    }

}
