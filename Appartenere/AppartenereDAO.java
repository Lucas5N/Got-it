package Model.Appartenere;

import Model.ConPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppartenereDAO {
    public List<AppartenereBean> doRetrieveByProdotto(int prodottoCodice) {
        String sql = "SELECT * FROM Appartenere WHERE Prodotto_Codice = ?";
        List<AppartenereBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, prodottoCodice);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppartenereBean a = new AppartenereBean();
                    a.setProdottoCodice(rs.getInt("Prodotto_Codice"));
                    a.setCategoriaNome(rs.getString("Categoria_Nome"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero categorie prodotto", e);
        }
        return list;
    }

    public void doSave(AppartenereBean a) {
        String sql = "INSERT INTO Appartenere (Prodotto_Codice, Categoria_Nome) VALUES (?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, a.getProdottoCodice());
            ps.setString(2, a.getCategoriaNome());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio categoria prodotto", e);
        }
    }

    public void doDelete(int prodottoCodice, String categoriaNome) {
        String sql = "DELETE FROM Appartenere WHERE Prodotto_Codice = ? AND Categoria_Nome = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, prodottoCodice);
            ps.setString(2, categoriaNome);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione categoria prodotto", e);
        }
    }
}
