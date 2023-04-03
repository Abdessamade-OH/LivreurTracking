package ma.fstt.model;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class LivreurDAO extends BaseDAO<Livreur>{

    public LivreurDAO() throws SQLException {

        super();
    }

    //on doit redéfinir les méthodes qu'on est besion


    @Override
    public void save(Livreur object) throws SQLException {

        String request = "insert into livreur (nom, telephone) values (?, ?)";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setString(1, object.getNom());
        this.preparedStatement.setString(2, object.getTelephone());

        this.preparedStatement.execute();
    }

    @Override
    public void update(Livreur object) throws SQLException {
        String request = "update livreur set nom = ?,  telephone = ? where id_livreur = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setString(1, object.getNom());
        this.preparedStatement.setString(2, object.getTelephone());
        this.preparedStatement.setLong(3, object.getId_livreur());

        this.preparedStatement.execute();
    }

    @Override
    public void delete(Livreur object) throws SQLException {
        String request = "delete from livreur where id_livreur = (?)";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, object.getId_livreur());

        this.preparedStatement.execute();
    }

    @Override
    public List<Livreur> getAll() throws SQLException {
        List<Livreur> myList = new ArrayList<>();
        String request = "select * from livreur";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        while(this.resultSet.next()){
            myList.add(
                    new Livreur(
                        this.resultSet.getLong(1),
                        this.resultSet.getString(2),
                        this.resultSet.getString(3)
                    )
            );
        }
        return myList;
    }

    @Override
    public Livreur getOne(Long id) throws SQLException {

        String request = "Select from livreur where id_livreur = (?)";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);

        this.resultSet = preparedStatement.executeQuery();
        return (Livreur) resultSet.getObject(0);
    }
}
