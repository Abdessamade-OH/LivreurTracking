package ma.fstt.livreur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;

import java.sql.SQLException;

public class AddEditLivreurController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField teleField;
    @FXML
    private TextField vehiculeField;
    @FXML
    private ChoiceBox<String> teleArea;
    @FXML
    private Label opLabel;
    //private String areas[] = Locale.getISOCountries();
    private String areas[] = {"+212", "+23", "+33", "+56", "+123", "+21", "+213", "+83", "+111","+98","+122","+38"};
    private Stage dialogeStage;

    private Livreur livreur;

    @FXML
    private void initialize(){
        teleArea.getItems().addAll(areas);
        teleArea.setValue("Area");
    }

    public void setDialogeStage(Stage dialogeStage){

        this.dialogeStage = dialogeStage;
        opLabel.setText("Ajouter un livreur");

    }

    public void setDialogeStage(Stage dialogeStage, Livreur livreur){
        this.dialogeStage = dialogeStage;
        this.livreur = livreur;

        this.nomField.setText(livreur.getNom());
        this.teleField.setText(livreur.getTelephone());
        vehiculeField.setText(livreur.getVehicule());
        opLabel.setText("Editer un livreur");
    }

    @FXML
    protected void onSaveButtonClick() throws SQLException{
            String errorMessage = "";
            if(isInputValid()) {
                String telephone = teleArea.getValue()+teleField.getText();
                try {
                    LivreurDAO ldao = new LivreurDAO();
                    if(livreur==null) {
                        ldao.save(new Livreur(
                                1L,
                                nomField.getText(),
                                telephone,
                                vehiculeField.getText()
                        ));
                    }else{
                        livreur.setNom(nomField.getText());
                        livreur.setTelephone(telephone);
                        livreur.setVehicule(vehiculeField.getText());
                        ldao.update(livreur);
                    }
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
        if( vehiculeField.getText()==null || vehiculeField.getText().length()==0){
            errorMessage += "le champ nom du vehicule est vide\n";
        }
        if( teleField.getText()==null || teleField.getText().length()!=9) {
            errorMessage += "le champ telephone du livreur est vide ou pas le bon nombre des chiffres\n";
        }else {
            try {
                Float.parseFloat(teleField.getText());
            }catch(NumberFormatException e) {
                teleField.setText("");
                errorMessage += "Le prix n'est pas valide (Doit être un nombre)";
            }
        }
        if( teleArea.getValue().equals("Area") ){
            errorMessage += "Le code telephonique du payé";
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
