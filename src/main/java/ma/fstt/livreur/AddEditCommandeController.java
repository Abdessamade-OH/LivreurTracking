package ma.fstt.livreur;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.fstt.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddEditCommandeController {

    @FXML
    private ChoiceBox<Long> livChoice;
    @FXML
    private TextField kmField;
    @FXML
    private TextField clientField;
    @FXML
    private ChoiceBox<String> etatChoice;
    @FXML
    private Label opLabel;
    @FXML
    private Label etatLabel;
    @FXML
    private Label livNomLabel;

    private Stage dialogeStage;
    private Commande commande;

    private String etats[] = {"en cours", "fini", "annulée"};

    @FXML
    private void initialize(){
        etatChoice.getItems().addAll(etats);

        List<Long> myList = new ArrayList<>();
        try {
            LivreurDAO ldao = new LivreurDAO();
            for(Livreur livreur : ldao.getAll()){
                myList.add(livreur.getId_livreur());
            }
            livChoice.getItems().addAll(myList);

            livChoice.setOnAction(event -> {
                System.out.println(livChoice.getValue());
                try {
                    livNomLabel.setText(ldao.getOne(livChoice.getValue()).getNom());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        livChoice.getItems().addAll();


    }

    public void setDialogeStage(Stage dialogeStage){

        this.dialogeStage = dialogeStage;
        opLabel.setText("Ajouter une commande");
        etatChoice.setVisible(false);
        etatLabel.setVisible(false);
    }
    public void setDialogeStage(Stage dialogeStage, Commande commande){
        etatChoice.setValue("choisir état");
        this.dialogeStage = dialogeStage;
        this.commande = commande;

        this.livChoice.setValue(commande.getId_livreur());
        this.kmField.setText(String.valueOf(commande.getKm()));
        this.clientField.setText(commande.getClient());
        this.etatChoice.setValue(commande.getEtat());
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
                            Float.parseFloat(kmField.getText()),
                            clientField.getText(),
                            etatChoice.getValue(),
                            livChoice.getValue()
                    ));
                }else {

                    commande.setKm(Float.parseFloat(kmField.getText()));
                    commande.setClient(clientField.getText());
                    commande.setEtat(etatChoice.getValue());
                    commande.setId_livreur(livChoice.getValue());
                    if(etatChoice.getValue()=="en cours"){
                        commande.setDate_fin(null);
                    }else {
                        java.util.Date date = new java.util.Date();
                        commande.setDate_fin(new Timestamp(date.getTime()));
                    }

                    cdao.update(commande);
                }
                dialogeStage.close();
            }catch(SQLException e){
                this.kmField.setText("");
                this.clientField.setText("");
                this.etatChoice.setValue("choisir etat");
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
        if( livChoice.getValue()==null){
            errorMessage += "Aucun livreur sélectioné\n";
        }else if(livChoice.getItems().size()==0){
            errorMessage += "Aucun livreur au base de données\n";
        }
        if( kmField.getText()==null || kmField.getText().length()==0) {
            errorMessage += "le champ de la distance est vide\n";
        }else {
            try {
                Float.parseFloat(kmField.getText());
            }catch(NumberFormatException e) {
                kmField.setText("");
                errorMessage += "La distance n'est pas valide (Doit être un nombre)";
            }
        }
        if(clientField.getText().equals("") || clientField.getText()==null){
            errorMessage += "le champ client est vide\n";
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
