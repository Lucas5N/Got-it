package Controller;

import Model.Prodotto.ProdottoDAO;
import Model.Vendere.VendereDAO;
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
import java.util.List;
import Model.Prodotto.ProdottoBean;

@WebServlet(name = "profileServlet", value = "/profileServlet")
public class profileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        UtenteBean utente = (session != null) ? (UtenteBean) session.getAttribute("utente") : null;
        if (utente == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente non autenticato");
            return;
        }

        String codiceParam = req.getParameter("codice");
        if (codiceParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametro 'codice' mancante");
            return;
        }

        int codice;
        try {
            codice = Integer.parseInt(codiceParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametro 'codice' non valido");
            return;
        }

        VendereDAO vdao = new VendereDAO();
        vdao.doDelete(utente.getID_Utente(), codice);

        ProdottoDAO pdao = new ProdottoDAO();
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setCodice(codice);
        pdao.doDelete(prodotto);

        List<ProdottoBean> prodotti = vdao.doRetrieveByUtenteId(utente.getID_Utente());
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (ProdottoBean p : prodotti) {
            JsonObjectBuilder obj = Json.createObjectBuilder()
                    .add("codice", p.getCodice())
                    .add("nome", p.getNome())
                    .add("prezzo", p.getPrezzo())
                    .add("discounted", p.getDiscounted());
            arrayBuilder.add(obj);
        }
        out.print(arrayBuilder.build().toString());
        out.flush();
    }
}
