package com.jhc.benchmarking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Benchmarking extends Application {
    
    BenchmarkingController controller;  // not sure if needed but...

    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myui.fxml"));
        GridPane grid = loader.load();
        controller = loader.getController();
        
        Scene scene = new Scene(grid, 700, 880);
        
        stage.setScene(scene);
        stage.setTitle("My Benchmarking Thing");
        stage.setAlwaysOnTop(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
