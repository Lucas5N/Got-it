package Model.COP;

import Model.ConPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class COPDAO {
    public List<COPBean> doRetrieveByOrdine(int ordineCodice) {
        String sql = "SELECT * FROM Contenere_ordine_prodotto WHERE Ordine_Codice = ? ";
        List<COPBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ordineCodice);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    COPBean cp = new COPBean();
                    cp.setOrdine_Codice(rs.getInt("Ordine_Codice"));
                    cp.setProdotto_Codice(rs.getInt("Prodotto_Codice"));
                    list.add(cp);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero COP", e);
        }
        return list;
    }

    public void doSave(COPBean cp) {
        String sql = "INSERT INTO Contenere_ordine_prodotto (Ordine_Codice, Prodotto_Codice) VALUES (?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cp.getOrdine_Codice());
            ps.setInt(2, cp.getProdotto_Codice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio COP", e);
        }
    }

}
