
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Demo to use JavaFX 8 Printer API.
 *
 * @author cdea
 */
public class PrintDemo extends Application {
    @Override
    public void start(Stage primaryStage) {

        final TextField urlTextField = new TextField();
        final Button printButton = new Button("Print");
        final TextArea webPage = new TextArea();

        HBox hbox = new HBox();
        hbox.getChildren().addAll(urlTextField, printButton);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hbox);
        borderPane.setCenter(webPage);
        Scene scene = new Scene(borderPane, 300, 950);
        primaryStage.setTitle("Print Demo");
        primaryStage.setScene(scene);

        // print button pressed, page loaded
        final BooleanProperty printButtonClickedProperty = new SimpleBooleanProperty(false);
        final BooleanProperty pageLoadedProperty = new SimpleBooleanProperty(false);

        // when the a page is loaded and the button was pressed call the print() method.
        final BooleanProperty printActionProperty = new SimpleBooleanProperty(false);
        printActionProperty.bind(pageLoadedProperty.and(printButtonClickedProperty));



        // When the user clicks the print button the webview node is printed
        printButton.setOnAction( aEvent -> {
            System.out.println("clicando");
            printButtonClickedProperty.set(true);
            print(webPage);
        });

        // Once the print action hears a true go print the WebView node.
        printActionProperty.addListener( (ChangeListener) (obsValue, oldState, newState) -> {
            System.out.println("Imprimiendo");
            if (((boolean) newState)) {
                //print(webPage);
            }
        });

        primaryStage.show();

    }

    /** Scales the node based on the standard letter, portrait paper to be printed.
     * @param node The scene node to be printed.
     */
    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}