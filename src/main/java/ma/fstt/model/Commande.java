package ma.fstt.model;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Commande {
    private long id_commande;
    private float km;
    private String client;
    private String etat;
    private float prix_total = 0;

    private long id_livreur;

    private Timestamp date_debut;
    private Timestamp date_fin;

    public Commande(long id_commande, float km, String client, String etat, float prix_total, Timestamp date_debut, Timestamp date_fin, long id_livreur) {
        this.id_commande = id_commande;
        this.km = km;
        this.client = client;
        this.etat = etat;
        this.prix_total = prix_total;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_livreur = id_livreur;
    }

    public Commande(float km, String client, String etat, long id_livreur) {
        this.km = km;
        this.client = client;
        this.etat = etat;
        this.id_livreur = id_livreur;
    }

    public float getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(float prix_total) {
        this.prix_total = prix_total;
    }

    public void updatePrix(Long id_produit, int quantite) throws SQLException{
        ProduitDAO pdao = new ProduitDAO();
        float prix = pdao.getOne(id_produit).getPrix();
        prix_total += prix*quantite;
        
        CommandeDAO cdao = new CommandeDAO();
        cdao.update(this);
    }
    public void updatePrix() throws SQLException{
        this.setPrix_total(0);
        CommandeDAO cdao = new CommandeDAO();
        cdao.update(this);
    }
    public long getId_commande() {
        return id_commande;
    }

    public Livreur getLivreur() throws SQLException {
        LivreurDAO ldao = new LivreurDAO();
        return ldao.getOne(this.id_livreur);
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }

    public long getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(long id_livreur) {
        this.id_livreur = id_livreur;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }

    public void emptyProduits() throws SQLException {
        CommandeDAO cdao = new CommandeDAO();
        cdao.emptyProduits(id_commande);
    }

    public void addProduct(long id_produit, int quantite) throws SQLException{
        CommandeDAO cdao = new CommandeDAO();
        cdao.saveProduit(id_produit, quantite, id_commande);
    }

}
