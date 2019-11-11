package oilcharts;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class OilPriceLineChart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25,25,25,25));
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Price");
        LineChart<String, Number> lineChart = new LineChart<>(xAxis,yAxis);
        List<OilPrice> crudeOil = getOilData("crude.oil");
        List<OilPrice> brentOil = getOilData("brent.oil");

        lineChart.getData().add(getSeries("Crude Oil", crudeOil));
        lineChart.getData().add(getSeries("Brent Oil", brentOil));
        gridPane.add(lineChart, 1,1 );
        Scene scene = new Scene(gridPane, 800, 600);
        stage.setTitle("Oil Charts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String...args) {
        launch(args);
    }

    /**
     * get the oil data from the file which correspond to the type of the oil.
     * @param oilType the type of the oil
     * @return list of oil price
     * @throws IOException
     */
    private List<OilPrice> getOilData(String oilType) throws IOException {
        Scanner reader = new Scanner(getClass().getClassLoader().getResourceAsStream(oilType));
        List<OilPrice> data = new LinkedList<>();
        while(reader.hasNext()) {
            String line = reader.nextLine();
            String[] elements = line.split("\\t");
            OilPrice op = new OilPrice();
            op.setPeriod(elements[0]);
            op.setValue(Double.parseDouble(elements[1]));
            data.add(op);
        }
        Collections.reverse(data);
        return data;
    }

    /**
     * create the series
     * @param seriesName  name of the series
     * @param data the list of oil pices
     * @return the series chart.
     * @throws IOException
     */
    private XYChart.Series<String, Number> getSeries(String seriesName, List<OilPrice> data) throws  IOException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        data.forEach(d -> {
            series.getData().add(new XYChart.Data<String, Number>(d.getPeriod(), d.getValue()));
        });
        return series;
    }
}
