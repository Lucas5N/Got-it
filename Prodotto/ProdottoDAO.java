

package Model.Prodotto;

import Model.ConPool;
import Model.Prodotto.ProdottoBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public List<ProdottoBean> doRetrieveAll() {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto WHERE isDeleted = 0 OR isDeleted IS NULL");
            ResultSet rs = ps.executeQuery();
            List<ProdottoBean> prodotti = new ArrayList<>();

            while (rs.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setCodice(rs.getInt("Codice"));
                prodottoBean.setNome(rs.getString("Nome"));
                prodottoBean.setPrezzo(rs.getDouble("Prezzo"));
                prodottoBean.setDescrizione(rs.getString("Descrizione"));
                prodottoBean.setPeso(rs.getDouble("Peso"));
                prodottoBean.setColore(rs.getString("Colore"));
                prodottoBean.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                prodottoBean.setImg_path(rs.getString("img_path"));
                prodottoBean.setNewPrezzo(rs.getDouble("prezzo_scontato"));
                prodottoBean.setCondizioni(rs.getString("Condizioni"));
                prodottoBean.setDiscounted(rs.getInt("isDiscounted"));
                prodottoBean.setCategoria(rs.getString("Categoria"));

                prodotti.add(prodottoBean);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_RETRIEVE_ALL -> ProdottoDAO", e);
        }
    }

    public List<ProdottoBean> doRetrieveByNome(String nome) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto WHERE Nome = ? AND isDeleted = 0");
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            List<ProdottoBean> prodotti = new ArrayList<>();

            while (rs.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setCodice(rs.getInt("Codice"));
                prodottoBean.setNome(rs.getString("Nome"));
                prodottoBean.setPrezzo(rs.getDouble("Prezzo"));
                prodottoBean.setDescrizione(rs.getString("Descrizione"));
                prodottoBean.setPeso(rs.getDouble("Peso"));
                prodottoBean.setColore(rs.getString("Colore"));
                prodottoBean.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                prodottoBean.setImg_path(rs.getString("img_path"));

                prodotti.add(prodottoBean);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_RETRIEVE_BY_NOME -> ProdottoDAO", e);
        }
    }

    public ProdottoBean doRetrieveByCodice(int codice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto WHERE Codice = ? AND isDeleted = 0");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setCodice(rs.getInt("Codice"));
                prodottoBean.setNome(rs.getString("Nome"));
                prodottoBean.setPrezzo(rs.getDouble("Prezzo"));
                prodottoBean.setDescrizione(rs.getString("Descrizione"));
                prodottoBean.setColore(rs.getString("Colore"));
                prodottoBean.setPeso(rs.getDouble("Peso"));
                prodottoBean.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                prodottoBean.setImg_path(rs.getString("img_path"));
                prodottoBean.setCondizioni(rs.getString("Condizioni"));
                prodottoBean.setNewPrezzo(rs.getDouble("prezzo_scontato"));
                prodottoBean.setDiscounted(rs.getInt("isDiscounted"));
                prodottoBean.setCategoria(rs.getString("Categoria"));

                return prodottoBean;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_RETRIEVE_BY_CODICE -> ProdottoDAO", e);
        }
    }



    public ProdottoBean doRetrieveByCodiceForReview(int codice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto WHERE Codice = ?");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setCodice(rs.getInt("Codice"));
                prodottoBean.setNome(rs.getString("Nome"));
                prodottoBean.setPrezzo(rs.getDouble("Prezzo"));
                prodottoBean.setDescrizione(rs.getString("Descrizione"));
                prodottoBean.setColore(rs.getString("Colore"));
                prodottoBean.setPeso(rs.getDouble("Peso"));
                prodottoBean.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                prodottoBean.setImg_path(rs.getString("img_path"));
                prodottoBean.setCondizioni(rs.getString("Condizioni"));
                prodottoBean.setNewPrezzo(rs.getDouble("prezzo_scontato"));
                prodottoBean.setDiscounted(rs.getInt("isDiscounted"));
                prodottoBean.setCategoria(rs.getString("Categoria"));

                return prodottoBean;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_RETRIEVE_BY_CODICE -> ProdottoDAO", e);
        }
    }




    public List<ProdottoBean> doFilterByPrezzo(double prezzo) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto WHERE Prezzo <= ? AND isDeleted = 0");
            ps.setDouble(1, prezzo);
            ResultSet rs = ps.executeQuery();
            List<ProdottoBean> prodotti = new ArrayList<>();

            while (rs.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setCodice(rs.getInt("Codice"));
                prodottoBean.setNome(rs.getString("Nome"));
                prodottoBean.setPrezzo(rs.getDouble("Prezzo"));
                prodottoBean.setDescrizione(rs.getString("Descrizione"));
                prodottoBean.setPeso(rs.getDouble("Peso"));
                prodottoBean.setColore(rs.getString("Colore"));
                prodottoBean.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                prodottoBean.setImg_path(rs.getString("img_path"));

                prodotti.add(prodottoBean);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_FILTER_BY_PREZZO -> ProdottoDAO", e);
        }
    }

    public synchronized int doSave(ProdottoBean prodottoBean) {
        try (Connection con = ConPool.getConnection()) {

            int generatedId = -1;

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Prodotto (Nome, Prezzo, Descrizione, Peso, Colore, Media_recensioni, img_path, Condizioni, Categoria, isDeleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, prodottoBean.getNome());
            ps.setDouble(2, prodottoBean.getPrezzo());
            ps.setString(3, prodottoBean.getDescrizione());
            ps.setDouble(4, prodottoBean.getPeso());
            ps.setString(5, prodottoBean.getColore());
            ps.setBigDecimal(6, prodottoBean.getMedia_recensioni());
            ps.setString(7, prodottoBean.getImg_path());
            ps.setString(8, prodottoBean.getCondizioni());
            ps.setString(9, prodottoBean.getCategoria());
            ps.setBoolean(10, prodottoBean.getDeleted());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    prodottoBean.setCodice(generatedId);
                }
            }

            return generatedId;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_SAVE -> ProdottoDAO", e);
        }
    }

    public void doDelete(ProdottoBean prodottoBean) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Prodotto SET isDeleted = 1 WHERE Codice = ?"
            );
            ps.setInt(1, prodottoBean.getCodice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_DELETE -> ProdottoDAO", e);
        }
    }

    public boolean setPrezzo(int id, double prezzo) throws SQLException {
        String sql = "UPDATE Prodotto SET Prezzo = ? WHERE Codice = ?";
        try (
                Connection con = ConPool.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setDouble(1, prezzo);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            return rows > 0;  // true se almeno una riga è stata aggiornata
        } catch (SQLException e) {
            // Rilancia con messaggio più chiaro
            throw new SQLException("Errore in setPrezzo (ProdottoDAO)", e);
        }
    }


    public boolean setPrezzoScontato(int id, double prezzoScontato) throws SQLException {
        String sql = "UPDATE Prodotto SET prezzo_scontato = ?, isDiscounted = ? WHERE Codice = ?";
        try (
                Connection con = ConPool.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            if(prezzoScontato == 0){
                ps.setDouble(1, prezzoScontato);
                ps.setInt(2, 0);
                ps.setInt(3, id);

                ps.executeUpdate();
                return false;
            }

            ps.setDouble(1, prezzoScontato);
            ps.setInt(2, 1);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            return rows > 0;  // true se almeno una riga è stata aggiornata
        } catch (SQLException e) {
            // Rilancia con messaggio più chiaro
            throw new SQLException("Errore in setPrezzoScontato (ProdottoDAO)", e);
        }
    }



    public List<ProdottoBean> doSearchByText(String text) throws SQLException {
        List<ProdottoBean> risultati = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT\n" +
                    "              Codice,\n" +
                    "              Nome,\n" +
                    "              Prezzo,\n" +
                    "              Prezzo_scontato,\n" +
                    "              img_path,\n" +
                    "              Condizioni,\n" +
                    "              MATCH(Nome, Descrizione, Colore)\n" +
                    "                AGAINST(? IN NATURAL LANGUAGE MODE) AS rilevanza\n" +
                    "            FROM Prodotto\n" +
                    "            WHERE isDeleted = 0\n" +
                    "              AND MATCH(Nome, Descrizione, Colore)\n" +
                    "                    AGAINST(? IN NATURAL LANGUAGE MODE)\n" +
                    "            ORDER BY rilevanza DESC\n" +
                    "            LIMIT 20");

            ps.setString(1, text);
            ps.setString(2, text);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdottoBean p = new ProdottoBean();
                    p.setCodice(Integer.parseInt(rs.getString("Codice")));
                    p.setNome(rs.getString("Nome"));
                    p.setPrezzo(rs.getDouble("Prezzo"));
                    p.setNewPrezzo(rs.getDouble("prezzo_scontato"));
                    p.setImg_path(rs.getString("img_path"));
                    p.setCondizioni(rs.getString("Condizioni"));
                    p.setRilevanza(rs.getFloat("rilevanza"));
                    risultati.add(p);
                }
                return risultati;
            } catch (SQLException e) {
                throw new RuntimeException("ERRORE DO_TEXT_RESERACH -> ProdottoDAO", e);
            }
        }
    }




    public ProdottoBean doRetrieveByCodiceOrder(int codice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto WHERE Codice = ?");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setCodice(rs.getInt("Codice"));
                prodottoBean.setNome(rs.getString("Nome"));
                prodottoBean.setPrezzo(rs.getDouble("Prezzo"));
                prodottoBean.setDescrizione(rs.getString("Descrizione"));
                prodottoBean.setColore(rs.getString("Colore"));
                prodottoBean.setPeso(rs.getDouble("Peso"));
                prodottoBean.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                prodottoBean.setImg_path(rs.getString("img_path"));

                return prodottoBean;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_RETRIEVE_BY_CODICE -> ProdottoDAO", e);
        }
    }

    public List<ProdottoBean> doRetrieveByCategoria(String categoria) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Prodotto WHERE Categoria = ? AND isDeleted = 0");
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            List<ProdottoBean> prodotti = new ArrayList<>();

            while (rs.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                prodottoBean.setCodice(rs.getInt("Codice"));
                prodottoBean.setNome(rs.getString("Nome"));
                prodottoBean.setPrezzo(rs.getDouble("Prezzo"));
                prodottoBean.setNewPrezzo(rs.getDouble("prezzo_scontato"));
                prodottoBean.setDescrizione(rs.getString("Descrizione"));
                prodottoBean.setPeso(rs.getDouble("Peso"));
                prodottoBean.setColore(rs.getString("Colore"));
                prodottoBean.setMedia_recensioni(rs.getBigDecimal("Media_recensioni"));
                prodottoBean.setImg_path(rs.getString("img_path"));
                prodottoBean.setCondizioni(rs.getString("Condizioni"));

                prodotti.add(prodottoBean);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE DO_RETRIEVE_BY_NOME -> ProdottoDAO", e);
        }
}
}