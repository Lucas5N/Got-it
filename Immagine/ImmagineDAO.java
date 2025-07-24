package Model.Immagine;

import Model.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImmagineDAO {
    public List<String> doRetrieveImageByProduct(int codice){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT percorso FROM Immagini WHERE Codice = ?");
            ps.setInt(1, codice);

            ResultSet rs = ps.executeQuery();
            List<String> path = new ArrayList<>();

            while (rs.next() ) {
                path.add(rs.getString("percorso"));
            }

            return path;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_RETRIEVE_IMAGE_BY_PRODUCT -> ImmagineDAO", e);
        }
    }

    public void doSave(ImmagineBean immagineBean){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO Immagini(percorso, codice) VALUES(?,?)");

            ps.setString(1, immagineBean.getPath());
            ps.setInt(2, immagineBean.getCodice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_SAVE -> ImmaginiDAO", e);
        }
    }

}
