package modelo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.Map;

public class VistaReporte extends RecursiveTreeObject<VistaReporte> {


    private Map<String, Object> datos;

    public VistaReporte(Map<String, Object> datos) {
        this.datos = datos;
    }

    public String getDato(String campo) {
        return (String)datos.get(campo);
    }

    public void setDato(String campo, Object valor) {
        datos.put(campo, valor);
    }
}
