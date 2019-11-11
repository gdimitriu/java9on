package charts;

import charts.student_processor.Student;
import charts.student_processor.StudentDataProcessor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PieChartDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        StudentDataProcessor studentDataProcessor = new StudentDataProcessor();
        List<Student> students = studentDataProcessor.loadStudent();
        PieChart motherOccupationBreakUp = getStudentCountByOccupation(students, Student::getMotherJob);
        motherOccupationBreakUp.setTitle("Mother's Occupation");
        gridPane.add(motherOccupationBreakUp, 1,1);
        PieChart fatherOccupationBreakUp = getStudentCountByOccupation(students, Student::getFatherJob);
        fatherOccupationBreakUp.setTitle("Father's Occupation");
        gridPane.add(fatherOccupationBreakUp, 2, 1);
        Scene scene = new Scene(gridPane, 800, 600);
        stage.setTitle("Pie Charts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String...args) {
        launch(args);
    }

    private PieChart getStudentCountByOccupation(List<Student> students, Function<Student, String> classifier) {
        Map<String, Long> occupationBreakUp = students.stream().collect(Collectors.groupingBy(classifier, Collectors.counting()));
        List<PieChart.Data> pieChartData = new ArrayList<>();
        occupationBreakUp.forEach((key, value) -> pieChartData.add(new PieChart.Data(key, value)));
        PieChart chart = new PieChart(FXCollections.observableList(pieChartData));
        return chart;
    }
}
