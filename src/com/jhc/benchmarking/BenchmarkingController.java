package com.jhc.benchmarking;

import com.jhc.benchmarking.bmwork.ArrayListMeasure;
import com.jhc.benchmarking.bmwork.HashMapMeasure;
import com.jhc.benchmarking.bmwork.LinkedListMeasure;
import com.jhc.benchmarking.bmwork.Measure;
import com.jhc.benchmarking.bmwork.TreeMapMeasure;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class BenchmarkingController implements Initializable {
    
    @FXML
    private Button arrayListBenchmark;

    @FXML
    private Button linkedListBenchmark;
    
    @FXML
    private Button hashMapBenchmark;

    @FXML
    private Button treeMapBenchmark;

    @FXML
    private TextArea detailsTextArea;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Population of anything ??
        
        // Binding of various controls to properties ??
    }    

    @FXML
    void arrayListBenchmarkClicked(ActionEvent event) {        
        detailsTextArea.clear();
        detailsTextArea.appendText("ArrayList measures:\n");
        
        Measure measure = new ArrayListMeasure();
        
        // formerly - run synchronously
//        performRequiredListBenchmarks(measure);

        // Don't do this - JavaFx does not like it !! (need to find out why !?)
//        Thread t = new Thread( () -> performRequiredListBenchmarks(measure));
//        t.start();

        // do this to run async
        Platform.runLater(() -> performRequiredListBenchmarks(measure));
    }    

    @FXML
    void linkedListBenchmarkClicked(ActionEvent event) {
        detailsTextArea.clear();
        detailsTextArea.appendText("LinkedList measures:\n");
        
        Measure measure = new LinkedListMeasure();
        Platform.runLater( () -> performRequiredListBenchmarks(measure));
    }

    private void performRequiredListBenchmarks(Measure measure) {
        
        // 10 is underkill, 100 mill overkill..
        
        List<String> benchmark100Elements = measure.performHundredElementBenchmarks();
        detailsTextArea.appendText("--- for 100 elements\n");
        benchmark100Elements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );

        List<String> benchmarkThouElements = measure.performThousandElementBenchmarks();
        detailsTextArea.appendText("--- for thousand elements\n");
        benchmarkThouElements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );

        List<String> benchmarkTenThouElements = measure.performTenThousandElementBenchmarks();
        detailsTextArea.appendText("--- for ten thousand elements\n");
        benchmarkTenThouElements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );

        List<String> benchmarkMillElements = measure.performMillionElementBenchmarks();
        detailsTextArea.appendText("--- for million elements\n");
        benchmarkMillElements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );
        
    }
    
    @FXML
    void hashMapBenchmarkClicked(ActionEvent event) {
        detailsTextArea.clear();
        detailsTextArea.appendText("HashMap measures:\n");
        
        Measure measure = new HashMapMeasure();
        performRequiredMapBenchmarks(measure);

    }


    @FXML
    void treeMapBenchmarkClicked(ActionEvent event) {
        detailsTextArea.clear();
        detailsTextArea.appendText("TreeMap measures:\n");
        
        Measure measure = new TreeMapMeasure();
        performRequiredMapBenchmarks(measure);

    }

    private void performRequiredMapBenchmarks(Measure measure) {
        List<String> benchmark100Elements = measure.performHundredElementBenchmarks();
        detailsTextArea.appendText("--- for 100 elements\n");
        benchmark100Elements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );

        List<String> benchmarkThouElements = measure.performThousandElementBenchmarks();
        detailsTextArea.appendText("--- for thousand elements\n");
        benchmarkThouElements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );

        List<String> benchmarkTenThouElements = measure.performTenThousandElementBenchmarks();
        detailsTextArea.appendText("--- for ten thousand elements\n");
        benchmarkTenThouElements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );

        List<String> benchmarkMillElements = measure.performMillionElementBenchmarks();
        detailsTextArea.appendText("--- for million elements\n");
        benchmarkMillElements.forEach(str -> detailsTextArea.appendText("\t" + str + "\n") );
    }
    
}
