package wicketcharts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class WicketScatterCharts extends Application {

    public static void main(String...args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        //read the data
        Map<String, List<FallOfWicket>> fow = getFallOfWickets();
        //create the axis
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Age");
        yAxis.setLabel("Marks");
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.getData().add(getSeries(fow.get("NZ"), "NZ"));
        scatterChart.getData().add(getSeries(fow.get("IND"), "IND"));
        //add the chart to panel
        gridPane.add(scatterChart, 1,1);
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("Cricket Bubble Charts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * get the map of fall of wickets
     * @return map of fall of wickets mapped on country
     * @throws IOException
     */
    private Map<String, List<FallOfWicket>>  getFallOfWickets() throws IOException {
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream("wickets"));
        Map<String, List<FallOfWicket>> data = new HashMap<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] elements = line.split(",");
            String country = elements[0];
            if (!data.containsKey(country)) {
                data.put(country, new ArrayList<FallOfWicket>());
            }
            data.get(country).add(new FallOfWicket(elements));
        }
        return data;
    }

    /**
     * get the XY series from the actual data
     * @param data the data
     * @param seriesName the name of the series
     * @return the XY Chart
     */
    private XYChart.Series<Number, Number> getSeries(List<FallOfWicket> data, String seriesName) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        data.forEach( s -> {
           series.getData().add(new XYChart.Data<>(s.getOver(), s.getScore()));
        });
        return series;
    }
}
