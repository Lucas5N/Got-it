package Controller;

import Model.CCP.CCPBean;
import Model.CCP.CCPDAO;
import Model.Carta_di_Credito.CDCBean;
import Model.Carta_di_Credito.CDCDAO;
import Model.Ordine.OrdineBean;
import Model.Ordine.OrdineDAO;
import Model.COP.COPBean;
import Model.COP.COPDAO;
import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import Model.Utente.UtenteBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@WebServlet(name = "Checkout", value = "/Checkout")
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if ("chiudi".equals(action)) {
            resp.sendRedirect(req.getContextPath() + "/InitServlet");
            return;
        }

        try{
            String numeroCarta = req.getParameter("numeroCarta");
            String dataParam   = req.getParameter("dataDiScadenza");
            String cvv         = req.getParameter("cvv");

            String rgxNum = "[0-9]{16,19}";
            String rgxData = "(0[1-9]|1[0-2])/([0-9]{2})";
            String rgxCvv = "[0-9]{3}";

            if (numeroCarta == null || !numeroCarta.matches(rgxNum)) {
                req.setAttribute("errore", "Numero carta non rispetta il formato");
                resp.sendRedirect(req.getContextPath() + "/InitServlet");
            }
            if (dataParam == null || !dataParam.matches(rgxData)) {
                req.setAttribute("errore", "Data non rispetta il formato MM/AA");
                resp.sendRedirect(req.getContextPath() + "/InitServlet");
                return;
            }
            if (cvv == null || !cvv.matches(rgxCvv)) {
                req.setAttribute("errore", "CVV non rispetta il formato");
                resp.sendRedirect(req.getContextPath() + "/InitServlet");
                return;
            }

            Date dataScad;
            try {
                dataParam = "01/" + dataParam;
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM'/'yy");
                LocalDate ld = LocalDate.parse(dataParam, fmt);
                dataScad = Date.valueOf(ld.withDayOfMonth(1));
            } catch (DateTimeParseException e) {
                req.setAttribute("errore", "Data non rispetta il formato MM/AA");
                resp.sendRedirect(req.getContextPath() + "/InitServlet");
                return;
            }

            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Sessione non valida");
                return;
            }
            UtenteBean utente = (UtenteBean) session.getAttribute("utente");
            if (utente == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Utente non loggato");
                return;
            }

            List<ProdottoBean> carrello = new ArrayList<>();
            CCPDAO ccpdao = new CCPDAO();
            List<CCPBean> listCCP = ccpdao.doRetrieveByUtente(utente.getID_Utente());
            ProdottoDAO pdao = new ProdottoDAO();
            for (CCPBean ccp : listCCP) {
                ProdottoBean pb = pdao.doRetrieveByCodice(ccp.getProdotto_Codice());
                if (pb != null) carrello.add(pb);
            }
            List<ProdottoBean> sessionCart = (List<ProdottoBean>) session.getAttribute("carrello");
            if (sessionCart != null) {
                carrello.addAll(sessionCart);
            }

            if (carrello.isEmpty()) {
                req.setAttribute("errore", "Carrello vuoto");
                resp.sendRedirect(req.getContextPath() + "/InitServlet");
                return;
            }

            try {
                CDCDAO cdcdao = new CDCDAO();
                List<CDCBean> cards = cdcdao.doRetrieveByUtenteId(utente.getID_Utente());
                boolean exists = cards.stream().anyMatch(c -> numeroCarta.equals(c.getNumero()));
                if (!exists) {
                    CDCBean card = new CDCBean();
                    card.setID_Utente(utente.getID_Utente());
                    card.setNumero(numeroCarta);
                    card.setData_scadenza(dataScad);
                    card.setCVV(cvv);
                    cdcdao.doSave(card);
                }
            } catch (Exception ignored) { }

            LocalDate today = LocalDate.now();
            Date dataAcq = Date.valueOf(today);
            LocalDate ldCon = today.plusDays(ThreadLocalRandom.current().nextInt(2, 6));
            Date dataCon = Date.valueOf(ldCon);

            OrdineDAO ordineDao = new OrdineDAO();
            OrdineBean ord = new OrdineBean();
            ord.setID_Utente(utente.getID_Utente());
            ord.setCarta_di_credito(numeroCarta);
            ord.setData_acquisto(dataAcq);
            ord.setData_consegna(dataCon);
            ord.setQuantita(carrello.size());
            ord.setIndirizzo_di_consegna(
                    utente.getCitta() + ", " + utente.getVia() + " " + utente.getNumero_civico()
            );
            ord.setCosto_consegna(5);
            ord.setStato_consegna(today.isEqual(ldCon) || today.isAfter(ldCon) ? "Consegnata" : "In Elaborazione"
            );
            ordineDao.doSave(ord);

            List<OrdineBean> tutti = ordineDao.doRetrieveByUtenteId(utente.getID_Utente());
            int ordineId = tutti.stream().mapToInt(OrdineBean::getCodice).max().orElseThrow();

            COPDAO copdao = new COPDAO();
            for (ProdottoBean p : carrello) {
                COPBean cop = new COPBean();
                cop.setOrdine_Codice(ordineId);
                cop.setProdotto_Codice(p.getCodice());
                copdao.doSave(cop);
            }

            try{
                for(ProdottoBean prodottoBean : carrello){
                    pdao.doDelete(prodottoBean);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            session.removeAttribute("carrello");

            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/InitServlet?pagamento=ok");

        }catch (Exception e) {
            e.printStackTrace();
            String contextPath = req.getContextPath();
            req.setAttribute("errore", "Errore interno, riprova più tardi");
            resp.sendRedirect(contextPath + "/InitServlet?pagamento");
        }
    }
}
