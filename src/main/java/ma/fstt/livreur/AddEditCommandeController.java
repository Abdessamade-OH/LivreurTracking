package ma.fstt.livreur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;
import ma.fstt.model.Produit;
import ma.fstt.model.ProduitDAO;

import java.sql.SQLException;

public class AddEditCommandeController {

    @FXML
    private TextField livField;
    @FXML
    private TextField kmField;
    @FXML
    private TextField clientField;
    @FXML
    private TextField etatField;
    @FXML
    private Label opLabel;

    private Stage dialogeStage;
    private Commande commande;

    @FXML
    private void initialize(){
    }

    public void setDialogeStage(Stage dialogeStage){

        this.dialogeStage = dialogeStage;
        opLabel.setText("Ajouter une commande");
    }
    public void setDialogeStage(Stage dialogeStage, Commande commande){
        this.dialogeStage = dialogeStage;
        this.commande = commande;

        this.livField.setText(String.valueOf(commande.getId_livreur()));
        this.kmField.setText(String.valueOf(commande.getKm()));
        this.clientField.setText(commande.getClient());
        this.etatField.setText(commande.getEtat());
        opLabel.setText("Editer une commande");
    }

    @FXML
    protected void onSaveButtonClick(){
        if(isInputValid()) {
            String errorMessage = "";
            try {
                CommandeDAO cdao = new CommandeDAO();
                if(commande == null) {
                    cdao.save(new Commande(
                            0L,
                            Float.parseFloat(kmField.getText()),
                            clientField.getText(),
                            etatField.getText(),
                            Long.parseLong(livField.getText())
                    ));
                }else {

                    commande.setKm(Float.parseFloat(kmField.getText()));
                    commande.setClient(clientField.getText());
                    commande.setEtat(etatField.getText());
                    commande.setId_livreur(Long.parseLong(livField.getText()));

                    cdao.update(commande);
                }
                dialogeStage.close();
            }catch(SQLException e){
                this.livField.setText("");
                this.kmField.setText("");
                this.clientField.setText("");
                this.etatField.setText("");
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
        if( livField.getText()==null || livField.getText().length()==0){
            errorMessage += "le livreur est vide\n";
        }
        if( kmField.getText()==null || kmField.getText().length()==0) {
            errorMessage += "la distance est vide\n";
        }else {
            try {
                Float.parseFloat(kmField.getText());
            }catch(NumberFormatException e) {
                kmField.setText("");
                errorMessage += "La distance n'est pas valide (Doit être un nombre)";
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
