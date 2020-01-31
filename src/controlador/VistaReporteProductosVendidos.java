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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
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
import java.time.LocalTime;
import java.util.*;

public class VistaReporteProductosVendidos extends Controlador implements Initializable {


    Map<String,Object> paramsJSONReporte = new LinkedHashMap<>();
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

    String descripcion = "";
    @FXML
    private Label Titulo;

    @FXML
    private Label Descripcion;

    @FXML
    private JFXDatePicker FechaInicio;

    @FXML
    private JFXDatePicker FechaFin;

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTreeTableView<VistaReporte> TablaReporte;




    @FXML
    void imprimir(ActionEvent event) {

        try {


            new Reporte().mostrarReporte("reportes/reporteProductosVendidos.jrxml", list);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void aceptar(ActionEvent event) throws IOException {
        quitarVistas();
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());

    }
    private ObservableList<VistaReporte> listaReporte;
    private String[] titulos;

    @Override
    public void init() {

        listaReporte = FXCollections.observableArrayList();
        TreeItem<VistaReporte> root = new RecursiveTreeItem<>(listaReporte, RecursiveTreeObject::getChildren);

        titulos= (String[]) parametros.get(0).get("titulos");

        Titulo.setText((String) parametros.get(0).get("Descripcion"));
        Descripcion.setText("");

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


        FechaInicio.setValue(LocalDate.now() );
        FechaFin.setValue(LocalDate.now() );
        try {
            cargarDatos();
        } catch (IOException  e) {
            e.printStackTrace();
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void generarReporte(ActionEvent event) throws IOException {

        Descripcion.setText(

                "Periodo "+FechaInicio.getValue()+" a " + FechaFin.getValue());

        descripcion = "Periodo de\n" +
                FechaInicio.getValue()+" a\n" +
                FechaFin.getValue()+"\n" +
                "Generado: "+LocalDate.now();

        cargarDatos();
    }

    private void cargarDatos() throws IOException {
        String actividad = (String) parametros.get(0).get("reporte");

        listaReporte.clear();
        list.clear();

        paramsJSONReporte.clear();
        paramsJSONReporte.put("fechaInicial", FechaInicio.getValue());
        paramsJSONReporte.put("fechaFinal", FechaFin.getValue());
        paramsJSONReporte.put("Actividad", actividad);
        paramsJSONReporte.put("idClinica", parametros.get(0).get("idClinica").toString());

        JsonArray rootArray = Funciones.consultarBD(paramsJSONReporte);

        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            double total = 0;
            double granTotal = 0;
            String clase = "";
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {

                Map<String, Object> v = new LinkedHashMap<>();
                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());
                VistaReporte vr = new VistaReporte(v);
                String nuevaClase = vr.getDato("Clase");
                if(!clase.equalsIgnoreCase(nuevaClase) && !clase.equalsIgnoreCase("")) {
                    Map<String, Object> totalCalse = new LinkedHashMap<>();
                    totalCalse.put("Total", Funciones.valorAmoneda(total));
                    granTotal += total;
                    total = 0;
                    listaReporte.add(new VistaReporte(totalCalse));


                    HashMap<String, Object> filaTotalClase = new HashMap<String, Object>();
                    filaTotalClase.put("Clave", "");
                    filaTotalClase.put("Producto", "");
                    filaTotalClase.put("Vendidos", "");
                    filaTotalClase.put("Precio", "");
                    filaTotalClase.put("Total", Funciones.valorAmoneda(total));
                    list.add(filaTotalClase);

                }

                total += Double.valueOf(vr.getDato("Total")!=null?vr.getDato("Total"):"0");
                if(!clase.equalsIgnoreCase(nuevaClase)) {
                    clase = nuevaClase;
                    Map<String, Object> datos =  new LinkedHashMap<>();
                    datos.put("Clave", "******");
                    datos.put("Producto", clase);
                    listaReporte.add(new VistaReporte(datos));

                    HashMap<String, Object> filaTotalClase = new HashMap<String, Object>();
                    filaTotalClase.put("Clave", "******");
                    filaTotalClase.put("Producto", clase);
                    filaTotalClase.put("Vendidos", "");
                    filaTotalClase.put("Precio", "");
                    filaTotalClase.put("Total", "");
                    list.add(filaTotalClase);
                }

                if(vr.getDato("Vendidos")==null) vr.setDato("Vendidos", "");



                if(vr.getDato("Total")!=null)
                    vr.setDato("Total", Funciones.valorAmoneda(Double.valueOf(vr.getDato("Total")) ));
                else
                    vr.setDato("Total", "");
                if(vr.getDato("Precio")!=null)
                    vr.setDato("Precio", Funciones.valorAmoneda(Double.valueOf(vr.getDato("Precio")) ));
                listaReporte.add(vr);

                HashMap<String, Object> filaTotalClase = new HashMap<String, Object>();
                filaTotalClase.put("Clave", vr.getDato("Clave"));
                filaTotalClase.put("Producto", vr.getDato("Producto"));
                filaTotalClase.put("Vendidos", vr.getDato("Vendidos"));
                filaTotalClase.put("Precio", vr.getDato("Precio"));
                filaTotalClase.put("Total", vr.getDato("Total"));
                list.add(filaTotalClase);


            }


            VistaReporte vrTotal = new VistaReporte(new LinkedHashMap<>());
            vrTotal.setDato("Clave", "");
            vrTotal.setDato("Clase", "");
            vrTotal.setDato("Producto", "");
            vrTotal.setDato("Existencia", "");
            vrTotal.setDato("Precio", "Total");
            vrTotal.setDato("Total", Funciones.valorAmoneda(granTotal));
            listaReporte.add(vrTotal);


            HashMap<String, Object> filaTotalClase = new HashMap<String, Object>();
            filaTotalClase.put("Clave", "");
            filaTotalClase.put("Producto", "");
            filaTotalClase.put("Vendidos", "");
            filaTotalClase.put("Precio", "");
            filaTotalClase.put("Total", Funciones.valorAmoneda(granTotal));
            list.add(filaTotalClase);

        }
    }

}
