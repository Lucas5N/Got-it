package Controller;

import Model.Utente.UtenteBean;
import Model.Utente.UtenteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String ctx = req.getContextPath();

        String username = req.getParameter("username");
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String email = req.getParameter("email");
        String stato = req.getParameter("stato");
        String regione = req.getParameter("regione");
        String citta = req.getParameter("citta");
        String via = req.getParameter("via");
        String civico = req.getParameter("n_civico");
        String telefono = req.getParameter("telefono");
        String contoCorrente = req.getParameter("conto_corrente");
        String password = req.getParameter("password");
        String confermaPassword = req.getParameter("conferma_Password");

        // Pattern di validazione aggiornati
        String usernamePattern = "^[A-Za-z0-9_]{3,20}$";
        String nomeCognomePattern = "^[A-Za-zÀ-ÿ ']{2,30}$";
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String statoRegioneCittaPattern = "^[A-Za-zÀ-ÿ ']{2,40}$";
        String viaPattern = "^[A-Za-zÀ-ÿ0-9 ']{2,50}$";
        String civicoPattern = "^\\d{1,4}[A-Za-z]?$";
        String telefonoPattern = "^\\d{8,13}$";
        String contoCorrentePattern = "^IT\\d{2}[A-Z]\\d{10,30}$";
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_=+?-]).{8,}$";

        // Controlli
        if (!username.matches(usernamePattern)) {
            req.setAttribute("errore", "Username non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!nome.matches(nomeCognomePattern)) {
            req.setAttribute("errore", "Nome non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!cognome.matches(nomeCognomePattern)) {
            req.setAttribute("errore", "Cognome non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!email.matches(emailPattern)) {
            req.setAttribute("errore", "Email non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!stato.matches(statoRegioneCittaPattern)) {
            req.setAttribute("errore", "Stato non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!regione.matches(statoRegioneCittaPattern)) {
            req.setAttribute("errore", "Regione non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!citta.matches(statoRegioneCittaPattern)) {
            req.setAttribute("errore", "Città non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!via.matches(viaPattern)) {
            req.setAttribute("errore", "Via non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!civico.matches(civicoPattern)) {
            req.setAttribute("errore", "Numero civico non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!telefono.matches(telefonoPattern)) {
            req.setAttribute("errore", "Telefono non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!contoCorrente.matches(contoCorrentePattern)) {
            req.setAttribute("errore", "Conto corrente non rispetta il formato IBAN italiano");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!password.matches(passwordPattern)) {
            req.setAttribute("errore", "Password non rispetta il formato");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(confermaPassword)) {
            req.setAttribute("errore", "Le password non coincidono");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }

        UtenteDAO utenteDAO = new UtenteDAO();

        if (utenteDAO.doRetrieveByEmail(email) != null || utenteDAO.doRetrieveByUsername(username) != null) {
            req.setAttribute("errore", "Username o email già registrati");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }

        UtenteBean utente = new UtenteBean();
        utente.setUsername(username);
        utente.setPassword(password);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setStato(stato);
        utente.setRegione(regione);
        utente.setCitta(citta);
        utente.setEmail(email);
        utente.setTelefono(telefono);
        utente.setVia(via);
        utente.setNumero_civico(civico);
        utente.setConto_corrente_addebbitabile(contoCorrente);

        req.getSession().setAttribute("utente", utente);
        utenteDAO.doSave(utente);

        resp.sendRedirect(ctx + "/InitServlet");
    }
}
