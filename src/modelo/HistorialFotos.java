package modelo;

public class HistorialFotos {
    private int idFoto;
    private String archivo;
    private int idHistorial;

    public HistorialFotos(int idFoto, String archivo, int idHistorial) {
        this.idFoto = idFoto;
        this.archivo = archivo;
        this.idHistorial = idHistorial;
    }

    public HistorialFotos() {
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }
}
