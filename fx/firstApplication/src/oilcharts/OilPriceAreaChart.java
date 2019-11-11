package oilcharts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class OilPriceAreaChart extends Application {

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
        //create the axis
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Price");
        AreaChart<String, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        List<OilPrice> crudeOil = getOilData("crude.oil");
        List<OilPrice> brentOil = getOilData("brent.oil");
        areaChart.getData().add(getSeries("Crude Oil", crudeOil));
        areaChart.getData().add(getSeries("Brent Oil", brentOil));
        gridPane.add(areaChart, 1,1 );
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setTitle("Oil Area Chart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * get the oil data from the file
     * @param oilType the file name
     * @return list of oil prices
     */
    private List<OilPrice> getOilData(String oilType) {
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(oilType));
        List<OilPrice> data = new LinkedList<>();
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] elements = line.split("\\t");
            OilPrice oilPrice = new OilPrice();
            oilPrice.setPeriod(elements[0]);
            oilPrice.setValue(Double.parseDouble(elements[1]));
            data.add(oilPrice);
        }
        Collections.reverse(data);
        return data;
    }

    /**
     * get the series with the name of the series and data
     * @param seriesName the name of the series
     * @param data list of oil prices
     * @return XYChart series
     * @throws IOException
     */
    private XYChart.Series<String, Number> getSeries(String seriesName, List<OilPrice> data) throws IOException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        data.forEach( d -> {
           series.getData().add(new XYChart.Data<>(d.getPeriod(), d.getValue()));
        });
        return series;
    }
}
