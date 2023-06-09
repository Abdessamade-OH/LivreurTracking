package ma.fstt.model;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.sql.SQLException;

public class ProduitCommande {

    private long id_produit;
    private int quantite;
    private long id_commande;
    private long id;
    private String nom;
    private float prix;


    public ProduitCommande(long id_produit, int quantite, long id_commande, long id, String nom, float prix)  {
        this.id_produit = id_produit;
        this.quantite = quantite;
        this.id_commande = id_commande;
        this.id = id;
        this.nom = nom;
        this.prix = prix;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    /*public StringProperty nomProperty(){

        return nom;
    }*/

    public long getId() {
        return id;
    }

    public long getId_produit() {
        return id_produit;
    }

    public void setId_produit(long id_produit) {
        this.id_produit = id_produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public long getId_commande() {
        return id_commande;
    }

    public void setId_commande(long id_commande) {
        this.id_commande = id_commande;
    }

}
