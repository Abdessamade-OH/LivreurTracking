package ma.fstt.livreur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import ma.fstt.model.Produit;
import ma.fstt.model.ProduitCommande;
import ma.fstt.model.ProduitDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitBilanController implements Initializable {
    @FXML
    private BarChart<?, ?> barChart2;
    @FXML
    private NumberAxis y5;
    @FXML
    private CategoryAxis x5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series seriesProdCmd = new XYChart.Series();
        XYChart.Series seriesProdQnt = new XYChart.Series();

        List<Produit> produitList;
        String xProdVal;
        try{
            ProduitDAO pdao = new ProduitDAO();
            produitList = pdao.getAll();
            System.out.println("One");
            for (Produit produit : produitList) {
                float quantiteTotal = 0;
                xProdVal = produit.getId_produit() + "\n" + produit.getNom();
                List<ProduitCommande> myList = pdao.getProduitsCommandes(produit.getId_produit());
                for (ProduitCommande produitCommande : myList) {
                    System.out.println("produit n°" + " " + produitCommande.getId_produit());
                    System.out.println("quantite " + produitCommande.getQuantite());
                    quantiteTotal += produitCommande.getQuantite();
                }
                if(quantiteTotal!=0) {
                    quantiteTotal /= myList.size();
                }
                float quantiteMoyenne = pdao.getProduitsCommandes(produit.getId_produit()).size();
                //System.out.println(produit.getQauntiteMoy());
                seriesProdCmd.getData().add(new XYChart.Data(xProdVal, quantiteTotal));
                seriesProdQnt.getData().add(new XYChart.Data(xProdVal, quantiteMoyenne));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        seriesProdCmd.setName("Nombre Commandes");
        seriesProdQnt.setName("Quantité moyenne");
        barChart2.getData().addAll(seriesProdCmd);
        barChart2.getData().addAll(seriesProdQnt);
    }

    @FXML
    protected void onBackButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
        try {
            Scene myScene = new Scene(loader.load(), HelloApplication.getScene().getWidth(), HelloApplication.getScene().getHeight());
            HelloApplication.setScene(myScene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(HelloApplication.getStage());
            alert.setTitle("Erreur");
            alert.setHeaderText("Opération n'a pas pu être effectuée");
            String errMsg = e.toString();
            alert.setContentText(errMsg);

            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
}
