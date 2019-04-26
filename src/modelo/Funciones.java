package modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
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

    public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
                                   String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth+3)/4)*4);
        int bfType = 0x424d; // 位图文件类型（0—1字节）
        int bfSize = 54 + 1024 + w * nHeight;// bmp文件的大小（2—5字节）
        int bfReserved1 = 0;// 位图文件保留字，必须为0（6-7字节）
        int bfReserved2 = 0;// 位图文件保留字，必须为0（8-9字节）
        int bfOffBits = 54 + 1024;// 文件头开始到位图实际数据之间的字节的偏移量（10-13字节）

        dos.writeShort(bfType); // 输入位图文件类型'BM'
        dos.write(changeByte(bfSize), 0, 4); // 输入位图文件大小
        dos.write(changeByte(bfReserved1), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfReserved2), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfOffBits), 0, 4);// 输入位图文件偏移量

        int biSize = 40;// 信息头所需的字节数（14-17字节）
        int biWidth = nWidth;// 位图的宽（18-21字节）
        int biHeight = nHeight;// 位图的高（22-25字节）
        int biPlanes = 1; // 目标设备的级别，必须是1（26-27字节）
        int biBitcount = 8;// 每个像素所需的位数（28-29字节），必须是1位（双色）、4位（16色）、8位（256色）或者24位（真彩色）之一。
        int biCompression = 0;// 位图压缩类型，必须是0（不压缩）（30-33字节）、1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之一。
        int biSizeImage = w * nHeight;// 实际位图图像的大小，即整个实际绘制的图像大小（34-37字节）
        int biXPelsPerMeter = 0;// 位图水平分辨率，每米像素数（38-41字节）这个数是系统默认值
        int biYPelsPerMeter = 0;// 位图垂直分辨率，每米像素数（42-45字节）这个数是系统默认值
        int biClrUsed = 0;// 位图实际使用的颜色表中的颜色数（46-49字节），如果为0的话，说明全部使用了
        int biClrImportant = 0;// 位图显示过程中重要的颜色数(50-53字节)，如果为0的话，说明全部重要

        dos.write(changeByte(biSize), 0, 4);// 输入信息头数据的总字节数
        dos.write(changeByte(biWidth), 0, 4);// 输入位图的宽
        dos.write(changeByte(biHeight), 0, 4);// 输入位图的高
        dos.write(changeByte(biPlanes), 0, 2);// 输入位图的目标设备级别
        dos.write(changeByte(biBitcount), 0, 2);// 输入每个像素占据的字节数
        dos.write(changeByte(biCompression), 0, 4);// 输入位图的压缩类型
        dos.write(changeByte(biSizeImage), 0, 4);// 输入位图的实际大小
        dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入位图的水平分辨率
        dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入位图的垂直分辨率
        dos.write(changeByte(biClrUsed), 0, 4);// 输入位图使用的总颜色数
        dos.write(changeByte(biClrImportant), 0, 4);// 输入位图使用过程中重要的颜色数

        for (int i = 0; i < 256; i++) {
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(0);
        }

        byte[] filter = null;
        if (w > nWidth)
        {
            filter = new byte[w-nWidth];
        }

        for(int i=0;i<nHeight;i++)
        {
            dos.write(imageBuf, (nHeight-1-i)*nWidth, nWidth);
            if (w > nWidth)
                dos.write(filter, 0, w-nWidth);
        }
        dos.flush();
        dos.close();
        fos.close();
    }

    public static byte[] changeByte(int data) {
        return intToByteArray(data);
    }
    public static byte[] intToByteArray (final int number) {
        byte[] abyte = new byte[4];
        // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
        abyte[0] = (byte) (0xff & number);
        // ">>"右移位，若为正数则高位补0，若为负数则高位补1
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }
    public static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        // "|="按位或赋值。
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

    public static void FreeSensor()
    {
        try {		//wait for thread stopping
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (0 != Configuraciones.mhDB)
        {
            FingerprintSensorEx.DBFree(Configuraciones.mhDB);
            Configuraciones.mhDB = 0;
        }
        if (0 != Configuraciones.mhDevice)
        {
            FingerprintSensorEx.CloseDevice(Configuraciones.mhDevice);
            Configuraciones.mhDevice = 0;
        }
        FingerprintSensorEx.Terminate();
    }


    public static void inicializarFP() {

        Configuraciones.fpActivo = false;

        if (0 != Configuraciones.mhDevice)
        {
            //already inited
            System.out.println("Please close device first!\n");
            return;
        }
        int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
        //Initialize
        Configuraciones.cbRegTemp = 0;
        Configuraciones.bRegister = false;
        Configuraciones.bIdentify = false;
        Configuraciones.iFid = 1;
        Configuraciones.enroll_idx = 0;
        if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init())
        {
            System.out.println("Init failed!\n");
            return;
        }
        ret = FingerprintSensorEx.GetDeviceCount();
        if (ret < 0)
        {
            System.out.println("No devices connected!\n");
            FreeSensor();
            return;
        }
        if (0 == (Configuraciones.mhDevice = FingerprintSensorEx.OpenDevice(0)))
        {
            System.out.println("Open device fail, ret = " + ret + "!\n");
            FreeSensor();
            return;
        }
        if (0 == (Configuraciones.mhDB = FingerprintSensorEx.DBInit()))
        {
            System.out.println("Init DB fail, ret = " + ret + "!\n");
            FreeSensor();
            return;
        }

        FingerprintSensorEx.DBSetParameter(Configuraciones.mhDB,  5010, 0);
        byte[] paramValue = new byte[4];
        int[] size = new int[1];

        size[0] = 4;
        FingerprintSensorEx.GetParameters(Configuraciones.mhDevice, 1, paramValue, size);
        Configuraciones.fpWidth = Funciones.byteArrayToInt(paramValue);
        size[0] = 4;
        FingerprintSensorEx.GetParameters(Configuraciones.mhDevice, 2, paramValue, size);
        Configuraciones.fpHeight = Funciones.byteArrayToInt(paramValue);

        Configuraciones.imgbuf = new byte[Configuraciones.fpWidth*Configuraciones.fpHeight];
        Configuraciones.fpActivo = true;
        System.out.println("Open succ! Finger Image Width:" + Configuraciones.fpWidth + ",Height:" + Configuraciones.fpHeight +"\n");


    }
}
