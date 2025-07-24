package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RemoveProductServlet", value = "/removeProduct")
public class RemoveProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response )throws IOException, ServletException {

        ProdottoDAO dao = new ProdottoDAO();
        int id = Integer.parseInt(request.getParameter("codice"));

        if(id != 0) {
            ProdottoBean deleted = dao.doRetrieveByCodice(id);
            dao.doDelete(deleted);
            request.getSession().setAttribute("mexSuccess",
                    "Prodotto eliminato con successo.");
            response.sendRedirect("/admin/RimuoviProdotto.jsp");
            return;
        }

        request.getSession().setAttribute("mexError",
                "Prodotto non eliminato.");

    }
}
