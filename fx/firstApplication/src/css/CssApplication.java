package css;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CssApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //create container
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        //create primary buttons
        Button primaryButton = new Button("Primary");
        primaryButton.getStyleClass().add("btn");
        primaryButton.getStyleClass().add("btn-primary");
        gridPane.add(primaryButton, 0, 1);
        //create success button
        Button successButton = new Button("Success");
        successButton.getStyleClass().add("btn");
        successButton.getStyleClass().add("btn-success");
        gridPane.add(successButton, 1,1 );
        //create the danger button
        Button dangerButton = new Button("Danger");
        dangerButton.getStyleClass().add("btn");
        dangerButton.getStyleClass().add("btn-danger");
        gridPane.add(dangerButton, 2, 1);
        //create the labels
        Label label = new Label("Default Label");
        label.getStyleClass().add("badge");
        gridPane.add(label, 0, 2);
        Label infoLabel = new Label("Info Label");
        infoLabel.getStyleClass().add("badge");
        infoLabel.getStyleClass().add("badge-info");
        gridPane.add(infoLabel, 1, 2);
        //create the TextField
        TextField bigTextField = new TextField();
        bigTextField.getStyleClass().add("big-input");
        gridPane.add(bigTextField, 0, 3, 3, 1);
        //create the radio buttons
        ToggleGroup group = new ToggleGroup();
        RadioButton bigRadioOne = new RadioButton("First");
        bigRadioOne.getStyleClass().add("big-radio");
        bigRadioOne.setToggleGroup(group);
        bigRadioOne.setSelected(true);
        gridPane.add(bigRadioOne, 0, 4);
        RadioButton bigRadioTwo = new RadioButton("Second");
        bigRadioTwo.setToggleGroup(group);
        bigRadioTwo.getStyleClass().addAll("big-radio");
        gridPane.add(bigRadioTwo,1, 4);
        //main scene part
        Scene scene = new Scene(gridPane, 600, 500);
        scene.getStylesheets().add("css/application.css");
        stage.setTitle("CSS Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String...args) {
        launch(args);
    }
}
