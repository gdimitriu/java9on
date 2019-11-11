package storecharts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class StoreBubbleChart extends Application {
    public static void main(String...args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(25,25, 25, 25));
        //create the axis
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hour");
        yAxis.setLabel("Visits");
        //create the bubbleChart
        BubbleChart<Number, Number> bubbleChart = new BubbleChart<>(xAxis, yAxis);
        //create the series
        List<StoreVisit> data = getData();
        Integer maxSale = getMaxScale(data);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Store Visits");
        data.forEach(sv -> {
            series.getData().add(new XYChart.Data<>(sv.getHour(), sv.getVisits(), (sv.getSales()/(maxSale*1d)) *2));
        });
        bubbleChart.getData().add(series);
        gridPane.add(bubbleChart, 1,1);
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("Store Sales Bubble Chart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * get the list of store visits from the file
     * @return the list of store visit
     * @throws IOException
     */
    private List<StoreVisit> getData() throws IOException {
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("store"));
        List<StoreVisit> data = new LinkedList<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] elements = line.split(",");
            StoreVisit sv = new StoreVisit(elements);
            data.add(sv);
        }
        return data;
    }

    /**
     * get the maximum number of sales
     * @param data the data
     * @return maximum nr of sales
     */
    private Integer getMaxScale(List<StoreVisit> data) {
        return data.stream().mapToInt(StoreVisit::getSales).max().getAsInt();
    }

}
