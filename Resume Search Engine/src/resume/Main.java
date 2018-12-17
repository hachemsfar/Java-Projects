package resume;

import java.io.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.xml.sax.SAXException;

import static javafx.application.Application.launch;
import org.apache.tika.Tika;

public class Main extends Application {
    private Desktop desktop = Desktop.getDesktop();
    public static String URL0;
//    public   static List<String> str0 = new ArrayList<String>();

    public  static String sommestr="";
    public   static String O;
    public   static String concats;

    public  static String getcontenu(String url0) throws IOException, TikaException, SAXException{

        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(url0));
        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata,pcontext);

        //getting the content of the document
        String contenu=handler.toString();
        return contenu;

    }

    @Override
    public void start(final Stage stage)  throws IOException, TikaException, SAXException   {
        stage.setTitle("Resume Search Engine");

        final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Open the CV...");
        Text nameLabel3 = new Text("");

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e)  {
                        configureFileChooser(fileChooser);
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            URL0=file.getPath().toString();
                            nameLabel3.setText(file.getName());
                            try{
                                  O=getcontenu(URL0);

                            }
                            catch (Exception p)
                            {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setHeaderText("Ooops, there was something wrong!");
                                alert.setContentText("Please check if all fields are correctly filled");

                                alert.showAndWait();
                            }





                        }
                    }
                });


        Text nameLabel = new Text("Key-words:");

        //Text field for name
        TextField nameText = new TextField();
        Text nameLabel2 = new Text("");
        Button buttonRegister2 = new Button("Add");


        final GridPane inputGridPane = new GridPane();
        inputGridPane.add(nameLabel, 0, 0);
        inputGridPane.add(nameText, 1, 0);
        inputGridPane.add(buttonRegister2, 2, 0);
        buttonRegister2.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                String temp=nameText.getText().toString();
                sommestr=sommestr.concat(temp.concat(","));
                nameLabel2.setText(sommestr);
                nameText.setText("");

            }

        }));
        Button buttonRegister = new Button("Verifiy");
        buttonRegister.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                List<String> str0 = new ArrayList<String>();

                try {
                    String temp1;
                    for (String i : sommestr.split(",")) {
                        if(O.toLowerCase().contains(i.toLowerCase()))
                        {
                            temp1="found";
                        }
                        else
                        {
                            temp1="not found";

                        }
                        String temp2="";
                        temp2=i.concat("=>").concat(temp1).concat("\n");
                        str0.add(temp2);
                    }
                    String temp5=str0.toString();
                    temp5=temp5.substring(1, temp5.length()-1);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Results:");
                    alert.setHeaderText("");
                    alert.setContentText(temp5);

                    alert.showAndWait();
                }
                catch (NullPointerException e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Ooops, there was something wrong!");
                    alert.setContentText("Please check if all fields are correctly filled");

                    alert.showAndWait();
                }
            }

        }));
        inputGridPane.add(nameLabel2, 0, 1);

        GridPane.setConstraints(openButton, 0, 2);
        inputGridPane.add(nameLabel3, 1, 2);
        inputGridPane.add(buttonRegister, 2, 2);

        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));


        stage.setScene(new Scene(rootGroup));
        stage.getIcons().add(new Image("file:Resume.png"));

        stage.show();
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Upload CV");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDFs", "*.pdf*")
        );
    }




    public static void main(final String[] args)  {

        launch(args);



    }
}