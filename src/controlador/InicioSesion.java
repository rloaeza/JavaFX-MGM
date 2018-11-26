package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class InicioSesion  {

    @FXML
    private AnchorPane Pane;

    @FXML
    private JFXTextField textUsuario;

    @FXML
    private JFXPasswordField textClave;

    @FXML
    void entrarSistema(ActionEvent event) throws IOException {

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("Actividad", "InicioSesion");
        params.put("idClinica", "1");
        params.put("usuario", textUsuario.getText());
        params.put("clave", textClave.getText());

        JsonArray rootArray = Funciones.consultarBD(params);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/inicio_administrador.fxml"));
            Parent root = fxmlLoader.load();
            Stage escenario = (Stage) Pane.getScene().getWindow();
            escenario.setScene(new Scene(root, Funciones.ancho, Funciones.alto));
            InicioAdministrador inicioAdministrador = fxmlLoader.getController();

            Personal usuario = new Gson().fromJson(rootArray.get(1).getAsJsonObject(), Personal.class);


            inicioAdministrador.setUsuario(usuario);
            inicioAdministrador.init();

        }
        else {
            Funciones.display("Error","Usuario y/o clave incorrectos");
        }
    }



}
