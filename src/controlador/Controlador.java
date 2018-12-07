package controlador;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.Map;

public abstract class  Controlador {

    public static ArrayList<Map<String, Object> > parametros;
  //  public Map<String,Object> params;


    public void setParams(Map<String, Object> params)  {
        if(parametros==null)
            parametros = new ArrayList<>();
      //  this.params = params;
        parametros.add(0, params);
        System.out.println("Agregando: "+params.get("vista").toString());

    }



    public abstract void init();


}
