package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO extends BaseDAO<Commande>{
    public CommandeDAO() throws SQLException {

        super();
    }

    @Override
    public void save(Commande object) throws SQLException {
        String request = "insert into commande (km, client, id_livreur) values (?, ?, ?)";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setFloat(1, object.getKm());
        this.preparedStatement.setString(2, object.getClient());
        this.preparedStatement.setLong(3, object.getId_livreur());

        this.preparedStatement.execute();
    }

    public void saveProduit(long id_produit, int quantite, long id_commande) throws SQLException{
        String request = "insert into produit_commande (id_produit, quantite, id_commande) values(?, ?, ?)";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id_produit);
        this.preparedStatement.setLong(2, quantite);
        this.preparedStatement.setLong(3, id_commande);

        this.preparedStatement.execute();
    }

    public void emptyProduits(long id_commande) throws SQLException{
        String request = "delete from produit_commande where id_commande = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id_commande);

        this.preparedStatement.execute();
    }

    public List<ProduitCommande> getAllProduits(long id_commande) throws SQLException{
        String request = "Select * from produit_commande where id_commande = ?";
        List<ProduitCommande> myList = new ArrayList<>();
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id_commande);

        this.resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            ProduitDAO pdao = new ProduitDAO();
            Long id_produit = this.resultSet.getLong(1);
            Produit produit = pdao.getOne(id_produit);
            myList.add(
                    new ProduitCommande(
                        id_produit,
                        this.resultSet.getInt(2),
                        this.resultSet.getLong(3),
                        this.resultSet.getLong(4),
                        produit.getNom(),
                        produit.getPrix()
                     )
            );
        }

        return myList;
    }

    @Override
    public void update(Commande object) throws SQLException {
        String request = "update commande set km = ?,  client = ?, etat = ?, id_livreur = ?, date_fin = ?, prix_total = ? where id_commande = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setFloat(1, object.getKm());
        this.preparedStatement.setString(2, object.getClient());
        this.preparedStatement.setString(3, object.getEtat());
        this.preparedStatement.setLong(4, object.getId_livreur());
        this.preparedStatement.setTimestamp(5, object.getDate_fin());
        this.preparedStatement.setFloat(6, object.getPrix_total());
        this.preparedStatement.setLong(7, object.getId_commande());

        this.preparedStatement.execute();
    }

    @Override
    public void delete(Commande object) throws SQLException {
        String request = "delete from commande where id_commande = (?)";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, object.getId_commande());

        this.preparedStatement.execute();
    }

    @Override
    public List<Commande> getAll() throws SQLException {
        List<Commande> myList = new ArrayList<>();
        String request = "select * from commande";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        while(this.resultSet.next()){
            myList.add(
                    new Commande(
                            this.resultSet.getLong(1),
                            this.resultSet.getFloat(2),
                            this.resultSet.getString(3),
                            this.resultSet.getString(4),
                            this.resultSet.getFloat(5),
                            this.resultSet.getTimestamp(6),
                            this.resultSet.getTimestamp(7),
                            this.resultSet.getLong(8)
                    )
            );
        }
        return myList;
    }

    @Override
    public Commande getOne(Long id) throws SQLException {
        return null;
    }

    public List<Commande> getAllById(Long id_livreur) throws SQLException{
            List<Commande> myList = new ArrayList<>();
            String request = "Select * from commande where id_livreur = ?";
            this.preparedStatement = this.connection.prepareStatement(request);
            this.preparedStatement.setLong(1, id_livreur);

            this.resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                myList.add(
                        new Commande(
                                this.resultSet.getLong(1),
                                this.resultSet.getFloat(2),
                                this.resultSet.getString(3),
                                this.resultSet.getString(4),
                                this.resultSet.getFloat(5),
                                this.resultSet.getTimestamp(6),
                                this.resultSet.getTimestamp(7),
                                this.resultSet.getLong(8)
                        )
                );
            }

        return myList;
    }


    public boolean authentifier(String username, String password) throws SQLException{
        String request = "Select username,password from admin where username = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setString(1, username);

        this.resultSet = this.preparedStatement.executeQuery();

        while(this.resultSet.next()){
            System.out.println(this.resultSet.getString(1));
            System.out.println(this.resultSet.getString(2));
            if (password.equals(this.resultSet.getString(2))){
                return true;
            }
        }
        return false;
    }
}
