package ma.fstt.model;

public class Livreur {
    private Long id_livreur;
    private String nom;
    private String telephone;
    private String vehicule;

    public Livreur(Long id, String nom, String telephone, String vehicule) {
        this.id_livreur = id;
        this.nom = nom;
        this.telephone = telephone;
        this.vehicule = vehicule;
    }
    public Long getId_livreur() {
        return id_livreur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
