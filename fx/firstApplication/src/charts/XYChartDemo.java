package charts;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class XYChartDemo extends Application {
    public static void main(String...args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Category");
        yAxis.setLabel("Price");
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Series 1");
        //add data to the first series
        series.getData().add(new XYChart.Data<>("Cat 1", 10));
        series.getData().add(new XYChart.Data<>("Cat 2", 20));
        series.getData().add(new XYChart.Data<>("Cat 3", 30));
        series.getData().add(new XYChart.Data<>("Cat 4", 40));
        lineChart.getData().add(series);
        //add data to the second series
        XYChart.Series<String, Number> series2 = new LineChart.Series<>();
        series2.setName("Series 2");
        series2.getData().add(new XYChart.Data<>("Cat 1", 40));
        series2.getData().add(new XYChart.Data<>("Cat 2", 30));
        series2.getData().add(new XYChart.Data<>("Cat 3", 20));
        series2.getData().add(new XYChart.Data<>("Cat 4", 10));
        lineChart.getData().add(series2);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        gridPane.add(lineChart, 1,1);
        Scene scene = new Scene(gridPane, 800, 600);
        stage.setTitle("Line Chart");
        stage.setScene(scene);
        stage.show();
    }
}
