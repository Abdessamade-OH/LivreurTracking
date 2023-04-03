package ma.fstt.livreur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;

import java.sql.SQLException;

public class AddLivreurController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField teleField;

    private Stage dialogeStage;
    private Label feedbackText;

    @FXML
    private void initialize(){
    }

    public void setDialogeStage(Stage dialogeStage){
        this.dialogeStage = dialogeStage;
    }

    @FXML
    protected void onSaveButtonClick() throws SQLException{
            if(isInputValid()) {
                    LivreurDAO ldao = new LivreurDAO();
                    ldao.save(new Livreur(
                            1L,
                            nomField.getText(),
                            teleField.getText()
                    ));
                    //feedbackText.setText("Livreur Ajouté");
                    //updateTable();
                    dialogeStage.close();
            }
    }

    @FXML
    protected void onCancelButtonClick(){
        dialogeStage.close();
    }

    private boolean isInputValid(){
        if(teleField.getText().length() > 0) {
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogeStage);
            alert.setTitle("Valeurs non valides");
            alert.setHeaderText("Erreur, SVP Correcter");
            alert.setContentText("Erreur: Vous devez remplir le champ telephone");

            alert.showAndWait();
            return false;
        }
    }
}
