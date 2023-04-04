package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit> {

    public ProduitDAO() throws SQLException{

        super();
    }

    @Override
    public void save(Produit object) throws SQLException {

        String request = "insert into produit (prix, nom, description) values (?, ?, ?)";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setFloat(1, object.getPrix());
        this.preparedStatement.setString(2, object.getNom());
        this.preparedStatement.setString(3, object.getDescription());

        this.preparedStatement.execute();
    }

    @Override
    public void update(Produit object) throws SQLException {
        String request = "update produit set prix = ?,  nom = ?, description = ? where id_produit = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setFloat(1, object.getPrix());
        this.preparedStatement.setString(2, object.getNom());
        this.preparedStatement.setString(3, object.getDescription());
        this.preparedStatement.setLong(4, object.getId_produit());

        this.preparedStatement.execute();
    }

    @Override
    public void delete(Produit object) throws SQLException {
        String request = "delete from produit where id_produit = (?)";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, object.getId_produit());

        this.preparedStatement.execute();
    }

    @Override
    public List<Produit> getAll() throws SQLException {

        List<Produit> myList = new ArrayList<>();
        String request ="Select * from produit";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        while(this.resultSet.next()){
            myList.add(
                    new Produit(
                        this.resultSet.getLong(1),
                        this.resultSet.getFloat(2),
                        this.resultSet.getString(3),
                        this.resultSet.getString(4)
                    )
            );
        }
        return myList;
    }

    @Override
    public Produit getOne(Long id) throws SQLException {
        String request = "Select * from produit where id_produit = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);

        this.resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Produit produit = new Produit(
                    this.resultSet.getLong(1),
                    this.resultSet.getFloat(2),
                    this.resultSet.getString(3),
                    this.resultSet.getString(4)
            );
            return produit;
        }

        return null;
    }

    public List<ProduitCommande> getProduitsCommandes(Long id_produit) throws SQLException{
        String request = "Select * from produit_commande where id_produit = ?";
        List<ProduitCommande> myList = new ArrayList<>();
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id_produit);

        this.resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            ProduitDAO pdao = new ProduitDAO();
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
}
