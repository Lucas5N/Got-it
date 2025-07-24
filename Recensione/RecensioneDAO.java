package Model.Recensione;

import Model.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO {

    public List<RecensioneBean> doRetrieveByUtenteId(int utenteId) {
        String sql = "SELECT * FROM Recensione WHERE ID_Utente = ?";
        List<RecensioneBean> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecensioneBean r = new RecensioneBean();
                    r.setID_Utente(utenteId);
                    r.setProdotto_Codice(rs.getInt("Prodotto_Codice"));
                    r.setVoto(rs.getInt("Voto"));
                    r.setData(rs.getDate("Data"));
                    r.setDescrizione(rs.getString("Descrizione"));
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore recupero recensioni per utente", e);
        }
        return list;
    }

    public boolean doSave(RecensioneBean r) {
        String sql = "INSERT INTO Recensione (ID_Utente, Prodotto_Codice, Voto, Data, Descrizione) VALUES (?,?,?,?,?)";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getID_Utente());
            ps.setInt(2, r.getProdotto_Codice());
            ps.setInt(3, r.getVoto());
            ps.setDate(4, new java.sql.Date(r.getData().getTime()));
            ps.setString(5, r.getDescrizione());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void doUpdate(RecensioneBean r) {
        String sql = "UPDATE Recensione SET Voto = ?, Data = ?, Descrizione = ? WHERE ID_Utente = ? AND Prodotto_Codice = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getVoto());
            ps.setDate(2, new java.sql.Date(r.getData().getTime()));
            ps.setString(3, r.getDescrizione());
            ps.setInt(4, r.getID_Utente());
            ps.setInt(5, r.getProdotto_Codice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore aggiornamento recensione", e);
        }
    }

    public void doDelete(int utenteId, int prodottoCodice) {
        String sql = "DELETE FROM Recensione WHERE ID_Utente = ? AND Prodotto_Codice = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            ps.setInt(2, prodottoCodice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore cancellazione recensione", e);
        }
    }

    public List<RecensioneBean> doRetrieveByVenditore(int Id_Venditore){
        String sql = "SELECT * FROM Recensione WHERE Prodotto_Codice IN (SELECT Prodotto_Codice FROM vendere WHERE ID_Utente = ?)";
        List<RecensioneBean> recensioni = new ArrayList<>();

        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,Id_Venditore);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    RecensioneBean receBean = new RecensioneBean();
                    receBean.setID_Utente(rs.getInt("ID_Utente"));
                    receBean.setVoto(rs.getInt("Voto"));
                    receBean.setDescrizione(rs.getString("Descrizione"));
                    receBean.setData(rs.getDate("Data"));
                    receBean.setProdotto_Codice(rs.getInt("Prodotto_Codice"));
                    recensioni.add(receBean);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException("Errore nel recupero delle recensioni per utente", e);
        }
        return recensioni;
    }

    public List<RecensioneBean> doRetireveByProdotto(int prodottocodice){
        String sql = "SELECT * FROM Recensione WHERE Prodotto_Codice = ?";
        List<RecensioneBean> recensioniBean = new ArrayList<>();
        try(Connection con = ConPool.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,prodottocodice);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    RecensioneBean rbean = new RecensioneBean();
                    rbean.setID_Utente(rs.getInt("ID_Utente"));
                    rbean.setProdotto_Codice(prodottocodice);
                    rbean.setDescrizione(rs.getString("Descrizione"));
                    rbean.setVoto(rs.getInt("Voto"));
                    rbean.setData(rs.getDate("Data"));
                    recensioniBean.add(rbean);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Errore nel recupero delle recensioni per prodotto");
        }
        return recensioniBean;
    }

    public boolean hasUserReviewedProduct(int idUtente, int codiceProdotto) {
        String sql = "SELECT COUNT(*) FROM Recensione WHERE ID_Utente = ? AND Prodotto_Codice = ?";
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUtente);
            ps.setInt(2, codiceProdotto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il controllo della recensione esistente: " + e.getMessage());
        }
        return false;
    }
}