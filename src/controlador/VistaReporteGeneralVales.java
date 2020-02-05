package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import modelo.Configuraciones;
import modelo.Funciones;
import modelo.PDFvalores;
import modelo.VistaReporte;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class VistaReporteGeneralVales extends Controlador implements Initializable {


    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTreeTableView<VistaReporte> TablaReporte;

    @FXML
    private Label Titulo;

    @FXML
    private Label Descripcion;

    @FXML
    private JFXDatePicker FechaInicio;

    @FXML
    private JFXDatePicker FechaFin;

    Map<String,Object> paramsJSONReporte = new LinkedHashMap<>();

    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    VistaReporte vr=null;
    String descripcion = "";
    @FXML
    void generarReporte(ActionEvent event) throws IOException {
        paramsJSONReporte.clear();
        paramsJSONReporte.put("fechaInicial", FechaInicio.getValue());
        paramsJSONReporte.put("fechaFinal", FechaFin.getValue());
        Descripcion.setText(

                "Periodo "+FechaInicio.getValue()+" a " + FechaFin.getValue());
        descripcion = "Periodo de\n" +
                FechaInicio.getValue()+" a\n" +
                FechaFin.getValue()+"\n" +
                "Generado: "+LocalDate.now();
        cargarDatos();
    }


    @FXML
    void imprimir(ActionEvent event) {

        try {


            new Reporte().mostrarReporte2("reportes/reporteValesEntrada.jrxml", list);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void descargar(ActionEvent event) {
        vr = listaReporte.remove(listaReporte.size()-1);
        prepararPDF(false, true);
        listaReporte.add(vr);

    }

    @FXML
    void aceptar(ActionEvent event) throws IOException {

        quitarVistas();
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());

    }
    private ObservableList<VistaReporte> listaReporte;
    private String[] titulos;
    private void prepararPDF(boolean imprimir, boolean guardar) {
        String tituloAnterior = "";
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();

        String predeterminados = "celdaTitulo="+(String) parametros.get(0).get("Titulo") +
                "@celdaDescripcion="+descripcion+
                "@celdaTratamiento="+vr.getDato("Tratamiento")+
                "@celdaProducto="+vr.getDato("Producto")+
                "@celdaTotal="+vr.getDato("Total");

        valoresPDF.add(new PDFvalores("-1", predeterminados) );

        int row = 0;
        for(VistaReporte fila: listaReporte) {


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
            Funciones.llenarPDF2((String) parametros.get(0).get("pdf"),  valoresPDF, imprimir, guardar?destino:null);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public void init() {

        listaReporte = FXCollections.observableArrayList();
        TreeItem<VistaReporte> root = new RecursiveTreeItem<>(listaReporte, RecursiveTreeObject::getChildren);

        titulos= (String[]) parametros.get(0).get("titulos");

        Titulo.setText((String) parametros.get(0).get("Titulo"));

        double anchoTabla = TablaReporte.getPrefWidth();

        for(String t : titulos) {
            String titulo = t.split(":")[0];
            double ancho = Double.valueOf(t.split(":")[1]);
            String alineacion = t.split(":")[2];
            JFXTreeTableColumn<VistaReporte, String> column = new JFXTreeTableColumn<>(titulo);

            column.setPrefWidth(( (ancho*anchoTabla)/100) -1);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getDato(titulo.replace(" ", ""))));
            column.setStyle("-fx-alignment: "+alineacion+";");
            TablaReporte.getColumns().add(column);
        }


        TablaReporte.setRoot(root);

        TablaReporte.setEditable(true);
        TablaReporte.setShowRoot(false);
        TablaReporte.setColumnResizePolicy(TreeTableView.UNCONSTRAINED_RESIZE_POLICY);

        //FechaInicio.setValue(LocalDate.now().minusMonths(1) );
        FechaInicio.setValue(LocalDate.now() );
        FechaFin.setValue(LocalDate.now() );


        try {
            generarReporte(null);
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    private void cargarDatos() throws IOException {

        VistaReporte vrTotal=null;
        String actividad = (String) parametros.get(0).get("reporte");
        //list.clear();
        listaReporte.clear();
        paramsJSONReporte.put("Actividad", actividad);

        //paramsJSONReporte.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSONReporte);
        String idAlmacenAnterior  = "";

        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();



            int totalProductos = 0;
            VistaReporte vistaReporte = new VistaReporte("clave", "nombre", "cantidad");

            for(int i = 1; i< t; i++) {
                Map<String, Object> v = new LinkedHashMap<>();
                HashMap<String, Object> parameters = new HashMap<String, Object>();
                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());
                if( !v.get("idAlmacenVale").toString().equalsIgnoreCase(idAlmacenAnterior) ) {
                    if( totalProductos != 0 ) {
                        vistaReporte = vistaReporte.registroNuevo();
                        vistaReporte.setDato("nombre", "Cantidad total de productos");
                        vistaReporte.setDato("cantidad", totalProductos+"");
                        listaReporte.add(vistaReporte);
                        listaReporte.add(vistaReporte.registroNuevo() );
                    }
                    vistaReporte = vistaReporte.registroNuevo();
                    vistaReporte.setDato("clave", "****");
                    vistaReporte.setDato("nombre", v.get("idAlmacenVale").toString() + ": " + v.get("usuario").toString());
                    vistaReporte.setDato("cantidad", v.get("fecha").toString());
                    listaReporte.add(vistaReporte);
                    idAlmacenAnterior = v.get("idAlmacenVale").toString();
                    totalProductos = 0;
                }


                parameters.put("clave", v.get("clave"));
                parameters.put("nombre", v.get("nombre"));
                parameters.put("cantidad", v.get("cantidad"));
                System.out.println(v);
                System.out.println("Cantidad: "+v.get("cantidad"));
                totalProductos = totalProductos + Integer.valueOf(v.get("cantidad")+"");
                //list.add(parameters);

                vistaReporte = vistaReporte.registroNuevo();
                vistaReporte.setDatos(v);
                listaReporte.add(vistaReporte);
            }
            vistaReporte = vistaReporte.registroNuevo();
            vistaReporte.setDato("nombre", "Cantidad total de productos");
            vistaReporte.setDato("cantidad", totalProductos+"");
            listaReporte.add(vistaReporte);

            list = Funciones.table2JasperReports(listaReporte);
        }
    }

}
