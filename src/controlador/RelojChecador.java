package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXPasswordField;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modelo.Funciones;
import modelo.Personal;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class RelojChecador extends Controlador implements Initializable {


    private long mhDevice = 0;
    private long mhDB = 0;
    private byte[] imgbuf = null;
    private byte[] template = new byte[2048];
    private int[] templateLen = new int[1];
    private int ret = 0;
    //the width of fingerprint image
    int fpWidth = 0;
    //the height of fingerprint image
    int fpHeight = 0;


    @FXML
    private AnchorPane Pane;

    @FXML
    private Label Hora;

    @FXML
    private JFXPasswordField UsuarioEntrada;

    @FXML
    private ImageView imagenHuella;
    private boolean fpActivo;


    @FXML
    void entrar(ActionEvent event) throws IOException {

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Lista con codigo");
        paramsJSON.put("idClinica", parametros.get(0).get("idClinica"));
        paramsJSON.put("codigo", UsuarioEntrada.getText());
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        Personal persona=null;
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                 persona = new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Personal.class);
            }
        }



        if(persona!=null) {
            paramsJSON.clear();
            paramsJSON.put("Actividad", "Reloj: Agregar");
            paramsJSON.put("idPersonal", persona.getIdPersonal());
            paramsJSON.put("fecha", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            rootArray = Funciones.consultarBD(paramsJSON);

            Map<String, Object> paramsAlert = new LinkedHashMap<>();
            paramsAlert.put("titulo", "Entrada / Salida");
            paramsAlert.put("texto", "Bienvenido " + persona.getApellidos() + ", " + persona.getNombre());
            paramsAlert.put("vista", "/vista/alert_box.fxml");
            Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox());


        }
        else {
            Map<String, Object> paramsAlert = new LinkedHashMap<>();
            paramsAlert.put("titulo", "Entrada / Salida");
            paramsAlert.put("texto", "Error en código");
            paramsAlert.put("vista", "/vista/alert_box.fxml");
            Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox());
        }

        UsuarioEntrada.setText("");

    }

    @Override
    public void init() {
        inicializarFP();

    }

    private boolean bRegister = false;
    //Identify
    private boolean bIdentify = true;
    //finger id
    private int iFid = 1;

    private int nFakeFunOn = 1;
    //must be 3
    static final int enroll_cnt = 3;
    //the index of pre-register function
    private int enroll_idx = 0;
    //for verify test
    //the length of lastRegTemp
    private int cbRegTemp = 0;

    private void inicializarFP() {

        fpActivo = false;

        if (0 != mhDevice)
        {
            //already inited
            System.out.println("Please close device first!\n");
            return;
        }
        int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
        //Initialize
        cbRegTemp = 0;
        bRegister = false;
        bIdentify = false;
        iFid = 1;
        enroll_idx = 0;
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
        if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0)))
        {
            System.out.println("Open device fail, ret = " + ret + "!\n");
            FreeSensor();
            return;
        }
        if (0 == (mhDB = FingerprintSensorEx.DBInit()))
        {
            System.out.println("Init DB fail, ret = " + ret + "!\n");
            FreeSensor();
            return;
        }

        FingerprintSensorEx.DBSetParameter(mhDB,  5010, 0);
        byte[] paramValue = new byte[4];
        int[] size = new int[1];

        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
        fpWidth = Funciones.byteArrayToInt(paramValue);
        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
        fpHeight = Funciones.byteArrayToInt(paramValue);

        imgbuf = new byte[fpWidth*fpHeight];
        fpActivo = true;
        System.out.println("Open succ! Finger Image Width:" + fpWidth + ",Height:" + fpHeight +"\n");


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Hora.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        Hora.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
            Hora.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        } ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();




        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {

            if(!fpActivo)
                return;

            templateLen[0] = 2048;

            if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen))) {
                System.out.println("cargando imagen");
                OnCatpureOK(imgbuf);
            }


        } ));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();




    }
    private void OnCatpureOK(byte[] imgBuf)
    {
        try {
            Funciones.writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
            File f = new File("fingerprint.bmp");
            imagenHuella.setImage(new Image(f.toURI().toString()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void FreeSensor()
    {
        try {		//wait for thread stopping
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (0 != mhDB)
        {
            FingerprintSensorEx.DBFree(mhDB);
            mhDB = 0;
        }
        if (0 != mhDevice)
        {
            FingerprintSensorEx.CloseDevice(mhDevice);
            mhDevice = 0;
        }
        FingerprintSensorEx.Terminate();
    }
}
