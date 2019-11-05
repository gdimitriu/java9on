package agecalculator.xml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AgeCalculatorFxml extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane pane = (Pane) loader.load(getClass().getClassLoader().getResourceAsStream("agecalculator/xml/fxml_age_calc_gui.fxml"));
        Scene scene = new Scene(pane, 300, 250);
        stage.setTitle("Age calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String...args) {
        launch(args);
    }
}
