package ma.fstt.livreur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.fstt.model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjouterProduitCommandeController {
    @FXML
    private TextField quantiteField;
    @FXML
    private ChoiceBox<Long> prodChoice;
    @FXML
    private Label prodLabel;
    private Stage dialogeStage;
    private Commande commande;

    @FXML
    private void initialize(){

        List<Long> myList = new ArrayList<>();
        try {
            ProduitDAO pdao = new ProduitDAO();
            for(Produit produit : pdao.getAll()){
                myList.add(produit.getId_produit());
            }
            prodChoice.getItems().addAll(myList);

            prodChoice.setOnAction(event -> {
                System.out.println(prodChoice.getValue());
                try {
                    prodLabel.setText(pdao.getOne(prodChoice.getValue()).getNom());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch(SQLException e){
            throw new RuntimeException(e);
        }


        prodChoice.getItems().addAll();
    }
    public void setDialogeStage(Stage dialogStage, Commande commande) {
        this.dialogeStage = dialogStage;
        this.commande = commande;
    }
    @FXML
    public void onSaveButtonClick(){
        if(isInputValid()) {
            try {
                commande.addProduct( prodChoice.getValue(), Integer.parseInt(quantiteField.getText() ) );
                dialogeStage.close();
            } catch (SQLException e) {
                this.quantiteField.setText("");
                String errorMessage = e.toString();
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
    public void onCancelButtonClick(){
        this.dialogeStage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if( prodChoice.getValue()==null){
            errorMessage += "Aucun produit sélectioné\n";
        }else if(prodChoice.getItems().size()==0){
            errorMessage += "Aucun produit au base de données\n";
        }
        if( quantiteField.getText()==null || quantiteField.getText().length()==0) {
            errorMessage += "la quantite est vide\n";
        }else {
            try {
                Integer.parseInt(quantiteField.getText());
            }catch(NumberFormatException e) {
                quantiteField.setText("");
                errorMessage += "La quantite n'est pas valide (Doit être un nombre)";
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
