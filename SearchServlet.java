package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchServlet")
public class SearchServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String query = request.getParameter("search");
        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        List<ProdottoBean> prodotti = new ArrayList<>();
        ProdottoDAO dao = new ProdottoDAO();
        try {
            prodotti = dao.doSearchByText(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("prodotti", prodotti);
        request.setAttribute("query", query);
        request.getRequestDispatcher("/searchResults.jsp")
                .forward(request, response);
    }


    }


