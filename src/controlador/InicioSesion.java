package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;
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

            Personal usuario = new Gson().fromJson(rootArray.get(1).getAsJsonObject(), Personal.class);
            params = new LinkedHashMap<>();
            params.put("idPersonal", usuario.getIdPersonal());
            params.put("idClinica", usuario.getIdClinica());
            params.put("nombre", usuario.getNombre());
            params.put("vista", "/vista/inicio_resumen.fxml");


            Funciones.CargarVista(Pane, getClass().getResource("/vista/inicio_administrador.fxml"), params, new InicioAdministrador());

        }
        else {
            Map<String,Object> paramsAlert = new LinkedHashMap<>();
            paramsAlert.put("titulo", "Error");
            paramsAlert.put("texto", "Usuario y/o clave incorrectos");
            paramsAlert.put("vista", "/vista/alert_box.fxml");
            Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
        }
    }



}
