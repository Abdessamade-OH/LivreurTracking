package ma.fstt.model;

public class Produit {

    private int id_produit;

    private String nom;
    private String description;

    public Produit(int id_produit, String nom, String description) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.description = description;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
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
