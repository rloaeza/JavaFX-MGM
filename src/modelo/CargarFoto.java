package modelo;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class CargarFoto extends Task<Image> {

    private String url;

    public CargarFoto(String url) {
        this.url = url;
    }

    @Override
    protected Image call() {
        return new Image(url);

    }
}
