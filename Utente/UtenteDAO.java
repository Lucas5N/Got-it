package Model.Utente;

import Model.ConPool;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;




public class UtenteDAO {

    public UtenteBean doRetrieveByEmail(String email) {
        String sql = "SELECT * FROM Utente WHERE Email = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UtenteBean u = new UtenteBean();
                    u.setID_Utente(rs.getInt("ID_Utente"));
                    u.setNome(rs.getString("Nome"));
                    u.setCognome(rs.getString("Cognome"));
                    u.setEmail(rs.getString("Email"));
                    u.setUsername(rs.getString("Username"));
                    u.setPassword(rs.getString("Password"));
                    u.setStato(rs.getString("Stato"));
                    u.setRegione(rs.getString("Regione"));
                    u.setCitta(rs.getString("Città"));
                    u.setVia(rs.getString("Via"));
                    u.setNumero_civico(rs.getString("Numero_civico"));
                    u.setConto_corrente_addebbitabile(rs.getString("Conto_corrente_addebitabile"));
                    u.setTelefono(rs.getString("Telefono"));
                    u.setAmministratore(rs.getBoolean("Amministratore"));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero dell'utente per email", e);
        }
        return null;
    }


    public UtenteBean doRetrieveById(int id) {
        String sql = "SELECT * FROM Utente WHERE ID_Utente = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UtenteBean u = new UtenteBean();
                    u.setID_Utente(rs.getInt("ID_Utente"));
                    u.setNome(rs.getString("Nome"));
                    u.setCognome(rs.getString("Cognome"));
                    u.setEmail(rs.getString("Email"));
                    u.setUsername(rs.getString("Username"));
                    u.setPassword(rs.getString("Password"));
                    u.setStato(rs.getString("Stato"));
                    u.setRegione(rs.getString("Regione"));
                    u.setCitta(rs.getString("Città"));
                    u.setVia(rs.getString("Via"));
                    u.setNumero_civico(rs.getString("Numero_civico"));
                    u.setConto_corrente_addebbitabile(rs.getString("Conto_corrente_addebitabile"));
                    u.setTelefono(rs.getString("Telefono"));
                    u.setAmministratore(rs.getBoolean("Amministratore"));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero dell'utente per ID", e);
        }
        return null;
    }


    public UtenteBean doRetrieveByUsernamePassword(String username, String password) {
        String sql = "SELECT * FROM Utente WHERE Username = ? AND Password = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String passwordHash = hashPassword(password);

            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UtenteBean u = new UtenteBean();
                    u.setID_Utente(rs.getInt("ID_Utente"));
                    u.setNome(rs.getString("Nome"));
                    u.setCognome(rs.getString("Cognome"));
                    u.setEmail(rs.getString("Email"));
                    u.setUsername(rs.getString("Username"));
                    u.setPassword(rs.getString("Password"));
                    u.setStato(rs.getString("Stato"));
                    u.setRegione(rs.getString("Regione"));
                    u.setCitta(rs.getString("Città"));
                    u.setVia(rs.getString("Via"));
                    u.setNumero_civico(rs.getString("Numero_civico"));
                    u.setConto_corrente_addebbitabile(rs.getString("Conto_corrente_addebitabile"));
                    u.setTelefono(rs.getString("Telefono"));
                    u.setAmministratore(rs.getBoolean("Amministratore"));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero dell'utente dati Username e Password", e);
        }
        return null;
    }




    public UtenteBean doRetrieveByUsername(String username) {
        String sql = "SELECT * FROM Utente WHERE Username = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return doRetrieveById(rs.getInt("ID_Utente"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero dell'utente per username", e);
        }
        return null;
    }

    public List<UtenteBean> doRetrieveAll() {
        String sql = "SELECT * FROM Utente";
        List<UtenteBean> utenti = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                utenti.add(doRetrieveById(rs.getInt("ID_Utente")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero di tutti gli utenti", e);
        }
        return utenti;
    }

    public void doSave(UtenteBean u) {
        String sql = "INSERT INTO Utente (Nome, Cognome, Email, Username, Password, Stato, Regione, Città, Via, Numero_civico, Conto_corrente_addebitabile, Telefono, Amministratore) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Hash della password
            String passwordHash = hashPassword(u.getPassword());

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getUsername());
            ps.setString(5, passwordHash);
            ps.setString(6, u.getStato());
            ps.setString(7, u.getRegione());
            ps.setString(8, u.getCitta());
            ps.setString(9, u.getVia());
            ps.setString(10, u.getNumero_civico());
            ps.setString(11, u.getConto_corrente_addebbitabile());
            ps.setString(12, u.getTelefono());
            ps.setBoolean(13,u.getAmministratore());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setID_Utente(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel salvataggio dell'utente", e);
        }
    }

    public void doUpdate(UtenteBean u) {
        String sql = "UPDATE Utente SET Nome=?, Cognome=?, Email=?, Username=?, Password=?, Stato=?, Regione=?, Città=?, Via=?, Numero_civico=?, Conto_corrente_addebitabile=?, Telefono = ? WHERE ID_Utente=?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String passwordHash = hashPassword(u.getPassword());

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getUsername());
            ps.setString(5, passwordHash);
            ps.setString(6, u.getStato());
            ps.setString(7, u.getRegione());
            ps.setString(8, u.getCitta());
            ps.setString(9, u.getVia());
            ps.setString(10, u.getNumero_civico());
            ps.setString(11, u.getConto_corrente_addebbitabile());
            ps.setString(12, u.getTelefono());
            ps.setInt(13, u.getID_Utente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nell'aggiornamento dell'utente", e);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return String.format("%040x", new BigInteger(1, hash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 non disponibile", e);
        }
    }


    public void doDelete(int id) {
        String sql = "DELETE FROM Utente WHERE ID_Utente = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella cancellazione dell'utente", e);
        }
    }
}
