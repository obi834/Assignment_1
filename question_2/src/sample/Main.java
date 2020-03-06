
//Obaida Issa 100702054
//CSCI2020U



package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        LoanInvestCalculator calcPane = new LoanInvestCalculator();

        primaryStage.setScene(new Scene(calcPane, calcPane.getPrefWidth(), calcPane.getPrefHeight()));
        primaryStage.setTitle("Question 2");//Title of the calculator
        primaryStage.show();
    }

    public static void main(String[] args) {

        Application.launch(args);//launch the calculator
    }


    private class LoanInvestCalculator extends GridPane {

        Label amountLabel = new Label("Investment Amount:");//first label
        TextField amountText = new TextField();

        Label yearsLabel = new Label("Years:");//second label
        TextField yearsText = new TextField();

        Label rateLabel = new Label("Annual Interest Rate:");//third label
        TextField rateText = new TextField();

        Label futureValueLabel = new Label("Future Value:");//fourth label
        TextField futureValueText = new TextField();

        Button btCalc = new Button("Calculate");//Calculate button

        private LoanInvestCalculator() {


            setPadding(new Insets(10, 10, 10, 10));
            setAlignment(Pos.CENTER);
            setHgap(10);
            setVgap(10);
                //columns and rows declaration
            add(amountLabel, 0, 0);
            add(amountText, 1, 0);

            add(yearsLabel, 0, 1);
            add(yearsText, 1, 1);

            add(rateLabel, 0, 2);
            add(rateText, 1, 2);

            add(futureValueLabel, 0, 3);
            add(futureValueText, 1, 3);

            HBox buttons = new HBox();
            buttons.getChildren().add(btCalc);
            buttons.setAlignment(Pos.BOTTOM_RIGHT);//position of the button
            add(buttons, 1, 4);
            btCalc.setOnAction(e-> calcFutureValue());

// Editing TextField settings
            TextField[] textFields = (TextField[])getArray(
                    amountText, yearsText, rateText, futureValueText);//array of textfields

            for (TextField tf : textFields) {
                tf.setAlignment(Pos.BASELINE_RIGHT);
            }
            futureValueText.setDisable(true);

        }

        private Object[] getArray(Object... objects) {
            Object[] temp = new TextField[objects.length];
            for (int i = 0; i < objects.length; i++) {
                temp[i] = objects[i];
            }
            return temp;
        }

        public void calcFutureValue() {
            double investmentAmount = Double.parseDouble(amountText.getText());
            double years = Double.parseDouble(yearsText.getText());
            double monthInterestRate = Double.parseDouble(rateText.getText()) / 12 / 100;

            double futureValue = investmentAmount * Math.pow(1 + monthInterestRate, years * 12);
            futureValueText.setText(String.format("$%.2f", futureValue));//set text to calculated value
        }
    }
}