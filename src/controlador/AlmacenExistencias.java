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
import javafx.stage.FileChooser;
import modelo.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
    void imprimir(ActionEvent event) throws IOException {
        prepararPDF(true, false);

    }

    @FXML
    void guardar(ActionEvent event) throws IOException {
        prepararPDF(false, true);


    }


    @FXML
    void actualizar(ActionEvent event) throws IOException {
        Map<String, Object> paramsAlert = new LinkedHashMap<>();
        paramsAlert.put("titulo", "¿Modificar valores de almacen?");
        paramsAlert.put("vista", "/vista/acepta_administrador.fxml");
        Configuraciones.supervisorOK = false;
        Funciones.display(paramsAlert, getClass().getResource("/vista/acepta_administrador.fxml"), new AceptaAdministrador(), 762, 446);
        if (!Configuraciones.supervisorOK)
            return;




        for(int i=0; i<listaProductos.size(); i++) {
            if(listaProductos.get(i).getDato("Fisico") !=  null) {
                int dif = Integer.valueOf(listaProductos.get(i).getDato("Diferencias") );
                String clave = listaProductos.get(i).getDato("idProducto");
                Map<String,Object> paramsJSON = new LinkedHashMap<>();
                paramsJSON.put("Actividad", "Almacen: Actualizar cantidades");
                paramsJSON.put("cantidad", -dif);
                paramsJSON.put("idProducto", clave);
                JsonArray rootArray = Funciones.consultarBD(paramsJSON);

            }

        }

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




        JFXTreeTableColumn<VistaReporte, String> columnDiferencias = new JFXTreeTableColumn<>("Diferencias");
        columnDiferencias.setPrefWidth(100);
        columnDiferencias.setCellValueFactory((TreeTableColumn.CellDataFeatures<VistaReporte, String> param) ->  {
            if (columnDiferencias.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getDato("Diferencias"));

            }
            else {
                return columnDiferencias.getComputedValue(param);

            }
        });
        columnDiferencias.setStyle("-fx-alignment: CENTER;");


        JFXTreeTableColumn<VistaReporte, String> columnFisico = new JFXTreeTableColumn<>("Físico");
        columnFisico.setPrefWidth(100);
        columnFisico.setCellValueFactory((TreeTableColumn.CellDataFeatures<VistaReporte, String> param) ->  {
            if (columnFisico.validateValue(param)) {
                return new ReadOnlyObjectWrapper<String>(param.getValue().getValue().getDato("Fisico"));

            }
            else {
                return columnFisico.getComputedValue(param);

            }
        });
        columnFisico.setStyle("-fx-alignment: CENTER;");

        columnFisico.setCellFactory((TreeTableColumn<VistaReporte, String> param) ->
                new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder())
        );

        columnFisico.setOnEditCommit((TreeTableColumn.CellEditEvent<VistaReporte, String> t) -> {
                    int index = t.getTreeTablePosition().getRow();
                    //modelo.AlmacenExistencias productosConCosto = listaProductos.get(index);
                    String existencia = listaProductos.get(index).getDato("Existencia");
                    int intExistencia = Integer.valueOf(existencia);
                    int intFisico = Integer.valueOf(t.getNewValue());


                    listaProductos.get(index).setDato("Diferencias", (intExistencia-intFisico)+"" );
                    listaProductos.get(index).setDato("Fisico", (intFisico)+"" );

                    tvProductos.refresh();


                }
        );



        listaProductos =  FXCollections.observableArrayList();
        cargarProductos();

        TreeItem<VistaReporte> root = new RecursiveTreeItem<>(listaProductos, RecursiveTreeObject::getChildren);



        tvProductos.getColumns().addAll( columnClave, columnNombre, columnExistencia, columnFisico, columnDiferencias);
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
                Map<String, Object> v = new LinkedHashMap<>();

                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());
               // System.out.println(v);
                listaProductos.add(new VistaReporte(v));
            }
        }

    }






    private String[] titulos = new String[]{"Clave:20:CENTER", "Producto:70:CENTER-LEFT", "Existencia:20:CENTER", "Fisico:10:CENTER", "Diferencias:10:CENTER"};

    private void prepararPDF(boolean imprimir, boolean guardar) {
        String tituloAnterior = "";
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();


        String predeterminados = "celdaTitulo="+Configuraciones.reporteEncabezado+"Reporte: Existencia" +
                "@celdaDescripcion=Reporte de verificación de almacen";
        valoresPDF.add(new PDFvalores("-1", predeterminados) );


        int row = 0;
        for(VistaReporte fila: listaProductos) {

            // Agrega titulo de categorias
            if( !tituloAnterior.equalsIgnoreCase(fila.getDato("Clase")) ) {
                tituloAnterior = fila.getDato("Clase");
                valoresPDF.add(new PDFvalores(row+"","Clave=*****@Producto=" + tituloAnterior ));
                row++;
            }


            String valor="";
            for(int col=0; col<titulos.length; col++) {

                String t = titulos[col].split(":")[0].replace(" ", "");
                String v = fila.getDato(t)==null?"":fila.getDato(t);
                valor += t+"="+v+"@";
                // System.out.print(row+", "+col+"="+valor+"\t");

            }
            valoresPDF.add(new PDFvalores(row+"" ,valor));
            row++;
        }


        File file = null;
        String destino=null;

        if(guardar) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Portable Document Format(*.pdf)", "*.pdf"));
            file = fileChooser.showSaveDialog(Pane.getScene().getWindow());
            if(file == null)
                return;

            destino = file.getAbsolutePath();
        }
        try {
            Funciones.llenarPDF2("formatos/reporte_existencias_verificar_2.pdf",  valoresPDF, imprimir, guardar?destino:null, 27);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
