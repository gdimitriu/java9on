package charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ChartsExample extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        //create data to the series that will be display
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("My series");
        series.getData().add(new XYChart.Data<String, Number>("Cat1",12));
        series.getData().add(new XYChart.Data<String, Number>("Cat2",3));
        series.getData().add(new XYChart.Data<String, Number>("Cat3",16));
        bc.getData().add(series);
        Scene scene = new Scene(bc, 800, 600);
        stage.setTitle("Bar Charts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String...args) {
        launch(args);
    }
}
