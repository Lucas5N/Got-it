package Model.CCP;

import Model.ConPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CCPDAO {
    public List<CCPBean> doRetrieveByUtente(int utenteId) {
        String sql = "SELECT * FROM Contenere_carrello_prodotto WHERE ID_Utente = ?";
        List<CCPBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CCPBean cc = new CCPBean();
                    cc.setID_Utente(rs.getInt("ID_Utente"));
                    cc.setProdotto_Codice(rs.getInt("Prodotto_Codice"));
                    list.add(cc);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero CCP", e);
        }
        return list;
    }

    public void doSave(CCPBean cc) {
        String sql = "INSERT INTO Contenere_carrello_prodotto (ID_Utente, Prodotto_Codice) VALUES (?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cc.getID_Utente());
            ps.setInt(2, cc.getProdotto_Codice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio CCP", e);
        }
    }

    public void doDeleteOne(int utenteId, int prodottoCodice) {
        String sql = "DELETE FROM Contenere_carrello_prodotto WHERE ID_Utente = ? AND Prodotto_Codice = ? ";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.setInt(2, prodottoCodice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione singolo CCP", e);
        }
    }

    public boolean isProductInCart(int userId, int productId) {
        String sql = "SELECT * FROM Contenere_carrello_prodotto WHERE ID_Utente = ? AND Prodotto_Codice = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
