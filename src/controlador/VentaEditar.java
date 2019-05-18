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
import javafx.event.EventHandler;
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

import javax.print.DocFlavor;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class VentaEditar extends Controlador implements Initializable {
    private int nVenta=1;
    private int nVentaSelect = 1;
    private boolean pagoValido=false;


    @FXML
    private AnchorPane Pane;

    private ArrayList<JFXTreeTableView<VentaMostrador>> TablasVentas = new ArrayList<>();


    private ArrayList<ObservableList<VentaMostrador>> listasVentasMostrador = new ArrayList<>();

    @FXML
    private JFXListView<Cobro> ListaCobros;


    private ArrayList<ObservableList<Cobro>> listaCobros = new ArrayList<>();

    private ArrayList<List<VentaMostradorQuitar>> productosActualizar = new ArrayList<>();

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

        ListaCobros.setItems(listaCobros.get(nVentaSelect));

    }


    @FXML
    void agregarTab(ActionEvent event) {
        Tab tab = new Tab();
        tab.setText("Venta "+nVenta++);



        TablasVentas.add(cargarTabla(new JFXTreeTableView<>() ));

        tab.setContent(TablasVentas.get(TablasVentas.size()-1));



        List<VentaMostradorQuitar> l = new ArrayList<>();
        productosActualizar.add(l);

        Tabs.getTabs().add(tab);
        Tabs.getSelectionModel().select(Tabs.getTabs().size()-1);
        calcularTotal();


    }

    private void quitarProductos(int nv, int idVentaProductosDetalle, int cantidad) {
        for(VentaMostradorQuitar vmq : productosActualizar.get(nv)) {
            if(vmq.getIdVentaProductosDetalle() == idVentaProductosDetalle) {
                vmq.setCantidad(cantidad);
                return;
            }
        }
        VentaMostradorQuitar vmq = new VentaMostradorQuitar(idVentaProductosDetalle, cantidad);
        productosActualizar.get(nv).add(vmq);

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
        columnCantidad.setStyle("-fx-alignment: CENTER;");



        columnCantidad.setOnEditCommit((TreeTableColumn.CellEditEvent<VentaMostrador, String> t) -> {

                    int index = t.getTreeTablePosition().getRow();

                    double costo = listasVentasMostrador.get(nVentaSelect).get(index).getCosto();

                    int cantidadNueva = Integer.valueOf(t.getNewValue());
                    int cantidadActual = listasVentasMostrador.get(nVentaSelect).get(index).getCant();
                    int idProducto = listasVentasMostrador.get(nVentaSelect).get(index).getIdProducto();
                    int idVentaProductosDetalle = listasVentasMostrador.get(nVentaSelect).get(index).getIdVentaProductosDetalle();
                    String producto = listasVentasMostrador.get(nVentaSelect).get(index).getProducto();


                    if(cantidadNueva<cantidadActual) {


                        modelo.VentaMostrador vM = new modelo.VentaMostrador(idProducto, cantidadNueva,producto,costo, idVentaProductosDetalle);
                        listasVentasMostrador.get(nVentaSelect).remove(index);
                        listasVentasMostrador.get(nVentaSelect).add( vM);
                        t.getTreeTableView().getSelectionModel().select(listasVentasMostrador.get(nVentaSelect).size()-1);



                        if(cantidadNueva<=0) {
                            listasVentasMostrador.get(nVentaSelect).remove(listasVentasMostrador.get(nVentaSelect).size()-1);
                            cantidadNueva=0;
                        }


                        quitarProductos(nVentaSelect, idVentaProductosDetalle, cantidadNueva);

                    }
                    else if(cantidadNueva>cantidadActual) {

                        Map<String,Object> paramsAlert = new LinkedHashMap<>();
                        paramsAlert.put("titulo", "Error");
                        paramsAlert.put("texto", Configuraciones.ventaEditarNoMasProductos);
                        paramsAlert.put("tiempo", "3");
                        paramsAlert.put("vista", "/vista/alert_box.fxml");
                        try {
                            Funciones.displayFP(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        modelo.VentaMostrador vM = new modelo.VentaMostrador(idProducto, cantidadActual,producto,costo, idVentaProductosDetalle);
                        listasVentasMostrador.get(nVentaSelect).remove(index);
                        listasVentasMostrador.get(nVentaSelect).add( vM);
                        t.getTreeTableView().getSelectionModel().select(listasVentasMostrador.get(nVentaSelect).size()-1);
                    }


                    calcularTotal();


                }
        );




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
        listaCobros.remove(index);

        ListaCobros.getItems().clear();

        productosActualizar.remove(index);


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
    void regresar(ActionEvent event) throws IOException, PrinterException {


        //quitarVistas();
        //Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());

        //parametros.remove(0);
        //Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());
        cargarVista(Pane, new InicioAdministrador(), Configuraciones.panelInicial);


    }

/**
    @FXML
    void agregar_quitar(KeyEvent event) {
        if(event.getText().contains("+")) {
            if(!ListaDeProductos.getSelectionModel().isEmpty()) {
             //   agregarProducto(ListaDeProductos.getSelectionModel().getSelectedItem());
            }

        }
    }


 */


    @FXML
    void imprimir(ActionEvent event) throws IOException {


        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();

        if(productosActualizar.get(nVentaSelect).size()>0)
        {
            Map<String,Object> paramsAlerta = new LinkedHashMap<>();
            paramsAlerta.put("titulo", "Error");
            paramsAlerta.put("tiempo", "5");
            paramsAlerta.put("vista", "/vista/alert_box.fxml");

            paramsAlerta.put("texto", "Se debe guardar primero");

            Funciones.displayFP(paramsAlerta, getClass().getResource("/vista/alert_box.fxml"), new AlertBox());

            return;
        }

        PrinterService printerService = new PrinterService();
        List<String> listPrinters = printerService.getPrinters();
        boolean impresoraValida = false;
        for(String printer : listPrinters) {
            if( printer.contains(Configuraciones.impresoraTicket)) {
                impresoraValida = true;

            }
        }

        Map<String,Object> paramsAlertImpresora = new LinkedHashMap<>();
        paramsAlertImpresora.put("titulo", "Error");
        paramsAlertImpresora.put("tiempo", "5");
        paramsAlertImpresora.put("vista", "/vista/alert_box.fxml");
        if(!impresoraValida) {
            paramsAlertImpresora.put("texto", "No existe la impresora: "+Configuraciones.impresoraTicket+ " en el sistema");

            Funciones.displayFP(paramsAlertImpresora, getClass().getResource("/vista/alert_box.fxml"), new AlertBox());

            return;
        }


        int ultimoInsertado = ListaDeProductos.getSelectionModel().getSelectedItem().getIdVentaProductos();
        String timeStamp = new SimpleDateFormat("dd/MM/YY HH:mm").format(Calendar.getInstance().getTime());



        String ticketSTR=Configuraciones.ticketTituloClinicaThermal+
                "Fecha: "+ timeStamp +"\n"+
                //"Cliente:\n"+
                "Venta: "+ ultimoInsertado+ "\n\n"+
                Funciones.nuevaLinea("Cant", "Producto", "C. U.", "Total");


        for(modelo.VentaMostrador ventaMostrador: listasVentasMostrador.get(nVentaSelect)) {
            ticketSTR = ticketSTR + "\n"+ Funciones.nuevaLinea(" "+ventaMostrador.getCantidad(), ventaMostrador.getProducto(), ventaMostrador.getCosto()+"", ventaMostrador.getTotal()+"");
        }

        ticketSTR = ticketSTR + "\n\n"+ Funciones.nuevaLinea(" "+CantidadProductos.getText(), "productos", "Total   $", Configuraciones.ventaMostradorTotal+"");

        Configuraciones.formaPagoCobros = ListaCobros.getItems();
        ticketSTR = ticketSTR + "\n\nMovimientos:\n"+Funciones.formaPago();

        ticketSTR = ticketSTR + "\n\n¡Gracias por su compra!\n\n\n\n\n\n\n\n\n";




        try {
            //printerService.printImage(Configuraciones.impresoraTicket, "formatos/mgm_t.png", DocFlavor.INPUT_STREAM.PNG);
            printerService.printString(Configuraciones.impresoraTicket, ticketSTR);

            byte[] cutP = new byte[]{0x1d, 'V', 1};
            printerService.printBytes(Configuraciones.impresoraTicket, cutP);


            byte[] openCashDrawer = new byte[]{27, 112, 48, 55, 121};
            printerService.printBytes(Configuraciones.impresoraTicket, openCashDrawer);
        } catch (Exception e) {
            paramsAlertImpresora.put("texto", "Error en la impresora :(, "+Configuraciones.impresoraTicket);
            Funciones.displayFP(paramsAlertImpresora, getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
            return;
        }
    }

    @FXML
    void cerrar(ActionEvent event) throws IOException {
        eliminarTab(null);

    }
    @FXML
    void eliminarFila(ActionEvent event) throws IOException {
        int index = Tabs.getSelectionModel().getSelectedIndex();
        int fila = TablasVentas.get(index).getSelectionModel().getSelectedIndex();
        int idVentaProductosDetalle= listasVentasMostrador.get(index).get(fila).getIdVentaProductosDetalle();

        quitarProductos(index, idVentaProductosDetalle, 0);
        listasVentasMostrador.get(index).remove(fila);
        calcularTotal();


    }
    @FXML
    void eliminarVenta(ActionEvent event) throws IOException {
        Map<String, Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "¿Eliminar venta?");
        paramsAlert.put("vista", "/vista/acepta_administrador.fxml");
        Configuraciones.supervisorOK = false;
        Funciones.display(paramsAlert, getClass().getResource("/vista/acepta_administrador.fxml"), new AceptaAdministrador(), 762, 324);
        if (!Configuraciones.supervisorOK)
            return;


        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();
        int idVentaProductos = ListaDeProductos.getSelectionModel().getSelectedItem().getIdVentaProductos();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Venta Productos: Eliminar");
        paramsJSON.put("idVentaProductos", idVentaProductos);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);

        ListaDeProductos.getItems().remove(ListaDeProductos.getSelectionModel().getSelectedIndex());
        eliminarTab(null);




    }

    @FXML
    void actualizar(ActionEvent event) throws IOException {



        Map<String, Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "¿Actualizar venta?");
        paramsAlert.put("vista", "/vista/acepta_administrador.fxml");
        Configuraciones.supervisorOK = false;
        Funciones.display(paramsAlert, getClass().getResource("/vista/acepta_administrador.fxml"), new AceptaAdministrador(), 762, 324);
        if (!Configuraciones.supervisorOK)
            return;







        nVentaSelect = Tabs.getSelectionModel().getSelectedIndex();
        for(VentaMostradorQuitar vmq : productosActualizar.get(nVentaSelect)) {

            Map<String,Object> paramsJSON_actualizar = new LinkedHashMap<>();
            paramsJSON_actualizar.put("Actividad", "Venta Productos: Editar Venta Detalles");
            paramsJSON_actualizar.put("idVentaProductosDetalle", vmq.getIdVentaProductosDetalle() );
            paramsJSON_actualizar.put("cantidad", vmq.getCantidad() );
            JsonArray rootArray = Funciones.consultarBD(paramsJSON_actualizar);

        }


        VentaLista vl = ListaDeProductos.getSelectionModel().getSelectedItem();
        vl.setTotal(Configuraciones.ventaMostradorTotal);
        vl.setCantidadProductos(Integer.valueOf(CantidadProductos.getText()));

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Venta Productos: Editar Venta");
        paramsJSON.put("idVentaProductos", vl.getIdVentaProductos());
        paramsJSON.put("cantidadProductos", vl.getCantidadProductos());
        paramsJSON.put("total", vl.getTotal());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);


        productosActualizar.get(nVentaSelect).clear();



        /*
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


         */


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


        List<VentaMostradorQuitar> l = new ArrayList<>();
        productosActualizar.add(l);


        /**
         * Cargar cobros
         */

        Map<String,Object> paramsJSON_Cobros = new LinkedHashMap<>();
        paramsJSON_Cobros.put("Actividad", "Cobros de venta: listar");
        paramsJSON_Cobros.put("idVentaProductos", p.getIdVentaProductos());
        JsonArray rootArrayCobros = Funciones.consultarBD(paramsJSON_Cobros);
        ObservableList<Cobro> lc = FXCollections.observableArrayList();
        listaCobros.add(lc);
        ListaCobros.setItems(lc);
        if(rootArrayCobros.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {

            int t = rootArrayCobros.size();
            for(int i = 1; i< t; i++) {
                Cobro cobro = new Gson().fromJson(rootArrayCobros.get(i).getAsJsonObject(), Cobro.class);
                lc.add(cobro);
            }
            ListaCobros.setItems(lc);

        }







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
