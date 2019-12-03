package webbrowser;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BrowserDemo extends Application {

    public static void main(String...args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //this will render the web page
        WebView webView = new WebView();
        //this is the engine which will load the default page
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://wwww.google.com");
        //create the browser address bar
        TextField webAddress = new TextField("http://www.google.com");
        //add listener to change the title of the browser and the web page in address bar
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue == Worker.State.SUCCEEDED) {
                    stage.setTitle(webEngine.getTitle());
                    webAddress.setText(webEngine.getLocation());
                }
            }
        });
        //create the button to load the page indentified by the URL entered in the address bar
        Button goButton = new Button("Go");
        goButton.setOnAction((event) -> {
           String url = webAddress.getText();
           if (url != null && url.length() > 0) {
               webEngine.load(url);
           }
        });
        //create the button which will go to the previous web page in the history using javascript history.back()
        Button prevButton  = new Button("Prev");
        prevButton.setOnAction(e -> webEngine.executeScript("history.back()"));
        //create the button which will go to the next web page in the history using webhistory api
        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            WebHistory wh = webEngine.getHistory();
            Integer historySize = wh.getEntries().size();
            Integer currentIndex = wh.getCurrentIndex();
            if (currentIndex < (historySize -1)) {
                wh.go(1);
            }
        });
        //create the reload button
        Button reloadButton = new Button("Refresh");
        reloadButton.setOnAction(e -> webEngine.reload());
        //add the created componenets to the addressBar
        HBox addressBar = new HBox(10);
        addressBar.setPadding(new Insets(10,5,10,5));
        addressBar.setHgrow(webAddress, Priority.ALWAYS);
        addressBar.getChildren().addAll(prevButton, nextButton, reloadButton, webAddress, goButton);
        //create the status for the loaded web page
        Label websiteLoadingStatus = new Label();
        webEngine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() != 100.0) {
                    websiteLoadingStatus.setText("Loading " + webAddress.getText());
                } else {
                    websiteLoadingStatus.setText("Done");
                }
            }
        });
        //align address bar webView, websiteLoadingStatus vertically
        VBox root = new VBox();
        root.getChildren().addAll(addressBar, webView, websiteLoadingStatus);
        Scene scene = new Scene(root);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setTitle("Web Browser");
        stage.setScene(scene);
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.show();
    }
}
