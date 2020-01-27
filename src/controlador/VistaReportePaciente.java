package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.*;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import modelo.*;
import modelo.Pacientes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class VistaReportePaciente extends Controlador implements Initializable {

    private String patron = "";
    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTreeTableView<VistaReporte> TablaReporte;


    @FXML
    private JFXTextField PacienteNombre;


    @FXML
    private Label titulo;


    @FXML
    private JFXListView<Pacientes> Paciente;

    @FXML
    private Label descripcion;

    @FXML
    void imprimir(ActionEvent event) {

        prepararPDF(true, false);
    }

    double total = 0;
    @FXML
    void descargar(ActionEvent event) {

        prepararPDF(false, true);
    }

    @FXML
    void aceptar(ActionEvent event) throws IOException {
        quitarVistas();
        Funciones.CargarVistaAnterior(Pane, getClass().getResource( parametros.get(0).get("vista").toString() ), new InicioAdministrador());

    }


    @FXML
    void patronPaciente(KeyEvent event) throws IOException {
        patron = PacienteNombre.getText();
        Paciente.setItems(Datos.buscarPacientes(patron));
    }
    @FXML
    void cambiarPaciente(MouseEvent event) throws IOException {
        cargarDatos();
    }


    private ObservableList<VistaReporte> listaReporte;
    private String[] titulos;
    private void prepararPDF(boolean imprimir, boolean guardar) {


        String tituloAnterior = "";
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();
        String pacienteSelec = "Mostrador";
        if( Paciente.getSelectionModel().getSelectedIndex() != -1) {

            pacienteSelec = Paciente.getSelectionModel().getSelectedItem().getApellidos() + " " + Paciente.getSelectionModel().getSelectedItem().getNombre();
        }

        String predeterminados = "celdaTitulo="+(String) parametros.get(0).get("Titulo") +
                "@celdaDescripcion= Paciente: "+pacienteSelec +
                "@celdaTotal="+Funciones.valorAmoneda(total);

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
            Paciente.setItems(Datos.buscarPacientes(patron));
            //Paciente.setPromptText("Mostrador");
        } catch (IOException  e) {
            e.printStackTrace();
        }

        TablaReporte.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==1)
                    return;
                try {
                    Configuraciones.idVentaProductos = Integer.valueOf(TablaReporte.getSelectionModel().getSelectedItem().getValue().getDato("idVentaProductos"));
                    Map<String, Object> paramsVista = new LinkedHashMap<>();
                    paramsVista.put("idClinica", Configuraciones.idClinica);
                    paramsVista.put("vista", "/vista/venta_editar_unica.fxml");
                    Funciones.CargarVista((AnchorPane) Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new VentaEditarUnica());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


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
        total=0;
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                Map<String, Object> v = new LinkedHashMap<>();

                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());
                total += Double.valueOf(v.get("Importe").toString());
                v.put("Importe", Funciones.valorAmoneda(Double.valueOf((v.get("Importe").toString()) )));

                String f = v.get("Fecha").toString();
                try {
                    v.put("Fecha", Funciones.cambiarFormatoFecha(f, "yyyy-MM-dd hh:mm:ss", "dd-MM-yyyy    hh:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                listaReporte.add(new VistaReporte(v));
            }
            Map<String, Object> v = new LinkedHashMap<>();
            v.put("Importe", Funciones.valorAmoneda(total) );
            v.put("Paciente", "Total");
            listaReporte.add(new VistaReporte(v));

        }
    }

}
