package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import modelo.Configuraciones;
import modelo.Funciones;
import modelo.Personal;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class InicioSesion  extends Controlador{

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

            params.put("clinicaDescripcion", usuario.getTitulo());

            params.put("nombre", usuario.getNombre());
            params.put("vista", "/vista/inicio_resumen.fxml");


            Configuraciones.idClinica = usuario.getIdClinica();
            Configuraciones.idPersonal = usuario.getIdPersonal();
            Configuraciones.clinicaDescripcion = usuario.getTitulo();
            Configuraciones.nombrePersonal=usuario.getApellidos()+", "+usuario.getNombre();
            Configuraciones.clavePersonal = usuario.getClave();
            Configuraciones.cajaAbierta=false;
            //Usuario de venta
            if(usuario.getTipo()==Configuraciones.tipoVendedor) {
                Configuraciones.tipoUsuarioActivo = Configuraciones.tipoVendedor;
                Configuraciones.cajaAbierta = false;

                params = new LinkedHashMap<>();
                params.put("Actividad", "CajaCorte: Listar");
                params.put("idPersonal", Configuraciones.idPersonal);

                rootArray = Funciones.consultarBD(params);
                if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {

                    modelo.CorteCaja corteCaja = new Gson().fromJson(rootArray.get(1).getAsJsonObject(), modelo.CorteCaja.class);
                    if ( corteCaja.getTipo() == 1) {
                        Configuraciones.cajaAbierta = true;
                        Configuraciones.idCaja = corteCaja.getIdCaja();
                        Configuraciones.aperturaCaja = corteCaja.getMonto();
                    }
                }
                if(Configuraciones.cajaAbierta==false) {


                    Map<String, Object> paramsAlert = new LinkedHashMap<>();
                    paramsAlert.put("titulo", "Corte de caja");

                    paramsAlert.put("vista", "/vista/corte_caja.fxml");

                    Configuraciones.corteCajaValido = false;
                    Configuraciones.abriendoCaja = true;
                    Funciones.display(paramsAlert, getClass().getResource("/vista/corte_caja.fxml"), new CorteCaja(), 762, 418);
                    if (!Configuraciones.corteCajaValido)
                        return;
                    Configuraciones.cajaAbierta = true;

                }


                Map<String,Object> paramsVista = new LinkedHashMap<>();
                paramsVista.put("idPersonal", Configuraciones.idPersonal);
                paramsVista.put("vista", "/vista/inicio_venta.fxml" );
                Funciones.CargarVista((AnchorPane)Pane, getClass().getResource(paramsVista.get("vista").toString()), paramsVista, new InicioAdministrador());
            } else {

                Funciones.CargarVista(Pane, getClass().getResource("/vista/inicio_administrador.fxml"), params, new InicioAdministrador());
            }
        }
        else {
            Map<String,Object> paramsAlert = new LinkedHashMap<>();
            paramsAlert.put("titulo", "Error");
            paramsAlert.put("texto", "Usuario y/o clave incorrectos");
            paramsAlert.put("vista", "/vista/alert_box.fxml");
            Funciones.display(paramsAlert, getClass().getResource("/vista/alert_box.fxml"), new AlertBox() );
        }
    }


    @Override
    public void init() {

    }
}
