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

@WebServlet(name = "Edituserservlet", value = "/editUserServlet")
public class EdituserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UtenteDAO utenteDAO = new UtenteDAO();
        HttpSession session = request.getSession();

                UtenteBean utente = (UtenteBean) session.getAttribute("utente");

                if (utente == null) {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }

                UtenteBean utenteModificato = new UtenteBean();
                utenteModificato.setID_Utente(utente.getID_Utente());
                utenteModificato.setNome(utente.getNome());
                utenteModificato.setCognome(utente.getCognome());

                utenteModificato.setEmail(utente.getEmail());
                utenteModificato.setUsername(utente.getUsername());
                utenteModificato.setPassword(utente.getPassword());
                utenteModificato.setStato(utente.getStato());
                utenteModificato.setRegione(utente.getRegione());
                utenteModificato.setCitta(utente.getCitta());
                utenteModificato.setTelefono(utente.getTelefono());
                utenteModificato.setVia(utente.getVia());
                utenteModificato.setNumero_civico(utente.getNumero_civico());
                utenteModificato.setConto_corrente_addebbitabile(utente.getConto_corrente_addebbitabile());

                String newEmail = request.getParameter("newEmail");
                if (newEmail != null && !newEmail.trim().isEmpty()) {
                    utenteModificato.setEmail(newEmail.trim());
                }

                String newUsername = request.getParameter("newUsername");
                if (newUsername != null) {
                    utenteModificato.setUsername(newUsername);
                }

        String newPassword = request.getParameter("newPassword");
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            utenteModificato.setPassword(newPassword);
        }

        String newStato = request.getParameter("newStato");
                if (newStato != null && !newStato.trim().isEmpty()) {
                    utenteModificato.setStato(newStato.trim());
                }

                String newRegione = request.getParameter("newRegione");
                if (newRegione != null && !newRegione.trim().isEmpty()) {
                    utenteModificato.setRegione(newRegione.trim());
                }

                String newCitta = request.getParameter("newCitta");
                if (newCitta != null && !newCitta.trim().isEmpty()) {
                    utenteModificato.setCitta(newCitta.trim());
                }

                String newTelefono = request.getParameter("newTelefono");
                if (newTelefono != null && !newTelefono.trim().isEmpty()) {
                    utenteModificato.setTelefono(newTelefono.trim());
                }

                String newVia = request.getParameter("newVia");
                if (newVia != null && !newVia.trim().isEmpty()) {
                    utenteModificato.setVia(newVia.trim());
                }

                String newCivico = request.getParameter("newCivico");
                if (newCivico != null && !newCivico.trim().isEmpty()) {
                    utenteModificato.setNumero_civico(newCivico.trim());
                }

                String newContoCorrente = request.getParameter("newContoCorrente");
                if (newContoCorrente != null && !newContoCorrente.trim().isEmpty()) {
                    utenteModificato.setConto_corrente_addebbitabile(newContoCorrente.trim());
                }

                utenteDAO.doUpdate(utenteModificato);
                String result = "Dati inseriti correttamente";

                session.setAttribute("utente", utenteModificato);
                session.setAttribute("result", result);

                RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);

            }
        }
