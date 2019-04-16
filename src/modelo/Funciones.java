package modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import controlador.Controlador;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class Funciones {

    public static String sitio =  "http://mgm.mas-aplicaciones.com/php/";

    //static String sitio =  "http://localhost/php/";

    public static String res = "Resultados";
    public static String ultimoInsertado = "ultimoInsertado";
    public static int alto = 700;
    public static int ancho = 900;


    public static String pdfAutor = "Robeto Loaeza Valerio";

    public static byte[] prepareVars(Map<String,Object> params) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        return  postDataBytes;
    }


    public static JsonArray consultarBD(Map<String,Object> params) throws IOException {
        URL url = new URL(sitio);


        URLConnection request = url.openConnection();

        request.setDoOutput(true);

        request.getOutputStream().write(Funciones.prepareVars(params));
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

        JsonArray rootArray = root.getAsJsonArray();
        return rootArray;



    }

    public static void display2(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Aceptar");
        closeButton.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,200, 100);
        window.setScene(scene);
        window.showAndWait();

    }
    public static void display( Map<String, Object> params,  URL vista,  Controlador c , int ancho, int alto) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);



        FXMLLoader fxmlLoader = new FXMLLoader(vista);
        AnchorPane root = null;

        root = fxmlLoader.load();

        c = fxmlLoader.getController();
        c.setParams(params);
        c.init();
        window.setTitle(params.get("titulo").toString());
        AnchorPane p = new AnchorPane();
        p.getChildren().setAll(root);

        Scene scene = new Scene(p, ancho, alto);
        window.setScene(scene);
        window.showAndWait();

    }
    public static void display( Map<String, Object> params,  URL vista,  Controlador c ) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);



        FXMLLoader fxmlLoader = new FXMLLoader(vista);
        AnchorPane root = null;

        root = fxmlLoader.load();

        c = fxmlLoader.getController();
        c.setParams(params);
        c.init();
        window.setTitle(params.get("titulo").toString());
        AnchorPane p = new AnchorPane();
        p.getChildren().setAll(root);

        Scene scene = new Scene(p, 500, 250);
        window.setScene(scene);
        window.showAndWait();

    }
    public static void displayImage( Map<String, Object> params,  URL vista,  Controlador c ) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);



        FXMLLoader fxmlLoader = new FXMLLoader(vista);
        AnchorPane root = null;

        root = fxmlLoader.load();

        c = fxmlLoader.getController();
        c.setParams(params);
        c.init();
        window.setTitle(params.get("titulo").toString());
        AnchorPane p = new AnchorPane();
        p.getChildren().setAll(root);

        Scene scene = new Scene(p, 900, 600);
        window.setScene(scene);
        window.showAndWait();

    }
    public static void displayBox( Map<String, Object> params,  URL vista,  Controlador c ) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);



        FXMLLoader fxmlLoader = new FXMLLoader(vista);
        AnchorPane root = null;

        root = fxmlLoader.load();

        c = fxmlLoader.getController();
        c.setParams(params);
        c.init();
        window.setTitle(params.get("titulo").toString());
        AnchorPane p = new AnchorPane();
        p.getChildren().setAll(root);

        Scene scene = new Scene(p, Integer.valueOf(params.get("ancho").toString()), Integer.valueOf(params.get("alto").toString()));
        window.setScene(scene);
        window.showAndWait();

    }

        public static void CargarVista(AnchorPane Pane, URL vista, Map<String, Object> params, Controlador c ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(vista);
        AnchorPane root = fxmlLoader.load();
        root.setPrefHeight(Pane.getHeight());
        root.setPrefWidth(Pane.getWidth());
        Pane.getChildren().setAll(root);

        c = fxmlLoader.getController();
        c.setParams(params);
        c.init();

    }

    public static void CargarVistaAnterior(AnchorPane Pane, URL vista, Controlador c ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(vista);
        AnchorPane root = fxmlLoader.load();
        root.setPrefHeight(Pane.getHeight());
        root.setPrefWidth(Pane.getWidth());
        Pane.getChildren().setAll(root);
        c = fxmlLoader.getController();
        c.init();

    }


    public static void CargarArchivo(String archivo, String nombre) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(sitio+"upload.php");

            FileBody bin = new FileBody(new File(archivo));
            StringBody comment = new StringBody(nombre, ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("userfile", bin)
                    .addPart("nombreFoto", comment)
                    .build();


            httppost.setEntity(reqEntity);

            //System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                //System.out.println("----------------------------------------");
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    public static String getURLfoto(String archivo) {
        //return sitio+"foto_preview.php?foto="+archivo;
        return sitio+"fotos/#"+archivo;
    }


    public static  void crearPDFLimpio(String archivo) throws IOException {
        PDDocument document = new PDDocument();
        PDDocumentInformation documentInformation = document.getDocumentInformation();
        documentInformation.setAuthor(pdfAutor);
        PDPage page = new PDPage();
        document.addPage(page);
        document.save(archivo);
        document.close();
    }

    public static void llenarPDF(String archivoOrigen,  ArrayList<PDFvalores>  valores, boolean imprimir, String archivoDestino) throws IOException, PrinterException {
        File file = new File(archivoOrigen);
        PDDocument document = PDDocument.load(file);
        llenarPDF(document.getDocumentCatalog().getAcroForm(), valores);
        if(imprimir)
            imprimirPDF(document);
        if(archivoDestino!=null)
            document.save(archivoDestino);
        document.close();
    }


    private static void llenarPDF(PDAcroForm acroForm, ArrayList<PDFvalores>  valores) throws IOException {
        if(acroForm!=null) {

            PDResources resources = new PDResources();
            resources.put(COSName.getPDFName("Courier"), PDType1Font.COURIER);
            resources.put(COSName.getPDFName("Cour"), PDType1Font.COURIER);

            acroForm.setDefaultResources(resources);

            for (PDFvalores valor : valores) {
                acroForm.getField(valor.getCampo()).setValue(valor.getValor());
            }
        }
    }
    private static void imprimirPDF(PDDocument pdf) throws PrinterException {
        PDFPrintable printable = new PDFPrintable(pdf, Scaling.SHRINK_TO_FIT);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(printable);
        job.print();
    }

    public static double fixN(double n, int lugares) {
        BigDecimal bd = new BigDecimal(n);
        bd = bd.setScale(lugares, RoundingMode.HALF_UP);



        return bd.doubleValue();
    }

    public static String valorAmoneda(double val) {
        return NumberFormat.getCurrencyInstance(new Locale("es", "MX"))
                .format(val);
    }

    public static String capitalize(String s) {
        if(s.isEmpty())
            return "";

        return s.substring(0,1).toUpperCase() + s.substring(1);

    }
    public static String capitalizeAll(String s) {
        if(s.isEmpty())
            return "";
        String[] array = s.split(" ");
        String newStr=new String();
        for(int i = 0; i < array.length; i++) {
            newStr += array[i].substring(0,1).toUpperCase() + array[i].substring(1) + " ";
        }
        return newStr.trim()+(s.charAt(s.length()-1)==' '?" ":"");
    }



    public static void CargarArchivo(String archivoOrigen, String archivoDestino, String carpeta, String resolucion) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(sitio+"upload2.php");

            FileBody userfile = new FileBody(new File(archivoOrigen));
            StringBody nombreFoto = new StringBody(archivoDestino, ContentType.TEXT_PLAIN);
            StringBody directorio = new StringBody(carpeta, ContentType.TEXT_PLAIN);
            StringBody res = new StringBody(resolucion, ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("userfile", userfile)
                    .addPart("nombreFoto", nombreFoto)
                    .addPart("directorio", directorio)
                    .addPart("res", res)
                    .build();


            httppost.setEntity(reqEntity);

            //System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                //System.out.println("----------------------------------------");
                //System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
