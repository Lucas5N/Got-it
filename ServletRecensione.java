package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import Model.Recensione.RecensioneBean;
import Model.Recensione.RecensioneDAO;
import Model.Utente.UtenteBean;
import Model.Utente.UtenteDAO;
import Model.Vendere.VendereDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "ServletRecensione", value = "/ServletRecensione")
public class ServletRecensione extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pid = req.getParameter("productId");
        if (pid == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "productId mancante");
            return;
        }
        int productId = Integer.parseInt(pid);

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        VendereDAO vendereDAO = new VendereDAO();
        UtenteDAO utenteDAO = new Model.Utente.UtenteDAO();

        ProdottoBean prodotto = prodottoDAO.doRetrieveByCodiceForReview(productId);
        if (prodotto == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
            return;
        }


        int venditoreId = vendereDAO.doRetrieveByProdottoCodice(productId);

        UtenteBean venditore = utenteDAO.doRetrieveById(venditoreId);


        String nomeVenditoreCorretto = (venditore != null && venditore.getUsername() != null)
                ? venditore.getUsername()
                : "Venditore Sconosciuto";

        List<RecensioneBean> recensioni = recensioneDAO.doRetrieveByVenditore(venditoreId);

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        JsonObjectBuilder prodObj = Json.createObjectBuilder()
                .add("codice", prodotto.getCodice())
                .add("nome", prodotto.getNome() != null ? prodotto.getNome() : "Prodotto Sconosciuto")
                .add("nomeVenditore", nomeVenditoreCorretto) // <-- USA LA VARIABILE CORRETTA
                .add("prezzo", prodotto.getPrezzo())
                .add("img_path", prodotto.getImg_path() != null ? prodotto.getImg_path() : "");

        JsonArrayBuilder recArray = Json.createArrayBuilder();
        for (RecensioneBean r : recensioni) {
            ProdottoBean prodottoRecensito = prodottoDAO.doRetrieveByCodiceForReview(r.getProdotto_Codice());
            if (prodottoRecensito != null) {
                recArray.add(Json.createObjectBuilder()
                        .add("voto", r.getVoto())
                        .add("descrizione", r.getDescrizione() != null ? r.getDescrizione() : "")
                        .add("data", r.getData() != null ? r.getData().toString() : "")
                        .add("username", "Utente") // Placeholder
                        .add("nome_prodotto", prodottoRecensito.getNome() != null ? prodottoRecensito.getNome() : "Prodotto non disponibile")
                        .add("prezzo_prodotto", prodottoRecensito.getPrezzo())
                        .add("img_path", prodottoRecensito.getImg_path() != null ? prodottoRecensito.getImg_path() : "")
                );
            }
        }

        JsonObjectBuilder respJson = Json.createObjectBuilder()
                .add("prodotto", prodObj)
                .add("recensioni", recArray);

        out.print(respJson.build().toString());
        out.flush();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UtenteBean utente = (UtenteBean) req.getSession().getAttribute("utente");
        String pid = req.getParameter("productId");
        if (utente == null || pid == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente non autorizzato o productId mancante");
            return;
        }
        int productId = Integer.parseInt(pid);

        ProdottoBean prodotto = new ProdottoDAO().doRetrieveByCodiceForReview(productId);
        if (prodotto == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Prodotto non valido");
            return;
        }

        String descrizione = req.getParameter("descrizione");
        int voto;
        try {
            voto = Integer.parseInt(req.getParameter("voto"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Voto non valido");
            return;
        }

        RecensioneBean rec = new RecensioneBean();
        rec.setID_Utente(utente.getID_Utente());
        rec.setProdotto_Codice(productId);
        rec.setVoto(voto);
        rec.setDescrizione(descrizione);
        rec.setData(new Date(System.currentTimeMillis()));
        boolean ok = new RecensioneDAO().doSave(rec);

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/InitServlet?recensione=ok");
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante il salvataggio della recensione");
        }
    }
}