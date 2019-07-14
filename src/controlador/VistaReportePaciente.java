package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXComboBox;
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
import modelo.*;
import modelo.Pacientes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VistaReportePaciente extends Controlador implements Initializable {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTreeTableView<VistaReporte> TablaReporte;



    @FXML
    private Label titulo;

    @FXML
    private JFXComboBox <Pacientes> Paciente;

    @FXML
    private Label descripcion;

    @FXML
    void imprimir(ActionEvent event) {

        //prepararPDF(true, false);
    }

    @FXML
    void descargar(ActionEvent event) {

        //prepararPDF(false, true);
    }

    @FXML
    void aceptar(ActionEvent event) throws IOException {
        quitarVistas();
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());

    }

    @FXML
    void cambiarPaciente(ActionEvent event) throws IOException {
        cargarDatos();
    }


    private ObservableList<VistaReporte> listaReporte;
    private String[] titulos;
    private void prepararPDF(boolean imprimir, boolean guardar) {
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();

        int index=0;
        for(VistaReporte elemento : listaReporte) {


            for(String t: titulos) {
                String titulo = (t.split(":")[0]).replace(" ","");
                String valor = (valorPDf.get(titulo)==null?"":valorPDf.get(titulo)+"\n")+elemento.getDato(titulo);
                if((index+1)%Configuraciones.lineasPorReporte==0)
                    valor += "@";

                valorPDf.put(titulo, valor);
            }
            index++;




        }


        for(String t: titulos) {
            String titulo = (t.split(":")[0]).replace(" ","");
            valoresPDF.add(new PDFvalores(titulo, valorPDf.get(titulo)));
        }

        //System.out.println("titulo="+(String) parametros.get(0).get("clinicaDescripcion"));
       valoresPDF.add(new PDFvalores("Descripcion", Configuraciones.clinicaDescripcion));


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
        TreeItem<VistaReporte> root = new RecursiveTreeItem<>(listaReporte, RecursiveTreeObject::getChildren);

        titulos= (String[]) parametros.get(0).get("titulos");

        titulo.setText((String) parametros.get(0).get("clinicaDescripcion"));

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
        try {
            cargarDatos();
            Paciente.setItems(Datos.pacientes);
        } catch (IOException  e) {
            e.printStackTrace();
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void cargarDatos() throws IOException {
        String actividad = (String) parametros.get(0).get("reporte");

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", actividad);
        int idPaciente = Paciente.getSelectionModel().getSelectedIndex() == -1?-1:Paciente.getSelectionModel().getSelectedItem().getIdPaciente();
        paramsJSON.put("idPaciente", idPaciente);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        listaReporte.clear();
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                Map<String, Object> v = new LinkedHashMap<>();
                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());

                listaReporte.add(new VistaReporte(v));
            }
        }
    }

}
