package Model.Telefono;

import Model.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefonoDAO {
    public List<TelefonoBean> doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT Numero, Ruolo FROM Telefono WHERE Utente_ID = ?";
        List<TelefonoBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TelefonoBean t = new TelefonoBean();
                    t.setNumero(rs.getString("Numero"));
                    t.setUtenteId(utenteId);
                    t.setRuolo(rs.getString("Ruolo"));
                    list.add(t);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero telefoni per utente", e);
        }
        return list;
    }

    public void doSave(TelefonoBean t) {
        String sql = "INSERT INTO Telefono (Numero, Utente_ID, Ruolo) VALUES (?,?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNumero());
            ps.setInt(2, t.getUtenteId());
            ps.setString(3, t.getRuolo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio telefono", e);
        }
    }

    public void doDelete(String numero) {
        String sql = "DELETE FROM Telefono WHERE Numero = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numero);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione telefono", e);
        }
    }
}
