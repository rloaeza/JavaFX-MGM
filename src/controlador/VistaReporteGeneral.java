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
import modelo.*;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class VistaReporteGeneral extends Controlador implements Initializable {



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

        vr = listaReporte.remove(listaReporte.size()-1);
        prepararPDF(true, false);
        listaReporte.add(vr);
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
    private ObservableList<modelo.VistaReporte> listaReporte;
    private String[] titulos;
    private void prepararPDF(boolean imprimir, boolean guardar) {
        String tituloAnterior = "";
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();

        String predeterminados = "celdaTitulo="+(String) parametros.get(0).get("Titulo") +
                "@celdaDescripcion="+descripcion+
                "@celdaTratamiento="+vr.getDato("Tratamiento")+
                "@celdaProducto="+vr.getDato("Producto")+
                "@celdaEfectivo="+vr.getDato("Efectivo")+
                "@celdaTarjeta="+vr.getDato("Tarjeta")+
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



       /*
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();

        int index=0;
        for(modelo.VistaReporte elemento : listaReporte) {


            for(String t: titulos) {
                String titulo = (t.split(":")[0]).replace(" ","");
                if((titulo.contains("IdVenta")||titulo.contains("Paciente"))) {
                    String valor = (valorPDf.get(titulo) == null ? "" : valorPDf.get(titulo) + "\n") + (elemento.getDato(titulo) == null ? "" : elemento.getDato(titulo));
                    if((index+1)%Configuraciones.lineasPorReporte==0)
                        valor += "@";
                    valorPDf.put(titulo, valor);
                }else {


                    String valor = (valorPDf.get(titulo) == null ? "" : valorPDf.get(titulo) + "\n") + (elemento.getDato(titulo) == null ? "" :
                            NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                                    .format(Double.valueOf(
                            elemento.getDato(titulo)
                                            )
                                    )
                    );
                    if((index+1)%Configuraciones.lineasPorReporte==0)
                        valor += "@";
                    valorPDf.put(titulo, valor);
                }

                if(!(titulo.contains("IdVenta")||titulo.contains("Paciente"))) {
                    double val =0;
                    try {
                        val = Double.valueOf(elemento.getDato(titulo) == null ? "0" : elemento.getDato(titulo));
                    } catch (Exception e){

                    }
                    //System.out.println(titulo+"->"+valorPDf.get("total"+titulo));
                    String valor2 = ""+(valorPDf.get("total" + titulo) == null ? val : Double.valueOf(valorPDf.get("total"+titulo).replace("@",""))+val );
                    if((index+1)%Configuraciones.lineasPorReporte==0)
                        valor2 += "@";
                    valorPDf.put("total"+titulo, valor2);
                }

            }
            index++;



        }


        for(String t: titulos) {
            String titulo = (t.split(":")[0]).replace(" ","");
            valoresPDF.add(new PDFvalores(titulo, valorPDf.get(titulo)));

            if(!(titulo.contains("IdVenta")||titulo.contains("Paciente"))) {
                double val=Double.valueOf(valorPDf.get("total"+titulo).replace("@", ""));
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


        */

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
        //FechaInicio.setValue(LocalDate.now().minusMonths(1) );
        FechaInicio.setValue(LocalDate.now() );
        FechaFin.setValue(LocalDate.now() );


        try {
            generarReporte(null);
        } catch (IOException  e) {
            e.printStackTrace();
        }



        TablaReporte.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==1)
                    return;
                try {
                    Configuraciones.idVentaProductos = Integer.valueOf(TablaReporte.getSelectionModel().getSelectedItem().getValue().getDato("IdVenta"));
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
        modelo.VistaReporte vrTotal=null;
        modelo.VistaReporte vrSig=null;
        String actividad = (String) parametros.get(0).get("reporteVentas");
        System.out.println("Reporte: "+ actividad);
        listaReporte.clear();
        paramsJSONReporte.put("Actividad", actividad);
        paramsJSONReporte.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSONReporte);

        double tratamiento=0;
        double producto = 0;
        double efectivo = 0;
        double tarjeta = 0;
        double total  =0;
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                Map<String, Object> v = new LinkedHashMap<>();

                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());

                tratamiento += v.get("Tratamiento")==null?0:Double.valueOf(v.get("Tratamiento").toString());
                producto += v.get("Producto")==null?0:Double.valueOf(v.get("Producto").toString());
                efectivo += v.get("Efectivo")==null?0:Double.valueOf(v.get("Efectivo").toString());
                tarjeta += v.get("Tarjeta")==null?0:Double.valueOf(v.get("Tarjeta").toString());
                total += v.get("Total")==null?0:Double.valueOf(v.get("Total").toString());

                if(v.get("Tratamiento")!=null) v.put("Tratamiento", Funciones.valorAmoneda(Double.valueOf(v.get("Tratamiento").toString())));
                if(v.get("Producto")!=null) v.put("Producto", Funciones.valorAmoneda(Double.valueOf(v.get("Producto").toString())));
                if(v.get("Efectivo")!=null) v.put("Efectivo", Funciones.valorAmoneda(Double.valueOf(v.get("Efectivo").toString())));
                if(v.get("Tarjeta")!=null) v.put("Tarjeta", Funciones.valorAmoneda(Double.valueOf(v.get("Tarjeta").toString())));
                if(v.get("Total")!=null) v.put("Total", Funciones.valorAmoneda(Double.valueOf(v.get("Total").toString())));

                listaReporte.add(new modelo.VistaReporte(v));
            }
            vrTotal = new VistaReporte(new LinkedHashMap<>());
            vrTotal.setDato("Venta", "");
            vrTotal.setDato("Paciente", "Total");
            vrTotal.setDato("Tratamiento", Funciones.valorAmoneda(tratamiento));
            vrTotal.setDato("Producto", Funciones.valorAmoneda(producto));
            vrTotal.setDato("Efectivo", Funciones.valorAmoneda(efectivo));
            vrTotal.setDato("Tarjeta", Funciones.valorAmoneda(tarjeta));
            vrTotal.setDato("Total", Funciones.valorAmoneda(total));
            listaReporte.add(vrTotal);

            vrSig = new VistaReporte(new LinkedHashMap<>());
            vrSig.setDato("Venta", "");
            vrSig.setDato("Paciente", "");
            vrSig.setDato("Tratamiento", "");
            vrSig.setDato("Producto", "");
            vrSig.setDato("Efectivo", "");
            vrSig.setDato("Tarjeta", "");
            vrSig.setDato("Total", "");
            listaReporte.add(vrSig);


        }




        vrTotal=null;
        actividad = (String) parametros.get(0).get("reporteVentasCanceladas");

        paramsJSONReporte.put("Actividad", actividad);
        paramsJSONReporte.put("idClinica", Configuraciones.idClinica);
        rootArray = Funciones.consultarBD(paramsJSONReporte);

        double tratamientoC=0;
        double productoC = 0;
        double efectivoC = 0;
        double tarjetaC = 0;
        double totalC  =0;
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {

            vrSig = new VistaReporte(new LinkedHashMap<>());
            vrSig.setDato("Venta", "");
            vrSig.setDato("Paciente", "CANCELADAS");
            vrSig.setDato("Tratamiento", "");
            vrSig.setDato("Producto", "");
            vrSig.setDato("Efectivo", "");
            vrSig.setDato("Tarjeta", "");
            vrSig.setDato("Total", "");


            listaReporte.add(vrSig);

            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                Map<String, Object> v = new LinkedHashMap<>();

                v= new Gson().fromJson(rootArray.get(i).getAsJsonObject(), v.getClass());

                tratamientoC += v.get("Tratamiento")==null?0:Double.valueOf(v.get("Tratamiento").toString());
                productoC += v.get("Producto")==null?0:Double.valueOf(v.get("Producto").toString());
                efectivoC += v.get("Efectivo")==null?0:Double.valueOf(v.get("Efectivo").toString());
                tarjetaC += v.get("Tarjeta")==null?0:Double.valueOf(v.get("Tarjeta").toString());
                totalC += v.get("Total")==null?0:Double.valueOf(v.get("Total").toString());

                if(v.get("Tratamiento")!=null) v.put("Tratamiento", Funciones.valorAmoneda(Double.valueOf(v.get("Tratamiento").toString())));
                if(v.get("Producto")!=null) v.put("Producto", Funciones.valorAmoneda(Double.valueOf(v.get("Producto").toString())));
                if(v.get("Efectivo")!=null) v.put("Efectivo", Funciones.valorAmoneda(Double.valueOf(v.get("Efectivo").toString())));
                if(v.get("Tarjeta")!=null) v.put("Tarjeta", Funciones.valorAmoneda(Double.valueOf(v.get("Tarjeta").toString())));
                if(v.get("Total")!=null) v.put("Total", Funciones.valorAmoneda(Double.valueOf(v.get("Total").toString())));

                listaReporte.add(new VistaReporte(v));
            }
            vrTotal = new VistaReporte(new LinkedHashMap<>());
            vrTotal.setDato("IdVenta", "");
            vrTotal.setDato("Paciente", "Total");
            vrTotal.setDato("Tratamiento", Funciones.valorAmoneda(tratamientoC));
            vrTotal.setDato("Producto", Funciones.valorAmoneda(productoC));
            vrTotal.setDato("Efectivo", Funciones.valorAmoneda(efectivoC));
            vrTotal.setDato("Tarjeta", Funciones.valorAmoneda(tarjetaC));
            vrTotal.setDato("Total", Funciones.valorAmoneda(totalC));
            listaReporte.add(vrTotal);
            vrSig = new VistaReporte(new LinkedHashMap<>());
            vrSig.setDato("Venta", "");
            vrSig.setDato("Paciente", "");
            vrSig.setDato("Tratamiento", "");
            vrSig.setDato("Producto", "");
            vrSig.setDato("Efectivo", "");
            vrSig.setDato("Tarjeta", "");
            vrSig.setDato("Total", "");
            listaReporte.add(vrSig);


            vrTotal = new VistaReporte(new LinkedHashMap<>());
            vrTotal.setDato("IdVenta", "");
            vrTotal.setDato("Paciente", "Total General");
            vrTotal.setDato("Tratamiento", Funciones.valorAmoneda(tratamiento-tratamientoC));
            vrTotal.setDato("Producto", Funciones.valorAmoneda(producto-productoC));
            vrTotal.setDato("Efectivo", Funciones.valorAmoneda(efectivo-efectivoC));
            vrTotal.setDato("Tarjeta", Funciones.valorAmoneda(tarjeta-tarjetaC));
            vrTotal.setDato("Total", Funciones.valorAmoneda(total-totalC));
            listaReporte.add(vrTotal);
        }


    }


    /*

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
        String tituloAnterior = "";
        ArrayList<PDFvalores> valoresPDF = new ArrayList<>();
        Map<String, String> valorPDf = new LinkedHashMap<>();



        String predeterminados = "celdaTitulo="+(String) parametros.get(0).get("Titulo") +
                "@celdaDescripcion="+LocalDate.now();
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
                String valor = Funciones.valorAmoneda(Double.valueOf(v.get("Ventas").toString()));
                v.put("Ventas", valor);
                listaReporte.add(new modelo.VistaReporte(v));
            }
        }
    }


     */

}
