package agecalculator.xml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.Period;

public class FxmlController {
    @FXML private Text resultText;
    @FXML private DatePicker dateOfBirthPicker;
    @FXML private TextField nameField;

    @FXML
    public void calculateAge(ActionEvent event) {
        String name = nameField.getText();
        LocalDate dob = dateOfBirthPicker.getValue();
        if ( dob != null ){
            LocalDate now = LocalDate.now();
            Period period = Period.between(dob, now);
            StringBuilder resultBuilder = new StringBuilder();

            if ( name != null && name.length() > 0 ){
                resultBuilder.append("Hello, ").append(name).append("\n");
            }
            resultBuilder.append(
                    String.format("Your age is %d years %d months %d days",
                            period.getYears(), period.getMonths(), period.getDays())
            );
            resultText.setText(resultBuilder.toString());
        }
    }
}
