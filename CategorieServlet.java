package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CategorieServlet")
public class CategorieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String categoria = req.getParameter("category");
        if(categoria==null || categoria.isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        List<ProdottoBean> prodotti = new ArrayList<ProdottoBean>();
        ProdottoDAO dao = new ProdottoDAO();

        prodotti = dao.doRetrieveByCategoria(categoria);

        if(prodotti.isEmpty()){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        req.setAttribute("prodottiC", prodotti);
        req.setAttribute("categoria", categoria);
        req.getRequestDispatcher("/categoria.jsp")
                .forward(req, resp);
    }

}