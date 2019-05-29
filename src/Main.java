import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Configuraciones;
import modelo.Funciones;

import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicio_sesion.fxml"));
        primaryStage.setTitle("MGM");
        primaryStage.setScene(new Scene(root, Funciones.ancho, Funciones.alto));
        primaryStage.setMaximized(true);
        primaryStage.show();

        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        while (n.hasMoreElements()) {
            NetworkInterface ni = n.nextElement();
            byte[] mac = ni.getHardwareAddress();

            if(mac == null)
                continue;

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            Configuraciones.MAC = sb.toString();
            break;
        }


        Configuraciones.urlAlertBox=getClass().getResource("/vista/alert_box.fxml");
        //System.out.println(Configuraciones.MAC);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
