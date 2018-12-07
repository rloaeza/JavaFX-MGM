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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class Funciones {

    static String sitio =  "http://mgm.mas-aplicaciones.com/php/";
    public static String res = "Resultados";
    public static int alto = 600;
    public static int ancho = 900;

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

    public static void display(String title, String message) {
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

    public static void CargarVista2(AnchorPane Pane, URL vista, Controlador c ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(vista);
        AnchorPane root = fxmlLoader.load();
        root.setPrefHeight(Pane.getHeight());
        root.setPrefWidth(Pane.getWidth());
        Pane.getChildren().setAll(root);

        c = fxmlLoader.getController();
        c.init();

    }

}
