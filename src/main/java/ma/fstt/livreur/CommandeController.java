package ma.fstt.livreur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.fstt.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    @FXML
    private TableView<Commande> cmdTab;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button emptyButton;

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
    private Label prixTotalLabel;
    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private TableView<ProduitCommande> prodTab;
    @FXML
    private TableColumn<ProduitCommande, Long> id_col;
    @FXML
    private TableColumn<ProduitCommande, Integer> quantite_col;
    @FXML
    private TableColumn<ProduitCommande, String> nom_col;
    @FXML
    private TableColumn<ProduitCommande, Float> prix_col;

    public void updateProdTable(){
            id_col.setCellValueFactory(new PropertyValueFactory<ProduitCommande, Long>("id_produit"));
            nom_col.setCellValueFactory(new PropertyValueFactory<ProduitCommande, String>("nom"));
            prix_col.setCellValueFactory(new PropertyValueFactory<ProduitCommande, Float>("prix"));
            quantite_col.setCellValueFactory(new PropertyValueFactory<ProduitCommande, Integer>("quantite"));

            prodTab.setItems(getProdData());
    }

    public ObservableList<ProduitCommande> getProdData(){
        ObservableList<ProduitCommande> myList = FXCollections.observableArrayList();
        Commande commande = cmdTab.getSelectionModel().getSelectedItem();
        if(commande!=null) {
            try {
                CommandeDAO cdao = new CommandeDAO();
                for (ProduitCommande pc : cdao.getAllProduits(commande.getId_commande())) {
                    myList.add(pc);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return myList;
    }
    @FXML
    protected void onBackButtonClick() {
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
        updateProdTable();
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

            if(!commande.getEtat().equals("en cours")){
                ajouterButton.setDisable(true);
            }else{
                ajouterButton.setDisable(false);
            }
            updateProdTable();
            try{
                CommandeDAO cdao = new CommandeDAO();
                emptyButton.setDisable(cdao.getAllProduits(commande.getId_commande()).size() == 0);
                livLabel.setText(commande.getLivreur().getNom());
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
            etatLebel.setText(commande.getEtat());
            prixTotalLabel.setText(String.valueOf(commande.getPrix_total()));
            clientLabel.setText(commande.getClient());
            dateLabel.setText(String.valueOf(commande.getDate_debut()));
            kmLabel.setText(String.valueOf(commande.getKm()));
            if(commande.getEtat().equals("en cours")) {
                dateFinLabel.setText("Pas encour fini");
            }else{
                dateFinLabel.setText(String.valueOf(commande.getDate_fin()));
            }
            //nous activon les boutons aprés qu'un élément est selectionné
            deleteButton.setDisable(false);
            editButton.setDisable(false);
        } else {
            livLabel.setText("");
            etatLebel.setText("");
            prixTotalLabel.setText("");
            clientLabel.setText("");
            dateLabel.setText("");
            dateFinLabel.setText("");
            kmLabel.setText("");
            //on désactive les boutons supprimer et editer si aucun élément n'est sélectionné
            deleteButton.setDisable(true);
            editButton.setDisable(true);
            ajouterButton.setDisable(true);
            emptyButton.setDisable(true);
        }
    }

    @FXML
    protected void onAddButtonClick() {
        handleClick(2);
    }

    @FXML
    protected void onEditButtonClick() {
        handleClick(1);
    }

    @FXML
    protected void onDeleteButtonClick() {
        Commande commande = cmdTab.getSelectionModel().getSelectedItem();
        if(commande!=null) {
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
            String confirmationString = "Êtes-vous sûr de vouloir supprimer la commande n°" + commande.getId_commande();
            alert.getDialogPane().setContentText(confirmationString);
            //nous définissons le texte d'entête de la boîte de dialogue de confirmation
            alert.getDialogPane().setHeaderText("Supprimer commande");

            //nous définissons la fenêtre de dialogue à afficher et attendons un résultat
            Optional<ButtonType> result = alert.showAndWait();
            //on supprime l'élément une fois le bouton "OK" sélectionné
            if(result.get() == ButtonType.OK) {
                try {
                    CommandeDAO cdao = new CommandeDAO();
                    cdao.delete(commande);
                    updateTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else if(result.get() == ButtonType.CANCEL){
                System.out.println("cancled");
            }
            updateProdTable();
        }
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
            updateTable();
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

    @FXML
    protected void onAjouterButtonClick(){
        Commande commande = cmdTab.getSelectionModel().getSelectedItem();
        if(commande!=null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(HelloApplication.class.getResource("ajouterProduitCommande-view.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Ajouter produit à la commande");

                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(HelloApplication.getStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                AjouterProduitCommandeController controller = loader.getController();
                controller.setDialogeStage(dialogStage, commande);

                dialogStage.showAndWait();
                updateProdTable();
                updateTable();
                cmdTab.getSelectionModel().select(commande);
            } catch (Exception e) {
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
    }

    @FXML
    protected void onEmptyButtonClick(){
        Commande commande = cmdTab.getSelectionModel().getSelectedItem();
        if(commande!=null) {
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
            String confirmationString = "Êtes-vous sûr de vouloir vider la commande n°" + commande.getId_commande();
            alert.getDialogPane().setContentText(confirmationString);
            //nous définissons le texte d'entête de la boîte de dialogue de confirmation
            alert.getDialogPane().setHeaderText("vider commande");

            //nous définissons la fenêtre de dialogue à afficher et attendons un résultat
            Optional<ButtonType> result = alert.showAndWait();
            //on supprime l'élément une fois le bouton "OK" sélectionné
            if(result.get() == ButtonType.OK) {
                try {
                    commande.emptyProduits();
                    commande.updatePrix();
                    updateProdTable();
                    updateTable();
                    cmdTab.getSelectionModel().select(commande);
                }catch(SQLException e){
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.initOwner(HelloApplication.getStage());
                    alert2.setTitle("Erreur");
                    alert2.setHeaderText("Erreur au niveau de BDD");

                    String errMsg = e.toString();
                    alert2.setContentText(errMsg);

                    alert2.showAndWait();
                    throw new RuntimeException(e);
                }
            }else if(result.get() == ButtonType.CANCEL){
                System.out.println("cancled");
            }

        }

    }
}
