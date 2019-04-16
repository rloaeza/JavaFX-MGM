package modelo;

import javafx.concurrent.Task;

public class SubirFoto extends Task {

    private String archivoOrigen;
    private String archivoDestino;
    private String directorio;
    private String resolucion;

    public SubirFoto(String archivoOrigen, String archivoDestino) {
        this.archivoOrigen = archivoOrigen;
        this.archivoDestino = archivoDestino;
    }

    public SubirFoto(String archivoOrigen, String archivoDestino, String directorio, String resolucion) {
        this.archivoOrigen = archivoOrigen;
        this.archivoDestino = archivoDestino;
        this.directorio = directorio;
        this.resolucion = resolucion;
    }


    @Override
    protected Object call() throws Exception {
        Funciones.CargarArchivo(archivoOrigen, archivoDestino, directorio, resolucion);
        return null;
    }


}
