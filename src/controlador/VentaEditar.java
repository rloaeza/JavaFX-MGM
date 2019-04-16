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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.VentaMostrador;
import modelo.*;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VentaEditar extends Controlador implements Initializable {
    private int nVenta=1;
    private int nVentaSelect = 1;
    private boolean pagoValido=false;

    @FXML
    private AnchorPane Pane;

    private ArrayList<JFXTreeTableView<VentaMostrador>> TablasVentas = new ArrayList<>();


    private ArrayList<ObservableList<VentaMostrador>> listasVentasMostrador = new ArrayList<>();



    @FXML
    private JFXTreeTableView<VentaMostrador> TablaVenta;


    @FXML
    private JFXListView<VentaLista> ListaDeProductos;

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


    @FXML
    private JFXTextField FormaPagoAuxiliar;

    @FXML
    private Label FormaPagoRespuesta;

    @FXML
    private JFXTabPane Tabs;

    @FXML
    void TabCambiando(MouseEvent event) {
        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();
        calcularTotal();
        int idVentaProductos = Integer.valueOf(Tabs.getSelectionModel().getSelectedItem().getText());

        for(int index= 0; index < ListaDeProductos.getItems().size(); index++) {
            if(idVentaProductos == ListaDeProductos.getItems().get(index).getIdVentaProductos() ) {
                ListaDeProductos.getSelectionModel().select(index);
                break;
            }
        }

    }


    @FXML
    void agregarTab(ActionEvent event) {
        Tab tab = new Tab();
        tab.setText("Venta "+nVenta++);



        TablasVentas.add(cargarTabla(new JFXTreeTableView<>() ));

        tab.setContent(TablasVentas.get(TablasVentas.size()-1));

        Tabs.getTabs().add(tab);
        Tabs.getSelectionModel().select(Tabs.getTabs().size()-1);
        calcularTotal();


    }

    private  JFXTreeTableView<VentaMostrador> cargarTabla(JFXTreeTableView<VentaMostrador> fxTreeTableView) {



        JFXTreeTableColumn<modelo.VentaMostrador, ImageView> columnImagen = new JFXTreeTableColumn<>("Imágen");
        columnImagen.setPrefWidth(80);
        columnImagen.setCellValueFactory(param -> {
                    ImageView iv = new ImageView(Funciones.sitio + "../fotos/productos/_P" + param.getValue().getValue().getIdProducto() + ".JPG");
                    iv.setFitWidth(60);
                    iv.setPreserveRatio(true);
                    return new ReadOnlyObjectWrapper<>(iv);

                }
        );
        columnImagen.setStyle("-fx-alignment: CENTER;");

        JFXTreeTableColumn<VentaMostrador, String> columnCantidad = new JFXTreeTableColumn<>("Cant");
        columnCantidad.setPrefWidth(100);
        columnCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<VentaMostrador, String> param) ->  {
            if (columnCantidad.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getCantidad());
            }
            else {
                return columnCantidad.getComputedValue(param);

            }
        });

        columnCantidad.setCellFactory((TreeTableColumn<VentaMostrador, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );
        columnCantidad.setStyle("-fx-alignment: CENTER;");



        JFXTreeTableColumn<VentaMostrador, String> columnProducto = new JFXTreeTableColumn<>("Producto");
        columnProducto.setPrefWidth(380);
        columnProducto.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getProducto()));

        JFXTreeTableColumn<VentaMostrador, String> columnCosto = new JFXTreeTableColumn<>("Costo");
        columnCosto.setPrefWidth(150);
        columnCosto.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(Funciones.valorAmoneda(param.getValue().getValue().getCosto())));
        columnCosto.setStyle("-fx-alignment: CENTER;");


        JFXTreeTableColumn<VentaMostrador, String> columnSubTotal = new JFXTreeTableColumn<>("SubTotal");
        columnSubTotal.setPrefWidth(150);
        columnSubTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<VentaMostrador, String> param) ->
                new ReadOnlyObjectWrapper<>(Funciones.valorAmoneda(param.getValue().getValue().getTotal()))

        );
        columnSubTotal.setStyle("-fx-alignment: CENTER;");



        listasVentasMostrador.add(FXCollections.observableArrayList());




        TreeItem<VentaMostrador> root = new RecursiveTreeItem<>(listasVentasMostrador.get(listasVentasMostrador.size()-1), RecursiveTreeObject::getChildren);



        fxTreeTableView.getColumns().addAll(columnImagen, columnCantidad, columnProducto, columnCosto, columnSubTotal);
        fxTreeTableView.setRoot(root);
        fxTreeTableView.setEditable(true);
        fxTreeTableView.setShowRoot(false);



        return fxTreeTableView;
    }

    @FXML
    void eliminarTab(ActionEvent event) {

        int index = Tabs.getSelectionModel().getSelectedIndex();
        Tabs.getTabs().remove(index);
        listasVentasMostrador.remove(index);
        nVenta--;
        if(nVenta==1) {
            agregarTab(null);
        }

        calcularTotal();




    }








    @Override
    public void init() {
        try {
            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ListaDeProductos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    agregarProducto(ListaDeProductos.getSelectionModel().getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });





    }



    @FXML
    void aceptarVenta(ActionEvent event) throws IOException, PrinterException {

        quitarVistas(1);
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());




    }


    @FXML
    void agregar_quitar(KeyEvent event) {
        if(event.getText().contains("+")) {
            if(!ListaDeProductos.getSelectionModel().isEmpty()) {
             //   agregarProducto(ListaDeProductos.getSelectionModel().getSelectedItem());
            }

        }
    }

    @FXML
    void eliminar(ActionEvent event) throws IOException {
        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();
        System.out.println("tab="+nVentaSelect+", fila="+TablasVentas.get(nVentaSelect).getSelectionModel().getSelectedIndex());
        if(!TablasVentas.get(nVentaSelect).getSelectionModel().isEmpty()) {


            VentaMostrador vm =  listasVentasMostrador.get(nVentaSelect).get(TablasVentas.get(nVentaSelect).getSelectionModel().getSelectedIndex());
            Map<String,Object> paramsJSON = new LinkedHashMap<>();
            paramsJSON.put("Actividad", "Venta Productos: Eliminar fila");
            paramsJSON.put("idVentaProductosDetalle", vm.getIdVentaProductosDetalle() );



            VentaLista vl = ListaDeProductos.getSelectionModel().getSelectedItem();
            vl.setCantidadProductos(vl.getCantidadProductos()-vm.getCant());
            vl.setSubtotoal(vl.getSubtotoal()-vm.getTotal());
            vl.setTotal(vl.getTotal()-vm.getTotal());

            paramsJSON.put("idVentaProductos", vl.getIdVentaProductos());
            paramsJSON.put("cantidadProductos", vl.getCantidadProductos()) ;
            paramsJSON.put("subtotal", vl.getSubtotoal());
            paramsJSON.put("total", vl.getTotal());

            ListaDeProductos.getItems().set(ListaDeProductos.getSelectionModel().getSelectedIndex(), vl);


            JsonArray rootArray = Funciones.consultarBD(paramsJSON);



            listasVentasMostrador.get(nVentaSelect).remove(TablasVentas.get(nVentaSelect).getSelectionModel().getSelectedIndex());
            TablasVentas.get(nVentaSelect).getSelectionModel().clearSelection();
            calcularTotal();
        }

    }

    @FXML
    void limpiar(ActionEvent event) throws IOException {
        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();
        int idVentaProductos = ListaDeProductos.getSelectionModel().getSelectedItem().getIdVentaProductos();
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Venta Productos: Eliminar");
        paramsJSON.put("idVentaProductos", idVentaProductos);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);

        ListaDeProductos.getItems().remove(ListaDeProductos.getSelectionModel().getSelectedIndex());
        eliminarTab(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void cargarDatos() throws IOException {

        ObservableList<VentaLista> listadeVentas = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Venta Productos: Listar");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                listadeVentas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), VentaLista.class) );
            }
        }

        ListaDeProductos.setItems(listadeVentas);

    }

    private void calcularTotal() {
        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();


        double subtotal=0;
        double total;
        int cantidadProductos=0;

        if(nVentaSelect!=-1)
            for(VentaMostrador producto: listasVentasMostrador.get(nVentaSelect)) {
                subtotal+=producto.getTotal();
                cantidadProductos+= producto.getCant();
            }
        total = subtotal;


        CantidadProductos.setText(cantidadProductos+"");
        Subtotal.setText(Funciones.fixN(subtotal, 2)+"");


        Total.setText(Funciones.fixN(total,2)+"");



        Configuraciones.ventaMostradorSubTotal = subtotal;
        Configuraciones.ventaMostradorTotal = total;

        Subtotal.setText(Funciones.valorAmoneda(Funciones.fixN(Configuraciones.ventaMostradorSubTotal,2)));
        Total.setText(Funciones.valorAmoneda(Funciones.fixN(Configuraciones.ventaMostradorTotal,2)));
    }

    private void agregarProducto(VentaLista p) throws IOException {




        Tab tab = new Tab();
        tab.setText(p.getIdVentaProductos()+"");
        TablasVentas.add(cargarTabla(new JFXTreeTableView<>() ));
        tab.setContent(TablasVentas.get(TablasVentas.size()-1));
        Tabs.getTabs().add(tab);
        Tabs.getSelectionModel().select(Tabs.getTabs().size()-1);



        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();


        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Venta Productos: Listar detalles");
        paramsJSON.put("idVentaProductos", p.getIdVentaProductos());

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                VentaListaDetalles vld = new Gson().fromJson(rootArray.get(i).getAsJsonObject(), VentaListaDetalles.class);
                listasVentasMostrador.get(nVentaSelect).add(new VentaMostrador(
                        vld.getIdProducto(),
                        vld.getCantidad(),
                        vld.getNombre(),
                        vld.getCosto(),
                        vld.getIdVentaProductosDetalle()
                ));

            }
        }





        calcularTotal();
        TablasVentas.get(nVentaSelect).getSelectionModel().select(listasVentasMostrador.get(nVentaSelect).size()-1);
    }

}
