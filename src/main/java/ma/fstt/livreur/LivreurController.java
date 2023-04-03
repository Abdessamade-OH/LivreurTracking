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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LivreurController implements Initializable {

    /*@FXML
    private TextField nom;
    @FXML
    private TextField telephone;
    @FXML
    private Label feedbackText;*/
    @FXML
    private TableView<Livreur> livreurTab;
    @FXML
    private TableColumn<Livreur, Long> col_id;
    @FXML
    private TableColumn<Livreur, String> col_nom;
    @FXML
    private TableColumn<Livreur, String> col_telephone;
    /*@FXML
    private VBox addBox;
    @FXML
    private Button addButton;*/
    @FXML
    private Label nomLabel;
    @FXML
    private Label teleLabel;
    @FXML
    private Button deleteButton;
    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private Button editButton;


    //on save button click
    /*@FXML
    protected void onSaveButtonClick() {

        if(telephone.getText().length() > 0 ) {
            try {
                LivreurDAO ldao = new LivreurDAO();
                ldao.save(new Livreur(
                        1L,
                        nom.getText(),
                        telephone.getText()
                ));
                feedbackText.setText("Livreur A jouté");
                nom.setText("");
                telephone.setText("");
                updateTable();
            } catch (SQLException e) {
                feedbackText.setText("SQL Error!");
                throw new RuntimeException(e);
            }
            addBox.setVisible(false);
            addButton.setDisable(false);
        }else{
            feedbackText.setText("Erreur: Vous devez remplir le champ telephone");
        }

    }*/

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
    @FXML
    protected void onAddButtonClick(){
        //addBox.setVisible(true);
        //addButton.setDisable(true);
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("addLivreur-view.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter Livreur");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(HelloApplication.getStage());

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddLivreurController controller = loader.getController();
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

    public void updateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Livreur, Long>("id_livreur"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Livreur, String>("nom"));
        col_telephone.setCellValueFactory(new PropertyValueFactory<Livreur, String>("telephone"));

        livreurTab.setItems(getData());
    }

    public ObservableList<Livreur> getData(){
        ObservableList<Livreur> myList = FXCollections.observableArrayList();

        try {
            LivreurDAO ldao = new LivreurDAO();
            for(Livreur livreur : ldao.getAll()){
                myList.add(livreur);
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
        livreurTab.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));

        //on désactive les boutons supprimer et editer
        deleteButton.setDisable(true);
        editButton.setDisable(true);

        //pour sélectionner le premier élement par défaut
        livreurTab.getSelectionModel().selectFirst();
    }

    private void showDetails(Livreur livreur) {
        if (livreur != null) {
            nomLabel.setText(livreur.getNom());
            teleLabel.setText(livreur.getTelephone());
            //nous activon les boutons aprés qu'un élément est selectionné
            deleteButton.setDisable(false);
            editButton.setDisable(false);
        } else {
            nomLabel.setText("");
            teleLabel.setText("");
            //on désactive les boutons supprimer et editer si aucun élément n'est sélectionné
            deleteButton.setDisable(true);
            editButton.setDisable(true);
        }
    }

    @FXML
    protected void onDeleteButtonClick(){
        Livreur livreur = livreurTab.getSelectionModel().getSelectedItem();
        if(livreur!=null) {
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
            String confirmationString = "Êtes-vous sûr de vouloir supprimer le livreur n°" + livreur.getId_livreur();
            alert.getDialogPane().setContentText(confirmationString);
            //nous définissons le texte d'entête de la boîte de dialogue de confirmation
            alert.getDialogPane().setHeaderText("Supprimer livreur");

            //nous définissons la fenêtre de dialogue à afficher et attendons un résultat
            Optional<ButtonType> result = alert.showAndWait();
            //on supprime l'élément une fois le bouton "OK" sélectionné
            if(result.get() == ButtonType.OK) {
                try {
                    LivreurDAO ldao = new LivreurDAO();
                    ldao.delete(livreur);
                    updateTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else if(result.get() == ButtonType.CANCEL){
                System.out.println("cancled");
            }
        }
    }

    @FXML
    protected void onEditButtonClick(){
        Livreur livreur = livreurTab.getSelectionModel().getSelectedItem();
        if(livreur!=null){
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(HelloApplication.class.getResource("addLivreur-view.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Editer Livreur");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(HelloApplication.getStage());

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                AddLivreurController controller = loader.getController();
                controller.setDialogeStage(dialogStage, livreur);

                dialogStage.showAndWait();
                updateTable();
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(HelloApplication.getStage());
                alert.setTitle("Erreur");
                alert.setHeaderText("L'élément n'a pas pu être edité");
                String errMsg = e.toString();
                alert.setContentText(errMsg);

                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }

    }
}