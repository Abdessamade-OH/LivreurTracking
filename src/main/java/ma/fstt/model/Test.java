package ma.fstt.model;

import java.sql.SQLException;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        Livreur l1 = new Livreur(0L,"Ahmed", "+212629203953");
        Livreur l2 = new Livreur(0L,"Moha", "+212829285082");
        try{
            LivreurDAO ldao = new LivreurDAO();
            ldao.save(l1);
            ldao.save(l2);

            List<Livreur> livreurs= ldao.getAll();
            for(int i=0; i<livreurs.size(); i++){
                System.out.println(livreurs.get(i).toString());
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}
