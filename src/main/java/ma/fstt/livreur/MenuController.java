package ma.fstt.livreur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ma.fstt.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private NumberAxis y2;
    @FXML
    private CategoryAxis x2;
    @FXML
    private LineChart<?, ?> lineChart2;
    @FXML
    private NumberAxis y3;
    @FXML
    private CategoryAxis x3;
    @FXML
    private BarChart<?, ?> barChart2;
    @FXML
    private NumberAxis y5;
    @FXML
    private CategoryAxis x5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series seriesCmdLiv = new XYChart.Series();
        XYChart.Series seriesLivVit = new XYChart.Series();
        XYChart.Series seriesLivDistance = new XYChart.Series();
        XYChart.Series seriesProdCmd = new XYChart.Series();
        XYChart.Series seriesProdQnt = new XYChart.Series();

        List<Livreur> livreurList;
        List<Produit> produitList;
        try{
            LivreurDAO ldao = new LivreurDAO();
            livreurList = ldao.getAll();
            ProduitDAO pdao = new ProduitDAO();
            produitList = pdao.getAll();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        String xVal;
        String xProdVal;
        try {
            System.out.println("One");
            for (Produit produit : produitList) {
                xProdVal = produit.getId_produit() + "\n" + produit.getNom();
                System.out.println(produit.getCommandedProduits());
                System.out.println(produit.getQauntiteMoy());
                seriesProdCmd.getData().add(new XYChart.Data(xProdVal, produit.getCommandedProduits()));
                seriesProdQnt.getData().add(new XYChart.Data(xProdVal, produit.getQauntiteMoy()));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        seriesProdCmd.setName("Nombre Commandes");
        seriesProdQnt.setName("Quantité moyenne");
        barChart2.getData().addAll(seriesProdCmd);
        barChart2.getData().addAll(seriesProdQnt);
        for(Livreur livreur : livreurList){
            float vitesse = 0;
            float distance = 0;
            List<Commande> commandList = new ArrayList<>();
            try {
                CommandeDAO cdao = new CommandeDAO();

                for (Commande cmd : cdao.getAllById(livreur.getId_livreur())) {
                    if(cmd.getEtat().equals("fini")) {
                        //System.out.println(cmd.getDate_fin().getTime() - cmd.getDate_debut().getTime());
                        double time = (cmd.getDate_fin().getTime() - cmd.getDate_debut().getTime()) / 1000.0 / 3600.0;
                        vitesse += cmd.getKm() / time;
                    }
                    distance += cmd.getKm();
                    commandList.add(cmd);
                }
                if(vitesse!=0) {
                    vitesse /= commandList.size();
                }
                if(distance != 0){
                    distance /= commandList.size();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            xVal = livreur.getId_livreur() + "\n" + livreur.getNom();
            seriesCmdLiv.getData().add(new XYChart.Data(xVal, commandList.size()));
            seriesLivVit.getData().add(new XYChart.Data(xVal, vitesse));
            seriesLivDistance.getData().add(new XYChart.Data(xVal, distance));
        }

        barChart.getData().addAll(seriesCmdLiv);
        lineChart.getData().addAll(seriesLivVit);
        lineChart2.getData().addAll(seriesLivDistance);
    }

    @FXML
    protected void onLivButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("livreur-view.fxml"));
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
    @FXML
    protected void onCmdButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("commande-view.fxml"));
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

    @FXML
    protected void onProdButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("produit-view.fxml"));
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
