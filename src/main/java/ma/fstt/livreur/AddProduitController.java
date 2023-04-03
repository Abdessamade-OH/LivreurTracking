package ma.fstt.livreur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;
import ma.fstt.model.Produit;
import ma.fstt.model.ProduitDAO;

import java.sql.SQLException;

public class AddProduitController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prixField;
    @FXML
    private TextArea descArea;

    private Stage dialogeStage;

    @FXML
    private void initialize(){
    }

    public void setDialogeStage(Stage dialogeStage){
        this.dialogeStage = dialogeStage;
    }

    @FXML
    protected void onSaveButtonClick(){
        if(isInputValid()) {
            String errorMessage = "";
            try {
                ProduitDAO pdao = new ProduitDAO();
                pdao.save(new Produit(
                        0L,
                        Float.parseFloat(prixField.getText()),
                        nomField.getText(),
                        descArea.getText()
                ));
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
            //feedbackText.setText("Livreur Ajouté");
            //updateTable();
        }
    }

    @FXML
    protected void onCancelButtonClick(){
        dialogeStage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if( nomField.getText()==null || nomField.getText().length()==0){
            errorMessage += "le nom du produit est vide\n";
        }
        if( prixField.getText()==null || prixField.getText().length()==0) {
            errorMessage += "le prix du produit est vide\n";
        }else {
            try {
                Float.parseFloat(prixField.getText());
            }catch(NumberFormatException e) {
                prixField.setText("");
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
