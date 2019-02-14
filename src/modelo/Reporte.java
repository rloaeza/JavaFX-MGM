package modelo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Reporte {
    public Map<String, Object> geetMap() throws InvocationTargetException, IllegalAccessException {
        Map<String, Object>map = new LinkedHashMap<>();
        Method[] methods=this.getClass().getMethods();

        for(Method method : methods) {
            if(method.getName().startsWith("get") && !method.getName().endsWith("Class")) {

                String val = (String) method.invoke(this, null);
                map.put(method.getName().substring(3), val);
            }
        }

        return map;
    }
}
