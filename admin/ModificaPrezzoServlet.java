package Controller;

import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "modifypriceServlet", value = "/modifypriceServlet")
public class ModificaPrezzoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        ProdottoDAO dao = new ProdottoDAO();

        String prezzoScontato = request.getParameter("newPrice");
        String id = request.getParameter("productId");

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder jsonObj = Json.createObjectBuilder();

        if (id == null || prezzoScontato == null) {
            jsonObj.add("success", false)
                    .add("message", "Parametri mancanti (productId o prezzoScontato).");
            arrayBuilder.add(jsonObj);
            response.getWriter().write(arrayBuilder.build().toString());
            return;
        }

        try {
            int codice = Integer.parseInt(id);
            double prezzo = Double.parseDouble(prezzoScontato);

            ProdottoBean p = dao.doRetrieveByCodice(codice);
            double prezzoOriginale = p.getPrezzo();
            dao.setPrezzoScontato(codice, prezzo);

            jsonObj.add("success", true)
                    .add("productId", codice)
                    .add("prezzoOriginale", prezzoOriginale)
                    .add("prezzoScontato", prezzo);

        } catch (NumberFormatException e) {
            response.setStatus(400);
            jsonObj.add("success", false)
                    .add("message", "Formato numerico non valido");
        } catch (SQLException e) {
            response.setStatus(500);
            jsonObj.add("success", false)
                    .add("message", "Errore SQL");
        } catch (Exception e) {
            response.setStatus(500);
            jsonObj.add("success", false)
                    .add("message", "Errore interno");
        }

        arrayBuilder.add(jsonObj);
        response.getWriter().write(arrayBuilder.build().toString());


    }
}
