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
            String errorMessage = "";
            if(isInputValid()) {
                try {
                    LivreurDAO ldao = new LivreurDAO();
                    ldao.save(new Livreur(
                            1L,
                            nomField.getText(),
                            teleField.getText()
                    ));
                    //feedbackText.setText("Livreur Ajouté");
                    //updateTable();
                    dialogeStage.close();
                }catch(SQLException e){
                    nomField.setText("");
                    errorMessage += e.toString();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(dialogeStage);
                    alert.setTitle("Erreur BDD");
                    alert.setHeaderText("Erreur au niveau de la base de donneés, SVP Correcter");
                    alert.setContentText(errorMessage);

                    alert.showAndWait();
                }
            }
    }

    @FXML
    protected void onCancelButtonClick(){
        dialogeStage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if( nomField.getText()==null || nomField.getText().length()==0){
            errorMessage += "le champ nom du livreur est vide\n";
        }
        if( teleField.getText()==null || teleField.getText().length()==0) {
            errorMessage += "le champ telephone du livreur est vide\n";
        }else {
            try {
                Float.parseFloat(teleField.getText());
            }catch(NumberFormatException e) {
                teleField.setText("");
                errorMessage += "Le prix n'est pas valide (Doit être un nombre)";
            }
        }
        if(errorMessage.length()==0) {
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogeStage);
            alert.setTitle("Valeurs non valides");
            alert.setHeaderText("Erreur, SVP Correcter");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
}
