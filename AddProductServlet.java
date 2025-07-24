package Controller;

import Model.Immagine.ImmagineBean;
import Model.Immagine.ImmagineDAO;
import Model.Prodotto.ProdottoBean;
import Model.Prodotto.ProdottoDAO;
import Model.Vendere.VendereBean;
import Model.Vendere.VendereDAO;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/add-product")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AddProductServlet extends HttpServlet {


    private static final String UPLOAD_DIRECTORY = "C:\\Users\\Utente\\Desktop\\uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeProdotto, categoria, colore, descrizione, condizione;
        double prezzo, peso;
        int userId;

        try {
            nomeProdotto = request.getParameter("name-product");
            prezzo = Double.parseDouble(request.getParameter("prezzo"));
            categoria = request.getParameter("category");
            colore = request.getParameter("colore");
            descrizione = request.getParameter("descrizione");
            condizione = request.getParameter("condizione");
            peso = Double.parseDouble(request.getParameter("peso"));
            userId = Integer.parseInt(request.getParameter("user_id"));

            if (nomeProdotto == null || nomeProdotto.trim().isEmpty() ||
                    categoria == null || categoria.trim().isEmpty() ||
                    descrizione == null || descrizione.trim().length() < 50 ||
                    condizione == null || condizione.trim().isEmpty())
            {
                response.sendRedirect("addproduct.jsp?error=missingFields");
                return;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("addproduct.jsp?error=invalidNumber");
            return;
        }



        List<String> savedFileNames = new ArrayList<>();
        try {

            String sanitizedCategory = categoria.replaceAll("[^a-zA-Z0-9]", "");
            File categoryUploadDir = new File(UPLOAD_DIRECTORY, sanitizedCategory);

            if (!categoryUploadDir.exists()) {
                categoryUploadDir.mkdirs();
            }
            List<Part> fileParts = request.getParts().stream()
                    .filter(part -> "immagine".equals(part.getName()) && part.getSize() > 0)
                    .collect(Collectors.toList());

            for (Part filePart : fileParts) {
                String submittedFileName = filePart.getSubmittedFileName();
                String fileName = Paths.get(submittedFileName).getFileName().toString();
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

                String absoluteFilePath = categoryUploadDir.getAbsolutePath() + File.separator + uniqueFileName;
                filePart.write(absoluteFilePath);

                String relativePath = "/" + sanitizedCategory + "/" + uniqueFileName;
                savedFileNames.add(relativePath);

            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendRedirect("addproduct.jsp?error=uploadFailed");
            return;
        }

        if (savedFileNames.isEmpty()) {
            response.sendRedirect("addproduct.jsp?error=noImage");
            return;
        }

        try {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            ImmagineDAO immagineDAO = new ImmagineDAO();
            VendereDAO vendereDAO = new VendereDAO();

            ProdottoBean prodotto = new ProdottoBean();
            prodotto.setNome(nomeProdotto);
            prodotto.setPrezzo(prezzo);
            prodotto.setCategoria(categoria);
            prodotto.setColore(colore);
            prodotto.setDescrizione(descrizione);
            prodotto.setCondizioni(condizione);
            prodotto.setPeso(peso);
            prodotto.setImg_path(savedFileNames.get(0));

            int generatedProductId = prodottoDAO.doSave(prodotto);

            if (generatedProductId <= 0) {
                throw new SQLException("Impossibile salvare il prodotto nessun ID ottenuto");
            }

            VendereBean vendere = new VendereBean();
            vendere.setID_Utente(userId);
            vendere.setProdotto_Codice(generatedProductId);
            vendereDAO.doSave(vendere.getID_Utente(), generatedProductId);

            for (String fileName : savedFileNames) {
                ImmagineBean immagine = new ImmagineBean();
                immagine.setCodice(generatedProductId);
                immagine.setPath(fileName);
                immagineDAO.doSave(immagine);
            }

            response.sendRedirect(request.getContextPath() + "/InitServlet?productAdded=true");

        } catch (SQLException e) {
            e.printStackTrace();

            response.sendRedirect("addproduct.jsp?error=dbError");

        }
    }
}