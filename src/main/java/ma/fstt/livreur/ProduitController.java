package ma.fstt.livreur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;
import ma.fstt.model.Produit;
import ma.fstt.model.ProduitDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProduitController implements Initializable{

    @FXML
    private TableColumn<Produit, Long> col_id;
    @FXML
    private TableColumn<Produit, Float> col_prix;
    @FXML
    private TableColumn<Produit, String> col_nom;
    @FXML
    private TableView<Produit> prodTab;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prixLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Button deleteButton;
    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    protected void onBackButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
        try {
            Scene myScene = new Scene(loader.load(), HelloApplication.getScene().getWidth(), HelloApplication.getScene().getHeight());
            HelloApplication.setScene(myScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // save button
    /*@FXML
    protected void onSaveButtonClick(){
        try{
            ProduitDAO pdao = new ProduitDAO();

            pdao.save(new Produit(
                    0,
                    Float.parseFloat(prix.getText()),
                    nom.getText(),
                    description.getText()
            ));
            feedbackText.setText("Produit ajouté");
            nom.setText("");
            description.setText("");
            prix.setText("");
            updateTable();
        }catch(SQLException e){
            feedbackText.setText("SQL Erreur, make sure the name is unique");
            throw new RuntimeException(e);
        }catch(NumberFormatException e){
            feedbackText.setText("Erreur: Le prix doit etre un nombre!");
        }
    }*/

    public void updateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Produit, Long>("id_produit"));
        col_prix.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));

        prodTab.setItems(getData());
    }
    public ObservableList<Produit> getData(){
        ObservableList<Produit> myList = FXCollections.observableArrayList();

        try {
            ProduitDAO pdao = new ProduitDAO();
            for(Produit p : pdao.getAll()){
                myList.add(p);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return myList;
    }

    @Override
    public void initialize(URL connection, ResourceBundle resources){
        updateTable();

        //on ajoute un écouteur à la tableview pour pour obtenir l'élément sélectionné
        prodTab.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showDetails(newValue));

        //on désactive le bouton supprimer
        deleteButton.setDisable(true);

        //pour sélectionner le premier élement par défaut
        prodTab.getSelectionModel().selectFirst();
    }

    private void showDetails(Produit produit) {
        if (produit != null) {
            nomLabel.setText(produit.getNom());
            prixLabel.setText(Float.toString(produit.getPrix()));
            descLabel.setText(produit.getDescription());
            deleteButton.setDisable(false);

        } else {
            nomLabel.setText("");
            prixLabel.setText("");
            descLabel.setText("");
            deleteButton.setDisable(true);
        }
    }

    @FXML
    protected void onAddButtonClick(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("addProduit-view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter Produit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(HelloApplication.getStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddProduitController controller = loader.getController();
            controller.setDialogeStage(dialogStage);

            dialogStage.showAndWait();
            updateTable();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(HelloApplication.getStage());
            alert.setTitle("Erreur");
            alert.setHeaderText("L'élément n'a pas pu être ajouté");
            String errMsg = e.toString();
            alert.setContentText(errMsg);

            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onDeleteButtonClick(){
        Produit produit = prodTab.getSelectionModel().getSelectedItem();
        if(produit!=null) {
            //On récupère le stage courant
            Stage stage = (Stage) myAnchorPane.getScene().getWindow();
            //On fixe le type de la fenêtre de dialogue
            Alert.AlertType type = Alert.AlertType.CONFIRMATION;
            //nous instancions un nouveau dialogue d'alerte
            Alert alert = new Alert(type, "");

            //pour s'assurer que la fenêtre parente est désactivée jusqu'à ce que la confirmation soit faite
            alert.initModality(Modality.APPLICATION_MODAL);
            //nous attribuons la boîte de dialogue de confirmation à notre fenêtre actuelle
            alert.initOwner(stage);
            //nous définissons le texte du corps de la boîte de dialogue de confirmation
            String confirmationString = "Êtes-vous sûr de vouloir supprimer le produit n°" + produit.getId_produit();
            alert.getDialogPane().setContentText(confirmationString);
            //nous définissons le texte d'entête de la boîte de dialogue de confirmation
            alert.getDialogPane().setHeaderText("Supprimer produit");

            //nous définissons la fenêtre de dialogue à afficher et attendons un résultat
            Optional<ButtonType> result = alert.showAndWait();
            //on supprime l'élément une fois le bouton "OK" sélectionné
            if(result.get() == ButtonType.OK) {
                try {
                    ProduitDAO pdao = new ProduitDAO();
                    pdao.delete(produit);
                    updateTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else if(result.get() == ButtonType.CANCEL){
                System.out.println("cancled");
            }
        }
    }

}
