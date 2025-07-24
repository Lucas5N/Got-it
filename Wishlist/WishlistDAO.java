package Model.Wishlist;

import Model.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    public List<WishlistBean> doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT * FROM Wishlist WHERE ID_Utente = ?";
        List<WishlistBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WishlistBean w = new WishlistBean();
                    w.setID_Utente(utenteId);
                    w.setNome(rs.getString("Nome"));
                    w.setData_creazione(rs.getDate("Data_creazione"));
                    w.setNumero_articoli(rs.getInt("Numero_articoli"));
                    list.add(w);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero wishlist per utente", e);
        }
        return list;
    }

    public void doSave(WishlistBean w) {
        String sql = "INSERT INTO Wishlist (ID_Utente, Nome, Data_creazione, Numero_articoli) VALUES (?,?,?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, w.getID_Utente());
            ps.setString(2, w.getNome());
            ps.setDate(3, new java.sql.Date(w.getData_creazione().getTime()));
            ps.setInt(4, w.getNumero_articoli());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio wishlist", e);
        }
    }

    public void doDelete(int utenteId, String nome) {
        String sql = "DELETE FROM Wishlist WHERE ID_Utente = ? AND Nome = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.setString(2, nome);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione wishlist", e);
        }
    }
}
