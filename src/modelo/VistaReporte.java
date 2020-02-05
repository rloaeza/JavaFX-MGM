package modelo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class VistaReporte extends RecursiveTreeObject<VistaReporte> {


    private Map<String, Object> datos;

    public VistaReporte(Map<String, Object> datos) {
        this.datos = datos;
    }

    public VistaReporte( String... keys ){
        datos = new LinkedHashMap<>();
        for(String key: keys) {
            datos.put(key, "");
        }
    }

    public String getDato(String campo) {
        return (String)datos.get(campo);
    }

    public Map<String, Object> getDatos() {
        return datos;
    }

    public VistaReporte registroNuevo () {
        Map<String, Object> registro = new LinkedHashMap<>();
        datos.forEach((k, v)-> {
            registro.put(k, "");
        });
        return  new VistaReporte(registro);
    }

    public void setDatos(Map<String, Object> nuevosDatos) {
        this.datos = nuevosDatos;
    }

    public void setDato(String campo, Object valor) {
        datos.put(campo, valor);
    }
}
