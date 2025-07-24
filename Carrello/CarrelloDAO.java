package Model.Carrello;

import Model.ConPool;

import java.sql.*;

public class CarrelloDAO {
    public CarrelloBean doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT * FROM Carrello WHERE ID_Utente = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CarrelloBean c = new CarrelloBean();
                    c.setUtenteId(utenteId);
                    c.setData_creazione(rs.getDate("Data_creazione"));
                    c.setNumero_articoli(rs.getInt("Numero_articoli"));
                    return c;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero carrello per utente", e);
        }
        return null;
    }

    public void doSave(CarrelloBean c) {
        String sql = "INSERT INTO Carrello (ID_Utente, Data_creazione, Numero_articoli) VALUES (?,?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getID_Utente());
            ps.setDate(2, new java.sql.Date(c.getData_creazione().getTime()));
            ps.setInt(3, c.getNumero_articoli());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio carrello", e);
        }
    }

    public void doUpdate(CarrelloBean c) {
        String sql = "UPDATE Carrello SET Data_creazione=?, Numero_articoli=? WHERE ID_Utente=?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(c.getData_creazione().getTime()));
            ps.setInt(2, c.getNumero_articoli());
            ps.setInt(3, c.getID_Utente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore aggiornamento carrello", e);
        }
    }

    public void doDelete(int utenteId) {
        String sql = "DELETE FROM Carrello WHERE ID_Utente=?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione carrello", e);
        }
    }
}
