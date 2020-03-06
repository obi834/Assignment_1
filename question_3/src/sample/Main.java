//Obaida Issa 100702054
//CSCI2020U



package sample;




import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class Main extends Application {

    @Override

    public void start(Stage primaryStage) {

        final PointPane pane = new PointPane(640, 480);

        pane.setStyle("-fx-background-color: wheat;");

        Label label = new Label("Click and drag the points.");

        BorderPane borderPane = new BorderPane(pane);

        BorderPane.setAlignment(label, Pos.CENTER);

        label.setPadding(new Insets(5));

        borderPane.setBottom(label);

        Scene scene = new Scene(borderPane);

        primaryStage.setTitle("Question 3");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    private class PointPane extends Pane {

        final Circle circle = new Circle();

        final Vertex[] v = new Vertex[3];

        final int strokeWidth = 2;

        final Color circleStroke = Color.GRAY, legStroke = Color.BLACK;

        @SuppressWarnings("SameParameterValue")

        PointPane(double w, double h) {

            this.setPrefSize(w, h);

            this.setWidth(w);

            this.setHeight(h);

            circle.setStroke(circleStroke);
            circle.setFill(Color.TRANSPARENT);
            circle.setStrokeWidth(strokeWidth);
            circle.radiusProperty().bind(this.heightProperty().multiply(0.4));
            circle.centerXProperty().bind(this.widthProperty().divide(2));
            circle.centerYProperty().bind(this.heightProperty().divide(2));

            this.getChildren().add(circle);

            // create the vertices at random angles

            for (int i = 0; i < v.length; i++) {

                v[i] = new Vertex(circle, 2 * Math.PI / v.length * (i + Math.random()));

                v[i].radiusProperty().bind(circle.radiusProperty().divide(10));

                v[i].setPosition();

                v[i].setStroke(new Color(i == 0 ? 1 : 0, i == 1 ? 1 : 0, i == 2 ? 1 : 0, 1));

                v[i].setFill(Color.TRANSPARENT);

                v[i].setStrokeWidth(strokeWidth);

                this.getChildren().add(v[i]);

                v[i].setOnMouseDragged(new EventHandler<MouseEvent>() {

                    @Override

                    public void handle(MouseEvent event) {

                        int i;

                        for (i = 0; i < v.length; i++)

                            if (v[i] == event.getSource())

                                break;

                        v[i].setAngle(event.getX(), event.getY());

                        moveUpdate((Vertex) event.getSource());

                    }

                });

            }

            // create the legs and bind their endpoints to their corresponding vertices

            for (int i = 0; i < v.length; i++) {

                int j = i + 1 < v.length ? i + 1 : 0;

                int k = j + 1 < v.length ? j + 1 : 0;

                v[i].bindLeg(v[j], v[k]);

                v[i].leg.setStroke(legStroke);

                v[i].leg.setStrokeWidth(strokeWidth);

                this.getChildren().add(v[i].leg);

                // create the text nodes and bind their locations to their corresponding vertices

                this.getChildren().add(v[i].text);

            }

            for(DoubleProperty p: new DoubleProperty[]

                    {circle.radiusProperty(), circle.centerXProperty(), circle.centerYProperty()})

                p.addListener(new ResizeListener());

            moveUpdate(v[0]);

        }

        void moveUpdate(Vertex vert) {

            vert.setPosition();

            double[] legLength = new double[3];

            for (int i = 0; i < v.length; i++)

                legLength[i] = v[i].getLegLength();

            for (int i = 0; i < v.length; i++) {
                int j = i + 1 < v.length ? i + 1 : 0;
                int k = j + 1 < v.length ? j + 1 : 0;
                double a = legLength[i], b = legLength[j], c = legLength[k];
                double d = Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c)));

                v[i].setText(d);

            }

        }

        private class ResizeListener implements ChangeListener<Number> {

            @Override

            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {

                for (Vertex aV : v) {

                    aV.setPosition();

                }

            }

        }

    }

    private class Vertex extends Circle {

        final Circle circle;

        final Line leg;

        final Text text;

        double centerAngle;
    //circle declaration
        Vertex(Circle circle, double centerAngle) {
            this.circle = circle;
            this.setAngle(centerAngle);
            this.leg = new Line();
            this.text = new Text();
            this.text.setFont(Font.font(20));
            this.text.setStroke(Color.BLACK);
            this.text.setTextAlignment(TextAlignment.CENTER);
            this.text.xProperty().bind(this.centerXProperty().add(25));
            this.text.yProperty().bind(this.centerYProperty().subtract(10));

        }

        double getCenterAngle() {return this.centerAngle;}

        void setPosition() {

            this.setCenterX(circle.getCenterX() + circle.getRadius() * Math.cos(this.getCenterAngle()));

            this.setCenterY(circle.getCenterY() + circle.getRadius() * Math.sin(this.getCenterAngle()));

        }

        void setAngle(double centerAngle) {

            this.centerAngle = centerAngle;

        }

        void setAngle(double x, double y) {

            this.setAngle(Math.atan2(y - circle.getCenterY(), x - circle.getCenterX()));

        }

        void bindLeg(Vertex v1, Vertex v2) {

            leg.startXProperty().bind(v1.centerXProperty());
            leg.startYProperty().bind(v1.centerYProperty());
            leg.endXProperty().bind(v2.centerXProperty());
            leg.endYProperty().bind(v2.centerYProperty());

        }

        double getLegLength() {

            return Math.sqrt(Math.pow(leg.getStartX()-leg.getEndX(),2) + Math.pow(leg.getStartY()-leg.getEndY(),2));

        }

        void setText(double angle) {

            this.text.setText(String.format("%.0f\u00B0", angle));

        }

    }

}