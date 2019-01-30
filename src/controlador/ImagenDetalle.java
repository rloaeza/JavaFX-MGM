package controlador;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.CargarFotoCompleta;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImagenDetalle extends Controlador {


    @FXML
    private AnchorPane Pane;

    @FXML
    private ProgressBar ProgressBar;
    @FXML
    private AnchorPane PaneImagen;

    @FXML
    void aceptar(ActionEvent event) {

       cerrar();
    }



    @FXML
    private JFXButton BotonAceptar;

    private void cerrar() {
        Stage window = (Stage)(Pane.getScene().getWindow());
        parametros.remove(0);
        window.close();
    }
    @Override
    public void init() {
        while(PaneImagen.getChildren().size()>0)
            PaneImagen.getChildren().remove(0);


        CargarFotoCompleta cargarFotoCompleta = new CargarFotoCompleta(parametros.get(0).get("url").toString());

        cargarFotoCompleta.setOnRunning(event -> BotonAceptar.setText("Cargando ..."));
        cargarFotoCompleta.setOnSucceeded(e-> {
            ImageView imageView = null;
            try {
                imageView = new ImageView(cargarFotoCompleta.get() );
                imageView.setFitWidth(1400);
                imageView.setPreserveRatio(true);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            }
            PaneImagen.getChildren().add(imageView);
            BotonAceptar.setText("Aceptar");
            ProgressBar.setVisible(false);
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(cargarFotoCompleta);

        executorService.shutdown();


    }
}
