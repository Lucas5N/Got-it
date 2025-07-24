package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
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
import java.sql.SQLException;

@WebServlet(name="modifypriceServlet", value="/modifypriceServlet")
public class ModificaPrezzoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            out.print("{\"success\":false,\"message\":\"Utente non autenticato\"}");
            return;
        }

        int id;
        double newPrice, originale;
        try {
            id        = Integer.parseInt(req.getParameter("id"));
            newPrice  = Double.parseDouble(req.getParameter("prezzo_scontato").replace(',', '.'));
            originale = Double.parseDouble(req.getParameter("originale").replace(',', '.'));
        } catch (NumberFormatException ex) {
            out.print("{\"success\":false,\"message\":\"Parametri non validi\"}");
            return;
        }

        ProdottoDAO dao = new ProdottoDAO();
        boolean success, isDiscount;
        String  prezzoFormattato = String.format("%.2f", newPrice).replace('.', ',');

        try {
            if (newPrice < originale) {
                success    = dao.setPrezzoScontato(id, newPrice);
                isDiscount = true;


            } else if (newPrice > originale ||  newPrice == originale) {
                success    = dao.setPrezzo(id, newPrice);
                isDiscount = dao.setPrezzoScontato(id, 0);


            } else {
                out.print("{\"success\":false,\"message\":\"Prezzo invariato\"}");
                return;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            out.print("{\"success\":false,\"message\":\"Errore DB\"}");
            return;
        }

        out.print("{\"success\":" + success +
                ",\"isDiscount\":" + isDiscount +
                ",\"prezzoFormattato\":\"" + prezzoFormattato + "\"}");
    }
}