package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InitServlet", value = "/InitServlet")
public class InitServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<ProdottoBean> productList = prodottoDAO.doRetrieveAll();

        request.setAttribute("productList", productList);

        String recensioneStatus = request.getParameter("recensione");
        if ("ok".equals(recensioneStatus)) {
            request.setAttribute("recensione", "ok");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/homePage.jsp");
        rd.forward(request, response);
    }
}