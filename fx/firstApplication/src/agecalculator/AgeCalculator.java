package agecalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //create gridPane geometry and alignment
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        //create the name of the application label
        Text appTitle = new Text("Age calculator");
        appTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(appTitle, 0,0,2,1);
        //add Name field and label
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1,1);
        //add date of birth picker and label
        Label dateLabel = new Label("Date of birth");
        gridPane.add(dateLabel, 0, 2);
        DatePicker datePicker = new DatePicker();
        gridPane.add(datePicker, 1,2);
        //add the button to trigger the date calculation
        Button ageCalculator = new Button("Calculate");
        gridPane.add(ageCalculator, 1, 3);
        //add the component which will hold the result of the calculation
        Text resultText = new Text();
        resultText.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        gridPane.add(resultText, 0, 5, 2, 1);

        //add trigger for the text event
        ageCalculator.setOnAction((event) -> {
           String name = nameField.getText();
           LocalDate dob = datePicker.getValue();
           if (dob != null) {
               LocalDate now = LocalDate.now();
               Period period = Period.between(dob, now);
               StringBuilder resultBuilder = new StringBuilder();
               if (name != null && name.length() > 0 ) {
                   resultBuilder.append("Hello, ").append(name).append("\n");
               }
               resultBuilder.append(String.format("Your age is %d years %d months %d days", period.getYears(), period.getMonths(), period.getDays()));
               resultText.setText(resultBuilder.toString());
           }
        });

        //set the grid panel to the scene which is the layout
        Scene scene = new Scene(gridPane, 300,250);

        //stage is the root container similar to Frame
        stage.setTitle("Age calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String...args) {
        launch(args);
    }
}
