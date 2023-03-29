package ma.fstt.livreur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LivreurController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField telephone;
    @FXML
    private Label feedbackText;
    @FXML
    private TableView<Livreur> livreurTab;
    @FXML
    private TableColumn<Livreur, Long> col_id;
    @FXML
    private TableColumn<Livreur, String> col_nom;
    @FXML
    private TableColumn<Livreur, String> col_telephone;
    @FXML
    private VBox addBox;
    @FXML
    private Button addButton;


    @FXML
    protected void onSaveButtonClick() {

        if(telephone.getText().length() > 0 ) {
            try {
                LivreurDAO ldao = new LivreurDAO();
                ldao.save(new Livreur(
                        1L,
                        nom.getText(),
                        telephone.getText()
                ));
                feedbackText.setText("Livreur Ajouté");
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

    }

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
        addBox.setVisible(true);
        addButton.setDisable(true);
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
        addBox.setVisible(false);
        updateTable();
    }
}