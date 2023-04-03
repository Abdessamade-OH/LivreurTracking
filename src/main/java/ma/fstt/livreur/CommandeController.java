package ma.fstt.livreur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    @FXML
    private TableView<Commande> cmdTab;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Commande, Long> col_id;
    @FXML
    private TableColumn<Commande, Long> col_livreur;
    @FXML
    private TableColumn<Livreur, String> col_client;
    @FXML
    private TableColumn<Livreur, String> col_etat;

    @FXML
    private Label livLabel;
    @FXML
    private Label etatLebel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label dateFinLabel;
    @FXML
    private Label kmLabel;

    @FXML
    public void onBackButtonClick() {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
        try {
            Scene myScene = new Scene(loader.load(), HelloApplication.getScene().getWidth(), HelloApplication.getScene().getHeight());
            HelloApplication.setScene(myScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Commande, Long>("id_commande"));
        col_livreur.setCellValueFactory(new PropertyValueFactory<Commande, Long>("id_livreur"));
        col_client.setCellValueFactory(new PropertyValueFactory<Livreur, String>("client"));
        col_etat.setCellValueFactory(new PropertyValueFactory<Livreur, String>("etat"));

        cmdTab.setItems(getData());
    }

    public ObservableList<Commande> getData(){
        ObservableList<Commande> myList = FXCollections.observableArrayList();

        try {
            CommandeDAO cdao = new CommandeDAO();
            for(Commande cmd : cdao.getAll()){
                myList.add(cmd);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return myList;
    }

    @Override
    public void initialize(URL connection, ResourceBundle resources){
        //addBox.setVisible(false);
        updateTable();

        //on ajoute un écouteur à la tableview pour pour obtenir l'élément sélectionné
        cmdTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));

        //on désactive les boutons supprimer et editer
        deleteButton.setDisable(true);
        editButton.setDisable(true);

        //pour sélectionner le premier élement par défaut
        cmdTab.getSelectionModel().selectFirst();
    }

    private void showDetails(Commande commande) {
        if (commande != null) {
            //livLabel.setText(commande.getLivreur().getNom());
            livLabel.setText(String.valueOf(commande.getId_livreur()));
            etatLebel.setText(commande.getEtat());
            clientLabel.setText(commande.getClient());
            dateLabel.setText(String.valueOf(commande.getDate_debut()));
            dateFinLabel.setText(String.valueOf(commande.getDate_fin()));
            kmLabel.setText(String.valueOf(commande.getKm()));
            //nous activon les boutons aprés qu'un élément est selectionné
            deleteButton.setDisable(false);
            editButton.setDisable(false);
        } else {
            livLabel.setText("");
            etatLebel.setText("");
            clientLabel.setText("");
            dateLabel.setText("");
            dateFinLabel.setText("");
            kmLabel.setText("");
            //on désactive les boutons supprimer et editer si aucun élément n'est sélectionné
            deleteButton.setDisable(true);
            editButton.setDisable(true);
        }
    }

    @FXML
    public void onAddButtonClick() {
        handleClick(2);
    }

    @FXML
    public void onEditButtonClick() {
        handleClick(1);
    }

    @FXML
    public void onDeleteButtonClick() {

    }

    private void handleClick(int option) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("addEditCommande-view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            if (option == 1) dialogStage.setTitle("Editer Commande");
            else {
                dialogStage.setTitle("Ajouter Commande");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(HelloApplication.getStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddEditCommandeController controller = loader.getController();
            if (option == 1) {
                Commande commande = cmdTab.getSelectionModel().getSelectedItem();
                controller.setDialogeStage(dialogStage, commande);
            } else {
                controller.setDialogeStage(dialogStage);
            }

            dialogStage.showAndWait();
            //updateTable();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(HelloApplication.getStage());
            alert.setTitle("Erreur");
            if (option == 1) {
                alert.setHeaderText("L'élément n'a pas pu être edité");
            } else {
                alert.setHeaderText("L'élément n'a pas pu être ajouté");
            }
            String errMsg = e.toString();
            alert.setContentText(errMsg);

            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

}
