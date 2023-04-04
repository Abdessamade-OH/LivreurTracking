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
import java.sql.Connection;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn1 = null;
        Connection conn2 = null;
        Connection conn3 = null;
        XYChart.Series seriesCmdLiv = new XYChart.Series();
        XYChart.Series seriesLivVit = new XYChart.Series();
        XYChart.Series seriesLivDistance = new XYChart.Series();

        List<Livreur> livreurList;
        try{
            LivreurDAO ldao = new LivreurDAO();
            conn1 = ldao.getConnection();
            livreurList = ldao.getAll();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally{
            if(conn1!=null){
                try {
                    conn1.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        String xVal;
        /**/
        for(Livreur livreur : livreurList){
            float vitesse = 0;
            float distance = 0;
            List<Commande> commandList = new ArrayList<>();
            try {
                CommandeDAO cdao = new CommandeDAO();
                conn3 = cdao.getConnection();
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
            }finally{
                if(conn3!=null){
                    try {
                        conn3.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
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

    @FXML
    protected void onBilanButtonClick(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("produitBilan-view.fxml"));
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
