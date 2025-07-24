package Model.Vendere;

import Model.ConPool;
import Model.Prodotto.ProdottoBean;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendereDAO {

    public int doRetrieveByProdottoCodice(int prodottoCodice) {
        String sql = "SELECT ID_Utente FROM vendere WHERE Prodotto_Codice = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, prodottoCodice);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_Utente");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<ProdottoBean> doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT P.* FROM Prodotto P JOIN vendere V ON P.Codice = V.Prodotto_Codice WHERE V.ID_Utente = ?";
        List<ProdottoBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdottoBean p = new ProdottoBean();
                    p.setCodice(rs.getInt("Codice"));
                    p.setNome(rs.getString("Nome"));
                    p.setPrezzo((rs.getDouble("Prezzo")));
                    p.setDescrizione(rs.getString("Descrizione"));
                    p.setColore(rs.getString("Colore"));
                    p.setPeso((rs.getDouble("Peso")));
                    p.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                    p.setNewPrezzo(rs.getDouble("prezzo_scontato"));
                    p.setImg_path(rs.getString("img_path"));
                    p.setCondizioni(rs.getString("Condizioni"));
                    p.setDiscounted(rs.getInt("isDiscounted"));
                    p.setDeleted(rs.getBoolean("isDeleted"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero prodotti venduti per utente", e);
        }
        return list;
    }

    public void doSave(int utenteId, int prodottoCodice) {
        String sql = "INSERT INTO vendere (ID_Utente, Prodotto_Codice) VALUES (?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.setInt(2, prodottoCodice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio venditore-prodotto", e);
        }
    }

    public void doDelete(int utenteId, int prodottoCodice) {
        String sql = "DELETE FROM  vendere WHERE ID_Utente = ? AND Prodotto_Codice = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.setInt(2, prodottoCodice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione venditore-prodotto", e);
        }
    }
}