package controlador;

import java.util.Map;

public abstract class  Controlador {
    public Map<String,Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    public void init() {}
}
