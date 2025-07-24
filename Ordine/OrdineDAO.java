package Model.Ordine;

import Model.ConPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class OrdineDAO {
    public List<OrdineBean> doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT * FROM Ordine WHERE ID_Utente = ?";
        List<OrdineBean> list = new ArrayList<>();

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineBean o = new OrdineBean();
                    o.setCodice(rs.getInt("Codice"));
                    o.setID_Utente(utenteId);
                    o.setCarta_di_credito(rs.getString("Carta_di_credito"));
                    o.setData_consegna(rs.getDate("Data_consegna"));
                    o.setData_acquisto(rs.getDate("Data_acquisto"));
                    o.setCosto_consegna(rs.getDouble("Costo_consegna"));
                    o.setQuantita(rs.getInt("Quantita"));
                    o.setIndirizzo_di_consegna(rs.getString("Indirizzo_di_consegna"));
                    LocalDate consegna = o.getData_consegna().toLocalDate();
                    String stato = LocalDate.now().isAfter(consegna) || LocalDate.now().isEqual(consegna) ? "Consegnato" : "In Elaborazione";
                    o.setStato_consegna(stato);
                    list.add(o);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero ordini per utente", e);
        }
        return list;
    }

    // Modifica: Restituisce l'ID generato

    public int doSave(OrdineBean o) {

        LocalDate consegna = o.getData_consegna().toLocalDate();
        String stato = LocalDate.now().isAfter(consegna) || LocalDate.now().isEqual(consegna) ? "Consegnato" : "In Elaborazione";
        o.setStato_consegna(stato);

        String sql = "INSERT INTO Ordine (ID_Utente, Carta_di_credito, Data_consegna, Data_acquisto, Costo_consegna, Quantita, Indirizzo_di_consegna, Stato_consegna) VALUES (?,?,?,?,?,?,?,?)";
        int generatedId = -1;

        try (Connection con = ConPool.getConnection()) {
            con.setAutoCommit(true);

            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, o.getID_Utente());
                ps.setString(2, o.getCarta_di_credito());
                ps.setDate(3, new java.sql.Date(o.getData_consegna().getTime()));
                ps.setDate(4, new java.sql.Date(o.getData_acquisto().getTime()));
                ps.setDouble(5, o.getCosto_consegna());
                ps.setInt(6, o.getQuantita());
                ps.setString(7, o.getIndirizzo_di_consegna());
                ps.setString(8, o.getStato_consegna());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio ordine", e);
        }
        return generatedId;
    }


    public void doUpdate(OrdineBean o) {
        String sql = "UPDATE Ordine SET Carta_di_credito=?, Data_consegna=?, Data_acquisto=?, Costo_consegna=?, Quantita=?, Indirizzo_di_consegna=?, Stato_consegna=? WHERE Codice=?";

        try (Connection con = ConPool.getConnection()) {
            con.setAutoCommit(true);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, o.getCarta_di_credito());
                ps.setDate(2, new java.sql.Date(o.getData_consegna().getTime()));
                ps.setDate(3, new java.sql.Date(o.getData_acquisto().getTime()));
                ps.setDouble(4, o.getCosto_consegna());
                ps.setInt(5, o.getQuantita());
                ps.setString(6, o.getIndirizzo_di_consegna());
                ps.setString(7, o.getStato_consegna());
                ps.setInt(8, o.getCodice());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore aggiornamento ordine", e);
        }
    }

    public void doDelete(int codice) {
        String sql = "DELETE FROM Ordine WHERE Codice = ?";

        try (Connection con = ConPool.getConnection()) {
            con.setAutoCommit(true);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, codice);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione ordine", e);
        }
    }
}