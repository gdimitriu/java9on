package charts;

import charts.student_processor.ParentEducation;
import charts.student_processor.Student;
import charts.student_processor.StudentDataProcessor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class BarChartDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        StudentDataProcessor studentDataProcessor = new StudentDataProcessor();
        List<Student> students = studentDataProcessor.loadStudent();
        BarChart<String, Number> avgGradeMyMotherEducation = getAverageGradeByEducationBarChart(students, Student::getMotherEducation);
        avgGradeMyMotherEducation.setTitle("Average grade by Mother's Education");
        gridPane.add(avgGradeMyMotherEducation, 1,1 );
        BarChart<String, Number> avgGradeByFatherEducation = getAverageGradeByEducationBarChart(students, Student::getFatherEducation);
        avgGradeByFatherEducation.setTitle("Average grade by Father's Education");
        gridPane.add(avgGradeByFatherEducation, 2,1 );
        Scene scene = new Scene(gridPane, 800, 600);
        stage.setTitle("Bar Charts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String...args) {
        launch(args);
    }

    /**
     * this will summarize the data from statistics
     * @param students the students
     * @param classifier the classifiers to make the grouping
     * @param mapper the mapper to int
     * @return the map
     */
    private Map<ParentEducation, IntSummaryStatistics> summarize(List<Student> students,
                                                                 Function<Student, ParentEducation> classifier,
                                                                 ToIntFunction<Student> mapper) {
        Map<ParentEducation, IntSummaryStatistics> statistics = students.stream()
                .collect(Collectors.groupingBy(classifier, Collectors.summarizingInt(mapper)));
        return statistics;
    }

    /**
     * get the series from statistics
     * @param seriesName the name of the series
     * @param statistic the statistic
     * @return the chart
     */
    private XYChart.Series<String, Number> getSeries(String seriesName, Map<ParentEducation, IntSummaryStatistics> statistic) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        statistic.forEach((key, value) ->{
            series.getData().add(new XYChart.Data<String, Number>(key.toString(), value.getAverage()));
        });
        return series;
    }

    /**
     * create the bar chart for the average of degree
     * @param students the list of students
     * @param classifier the classifier
     * @return the bar chart
     */
    private BarChart<String, Number> getAverageGradeByEducationBarChart(List<Student> students, Function<Student, ParentEducation> classifier) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
        xAxis.setLabel("Education");
        yAxis.setLabel("Grade");
        bc.getData().add(getSeries("G1", summarize(students, classifier, Student::getFirstTermGrade)));
        bc.getData().add(getSeries("G2", summarize(students, classifier, Student::getSecondTermGrade)));
        bc.getData().add(getSeries("Final", summarize(students, classifier, Student::getFinalGrade)));
        return bc;
    }
}
