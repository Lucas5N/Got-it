package Controller;

import Model.COP.COPBean;
import Model.COP.COPDAO;
import Model.Ordine.OrdineBean;
import Model.Ordine.OrdineDAO;
import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import Model.Recensione.RecensioneDAO;
import Model.Utente.UtenteBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import javax.json.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "ServletOrdini", value = "/ServletOrdini")
public class ServletOrdini extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UtenteBean utenteBean = (UtenteBean) req.getSession().getAttribute("utente");
        if (utenteBean == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente non loggato");
            return;
        }

        List<OrdineBean> ordiniDB = new OrdineDAO().doRetrieveByUtenteId(utenteBean.getID_Utente());

        HashMap<Integer, List<COPBean>> prodottiPerOrdine = new HashMap<>();
        COPDAO copdao = new COPDAO();
        for (OrdineBean o : ordiniDB) {
            prodottiPerOrdine.put(
                    o.getCodice(),
                    copdao.doRetrieveByOrdine(o.getCodice())
            );
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        JsonArrayBuilder arrayOrdini = Json.createArrayBuilder();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        RecensioneDAO recensioneDAO = new RecensioneDAO();

        for (OrdineBean ordineBean : ordiniDB) {
            List<COPBean> listaCop = prodottiPerOrdine.get(ordineBean.getCodice());

            JsonObjectBuilder ordineObj = Json.createObjectBuilder()
                    .add("ID_Ordine", ordineBean.getCodice())
                    .add("dataAcquisto", ordineBean.getData_acquisto().toString())
                    .add("stato", ordineBean.getStato_consegna())
                    .add("consegnaStimata", ordineBean.getData_consegna().toString());

            JsonArrayBuilder prodottiInOrdine = Json.createArrayBuilder();

            if (listaCop != null) {
                for (COPBean cp : listaCop) {
                    ProdottoBean pbean = prodottoDAO.doRetrieveByCodiceOrder(cp.getProdotto_Codice());

                    if (pbean == null) continue;

                    boolean isReviewed = recensioneDAO.hasUserReviewedProduct(utenteBean.getID_Utente(), pbean.getCodice());

                    JsonObjectBuilder prodottoObj = Json.createObjectBuilder()
                            .add("prodottoCodice", cp.getProdotto_Codice())
                            .add("nome", pbean.getNome())
                            .add("reviewed", isReviewed);

                    if (pbean.getPrezzoScontato() > 0 &&
                            pbean.getPrezzoScontato() != pbean.getPrezzo()) {
                        prodottoObj.add("prezzo", pbean.getPrezzoScontato());
                    } else {
                        prodottoObj.add("prezzo", pbean.getPrezzo());
                    }

                    prodottiInOrdine.add(prodottoObj);
                }
            }
            ordineObj.add("prodotti", prodottiInOrdine);
            arrayOrdini.add(ordineObj);
        }

        out.print(arrayOrdini.build().toString());
        out.flush();
    }
}