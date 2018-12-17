
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javafx.stage.Stage;

import javafx.event.EventHandler;


public class Main extends Application  {
    public static String device;
    public  static ArrayList L1 = new ArrayList();
    public static String devicefrom;
    public static String URL="https://transferwise.com/gb/currency-converter/";

    public  static String getdevice(String str1,String str2,String int1)throws Exception {
        String URL1=URL.concat(str1.concat("-to-".concat(str2.concat("-rate".concat("?amount=".concat(int1))))));
        Document doc1 = Jsoup.connect(URL1).get();

        Element y = doc1.getElementById("cc-amount-from");
        Element z=doc1.getElementById("cc-amount-to");
        device=z.attr("value").toString();
        return device;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //Label for name
        Text nameLabel = new Text("Amount");

        //Text field for name
        TextField nameText = new TextField();

        //Label for location
        Text locationLabel = new Text("To");

        //Choice box for location
        ComboBox locationchoiceBox = new ComboBox();
        locationchoiceBox.getItems().addAll(L1);
        locationchoiceBox.setEditable(true);
        locationchoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                String devicefrom= locationchoiceBox.getItems().get((Integer) number2).toString();
            }
        });

        //Label for location
        Text locationLabel1 = new Text("From");
        locationLabel1.setX(500);
        locationLabel1.setY(500);


        //Creating a Text object
        Text text = new Text();

        //Setting the text to be added.
        text.setText("________");

        //setting the position of the text
        text.setX(50);
        text.setY(50);

        //Creating a Text object
        Text text1 = new Text();

        //Setting the text to be added.
        text1.setText("developed by Hachem SFAR");

        //Choice box for location
        ComboBox locationchoiceBox1 = new ComboBox();
        locationchoiceBox1.getItems().addAll(L1);
        locationchoiceBox1.setEditable(true);
        locationchoiceBox1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                String devicefrom= locationchoiceBox1.getItems().get((Integer) number2).toString();
            }
        });

        //Label for register
        Button buttonRegister = new Button("Convert");
        buttonRegister.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                try {
                    String str1=locationchoiceBox.getValue().toString();
                    String str2=locationchoiceBox1.getValue().toString();
                    String int1=nameText.getText();
                    if (str1!=str2)
                    {
                        String o = getdevice(str1, str2, int1);
                        text.setText(o);
                    }
                    else
                    {
                        System.out.println(1/0);
                    }

                }
                catch(Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Ooops, there was something wrong!");
                    alert.setContentText("Please check if all fields are correctly filled");

                    alert.showAndWait();                }

            }

        }));

        //Creating a Grid Pane
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(400, 200);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameText, 1, 0);


        //gridPane.add(locationLabel, 0, 0);
        gridPane.add(locationchoiceBox, 1, 1);
        gridPane.add(locationLabel1,0,1);

        //gridPane.add(locationLabel1, 1, 0);
        gridPane.add(locationLabel,0,2);

        gridPane.add(locationchoiceBox1, 1, 2);

        gridPane.add(buttonRegister, 0, 3);
        gridPane.add(text,1,3);
        gridPane.add(text1,1,5);


        //Styling nodes
        buttonRegister.setStyle(
                "-fx-background-color: darkslateblue; -fx-textfill: white;");

        nameLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        locationLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        locationLabel1.setStyle("-fx-font: normal bold 15px 'serif' ");
        text.setStyle("-fx-font: normal bold 15px 'serif' ");

        //Setting the back ground color
        gridPane.setStyle("-fx-background-color: #E8EEFA;");

        //Creating a scene object
        Scene scene = new Scene(gridPane);

        //Setting title to the Stage
        stage.setTitle("Device Converter");

        stage.getIcons().add(new Image("file:coin.png"));

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }

    public static void main(String[] args)throws IOException  {

        Document doc = Jsoup.connect(URL).get();
        Element t = doc.getElementById("targetCurrency");
        Elements e = t.getElementsByTag("option");
        //ArrayList L = new ArrayList();
        for (Element u:e)
        {
            L1.add(u.attr("value"));
        }

        launch(args);

    }


}
