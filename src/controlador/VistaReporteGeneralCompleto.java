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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class VistaReporteGeneralCompleto extends Controlador implements Initializable {


    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTreeTableView<modelo.VistaReporte> TablaReporte;

    @FXML
    private Label Titulo;

    @FXML
    private Label Descripcion;

    @FXML
    private JFXDatePicker FechaInicio;

    @FXML
    private JFXDatePicker FechaFin;

    Map<String,Object> paramsJSONReporte = new LinkedHashMap<>();

    @FXML
    void generarReporte(ActionEvent event) throws IOException {
        paramsJSONReporte.clear();
        paramsJSONReporte.put("fechaInicial", FechaInicio.getValue());
        paramsJSONReporte.put("fechaFinal", FechaFin.getValue());
        Descripcion.setText(

                "Periodo "+FechaInicio.getValue()+" a " + FechaFin.getValue());

        cargarDatos();
    }


    @FXML
    void imprimir(ActionEvent event) {
        prepararPDF(true, false);
    }

    @FXML
    void descargar(ActionEvent event) {
        prepararPDF(false, true);
    }

    @FXML
    void aceptar(ActionEvent event) throws IOException {

        quitarVistas();
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());

    }
    private ObservableList<modelo.VistaReporte> listaReporte;
    private String[] titulos;
    private void prepararPDF(boolean imprimir, boolean guardar) {
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();

        for(modelo.VistaReporte elemento : listaReporte) {


            for(String t: titulos) {
                String titulo = (t.split(":")[0]).replace(" ","");
                if((titulo.contains("IdVenta")||titulo.contains("Paciente"))) {
                    String valor = (valorPDf.get(titulo) == null ? "" : valorPDf.get(titulo) + "\n") + (elemento.getDato(titulo) == null ? "" : elemento.getDato(titulo));
                    valorPDf.put(titulo, valor);
                }else {


                    String valor = (valorPDf.get(titulo) == null ? "" : valorPDf.get(titulo) + "\n") + (elemento.getDato(titulo) == null ? "" :
                            NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                                    .format(Double.valueOf(
                            elemento.getDato(titulo)
                                            )
                                    )
                    );
                    valorPDf.put(titulo, valor);
                }

                if(!(titulo.contains("IdVenta")||titulo.contains("Paciente"))) {
                    double val =0;
                    try {
                        val = Double.valueOf(elemento.getDato(titulo) == null ? "0" : elemento.getDato(titulo));
                    } catch (Exception e){

                    }
                    //System.out.println(titulo+"->"+valorPDf.get("total"+titulo));
                    String valor2 = ""+(valorPDf.get("total" + titulo) == null ? val : Double.valueOf(valorPDf.get("total"+titulo))+val );

                    valorPDf.put("total"+titulo, valor2);
                }

            }




        }


        for(String t: titulos) {
            String titulo = (t.split(":")[0]).replace(" ","");
            valoresPDF.add(new PDFvalores(titulo, valorPDf.get(titulo)));

            if(!(titulo.contains("IdVenta")||titulo.contains("Paciente"))) {
                double val=Double.valueOf(valorPDf.get("total"+titulo));
                if(val>0) {


                    valoresPDF.add(new PDFvalores("total" + titulo,
                            NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                            .format(val )));
                }
            }

        }






        valoresPDF.add(new PDFvalores("Titulo", (String) parametros.get(0).get("Titulo")));
        valoresPDF.add(new PDFvalores("Informacion", Descripcion.getText()));


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


            Funciones.llenarPDF((String) parametros.get(0).get("pdf"),  valoresPDF, imprimir, guardar?destino:null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init() {

        listaReporte = FXCollections.observableArrayList();
        TreeItem<modelo.VistaReporte> root = new RecursiveTreeItem<>(listaReporte, RecursiveTreeObject::getChildren);

        titulos= (String[]) parametros.get(0).get("titulos");

        Titulo.setText((String) parametros.get(0).get("clinicaDescripcion"));

        double anchoTabla = TablaReporte.getPrefWidth();

        for(String t : titulos) {
            String titulo = t.split(":")[0];
            double ancho = Double.valueOf(t.split(":")[1]);
            String alineacion = t.split(":")[2];
            JFXTreeTableColumn<modelo.VistaReporte, String> column = new JFXTreeTableColumn<>(titulo);

            column.setPrefWidth(( (ancho*anchoTabla)/100) -1);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getDato(titulo.replace(" ", ""))));
            column.setStyle("-fx-alignment: "+alineacion+";");
            TablaReporte.getColumns().add(column);
        }


        TablaReporte.setRoot(root);

        TablaReporte.setEditable(true);
        TablaReporte.setShowRoot(false);
        TablaReporte.setColumnResizePolicy(TreeTableView.UNCONSTRAINED_RESIZE_POLICY);

        Titulo.setText("Reporte general");
        FechaInicio.setValue(LocalDate.now().minusMonths(1) );
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
        String actividad = (String) parametros.get(0).get("reporte");
        listaReporte.clear();
        paramsJSONReporte.put("Actividad", actividad);
        paramsJSONReporte.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSONReporte);

        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                Map<String, Object> v = new LinkedHashMap<>();
                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());
                listaReporte.add(new modelo.VistaReporte(v));
            }
        }
    }

}
