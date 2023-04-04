package ma.fstt.model;

import java.sql.SQLException;

public class Produit {

    private long id_produit;
    private float prix;
    private String nom;
    private String description;

    public Produit(long id_produit, float prix, String nom, String description) {
        this.id_produit = id_produit;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
    }

    public int getCommanded() throws SQLException {

        ProduitDAO pdao = new ProduitDAO();

        return 0;
    }
    public long getId_produit() {
        return id_produit;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
