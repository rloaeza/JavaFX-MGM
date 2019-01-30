package modelo;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class CargarFotoCompleta extends Task<Image> {

    private String url;


    public CargarFotoCompleta(String url) {
        this.url = url;
    }

    @Override
    protected Image call() {
        //System.out.println(url);
        return new Image(url.replace("#", ""));

    }

    public String getUrl() {
        return url;
    }
}
