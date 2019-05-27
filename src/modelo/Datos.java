package modelo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Datos {

    public static ObservableList<Pacientes> pacientes;
    public static ObservableList<Personal> personal;
    public static ObservableList<Caja> cajas;
    public static ObservableList<ProductosConCosto> productosConCostos;


    public static void cargarPacientes() throws IOException {
        pacientes = FXCollections.observableArrayList();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Pacientes: Lista");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                pacientes.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), Pacientes.class) );
            }
        }
    }



    public static ObservableList<Pacientes> buscarPacientes(String patron) {
        ObservableList<Pacientes> p = FXCollections.observableArrayList();
        if(patron.isEmpty())
            return pacientes;
        patron = patron.toUpperCase();
        for(Pacientes paciente : pacientes) {
            if(paciente.getNombre().toUpperCase().contains(patron) || paciente.getApellidos().toUpperCase().contains(patron))
                p.add(paciente);
        }
        return p;
    }



    public static void cargarPersonal() throws IOException {
        personal = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Personal: Lista");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                personal.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), modelo.Personal.class) );
            }
        }
    }

    public static ObservableList<Personal> buscarPersonal(int tipo) {
        ObservableList<Personal> p = FXCollections.observableArrayList();
        if(tipo==-1)
            return personal;
        for(Personal persona : personal) {
            if(persona.getTipo() == tipo)
                p.add(persona);
        }
        return p;
    }


    public static void cargarCajas() throws IOException {
        cajas = FXCollections.observableArrayList();
        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Caja: Lista");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                cajas.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), Caja.class) );
            }
        }
    }

    /**
     * Productos con precio
     */

    public static void cargarProductosConCosto() throws IOException {

        productosConCostos = FXCollections.observableArrayList();

        Map<String,Object> paramsJSON = new LinkedHashMap<>();
        paramsJSON.put("Actividad", "Productos: Lista con precios");
        paramsJSON.put("idClinica", Configuraciones.idClinica);
        JsonArray rootArray = Funciones.consultarBD(paramsJSON);
        if(rootArray.get(0).getAsJsonObject().get(Funciones.res).getAsInt()>0) {
            int t = rootArray.size();
            for(int i = 1; i< t; i++) {
                productosConCostos.add(new Gson().fromJson(rootArray.get(i).getAsJsonObject(), ProductosConCosto.class) );
            }
        }
    }
    public static ObservableList<ProductosConCosto> buscarProductosConCosto(String patron) {
        ObservableList<ProductosConCosto> p = FXCollections.observableArrayList();
        if(patron.isEmpty())
            return productosConCostos;
        patron = patron.toUpperCase();
        for(ProductosConCosto producto : productosConCostos) {
            if(     producto.getNombre().toUpperCase().contains(patron) ||
                    producto.getClave().toUpperCase().contains(patron)
                )
                p.add(producto);
        }
        return p;
    }
}
