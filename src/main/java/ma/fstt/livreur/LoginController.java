package ma.fstt.livreur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ma.fstt.model.CommandeDAO;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    protected void onLoginButtonClick(){
        try{
            CommandeDAO cdao = new CommandeDAO();
            if(cdao.authentifier(username.getText(), password.getText())){
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
                try {
                    Scene myScene = new Scene(loader.load(), 750, 470);
                    HelloApplication.setScene(myScene);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(HelloApplication.getStage());
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Opération n'a pas pu être effectuée");
                    String errMsg = e.toString();
                    alert.setContentText(errMsg);

                    alert.showAndWait();
                    throw new RuntimeException(e);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(HelloApplication.getStage());
                alert.setTitle("accès non autorisé");
                alert.setHeaderText("Erreur Authentification echoué");
                alert.setContentText("Les valeurs saisie ne sont pas correctes");

                alert.showAndWait();
            }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(HelloApplication.getStage());
            alert.setTitle("Erreur");
            alert.setHeaderText("Connexion à la base de données");
            String errMsg = e.toString();
            alert.setContentText(errMsg);

            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
}
