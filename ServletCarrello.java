package Controller;
import Model.CCP.CCPBean;
import Model.CCP.CCPDAO;
import Model.CWP.CWPBean;
import Model.CWP.CWPDAO;
import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import Model.Utente.UtenteBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "ServletCarrello", value = "/ServletCarrello")
public class ServletCarrello extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        String action = req.getParameter("action");
        int codice = Integer.parseInt(req.getParameter("codice"));

        List<ProdottoBean> sessionCart = (List<ProdottoBean>) session.getAttribute("carrello");
        if (sessionCart == null) {
            sessionCart = new ArrayList<>();
            session.setAttribute("carrello", sessionCart);
        }

        if ("remove".equals(action)) {
            if (utente != null) {
                new CCPDAO().doDeleteOne(utente.getID_Utente(), codice);
            }
            sessionCart.removeIf(p -> p.getCodice() == codice);
            doGet(req,resp);
            return;

        } else {
            boolean isProductInCart = sessionCart.stream().anyMatch(p -> p.getCodice() == codice);

            if (!isProductInCart && utente != null) {
                isProductInCart = new CCPDAO().isProductInCart(utente.getID_Utente(), codice);
            }

            if (!isProductInCart) {
                ProdottoBean prodottoDaAggiungere = new ProdottoDAO().doRetrieveByCodice(codice);

                if (prodottoDaAggiungere != null) {
                    if (utente != null) {
                        CCPBean ccp = new CCPBean();
                        ccp.setID_Utente(utente.getID_Utente());
                        ccp.setProdotto_Codice(codice);
                        new CCPDAO().doSave(ccp);
                    } else {
                        sessionCart.add(prodottoDaAggiungere);
                    }
                }
            }
        }

        resp.sendRedirect("paginaProdotto.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UtenteBean utente = (UtenteBean) req.getSession().getAttribute("utente");
        List<ProdottoBean> prodotti = new ArrayList<>();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        if (utente != null) {
            List<CCPBean> fromDb = new CCPDAO().doRetrieveByUtente(utente.getID_Utente());
            for (CCPBean ccp : fromDb) {
                ProdottoBean p = prodottoDAO.doRetrieveByCodice(ccp.getProdotto_Codice());
                if (p != null) prodotti.add(p);
            }
        }

        List<ProdottoBean> sessionCart = (List<ProdottoBean>) req.getSession().getAttribute("carrello");
        if (sessionCart != null) {
            prodotti.addAll(sessionCart);
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (ProdottoBean p : prodotti) {
            JsonObjectBuilder obj = Json.createObjectBuilder()
                    .add("codice", p.getCodice())
                    .add("nome", p.getNome())
                    .add("descrizione", p.getDescrizione())
                    .add("prezzo", p.getPrezzoScontato() == 0.0 ? p.getPrezzo() : p.getPrezzoScontato())
                    .add("img_path", p.getImg_path());
            arrayBuilder.add(obj);
        }
        out.print(arrayBuilder.build().toString());
        out.flush();
    }
}





