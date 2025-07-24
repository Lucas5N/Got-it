package Model.Carta_di_Credito;

import Model.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CDCDAO {
    public List<CDCBean> doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT * FROM Carta_di_credito WHERE ID_Utente = ?";
        List<CDCBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CDCBean c = new CDCBean();
                    c.setNumero(rs.getString("Numero"));
                    c.setID_Utente(utenteId);
                    c.setCVV(rs.getString("CVV"));
                    c.setData_scadenza(rs.getDate("Data_scadenza"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero carte di credito per utente", e);
        }
        return list;
    }

    public void doSave(CDCBean c) {
        String sql = "INSERT INTO Carta_di_credito (Numero, ID_Utente, CVV, Data_scadenza) VALUES (?,?,?,?)";
        try (Connection con = ConPool.getConnection();

             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNumero());
            ps.setInt(2, c.getID_Utente());
            ps.setString(3, c.getCVV());
            ps.setDate(4, new java.sql.Date(c.getData_scadenza().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio carta di credito", e);
        }
    }

    public void doDelete(String numero) {
        String sql = "DELETE FROM Carta_di_credito WHERE Numero = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numero);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione carta di credito", e);
        }
    }
}
