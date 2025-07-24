package Model.CWP;

import Model.ConPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CWPDAO {
    public List<CWPBean> doRetrieveByUtente(int utenteId) {
        String sql = "SELECT * FROM Contenere_wishlist_prodotto WHERE ID_Utente = ?";
        List<CWPBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CWPBean cw = new CWPBean();
                    cw.setID_Utente(rs.getInt("ID_Utente"));
                    cw.setProdotto_Codice(rs.getInt("Prodotto_Codice"));
                    list.add(cw);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero CWP", e);
        }
        return list;
    }

    public void doSave(CWPBean cw) {
        String sql = "INSERT INTO Contenere_wishlist_prodotto (ID_Utente, Prodotto_Codice) VALUES (?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cw.getID_Utente());
            ps.setInt(2, cw.getProdotto_Codice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio CWP", e);
        }
    }

    public void doDeleteOne(int utenteId, int prodottoCodice) {
        String sql = "DELETE FROM Contenere_wishlist_prodotto WHERE ID_Utente = ? AND Prodotto_Codice = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.setInt(2, prodottoCodice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione CWP", e);
        }
    }




    public boolean isProductInWishlist(int userId, int productId) {
        String sql = "SELECT * FROM Contenere_wishlist_prodotto WHERE ID_Utente = ? AND Prodotto_Codice = ?";
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
