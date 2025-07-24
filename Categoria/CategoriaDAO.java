package Model.Categoria;
import Model.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    public List<CategoriaBean> doRetrieveAll(){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM categoria");
            ResultSet rs = ps.executeQuery();
            List<CategoriaBean> categorie = new ArrayList<>();
            while(rs.next()){
                CategoriaBean cat = new CategoriaBean();
                cat.setNome(rs.getString("nome"));
                categorie.add(cat);
            }
            return categorie;
        }catch(SQLException e){
            throw new RuntimeException("ERRORE DO_RETRIEVE_ALL -> CategoriaDAO",e);
        }
    }
}
