package controlador;

import modelo.Funciones;

import java.util.ArrayList;
import java.util.Map;

public abstract class  Controlador {

    public static ArrayList<Map<String, Object> > parametros;

    public void setParams(Map<String, Object> params)  {
        if(parametros==null)
            parametros = new ArrayList<>();
        parametros.add(0, params);

    }


    public void quitarVistas() {
        while (parametros.size()>2)
            parametros.remove(0);

    }

    public abstract void init();


}
