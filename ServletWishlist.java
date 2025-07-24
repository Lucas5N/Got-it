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



@WebServlet(name = "ServletWishlist", value = "/ServletWishlist")
public class ServletWishlist extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        String action = req.getParameter("action");
        int codice = Integer.parseInt(req.getParameter("codice"));

        List<ProdottoBean> sessionWishlist = (List<ProdottoBean>) session.getAttribute("wishlist");
        if (sessionWishlist == null) {
            sessionWishlist = new ArrayList<>();
            session.setAttribute("wishlist", sessionWishlist);
        }

        if ("remove".equals(action)) {
            if (utente != null) {
                new CWPDAO().doDeleteOne(utente.getID_Utente(), codice);
                CCPBean cartBean = new CCPBean();
                cartBean.setID_Utente(utente.getID_Utente());
                cartBean.setProdotto_Codice(codice);
                new CCPDAO().doSave(cartBean);
            }
            ProdottoBean removed = null;
            for (int i = 0; i < sessionWishlist.size(); i++) {
                if (sessionWishlist.get(i).getCodice() == codice) {
                    removed = sessionWishlist.remove(i);
                    break;
                }
            }
            if (removed != null) {
                List<ProdottoBean> sessionCart = (List<ProdottoBean>) session.getAttribute("carrello");
                if (sessionCart == null) {
                    sessionCart = new ArrayList<>();
                    session.setAttribute("carrello", sessionCart);
                }
                sessionCart.add(removed);
            }
            doGet(req, resp);
            return;
        }

        if ("delete".equals(action)) {
            if (utente != null) {
                new CWPDAO().doDeleteOne(utente.getID_Utente(), codice);
            }
            for (int i = 0; i < sessionWishlist.size(); i++) {
                if (sessionWishlist.get(i).getCodice() == codice) {
                    sessionWishlist.remove(i);
                    break;
                }
            }
            doGet(req, resp);
            return;
        }

        String nome = req.getParameter("nome");
        String descrizione = req.getParameter("descrizione");
        String prezzoStr = req.getParameter("prezzo");
        String prezzoDiscStr = req.getParameter("prezzo_scontato");
        double prezzo = Double.parseDouble(prezzoStr);
        double prezzoDisc = prezzoDiscStr != null && !prezzoDiscStr.isEmpty() ? Double.parseDouble(prezzoDiscStr) : 0.0;
        double finalPrice = prezzoDisc == 0.0 ? prezzo : prezzoDisc;

        boolean isProductInWishlist = false;
        for (ProdottoBean p : sessionWishlist) {
            if (p.getCodice() == codice) {
                isProductInWishlist = true;
                break;
            }
        }

        if (!isProductInWishlist && utente != null) {
            CWPDAO wishlistDAO = new CWPDAO();
            isProductInWishlist = wishlistDAO.isProductInWishlist(utente.getID_Utente(), codice);
        }

        if (!isProductInWishlist) {
            if (utente != null) {
                CWPBean bean = new CWPBean();
                bean.setID_Utente(utente.getID_Utente());
                bean.setProdotto_Codice(codice);
                new CWPDAO().doSave(bean);
            } else {
                ProdottoBean prodottoDaAggiungere = new ProdottoDAO().doRetrieveByCodice(codice);
                if (prodottoDaAggiungere != null) {
                    sessionWishlist.add(prodottoDaAggiungere);
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
            List<CWPBean> fromDb = new CWPDAO().doRetrieveByUtente(utente.getID_Utente());
            for (CWPBean wb : fromDb) {
                ProdottoBean p = prodottoDAO.doRetrieveByCodice(wb.getProdotto_Codice());
                if (p != null) prodotti.add(p);
            }
        }

        List<ProdottoBean> sessionWishlist =
                (List<ProdottoBean>) req.getSession().getAttribute("wishlist");
        if (sessionWishlist != null) {
            prodotti.addAll(sessionWishlist);
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (ProdottoBean p : prodotti) {
            double prezzoToShow = p.getPrezzoScontato() == 0.0 ? p.getPrezzo() : p.getPrezzoScontato();
            JsonObjectBuilder obj = Json.createObjectBuilder()
                    .add("codice", p.getCodice())
                    .add("nome", p.getNome())
                    .add("descrizione", p.getDescrizione())
                    .add("prezzo", prezzoToShow);
            arrayBuilder.add(obj);
        }
        out.print(arrayBuilder.build().toString());
        out.flush();
    }
}