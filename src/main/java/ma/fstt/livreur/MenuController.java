package ma.fstt.livreur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuController {

    @FXML
    private Label errorLabel;
    @FXML
    protected void onLivButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("livreur-view.fxml"));
        try {
            Scene myScene = new Scene(loader.load(), HelloApplication.getScene().getWidth(), HelloApplication.getScene().getHeight());
            HelloApplication.setScene(myScene);
        } catch (IOException e) {

            errorLabel.setText("SQL Error occured.");
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onCmdButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("commande-view.fxml"));
        try {
            Scene myScene = new Scene(loader.load(), HelloApplication.getScene().getWidth(), HelloApplication.getScene().getHeight());
            HelloApplication.setScene(myScene);
        } catch (IOException e) {

            errorLabel.setText("SQL Error occured.");
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onProdButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("produit-view.fxml"));
        try {
            Scene myScene = new Scene(loader.load(), HelloApplication.getScene().getWidth(), HelloApplication.getScene().getHeight());
            HelloApplication.setScene(myScene);
        } catch (IOException e) {

            errorLabel.setText("SQL Error occured.");
            throw new RuntimeException(e);
        }
    }


}
