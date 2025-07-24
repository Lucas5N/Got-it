package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import Model.Recensione.RecensioneBean;
import Model.Recensione.RecensioneDAO;
import Model.Utente.UtenteBean;
import Model.Utente.UtenteDAO;
import Model.Vendere.VendereDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import static java.lang.System.out;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String id = request.getParameter("id");
        if(id == null || id.isEmpty()){
            response.sendRedirect("index.jsp");
        }

        int id_prodotto = Integer.parseInt(id);
        out.println(id_prodotto);

        VendereDAO dao = new VendereDAO();
        ProdottoDAO pdao = new ProdottoDAO();
        UtenteDAO udao = new UtenteDAO();
        int id_utente = dao.doRetrieveByProdottoCodice(id_prodotto);

        out.println(id_utente);

        UtenteBean utenteProd = udao.doRetrieveById(id_utente);
        ProdottoBean prodottoSel = pdao.doRetrieveByCodice(id_prodotto);

        if(prodottoSel == null){
            out.println("Prodotto non trovato");
        }

        if(utenteProd == null){
            out.println("Utente non trovato");
        }

        HttpSession session = request.getSession();
        session.setAttribute("product", prodottoSel);
        session.setAttribute("seller", utenteProd);
        response.sendRedirect(request.getContextPath() + "/paginaProdotto.jsp");
    }
}
