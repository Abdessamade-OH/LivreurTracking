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

    @Override
    public void update(Commande object) throws SQLException {
        String request = "update livreur set km = ?,  client = ?, etat = ?, id_livreur = ?, date_fin = ? where id_commande = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setFloat(1, object.getKm());
        this.preparedStatement.setString(2, object.getClient());
        this.preparedStatement.setString(3, object.getEtat());
        this.preparedStatement.setLong(4, object.getId_livreur());
        this.preparedStatement.setTimestamp(5, object.getDate_fin());
        this.preparedStatement.setLong(6, object.getId_commande());

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
                            this.resultSet.getLong(7)
                    )
            );
        }
        return myList;
    }

    @Override
    public Commande getOne(Long id) throws SQLException {
        return null;
    }


}
