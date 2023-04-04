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

    private long id_livreur;

    private Timestamp date_debut;
    private Timestamp date_fin;

    private HashMap<Produit, Integer> produits;

    public Commande(long id_commande, float km, String client, String etat, Timestamp date_debut, Timestamp date_fin, long id_livreur) {
        this.id_commande = id_commande;
        this.km = km;
        this.client = client;
        this.etat = etat;
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

    public long getId_commande() {
        return id_commande;
    }

    public Livreur getLivreur() throws SQLException {
        LivreurDAO ldao = new LivreurDAO();
        return ldao.getOne(this.id_livreur);
    }

    public HashMap<Produit, Integer> getProduits() {
        return produits;
    }

    public void setProduits(HashMap<Produit, Integer> produits) {
        this.produits = produits;
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
