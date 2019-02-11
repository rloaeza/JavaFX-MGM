package modelo;

import javafx.concurrent.Task;

public class SubirFoto extends Task {

    private String archivo;
    private String nombre;

    public SubirFoto(String archivo, String nombre) {
        this.archivo = archivo;
        this.nombre = nombre;
    }

    @Override
    protected Object call() throws Exception {
        Funciones.CargarArchivo(archivo, nombre);
        return null;
    }


}
