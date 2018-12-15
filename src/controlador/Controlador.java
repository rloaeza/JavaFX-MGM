package controlador;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.Map;

public abstract class  Controlador {

    public static ArrayList<Map<String, Object> > parametros;

    public void setParams(Map<String, Object> params)  {
        if(parametros==null)
            parametros = new ArrayList<>();
        parametros.add(0, params);

    }



    public abstract void init();


}
