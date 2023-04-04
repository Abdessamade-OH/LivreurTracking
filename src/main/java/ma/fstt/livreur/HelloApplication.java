package ma.fstt.livreur;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage myStage;
    @Override
    public void start(Stage stage) throws IOException {

        myStage = stage;
        /*Button btn1 = new Button("Hello!");
        Text text1 = new Text();
        text1.setText("Hello World!");
        text1.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 30));
        text1.setX(300);
        text1.setY(100);
        text1.setStroke(Color.BLACK);
        text1.setFill(Color.BLUE);
        text1.setStrikethrough(false);
        Label label1 = new Label("Name");
        TextField tfield = new TextField();



        FlowPane root = new FlowPane();
        root.getChildren().addAll(text1, label1, tfield, btn1);
        root.setHgap(20);
        root.setVgap(90);
        root.setPrefWrapLength(250);
        root.setColumnHalignment(Pos.BOTTOM_RIGHT.getHpos());

        btn1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                System.out.println("Hello world");
                text1.setText("Yaay");
                text1.setUnderline(true);
                root.setAlignment(Pos.BOTTOM_RIGHT);

            }
        });*/

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 470);
        stage.setTitle("LivreurTracking");
        stage.setScene(scene);

        stage.show();

    }
    public static void setScene(Scene scene) {
        myStage.setScene(scene);
    }

    public static Scene getScene(){ return myStage.getScene(); }

    public static Stage getStage() {
        return myStage;
    }

    public static void main(String[] args) {
        launch();
    }
}