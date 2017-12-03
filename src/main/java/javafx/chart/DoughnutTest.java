package javafx.chart;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class DoughnutTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ObservableList<Pair<CSSClassLabel, Double>> ring1 = FXCollections.observableArrayList();
        ring1.add(new Pair<CSSClassLabel, Double>(CSSClassLabel.JUST_CLICK, 75D));
        ring1.add(new Pair<CSSClassLabel, Double>(CSSClassLabel.NO_CLICK, 25D));

        ObservableList<Pair<CSSClassLabel, Double>> ring2 = FXCollections.observableArrayList();
        ring2.add(new Pair<CSSClassLabel, Double>(CSSClassLabel.CREDENTIAL, 16.67D));
        ring2.add(new Pair<CSSClassLabel, Double>(CSSClassLabel.CLASSLESS, 83.33D));

        Map<Integer, ObservableList<Pair<CSSClassLabel, Double>>> data = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        data.put(1, ring1);
        data.put(2, ring2);

        ObservableMap<Integer, ObservableList<Pair<CSSClassLabel, Double>>> map = FXCollections.observableMap(data);
        DoughnutChart doughnutChart = new DoughnutChart(map);
        doughnutChart.setLegendVisible(true);
        doughnutChart.setLegendSide(Side.BOTTOM);

        Scene scene = new Scene(doughnutChart, 1200, 700);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
