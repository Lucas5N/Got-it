package Controller;

import Model.Utente.UtenteBean;
import Model.Utente.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String redirect = req.getParameter("redirect");

            if (username == null || password == null
                    || username.trim().isEmpty() || password.trim().isEmpty()) {
                req.setAttribute("errore", "Username o password non validi");
                RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
                rd.forward(req, resp);
                return;
            }

            UtenteDAO utenteDAO = new UtenteDAO();
            UtenteBean utente = utenteDAO.doRetrieveByUsernamePassword(username, password);

            if (utente == null) {
                req.setAttribute("errore", "Email o password errati!");
                RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
                rd.forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("utente", utente);

            String ctx = req.getContextPath();
            if (utente.getAmministratore()) {
                session.setAttribute("role", "admin");
                resp.sendRedirect(ctx + "/admin/RimuoviProdotto.jsp");
            } else {
                session.setAttribute("role", "user");
                if (redirect != null && !redirect.isEmpty()) {
                    if (!redirect.startsWith("/")) redirect = "/" + redirect;
                    resp.sendRedirect(ctx + redirect);
                } else {
                    resp.sendRedirect(ctx + "/InitServlet");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errore", "Errore interno del server.");
            RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
            rd.forward(req, resp);
        }
    }
}