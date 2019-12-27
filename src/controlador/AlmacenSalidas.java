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
import java.text.SimpleDateFormat;
import java.util.*;

public class AlmacenSalidas extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;


    @FXML
    JFXTreeTableView<VistaReporte> tvProductos;

    private ObservableList<VistaReporte> listaProductos = FXCollections.observableArrayList();
    @FXML
    void imprimir(ActionEvent event) throws IOException {
        // Verificar que la impresora este seleccionada
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

        String timeStamp = new SimpleDateFormat("dd/MM/YY HH:mm").format(Calendar.getInstance().getTime());


        String ticketSTR=Configuraciones.ticketTituloClinicaThermal+
                "Salida de almacen "+"\n"+
                "Fecha: "+ timeStamp +"\n\n"+


        Funciones.nuevaLinea("Cant", "Producto", " ", "");
        for(int i=0; i<listaProductos.size(); i++) {
            if (listaProductos.get(i).getDato("Entrada") != null) {
                String cant = listaProductos.get(i).getDato("Entrada");
                String prod = listaProductos.get(i).getDato("Producto");
                ticketSTR = ticketSTR + "\n"+ Funciones.nuevaLinea(cant, prod, "", "");
            }
        }

        try {
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
    void actualizar(ActionEvent event) throws IOException {
        Map<String, Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "¿Insertar productos a almacen?");
        paramsAlert.put("vista", "/vista/acepta_administrador.fxml");
        Configuraciones.supervisorOK = false;
        Funciones.display(paramsAlert, getClass().getResource("/vista/acepta_administrador.fxml"), new AceptaAdministrador(), 762, 446);
        if (!Configuraciones.supervisorOK)
            return;

        for(int i=0; i<listaProductos.size(); i++) {
            if(listaProductos.get(i).getDato("Entrada") !=  null) {
                int entrada = Integer.valueOf(listaProductos.get(i).getDato("Entrada") );
                String idProducto = listaProductos.get(i).getDato("idProducto");
                Map<String,Object> paramsJSON = new LinkedHashMap<>();
                paramsJSON.put("Actividad", "Almacen: Agregar entradas o salidas");
                paramsJSON.put("idProducto", idProducto);
                paramsJSON.put("cantidad", -entrada);
                JsonArray rootArray = Funciones.consultarBD(paramsJSON);

            }

        }
        Datos.cargarProductosConCosto();
        imprimir(null);
        cargarProductos();
        tvProductos.refresh();
        tvProductos.requestFocus();
    }


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


        JFXTreeTableColumn<VistaReporte, String> columnEntrada = new JFXTreeTableColumn<>("Entrada");
        columnEntrada.setPrefWidth(100);
        columnEntrada.setCellValueFactory((TreeTableColumn.CellDataFeatures<VistaReporte, String> param) ->  {
            if (columnEntrada.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getDato("Entrada"));

            }
            else {
                return columnEntrada.getComputedValue(param);

            }
        });
        columnEntrada.setStyle("-fx-alignment: CENTER;");





        columnEntrada.setCellFactory((TreeTableColumn<VistaReporte, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );

        columnEntrada.setOnEditCommit((TreeTableColumn.CellEditEvent<VistaReporte, String> t) -> {
                    int index = t.getTreeTablePosition().getRow();
                    if(!listaProductos.get(index).getDato("Clave").contains("*")) {
                        listaProductos.get(index).setDato("Entrada", t.getNewValue());

                    }
                tvProductos.refresh();
                tvProductos.requestFocus();
                }
        );



        listaProductos =  FXCollections.observableArrayList();
        cargarProductos();

        TreeItem<VistaReporte> root = new RecursiveTreeItem<>(listaProductos, RecursiveTreeObject::getChildren);

        tvProductos.getColumns().addAll( columnClave, columnNombre, columnEntrada);
        tvProductos.setRoot(root);
        tvProductos.setEditable(true);
        tvProductos.setShowRoot(false);

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
            String clase = "";
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                Map<String, Object> v = new LinkedHashMap<>();
                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());
                VistaReporte vr = new VistaReporte(v);
                String nuevaClase = vr.getDato("Clase");
                if(!clase.equalsIgnoreCase(nuevaClase)) {
                    clase = nuevaClase;
                    Map<String, Object> datos =  new LinkedHashMap<>();
                    datos.put("Clave", "******");
                    datos.put("Producto", clase);
                    listaProductos.add(new VistaReporte(datos));
                }

                listaProductos.add(vr);
            }
        }

    }
}
