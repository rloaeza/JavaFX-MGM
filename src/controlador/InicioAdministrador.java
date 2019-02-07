package controlador;

import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Funciones;
import modelo.PDFvalores;
import modelo.Personal;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class InicioAdministrador extends  Controlador implements Initializable {


    @FXML
    private Pane Pane;

    private Personal usuario;


    @FXML
    private JFXButton BarraFecha;

    @FXML
    private JFXButton BarraAlmacen;

    @FXML
    private JFXButton BarraPagosTratamientos;

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicio_sesion.fxml"));
        Stage escenario = (Stage) Pane.getScene().getWindow();
        escenario.setScene(new Scene(root, Funciones.ancho, Funciones.alto));

    }

    @FXML
    void salir(ActionEvent event) {
        //Platform.exit();
        System.exit(1);
    }



    @FXML
    void catalogoTiposDeProductos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
        paramsVista.put("vista", "/vista/tipos_productos.fxml");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new controlador.TiposProductos());


    }
    @FXML
    void catalogoPersonal(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
        paramsVista.put("idPersonal", parametros.get(0).get("idPersonal"));
        paramsVista.put("vista", "/vista/personal.fxml");


        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource("/vista/personal.fxml"), paramsVista, new controlador.Personal());
    }
    @FXML
    void catalogoPacientes(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
        paramsVista.put("idPersonal", parametros.get(0).get("idPersonal"));
        paramsVista.put("vista", "/vista/pacientes.fxml");
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Pacientes());
    }
    @FXML
    void catalogoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/tratamientos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Productos());
    }

    @FXML
    void costoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/costo_tratamientos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new CostoTratamientos());
    }

    @FXML
    void catalogoServicios(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/catalogo_servicios.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new CatalogoServicios());
    }

    @FXML
    void almacenEntradas(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/almacen_entrada.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new AlmacenEntrada());
    }

    @FXML
    void almacenSalidas(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/almacen_salida.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new AlmacenSalida());
    }

    @FXML
    void costoProductos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/costo_productos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new CostoProductos());
    }

    @FXML
    void relojChecadorEntradaSalida(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/reloj_checador.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new RelojChecador());
    }

    @FXML
    void VentaMostrador(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", parametros.get(0).get("idClinica").toString());
        paramsVista.put("idPersonal", parametros.get(0).get("idPersonal").toString());
        paramsVista.put("vista", "/vista/venta_mostrador.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Citas());
    }

    @FXML
    void relojChecadorFijarHorarios(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/horarios.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Horarios());
    }

    @FXML
    void AgendarCitas(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/citas.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Citas());
    }

    @FXML
    void pagoTratamientos(ActionEvent event) throws IOException {
        Map<String,Object> paramsVista = new LinkedHashMap<>();
        paramsVista.put("idClinica", 1);
        paramsVista.put("vista", "/vista/deuda_tratamientos.fxml" );
        Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new DeudaTratamientos());
    }



    @FXML
    void uno(ActionEvent event)  {
        try {

            PDDocument document = new PDDocument();
            PDDocumentInformation documentInformation = document.getDocumentInformation();
            documentInformation.setAuthor("Roberto Loaeza Valerio");


            PDPage page = new PDPage();
            document.addPage(page);
            /*
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            //Begin the Content stream
            contentStream.beginText();
            contentStream.setLeading(30);

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            //Setting the position for the line

            contentStream.newLineAtOffset(1, 500);
            String text = "This is the sample document and we are adding content to it 1 . contentStream.newLineAtOffset(1, 1); contentStream.setFont(PDType1Font.TIMES_ROMAN, 12); contentStream.setFont(PDType1Font.TIMES_ROMAN, 12); This is the sample document and we are adding content to it 1 . contentStream.newLineAtOffset(1, 1); contentStream.setFont(PDType1Font.TIMES_ROMAN, 12); contentStream.setFont(PDType1Font.TIMES_ROMAN, 12); This is the sample document and we are adding content to it 1 . contentStream.newLineAtOffset(1, 1); contentStream.setFont(PDType1Font.TIMES_ROMAN, 12); contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);";
            contentStream.showText(text);

            contentStream.newLine();
            String text2 = "This is the sample document and we are adding content to it 2.";
            contentStream.showText(text2);

            //Ending the content stream
            contentStream.endText();

            contentStream.close();

            document.save("salida1.pdf");

            document.close();


            /*


            File file = new File("pdf1.pdf");
            System.out.print("Abriendo: "+file.getAbsolutePath());
            PDDocument document = PDDocument.load(file);
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if(acroForm!=null ) {
                PDTextField field = (PDTextField) acroForm.getField("tbNombre");
                field.setValue("Skynet es la onda cabron\n o que?\n de que?");

            }
            else {
                System.out.println("AcronForm null");
            }
            document.save("pdf2.pdf");
            document.close();
            */
            ArrayList<PDFvalores> valores = new ArrayList<>();

            valores.add(new PDFvalores("nombre", "Roberto"));
            valores.add(new PDFvalores("apellidos", "Loaeza Valerio"));
            valores.add(new PDFvalores("direccion", "Nicolas Bravo 2016"));
            valores.add(new PDFvalores("telefono", "4235253413"));
            valores.add(new PDFvalores("movil", "4521181954"));
            valores.add(new PDFvalores("correoElectronico", "roberto.loaeza@gmail.com"));

            valores.add(new PDFvalores("fechaInicio", "01 / Enero / 2019"));
            valores.add(new PDFvalores("fechaFin", "01 / Febrero / 2019"));
            valores.add(new PDFvalores("DiasTrabajados", "20"));
            valores.add(new PDFvalores("DiasConRetraso", "2"));

            Funciones.llenarPDF("informe1.pdf", "informe1-.pdf", valores);

        }catch (IOException ioe) {
            System.out.println("Causa: "+ ioe.getCause()+", Mensaje: "+ ioe.getMessage());
        }

    }

    public void setUsuario(Personal p) {
        this.usuario = p;
    }

    public void init() {

        //Estado.setText(usuario.getNombre());
        ((Stage)Pane.getScene().getWindow()).setTitle(parametros.get(0).get("nombre").toString());


        Stage escenario = (Stage) Pane.getScene().getWindow();

        escenario.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(Pane.getChildren().size()>0) {
                AnchorPane root = (AnchorPane) Pane.getChildren().get(0);
                root.setPrefHeight(Pane.getHeight());
                root.setPrefWidth(Pane.getWidth());
            }
        });









    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BarraFecha.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            BarraFecha.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        } ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();


        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(10000), ae -> {

            try {
                if(verificarAlmacen()) {
                    BarraAlmacen.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    BarraAlmacen.setBackground(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } ));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();



        Timeline t3 = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            try {
                Map<String, Object> paramsVista = new LinkedHashMap<>();
                paramsVista.put("idClinica", parametros.get(0).get("idClinica"));
                paramsVista.put("idPersonal", parametros.get(0).get("idPersonal"));
                paramsVista.put("vista", "/vista/citas.fxml");
                Funciones.CargarVista((AnchorPane) Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new Citas());
            }catch (IOException ioE) {

            }
        } ));
        t3.setCycleCount(1);
        t3.play();



        Timeline tPagos = new Timeline(new KeyFrame(Duration.millis(10000), ae -> {

            try {
                if(verificarPagos()) {
                    BarraPagosTratamientos.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else {
                    BarraPagosTratamientos.setBackground(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } ));
        tPagos.setCycleCount(Animation.INDEFINITE);
        tPagos.play();

    }


    private boolean verificarAlmacen() throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Almacen: Verificar minimos");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            return true;
        }
        return false;
    }

    private boolean verificarPagos() throws IOException {
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Tratamientos recetados: Pagos pendientes");

        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            return true;
        }
        return false;
    }

}
