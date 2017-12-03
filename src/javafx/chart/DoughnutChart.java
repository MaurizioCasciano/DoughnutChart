package javafx.chart;

import com.sun.javafx.charts.Legend;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.chart.Chart;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

import java.util.Map;

public class DoughnutChart extends Chart {
    private Legend legend;
    private boolean legendItemsAdded = false;
    private static final double MIN_LEGEND_HEIGHT = 50;

    public DoughnutChart(ObservableMap<Integer, ObservableList<Pair<CSSClassLabel, Double>>> data) {
        this.data = data;
    }

    private double strokeWidth = 50;
    private double startAngle = 90;

    public double getStartAngle() {
        return this.startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        //to speed things up, I'm just removing old children and redrawing all of them again.
        //Instead of just updating center and radius of the arcs and so on...
        this.getChartChildren().clear();

        //calculate the center coordinates
        double centerX = contentWidth / 2 + left;
        double centerY = (contentHeight - MIN_LEGEND_HEIGHT) / 2 + top;
        //calculate the radius length
        double radius = Math.min(contentWidth, contentHeight - MIN_LEGEND_HEIGHT) / 2 - Math.max(left, top) * 2;

        /*------------------------
        -----Counterclockwise-----

                  +90°
            +180°     +0°/+360°
                 +270°
        ------------------------*/

         /*------------------------
         --------Clockwise---------

                 -270°
            -180°     +0°/-360°
                 -90°
         ------------------------*/

        int currentRing = 0;

        if (this.legend == null) {
            this.legend = new Legend();

            this.legend.setStyle("-fx-font-size: 30; -fx-alignment: center;");
            this.setLegend(legend);
        }

        for (Map.Entry<Integer, ObservableList<Pair<CSSClassLabel, Double>>> entry : this.data.entrySet()) {

            //------Reset the start angle at each iteration-------
            double currentStartAngle = this.startAngle;
            ObservableList<Pair<CSSClassLabel, Double>> ring = entry.getValue();


            for (Pair<CSSClassLabel, Double> pair : ring) {
                Arc arc = new Arc();

                arc.setCenterX(centerX);
                arc.setCenterY(centerY);

                arc.setRadiusX(radius - strokeWidth * currentRing);
                arc.setRadiusY(radius - strokeWidth * currentRing);

                arc.setStartAngle(currentStartAngle);
                arc.setLength(-percentageToAngle(pair.getSecond()));

                currentStartAngle += -percentageToAngle(pair.getSecond());

                arc.setStrokeWidth(this.strokeWidth);
                arc.setStrokeType(StrokeType.CENTERED);
                arc.setStroke(pair.getFirst().getColor());
                arc.setStrokeLineCap(StrokeLineCap.BUTT);
                arc.setFill(null);

                this.getChartChildren().add(arc);

                if (!this.legendItemsAdded) {
                    //ADD THE LEGEND ITEMS
                    CSSClassLabel cssClassLabel = pair.getFirst();
                    switch (cssClassLabel) {
                        case JUST_CLICK:
                        case NO_CLICK:
                        case CREDENTIAL: {
                            Legend.LegendItem item = new Legend.LegendItem(pair.getFirst().label + " (" + pair.getSecond() + "%)");
                            Circle circle = new Circle();
                            circle.setRadius(15);
                            circle.setFill(pair.getFirst().getColor());
                            item.setSymbol(circle);
                            this.legend.getItems().add(item);
                        }
                        case CLASSLESS: {
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            }

            currentRing++;
        }

        this.legendItemsAdded = true;
    }

    private ObservableMap<Integer, ObservableList<Pair<CSSClassLabel, Double>>> data;

    private static double percentageToAngle(double percentage) {
        //PERCENTAGE : 100 = ANGLE : 360
        //ANGLE = PERCENTAGE * 360 / 100
        return percentage * 360 / 100;
    }
}